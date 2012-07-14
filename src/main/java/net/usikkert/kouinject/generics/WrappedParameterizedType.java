
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

package net.usikkert.kouinject.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.apache.commons.lang.Validate;

/**
 * This is an implementation of {@link ParameterizedType}.
 *
 * <p>Used when replacing type variables with actual types. It does not do anything special,
 * but is needed because there is no official way to create an instance of a type. It should be mostly
 * compatible with the official implementation.</p>
 *
 * @author Christian Ihle
 */
public class WrappedParameterizedType implements ParameterizedType {

    private static final int HASH_CODE_MULTIPLIER = 41;

    private final Class<?> rawType;
    private final Type[] actualTypeArguments;

    /**
     * Creates a new wrapped parameterized type with the specified raw type and type arguments.
     *
     * @param rawType The class this parameterized type represents.
     * @param actualTypeArguments The generic arguments of the class.
     */
    public WrappedParameterizedType(final Class<?> rawType, final Type[] actualTypeArguments) {
        Validate.notNull(rawType, "Raw type can not be null");
        Validate.notNull(actualTypeArguments, "Actual type arguments can not be null");

        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments.clone();
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments.clone();
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(rawType.getName()).append("<");

        for (int i = 0; i < actualTypeArguments.length; i++) {
            final Type actualTypeArgument = actualTypeArguments[i];

            if (actualTypeArgument instanceof Class<?>) {
                sb.append(((Class<?>) actualTypeArgument).getName());
            }

            else {
                sb.append(actualTypeArgument);
            }

            if (i < actualTypeArguments.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(">");

        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ParameterizedType)) {
            return false;
        }

        final ParameterizedType that = (ParameterizedType) o;

        return rawType.equals(that.getRawType()) && Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
    }

    @Override
    public int hashCode() {
        return HASH_CODE_MULTIPLIER * rawType.hashCode() + Arrays.hashCode(actualTypeArguments);
    }
}
