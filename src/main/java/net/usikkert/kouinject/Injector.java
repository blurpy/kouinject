
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
 * Public interface for the KouInject injector, giving access to the available beans.
 *
 * @author Christian Ihle
 */
public interface Injector {

    /**
     * Gets a bean from the injector for the given class, with no qualifier.
     *
     * @param <T> The bean will be cast to the type specified.
     * @param beanClass The class to get a bean for.
     * @return An object satisfying the specified class.
     * @throws RuntimeException If no bean is found.
     */
    <T extends Object> T getBean(Class<T> beanClass);

    /**
     * Gets a bean from the injector for the given class and qualifier.
     *
     * @param <T> The bean will be cast to the type specified.
     * @param beanClass The class to get a bean for.
     * @param qualifier The qualifier for the bean to get.
     * @return An object satisfying the specified class.
     * @throws RuntimeException If no bean is found.
     */
    <T extends Object> T getBean(Class<T> beanClass, String qualifier);
}
