
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
 * Represents a dependency marked for injection.
 *
 * @author Christian Ihle
 */
public class Dependency {

    private final Class<?> beanClass;

    private final boolean isProvider;

    /**
     * Creates a new dependency for the specified bean class.
     *
     * @param beanClass The actual bean class dependency.
     * @param isProvider If the actual bean class is for a provider.
     */
    public Dependency(final Class<?> beanClass, final boolean isProvider) {
        this.beanClass = beanClass;
        this.isProvider = isProvider;
    }

    /**
     * Gets the actual bean class dependency. If this is a {@link Provider},
     * then this bean class is the generic type argument of the provider.
     *
     * @return The bean class dependency.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * If this dependency is for a {@link Provider}.
     *
     * @return If this is for a provider.
     */
    public boolean isProvider() {
        return isProvider;
    }

    @Override
    public String toString() {
        if (isProvider) {
            return "[provider] " + beanClass;
        }

        else {
            return beanClass.toString();
        }
    }
}
