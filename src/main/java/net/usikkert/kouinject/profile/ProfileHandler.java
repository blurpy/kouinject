
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

package net.usikkert.kouinject.profile;

/**
 * Interface for handling profiles.
 *
 * <p>Implementations must handle the injector's list of active profiles. The beans that have a profile
 * matching one of the injectors active profiles are considered active. Beans without a profile are
 * active by default.</p>
 *
 * @see net.usikkert.kouinject.annotation.Profile
 * @author Christian Ihle
 */
public interface ProfileHandler {

    /**
     * Checks if the bean is active by comparing the bean's profiles with the injector's active profiles.
     *
     * @param beanClass The bean class to check.
     * @return If the bean is associated with an active profile.
     */
    boolean beanIsActive(Class<?> beanClass);
}
