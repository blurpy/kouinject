
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This file is part of KouInject.                                       *
 *                                                                         *
 *   KouInject is free software; you can redistribute it and/or modify     *
 *   it under the terms of the GNU Lesser General Public License as        *
 *   published by the Free Software Foundation, either version 3 of        *
 *   the License, or (at your option) any later version.                   *
 *                                                                         *
 *   KouInject is distributed in the hope that it will be useful,          *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU      *
 *   Lesser General Public License for more details.                       *
 *                                                                         *
 *   You should have received a copy of the GNU Lesser General Public      *
 *   License along with KouInject.                                         *
 *   If not, see <http://www.gnu.org/licenses/>.                           *
 ***************************************************************************/

package net.usikkert.kouinject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.CollectionBeanKey;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.FieldData;
import net.usikkert.kouinject.beandata.InjectionPoint;
import net.usikkert.kouinject.beandata.MethodData;
import net.usikkert.kouinject.beandata.ProviderBeanKey;

import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link BeanDataHandler} that uses annotations and reflection
 * to extract meta-data from beans to find dependencies.
 *
 * <p>Scans beans for the {@link Inject} annotation to detect constructor, fields and methods
 * for dependency injection. They are then scanned for the {@link Qualifier} annotation to
 * find the required qualifier for the dependency.</p>
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanDataHandler implements BeanDataHandler {

    private static final Class<Inject> INJECTION_ANNOTATION = Inject.class;

    private final AnnotationBasedQualifierHandler qualifierHandler = new AnnotationBasedQualifierHandler();
    private final AnnotationBasedScopeHandler scopeHandler = new AnnotationBasedScopeHandler();
    private final ReflectionUtils reflectionUtils = new ReflectionUtils();

    /**
     * {@inheritDoc}
     */
    @Override
    public BeanData getBeanData(final Class<?> beanClass, final boolean skipConstructor) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final List<Method> allMethods = reflectionUtils.findAllMethods(beanClass);
        final List<Member> allMembers = reflectionUtils.findAllMembers(beanClass);
        final List<InjectionPoint> injectionPoints = findInjectionPoints(allMembers, allMethods);
        final ConstructorData constructorData = createConstructorDataIfNeeded(beanClass, skipConstructor);
        final boolean singleton = scopeHandler.isSingleton(beanClass);

        return new BeanData(beanClass, constructorData, injectionPoints, singleton);
    }

    private ConstructorData createConstructorDataIfNeeded(final Class<?> beanClass, final boolean skipConstructor) {
        if (skipConstructor) {
            return null;
        }

        final Constructor<?> constructor = findConstructor(beanClass);
        final ConstructorData constructorData = createConstructorData(constructor);

        return constructorData;
    }

    private List<InjectionPoint> findInjectionPoints(final List<Member> allMembers, final List<Method> allMethods) {
        final List<InjectionPoint> injectionPoints = new ArrayList<InjectionPoint>();

        for (final Member member : allMembers) {
            if (member instanceof Field) {
                final Field field = (Field) member;

                if (fieldNeedsInjection(field)) {
                    final FieldData fieldData = createFieldData(field);
                    injectionPoints.add(fieldData);
                }
            }

            else if (member instanceof Method) {
                final Method method = (Method) member;

                if (methodNeedsInjection(method) && !reflectionUtils.isOverridden(method, allMethods)) {
                    final MethodData methodData = createMethodData(method);
                    injectionPoints.add(methodData);
                }
            }

            else {
                throw new UnsupportedOperationException("Unsupported member: " + member);
            }
        }

        return injectionPoints;
    }

    private boolean fieldNeedsInjection(final Field field) {
        return !reflectionUtils.isStatic(field)
             && !reflectionUtils.isFinal(field)
             && field.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private FieldData createFieldData(final Field field) {
        final BeanKey dependency = findDependency(field);
        final FieldData fieldData = new FieldData(field, dependency);

        return fieldData;
    }

    private BeanKey findDependency(final Field field) {
        final Class<?> fieldBeanClass = field.getType();
        final String qualifier = qualifierHandler.getQualifier(field, field.getAnnotations());

        if (isProvider(fieldBeanClass)) {
            final Type genericType = field.getGenericType();
            final Class<?> beanClassFromProvider = getBeanClassFromGenericType(field, genericType);

            return new ProviderBeanKey(beanClassFromProvider, qualifier);
        }

        else if (isCollection(fieldBeanClass)) {
            final Type genericType = field.getGenericType();
            final Class<?> beanClassFromCollection = getBeanClassFromGenericType(field, genericType);

            return new CollectionBeanKey(beanClassFromCollection, qualifier);
        }

        else {
            return new BeanKey(fieldBeanClass, qualifier);
        }
    }

    private boolean methodNeedsInjection(final Method method) {
        return !reflectionUtils.isStatic(method) && method.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private MethodData createMethodData(final Method method) {
        final List<BeanKey> dependencies = findDependencies(method);
        final MethodData methodData = new MethodData(method, dependencies);

        return methodData;
    }

    private List<BeanKey> findDependencies(final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        return findDependencies(method, parameterTypes, genericParameterTypes, parameterAnnotations);
    }

    private Constructor<?> findConstructor(final Class<?> beanClass) {
        final Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        final List<Constructor<?>> matches = new ArrayList<Constructor<?>>();

        for (final Constructor<?> constructor : declaredConstructors) {
            if (constructorNeedsInjection(constructor)) {
                matches.add(constructor);
            }
        }

        if (matches.size() == 0) {
            try {
                return beanClass.getDeclaredConstructor();
            }

            catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        else if (matches.size() > 1) {
            throw new UnsupportedOperationException(
                    "Wrong number of constructors found for autowiring " + beanClass + " " + matches);
        }

        return matches.get(0);
    }

    private boolean constructorNeedsInjection(final Constructor<?> constructor) {
        return constructor.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private ConstructorData createConstructorData(final Constructor<?> constructor) {
        final List<BeanKey> dependencies = findDependencies(constructor);
        final ConstructorData constructorData = new ConstructorData(constructor, dependencies);

        return constructorData;
    }

    private List<BeanKey> findDependencies(final Constructor<?> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Type[] genericParameterTypes = constructor.getGenericParameterTypes();
        final Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();

        return findDependencies(constructor, parameterTypes, genericParameterTypes, parameterAnnotations);
    }

    private List<BeanKey> findDependencies(final Object parameterOwner, final Class<?>[] parameterTypes,
            final Type[] genericParameterTypes, final Annotation[][] annotations) {
        final List<BeanKey> dependencies = new ArrayList<BeanKey>();

        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterClass = parameterTypes[i];
            final Type parameterType = genericParameterTypes[i];

            final BeanKey dependency = findDependency(parameterOwner, parameterClass, parameterType, annotations[i]);
            dependencies.add(dependency);
        }

        return dependencies;
    }

    private BeanKey findDependency(final Object parameterOwner, final Class<?> parameterClass,
            final Type parameterType, final Annotation[] annotations) {
        final String qualifier = qualifierHandler.getQualifier(parameterOwner, annotations);

        if (isProvider(parameterClass)) {
            final Class<?> beanClassFromProvider = getBeanClassFromGenericType(parameterOwner, parameterType);

            return new ProviderBeanKey(beanClassFromProvider, qualifier);
        }

        else if (isCollection(parameterClass)) {
            final Class<?> beanClassFromCollection = getBeanClassFromGenericType(parameterOwner, parameterType);

            return new CollectionBeanKey(beanClassFromCollection, qualifier);
        }

        else {
            return new BeanKey(parameterClass, qualifier);
        }
    }

    private boolean isProvider(final Class<?> parameterType) {
        return Provider.class.isAssignableFrom(parameterType);
    }

    private boolean isCollection(final Class<?> parameterType) {
        return Collection.class.isAssignableFrom(parameterType);
    }

    private Class<?> getBeanClassFromGenericType(final Object parameterOwner, final Type genericParameterType) {
        if (genericParameterType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            final Type[] typeArguments = parameterizedType.getActualTypeArguments();
            final Type firstTypeArgument = typeArguments[0];

            if (firstTypeArgument instanceof Class<?>) {
                return (Class<?>) firstTypeArgument;
            }
        }

        throw new IllegalArgumentException("Generic class used without type argument: " + parameterOwner);
    }
}
