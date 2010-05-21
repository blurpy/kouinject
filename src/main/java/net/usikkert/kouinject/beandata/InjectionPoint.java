
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

package net.usikkert.kouinject.beandata;

import java.util.List;

/**
 * This interface represents an injection point like a method or a field.
 *
 * @author Christian Ihle
 */
public interface InjectionPoint {

    /**
     * Injects the parameters into this injection point in the object.
     *
     * @param object The object with this injection point to inject the parameters into.
     * @param parameters The parameters to inject.
     */
    void inject(final Object object, final Object[] parameters);

    /**
     * Gets the dependencies required to execute an injection into this injection point.
     *
     * @return A list of the required dependencies.
     */
    List<Dependency> getDependencies();
}
