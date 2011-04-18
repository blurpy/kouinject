
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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import org.apache.commons.lang.Validate;

/**
 * This is an implementation of {@link GenericArrayType}.
 *
 * <p>Used when replacing type variables with actual types. It does not do anything special,
 * but is needed because there is no official way to create an instance of a type. It should be mostly
 * compatible with the official implementation.</p>
 *
 * @author Christian Ihle
 */
public class WrappedGenericArrayType implements GenericArrayType {

    private static final int HASH_CODE_MULTIPLIER = 31;

    private final Type genericComponentType;

    /**
     * Creates a new wrapped generic array type for the specified component type.
     *
     * @param genericComponentType The type this array represents. Such as String.class For a String[].
     * Or another generic array type if this array has multiple dimensions.
     */
    public WrappedGenericArrayType(final Type genericComponentType) {
        Validate.notNull(genericComponentType, "Generic component type can not be null");
        this.genericComponentType = genericComponentType;
    }

    @Override
    public Type getGenericComponentType() {
        return genericComponentType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GenericArrayType)) {
            return false;
        }

        final GenericArrayType that = (GenericArrayType) o;

        return genericComponentType.equals(that.getGenericComponentType());
    }

    @Override
    public int hashCode() {
        return HASH_CODE_MULTIPLIER * genericComponentType.hashCode();
    }

    @Override
    public String toString() {
        if (genericComponentType instanceof Class<?>) {
            return ((Class<?>) genericComponentType).getName() + "[]";
        }

        else {
            return genericComponentType.toString() + "[]";
        }
    }
}
