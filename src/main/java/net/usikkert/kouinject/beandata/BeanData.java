
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

package net.usikkert.kouinject.beandata;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * This class describes the bean meta-data needed to instantiate a new bean to a consistent state.
 *
 * @author Christian Ihle
 */
public class BeanData {

    private final Class<?> beanClass;
    private final ConstructorData constructor;
    private final List<InjectionPoint> injectionPoints;
    private final List<BeanKey> dependencies;
    private final boolean isSingleton;

    /**
     * Constructs a new BeanData instance for describing the defined class.
     *
     * @param beanClass The class this BeanData will describe.
     * @param constructor Optional meta-data for the constructor to invoke on beanClass.
     *                     If this is null then the bean can not be instantiated.
     * @param injectionPoints Meta-data for the injection points in beanClass that requires dependency injection.
     * @param isSingleton If this bean is a singleton.
     */
    public BeanData(final Class<?> beanClass, final ConstructorData constructor,
            final List<InjectionPoint> injectionPoints, final boolean isSingleton) {
        Validate.notNull(beanClass, "Bean class can not be null");
        Validate.notNull(injectionPoints, "Injection points can not be null");

        this.beanClass = beanClass;
        this.constructor = constructor;
        this.injectionPoints = injectionPoints;
        this.isSingleton = isSingleton;
        this.dependencies = new ArrayList<BeanKey>();

        mapDependencies();
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
        final List<FieldData> fields = new ArrayList<FieldData>();

        for (final InjectionPoint injectionPoint : injectionPoints) {
            if (injectionPoint instanceof FieldData) {
                fields.add((FieldData) injectionPoint);
            }
        }

        return fields;
    }

    /**
     * Gets the meta-data describing the methods in {@link #getBeanClass()} requiring dependency injection.
     *
     * @return The meta-data of the methods.
     */
    public List<MethodData> getMethods() {
        final List<MethodData> methods = new ArrayList<MethodData>();

        for (final InjectionPoint injectionPoint : injectionPoints) {
            if (injectionPoint instanceof MethodData) {
                methods.add((MethodData) injectionPoint);
            }
        }

        return methods;
    }

    /**
     * Gets the meta-data describing the injection points in {@link #getBeanClass()} requiring dependency injection.
     *
     * @return The meta-data of the injection points.
     */
    public List<InjectionPoint> getInjectionPoints() {
        return injectionPoints;
    }

    /**
     * Gets a list of all the required dependencies for dependency injection in the constructor,
     * fields and methods of {@link #getBeanClass()}.
     *
     * @return All required dependencies.
     */
    public List<BeanKey> getDependencies() {
        return dependencies;
    }

    /**
     * If this bean is a singleton or not.
     *
     * @return If this bean is a singleton.
     */
    public boolean isSingleton() {
        return isSingleton;
    }

    private void mapDependencies() {
        if (!skipConstructor()) {
            mapConstructorDependencies();
        }

        mapInjectionPointDependencies();
    }

    private void mapConstructorDependencies() {
        dependencies.addAll(constructor.getDependencies());
    }

    private void mapInjectionPointDependencies() {
        for (final InjectionPoint injectionPoint : injectionPoints) {
            dependencies.addAll(injectionPoint.getDependencies());
        }
    }

    private boolean skipConstructor() {
        return constructor == null;
    }

    @Override
    public String toString() {
        return "[singleton=" + isSingleton + "] " + beanClass;
    }
}
