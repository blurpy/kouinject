
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.InjectionPoint;

import org.apache.commons.lang.Validate;

/**
 * Default implementation of the {@link BeanLoader}.
 *
 * <p>Example of how to use:</p>
 *
 * <pre>
 *   ClassLocator classLocator = new ClassPathScanner();
 *   final BeanLocator beanLocator = new AnnotationBasedBeanLocator("basepackage.to.scan", classLocator);
 *   BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
 *   BeanLoader beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator);
 * </pre>
 *
 * @author Christian Ihle
 */
public class DefaultBeanLoader implements BeanLoader {

    private static final Logger LOG = Logger.getLogger(DefaultBeanLoader.class.getName());

    private final SingletonMap singletonMap;
    private final BeanDataMap beanDataMap;
    private final BeansInCreation beansInCreation;
    private final BeanDataHandler beanDataHandler;
    private final BeanLocator beanLocator;

    /**
     * Constructs a new instance of this {@link BeanLoader} with the specified {@link BeanDataHandler}
     * and {@link BeanLocator}. Detected beans will be scanned and prepared, but not instantiated.
     *
     * @param beanDataHandler The handler to use for finding the meta-data required to instantiate beans.
     * @param beanLocator The locator to use for getting the beans to load.
     */
    public DefaultBeanLoader(final BeanDataHandler beanDataHandler, final BeanLocator beanLocator) {
        Validate.notNull(beanDataHandler, "Bean-data handler can not be null");
        Validate.notNull(beanLocator, "Bean locator can not be null");

        this.beanDataHandler = beanDataHandler;
        this.beanLocator = beanLocator;
        this.singletonMap = new SingletonMap();
        this.beanDataMap = new BeanDataMap();
        this.beansInCreation = new BeansInCreation();

        loadBeanData();
    }

    /**
     * Finds all registered beans, and prepares them for being instantiated.
     */
    private void loadBeanData() {
        final Set<BeanKey> detectedBeans = beanLocator.findBeans();
        LOG.fine("Beans found: " + detectedBeans.size());

        final long start = System.currentTimeMillis();

        for (final BeanKey bean : detectedBeans) {
            final BeanData beanData = beanDataHandler.getBeanData(bean.getBeanClass(), false);
            beanDataMap.addBeanData(bean, beanData);
        }

        final long stop = System.currentTimeMillis();

        LOG.fine("All bean-data loaded in: " + (stop - start) + " ms");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getBean(final Class<T> beanClass) {
        return getBean(beanClass, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Object> T getBean(final Class<T> beanClass, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final BeanKey dependency = new BeanKey(beanClass, qualifier);

        if (!beanDataMap.containsBeanData(dependency)) {
            throw new IllegalArgumentException("No registered bean-data for: " + dependency);
        }

        return (T) findOrCreateBean(dependency);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<T> getBeans(final Class<T> beanClass) {
        return getBeans(beanClass, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Object> Collection<T> getBeans(final Class<T> beanClass, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final BeanKey dependency = new BeanKey(beanClass, qualifier);
        final Collection<BeanKey> beanKeys = beanDataMap.findBeanKeys(dependency);

        if (beanKeys.isEmpty()) {
            throw new IllegalArgumentException("No registered bean-data for: " + dependency);
        }

        final Collection<T> beans = new ArrayList<T>();

        for (final BeanKey beanKey : beanKeys) {
            beans.add((T) findOrCreateBean(beanKey));
        }

        return beans;
    }

    private Object findOrCreateBean(final BeanKey dependency) {
        final Object bean = findBean(dependency, false);

        if (bean != null) {
            return bean;
        }

        return createBean(dependency);
    }

    /**
     * Adds a new bean to the container, with no qualifier.
     *
     * <p>The bean must be ready to use, and will be available for dependency injection in other beans.</p>
     *
     * <p>Do <strong>NOT</strong> depend on manually invoking this method from outside
     * the framework to get all the required beans in the container. The beans will
     * be instantiated in random order, and dependencies not instantiated by the container is
     * not guaranteed to be available at the right moment.</p>
     *
     * @param beanToAdd The fully instantiated and ready to use bean to add to the container.
     */
    protected void addBean(final Object beanToAdd) {
        addBean(beanToAdd, null);
    }

    /**
     * Adds a new bean to the container, with the given qualifier.
     *
     * <p>The bean must be ready to use, and will be available for dependency injection in other beans.</p>
     *
     * <p>Do <strong>NOT</strong> depend on manually invoking this method from outside
     * the framework to get all the required beans in the container. The beans will
     * be instantiated in random order, and dependencies not instantiated by the container is
     * not guaranteed to be available at the right moment.</p>
     *
     * @param beanToAdd The fully instantiated and ready to use bean to add to the container.
     * @param qualifier The qualifier for this bean. May be <code>null</code>.
     */
    protected void addBean(final Object beanToAdd, final String qualifier) {
        Validate.notNull(beanToAdd, "Bean can not be null");

        final Class<?> beanClass = beanToAdd.getClass();
        final BeanKey bean = new BeanKey(beanClass, qualifier);

        singletonMap.addSingleton(bean, beanToAdd);

        if (!beanDataMap.containsBeanData(bean)) {
            beanDataMap.addBeanData(bean, beanDataHandler.getBeanData(beanClass, false));
        }

        LOG.fine("Bean added: " + beanClass.getName());
    }

    private Object createBean(final BeanKey dependency) {
        LOG.finer("Checking bean before creation: " + dependency);

        if (dependency.isProvider()) {
            LOG.finer("Bean is provider - skipping: " + dependency);
            return null;
        }

        if (singletonMap.containsSingleton(dependency)) {
            LOG.finer("Bean already added - skipping: " + dependency);
            return null;
        }

        beansInCreation.addBean(dependency);

        final BeanData beanData = findBeanData(dependency);
        final Object instance = instantiateBean(beanData);

        if (beanData.isSingleton()) {
            addBean(instance, dependency.getQualifier());
        }

        beansInCreation.removeBean(dependency);

        return instance;
    }

    private BeanData findBeanData(final BeanKey beanNeeded) {
        return beanDataMap.getBeanData(beanNeeded);
    }

    private Object instantiateBean(final BeanData beanData) {
        final Object instance = instantiateConstructor(beanData);
        autowireBean(beanData, instance);

        return instance;
    }

    private Object instantiateConstructor(final BeanData beanData) {
        final ConstructorData constructor = beanData.getConstructor();
        LOG.finer("Invoking constructor: " + constructor);

        final List<BeanKey> dependencies = constructor.getDependencies();
        final Object[] beansForConstructor = new Object[dependencies.size()];

        for (int i = 0; i < dependencies.size(); i++) {
            final BeanKey dependency = dependencies.get(i);
            final Object bean = findBeanOrCreateProvider(dependency);
            beansForConstructor[i] = bean;
        }

        return constructor.createInstance(beansForConstructor);
    }

    private void autowireBean(final BeanData beanData, final Object instance) {
        final List<InjectionPoint> injectionPoints = beanData.getInjectionPoints();

        for (final InjectionPoint injectionPoint : injectionPoints) {
            LOG.finer("Autowiring injection point: " + injectionPoint);

            final List<BeanKey> dependencies = injectionPoint.getDependencies();
            final Object[] beansForInjectionPoint = new Object[dependencies.size()];

            for (int i = 0; i < dependencies.size(); i++) {
                final BeanKey dependency = dependencies.get(i);
                final Object bean = findBeanOrCreateProvider(dependency);
                beansForInjectionPoint[i] = bean;
            }

            injectionPoint.inject(instance, beansForInjectionPoint);
        }
    }

    private Object findBeanOrCreateProvider(final BeanKey dependency) {
        if (dependency.isProvider()) {
            return new Provider<Object>() {
                @Override
                public Object get() {
                    return getBean(dependency.getBeanClass(), dependency.getQualifier());
                }
            };
        }

        return getBean(dependency.getBeanClass(), dependency.getQualifier());
    }

    private Object findBean(final BeanKey beanNeeded, final boolean throwEx) {
        return singletonMap.getSingleton(beanNeeded, throwEx);
    }
}
