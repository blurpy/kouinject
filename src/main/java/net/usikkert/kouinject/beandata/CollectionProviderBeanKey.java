
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

package net.usikkert.kouinject.beandata;

import net.usikkert.kouinject.TypeLiteral;

/**
 * A {@link net.usikkert.kouinject.beandata.BeanKey} for representing a request for a
 * {@link net.usikkert.kouinject.CollectionProvider} of beans.
 *
 * @author Christian Ihle
 */
public class CollectionProviderBeanKey extends BeanKey {

    /**
     * Creates a new bean key for a collection provider of beans.
     *
     * @param beanClass The actual bean class for this key.
     * @param qualifier The qualifier for this key.
     */
    public CollectionProviderBeanKey(final Class<?> beanClass, final String qualifier) {
        super(beanClass, qualifier);
    }

    /**
     * Creates a new collection provider bean key for the specified bean type, with the specified qualifier.
     *
     * @param beanType The type literal with the actual type and class for this key.
     * @param qualifier The qualifier for this key.
     */
    public CollectionProviderBeanKey(final TypeLiteral<?> beanType, final String qualifier) {
        super(beanType, qualifier);
    }

    /**
     * Checks if this bean key is for a collection provider of beans.
     *
     * @return True.
     */
    @Override
    public boolean isCollectionProvider() {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("[collection provider] ");
        toStringBuilder.append(super.toString());

        return toStringBuilder.toString();
    }
}
