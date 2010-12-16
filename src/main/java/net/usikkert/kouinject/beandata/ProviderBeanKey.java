
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

/**
 * A {@link BeanKey} for representing request for a {@link javax.inject.Provider}.
 *
 * @author Christian Ihle
 */
public class ProviderBeanKey extends BeanKey {

    /**
     * Creates a new bean key for a provider.
     *
     * @param beanClass The actual bean class for this key.
     * @param qualifier The qualifier for this key.
     */
    public ProviderBeanKey(final Class<?> beanClass, final String qualifier) {
        super(beanClass, qualifier);
    }

    /**
     * Checks if this bean key is for a provider.
     *
     * @return True.
     */
    @Override
    public boolean isProvider() {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("[provider] ");
        toStringBuilder.append(super.toString());

        return toStringBuilder.toString();
    }
}
