
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;

import net.usikkert.kouinject.util.Validate;

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

    private static final Class<Qualifier> QUALIFIER_ANNOTATION = Qualifier.class;

    /**
     * {@inheritDoc}
     */
    @Override
    public BeanData getBeanData(final Class<?> beanClass, final boolean skipConstructor) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final List<Field> fields = findFields(beanClass);
        final List<FieldData> fieldData = createFieldData(fields);

        final List<Method> methods = findMethods(beanClass);
        final List<MethodData> methodData = createMethodData(methods);

        final BeanData beanData;

        if (!skipConstructor) {
            final Constructor<?> constructor = findConstructor(beanClass);
            final ConstructorData constructorData = createConstructorData(constructor);

            beanData = new BeanData(beanClass, constructorData, fieldData, methodData);
        }

        else {
            beanData = new BeanData(beanClass, fieldData, methodData);
        }

        return beanData;
    }

    private List<Field> findFields(final Class<?> beanClass) {
        final Field[] declaredFields = beanClass.getDeclaredFields();
        final List<Field> fields = new ArrayList<Field>();

        for (final Field field : declaredFields) {
            if (fieldNeedsInjection(field)) {
                fields.add(field);
            }
        }

        return fields;
    }

    private boolean fieldNeedsInjection(final Field field) {
        return field.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private List<FieldData> createFieldData(final List<Field> fields) {
        final List<FieldData> fieldDataList = new ArrayList<FieldData>();

        for (final Field field : fields) {
            final FieldData fieldData = createFieldData(field);
            fieldDataList.add(fieldData);
        }

        return fieldDataList;
    }

    private FieldData createFieldData(final Field field) {
        final Dependency dependency = findDependency(field);
        final FieldData fieldData = new FieldData(field, dependency);

        return fieldData;
    }

    private Dependency findDependency(final Field field) {
        final Class<?> fieldBeanClass = field.getType();
        final String qualifier = getQualifier(field);

        if (isProvider(fieldBeanClass)) {
            final Type genericType = field.getGenericType();
            final Class<?> beanClassFromProvider = getBeanClassFromProvider(field, genericType);

            return new Dependency(beanClassFromProvider, true, qualifier);
        }

        else {
            return new Dependency(fieldBeanClass, false, qualifier);
        }
    }

    private List<Method> findMethods(final Class<?> beanClass) {
        final Method[] declaredMethods = beanClass.getDeclaredMethods();
        final List<Method> methods = new ArrayList<Method>();

        for (final Method method : declaredMethods) {
            if (methodNeedsInjection(method)) {
                methods.add(method);
            }
        }

        return methods;
    }

    private boolean methodNeedsInjection(final Method method) {
        return method.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private List<MethodData> createMethodData(final List<Method> methods) {
        final List<MethodData> methodDataList = new ArrayList<MethodData>();

        for (final Method method : methods) {
            final MethodData methodData = createMethodData(method);
            methodDataList.add(methodData);
        }

        return methodDataList;
    }

    private MethodData createMethodData(final Method method) {
        final List<Dependency> dependencies = findDependencies(method);
        final MethodData methodData = new MethodData(method, dependencies);

        return methodData;
    }

    private List<Dependency> findDependencies(final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();

        return findDependencies(method, parameterTypes, genericParameterTypes);
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

            catch (final SecurityException e) {
                throw new RuntimeException(e);
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
        final List<Dependency> dependencies = findDependencies(constructor);
        final ConstructorData constructorData = new ConstructorData(constructor, dependencies);

        return constructorData;
    }

    private List<Dependency> findDependencies(final Constructor<?> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Type[] genericParameterTypes = constructor.getGenericParameterTypes();

        return findDependencies(constructor, parameterTypes, genericParameterTypes);
    }

    private List<Dependency> findDependencies(final AnnotatedElement parameterOwner,
            final Class<?>[] parameterTypes, final Type[] genericParameterTypes) {
        final List<Dependency> dependencies = new ArrayList<Dependency>();

        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterClass = parameterTypes[i];
            final Type parameterType = genericParameterTypes[i];

            final Dependency dependency = findDependency(parameterOwner, parameterClass, parameterType);
            dependencies.add(dependency);
        }

        return dependencies;
    }

    private Dependency findDependency(final AnnotatedElement parameterOwner, final Class<?> parameterClass, final Type parameterType) {
        final String qualifier = getQualifier(parameterOwner);

        if (isProvider(parameterClass)) {
            final Class<?> beanClassFromProvider = getBeanClassFromProvider(parameterOwner, parameterType);

            return new Dependency(beanClassFromProvider, true, qualifier);
        }

        else {
            return new Dependency(parameterClass, false, qualifier);
        }
    }

    private String getQualifier(final AnnotatedElement parameterOwner) {
        final Annotation[] annotations = parameterOwner.getAnnotations();
        final List<String> matches = new ArrayList<String>();

        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(QUALIFIER_ANNOTATION)) {
                matches.add(getQualifier(parameterOwner, annotation));
            }
        }

        if (matches.size() == 0) {
            return null;
        }

        else if (matches.size() > 1) {
            // TODO add test
            throw new UnsupportedOperationException(
                    "Wrong number of qualifier annotations found on " + parameterOwner + " " + matches);
        }

        return matches.get(0);
    }

    private String getQualifier(final AnnotatedElement parameterOwner, final Annotation annotation) {
        if (annotation instanceof Named) {
            final Named named = (Named) annotation;
            final String value = named.value();

            if (value == null || value.trim().length() == 0) {
                // TODO add test
                throw new UnsupportedOperationException(
                        "Named qualifier annotation used without a value on " + parameterOwner);
            }

            return value;
        }

        else {
            return annotation.annotationType().getSimpleName();
        }
    }

    private boolean isProvider(final Class<?> parameterType) {
        return Provider.class.isAssignableFrom(parameterType);
    }

    private Class<?> getBeanClassFromProvider(final Object parameterOwner, final Type genericParameterType) {
        if (genericParameterType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            final Type[] typeArguments = parameterizedType.getActualTypeArguments();
            final Class<?> beanClassFromProvider = (Class<?>) typeArguments[0];

            return beanClassFromProvider;
        }

        throw new IllegalArgumentException("Provider used without generic argument: " + parameterOwner);
    }
}
