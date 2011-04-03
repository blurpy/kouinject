
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
import java.util.Set;

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
        assertEquals(0, typeMap.size());

        final Type actualType = typeMap.getActualType(mock(TypeVariable.class));
        assertNull(actualType);
    }

    @Test
    public void getActualTypeShouldReturnValueWhenKeyExists() {
        final TypeVariable<?> typeVariable = createTypeVariableMock("type variable one");
        final Type actualType = createTypeMock("type one");

        typeMap.addActualType(typeVariable, actualType);

        final Type value = typeMap.getActualType(typeVariable);
        assertSame(actualType, value);
        assertEquals(1, typeMap.size());
    }

    @Test
    public void getActualTypeShouldHandleSeveralKeyValuePairs() {
        final TypeVariable<?> typeVariable1 = createTypeVariableMock("type variable one");
        final Type actualType1 = createTypeMock("type one");
        typeMap.addActualType(typeVariable1, actualType1);

        final Type value1 = typeMap.getActualType(typeVariable1);
        assertSame(actualType1, value1);

        final TypeVariable<?> typeVariable2 = createTypeVariableMock("type variable two");
        final Type actualType2 = createTypeMock("type two");
        typeMap.addActualType(typeVariable2, actualType2);

        final Type value2 = typeMap.getActualType(typeVariable2);
        assertSame(actualType2, value2);

        final TypeVariable<?> typeVariable3 = createTypeVariableMock("type variable three");
        final Type actualType3 = createTypeMock("type three");
        typeMap.addActualType(typeVariable3, actualType3);

        final Type value3 = typeMap.getActualType(typeVariable3);
        assertSame(actualType3, value3);

        assertEquals(3, typeMap.size());
    }

    @Test
    public void getKeysShouldReturnAllTheAddedKeys() {
        final TypeVariable<?> typeVariable1 = createTypeVariableMock("type variable one");
        final Type actualType1 = createTypeMock("type one");
        typeMap.addActualType(typeVariable1, actualType1);

        final TypeVariable<?> typeVariable2 = createTypeVariableMock("type variable two");
        final Type actualType2 = createTypeMock("type two");
        typeMap.addActualType(typeVariable2, actualType2);

        assertEquals(2, typeMap.size());

        final Set<TypeVariable<?>> keys = typeMap.getKeys();
        assertEquals(2, keys.size());

        assertTrue(keys.contains(typeVariable1));
        assertSame(actualType1, typeMap.getActualType(typeVariable1));

        assertTrue(keys.contains(typeVariable2));
        assertSame(actualType2, typeMap.getActualType(typeVariable2));
    }

    @Test
    public void addActualTypeShouldResolveExistingTypesWhenActualTypeIsTypeVariable() {
        final Type actualType = createTypeMock("type one");

        final TypeVariable<?> typeVariable1 = createTypeVariableMock("type variable one");
        typeMap.addActualType(typeVariable1, actualType);

        final TypeVariable<?> typeVariable2 = createTypeVariableMock("type variable two");
        typeMap.addActualType(typeVariable2, typeVariable1);

        final TypeVariable<?> typeVariable3 = createTypeVariableMock("type variable three");
        typeMap.addActualType(typeVariable3, typeVariable2);

        assertSame(actualType, typeMap.getActualType(typeVariable1));
        assertSame(actualType, typeMap.getActualType(typeVariable2));
        assertSame(actualType, typeMap.getActualType(typeVariable3));
    }

    private Type createTypeMock(final String name) {
        final Type type = mock(Type.class);
        when(type.toString()).thenReturn(name);

        return type;
    }

    private TypeVariable<?> createTypeVariableMock(final String name) {
        final TypeVariable<?> typeVariable = mock(TypeVariable.class);
        when(typeVariable.toString()).thenReturn(name);

        return typeVariable;
    }
}
