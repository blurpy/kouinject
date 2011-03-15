
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

import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;

/**
 * Interface for getting meta-data about factory points from beans.
 *
 * @author Christian Ihle
 */
public interface FactoryPointHandler {

    /**
     * Gets meta-data of all the factory points in the factory bean.
     *
     * @param factoryBean The key for the bean to get factory points from.
     * @return The detected factory points in the factory bean.
     */
    List<FactoryPoint<?>> getFactoryPoints(BeanKey factoryBean);
}
