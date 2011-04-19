
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
import java.util.List;

import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Box;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;

import org.junit.Test;

/**
 * Test {@link WrappedWildcardType}.
 *
 * @author Christian Ihle
 */
public class WrappedWildcardTypeTest {

    private final Type listOfWildcard = new TypeLiteral<List<?>>() {}.getGenericType();
    private final Type listOfWildcardWithExtends = new TypeLiteral<List<? extends Integer>>() {}.getGenericType();
    private final Type listOfWildcardWithSuper = new TypeLiteral<List<? super Long>>() {}.getGenericType();

    private final Type listOfWildcardWithOtherExtends = new TypeLiteral<List<? extends Long>>() {}.getGenericType();
    private final Type listOfWildcardWithOtherSuper = new TypeLiteral<List<? super Integer>>() {}.getGenericType();

    private final Type listOfWildcardWithExtendsType = new TypeLiteral<List<? extends Box<Liquid>>>() {}.getGenericType();
    private final Type listOfWildcardWithSuperType = new TypeLiteral<List<? super Box<Liquid>>>() {}.getGenericType();

    @Test
    public void wrappedWildcardWithNoBoundsShouldBeEqualToOfficialWildcard() {
        final Type[] upperBounds = { Object.class }; // Seems like upper bound with Object is the same as no bounds
        final Type[] lowerBounds = new Type[0];

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);
        final Type wildcard = GenericsHelper.getGenericArgumentAsType(listOfWildcard);

        assertTrue(GenericsHelper.isWildcard(wildcard));
        assertTrue(GenericsHelper.isWildcard(wrappedWildcard));
        assertTrue(wildcard.equals(wrappedWildcard));
        assertTrue(wrappedWildcard.equals(wildcard));

        assertEquals(wildcard.toString(), wrappedWildcard.toString());
        assertEquals("?", wrappedWildcard.toString());

        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuperType)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtendsType)));
    }

    @Test
    public void wrappedWildcardWithUpperBoundsShouldBeEqualToOfficialWildcard() {
        final Type[] upperBounds = { Integer.class };
        final Type[] lowerBounds = new Type[0];

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);
        final Type wildcard = GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtends);

        assertTrue(GenericsHelper.isWildcard(wildcard));
        assertTrue(wildcard.equals(wrappedWildcard));
        assertTrue(wrappedWildcard.equals(wildcard));

        assertEquals(wildcard.toString(), wrappedWildcard.toString());
        assertEquals("? extends java.lang.Integer", wrappedWildcard.toString());

        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcard)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuperType)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtendsType)));
    }

    @Test
    public void wrappedWildcardWithLowerBoundsShouldBeEqualToOfficialWildcard() {
        final Type[] upperBounds = { Object.class }; // Seems like upper bound with Object is the same as no bounds
        final Type[] lowerBounds = { Long.class };

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);
        final Type wildcard = GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuper);

        assertTrue(GenericsHelper.isWildcard(wildcard));
        assertTrue(wildcard.equals(wrappedWildcard));
        assertTrue(wrappedWildcard.equals(wildcard));

        assertEquals(wildcard.toString(), wrappedWildcard.toString());
        assertEquals("? super java.lang.Long", wrappedWildcard.toString());

        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcard)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuperType)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtendsType)));
    }

    @Test
    public void wrappedWildcardWithUpperBoundsAsTypeShouldBeEqualToOfficialWildcard() {
        final TypeLiteral<Box<Liquid>> boxTypeLiteral = new TypeLiteral<Box<Liquid>>() {};

        final Type[] upperBounds = { boxTypeLiteral.getGenericType() };
        final Type[] lowerBounds = new Type[0];

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);
        final Type wildcard = GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtendsType);

        assertTrue(GenericsHelper.isWildcard(wildcard));
        assertTrue(wildcard.equals(wrappedWildcard));
        assertTrue(wrappedWildcard.equals(wildcard));

        assertEquals(wildcard.toString(), wrappedWildcard.toString());
        assertEquals("? extends net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Box" +
                "<net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid>",
                wrappedWildcard.toString());

        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcard)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuperType)));
    }

    @Test
    public void wrappedWildcardWithLowerBoundsAsTypeShouldBeEqualToOfficialWildcard() {
        final TypeLiteral<Box<Liquid>> boxTypeLiteral = new TypeLiteral<Box<Liquid>>() {};

        final Type[] upperBounds = { Object.class }; // Seems like upper bound with Object is the same as no bounds
        final Type[] lowerBounds = { boxTypeLiteral.getGenericType() };

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);
        final Type wildcard = GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuperType);

        assertTrue(GenericsHelper.isWildcard(wildcard));
        assertTrue(wildcard.equals(wrappedWildcard));
        assertTrue(wrappedWildcard.equals(wildcard));

        assertEquals(wildcard.toString(), wrappedWildcard.toString());
        assertEquals("? super net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Box" +
                "<net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid>",
                wrappedWildcard.toString());

        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcard)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherExtends)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithOtherSuper)));
        assertFalse(wrappedWildcard.equals(GenericsHelper.getGenericArgumentAsType(listOfWildcardWithExtendsType)));
    }

    @Test
    public void sameInstanceShouldBeEqual() {
        final Type[] upperBounds = { Object.class };
        final Type[] lowerBounds = { Long.class };

        final WrappedWildcardType wrappedWildcard = new WrappedWildcardType(upperBounds, lowerBounds);

        assertTrue(wrappedWildcard.equals(wrappedWildcard));
    }
}
