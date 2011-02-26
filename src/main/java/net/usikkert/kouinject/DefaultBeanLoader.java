
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

package net.usikkert.kouinject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.InjectionPoint;
import net.usikkert.kouinject.factory.FactoryContext;
import net.usikkert.kouinject.factory.FactoryContextImpl;
import net.usikkert.kouinject.factory.FactoryPoint;
import net.usikkert.kouinject.factory.FactoryPointHandler;
import net.usikkert.kouinject.factory.FactoryPointMap;

import org.apache.commons.lang.Validate;

/**
 * Default implementation of the {@link BeanLoader}.
 *
 * <p>It's recommended to use the {@link DefaultInjector} instead of using this class directly,
 * unless you have special requirements.</p>
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
    private final FactoryPointMap factoryPointMap;
    private final FactoryPointHandler factoryPointHandler;

    /**
     * Constructs a new instance of this {@link BeanLoader} with the specified {@link BeanDataHandler},
     * {@link BeanLocator} and {@link FactoryPointHandler}. Detected beans will be scanned and prepared,
     * but not instantiated.
     *
     * @param beanDataHandler The handler to use for finding the meta-data required to instantiate beans.
     * @param beanLocator The locator to use for getting the beans to load.
     * @param factoryPointHandler The handler to use for finding the meta-data required for using factories.
     */
    public DefaultBeanLoader(final BeanDataHandler beanDataHandler, final BeanLocator beanLocator,
                             final FactoryPointHandler factoryPointHandler) {
        Validate.notNull(beanDataHandler, "Bean-data handler can not be null");
        Validate.notNull(beanLocator, "Bean locator can not be null");
        Validate.notNull(factoryPointHandler, "Factory point handler can not be null");

        this.beanDataHandler = beanDataHandler;
        this.beanLocator = beanLocator;
        this.factoryPointHandler = factoryPointHandler;
        this.singletonMap = new SingletonMap();
        this.beanDataMap = new BeanDataMap();
        this.beansInCreation = new BeansInCreation();
        this.factoryPointMap = new FactoryPointMap();

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
            LOG.finest("Loading bean-data for: " + bean);
            final BeanData beanData = beanDataHandler.getBeanData(bean, false);
            beanDataMap.addBeanData(beanData);
            final List<FactoryPoint> factoryPoints = factoryPointHandler.getFactoryPoints(bean);
            factoryPointMap.addFactoryPoints(factoryPoints);
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
    public <T> T getBean(final Class<T> beanClass, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final BeanKey dependency = new BeanKey(beanClass, qualifier);
        LOG.finer("Requesting: " + dependency);

        // TODO fail if in both
        // TODO better way to handle FactoryContext?
        if (!beanDataMap.containsBeanData(dependency) && !factoryPointMap.containsFactoryPoint(dependency)
                && !beanClass.equals(FactoryContext.class)) {
            throw new IllegalArgumentException("No registered bean-data for: " + dependency);
        }

        return (T) findOrCreateBean(dependency);
    }

    @SuppressWarnings("unchecked")
    private <T> T getBean(final BeanKey beanKey) {
        return (T) getBean(beanKey.getBeanClass(), beanKey.getQualifier());
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
    public <T> Collection<T> getBeans(final Class<T> beanClass, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final BeanKey dependency = new BeanKey(beanClass, qualifier);
        LOG.finer("Requesting: " + dependency);
        final Collection<BeanKey> beanKeys = new ArrayList<BeanKey>();
        beanKeys.addAll(beanDataMap.findBeanKeys(dependency));
        beanKeys.addAll(factoryPointMap.findFactoryPointKeys(dependency));

        if (beanKeys.isEmpty()) {
            throw new IllegalArgumentException("No registered bean-data for: " + dependency);
        }

        final Collection<T> beans = new ArrayList<T>();

        for (final BeanKey beanKey : beanKeys) {
            beans.add((T) findOrCreateBean(beanKey));
        }

        return beans;
    }

    @SuppressWarnings("unchecked")
    private <T> Collection<T> getBeans(final BeanKey beanKey) {
        return (Collection<T>) getBeans(beanKey.getBeanClass(), beanKey.getQualifier());
    }

    private Object findOrCreateBean(final BeanKey dependency) {
        final Object bean = singletonMap.getSingleton(dependency, false);

        if (bean != null) {
            LOG.finer("Mapping " + dependency + " to existing singleton " + bean.getClass());
            return bean;
        }

        return createBean(dependency);
    }

    /**
     * Adds a new singleton to the container, with no qualifier.
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
     * Adds a new singleton to the container, with the given qualifier.
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
        LOG.finer("Adding singleton: " + bean);

        singletonMap.addSingleton(bean, beanToAdd);

        if (!beanDataMap.containsBeanData(bean)) {
            beanDataMap.addBeanData(beanDataHandler.getBeanData(bean, false));
        }

        LOG.fine("Singleton added: " + bean);
    }

    private Object createBean(final BeanKey dependency) {
        LOG.finer("Checking bean before creation: " + dependency);

        if (!dependency.isBeanForCreation()) {
            throw new UnsupportedOperationException("This bean can't be instantiated: " + dependency);
        }

        if (singletonMap.containsSingleton(dependency)) {
            throw new UnsupportedOperationException("This singleton has already been created: " + dependency);
        }

        beansInCreation.addBean(dependency);

        final CreatedBean createdBean = createBeanUsingFactoryOrInjector(dependency);

        if (createdBean.isSingleton()) {
            addBean(createdBean.getInstance(), createdBean.getQualifier());
        }

        beansInCreation.removeBean(dependency);

        return createdBean.getInstance();
    }

    private CreatedBean createBeanUsingFactoryOrInjector(final BeanKey dependency) {
        if (factoryPointMap.containsFactoryPoint(dependency)) {
            return createBeanUsingFactory(dependency);
        } else {
            return createBeanUsingInjector(dependency);
        }
    }

    private CreatedBean createBeanUsingInjector(final BeanKey dependency) {
        final BeanData beanData = beanDataMap.getBeanData(dependency);
        final BeanKey beanKeyForBeanData = beanData.getBeanKey();
        LOG.finer("Mapping " + dependency + " to " + beanKeyForBeanData);

        final Object beanInstance = instantiateBean(beanData);

        return new CreatedBean(beanInstance, beanData.isSingleton(), beanKeyForBeanData.getQualifier());
    }

    private CreatedBean createBeanUsingFactory(final BeanKey dependency) {
        final FactoryPoint factoryPoint = factoryPointMap.getFactoryPoint(dependency);
        final BeanKey returnType = factoryPoint.getReturnType();
        LOG.finer("Mapping " + dependency + " to " + returnType);

        final Object factoryInstance = getBean(factoryPoint.getFactoryKey());
        final Object beanInstance = invokeFactoryPoint(factoryPoint, factoryInstance, dependency);

        return new CreatedBean(beanInstance, factoryPoint.isSingleton(), returnType.getQualifier());
    }

    private Object instantiateBean(final BeanData beanData) {
        final Object instance = instantiateConstructor(beanData);
        autowireBean(beanData, instance);
        LOG.fine("Created bean: " + instance.getClass());

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

        final Object instance = constructor.createInstance(beansForConstructor);
        LOG.finer("Constructor invoked: " + constructor);

        return instance;
    }

    private Object invokeFactoryPoint(final FactoryPoint<?> factoryPoint, final Object factoryInstance,
                                      final BeanKey dependency) {
        LOG.finer("Invoking factory point: " + factoryPoint);

        final List<BeanKey> parameters = factoryPoint.getParameters();
        final Object[] beansForFactoryPoint = new Object[parameters.size()];

        for (int i = 0; i < parameters.size(); i++) {
            final BeanKey parameter = parameters.get(i);
            final Object bean = getBeanOrFactoryContext(parameter, dependency);
            beansForFactoryPoint[i] = bean;
        }

        final Object instance = factoryPoint.create(factoryInstance, beansForFactoryPoint);
        LOG.finer("Factory point invoked: " + factoryPoint);

        return instance;
    }

    private Object getBeanOrFactoryContext(final BeanKey parameter, final BeanKey dependency) {
        if (parameter.getBeanClass().equals(FactoryContext.class)) {
            return new FactoryContextImpl(dependency.getQualifier());
        }

        else {
            return findBeanOrCreateProvider(parameter);
        }
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

            LOG.finer("Injection point autowired: " + injectionPoint);

            injectionPoint.inject(instance, beansForInjectionPoint);
        }
    }

    private Object findBeanOrCreateProvider(final BeanKey dependency) {
        if (dependency.isProvider()) {
            return new Provider<Object>() {
                @Override
                public Object get() {
                    return getBean(dependency);
                }
            };
        }

        if (dependency.isCollection()) {
            return getBeans(dependency);
        }

        if (dependency.isCollectionProvider()) {
            return new CollectionProvider() {
                @Override
                public Collection get() {
                    return getBeans(dependency);
                }
            };
        }

        return getBean(dependency);
    }

    private class CreatedBean {

        private final Object instance;
        private final boolean singleton;
        private final String qualifier;

        public CreatedBean(final Object instance, final boolean singleton, final String qualifier) {
            this.instance = instance;
            this.singleton = singleton;
            this.qualifier = qualifier;
        }

        public Object getInstance() {
            return instance;
        }

        public boolean isSingleton() {
            return singleton;
        }

        public String getQualifier() {
            return qualifier;
        }
    }
}
