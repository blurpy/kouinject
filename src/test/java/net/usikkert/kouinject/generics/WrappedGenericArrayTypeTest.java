
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

import java.lang.reflect.Type;

import org.junit.Test;

/**
 * Test of {@link WrappedGenericArrayType}.
 *
 * @author Christian Ihle
 */
public class WrappedGenericArrayTypeTest {

    private final Type oneDimensionalStringArray = new TypeLiteral<String[]>() {}.getGenericType();
    private final Type twoDimensionalStringArray = new TypeLiteral<String[][]>() {}.getGenericType();

    private final Type oneDimensionalIntegerArray = new TypeLiteral<Integer[]>() {}.getGenericType();
    private final Type twoDimensionalIntegerArray = new TypeLiteral<Integer[][]>() {}.getGenericType();

    @Test
    public void wrappedTypeWithOneDimensionShouldBeEqualToOfficialType() {
        final WrappedGenericArrayType wrappedStringArray = new WrappedGenericArrayType(String.class);

        assertTrue(GenericsHelper.isGenericArrayType(oneDimensionalStringArray));
        assertTrue(GenericsHelper.isGenericArrayType(wrappedStringArray));

        assertTrue(oneDimensionalStringArray.equals(wrappedStringArray));
        assertTrue(wrappedStringArray.equals(oneDimensionalStringArray));

        assertEquals(oneDimensionalStringArray.toString(), wrappedStringArray.toString());
        assertEquals("java.lang.String[]", wrappedStringArray.toString());

        assertFalse(wrappedStringArray.equals(twoDimensionalStringArray));
        assertFalse(wrappedStringArray.equals(oneDimensionalIntegerArray));
        assertFalse(wrappedStringArray.equals(twoDimensionalIntegerArray));
    }

    @Test
    public void wrappedTypeWithTwoDimensionsShouldBeEqualToOfficialType() {
        final WrappedGenericArrayType wrappedStringArrayDimension1 = new WrappedGenericArrayType(String.class);
        final WrappedGenericArrayType wrappedStringArrayDimension2 = new WrappedGenericArrayType(wrappedStringArrayDimension1);

        assertTrue(GenericsHelper.isGenericArrayType(twoDimensionalStringArray));
        assertTrue(GenericsHelper.isGenericArrayType(wrappedStringArrayDimension1));
        assertTrue(GenericsHelper.isGenericArrayType(wrappedStringArrayDimension2));

        assertTrue(twoDimensionalStringArray.equals(wrappedStringArrayDimension2));
        assertTrue(wrappedStringArrayDimension2.equals(twoDimensionalStringArray));

        assertEquals(twoDimensionalStringArray.toString(), wrappedStringArrayDimension2.toString());
        assertEquals("java.lang.String[][]", wrappedStringArrayDimension2.toString());

        assertFalse(wrappedStringArrayDimension2.equals(oneDimensionalStringArray));
        assertFalse(wrappedStringArrayDimension2.equals(oneDimensionalIntegerArray));
        assertFalse(wrappedStringArrayDimension2.equals(twoDimensionalIntegerArray));

        assertFalse(wrappedStringArrayDimension1.equals(wrappedStringArrayDimension2));
        assertFalse(wrappedStringArrayDimension2.equals(wrappedStringArrayDimension1));
    }
}
