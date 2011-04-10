
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import org.apache.commons.lang.Validate;

/**
 * Helper class for common operations on generics.
 *
 * @author Christian Ihle
 */
public final class GenericsHelper {

    private GenericsHelper() {
        // Static helpers only
    }

    /**
     * Returns the generic argument used on the type, as a type.
     *
     * <p>Example: the type <code>Set&lt;List&lt;String&gt;&gt;</code> would return <code>List&lt;String&gt;</code>.</p>
     *
     * @param type The type to get the generic argument from. Supports only 1 generic argument.
     * @return The argument of the type, as a type.
     */
    public static Type getGenericArgumentAsType(final Type type) {
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
    public static Class<?> getGenericArgumentAsClass(final Type type) {
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
     * @throws IllegalArgumentException If the class could not be determined.
     */
    public static Class<?> getAsClass(final Type type) {
        final Class<?> classOrNull = getAsClassOrNull(type);

        if (classOrNull == null) {
            throw new IllegalArgumentException("Unsupported generic type: " + type.getClass() + " - " + type);
        }

        return classOrNull;
    }

    /**
     * Returns the type as a class, if possible.
     *
     * <p>Example: the type <code>Set&lt;List&lt;String&gt;&gt;</code> would return the class for <code>Set</code>.</p>
     *
     * @param type The type to get as a class.
     * @return The type as a class, or <code>null</code> if the class could not be determined.
     */
    public static Class<?> getAsClassOrNull(final Type type) {
        Validate.notNull(type, "Type can not be null");

        if (isClass(type)) {
            return (Class<?>) type;
        }

        else if (isParameterizedType(type)) {
            final ParameterizedType parameterizedType = getAsParameterizedType(type);
            return (Class<?>) parameterizedType.getRawType();
        }

        return null;
    }

    /**
     * Checks if the type is a class, as opposed to a type with generic parameters.
     *
     * <p>Example: <code>String</code> would return true, <code>List&lt;String&gt;</code> would return false.
     *
     * @param type The type to check.
     * @return If the type is a regular class.
     */
    public static boolean isClass(final Type type) {
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
    public static boolean isParameterizedType(final Type type) {
        Validate.notNull(type, "Type can not be null");

        return type instanceof ParameterizedType;
    }

    /**
     * Checks if the type is a type variable, as opposed to an actual type.
     *
     * <p>Example: <code>T</code> would return true, <code>Number</code> would return false.
     *
     * @param type The type to check.
     * @return If the type is a type variable.
     */
    public static boolean isTypeVariable(final Type type) {
        return type instanceof TypeVariable<?>;
    }

    /**
     * Checks if the type is a wildcard, as opposed to a class or a type with generic parameters.
     *
     * <p>Example: <code>? extends Number</code> would return true, <code>Number</code> would return false.
     *
     * @param type The type to check.
     * @return If the type is a wildcard.
     */
    public static boolean isWildcard(final Type type) {
        return type instanceof WildcardType;
    }

    /**
     * Determines if "this" type is either the same as, or a supertype of, "that" type.
     *
     * <p>The assignability logic is mostly based on the principle that if it compiles, it's assignable.
     * Supports the following comparisons:</p>
     *
     * <ul>
     *   <li>Class vs class - Class 1 must be assignable from class 2.</li>
     *   <li>Parameterized type vs parameterized type - The raw class of type 1 must be assignable from the raw
     *      class of type 2, and all the parameters of both must be equal.</li>
     *   <li>Wildcard vs wildcard - The bounds of each wildcard must be assignable from each other.</li>
     *   <li>Wildcard vs class or parameterized type - The bounds of the wildcard must be assignable from the class or type.</li>
     *   <li>Class vs parameterized type - Class 1 must be assignable from the raw class of type 2.</li>
     * </ul>
     *
     * <p>Does not support parameterized type vs class.</p>
     *
     * <p>Type variables in "that" will be automatically resolved to an actual type, if possible.
     * "This" as a type variable is not automatically resolved, as it requires knowing the class the type belongs to.
     * Can be solved with {@link #mapTypeVariablesToActualTypes(Class)} and
     * {@link #wrapTypeAndReplaceTypeVariables(java.lang.reflect.Type, TypeMap)} before using this method.</p>
     *
     * @param thisType The left hand side, usually an injection point.
     * @param thatType The right hand side, usually a bean.
     * @return If "this" is assignable from "that".
     */
    public static boolean isAssignableFrom(final Type thisType, final Type thatType) {
        Validate.notNull(thisType, "This type can not be null");
        Validate.notNull(thatType, "That type can not be null");

        return isAssignableFromUsingMap(thisType, thatType, new TypeMap());
    }

    /**
     * Creates a map with all the type variables used on the specified class as keys, and their actual types
     * as values.
     *
     * <p>All super-classes and super-interfaces of the specified class are also searched. If no actual type is
     * found for a type variable, then the value for that key will be <code>null</code>.</p>
     *
     * <p>Example:</p>
     *
     * <pre>
     *   class Basket&lt;T&gt; {}
     *   class FruitBasket extends Basket&lt;Fruit&gt; {}
     * </pre>
     *
     * <p>If <code>FruitBasket.class</code> was the parameter, you would get a map with <code>T</code> as key,
     * and <code>Fruit.class</code> as value. If <code>Basket.class</code> was the parameter then you would get
     * a map with <code>T</code> as key, and <code>null</code> as value.
     *
     *
     * @param aClass The class to search for type variables on.
     * @return A map with the type variables and the actual types that was found on the specified class.
     */
    public static TypeMap mapTypeVariablesToActualTypes(final Class<?> aClass) {
        final TypeMap typeMap = new TypeMap();
        mapTypeVariablesToActualTypes(aClass, typeMap);

        return typeMap;
    }

    /**
     * Takes a type, and replaces any type variables with an actual type from the type map.
     *
     * <p>Supports both parameterized types and wildcards. The replacement is recursive, so if the type
     * variable is in a nested type then the whole hierarchy of types will be wrapped. If no actual type
     * is found in the map, then the type variable is kept as is.</p>
     *
     * <p>Example:</p>
     *
     * <p>If type is <code>List&lt;Basket&lt;T&gt;&gt;</code>, and map contains key <code>T</code> and
     * value <code>Fruit.class</code>, then the wrapped type will be <code>List&lt;Basket&lt;Fruit&gt;&gt;</code>.</p>
     *
     * @param type The type to wrap.
     * @param typeMap The map containing the actual types to use when replacing type variables.
     * @return A wrapped type with type variables replaced with actual types from the map.
     * @see #mapTypeVariablesToActualTypes(Class)
     */
    public static Type wrapTypeAndReplaceTypeVariables(final Type type, final TypeMap typeMap) {
        if (isParameterizedType(type)) {
            final ParameterizedType parameterizedType = getAsParameterizedType(type);
            final Type[] wrappedArguments = wrapTypeParameters(parameterizedType.getActualTypeArguments(), typeMap);
            final Class<?> asClass = getAsClass(type);

            return new WrappedParameterizedType(asClass, wrappedArguments);
        }

        else if (isWildcard(type)) {
            final WildcardType wildcardType = (WildcardType) type;
            final Type[] wrappedUpperBounds = wrapTypeParameters(wildcardType.getUpperBounds(), typeMap);
            final Type[] wrappedLowerBounds = wrapTypeParameters(wildcardType.getLowerBounds(), typeMap);

            return new WrappedWildcardType(wrappedUpperBounds, wrappedLowerBounds);
        }

        else if (isTypeVariable(type)) {
            final Type actualType = typeMap.getActualType((TypeVariable<?>) type);

            if (actualType != null) {
                return actualType;
            }
        }

        return type;
    }

    private static boolean isAssignableFromUsingMap(final Type thisType, final Type thatType, final TypeMap typeMap) {
        if (thisType.equals(thatType)) {
            return true;
        }

        if (isWildcard(thisType)) {
            return isAssignableFromWildcard((WildcardType) thisType, thatType, typeMap);
        }

        if (isWildcard(thatType)) {
            return false;
        }

        final Class<?> thisClass = getAsClass(thisType);
        final Class<?> thatClass = getAsClass(thatType);

        if (thisClass.isAssignableFrom(thatClass)) {
            if (isClass(thisType) && isClass(thatType)) {
                return true;
            }

            else if (isParameterizedType(thisType) && isParameterizedType(thatType)) {
                mapTypeVariablesToActualTypes(thatType, thatClass, typeMap);

                if (typesHaveTheSameParameters(thisType, thatType, typeMap)) {
                    return true;
                }
            }

            // Assignment from generic type to raw type, e.g. List list = new ArrayList<String>();
            else if (isClass(thisType) && isParameterizedType(thatType)) {
                return true;
            }

            // Assigning from a class that implements a generic interface or class to a generic type
            if (isAssignableFromSuperTypes(thisType, thatClass, typeMap)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isAssignableFromSuperTypes(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        return isAssignableFromSuperInterfaces(thisType, thatClass, typeMap)
                || isAssignableFromSuperClass(thisType, thatClass, typeMap);
    }

    private static boolean isAssignableFromSuperInterfaces(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        final Type[] genericInterfaces = thatClass.getGenericInterfaces();

        for (final Type genericInterface : genericInterfaces) {
            if (isAssignableFromUsingMap(thisType, genericInterface, typeMap)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isAssignableFromSuperClass(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        final Type genericSuperclass = thatClass.getGenericSuperclass();
        return genericSuperclass != null && isAssignableFromUsingMap(thisType, genericSuperclass, typeMap);
    }

    private static boolean typesHaveTheSameParameters(final Type thisType, final Type thatType, final TypeMap typeMap) {
        final Type[] thisArguments = getGenericArgumentsAsType(thisType);
        final Type[] thatArguments = getGenericArgumentsAsType(thatType);

        if (thisArguments.length != thatArguments.length) {
            return false;
        }

        for (int i = 0; i < thisArguments.length; i++) {
            final Type thisArgument = thisArguments[i];
            final Type thatArgument = thatArguments[i];

            if (!typesHaveTheSameParameter(thisArgument, thatArgument, typeMap)) {
                return false;
            }
        }

        return true;
    }

    private static Type[] getGenericArgumentsAsType(final Type type) {
        final ParameterizedType parameterizedType = getAsParameterizedType(type);
        return parameterizedType.getActualTypeArguments();
    }

    private static boolean typesHaveTheSameParameter(final Type thisArgument, final Type thatArgument, final TypeMap typeMap) {
        if (thisArgument.equals(thatArgument)) {
            return true;
        }

        if (isWildcard(thisArgument)) {
            if (isWildcard(thatArgument)) {
                return wildcardsAreAssignable((WildcardType) thisArgument, (WildcardType) thatArgument, typeMap);
            }

            else {
                return isAssignableFromWildcard((WildcardType) thisArgument, thatArgument, typeMap);
            }
        }

        else if (isTypeVariable(thatArgument)) {
            final TypeVariable<?> thatTypeVariable = (TypeVariable<?>) thatArgument;
            final Type thatResolvedType = typeMap.getActualType(thatTypeVariable);

            return typesHaveTheSameParameter(thisArgument, thatResolvedType, typeMap);
        }

        return false;
    }

    private static boolean isAssignableFromWildcard(final WildcardType thisWildcard, final Type thatType, final TypeMap typeMap) {
        final Type[] upperBounds = thisWildcard.getUpperBounds();
        final Type[] lowerBounds = thisWildcard.getLowerBounds();

        for (final Type upperBound : upperBounds) {
            if (!isAssignableFromUsingMap(upperBound, thatType, typeMap)) {
                return false;
            }
        }

        for (final Type lowerBound : lowerBounds) {
            if (!isAssignableFromUsingMap(thatType, lowerBound, typeMap)) {
                return false;
            }
        }

        return true;
    }

    private static boolean wildcardsAreAssignable(final WildcardType thisWildcard, final WildcardType thatWildcard,
                                           final TypeMap typeMap) {
        final Type[] thisWildcardUpperBounds = thisWildcard.getUpperBounds();
        final Type[] thisWildcardLowerBounds = thisWildcard.getLowerBounds();

        final Type[] thatWildcardUpperBounds = thatWildcard.getUpperBounds();
        final Type[] thatWildcardLowerBounds = thatWildcard.getLowerBounds();

        if (thisWildcardLowerBounds.length != thatWildcardLowerBounds.length
                || thisWildcardUpperBounds.length != thatWildcardUpperBounds.length) {
            return false;
        }

        for (int i = 0; i < thisWildcardUpperBounds.length; i++) {
            final Type thisWildcardUpperBound = thisWildcardUpperBounds[i];
            final Type thatWildcardUpperBound = thatWildcardUpperBounds[i];

            if (!isAssignableFromUsingMap(thisWildcardUpperBound, thatWildcardUpperBound, typeMap)) {
                return false;
            }
        }

        for (int i = 0; i < thisWildcardLowerBounds.length; i++) {
            final Type thisWildcardLowerBound = thisWildcardLowerBounds[i];
            final Type thatWildcardLowerBound = thatWildcardLowerBounds[i];

            if (!isAssignableFromUsingMap(thatWildcardLowerBound, thisWildcardLowerBound, typeMap)) {
                return false;
            }
        }

        return true;
    }

    private static ParameterizedType getAsParameterizedType(final Type type) {
        if (isClass(type)) {
            throw new IllegalArgumentException("Generic type <T> is required: " + type);
        }

        else if (isParameterizedType(type)) {
            return (ParameterizedType) type;
        }

        throw new IllegalArgumentException("Unsupported generic type: " + type);
    }

    private static void mapTypeVariablesToActualTypes(final Type type, final TypeMap typeMap) {
        final Class<?> asClass = getAsClass(type);

        if (isParameterizedType(type)) {
            mapTypeVariablesToActualTypes(type, asClass, typeMap);
        }

        final Type[] genericInterfaces = asClass.getGenericInterfaces();

        for (final Type genericInterface : genericInterfaces) {
            mapTypeVariablesToActualTypes(genericInterface, typeMap);
        }

        final Type genericSuperclass = asClass.getGenericSuperclass();

        if (genericSuperclass != null) {
            mapTypeVariablesToActualTypes(genericSuperclass, typeMap);
        }
    }

    private static void mapTypeVariablesToActualTypes(final Type thatType, final Class<?> thatClass, final TypeMap typeMap) {
        final ParameterizedType thatParameterizedType = getAsParameterizedType(thatType);
        final Type[] actualTypeArguments = thatParameterizedType.getActualTypeArguments();
        final TypeVariable<?>[] typeParameters = thatClass.getTypeParameters();

        for (int i = 0; i < typeParameters.length; i++) {
            final TypeVariable<?> typeParameter = typeParameters[i];
            final Type actualTypeArgument = actualTypeArguments[i];

            typeMap.addActualType(typeParameter, actualTypeArgument);
        }
    }

    private static Type[] wrapTypeParameters(final Type[] parameters, final TypeMap typeMap) {
        final Type[] wrappedParameters = new Type[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            wrappedParameters[i] = wrapTypeAndReplaceTypeVariables(parameters[i], typeMap);
        }

        return wrappedParameters;
    }
}
