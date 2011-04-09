
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

import net.usikkert.kouinject.generics.TypeLiteral;

import org.apache.commons.lang.Validate;

/**
 * A {@link BeanKey} for representing a request for a collection of beans.
 *
 * @author Christian Ihle
 */
public class CollectionBeanKey extends BeanKey {

    private final BeanKey actualBeanKey;

    /**
     * Creates a new collection bean key for the specified bean type, with the specified qualifier.
     *
     * @param actualBeanType The actual collection bean type this key describes.
     * @param beanType The type used on the collection.
     * @param qualifier The qualifier for this key.
     */
    public CollectionBeanKey(final TypeLiteral<?> actualBeanType, final TypeLiteral<?> beanType, final String qualifier) {
        super(beanType, qualifier);

        Validate.notNull(actualBeanType, "Actual bean type can not be null");
        actualBeanKey = new BeanKey(actualBeanType, qualifier);
    }

    /**
     * Checks if this bean key is for a collection of beans.
     *
     * @return True.
     */
    @Override
    public boolean isCollection() {
        return true;
    }

    /**
     * Returns the actual collection type as requested originally.
     *
     * @return The actual bean type as a new bean key.
     */
    @Override
    public BeanKey getActualBeanKey() {
        return actualBeanKey;
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("[collection] ");
        toStringBuilder.append(super.toString());

        return toStringBuilder.toString();
    }
}
