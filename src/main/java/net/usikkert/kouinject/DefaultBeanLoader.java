
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.Dependency;
import net.usikkert.kouinject.beandata.FieldData;
import net.usikkert.kouinject.beandata.MethodData;
import net.usikkert.kouinject.util.Validate;

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
 *   beanLoader.loadBeans();
 * </pre>
 *
 * @author Christian Ihle
 */
public class DefaultBeanLoader implements BeanLoader {

    private static final Logger LOG = Logger.getLogger(DefaultBeanLoader.class.getName());

    private final Map<Dependency, Object> beanMap;

    private final Collection<Class<?>> beansInCreation;

    private final BeanDataHandler beanDataHandler;

    private final BeanLocator beanLocator;

    /**
     * Constructs a new instance of this {@link BeanLoader} with the specified {@link BeanDataHandler}
     * and {@link BeanLocator}.
     *
     * @param beanDataHandler The handler to use for finding the meta-data required to instantiate beans.
     * @param beanLocator The locator to use for getting the beans to load.
     */
    public DefaultBeanLoader(final BeanDataHandler beanDataHandler, final BeanLocator beanLocator) {
        Validate.notNull(beanDataHandler, "Bean-data handler can not be null");
        Validate.notNull(beanLocator, "Bean locator can not be null");

        this.beanDataHandler = beanDataHandler;
        this.beanLocator = beanLocator;
        this.beanMap = Collections.synchronizedMap(new HashMap<Dependency, Object>());
        this.beansInCreation = Collections.synchronizedCollection(new ArrayList<Class<?>>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadBeans() {
        final Set<Dependency> detectedBeans = beanLocator.findBeans();
        LOG.fine("Beans found: " + detectedBeans.size());

        final long start = System.currentTimeMillis();

        final Map<Dependency, BeanData> beanDataMap = getBeanDataMap(detectedBeans);
        createBeans(beanDataMap);

        final long stop = System.currentTimeMillis();

        LOG.fine("All beans created in: " + (stop - start) + " ms");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void autowire(final Object objectToAutowire) {
        Validate.notNull(objectToAutowire, "Object to autowire can not be null");

        final BeanData beanData = beanDataHandler.getBeanData(objectToAutowire.getClass(), true);
        final List<Dependency> missingDependencies = findMissingDependencies(beanData);

        if (allDependenciesAreMet(missingDependencies)) {
            autowireBean(beanData, objectToAutowire);
        }

        else {
            throw new IllegalArgumentException(
                    "Could not autowire object, missing dependencies: " + missingDependencies);
        }
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
        final Dependency bean = new Dependency(beanClass, qualifier);

        return (T) findBean(bean, true);
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
        final Dependency bean = new Dependency(beanClass, qualifier);

        if (beanAlreadyExists(bean)) {
            throw new IllegalArgumentException("Cannot add already existing bean: " + beanClass);
        }

        synchronized (beanMap) {
            beanMap.put(bean, beanToAdd);
        }

        LOG.fine("Bean added: " + beanClass.getName());
    }

    private Map<Dependency, BeanData> getBeanDataMap(final Set<Dependency> detectedBeans) {
        final Map<Dependency, BeanData> beanDataMap = new HashMap<Dependency, BeanData>();

        for (final Dependency bean : detectedBeans) {
            final BeanData beanData = beanDataHandler.getBeanData(bean.getBeanClass(), false);
            beanDataMap.put(bean, beanData);
        }

        return beanDataMap;
    }

    private void createBeans(final Map<Dependency, BeanData> beanDataMap) {
        final Iterator<Dependency> beanIterator = beanDataMap.keySet().iterator();

        while (beanIterator.hasNext()) {
            final Dependency bean = beanIterator.next();
            createBean(bean, beanDataMap);
        }
    }

    private void createBean(final Dependency dependency, final Map<Dependency, BeanData> beanDataMap) {
        LOG.finer("Checking bean before creation: " + dependency);

        if (dependency.isProvider()) {
            LOG.finer("Bean is provider - skipping: " + dependency);
            return;
        }

        if (beanAlreadyExists(dependency)) {
            LOG.finer("Bean already added - skipping: " + dependency);
            return;
        }

        abortIfBeanCurrentlyInCreation(dependency.getBeanClass());
        addBeanInCreation(dependency.getBeanClass());

        final BeanData beanData = findBeanData(dependency, beanDataMap);
        final List<Dependency> missingDependencies = findMissingDependencies(beanData);

        for (final Dependency missingDependency : missingDependencies) {
            LOG.finer("Checking bean " + dependency + " for missing dependency: " + missingDependency);
            createBean(missingDependency, beanDataMap);
        }

        final Object instance = instantiateBean(beanData);
        addBean(instance, dependency.getQualifier());

        removeBeanInCreation(dependency.getBeanClass());
    }

    private void removeBeanInCreation(final Class<?> beanClass) {
        synchronized (beansInCreation) {
            beansInCreation.remove(beanClass);
        }
    }

    private void addBeanInCreation(final Class<?> beanClass) {
        synchronized (beansInCreation) {
            beansInCreation.add(beanClass);
        }
    }

    private void abortIfBeanCurrentlyInCreation(final Class<?> beanClass) {
        synchronized (beansInCreation) {
            final boolean beanCurrentlyInCreation = beansInCreation.contains(beanClass);

            if (beanCurrentlyInCreation) {
                throw new IllegalStateException("Circular dependency - bean already in creation: " + beanClass);
            }
        }
    }

    private boolean beanAlreadyExists(final Dependency bean) {
        final Object existingBean = findBean(bean, false);
        return existingBean != null;
    }

    private BeanData findBeanData(final Dependency beanNeeded, final Map<Dependency, BeanData> beanDataMap) {
        final Iterator<Dependency> beanIterator = beanDataMap.keySet().iterator();
        final Dependency matchingBean = getMatchingBean(beanNeeded, beanIterator, true);

        return beanDataMap.get(matchingBean);
    }

    private Object instantiateBean(final BeanData beanData) {
        final Object instance = instantiateConstructor(beanData);
        autowireBean(beanData, instance);

        return instance;
    }

    private Object instantiateConstructor(final BeanData beanData) {
        final ConstructorData constructor = beanData.getConstructor();
        LOG.finer("Invoking constructor: " + constructor);

        final List<Dependency> dependencies = constructor.getDependencies();
        final Object[] beansForConstructor = new Object[dependencies.size()];

        for (int i = 0; i < dependencies.size(); i++) {
            final Dependency dependency = dependencies.get(i);
            final Object bean = findBeanOrCreateProvider(dependency);
            beansForConstructor[i] = bean;
        }

        return constructor.createInstance(beansForConstructor);
    }

    private void autowireBean(final BeanData beanData, final Object instance) {
        autowireField(beanData, instance);
        autowireMethod(beanData, instance);
    }

    private void autowireField(final BeanData beanData, final Object objectToAutowire) {
        final List<FieldData> fields = beanData.getFields();

        for (final FieldData field : fields) {
            LOG.finer("Autowiring field: " + field);

            final Dependency dependency = field.getDependency();
            final Object bean = findBeanOrCreateProvider(dependency);

            field.setFieldValue(objectToAutowire, bean);
        }
    }

    private void autowireMethod(final BeanData beanData, final Object objectToAutowire) {
        final List<MethodData> methods = beanData.getMethods();

        for (final MethodData method : methods) {
            LOG.finer("Autowiring method: " + method);

            final List<Dependency> dependencies = method.getDependencies();
            final Object[] beansForMethod = new Object[dependencies.size()];

            for (int i = 0; i < dependencies.size(); i++) {
                final Dependency dependency = dependencies.get(i);
                final Object bean = findBeanOrCreateProvider(dependency);
                beansForMethod[i] = bean;
            }

            method.invokeMethod(objectToAutowire, beansForMethod);
        }
    }

    @SuppressWarnings("unchecked")
    private Object findBeanOrCreateProvider(final Dependency dependency) {
        if (dependency.isProvider()) {
            return new Provider() {
                @Override
                public Object get() {
                    return getBean(dependency.getBeanClass(), dependency.getQualifier());
                }
            };
        }

        return findBean(dependency, true);
    }

    private Object findBean(final Dependency beanNeeded, final boolean throwEx) {
        synchronized (beanMap) {
            final Iterator<Dependency> beanIterator = beanMap.keySet().iterator();
            final Dependency matchingBean = getMatchingBean(beanNeeded, beanIterator, throwEx);
            return beanMap.get(matchingBean);
        }
    }

    private Dependency getMatchingBean(final Dependency beanNeeded, final Iterator<Dependency> beanIterator, final boolean throwEx) {
        final List<Dependency> matches = new ArrayList<Dependency>();

        while (beanIterator.hasNext()) {
            final Dependency bean = beanIterator.next();

            if (beanNeeded.canInject(bean)) {
                matches.add(bean);
            }
        }

        if (matches.size() == 0) {
            if (throwEx) {
                throw new IllegalArgumentException("No matching bean found for " + beanNeeded);
            }

            else {
                return null;
            }
        }

        else if (matches.size() > 1) {
            throw new IllegalStateException("Too many matching beans found for " + beanNeeded + " " + matches);
        }

        return matches.get(0);
    }

    private boolean allDependenciesAreMet(final List<Dependency> missingDependencies) {
        return missingDependencies.size() == 0;
    }

    private List<Dependency> findMissingDependencies(final BeanData beanData) {
        final List<Dependency> missingDeps = new ArrayList<Dependency>();

        for (final Dependency dependency : beanData.getDependencies()) {
            final Object bean = findBean(dependency, false);

            if (bean == null) {
                missingDeps.add(dependency);
            }
        }

        return missingDeps;
    }
}
