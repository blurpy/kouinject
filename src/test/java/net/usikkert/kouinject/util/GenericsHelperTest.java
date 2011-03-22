
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

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import net.usikkert.kouinject.TypeLiteral;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link GenericsHelper}.
 *
 * @author Christian Ihle
 */
public class GenericsHelperTest {

    private GenericsHelper genericsHelper;

    @Before
    public void setUp() {
        genericsHelper = new GenericsHelper();
    }

    @Test
    public void getGenericArgumentAsType() {
        final TypeLiteral<Set<List<String>>> inputType = new TypeLiteral<Set<List<String>>>() {};
        final TypeLiteral<List<String>> expectedType = new TypeLiteral<List<String>>() {};

        final Type argumentAsType = genericsHelper.getGenericArgumentAsType(inputType.getGenericType());

        assertEquals(expectedType.getGenericType(), argumentAsType);
    }

    @Test
    public void getGenericArgumentAsClassShouldReturnRawType() {
        final TypeLiteral<Set<List<String>>> inputType = new TypeLiteral<Set<List<String>>>() {};

        final Class<?> argumentAsClass = genericsHelper.getGenericArgumentAsClass(inputType.getGenericType());
        assertEquals(List.class, argumentAsClass);
    }

    @Test
    public void getGenericArgumentAsClassShouldReturnClass() {
        final TypeLiteral<List<String>> inputType = new TypeLiteral<List<String>>() {};

        final Class<?> argumentAsClass = genericsHelper.getGenericArgumentAsClass(inputType.getGenericType());
        assertEquals(String.class, argumentAsClass);
    }

    @Test
    public void getAsClassShouldReturnRawType() {
        final TypeLiteral<List<String>> inputType = new TypeLiteral<List<String>>() {};

        final Class<?> asClass = genericsHelper.getAsClass(inputType.getGenericType());
        assertEquals(List.class, asClass);
    }

    @Test
    public void getAsClassShouldReturnClass() {
        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        final Class<?> asStringClass = genericsHelper.getAsClass(stringType.getGenericType());
        assertEquals(String.class, asStringClass);

        final Class<?> asHelloBeanClass = genericsHelper.getAsClass(HelloBean.class);
        assertEquals(HelloBean.class, asHelloBeanClass);
    }

    @Test
    public void isClass() {
        assertTrue(genericsHelper.isClass(HelloBean.class));

        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        assertTrue(genericsHelper.isClass(stringType.getGenericType()));
        assertTrue(genericsHelper.isClass(stringType.getGenericClass()));

        final TypeLiteral<List<String>> listOfStringType = new TypeLiteral<List<String>>() {};
        assertFalse(genericsHelper.isClass(listOfStringType.getGenericType()));
        assertTrue(genericsHelper.isClass(listOfStringType.getGenericClass()));
    }

    @Test
    public void isParameterizedType() {
        assertFalse(genericsHelper.isParameterizedType(HelloBean.class));

        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        assertFalse(genericsHelper.isParameterizedType(stringType.getGenericType()));
        assertFalse(genericsHelper.isParameterizedType(stringType.getGenericClass()));

        final TypeLiteral<List<String>> listOfStringType = new TypeLiteral<List<String>>() {};
        assertTrue(genericsHelper.isParameterizedType(listOfStringType.getGenericType()));
        assertFalse(genericsHelper.isParameterizedType(listOfStringType.getGenericClass()));
    }
}
