
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

import org.apache.commons.lang.Validate;

/**
 * An abstract class that can be used to represent a complete generic type argument.
 *
 * <p>This is a workaround for the fact that the language does not support writing
 * <code>List&lt;String&gt;.class</code>. Using this class you can instead write the following:</p>
 *
 * <pre>TypeLiteral&lt;List&lt;String&gt;&gt; listOfStrings = new TypeLiteral&lt;List&lt;String&gt;&gt;() {};</pre>
 *
 * <p>Use {@link #getGenericType()} to get the generic type, which would return <code>List&lt;String&gt;</code> in this example.</p>
 *
 * <p>Inspired by http://gafter.blogspot.com/2006/12/super-type-tokens.html. This is also used by
 * Google Guice and JSR-299.</p>
 *
 * @param <T> The type argument to capture, for later access.
 * @author Christian Ihle
 */
public abstract class TypeLiteral<T> {

    private final Type genericType;
    private final Class<T> genericClass;

    /**
     * Constructor that extracts the generic type argument.
     *
     * <p>{@link #getGenericType()} is the type of &lt;T&gt;, and {@link #getGenericClass()} is the raw type of &lt;T&gt;.</p>
     */
    @SuppressWarnings("unchecked")
    protected TypeLiteral() {
        final Type genericSuperclass = getClass().getGenericSuperclass();

        this.genericType = GenericsHelper.getGenericArgumentAsType(genericSuperclass);
        this.genericClass = (Class<T>) GenericsHelper.getGenericArgumentAsClass(genericSuperclass);
    }

    /**
     * Constructor that accepts the generic type as a parameter.
     *
     * <p>{@link #getGenericType()} is the type in the parameter, and {@link #getGenericClass()} is the
     * raw type of the type in the parameter.</p>
     *
     * @param type The type this type literal will handle.
     */
    @SuppressWarnings("unchecked")
    protected TypeLiteral(final Type type) {
        Validate.notNull(type, "Type can not be null");

        this.genericType = type;
        this.genericClass = (Class<T>) GenericsHelper.getAsClassOrNull(type);
    }

    /**
     * Gets the generic type argument.
     *
     * <p><code>TypeLiteral&lt;List&lt;String&gt;&gt;</code> will return the type for <code>List&lt;String&gt;</code>.</p>
     *
     * @return The generic type argument.
     */
    public Type getGenericType() {
        return genericType;
    }

    /**
     * Gets the type argument as a regular class.
     *
     * <p><code>TypeLiteral&lt;List&lt;String&gt;&gt;</code> will return <code>Class&lt;List&lt;String&gt;&gt;</code>.</p>
     *
     * @return The type as a regular class. Can be <code>null</code>.
     */
    public Class<T> getGenericClass() {
        return genericClass;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof TypeLiteral)) { return false; }

        final TypeLiteral<?> that = (TypeLiteral<?>) o;

        return genericType.equals(that.genericType);
    }

    @Override
    public int hashCode() {
        return genericType.hashCode();
    }

    @Override
    public String toString() {
        return genericType.toString();
    }
}
