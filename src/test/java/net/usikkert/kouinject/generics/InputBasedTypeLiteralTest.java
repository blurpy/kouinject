
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

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

/**
 * Test of {@link InputBasedTypeLiteral}.
 *
 * @author Christian Ihle
 */
public class InputBasedTypeLiteralTest {

    @Test
    public void typeUsedInConstructorShouldBeReturnedAsGenericTypeAndClass() {
        final TypeLiteral<Set<String>> stringSetTypeLiteral = new TypeLiteral<Set<String>>() {};
        final InputBasedTypeLiteral inputBasedTypeLiteral = new InputBasedTypeLiteral(stringSetTypeLiteral.getGenericType());

        assertSame(stringSetTypeLiteral.getGenericType(), inputBasedTypeLiteral.getGenericType());
        assertSame(stringSetTypeLiteral.getGenericClass(), inputBasedTypeLiteral.getGenericClass());
    }

    @Test
    public void equalsShouldBeCompatibleWithRegularTypeLiteral() {
        final TypeLiteral<Set<String>> stringSetTypeLiteral = new TypeLiteral<Set<String>>() {};
        final InputBasedTypeLiteral inputBasedTypeLiteral = new InputBasedTypeLiteral(stringSetTypeLiteral.getGenericType());

        assertTrue(stringSetTypeLiteral.equals(inputBasedTypeLiteral));
        assertTrue(inputBasedTypeLiteral.equals(stringSetTypeLiteral));
    }
}
