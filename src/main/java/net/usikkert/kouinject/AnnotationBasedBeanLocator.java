
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

import java.util.HashSet;
import java.util.Set;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.beandata.Bean;
import net.usikkert.kouinject.util.Validate;

/**
 * Implementation of {@link BeanLocator} using the {@link Component} annotation to detect beans.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanLocator implements BeanLocator {

    private static final Class<Component> COMPONENT_ANNOTATION = Component.class;

    private final ClassLocator classLocator;

    private final String basePackage;

    /**
     * Constructs a new instance of this {@link BeanLocator} using a {@link ClassLocator} for
     * autodetecting the beans in the specified base package.
     *
     * @param basePackage The package to start scanning for beans. All sub-packages will also be scanned.
     * @param classLocator The instance of {@link ClassLocator} to use for finding the classes
     * that are bean candidates.
     */
    public AnnotationBasedBeanLocator(final String basePackage, final ClassLocator classLocator) {
        Validate.notNull(basePackage, "Base package can not be null");
        Validate.notNull(classLocator, "Class locator can not be null");

        this.basePackage = basePackage;
        this.classLocator = classLocator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Bean> findBeans() {
        final Set<Class<?>> allClasses = classLocator.findClasses(basePackage);
        final Set<Bean> detectedBeans = new HashSet<Bean>();

        for (final Class<?> clazz : allClasses) {
            if (classIsBean(clazz)) {
                final Bean bean = new Bean(clazz, null);
                detectedBeans.add(bean);
            }
        }

        return detectedBeans;
    }

    private boolean classIsBean(final Class<?> clazz) {
        return clazz.isAnnotationPresent(COMPONENT_ANNOTATION);
    }
}
