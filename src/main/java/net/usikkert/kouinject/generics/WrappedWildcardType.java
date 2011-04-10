
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

package net.usikkert.kouinject.generics;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import org.apache.commons.lang.Validate;

/**
 * This is an implementation of {@link WildcardType}.
 *
 * <p>Used when replacing type variabled with actual types. It does not do anything special,
 * but is needed because there is no official way to create an instance of a type. It should be mostly
 * compatible with the official implementation.</p>
 *
 * @author Christian Ihle
 */
public class WrappedWildcardType implements WildcardType {

    private final Type[] upperBounds;
    private final Type[] lowerBounds;

    public WrappedWildcardType(final Type[] upperBounds, final Type[] lowerBounds) {
        Validate.notNull(upperBounds, "Upper bounds can not be null");
        Validate.notNull(lowerBounds, "Lower bounds can not be null");

        this.upperBounds = upperBounds.clone();
        this.lowerBounds = lowerBounds.clone();
    }

    @Override
    public Type[] getUpperBounds() {
        return upperBounds.clone();
    }

    @Override
    public Type[] getLowerBounds() {
        return lowerBounds.clone();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WildcardType)) { return false; }

        final WildcardType that = (WildcardType) o;

        return Arrays.equals(lowerBounds, that.getLowerBounds()) && Arrays.equals(upperBounds, that.getUpperBounds());
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(upperBounds) + Arrays.hashCode(lowerBounds);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (lowerBounds.length > 0) {
            final Type lowerBound = lowerBounds[0];

            if (lowerBound instanceof Class<?>) {
                sb.append("? super ").append(((Class<?>) lowerBound).getName());
            }

            else {
                sb.append(lowerBound.toString());
            }
        }

        else if (upperBounds.length > 0) {
            final Type upperBound = upperBounds[0];

            if (upperBound.equals(Object.class)) {
                sb.append("?");
            }

            else if (upperBound instanceof Class<?>) {
                sb.append("? extends ").append(((Class<?>) upperBound).getName());
            }

            else {
                sb.append(upperBound.toString());
            }
        }

        return sb.toString();
    }
}
