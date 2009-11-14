
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

import java.util.Set;

/**
 * The is the interface for finding beans handled by the IoC container,
 * and meta-data for those beans.
 *
 * @author Christian Ihle
 */
public interface BeanDataHandler
{
	/**
	 * Finds the beans registered in this IoC container, and returns
	 * them as class references.
	 *
	 * @return All registered beans as classes.
	 */
	Set<Class<?>> findBeans();

	/**
	 * Gets meta-data for a bean with the given class. This meta-data contains
	 * information about constructors, methods and fields that are
	 * marked for dependency injection.
	 *
	 * @param beanClass The class to get meta-data from.
	 * @param skipConstructor If finding the correct constructor to use when creating an
	 *                        instance of this class should be skipped.
	 * @return Class meta-data.
	 */
	BeanData getBeanData( Class<?> beanClass, boolean skipConstructor );
}
