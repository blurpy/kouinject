
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

package net.usikkert.kouinject.beandata;

import java.util.ArrayList;
import java.util.List;

import net.usikkert.kouinject.util.Validate;

/**
 * This class describes the bean meta-data needed to instantiate a new bean to a consistent state.
 *
 * @author Christian Ihle
 */
public class BeanData {

    private final Class<?> beanClass;
    private final ConstructorData constructor;
    private final List<FieldData> fields;
    private final List<MethodData> methods;
    private final List<Dependency> dependencies;

    /**
     * Constructs a new BeanData instance for describing the defined class.
     *
     * @param beanClass The class this BeanData will describe.
     * @param constructor Optional meta-data for the constructor to invoke on beanClass.
     *                    If this is null then the bean can not be instantiated.
     * @param fields Meta-data for the fields in beanClass that requires dependency injection.
     * @param methods Meta-data for the methods in beanClass that requires dependency injection.
     */
    public BeanData(final Class<?> beanClass, final ConstructorData constructor,
            final List<FieldData> fields, final List<MethodData> methods) {
        Validate.notNull(beanClass, "Bean class can not be null");
        Validate.notNull(fields, "Fields can not be null");
        Validate.notNull(methods, "Methods can not be null");

        this.beanClass = beanClass;
        this.constructor = constructor;
        this.fields = fields;
        this.methods = methods;
        this.dependencies = new ArrayList<Dependency>();

        mapDependencies();
    }

    /**
     * Constructs a new BeanData instance for describing the defined class. This constructor
     * does not take constructor meta-data, so the bean can not be instantiated.
     *
     * @param beanClass The class this BeanData will describe.
     * @param fields Meta-data for the fields in beanClass that requires dependency injection.
     * @param methods Meta-data for the methods in beanClass that requires dependency injection.
     */
    public BeanData(final Class<?> beanClass, final List<FieldData> fields, final List<MethodData> methods) {
        this(beanClass, null, fields, methods);
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
     * Gets the meta-data describing the constructor to invoke to create a new instance of the class
     * defined in {@link #getBeanClass()}.
     *
     * @return The constructor meta-data.
     */
    public ConstructorData getConstructor() {
        return constructor;
    }

    /**
     * Gets the meta-data describing the fields in {@link #getBeanClass()} requiring dependency injection.
     *
     * @return The meta-data of the fields.
     */
    public List<FieldData> getFields() {
        return fields;
    }

    /**
     * Gets the meta-data describing the methods in {@link #getBeanClass()} requiring dependency injection.
     *
     * @return The meta-data of the methods.
     */
    public List<MethodData> getMethods() {
        return methods;
    }

    /**
     * Gets a list of all the required dependencies for dependency injection in the constructor,
     * fields and methods of {@link #getBeanClass()}.
     *
     * @return All required dependencies.
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    private void mapDependencies() {
        if (!skipConstructor()) {
            mapConstructorDependencies();
        }

        mapFieldDependencies();
        mapMethodDependencies();
    }

    private void mapConstructorDependencies() {
        dependencies.addAll(constructor.getDependencies());
    }

    private void mapFieldDependencies() {
        for (final FieldData field : fields) {
            dependencies.add(field.getDependency());
        }
    }

    private void mapMethodDependencies() {
        for (final MethodData method : methods) {
            dependencies.addAll(method.getDependencies());
        }
    }

    private boolean skipConstructor() {
        return constructor == null;
    }
}
