
/***************************************************************************
 *   Copyright 2009-2012 by Christian Ihle                                 *
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.usikkert.kouinject.factory.AnnotationBasedFactoryPointHandler;
import net.usikkert.kouinject.factory.FactoryPointHandler;
import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.profile.AnnotationBasedProfileHandler;
import net.usikkert.kouinject.profile.InputBasedProfileLocator;
import net.usikkert.kouinject.profile.ProfileHandler;
import net.usikkert.kouinject.profile.ProfileLocator;

import org.apache.commons.lang.Validate;

/**
 * An {@link Injector} using classpath scanning to detect beans, and annotations for
 * setup of beans and the dependency injections.
 *
 * <p>This is the preferred way to use KouInject.</p>
 *
 * @author Christian Ihle
 */
public class DefaultInjector implements Injector {

    private final BeanLoader beanLoader;

    /**
     * Creates and initializes this injector. Does not support profiles.
     *
     * @param basePackages A set of packages to start scanning for beans. All sub-packages will also be scanned.
     */
    public DefaultInjector(final String... basePackages) {
        this(Collections.<String>emptyList(), basePackages);
    }

    /**
     * Creates and initializes this injector, with support for profiles.
     *
     * @param activeProfiles A list of the profiles that will be used to find the active beans.
     * @param basePackages A set of packages to start scanning for beans. All sub-packages will also be scanned.
     */
    public DefaultInjector(final List<String> activeProfiles, final String... basePackages) {
        Validate.notNull(activeProfiles, "Active profiles can not be null");
        Validate.notNull(basePackages, "Base packages can not be null");

        final ClassLocator classLocator = new ClassPathScanner();
        final ProfileLocator profileLocator = new InputBasedProfileLocator(activeProfiles);
        final ProfileHandler profileHandler = new AnnotationBasedProfileHandler(profileLocator);
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(classLocator, profileHandler, basePackages);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final FactoryPointHandler factoryPointHandler = new AnnotationBasedFactoryPointHandler();

        beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator, factoryPointHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getBean(final Class<T> beanClass) {
        return beanLoader.getBean(beanClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getBean(final TypeLiteral<T> beanType) {
        return beanLoader.getBean(beanType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getBean(final Class<T> beanClass, final String qualifier) {
        return beanLoader.getBean(beanClass, qualifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getBean(final TypeLiteral<T> beanType, final String qualifier) {
        return beanLoader.getBean(beanType, qualifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> getBeans(final Class<T> beanClass) {
        return beanLoader.getBeans(beanClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> getBeans(final TypeLiteral<T> beanType) {
        return beanLoader.getBeans(beanType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> getBeans(final Class<T> beanClass, final String qualifier) {
        return beanLoader.getBeans(beanClass, qualifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> getBeans(final TypeLiteral<T> beanType, final String qualifier) {
        return beanLoader.getBeans(beanType, qualifier);
    }
}
