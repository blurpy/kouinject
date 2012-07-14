
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Scope;
import javax.inject.Singleton;

import org.apache.commons.lang.Validate;

/**
 * Class for finding the scope of beans and factory methods.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedScopeHandler {

    private static final Class<Scope> SCOPE_ANNOTATION = Scope.class;

    /**
     * Checks if the bean has a singleton scope.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>There can be at most 1 scope annotation.</li>
     *   <li>If no scope annotations are found, then <code>false</code> is returned.</li>
     *   <li>If the {@link Singleton} annotation is found, then <code>true</code> is returned.</li>
     *   <li>If any other scope annotation is found, then an exception is thrown.</li>
     * </ul>
     *
     * @param beanClass The bean class to check the scope of.
     * @return If the bean is a singleton.
     */
    public boolean isSingleton(final Class<?> beanClass) {
        return isSingletonInternal(beanClass);
    }

    /**
     * Checks if the factory method has a singleton scope.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>There can be at most 1 scope annotation.</li>
     *   <li>If no scope annotations are found, then <code>false</code> is returned.</li>
     *   <li>If the {@link Singleton} annotation is found, then <code>true</code> is returned.</li>
     *   <li>If any other scope annotation is found, then an exception is thrown.</li>
     * </ul>
     *
     * @param factoryMethod The factory method to check the scope of.
     * @return If the factory method creates a singleton.
     */
    public boolean isSingleton(final Method factoryMethod) {
        return isSingletonInternal(factoryMethod);
    }

    private boolean isSingletonInternal(final AnnotatedElement annotatedElement) {
        Validate.notNull(annotatedElement, "Can't find scope of null");

        final Annotation scope = getScope(annotatedElement);

        if (scope instanceof Singleton) {
            return true;
        }

        if (scope == null) {
            return false;
        }

        throw new UnsupportedOperationException("Unknown scope on " + annotatedElement + " " + scope);
    }

    private Annotation getScope(final AnnotatedElement beanClass) {
        final List<Annotation> matches = new ArrayList<Annotation>();
        final Annotation[] annotations = beanClass.getAnnotations();

        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(SCOPE_ANNOTATION)) {
                matches.add(annotation);
            }
        }

        if (matches.size() == 0) {
            return null;
        }

        else if (matches.size() > 1) {
            throw new UnsupportedOperationException(
                    "Wrong number of scope annotations found on " + beanClass + " " + matches);
        }

        return matches.get(0);
    }
}
