
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
     * Creates and initializes this injector.
     *
     * @param basePackage The package to start scanning for beans. All sub-packages will also be scanned.
     */
    public DefaultInjector(final String basePackage) {
        final ClassLocator classLocator = new ClassPathScanner();
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(basePackage, classLocator);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator);
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
    public <T> T getBean(final Class<T> beanClass, final String qualifier) {
        return beanLoader.getBean(beanClass, qualifier);
    }
}
