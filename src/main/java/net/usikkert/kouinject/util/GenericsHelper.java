
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

package net.usikkert.kouinject.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang.Validate;

/**
 * Helper class for common operations on generics.
 *
 * @author Christian Ihle
 */
public class GenericsHelper {

    /**
     * Returns the generic argument used on the type, as a type.
     *
     * <p>Example: the type <code>Set&lt;List&lt;String&gt;&gt;</code> would return <code>List&lt;String&gt;</code>.</p>
     *
     * @param type The type to get the generic argument from. Supports only 1 generic argument.
     * @return The argument of the type, as a type.
     */
    public Type getGenericArgumentAsType(final Type type) {
        Validate.notNull(type, "Type can not be null");

        final ParameterizedType parameterizedType = getAsParameterizedType(type);
        final Type[] typeArguments = parameterizedType.getActualTypeArguments();
        Validate.isTrue(typeArguments.length == 1, "Wrong number of type arguments (expected 1): " + type);

        return typeArguments[0];
    }

    /**
     * Returns the generic argument used on the type, as a class.
     *
     * <p>Example: the type <code>Set&lt;List&lt;String&gt;&gt;</code> would return the class for <code>List</code>.</p>
     *
     * @param type The type to get the generic argument from. Supports only 1 generic argument.
     * @return The argument of the type, as a class.
     */
    public Class<?> getGenericArgumentAsClass(final Type type) {
        Validate.notNull(type, "Type can not be null");

        final ParameterizedType parameterizedType = getAsParameterizedType(type);
        final Type argument = getGenericArgumentAsType(parameterizedType);

        return getAsClass(argument);
    }

    /**
     * Returns the type as a class.
     *
     * <p>Example: the type <code>Set&lt;List&lt;String&gt;&gt;</code> would return the class for <code>Set</code>.</p>
     *
     * @param type The type to get as a class.
     * @return The type as a class.
     */
    public Class<?> getAsClass(final Type type) {
        Validate.notNull(type, "Type can not be null");

        if (isClass(type)) {
            return (Class<?>) type;
        }

        else if (isParameterizedType(type)) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<?>) parameterizedType.getRawType();
        }

        throw new IllegalArgumentException("Unsupported generic type: " + type);
    }

    /**
     * Checks if the type is a class, as opposed to a type with generic parameters.
     *
     * <p>Example: <code>String</code> would return true, <code>List&lt;String&gt;</code> would return false.
     *
     * @param type The type to check.
     * @return If the type is a regular class.
     */
    public boolean isClass(final Type type) {
        Validate.notNull(type, "Type can not be null");

        return type instanceof Class;
    }

    /**
     * Checks if the type has generic parameters, as opposed to a regular class.
     *
     * <p>Example: <code>String</code> would return false, <code>List&lt;String&gt;</code> would return true.
     *
     * @param type The type to check.
     * @return If the type is parameterized.
     */
    public boolean isParameterizedType(final Type type) {
        Validate.notNull(type, "Type can not be null");

        return type instanceof ParameterizedType;
    }

    private ParameterizedType getAsParameterizedType(final Type type) {
        if (isClass(type)) {
            throw new IllegalArgumentException("Generic type <T> is required: " + type);
        }

        else if (isParameterizedType(type)) {
            return (ParameterizedType) type;
        }

        throw new IllegalArgumentException("Unsupported generic type: " + type);
    }
}
