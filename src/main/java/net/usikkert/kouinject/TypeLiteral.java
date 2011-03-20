
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * An abstract class that can be used to represent a complete generic type argument.
 *
 * <p>This is a workaround for the fact that the language does not support writing
 * <code>List&lt;String&gt;.class</code>. Using this class you can instead write the following:</p>
 *
 * <pre>TypeLiteral&lt;List&lt;String&gt;&gt; listOfStrings = new TypeLiteral&lt;List&lt;String&gt;&gt;() {};</pre>
 *
 * <p>Use {@link #getType()} to get the generic type, which would return <code>List&lt;String&gt;</code> in this example.</p>
 *
 * <p>Inspired by http://gafter.blogspot.com/2006/12/super-type-tokens.html. This is also used by
 * Google Guice and JSR-299.</p>
 *
 * @param <T> The type argument to capture, for later access.
 * @author Christian Ihle
 */
public abstract class TypeLiteral<T> {

    private final Type type;

    /**
     * Constructor that extracts the generic type argument.
     */
    protected TypeLiteral() {
        final Type genericSuperclass = getClass().getGenericSuperclass();

        if (genericSuperclass instanceof Class) {
            throw new IllegalArgumentException("Generic type is required");
        }

        this.type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    /**
     * Gets the generic type argument.
     *
     * @return The generic type argument.
     */
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof TypeLiteral)) { return false; }

        final TypeLiteral that = (TypeLiteral) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
