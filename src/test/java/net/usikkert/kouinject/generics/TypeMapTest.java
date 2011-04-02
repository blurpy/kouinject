
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
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link TypeMap}.
 *
 * @author Christian Ihle
 */
public class TypeMapTest {

    private TypeMap typeMap;

    @Before
    public void setUp() {
        typeMap = new TypeMap();
    }

    @Test
    public void getActualTypeShouldReturnNullIfKeyDoesNotExist() {
        final Type actualType = typeMap.getActualType(mock(TypeVariable.class));
        assertNull(actualType);
    }

    @Test
    public void getActualTypeShouldReturnValueWhenKeyExists() {
        final TypeVariable<?> typeVariable = mock(TypeVariable.class);
        final Type actualType = mock(Type.class);

        typeMap.addActualType(typeVariable, actualType);

        final Type value = typeMap.getActualType(typeVariable);
        assertSame(actualType, value);
    }

    @Test
    public void getActualTypeShouldHandleSeveralKeyValuePairs() {
        final TypeVariable<?> typeVariable1 = mock(TypeVariable.class);
        final Type actualType1 = mock(Type.class);
        typeMap.addActualType(typeVariable1, actualType1);

        final Type value1 = typeMap.getActualType(typeVariable1);
        assertSame(actualType1, value1);

        final TypeVariable<?> typeVariable2 = mock(TypeVariable.class);
        final Type actualType2 = mock(Type.class);
        typeMap.addActualType(typeVariable2, actualType2);

        final Type value2 = typeMap.getActualType(typeVariable2);
        assertSame(actualType2, value2);

        final TypeVariable<?> typeVariable3 = mock(TypeVariable.class);
        final Type actualType3 = mock(Type.class);
        typeMap.addActualType(typeVariable3, actualType3);

        final Type value3 = typeMap.getActualType(typeVariable3);
        assertSame(actualType3, value3);
    }
}
