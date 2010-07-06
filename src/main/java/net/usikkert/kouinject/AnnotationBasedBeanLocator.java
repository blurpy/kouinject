
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
import java.util.HashSet;
import java.util.Set;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link BeanLocator} using the {@link Component} annotation to detect beans,
 * and the {@link Qualifier} annotation to get the qualifier.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanLocator implements BeanLocator {

    private static final Class<Component> COMPONENT_ANNOTATION = Component.class;

    private final ClassLocator classLocator;
    private final String basePackage;
    private final AnnotationBasedQualifierHandler qualifierHandler;

    /**
     * Constructs a new instance of this {@link BeanLocator} using a {@link ClassLocator} for
     * autodetecting the beans in the specified base package.
     *
     * @param basePackage The package to start scanning for beans. All sub-packages will also be scanned.
     * @param classLocator The instance of {@link ClassLocator} to use for finding the classes
     * that are bean candidates.
     */
    public AnnotationBasedBeanLocator(final String basePackage, final ClassLocator classLocator) {
        Validate.notEmpty(basePackage, "Base package can not be empty");
        Validate.notNull(classLocator, "Class locator can not be null");

        this.basePackage = basePackage;
        this.classLocator = classLocator;
        this.qualifierHandler = new AnnotationBasedQualifierHandler();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BeanKey> findBeans() {
        final Set<Class<?>> allClasses = classLocator.findClasses(basePackage);
        final Set<BeanKey> detectedBeans = new HashSet<BeanKey>();

        for (final Class<?> clazz : allClasses) {
            if (classIsBean(clazz)) {
                final String qualifier = getQualifier(clazz);
                final BeanKey bean = new BeanKey(clazz, qualifier);

                detectedBeans.add(bean);
            }
        }

        return detectedBeans;
    }

    private boolean classIsBean(final Class<?> clazz) {
        return clazz.isAnnotationPresent(COMPONENT_ANNOTATION);
    }

    private String getQualifier(final Class<?> clazz) {
        final Annotation[] annotations = clazz.getAnnotations();
        final String qualifier = qualifierHandler.getQualifier(clazz, annotations);

        return qualifier;
    }
}
