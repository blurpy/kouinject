
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

/**
 * This class describes the bean meta-data needed to instantiate a new bean to a consistent state.
 *
 * @author Christian Ihle
 */
public class BeanData {

    private final Class<?> beanClass;

    private Constructor<?> constructor;

    private List<Field> fields;

    private List<Method> methods;

    private final List<Class<?>> dependencies;

    public BeanData(final Class<?> beanClass) {
        this.beanClass = beanClass;
        dependencies = new ArrayList<Class<?>>();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(final Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(final List<Method> methods) {
        this.methods = methods;
    }

    public void mapDependencies() {
        if (constructor != null) {
            mapConstructorDependencies();
        }

        mapFieldDependencies();
        mapMethodDependencies();
    }

    private void mapConstructorDependencies() {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();

        for (final Class<?> class1 : parameterTypes) {
            dependencies.add(class1);
        }
    }

    private void mapFieldDependencies() {
        for (final Field field : fields) {
            dependencies.add(field.getType());
        }
    }

    private void mapMethodDependencies() {
        for (final Method method : methods) {
            final Class<?>[] parameterTypes = method.getParameterTypes();

            for (final Class<?> class1 : parameterTypes) {
                dependencies.add(class1);
            }
        }
    }

    public List<Class<?>> getDependencies() {
        return dependencies;
    }
}
