
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

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * A wrapper around a map with TypeVariable as key and Type as value.
 * This is used when resolving actual type parameters of a class.
 *
 * @author Christian Ihle
 */
public class TypeMap {

    private final Map<TypeVariable<?>, Type> typeMap;

    /**
     * Creates a new empty type map.
     */
    public TypeMap() {
        typeMap = new HashMap<TypeVariable<?>, Type>();
    }

    /**
     * Gets the actual type value for the type variable key.
     *
     * @param typeVariable The key.
     * @return The value.
     */
    public Type getActualType(final TypeVariable<?> typeVariable) {
        Validate.notNull(typeVariable, "Type variable can not be null");

        return typeMap.get(typeVariable);
    }

    /**
     * Adds an actual type to the map, with the type variable as the key.
     *
     * @param typeVariable The key.
     * @param actualType The value.
     */
    public void addActualType(final TypeVariable<?> typeVariable, final Type actualType) {
        Validate.notNull(typeVariable, "Type variable can not be null");
        Validate.notNull(actualType, "Actual type can not be null");

        final Type typeToAdd = resolveActualType(actualType);
        typeMap.put(typeVariable, typeToAdd);
    }

    /**
     * Handles cases where the actual type is a type variable. Tries to find a type in the map that
     * isn't a type variable, and uses that instead.
     *
     * @param actualType An actual type that may be a type variable.
     * @return Hopefully the real actual type that the type variable points to.
     */
    private Type resolveActualType(final Type actualType) {
        Type typeToAdd = actualType;

        while (typeToAdd instanceof TypeVariable<?>) {
            typeToAdd = getActualType((TypeVariable<?>) actualType);
        }

        return typeToAdd;
    }

    /**
     * Returns the number of elements in the map.
     *
     * @return The size of the map.
     */
    public int size() {
        return typeMap.size();
    }

    /**
     * Returns all the keys in the map.
     *
     * @return All the keys.
     */
    public Set<TypeVariable<?>> getKeys() {
        return typeMap.keySet();
    }
}
