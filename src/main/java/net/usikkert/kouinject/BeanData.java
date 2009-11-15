
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
    private final boolean skipConstructor;

    /**
     * Constructs a new BeanData instance for describing the defined class.
     *
     * @param beanClass The class this BeanData will describe.
     * @param skipConstructor If mapping dependencies for the constructor should be skipped.
     */
    public BeanData(final Class<?> beanClass, final boolean skipConstructor) {
        this.beanClass = beanClass;
        this.skipConstructor = skipConstructor;
        dependencies = new ArrayList<Class<?>>();
    }

    /**
     * The class this BeanData describes the meta-data of.
     *
     * @return The class this BeanData describes.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * Gets the constructor to invoke to create a new instance of the class
     * defined in {@link #getBeanClass()}.
     *
     * @return The constructor to invoke.
     */
    public Constructor<?> getConstructor() {
        return constructor;
    }

    /**
     * Sets the constructor to invoke to create a new instance of the class
     * defined in {@link #getBeanClass()}.
     *
     * @param constructor The constructor to invoke.
     */
    public void setConstructor(final Constructor<?> constructor) {
        this.constructor = constructor;
    }

    /**
     * Gets the fields in {@link #getBeanClass()} marked for dependency injection.
     *
     * @return The fields marked for dependency injection.
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Sets the fields in {@link #getBeanClass()} that needs dependency injection.
     *
     * @param fields The fields that needs dependency injection.
     */
    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    /**
     * Gets the methods in {@link #getBeanClass()} marked for dependency injection.
     *
     * @return The methods marked for dependency injection.
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * Sets the methods in {@link #getBeanClass()} that needs dependency injection.
     *
     * @param methods The methods that needs dependency injection.
     */
    public void setMethods(final List<Method> methods) {
        this.methods = methods;
    }

    /**
     * Finds required class dependencies in the constructor, in fields and in methods.
     *
     * <p>This method cannot be run before:</p>
     *
     * <ul>
     *   <li>{@link #setConstructor(Constructor)} (optional)</li>
     *   <li>{@link #setFields(List)}</li>
     *   <li>{@link #setMethods(List)}</li>
     * </ul>
     *
     * <p>The result will be available in {@link #getDependencies()}.</p>
     */
    public void mapDependencies() {
        if (!skipConstructor) {
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

    /**
     * All required class dependencies will be in this list after {@link #mapDependencies()} has been run.
     *
     * @return All class dependencies for {@link #getBeanClass()}.
     */
    public List<Class<?>> getDependencies() {
        return dependencies;
    }
}
