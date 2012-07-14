
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

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.BeanKey;

/**
 * Interface for getting meta-data from beans.
 *
 * @author Christian Ihle
 */
public interface BeanDataHandler {

    /**
     * Gets meta-data for a bean with the given key. This meta-data contains information about
     * constructors, methods and fields that are marked for dependency injection.
     *
     * @param beanKey The key containing the class to get meta-data from.
     * @param skipConstructor If finding the correct constructor to use when creating an instance of
     * this class should be skipped.
     * @return Class meta-data.
     */
    BeanData getBeanData(BeanKey beanKey, boolean skipConstructor);
}
