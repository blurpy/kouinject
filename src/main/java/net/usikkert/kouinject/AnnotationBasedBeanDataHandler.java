
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.usikkert.kouinject.util.Validate;

/**
 * Implementation of {@link BeanDataHandler} that uses annotations to extract meta-data from beans.
 *
 * <p>Scans beans for the {@link Inject} annotation to detect constructor, fields and methods
 * for dependency injection.</p>
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanDataHandler implements BeanDataHandler {

    private static final Class<Inject> INJECTION_ANNOTATION = Inject.class;

    /**
     * {@inheritDoc}
     */
    @Override
    public BeanData getBeanData(final Class<?> beanClass, final boolean skipConstructor) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final BeanData beanData = new BeanData(beanClass, skipConstructor);

        if (!skipConstructor) {
            final Constructor<?> constructor = findConstructor(beanClass);
            beanData.setConstructor(constructor);
        }

        final List<Field> fields = findFields(beanClass);
        beanData.setFields(fields);

        final List<Method> methods = findMethods(beanClass);
        beanData.setMethods(methods);

        beanData.mapDependencies();

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
}
