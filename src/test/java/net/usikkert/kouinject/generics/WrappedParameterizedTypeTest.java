
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

import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.AbstractDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.DualVariableInterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOne;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOnePointTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwoPointTwo;

import org.junit.Test;

/**
 * Test of {@link WrappedParameterizedType}.
 *
 * @author Christian Ihle
 */
public class WrappedParameterizedTypeTest {

    private final GenericsHelper genericsHelper = new GenericsHelper();

    private final Type originalSingleType = new TypeLiteral<AbstractDualVariableBean<VariableOne>>() {}.getGenericType();
    private final Type originalDualType = new TypeLiteral<DualVariableInterfaceBean<VariableOne, VariableTwo>>() {}.getGenericType();

    private final Type otherOriginalSingleType = new TypeLiteral<AbstractDualVariableBean<VariableOnePointTwo>>() {}.getGenericType();
    private final Type otherOriginalDualType = new TypeLiteral<DualVariableInterfaceBean<VariableOne, VariableTwoPointTwo>>() {}.getGenericType();

    @Test
    public void wrappedTypeWithOneParametersShouldBeEqualToOfficialType() {
        final Class<?> rawType = AbstractDualVariableBean.class;
        final Type[] actualTypeArguments = new Type[] { VariableOne.class };

        final WrappedParameterizedType wrappedType = new WrappedParameterizedType(rawType, actualTypeArguments);

        assertTrue(genericsHelper.isParameterizedType(originalSingleType));
        assertTrue(genericsHelper.isParameterizedType(wrappedType));
        assertTrue(originalSingleType.equals(wrappedType));
        assertTrue(wrappedType.equals(originalSingleType));

        assertEquals(originalSingleType.toString(), wrappedType.toString());
        assertEquals("net.usikkert.kouinject.testbeans.scanned.generics.typevariable.AbstractDualVariableBean" +
                     "<net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOne>",
                     wrappedType.toString());

        assertFalse(wrappedType.equals(originalDualType));
        assertFalse(wrappedType.equals(otherOriginalSingleType));
        assertFalse(wrappedType.equals(otherOriginalDualType));
    }

    @Test
    public void wrappedTypeWithTwoParametersShouldBeEqualToOfficialType() {
        final Class<?> rawType = DualVariableInterfaceBean.class;
        final Type[] actualTypeArguments = new Type[] { VariableOne.class, VariableTwo.class };

        final WrappedParameterizedType wrappedType = new WrappedParameterizedType(rawType, actualTypeArguments);

        assertTrue(genericsHelper.isParameterizedType(originalDualType));
        assertTrue(genericsHelper.isParameterizedType(wrappedType));
        assertTrue(originalDualType.equals(wrappedType));
        assertTrue(wrappedType.equals(originalDualType));

        assertEquals(originalDualType.toString(), wrappedType.toString());
        assertEquals("net.usikkert.kouinject.testbeans.scanned.generics.typevariable.DualVariableInterfaceBean" +
                     "<net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOne, " +
                     "net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwo>",
                     wrappedType.toString());

        assertFalse(wrappedType.equals(originalSingleType));
        assertFalse(wrappedType.equals(otherOriginalSingleType));
        assertFalse(wrappedType.equals(otherOriginalDualType));
    }
}
