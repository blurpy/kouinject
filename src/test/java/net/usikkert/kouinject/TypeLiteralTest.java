
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

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @Test
    public void getTypeShouldReturnClassWhenNotUsedWithGenericSubType() {
        final TypeLiteral<String> typeLiteral = new TypeLiteral<String>() {};

        final Type type = typeLiteral.getType();
        assertEquals(String.class, type);
    }

    @Test
    public void getTypeShouldReturnCorrectGenericSubType() {
        final TypeLiteral<List<String>> typeLiteral = new TypeLiteral<List<String>>() {};

        // List
        final Type type = typeLiteral.getType();
        assertTrue(type instanceof ParameterizedType);
        final ParameterizedType listType = (ParameterizedType) type;
        assertEquals(List.class, listType.getRawType());

        // String
        final Type[] ListTypeArguments = listType.getActualTypeArguments();
        assertEquals(1, ListTypeArguments.length);
        assertEquals(String.class, ListTypeArguments[0]);
    }

    @Test
    public void getTypeShouldReturnCorrectGenericSubTypeEvenWithNesting() {
        final TypeLiteral<Collection<Set<HelloBean>>> typeLiteral = new TypeLiteral<Collection<Set<HelloBean>>>() {};

        // Collection
        final Type type = typeLiteral.getType();
        assertTrue(type instanceof ParameterizedType);
        final ParameterizedType collectionType = (ParameterizedType) type;
        assertEquals(Collection.class, collectionType.getRawType());

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
}
