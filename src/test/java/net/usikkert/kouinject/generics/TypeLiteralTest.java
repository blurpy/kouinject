
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

import static org.junit.Assert.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.testbeans.scanned.HelloBean;

import org.junit.Test;

/**
 * Test of {@link TypeLiteral}.
 *
 * @author Christian Ihle
 */
public class TypeLiteralTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfUsedWithoutGenericType() {
        new TypeLiteral() {};
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithGenericArrays() {
        new TypeLiteral<List<String>[]>() {};
    }

    @Test
    public void getTypeShouldReturnClassWhenNotUsedWithGenericSubType() {
        final TypeLiteral<String> typeLiteral = new TypeLiteral<String>() {};

        assertEquals(String.class, typeLiteral.getGenericType());
        assertEquals(String.class, typeLiteral.getGenericClass());
    }

    @Test
    public void getTypeShouldReturnArrayClassWhenUsedWithArray() {
        final TypeLiteral<String[]> typeLiteral = new TypeLiteral<String[]>() {};

        final Type genericType = typeLiteral.getGenericType();
        assertTrue(genericType instanceof GenericArrayType);
        final GenericArrayType genericArrayType = (GenericArrayType) genericType;

        assertEquals(String[].class, GenericsHelper.getArrayClassFromGenericArray(genericArrayType));
        assertEquals(String[].class, typeLiteral.getGenericClass());
    }

    @Test
    public void getTypeShouldReturnCorrectGenericSubType() {
        final TypeLiteral<List<String>> typeLiteral = new TypeLiteral<List<String>>() {};

        // List
        final Type type = typeLiteral.getGenericType();
        assertTrue(type instanceof ParameterizedType);
        final ParameterizedType listType = (ParameterizedType) type;
        assertEquals(List.class, listType.getRawType());
        assertEquals(List.class, typeLiteral.getGenericClass());

        // String
        final Type[] ListTypeArguments = listType.getActualTypeArguments();
        assertEquals(1, ListTypeArguments.length);
        assertEquals(String.class, ListTypeArguments[0]);
    }

    @Test
    public void getTypeShouldReturnCorrectGenericSubTypeEvenWithNesting() {
        final TypeLiteral<Collection<Set<HelloBean>>> typeLiteral = new TypeLiteral<Collection<Set<HelloBean>>>() {};

        // Collection
        final Type type = typeLiteral.getGenericType();
        assertTrue(type instanceof ParameterizedType);
        final ParameterizedType collectionType = (ParameterizedType) type;
        assertEquals(Collection.class, collectionType.getRawType());
        assertEquals(Collection.class, typeLiteral.getGenericClass());

        // Set
        final Type[] collectionTypeArguments = collectionType.getActualTypeArguments();
        assertEquals(1, collectionTypeArguments.length);
        final Type setArgument = collectionTypeArguments[0];
        assertTrue(setArgument instanceof ParameterizedType);
        final ParameterizedType setType = (ParameterizedType) setArgument;
        assertEquals(Set.class, setType.getRawType());

        // HelloBean
        final Type[] setTypeArguments = setType.getActualTypeArguments();
        assertEquals(1, setTypeArguments.length);
        assertEquals(HelloBean.class, setTypeArguments[0]);
    }

    @Test
    public void equalsShouldReturnTrueForSameClassArgument() {
        final TypeLiteral<String> stringTypeLiteral1 = new TypeLiteral<String>() {};
        final TypeLiteral<String> stringTypeLiteral2 = new TypeLiteral<String>() {};

        assertTrue(stringTypeLiteral1.equals(stringTypeLiteral2));
    }

    @Test
    public void equalsShouldReturnFalseForDifferentClassArgument() {
        final TypeLiteral<String> stringTypeLiteral = new TypeLiteral<String>() {};
        final TypeLiteral<Integer> integerTypeLiteral = new TypeLiteral<Integer>() {};

        assertFalse(stringTypeLiteral.equals(integerTypeLiteral));
    }

    @Test
    public void equalsShouldReturnTrueForSameGenericArgument() {
        final TypeLiteral<Collection<Integer>> integerTypeLiteral1 = new TypeLiteral<Collection<Integer>>() {};
        final TypeLiteral<Collection<Integer>> integerTypeLiteral2 = new TypeLiteral<Collection<Integer>>() {};

        assertTrue(integerTypeLiteral1.equals(integerTypeLiteral2));
    }

    @Test
    public void equalsShouldReturnFalseForDifferentGenericArgument1() {
        final TypeLiteral<Collection<String>> stringTypeLiteral = new TypeLiteral<Collection<String>>() {};
        final TypeLiteral<Collection<Integer>> integerTypeLiteral = new TypeLiteral<Collection<Integer>>() {};

        assertFalse(stringTypeLiteral.equals(integerTypeLiteral));
    }

    @Test
    public void equalsShouldReturnFalseForDifferentGenericArgument2() {
        final TypeLiteral<String> stringTypeLiteral = new TypeLiteral<String>() {};
        final TypeLiteral<Collection<String>> collectionStringTypeLiteral = new TypeLiteral<Collection<String>>() {};

        assertFalse(stringTypeLiteral.equals(collectionStringTypeLiteral));
    }

    @Test
    public void equalsShouldReturnFalseForOtherTypes() {
        final TypeLiteral<String> stringTypeLiteral = new TypeLiteral<String>() {};

        assertFalse(stringTypeLiteral.equals(null));
        assertFalse(stringTypeLiteral.equals(String.class));
    }

    @Test
    public void shouldAcceptTypeInConstructorWhenNotUsedWithGenericSubType() {
        final TypeLiteral<String> stringTypeLiteral = new TypeLiteral<String>() {};

        final TypeLiteral typeLiteral = new TypeLiteral(stringTypeLiteral.getGenericType()) {};

        assertSame(stringTypeLiteral.getGenericType(), typeLiteral.getGenericType());
        assertSame(stringTypeLiteral.getGenericClass(), typeLiteral.getGenericClass());
        assertTrue(stringTypeLiteral.equals(typeLiteral));
    }

    @Test
    public void shouldAcceptTypeInConstructorWhenNotUsedWithGenericSubTypeAndIgnoreTypeArgument() {
        final TypeLiteral<String> stringTypeLiteral = new TypeLiteral<String>() {};

        final TypeLiteral<?> typeLiteral = new TypeLiteral<Object>(stringTypeLiteral.getGenericType()) {};

        assertSame(stringTypeLiteral.getGenericType(), typeLiteral.getGenericType());
        assertSame(stringTypeLiteral.getGenericClass(), typeLiteral.getGenericClass());
        assertTrue(stringTypeLiteral.equals(typeLiteral));
    }

    @Test
    public void shouldAcceptTypeInConstructorWhenUsedWithGenericSubType() {
        final TypeLiteral<Collection<Date>> dateTypeLiteral = new TypeLiteral<Collection<Date>>() {};

        final TypeLiteral typeLiteral = new TypeLiteral(dateTypeLiteral.getGenericType()) {};

        assertSame(dateTypeLiteral.getGenericType(), typeLiteral.getGenericType());
        assertSame(dateTypeLiteral.getGenericClass(), typeLiteral.getGenericClass());
        assertTrue(dateTypeLiteral.equals(typeLiteral));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateTypeInConstructor() {
        new TypeLiteral(null) {};
    }

    @Test
    public void shouldReturnNullForClassWhenUsedWithWildcardType() {
        final TypeLiteral<Provider<? extends HelloBean>> wildcardProvider = new TypeLiteral<Provider<? extends HelloBean>>() {};
        final Type wildcardType = GenericsHelper.getGenericArgumentAsType(wildcardProvider.getGenericType());
        assertTrue(GenericsHelper.isWildcard(wildcardType));

        final TypeLiteral<Object> typeLiteral = new TypeLiteral<Object>(wildcardType) {};
        assertEquals(wildcardType, typeLiteral.getGenericType());
        assertNull(typeLiteral.getGenericClass());
    }
}
