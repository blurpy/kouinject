
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    private final Map<Class<?>, Object> beanMap;

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
        this.beanMap = Collections.synchronizedMap(new HashMap<Class<?>, Object>());
        this.beansInCreation = Collections.synchronizedCollection(new ArrayList<Class<?>>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadBeans() {
        try {
            loadAndAutowireBeans();
        }

        catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        catch (final InstantiationException e) {
            throw new RuntimeException(e);
        }
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
            try {
                autowireBean(beanData, objectToAutowire);
            }

            catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            }
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
    public <T extends Object> T getBean(final Class<T> beanClass) {
        Validate.notNull(beanClass, "Bean class can not be null");

        return findBean(beanClass, true);
    }

    /**
     * Adds a new bean to the container. The bean must be ready to use, and will be available
     * for dependency injection in other beans.
     *
     * <p>Do <strong>NOT</strong> depend on manually invoking this method from outside
     * the framework to get all the required beans in the container. The beans will
     * be instantiated in random order, and dependencies not instantiated by the container is
     * not guaranteed to be available at the right moment.</p>
     *
     * @param beanToAdd The fully instantiated and ready to use bean to add to the container.
     */
    protected void addBean(final Object beanToAdd) {
        Validate.notNull(beanToAdd, "Bean can not be null");

        final Class<?> beanClass = beanToAdd.getClass();

        if (beanAlreadyExists(beanClass)) {
            throw new IllegalArgumentException("Cannot add already existing bean: " + beanClass);
        }

        synchronized (beanMap) {
            beanMap.put(beanClass, beanToAdd);
        }

        LOG.fine("Bean added: " + beanClass.getName());
    }

    private void loadAndAutowireBeans() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Set<Class<?>> detectedBeans = beanLocator.findBeans();
        LOG.fine("Beans found: " + detectedBeans.size());

        final long start = System.currentTimeMillis();

        final Map<Class<?>, BeanData> beanDataMap = getBeanDataMap(detectedBeans);
        createBeans(beanDataMap);

        final long stop = System.currentTimeMillis();

        LOG.fine("All beans created in: " + (stop - start) + " ms");
    }

    private Map<Class<?>, BeanData> getBeanDataMap(final Set<Class<?>> detectedBeans) {
        final Map<Class<?>, BeanData> beanDataMap = new HashMap<Class<?>, BeanData>();

        for (final Class<?> beanClass : detectedBeans) {
            final BeanData beanData = beanDataHandler.getBeanData(beanClass, false);
            beanDataMap.put(beanClass, beanData);
        }

        return beanDataMap;
    }

    private void createBeans(final Map<Class<?>, BeanData> beanDataMap)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Iterator<Class<?>> beanIterator = beanDataMap.keySet().iterator();

        while (beanIterator.hasNext()) {
            final Class<?> beanClass = beanIterator.next();
            createBean(new Dependency(beanClass, false), beanDataMap);
        }
    }

    private void createBean(final Dependency dependency, final Map<Class<?>, BeanData> beanDataMap)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        LOG.finer("Checking bean before creation: " + dependency);

        if (dependency.isProvider()) {
            LOG.finer("Bean is provider - skipping: " + dependency);
            return;
        }

        if (beanAlreadyExists(dependency.getBeanClass())) {
            LOG.finer("Bean already added - skipping: " + dependency);
            return;
        }

        abortIfBeanCurrentlyInCreation(dependency.getBeanClass());
        addBeanInCreation(dependency.getBeanClass());

        final BeanData beanData = findBeanData(dependency.getBeanClass(), beanDataMap);
        final List<Dependency> missingDependencies = findMissingDependencies(beanData);

        for (final Dependency missingDependency : missingDependencies) {
            LOG.finer("Checking bean " + dependency + " for missing dependency: " + missingDependency);
            createBean(missingDependency, beanDataMap);
        }

        final Object instance = instantiateBean(beanData);
        addBean(instance);

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

    private boolean beanAlreadyExists(final Class<?> beanClass) {
        final Object existingBean = findBean(beanClass, false);
        return existingBean != null;
    }

    private BeanData findBeanData(final Class<?> beanNeeded, final Map<Class<?>, BeanData> beanDataMap) {
        final Iterator<Class<?>> beanIterator = beanDataMap.keySet().iterator();
        final Class<?> matchingBean = getMatchingBean(beanNeeded, beanIterator, true);

        return beanDataMap.get(matchingBean);
    }

    private Object instantiateBean(final BeanData beanData)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object instance = instantiateConstructor(beanData);
        autowireBean(beanData, instance);

        return instance;
    }

    private Object instantiateConstructor(final BeanData beanData)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<?> constructor = beanData.getConstructor();
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Type[] genericParameterTypes = constructor.getGenericParameterTypes();
        final Object[] beansForConstructor = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> beanClass = parameterTypes[i];
            final Type genericParameterType = genericParameterTypes[i];
            final Object bean = findBeanOrCreateProvider(beanClass, genericParameterType);
            beansForConstructor[i] = bean;
        }

        LOG.finer("Invoking constructor: " + constructor.toGenericString());

        final boolean originalAccessible = constructor.isAccessible();
        constructor.setAccessible(true);

        try {
            final Object newInstance = constructor.newInstance(beansForConstructor);
            return newInstance;
        }

        finally {
            constructor.setAccessible(originalAccessible);
        }
    }

    private void autowireBean(final BeanData beanData, final Object instance)
            throws IllegalAccessException,  InvocationTargetException {
        autowireField(beanData, instance);
        autowireMethod(beanData, instance);
    }

    private void autowireField(final BeanData beanData, final Object objectToAutowire) throws IllegalAccessException {
        final List<Field> fields = beanData.getFields();

        for (final Field field : fields) {
            LOG.finer("Autowiring field: " + field.toGenericString());

            final Class<?> beanClass = field.getType();
            final Type genericType = field.getGenericType();
            final Object bean = findBeanOrCreateProvider(beanClass, genericType);

            final boolean originalAccessible = field.isAccessible();
            field.setAccessible(true);

            try {
                field.set(objectToAutowire, bean);
            }

            finally {
                field.setAccessible(originalAccessible);
            }
        }
    }

    private void autowireMethod(final BeanData beanData, final Object objectToAutowire)
            throws IllegalAccessException, InvocationTargetException {
        final List<Method> methods = beanData.getMethods();

        for (final Method method : methods) {
            LOG.finer("Autowiring method: " + method.toGenericString());

            final Class<?>[] parameterTypes = method.getParameterTypes();
            final Type[] genericParameterTypes = method.getGenericParameterTypes();
            final Object[] beansForMethod = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                final Class<?> beanClass = parameterTypes[i];
                final Type genericParameterType = genericParameterTypes[i];
                final Object bean = findBeanOrCreateProvider(beanClass, genericParameterType);
                beansForMethod[i] = bean;
            }

            final boolean originalAccessible = method.isAccessible();
            method.setAccessible(true);

            try {
                method.invoke(objectToAutowire, beansForMethod);
            }

            finally {
                method.setAccessible(originalAccessible);
            }
        }
    }

    private Object findBeanOrCreateProvider(final Class<?> beanNeeded, final Type genericParameterType) {
        if (isProvider(beanNeeded)) {
            final Class<?> beanClassFromProvider = getBeanClassFromProvider(genericParameterType);

            return new Provider() {
                @Override
                public Object get() {
                    return getBean(beanClassFromProvider);
                }
            };
        }

        return findBean(beanNeeded, true);
    }

    private boolean isProvider(final Class<?> beanClass) {
        return Provider.class.isAssignableFrom(beanClass);
    }

    private Class<?> getBeanClassFromProvider(final Type genericParameterType) {
        final ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
        final Type[] typeArguments = parameterizedType.getActualTypeArguments();
        final Class<?> beanClassFromProvider = (Class<?>) typeArguments[0];

        return beanClassFromProvider;
    }

    @SuppressWarnings("unchecked")
    private <T extends Object> T findBean(final Class<T> beanNeeded, final boolean throwEx) {
        synchronized (beanMap) {
            final Iterator<Class<?>> beanIterator = beanMap.keySet().iterator();
            final Class<?> matchingBean = getMatchingBean(beanNeeded, beanIterator, throwEx);
            return (T) beanMap.get(matchingBean);
        }
    }

    private Class<?> getMatchingBean(final Class<?> beanNeeded, final Iterator<Class<?>> beanIterator, final boolean throwEx) {
        final List<Class<?>> matches = new ArrayList<Class<?>>();

        while (beanIterator.hasNext()) {
            final Class<?> beanClass = beanIterator.next();

            if (beanNeeded.isAssignableFrom(beanClass)) {
                matches.add(beanClass);
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
            final Object bean = findBean(dependency.getBeanClass(), false);

            if (bean == null) {
                missingDeps.add(dependency);
            }
        }

        return missingDeps;
    }
}
