
/***************************************************************************
 *   Copyright 2009-2011 by Christian Ihle                                 *
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.FieldData;
import net.usikkert.kouinject.beandata.InjectionPoint;
import net.usikkert.kouinject.beandata.MethodData;
import net.usikkert.kouinject.util.BeanHelper;
import net.usikkert.kouinject.util.ReflectionUtils;

import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link BeanDataHandler} that uses annotations and reflection
 * to extract meta-data from beans to find dependencies.
 *
 * <p>Scans beans for the {@link Inject} annotation to detect constructor, fields and methods
 * for dependency injection. They are then scanned for the {@link javax.inject.Qualifier} annotation to
 * find the required qualifier for the dependency.</p>
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanDataHandler implements BeanDataHandler {

    private static final Class<Inject> INJECTION_ANNOTATION = Inject.class;

    private final AnnotationBasedScopeHandler scopeHandler = new AnnotationBasedScopeHandler();
    private final ReflectionUtils reflectionUtils = new ReflectionUtils();
    private final BeanHelper beanHelper = new BeanHelper();

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

        return createConstructorData(constructor);
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
        return !reflectionUtils.isStatic(field) &&
               !reflectionUtils.isFinal(field) &&
               field.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private FieldData createFieldData(final Field field) {
        final BeanKey dependency = beanHelper.findFieldKey(field);

        return new FieldData(field, dependency);
    }

    private boolean methodNeedsInjection(final Method method) {
        return !reflectionUtils.isStatic(method) && method.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private MethodData createMethodData(final Method method) {
        final List<BeanKey> dependencies = beanHelper.findParameterKeys(method);

        return new MethodData(method, dependencies);
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
        final List<BeanKey> dependencies = beanHelper.findParameterKeys(constructor);

        return new ConstructorData(constructor, dependencies);
    }
}
