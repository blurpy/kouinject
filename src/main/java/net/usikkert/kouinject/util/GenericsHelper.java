
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
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import net.usikkert.kouinject.generics.TypeMap;
import net.usikkert.kouinject.generics.WrappedParameterizedType;
import net.usikkert.kouinject.generics.WrappedWildcardType;

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
     * @throws IllegalArgumentException If the class could not be determined.
     */
    public Class<?> getAsClass(final Type type) {
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
    public Class<?> getAsClassOrNull(final Type type) {
        Validate.notNull(type, "Type can not be null");

        if (isClass(type)) {
            return (Class<?>) type;
        }

        else if (isParameterizedType(type)) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
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

    /**
     * TODO
     *
     * Determines if the class or interface represented by this Class object is either the same as,
     * or is a superclass or superinterface of, the class or interface represented by the specified Class parameter.
     *
     * A generic type A is a subtype of a generic type B if and only if the type parameters are identical
     * and A's raw type is a subtype of B's raw type.
     *
     *
     * @param thisType
     * @param thatType
     * @return
     */
    public boolean isAssignableFrom(final Type thisType, final Type thatType) {
        Validate.notNull(thisType, "This type can not be null");
        Validate.notNull(thatType, "That type can not be null");

        return isAssignableFromUsingMap(thisType, thatType, new TypeMap());
    }

    private boolean isAssignableFromUsingMap(final Type thisType, final Type thatType, final TypeMap typeMap) {
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

    private boolean isAssignableFromSuperTypes(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        return isAssignableFromSuperInterfaces(thisType, thatClass, typeMap) ||
               isAssignableFromSuperClass(thisType, thatClass, typeMap);
    }

    private boolean isAssignableFromSuperInterfaces(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        final Type[] genericInterfaces = thatClass.getGenericInterfaces();

        for (final Type genericInterface : genericInterfaces) {
            if (isAssignableFromUsingMap(thisType, genericInterface, typeMap)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAssignableFromSuperClass(final Type thisType, final Class<?> thatClass, final TypeMap typeMap) {
        final Type genericSuperclass = thatClass.getGenericSuperclass();
        return genericSuperclass != null && isAssignableFromUsingMap(thisType, genericSuperclass, typeMap);
    }

    private boolean typesHaveTheSameParameters(final Type thisType, final Type thatType, final TypeMap typeMap) {
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

    private Type[] getGenericArgumentsAsType(final Type type) {
        final ParameterizedType parameterizedType = getAsParameterizedType(type);
        return parameterizedType.getActualTypeArguments();
    }

    private boolean typesHaveTheSameParameter(final Type thisArgument, final Type thatArgument, final TypeMap typeMap) {
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

    public boolean isTypeVariable(final Type type) {
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
    public boolean isWildcard(final Type type) {
        return type instanceof WildcardType;
    }

    private boolean isAssignableFromWildcard(final WildcardType thisWildcard, final Type thatType, final TypeMap typeMap) {
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

    private boolean wildcardsAreAssignable(final WildcardType thisWildcard, final WildcardType thatWildcard,
                                           final TypeMap typeMap) {
        final Type[] thisWildcardUpperBounds = thisWildcard.getUpperBounds();
        final Type[] thisWildcardLowerBounds = thisWildcard.getLowerBounds();

        final Type[] thatWildcardUpperBounds = thatWildcard.getUpperBounds();
        final Type[] thatWildcardLowerBounds = thatWildcard.getLowerBounds();

        if (thisWildcardLowerBounds.length != thatWildcardLowerBounds.length ||
            thisWildcardUpperBounds.length != thatWildcardUpperBounds.length) {
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

    private ParameterizedType getAsParameterizedType(final Type type) {
        if (isClass(type)) {
            throw new IllegalArgumentException("Generic type <T> is required: " + type);
        }

        else if (isParameterizedType(type)) {
            return (ParameterizedType) type;
        }

        throw new IllegalArgumentException("Unsupported generic type: " + type);
    }

    public TypeMap mapTypeVariablesToActualTypes(final Class<?> aClass) {
        final TypeMap typeMap = new TypeMap();
        mapTypeVariablesToActualTypes(aClass, typeMap);

        return typeMap;
    }

    private void mapTypeVariablesToActualTypes(final Type type, final TypeMap typeMap) {
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

    /**
     * Type variables can be "passed on" between several layers of interfaces or superclasses,
     * and that will lead to a type parameter being a type variable instead. This
     * map will keep a reference to all the type variables and their actual types, so
     * that they can be resolved later when it's necessary to match type parameters.
     *
     * TODO example
     */
    private void mapTypeVariablesToActualTypes(final Type thatType, final Class<?> thatClass, final TypeMap typeMap) {
        final ParameterizedType thatParameterizedType = getAsParameterizedType(thatType);
        final Type[] actualTypeArguments = thatParameterizedType.getActualTypeArguments();
        final TypeVariable<?>[] typeParameters = thatClass.getTypeParameters();

        for (int i = 0; i < typeParameters.length; i++) {
            final TypeVariable<?> typeParameter = typeParameters[i];
            final Type actualTypeArgument = actualTypeArguments[i];

            typeMap.addActualType(typeParameter, actualTypeArgument);
        }
    }

    public Type wrapTypeAndReplaceTypeVariables(final Type type, final TypeMap typeMap) {
        if (isParameterizedType(type)) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
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

    private Type[] wrapTypeParameters(final Type[] parameters, final TypeMap typeMap) {
        final Type[] wrappedParameters = new Type[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            wrappedParameters[i] = wrapTypeAndReplaceTypeVariables(parameters[i], typeMap);
        }

        return wrappedParameters;
    }
}
