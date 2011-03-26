
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.usikkert.kouinject.TypeLiteral;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.FirstStartThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StartThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.ThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.MiddleBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.SuperBean;

import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public void isAssignableFromShouldBeTrueForTheSameClass() {
//        final String thatString = null;
//        final String thisString = thatString; // ok
        assertTrue(genericsHelper.isAssignableFrom(String.class, String.class));
        assertTrue(String.class.isAssignableFrom(String.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubTypeClass() {
//        final String thatString = null;
//        final Object thisObject = thatString; // ok
        assertTrue(genericsHelper.isAssignableFrom(Object.class, String.class));
        assertTrue(Object.class.isAssignableFrom(String.class));

//        final Object thatObject = null;
//        final String thisString = thatObject; // compile error
        assertFalse(genericsHelper.isAssignableFrom(String.class, Object.class));
        assertFalse(String.class.isAssignableFrom(Object.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForTheSameType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List<String> thisList = thatList; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<ArrayList<String>>() {}.getGenericType();

//        final ArrayList<String> thatList = null;
//        final List<String> thisList = thatList; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));

//        final List<String> thatList = null;
//        final ArrayList<String> thisList = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForTheDifferentSubType() {
        final Type thisType = new TypeLiteral<List<Integer>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List<Integer> thisList = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));

//        final List<Integer> thatList = null;
//        final List<String> thisList = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForTheDifferentTypeAndSameSubType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<String>>() {}.getGenericType();

//        final Set<String> thatSet = null;
//        final List<String> thisList = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));

//        final List<String> thatList = null;
//        final Set<String> thisSet = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSameTypeWithAndWithoutSubType1() {
        final Type type = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List thisList = thatList; // ok
        assertTrue(genericsHelper.isAssignableFrom(type, List.class));

//        final List thatList = null;
//        final List<String> thisList = thatList; // ok, but unchecked assignment
        assertTrue(genericsHelper.isAssignableFrom(List.class, type));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSameTypeWithAndWithoutSubType2() {
        final Type type = new TypeLiteral<ArrayList<String>>() {}.getGenericType();

//        final List thatList = null;
//        final ArrayList<String> thisList = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(type, List.class));

//        final ArrayList<String> thatList = null;
//        final List thisList = thatList; // ok
        assertTrue(genericsHelper.isAssignableFrom(List.class, type));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSameTypeWithAndWithoutSubType3() {
        final Type type = new TypeLiteral<List<String>>() {}.getGenericType();

//        final ArrayList thatList = null;
//        final List<String> thisList = thatList; // ok, but unchecked assignment
        assertTrue(genericsHelper.isAssignableFrom(type, ArrayList.class));

//        final List<String> thatList = null;
//        final ArrayList thisList = thatList; // compile error
        assertFalse(genericsHelper.isAssignableFrom(ArrayList.class, type));
    }

    @Test
    @Ignore("Not implemented")
    public void isAssignableFromShouldBeTrueForWildcardWithExtendsAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? extends SuperBean> thisSet = thatSet; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? extends SuperBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForWildcardWithExtendsAndInvertedInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends ChildBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? extends ChildBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? extends ChildBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    @Ignore("Not implemented")
    public void isAssignableFromShouldBeTrueForWildcardWithSuperAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? super ChildBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? super ChildBean> thisSet = thatSet; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? super ChildBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForWildcardWithSuperAndInvertedInheritance() {
        final Type thisType = new TypeLiteral<Set<? super SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? super SuperBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? super SuperBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAllParametersAreEqual() {
        final Type thisType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();

        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent1() {
        final Type thisType = new TypeLiteral<Map<String, Long>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();

        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent2() {
        final Type thisType = new TypeLiteral<Map<Long, String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Integer, String>>() {}.getGenericType();

        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent3() {
        final Type thisType = new TypeLiteral<Map<Long, String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Long>>() {}.getGenericType();

        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenSeveralLayersOfNestingAreTheSame() {
        final Type thisType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<HelloBean>>>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<HelloBean>>>>() {}.getGenericType();

        assertTrue(genericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfSeveralLayersOfNestingAreDifferent() {
        final Type thisType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<ChildBean>>>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<MiddleBean>>>>() {}.getGenericType();

        assertFalse(genericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(genericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    @Ignore("Not implemented")
    public void isAssignableFromShouldBeTrueWhenClassImplementsCorrectGenericInterface() {
        final Type thisType = new TypeLiteral<ThingListenerBean<StopThing>>() {}.getGenericType();

//        final StopThingListenerBean thatBean = null;
//        final ThingListenerBean<StopThing> thisBean = thatBean; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, StopThingListenerBean.class));

//        final ThingListenerBean<StopThing> thatBean = null;
//        final StopThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(genericsHelper.isAssignableFrom(StopThingListenerBean.class, thisType));
    }

    @Test
    @Ignore("Not implemented")
    public void isAssignableFromShouldBeTrueWhenClassExtendsAbstractClassImplementingCorrectGenericInterface() {
        final Type thisType = new TypeLiteral<ThingListenerBean<StartThing>>() {}.getGenericType();

//        final FirstStartThingListenerBean thatBean = null;
//        final ThingListenerBean<StartThing> thisBean = thatBean; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, FirstStartThingListenerBean.class));

//        final ThingListenerBean<StartThing> thatBean = null;
//        final FirstStartThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(genericsHelper.isAssignableFrom(FirstStartThingListenerBean.class, thisType));
    }

    @Test
    @Ignore("Not implemented")
    public void isAssignableFromShouldBeTrueWhenClassExtendsAbstractClassWithCorrectGenericParameter() {
        final Type thisType = new TypeLiteral<ThingListenerBean<MiddleThing>>() {}.getGenericType();

//        final MiddleThingListenerBean thatBean = null;
//        final ThingListenerBean<MiddleThing> thisBean = thatBean; // ok
        assertTrue(genericsHelper.isAssignableFrom(thisType, MiddleThingListenerBean.class));

//        final ThingListenerBean<MiddleThing> thatBean = null;
//        final MiddleThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(genericsHelper.isAssignableFrom(MiddleThingListenerBean.class, thisType));
    }
}
