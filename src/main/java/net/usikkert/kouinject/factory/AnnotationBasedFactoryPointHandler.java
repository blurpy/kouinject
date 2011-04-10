
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

package net.usikkert.kouinject.factory;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.usikkert.kouinject.AnnotationBasedScopeHandler;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.generics.GenericsHelper;
import net.usikkert.kouinject.generics.TypeMap;
import net.usikkert.kouinject.util.BeanHelper;
import net.usikkert.kouinject.util.ReflectionUtils;

import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link FactoryPointHandler} that uses annotations and reflection to scan beans
 * for meta-data about factory points.
 *
 * <p>Factory points are detected based on the {@link net.usikkert.kouinject.annotation.Produces} annotation.
 * Scope, qualifier, and dependencies are also detected.</p>
 *
 * @author Christian Ihle
 */
public class AnnotationBasedFactoryPointHandler implements FactoryPointHandler {

    private static final Class<Produces> FACTORY_ANNOTATION = Produces.class;
    private static final Class<Inject> INJECTION_ANNOTATION = Inject.class;

    private final AnnotationBasedScopeHandler scopeHandler = new AnnotationBasedScopeHandler();
    private final ReflectionUtils reflectionUtils = new ReflectionUtils();
    private final BeanHelper beanHelper = new BeanHelper();
    private final GenericsHelper genericsHelper = new GenericsHelper();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FactoryPoint<?>> getFactoryPoints(final BeanKey factoryBean) {
        Validate.notNull(factoryBean, "Factory bean can not be null");
        final Class<?> beanClass = factoryBean.getBeanClass();
        Validate.notNull(beanClass, "Factory class can not be null");

        final TypeMap typeMap = genericsHelper.mapTypeVariablesToActualTypes(beanClass);
        final List<Method> allMethods = reflectionUtils.findAllMethods(beanClass);
        final List<Member> allMembers = reflectionUtils.findAllMembers(beanClass);

        return findAllFactoryPoints(allMembers, allMethods, factoryBean, typeMap);
    }

    private List<FactoryPoint<?>> findAllFactoryPoints(final List<Member> allMembers, final List<Method> allMethods,
                                                       final BeanKey factoryBean, final TypeMap typeMap) {
        final List<FactoryPoint<?>> factoryPoints = new ArrayList<FactoryPoint<?>>();

        for (final Member member : allMembers) {
            if (member instanceof Method) {
                final Method method = (Method) member;

                if (methodIsFactoryPoint(method) && !reflectionUtils.isOverridden(method, allMethods)) {
                    failIfInjectionPoint(method);

                    final FactoryPointMethod<?> factoryPointMethod = createFactoryPointMethod(method, factoryBean, typeMap);
                    factoryPoints.add(factoryPointMethod);
                }
            }
        }

        return factoryPoints;
    }

    private void failIfInjectionPoint(final Method method) {
        if (methodIsInjectionPoint(method)) {
            throw new UnsupportedOperationException("A factory point can't also be an injection point: " + method);
        }
    }

    private boolean methodIsFactoryPoint(final Method method) {
        return !reflectionUtils.isStatic(method) && method.isAnnotationPresent(FACTORY_ANNOTATION);
    }

    private boolean methodIsInjectionPoint(final Method method) {
        return method.isAnnotationPresent(INJECTION_ANNOTATION);
    }

    private <T> FactoryPointMethod<T> createFactoryPointMethod(final Method method, final BeanKey factoryBean,
                                                               final TypeMap typeMap) {
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method, typeMap);
        final BeanKey returnType = beanHelper.findFactoryReturnType(method, typeMap);
        final boolean singleton = scopeHandler.isSingleton(method);

        return new FactoryPointMethod<T>(method, factoryBean, returnType, parameters, singleton);
    }
}
