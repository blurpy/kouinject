
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

package net.usikkert.kouinject;

import java.util.Collection;

/**
 * Provides a collection of instances of {@code T}.
 *
 * <p>Compared to injecting a {@link Collection} this interface gives the same benefits
 * as using a {@link javax.inject.Provider} for single instances.</p>
 *
 * @param <T> The type to get instances of.
 * @author Christian Ihle
 */
public interface CollectionProvider<T> {

    /**
     * Provides a collection of fully constructed instances of {@code T}.
     *
     * @return A collection with all matching instances of {@code T}.
     */
    Collection<T> get();
}
