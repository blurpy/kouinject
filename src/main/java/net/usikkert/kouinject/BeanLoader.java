
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

import java.util.Collection;

/**
 * Interface for the main IoC component, the component that loads beans and autowires the
 * dependencies.
 *
 * @author Christian Ihle
 */
public interface BeanLoader {

    /**
     * Gets a bean that matches the given class, with no qualifier.
     *
     * <p>A bean is considered a match if it's assignable from the bean class, and has no qualifier.</p>
     *
     * @param <T> The bean will be cast to the type specified.
     * @param beanClass The class to get a bean for.
     * @return An object satisfying the specified class.
     * @throws IllegalArgumentException If no bean is found.
     */
    <T> T getBean(Class<T> beanClass);

    /**
     * Gets a bean that matches the given class and qualifier.
     *
     * <p>A bean is considered a match if it's assignable from the bean class,
     * and has the exact same qualifier. If the qualifier is <code>null</code>, then only beans
     * with no qualifier will match. If the qualifier is <code>any</code> then
     * the qualifier match is ignored.</p>
     *
     * @param <T> The bean will be cast to the type specified.
     * @param beanClass The class to get a bean for.
     * @param qualifier The qualifier for the bean to get. Can be <code>null</code>.
     * @return An object satisfying the specified class and qualifier.
     * @throws IllegalArgumentException If no bean is found.
     */
    <T> T getBean(Class<T> beanClass, String qualifier);

    /**
     * Gets a collection of all the beans that matches the bean class, with no qualifier.
     *
     * <p>A bean is considered a match if it's assignable from the bean class, and has no qualifier.</p>
     *
     * @param <T> The beans will be cast to the type specified.
     * @param beanClass The class to get beans for.
     * @return A collection of beans satisfying the bean class.
     * @throws IllegalArgumentException If no beans are found.
     */
    <T> Collection<T> getBeans(Class<T> beanClass);

    /**
     * Gets a collection of all the beans that matches the bean class and qualifier.
     *
     * <p>A bean is considered a match if it's assignable from the bean class,
     * and has the exact same qualifier. If the qualifier is <code>null</code>, then only beans
     * with no qualifier will match. If the qualifier is <code>any</code> then
     * the qualifier match is ignored.</p>
     *
     * @param <T> The beans will be cast to the type specified.
     * @param beanClass The class to get beans for.
     * @param qualifier The qualifier for the bean to get. Can be <code>null</code>.
     * @return A collection of beans satisfying the bean class and qualifier.
     * @throws IllegalArgumentException If no beans are found.
     */
    <T> Collection<T> getBeans(Class<T> beanClass, String qualifier);
}
