
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

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.testbeans.notscanned.generics.typevariable.TypeVariableChild;
import net.usikkert.kouinject.testbeans.notscanned.generics.typevariable.TypeVariableParent;
import net.usikkert.kouinject.testbeans.notscanned.generics.typevariable.TypeVariableWithNestedWildcard;
import net.usikkert.kouinject.testbeans.notscanned.generics.typevariable.TypeVariableWithNestedWildcardImpl;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Square;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Comedy;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Horror;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Movie;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.FirstStartThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StartThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.ThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.AbstractDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Box;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ConcreteDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.DualVariableInterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pepsi;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pizza;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Shoe;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ShoeBoxBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOne;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOnePointTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwoPointTwo;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.MiddleBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.SuperBean;

import org.junit.Test;

/**
 * Test of {@link GenericsHelper}.
 *
 * @author Christian Ihle
 */
public class GenericsHelperTest {

    @Test
    public void getGenericArgumentAsTypeShouldGetFirstArgument() {
        final TypeLiteral<Set<List<String>>> inputType = new TypeLiteral<Set<List<String>>>() {};
        final TypeLiteral<List<String>> expectedType = new TypeLiteral<List<String>>() {};

        final Type argumentAsType = GenericsHelper.getGenericArgumentAsType(inputType.getGenericType());

        assertEquals(expectedType.getGenericType(), argumentAsType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGenericArgumentAsTypeShouldFailIfWrongNumberOfArguments() {
        final TypeLiteral<Map<String, Integer>> inputType = new TypeLiteral<Map<String, Integer>>() {};

        GenericsHelper.getGenericArgumentAsType(inputType.getGenericType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGenericArgumentAsTypeShouldFailIfWrongType() {
        final TypeLiteral<String[]> inputType = new TypeLiteral<String[]>() {};

        GenericsHelper.getGenericArgumentAsType(inputType.getGenericType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGenericArgumentAsTypeShouldFailIfClass() {
        GenericsHelper.getGenericArgumentAsType(String.class);
    }

    @Test
    public void getGenericArgumentAsClassShouldReturnRawType() {
        final TypeLiteral<Set<List<String>>> inputType = new TypeLiteral<Set<List<String>>>() {};

        final Class<?> argumentAsClass = GenericsHelper.getGenericArgumentAsClass(inputType.getGenericType());
        assertEquals(List.class, argumentAsClass);
    }

    @Test
    public void getGenericArgumentAsClassShouldReturnClass() {
        final TypeLiteral<List<String>> inputType = new TypeLiteral<List<String>>() {};

        final Class<?> argumentAsClass = GenericsHelper.getGenericArgumentAsClass(inputType.getGenericType());
        assertEquals(String.class, argumentAsClass);
    }

    @Test
    public void getAsClassShouldReturnRawType() {
        final TypeLiteral<List<String>> inputType = new TypeLiteral<List<String>>() {};

        final Class<?> asClass = GenericsHelper.getAsClass(inputType.getGenericType());
        assertEquals(List.class, asClass);
    }

    @Test
    public void getAsClassShouldReturnClass() {
        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        final Class<?> asStringClass = GenericsHelper.getAsClass(stringType.getGenericType());
        assertEquals(String.class, asStringClass);

        final Class<?> asHelloBeanClass = GenericsHelper.getAsClass(HelloBean.class);
        assertEquals(HelloBean.class, asHelloBeanClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAsClassShouldFailIfArrayIsGeneric() {
        // Creating generic arrays are forbidden by the jvm, so wont bother adding support for getting the class
        // final Container<String>[] anArrayContainer = new Container<String>[0]; // does not compile

        final TypeLiteral<Container<String>[]> arrayContainer = new TypeLiteral<Container<String>[]>() {};
        final Type arrayType = GenericsHelper.getGenericArgumentAsType(arrayContainer.getGenericType());

        GenericsHelper.getAsClass(arrayType);
    }

    @Test
    public void getAsClassShouldGetCorrectOneDimensionalArrayClass() {
        final TypeLiteral<Provider<String[]>> providerWithArray = new TypeLiteral<Provider<String[]>>() {};
        final Type array = GenericsHelper.getGenericArgumentAsType(providerWithArray.getGenericType());

        final Class<?> asClass = GenericsHelper.getAsClass(array);
        assertEquals(String[].class, asClass);
    }

    @Test
    public void getAsClassShouldGetCorrectTwoDimensionalArrayClass() {
        final TypeLiteral<Provider<String[][]>> providerWithArray = new TypeLiteral<Provider<String[][]>>() {};
        final Type array = GenericsHelper.getGenericArgumentAsType(providerWithArray.getGenericType());

        final Class<?> asClass = GenericsHelper.getAsClass(array);
        assertEquals(String[][].class, asClass);
    }

    @Test
    public void getAsClassShouldGetCorrectThreeDimensionalArrayClass() {
        final TypeLiteral<Provider<String[][][]>> providerWithArray = new TypeLiteral<Provider<String[][][]>>() {};
        final Type array = GenericsHelper.getGenericArgumentAsType(providerWithArray.getGenericType());

        final Class<?> asClass = GenericsHelper.getAsClass(array);
        assertEquals(String[][][].class, asClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAsClassShouldFailIfWildcard() {
        final TypeLiteral<Container<?>> wildcardContainer = new TypeLiteral<Container<?>>() {};
        final Type wildcardType = GenericsHelper.getGenericArgumentAsType(wildcardContainer.getGenericType());

        GenericsHelper.getAsClass(wildcardType);
    }

    @Test
    public void getAsClassOrNullShouldReturnClass() {
        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        final Class<?> asStringClass = GenericsHelper.getAsClassOrNull(stringType.getGenericType());
        assertEquals(String.class, asStringClass);

        final Class<?> asHelloBeanClass = GenericsHelper.getAsClassOrNull(HelloBean.class);
        assertEquals(HelloBean.class, asHelloBeanClass);
    }

    @Test
    public void getAsClassOrNullShouldReturnNullForWildcards() {
        final TypeLiteral<Container<?>> wildcardContainer = new TypeLiteral<Container<?>>() {};
        final Type wildcardType = GenericsHelper.getGenericArgumentAsType(wildcardContainer.getGenericType());

        final Class<?> classOrNull = GenericsHelper.getAsClassOrNull(wildcardType);
        assertNull(classOrNull);
    }

    @Test
    public void getClassFromGenericArrayShouldHandleOneDimension() {
        final Type genericType = new TypeLiteral<String[]>() {}.getGenericType();
        assertTrue(GenericsHelper.isGenericArrayType(genericType));

        assertEquals(String[].class, GenericsHelper.getArrayClassFromGenericArray((GenericArrayType) genericType));
    }

    @Test
    public void getClassFromGenericArrayShouldHandleTwoDimensions() {
        final Type genericType = new TypeLiteral<String[][]>() {}.getGenericType();
        assertTrue(GenericsHelper.isGenericArrayType(genericType));

        assertEquals(String[][].class, GenericsHelper.getArrayClassFromGenericArray((GenericArrayType) genericType));
    }

    @Test
    public void getClassFromGenericArrayShouldHandleThreeDimensions() {
        final Type genericType = new TypeLiteral<String[][][]>() {}.getGenericType();
        assertTrue(GenericsHelper.isGenericArrayType(genericType));

        assertEquals(String[][][].class, GenericsHelper.getArrayClassFromGenericArray((GenericArrayType) genericType));
    }

    @Test
    public void isClass() {
        assertTrue(GenericsHelper.isClass(HelloBean.class));

        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        assertTrue(GenericsHelper.isClass(stringType.getGenericType()));
        assertTrue(GenericsHelper.isClass(stringType.getGenericClass()));

        final TypeLiteral<List<String>> listOfStringType = new TypeLiteral<List<String>>() {};
        assertFalse(GenericsHelper.isClass(listOfStringType.getGenericType()));
        assertTrue(GenericsHelper.isClass(listOfStringType.getGenericClass()));
    }

    @Test
    public void isParameterizedType() {
        assertFalse(GenericsHelper.isParameterizedType(HelloBean.class));

        final TypeLiteral<String> stringType = new TypeLiteral<String>() {};
        assertFalse(GenericsHelper.isParameterizedType(stringType.getGenericType()));
        assertFalse(GenericsHelper.isParameterizedType(stringType.getGenericClass()));

        final TypeLiteral<List<String>> listOfStringType = new TypeLiteral<List<String>>() {};
        assertTrue(GenericsHelper.isParameterizedType(listOfStringType.getGenericType()));
        assertFalse(GenericsHelper.isParameterizedType(listOfStringType.getGenericClass()));
    }

    @Test
    public void isWildcard() {
        assertFalse(GenericsHelper.isWildcard(HelloBean.class));

        final TypeLiteral<Container<?>> wildcardContainer = new TypeLiteral<Container<?>>() {};
        assertFalse(GenericsHelper.isWildcard(wildcardContainer.getGenericType()));
        assertFalse(GenericsHelper.isWildcard(wildcardContainer.getGenericClass()));

        final Type wildcardType = GenericsHelper.getGenericArgumentAsType(wildcardContainer.getGenericType());
        assertTrue(GenericsHelper.isWildcard(wildcardType));
    }

    @Test
    public void isGenericArrayType() {
        assertFalse(GenericsHelper.isGenericArrayType(HelloBean.class));

        final TypeLiteral<Container<String[]>> arrayContainer = new TypeLiteral<Container<String[]>>() {};
        assertFalse(GenericsHelper.isGenericArrayType(arrayContainer.getGenericType()));
        assertFalse(GenericsHelper.isGenericArrayType(arrayContainer.getGenericClass()));

        final Type arrayType = GenericsHelper.getGenericArgumentAsType(arrayContainer.getGenericType());
        assertTrue(GenericsHelper.isGenericArrayType(arrayType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForTheSameClass() {
//        final String thatString = null;
//        final String thisString = thatString; // ok
        assertTrue(GenericsHelper.isAssignableFrom(String.class, String.class));
        assertTrue(String.class.isAssignableFrom(String.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForTheSameGenericClassWithoutTypeArguments() {
//        final List thatBean = null;
//        final List thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(List.class, List.class));
        assertTrue(List.class.isAssignableFrom(List.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubTypeClass() {
//        final String thatString = null;
//        final Object thisObject = thatString; // ok
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, String.class));
        assertTrue(Object.class.isAssignableFrom(String.class));

//        final Object thatObject = null;
//        final String thisString = thatObject; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(String.class, Object.class));
        assertFalse(String.class.isAssignableFrom(Object.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubTypeGenericClassWhenUsedRaw() {
//        final List thatList = null;
//        final Object thisObject = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, List.class));
        assertTrue(Object.class.isAssignableFrom(List.class));

//        final Object thatObject = null;
//        final List thisList = thatObject; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(List.class, Object.class));
        assertFalse(List.class.isAssignableFrom(Object.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubTypeGenericClassesWhenBothUsedRaw() {
//        final ArrayList thatList = null;
//        final List thisList = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(List.class, ArrayList.class));
        assertTrue(List.class.isAssignableFrom(ArrayList.class));

//        final List thatList = null;
//        final ArrayList thisList = thatList; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ArrayList.class, List.class));
        assertFalse(ArrayList.class.isAssignableFrom(List.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForTheSameType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List<String> thisList = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForSubType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<ArrayList<String>>() {}.getGenericType();

//        final ArrayList<String> thatList = null;
//        final List<String> thisList = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final List<String> thatList = null;
//        final ArrayList<String> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForTheDifferentSubType() {
        final Type thisType = new TypeLiteral<List<Integer>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List<Integer> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final List<Integer> thatList = null;
//        final List<String> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForSubTypeWithInheritance() {
        final Type thisType = new TypeLiteral<List<Object>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List<Object> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final List<Object> thatList = null;
//        final List<String> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForTheDifferentTypeAndSameSubType() {
        final Type thisType = new TypeLiteral<List<String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<String>>() {}.getGenericType();

//        final Set<String> thatSet = null;
//        final List<String> thisList = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final List<String> thatList = null;
//        final Set<String> thisSet = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForAssigningSameGenericTypeToRawType() {
        final Type type = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatList = null;
//        final List thisList = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(List.class, type));
    }

    @Test
    public void isAssignableFromShouldBeTrueForAssigningGenericTypeToRawSubType() {
        final Type type = new TypeLiteral<ArrayList<String>>() {}.getGenericType();

//        final ArrayList<String> thatList = null;
//        final List thisList = thatList; // ok
        assertTrue(GenericsHelper.isAssignableFrom(List.class, type));

//        final List thatList = null;
//        final ArrayList<String> thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(type, List.class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForUncheckedCastFromRawTypeToSameGenericType() {
        final Type type = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List thatList = null;
//        final List<String> thisList = thatList; // ok, but unchecked assignment
        // Valid, but not type safe. Not allowed.
        assertFalse(GenericsHelper.isAssignableFrom(type, List.class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForCastFromRawTypeToUnboundedGenericType() {
        final Type thisType = new TypeLiteral<List<?>>() {}.getGenericType();

//        final List thatBean = null;
//        final List<?> thisBean = thatBean; // ok
        // Type safe and valid, but not allowed because of the limitations added for the above test
        assertFalse(GenericsHelper.isAssignableFrom(thisType, List.class));

//        final List<?> thatBean = null;
//        final List thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(List.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForUncheckedCastFromRawTypeToGenericSubType() {
        final Type type = new TypeLiteral<List<String>>() {}.getGenericType();

//        final ArrayList thatList = null;
//        final List<String> thisList = thatList; // ok, but unchecked assignment
        assertFalse(GenericsHelper.isAssignableFrom(type, ArrayList.class));

//        final List<String> thatList = null;
//        final ArrayList thisList = thatList; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(ArrayList.class, type));
    }

    @Test
    public void isAssignableFromShouldBeTrueForWildcardWithExtendsAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? extends SuperBean> thisSet = thatSet; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? extends SuperBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForDirectWildcardWithExtendsAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();
        final Type thisWildcard = GenericsHelper.getGenericArgumentAsType(thisType);
        final Type thatType = new TypeLiteral<MiddleBean>() {}.getGenericType();

        assertTrue(GenericsHelper.isAssignableFrom(thisWildcard, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisWildcard));
    }

    @Test
    public void isAssignableFromShouldBeFalseForWildcardWithExtendsAndInvertedInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends ChildBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? extends ChildBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? extends ChildBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForWildcardWithSuperAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? super ChildBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? super ChildBean> thisSet = thatSet; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? super ChildBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForDirectWildcardWithSuperAndCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? super ChildBean>>() {}.getGenericType();
        final Type thisWildcard = GenericsHelper.getGenericArgumentAsType(thisType);
        final Type thatType = new TypeLiteral<MiddleBean>() {}.getGenericType();

        assertTrue(GenericsHelper.isAssignableFrom(thisWildcard, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisWildcard));
    }

    @Test
    public void isAssignableFromShouldBeFalseForWildcardWithSuperAndInvertedInheritance() {
        final Type thisType = new TypeLiteral<Set<? super SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();

//        final Set<MiddleBean> thatSet = null;
//        final Set<? super SuperBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? super SuperBean> thatSet = null;
//        final Set<MiddleBean> thisSet = thatSet; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningWildcardWithSuperToTheSameWildcard() {
        final Type thisType = new TypeLiteral<Set<? super SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<? super SuperBean>>() {}.getGenericType();

//        final Set<? super SuperBean> thatBean = null;
//        final Set<? super SuperBean> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningWildcardWithExtendsToTheSameWildcard() {
        final Type thisType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();

//        final Set<? extends SuperBean> thatBean = null;
//        final Set<? extends SuperBean> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningUnboundWildcardToTheSameWildcard() {
        final Type thisType = new TypeLiteral<Set<?>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<?>>() {}.getGenericType();

//        final Set<?> thatBean = null;
//        final Set<?> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenAssigningWildcardWithSuperToWildcardWithExtends() {
        final Type thisType1 = new TypeLiteral<Set<? super SuperBean>>() {}.getGenericType();
        final Type thisType2 = new TypeLiteral<Set<? super MiddleBean>>() {}.getGenericType();
        final Type thisType3 = new TypeLiteral<Set<? super ChildBean>>() {}.getGenericType();

        final Type thatType1 = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();
        final Type thatType2 = new TypeLiteral<Set<? extends MiddleBean>>() {}.getGenericType();
        final Type thatType3 = new TypeLiteral<Set<? extends ChildBean>>() {}.getGenericType();

        // compiler error on all of them
        assertFalse(GenericsHelper.isAssignableFrom(thisType1, thatType1));
        assertFalse(GenericsHelper.isAssignableFrom(thisType1, thatType2));
        assertFalse(GenericsHelper.isAssignableFrom(thisType1, thatType3));
        assertFalse(GenericsHelper.isAssignableFrom(thisType2, thatType1));
        assertFalse(GenericsHelper.isAssignableFrom(thisType2, thatType2));
        assertFalse(GenericsHelper.isAssignableFrom(thisType2, thatType3));
        assertFalse(GenericsHelper.isAssignableFrom(thisType3, thatType1));
        assertFalse(GenericsHelper.isAssignableFrom(thisType3, thatType2));
        assertFalse(GenericsHelper.isAssignableFrom(thisType3, thatType3));

        assertFalse(GenericsHelper.isAssignableFrom(thatType1, thisType1));
        assertFalse(GenericsHelper.isAssignableFrom(thatType1, thisType2));
        assertFalse(GenericsHelper.isAssignableFrom(thatType1, thisType3));
        assertFalse(GenericsHelper.isAssignableFrom(thatType2, thisType1));
        assertFalse(GenericsHelper.isAssignableFrom(thatType2, thisType2));
        assertFalse(GenericsHelper.isAssignableFrom(thatType2, thisType3));
        assertFalse(GenericsHelper.isAssignableFrom(thatType3, thisType1));
        assertFalse(GenericsHelper.isAssignableFrom(thatType3, thisType2));
        assertFalse(GenericsHelper.isAssignableFrom(thatType3, thisType3));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningWildcardWithExtendsWithCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? extends MiddleBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<? extends SuperBean>>() {}.getGenericType();

//        final Set<? extends SuperBean> thatBean = null;
//        final Set<? extends MiddleBean> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? extends MiddleBean> thatBean = null;
//        final Set<? extends SuperBean> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningWildcardWithSuperWithCorrectInheritance() {
        final Type thisType = new TypeLiteral<Set<? super MiddleBean>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Set<? super ChildBean>>() {}.getGenericType();

//        final Set<? super ChildBean> thatBean = null;
//        final Set<? super MiddleBean> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<? super MiddleBean> thatBean = null;
//        final Set<? super ChildBean> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenAssigningWildcardWithSuperToGenericParameter() {
        final Type thatType = new TypeLiteral<Set<? super MiddleBean>>() {}.getGenericType();
        final Type thisType1 = new TypeLiteral<Set<ChildBean>>() {}.getGenericType();
        final Type thisType2 = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();
        final Type thisType3 = new TypeLiteral<Set<SuperBean>>() {}.getGenericType();

//        final Set<? super MiddleBean> thatBean = null;
//        final Set<ChildBean> thisBean1 = thatBean; // compiler error
//        final Set<MiddleBean> thisBean2 = thatBean; // compiler error
//        final Set<SuperBean> thisBean3 = thatBean; // compiler error

        assertFalse(GenericsHelper.isAssignableFrom(thisType1, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thisType2, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thisType3, thatType));

//        final Set<ChildBean> thisBean1 = null;
//        final Set<MiddleBean> thisBean2 = null;
//        final Set<SuperBean> thisBean3 = null;
//        final Set<? super MiddleBean> thatBean = thisBean1; // compiler error
//        final Set<? super MiddleBean> thatBean = thisBean2; // ok
//        final Set<? super MiddleBean> thatBean = thisBean3; // ok

        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType1));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType2));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType3));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenAssigningWildcardWithExtendsToGenericParameter() {
        final Type thatType = new TypeLiteral<Set<? extends MiddleBean>>() {}.getGenericType();
        final Type thisType1 = new TypeLiteral<Set<ChildBean>>() {}.getGenericType();
        final Type thisType2 = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();
        final Type thisType3 = new TypeLiteral<Set<SuperBean>>() {}.getGenericType();

//        final Set<? extends MiddleBean> thatBean = null;
//        final Set<ChildBean> thisBean1 = thatBean; // compiler error
//        final Set<MiddleBean> thisBean2 = thatBean; // compiler error
//        final Set<SuperBean> thisBean3 = thatBean; // compiler error

        assertFalse(GenericsHelper.isAssignableFrom(thisType1, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thisType2, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thisType3, thatType));

//        final Set<ChildBean> thisBean1 = null;
//        final Set<MiddleBean> thisBean2 = null;
//        final Set<SuperBean> thisBean3 = null;
//        final Set<? extends MiddleBean> thatBean = thisBean1; // ok
//        final Set<? extends MiddleBean> thatBean = thisBean2; // ok
//        final Set<? extends MiddleBean> thatBean = thisBean3; // compiler error

        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType1));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType2));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType3));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningABoundWildcardToAnUnboundWildcard() {
        final Type thatType = new TypeLiteral<Set<? extends MiddleBean>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Set<?>>() {}.getGenericType();

//        final Set<? extends MiddleBean> thatBean = null;
//        final Set<?> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<?> thatBean = null;
//        final Set<? extends MiddleBean> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningGenericParameterToAnUnboundWildcard() {
        final Type thatType = new TypeLiteral<Set<MiddleBean>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Set<?>>() {}.getGenericType();

//        final Set<MiddleBean> thatBean = null;
//        final Set<?> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Set<?> thatBean = null;
//        final Set<MiddleBean> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenCompatibleWildcardsAreUsedALevelTooDeep() {
        final Type thatType = new TypeLiteral<Collection<Container<? extends MiddleBean>>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Collection<Container<?>>>() {}.getGenericType();

//        final Collection<Container<? extends MiddleBean>> thatBean = null;
//        final Collection<Container<?>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Collection<Container<?>> thatBean = null;
//        final Collection<Container<? extends MiddleBean>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningAParameterWithAWildcardToAWildcard() {
        final Type thatType = new TypeLiteral<Collection<Container<? extends MiddleBean>>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Collection<?>>() {}.getGenericType();

//        final Collection<Container<? extends MiddleBean>> thatBean = null;
//        final Collection<?> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Collection<?> thatBean = null;
//        final Collection<Container<? extends MiddleBean>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAllParametersAreEqual() {
        final Type thisType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();

        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent1() {
        final Type thisType = new TypeLiteral<Map<String, Long>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Integer>>() {}.getGenericType();

        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent2() {
        final Type thisType = new TypeLiteral<Map<Long, String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Integer, String>>() {}.getGenericType();

        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfTheParametersAreDifferent3() {
        final Type thisType = new TypeLiteral<Map<Long, String>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<String, Long>>() {}.getGenericType();

        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenSeveralLayersOfNestingAreTheSame() {
        final Type thisType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<HelloBean>>>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<HelloBean>>>>() {}.getGenericType();

        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertTrue(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenOneOfSeveralLayersOfNestingAreDifferent() {
        final Type thisType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<ChildBean>>>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Map<Map<String, Integer>, Map<Long, Set<MiddleBean>>>>() {}.getGenericType();

        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenClassImplementsCorrectGenericInterface() {
        final Type thisType = new TypeLiteral<ThingListenerBean<StopThing>>() {}.getGenericType();

//        final StopThingListenerBean thatBean = null;
//        final ThingListenerBean<StopThing> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, StopThingListenerBean.class));

//        final ThingListenerBean<StopThing> thatBean = null;
//        final StopThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(StopThingListenerBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenClassExtendsAbstractClassImplementingCorrectGenericInterface() {
        final Type thisType = new TypeLiteral<ThingListenerBean<StartThing>>() {}.getGenericType();

//        final FirstStartThingListenerBean thatBean = null;
//        final ThingListenerBean<StartThing> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, FirstStartThingListenerBean.class));

//        final ThingListenerBean<StartThing> thatBean = null;
//        final FirstStartThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(FirstStartThingListenerBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenClassExtendsAbstractClassWithCorrectGenericParameter() {
        final Type thisType = new TypeLiteral<ThingListenerBean<MiddleThing>>() {}.getGenericType();

//        final MiddleThingListenerBean thatBean = null;
//        final ThingListenerBean<MiddleThing> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, MiddleThingListenerBean.class));

//        final ThingListenerBean<MiddleThing> thatBean = null;
//        final MiddleThingListenerBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(MiddleThingListenerBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenTypeVariablesAreInheritedAndMatch() {
        final Type thisType = new TypeLiteral<DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenOneTypeVariableIsDefinedInClassAndOneAtRuntimeAndMatch() {
        final Type thisType = new TypeLiteral<DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<AbstractDualVariableBean<VariableOnePointTwo>>() {}.getGenericType();

//        final AbstractDualVariableBean<VariableOnePointTwo> thatBean = null;
//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo> thatBean = null;
//        final AbstractDualVariableBean<VariableOnePointTwo> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenTypeVariablesAreInheritedAndFirstDontMatch() {
        final Type thisType = new TypeLiteral<DualVariableInterfaceBean<VariableOne, VariableTwo>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final DualVariableInterfaceBean<VariableOne, VariableTwo> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final DualVariableInterfaceBean<VariableOne, VariableTwo> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenTypeVariablesAreInheritedAndSecondDontMatch() {
        final Type thisType = new TypeLiteral<DualVariableInterfaceBean<VariableOnePointTwo, VariableTwoPointTwo>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwoPointTwo> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwoPointTwo> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableShouldBeTrueWhenAssigningFromClassWithTypeVariableToTypeWithUnboundWildcard() {
        final Type thisType = new TypeLiteral<AbstractDualVariableBean<?>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final AbstractDualVariableBean<?> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final AbstractDualVariableBean<?> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableShouldBeTrueWhenAssigningFromClassWithTypeVariableToTypeWithCorrectWildcard() {
        final Type thisType = new TypeLiteral<AbstractDualVariableBean<? extends VariableOne>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final AbstractDualVariableBean<? extends VariableOne> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final AbstractDualVariableBean<? extends VariableOne> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableShouldBeFalseWhenAssigningFromClassWithTypeVariableToTypeWithWrongWildcard() {
        final Type thisType = new TypeLiteral<AbstractDualVariableBean<? extends VariableTwo>>() {}.getGenericType();

//        final ConcreteDualVariableBean thatBean = null;
//        final AbstractDualVariableBean<? extends VariableTwo> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, ConcreteDualVariableBean.class));

//        final AbstractDualVariableBean<? extends VariableTwo> thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningTwoGenericClassesWithoutGenericParameters() {
//        final ConcreteDualVariableBean thatBean = null;
//        final AbstractDualVariableBean thisBean = thatBean; // ok, but not really type safe. Allowed for now.
        assertTrue(GenericsHelper.isAssignableFrom(AbstractDualVariableBean.class, ConcreteDualVariableBean.class));

//        final AbstractDualVariableBean thatBean = null;
//        final ConcreteDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(ConcreteDualVariableBean.class, AbstractDualVariableBean.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenAssigningGenericClassWithoutGenericParametersToObject() {
//        final AbstractDualVariableBean thatBean = null;
//        final Object thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, AbstractDualVariableBean.class));

//        final Object thatBean = null;
//        final AbstractDualVariableBean thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(AbstractDualVariableBean.class, Object.class));
    }

    @Test
    public void isAssignableShouldBeTrueWhenAssigningFromClassWithTypeVariableToObject() {
        final Type thatType = new TypeLiteral<List<String>>() {}.getGenericType();

//        final List<String> thatBean = null;
//        final Object thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, thatType));

//        final Object thatBean = null;
//        final List<String> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, Object.class));
    }

    @Test
    public void isAssignableShouldBeTrueWhenAssigningFromClassWithWildcardToObject() {
        final Type thatType = new TypeLiteral<List<?>>() {}.getGenericType();

//        final List<?> thatBean = null;
//        final Object thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, thatType));

//        final Object thatBean = null;
//        final List<?> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, Object.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenMatchingWildcardWithResolvedTypeVariableOfCorrectType() {
        final Type thisType = new TypeLiteral<Box<? extends Shoe>>() {}.getGenericType();

//        final ShoeBoxBean thatBean = null;
//        final Box<? extends Shoe> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, ShoeBoxBean.class));

//        final Box<? extends Shoe> thatBean = null;
//        final ShoeBoxBean thisBean = thatBean; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(ShoeBoxBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenMatchingWildcardWithResolvedTypeVariableOfWrongType() {
        final Type thisType = new TypeLiteral<Box<? extends Pizza>>() {}.getGenericType();

//        final ShoeBoxBean thatBean = null;
//        final Box<? extends Pizza> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, ShoeBoxBean.class));

//        final Box<? extends Pizza> thatBean = null;
//        final ShoeBoxBean thisBean = thatBean; // compile error
        assertFalse(GenericsHelper.isAssignableFrom(ShoeBoxBean.class, thisType));
    }

    @Test
    public void isAssignableFromShouldReturnTrueWhenUsingNestedWildcardsAndTypeMatches() {
        final Type thatType = new TypeLiteral<Collection<Movie<Horror>>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Collection<? extends Movie<? extends Horror>>>() {}.getGenericType();

//        final Collection<Movie<Horror>> thatBean = null;
//        final Collection<? extends Movie<? extends Horror>> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Collection<? extends Movie<? extends Horror>> thatBean = null;
//        final Collection<Movie<Horror>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldReturnFalseWhenUsingNestedWildcardsAndNestedTypeDoesNotMatch() {
        final Type thatType = new TypeLiteral<Collection<Movie<Comedy>>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Collection<? extends Movie<? extends Horror>>>() {}.getGenericType();

//        final Collection<Movie<Comedy>> thatBean = null;
//        final Collection<? extends Movie<? extends Horror>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Collection<? extends Movie<? extends Horror>> thatBean = null;
//        final Collection<Movie<Comedy>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldReturnFalseWhenUsingWildcardAtLastLevelButNotPrevious() {
        final Type thatType = new TypeLiteral<Collection<Movie<Horror>>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Collection<Movie<? extends Horror>>>() {}.getGenericType();

//        final Collection<Movie<Horror>> thatBean = null;
//        final Collection<Movie<? extends Horror>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Collection<Movie<? extends Horror>> thatBean = null;
//        final Collection<Movie<Horror>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenUsingNestedWildcardInTypeVariableOfClassAndTypeMatches() {
        final Type thisType = new TypeLiteral<TypeVariableWithNestedWildcard<Container<Fanta>>>() {}.getGenericType();

//        final TypeVariableWithNestedWildcardImpl thatBean = null;
//        final TypeVariableWithNestedWildcard<Container<Fanta>> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, TypeVariableWithNestedWildcardImpl.class));

//        final TypeVariableWithNestedWildcard<Container<Fanta>> thatBean = null;
//        final TypeVariableWithNestedWildcardImpl thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(TypeVariableWithNestedWildcardImpl.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueWhenUsingNestedWildcardInTypeVariableOfClassAndTypeMatchesWildcard() {
        final Type thisType = new TypeLiteral<TypeVariableWithNestedWildcard<? extends Container<? extends Liquid>>>() {}.getGenericType();

//        final TypeVariableWithNestedWildcardImpl thatBean = null;
//        final TypeVariableWithNestedWildcard<? extends Container<? extends Liquid>> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, TypeVariableWithNestedWildcardImpl.class));

//        final TypeVariableWithNestedWildcard<? extends Container<? extends Liquid>> thatBean = null;
//        final TypeVariableWithNestedWildcardImpl thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(TypeVariableWithNestedWildcardImpl.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenUsingNestedWildcardInTypeVariableOfClassAndNestedTypeDoesNotMatch() {
        final Type thisType = new TypeLiteral<TypeVariableWithNestedWildcard<Container<Pepsi>>>() {}.getGenericType();

//        final TypeVariableWithNestedWildcardImpl thatBean = null;
//        final TypeVariableWithNestedWildcard<Container<Pepsi>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, TypeVariableWithNestedWildcardImpl.class));

//        final TypeVariableWithNestedWildcard<Container<Pepsi>> thatBean = null;
//        final TypeVariableWithNestedWildcardImpl thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(TypeVariableWithNestedWildcardImpl.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseWhenUsingNestedWildcardInTypeVariableOfClassAndNestedTypeIsSubclass() {
        final Type thisType = new TypeLiteral<TypeVariableWithNestedWildcard<Container<Liquid>>>() {}.getGenericType();

//        final TypeVariableWithNestedWildcardImpl thatBean = null;
//        final TypeVariableWithNestedWildcard<Container<Liquid>> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thisType, TypeVariableWithNestedWildcardImpl.class));

//        final TypeVariableWithNestedWildcard<Container<Liquid>> thatBean = null;
//        final TypeVariableWithNestedWildcardImpl thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(TypeVariableWithNestedWildcardImpl.class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForArrayOfTheSameClassAndOneDimension() {
        assertTrue(GenericsHelper.isAssignableFrom(String[].class, String[].class));
        assertTrue(String[].class.isAssignableFrom(String[].class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForArrayOfTheSameClassAndTwoDimensions() {
        assertTrue(GenericsHelper.isAssignableFrom(String[][].class, String[][].class));
        assertTrue(String[][].class.isAssignableFrom(String[][].class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForArrayOfTheSameClassAndDifferentDimensions() {
        assertFalse(GenericsHelper.isAssignableFrom(String[].class, String[][].class));
        assertFalse(String[].class.isAssignableFrom(String[][].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[][].class, String[].class));
        assertFalse(String[][].class.isAssignableFrom(String[].class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForAssignmentBetweenArrayAndRegularClassOfTheSameClass() {
        assertFalse(GenericsHelper.isAssignableFrom(String.class, String[].class));
        assertFalse(String.class.isAssignableFrom(String[].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[].class, String.class));
        assertFalse(String[].class.isAssignableFrom(String.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForAssignmentBetweenArrayAndObject() {
        assertTrue(GenericsHelper.isAssignableFrom(Object.class, String[].class));
        assertTrue(Object.class.isAssignableFrom(String[].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[].class, Object.class));
        assertFalse(String[].class.isAssignableFrom(Object.class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForAssignmentBetweenArrayAndObjectArray() {
        assertTrue(GenericsHelper.isAssignableFrom(Object[].class, String[].class));
        assertTrue(Object[].class.isAssignableFrom(String[].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[].class, Object[].class));
        assertFalse(String[].class.isAssignableFrom(Object[].class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForAssignmentBetweenArrayAndObjectArrayOfDifferentDimensions() {
        // True because any array is also an object - does not work with any other classes
        assertTrue(GenericsHelper.isAssignableFrom(Object[].class, String[][].class));
        assertTrue(Object[].class.isAssignableFrom(String[][].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[][].class, Object[].class));
        assertFalse(String[][].class.isAssignableFrom(Object[].class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForAssignmentBetweenArrayAndObjectArrayOfDifferentDimensionsWhenObjectHasMost() {
        assertFalse(GenericsHelper.isAssignableFrom(Object[][].class, String[].class));
        assertFalse(Object[][].class.isAssignableFrom(String[].class));

        assertFalse(GenericsHelper.isAssignableFrom(String[].class, Object[][].class));
        assertFalse(String[].class.isAssignableFrom(Object[][].class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForAssignmentBetweenArrayAndRegularSuperclass() {
        assertTrue(Number.class.isAssignableFrom(Integer.class)); // Verify non-array assignability

        assertFalse(GenericsHelper.isAssignableFrom(Number.class, Integer[].class));
        assertFalse(Number.class.isAssignableFrom(Integer[].class));

        assertFalse(GenericsHelper.isAssignableFrom(Integer[].class, Number.class));
        assertFalse(Integer[].class.isAssignableFrom(Number.class));
    }

    @Test
    public void isAssignableFromShouldBeFalseForAssignmentBetweenArraysWithCorrectInheritanceAndDifferentDimensions() {
        assertTrue(Number.class.isAssignableFrom(Integer.class)); // Verify non-array assignability

        assertFalse(GenericsHelper.isAssignableFrom(Number[].class, Integer[][].class));
        assertFalse(Number[].class.isAssignableFrom(Integer[][].class));

        assertFalse(GenericsHelper.isAssignableFrom(Integer[][].class, Number[].class));
        assertFalse(Integer[][].class.isAssignableFrom(Number[].class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForArraysOfCorrectInheritance() {
        assertTrue(GenericsHelper.isAssignableFrom(MiddleBean[].class, ChildBean[].class));
        assertTrue(MiddleBean[].class.isAssignableFrom(ChildBean[].class));

        assertFalse(GenericsHelper.isAssignableFrom(ChildBean[].class, MiddleBean[].class));
        assertFalse(ChildBean[].class.isAssignableFrom(MiddleBean[].class));
    }

    @Test
    public void isAssignableFromShouldBeTrueForGenericTypeWithArrayThatMatches() {
        final Type thisType = new TypeLiteral<Provider<String[]>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Provider<String[]>>() {}.getGenericType();

        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForGenericTypeWithArrayOfWrongType() {
        final Type thisType = new TypeLiteral<Provider<Number[]>>() {}.getGenericType();
        final Type thatType = new TypeLiteral<Provider<Integer[]>>() {}.getGenericType();

        assertFalse(GenericsHelper.isAssignableFrom(thisType, thatType));
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueInJava7WithConcreteArrayInsteadOfGenericArray() {
        // Tests an injection point with a concrete array (like Container<Fanta[]>, not Container<T[]>).
        // In Java 6 both the factory point and the injection point will be GenericArrayTypes, and matches on equals.
        // In Java 7 however, the injection point will be a real array, so special handling is needed to compare
        // the array to a GenericArrayType.
        // Since using a TypeLiteral gives different results (GenericArrayType vs real array) in Java 6 and 7
        // then this test needs to use wrappers to simulate the same effect in both versions.
        final Type thisConcreteArray = new WrappedParameterizedType(Container.class, new Type[] {Fanta[].class});
        final Type thatGenericArray =
                new WrappedParameterizedType(Container.class, new Type[] {new WrappedGenericArrayType(Fanta.class)});

        assertTrue(GenericsHelper.isAssignableFrom(thisConcreteArray, thatGenericArray));
        assertFalse(GenericsHelper.isAssignableFrom(thatGenericArray, thisConcreteArray));
    }

    @Test
    public void isAssignableFromShouldBeTrueForArrayFromGenericTypeThatMatchesRegularArrayOfCorrectType() {
        final Type thisProvider = new TypeLiteral<Provider<String[]>>() {}.getGenericType();
        final Type thisType = GenericsHelper.getGenericArgumentAsType(thisProvider);

        assertTrue(GenericsHelper.isAssignableFrom(thisType, String[].class));
        assertFalse(GenericsHelper.isAssignableFrom(String[].class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForArrayFromGenericTypeWithLessDimensionsThanRegularArrayOfCorrectType() {
        final Type thisProvider = new TypeLiteral<Provider<String[]>>() {}.getGenericType();
        final Type thisType = GenericsHelper.getGenericArgumentAsType(thisProvider);

        assertFalse(GenericsHelper.isAssignableFrom(thisType, String[][].class));
        assertFalse(GenericsHelper.isAssignableFrom(String[][].class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeFalseForArrayFromGenericTypeWithMoreDimensionsThanRegularArrayOfCorrectType() {
        final Type thisProvider = new TypeLiteral<Provider<String[][]>>() {}.getGenericType();
        final Type thisType = GenericsHelper.getGenericArgumentAsType(thisProvider);

        assertFalse(GenericsHelper.isAssignableFrom(thisType, String[].class));
        assertFalse(GenericsHelper.isAssignableFrom(String[].class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueForArrayFromGenericTypeThatMatchesRegularArrayOfCorrectInheritance() {
        final Type thisProvider = new TypeLiteral<Provider<Number[]>>() {}.getGenericType();
        final Type thisType = GenericsHelper.getGenericArgumentAsType(thisProvider);

        assertTrue(GenericsHelper.isAssignableFrom(thisType, Integer[].class));
        assertFalse(GenericsHelper.isAssignableFrom(Integer[].class, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueFromGenericArrayToWildcardOfCorrectType() {
        final Type thatType = new TypeLiteral<Box<String[]>>() {}.getGenericType();
        final Type thisType = new TypeLiteral<Box<? extends String[]>>() {}.getGenericType();

//        final Box<String[]> thatBean = null;
//        final Box<? extends String[]> thisBean = thatBean; // ok
        assertTrue(GenericsHelper.isAssignableFrom(thisType, thatType));

//        final Box<? extends String[]> thatBean = null;
//        final Box<String[]> thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(thatType, thisType));
    }

    @Test
    public void isAssignableFromShouldBeTrueFromArrayToWildcardOfCorrectType() {
        final Type thisType = new TypeLiteral<Provider<? extends String[]>>() {}.getGenericType();
        final Type thisWildcard = GenericsHelper.getGenericArgumentAsType(thisType);

//        final String[] thatBean = null;
//        final ? extends String[] thisBean = thatBean; // ok, but not possible to write, must come from a generic type
        assertTrue(GenericsHelper.isAssignableFrom(thisWildcard, String[].class));

//        final ? extends String[] thatBean = null;
//        final String[] thisBean = thatBean; // compiler error
        assertFalse(GenericsHelper.isAssignableFrom(String[].class, thisWildcard));
    }

    @Test
    public void mapTypeVariablesToActualTypesShouldHandleInheritance() {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(ConcreteDualVariableBean.class);
        assertEquals(3, typeMap.size());

        final TypeVariable<?> interfaceF = getTypeVariable("F", DualVariableInterfaceBean.class, typeMap);
        assertNotNull(interfaceF);
        assertEquals(VariableOnePointTwo.class, typeMap.getActualType(interfaceF));

        final TypeVariable<?> interfaceS = getTypeVariable("S", DualVariableInterfaceBean.class, typeMap);
        assertNotNull(interfaceS);
        assertEquals(VariableTwo.class, typeMap.getActualType(interfaceS));

        final TypeVariable<?> abstractS = getTypeVariable("S", AbstractDualVariableBean.class, typeMap);
        assertNotNull(abstractS);
        assertEquals(VariableOnePointTwo.class, typeMap.getActualType(abstractS));
    }

    @Test
    public void mapTypeVariablesToActualTypesShouldHandleNoTypeVariables() {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(HelloBean.class);
        assertEquals(0, typeMap.size());
    }

    @Test
    public void mapTypeVariablesToActualTypesShouldHandleNotResolvingAllTypes() {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(ArrayList.class);
        assertEquals(5, typeMap.size());

        final Set<TypeVariable<?>> keys = typeMap.getKeys();

        for (final TypeVariable<?> key : keys) {
            final Type actualType = typeMap.getActualType(key);
            assertNull(actualType);
        }
    }

    @Test
    public void mapTypeVariablesToActualTypesShouldNotMapTypeVariablesDeclaredLocallyOnMethods() throws NoSuchMethodException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableChild.class);

        assertNotNull(typeMap);
        assertEquals(1, typeMap.size());

        // Verifying expected type in the map
        final TypeVariable<?> typeVariable = getTypeVariable("T", TypeVariableParent.class, typeMap);
        assertNotNull(typeVariable);
        assertEquals(Square.class, typeMap.getActualType(typeVariable));

        // Method that uses it's own declaration of T - should map to null
        final Type localT = TypeVariableParent.class.getDeclaredMethod("getLocallyDeclaredT").getGenericReturnType();
        assertNull(typeMap.getActualType((TypeVariable<?>) localT));

        // Method that uses the class declaration of T - should map to the actual type
        final Type classT = TypeVariableParent.class.getDeclaredMethod("getClassDeclaredT").getGenericReturnType();
        assertEquals(Square.class, typeMap.getActualType((TypeVariable<?>) classT));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariableAsMainType() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Class<Fanta> expectedType = Fanta.class;

        final Type fieldType = getTypeFromField("standaloneT");
        assertFalse(expectedType.equals(fieldType));

        final Type typeFromMap = typeMap.getActualType((TypeVariable<?>) fieldType);
        assertEquals(expectedType, typeFromMap);

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldLeaveTypeVariableAsIsIfNoReplacementWasFoundForMainType() throws NoSuchFieldException {
        final TypeMap typeMap = new TypeMap();
        final Type fieldType = getTypeFromField("standaloneT");

        final Type typeFromMap = typeMap.getActualType((TypeVariable<?>) fieldType);
        assertNull(typeFromMap);

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertTrue(GenericsHelper.isTypeVariable(wrappedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariableAsSubType() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Container<Fanta>>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Container<Pepsi>>() {}.getGenericType();

        final Type fieldType = getTypeFromField("containerOfT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldLeaveTypeVariableAsIsIfNoReplacementWasFoundForSubType() throws NoSuchFieldException {
        final TypeMap typeMap = new TypeMap();
        final Type fieldType = getTypeFromField("containerOfT");

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        final Type wrappedTypeArgument = GenericsHelper.getGenericArgumentAsType(wrappedType);
        assertTrue(GenericsHelper.isTypeVariable(wrappedTypeArgument));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariableAsSubSubType() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Set<Container<Fanta>>>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Set<Container<Pepsi>>>() {}.getGenericType();

        final Type fieldType = getTypeFromField("setOfContainersOfT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariableInWildcardWithExtends() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Set<Container<? extends Fanta>>>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Set<Container<? extends Pepsi>>>() {}.getGenericType();

        final Type fieldType = getTypeFromField("setOfContainersOfExtendsT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariableInWildcardWithSuper() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Set<Container<? super Fanta>>>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Set<Container<? super Pepsi>>>() {}.getGenericType();

        final Type fieldType = getTypeFromField("setOfContainersOfSuperT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariablesInArrays() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Fanta[]>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Pepsi[]>() {}.getGenericType();

        final Type fieldType = getTypeFromField("arrayT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    @Test
    public void wrapTypeAndReplaceTypeVariablesShouldReplaceTypeVariablesInNestedArrays() throws NoSuchFieldException {
        final TypeMap typeMap = GenericsHelper.mapTypeVariablesToActualTypes(TypeVariableBeanWithFanta.class);
        final Type expectedType = new TypeLiteral<Set<Container<Fanta[]>>>() {}.getGenericType();
        final Type unexpectedType = new TypeLiteral<Set<Container<Pepsi[]>>>() {}.getGenericType();

        final Type fieldType = getTypeFromField("setOfContainersOfArrayT");
        assertFalse(expectedType.equals(fieldType));

        final Type wrappedType = GenericsHelper.wrapTypeAndReplaceTypeVariables(fieldType, typeMap);
        assertEquals(expectedType, wrappedType);
        assertEquals(wrappedType, expectedType);

        assertFalse(unexpectedType.equals(wrappedType));
        assertFalse(wrappedType.equals(unexpectedType));
    }

    private Type getTypeFromField(final String fieldName) throws NoSuchFieldException {
        final Field field = TypeVariableBean.class.getDeclaredField(fieldName);
        return field.getGenericType();
    }

    private TypeVariable<?> getTypeVariable(final String name, final Class<?> ownerClass, final TypeMap typeMap) {
        final Set<TypeVariable<?>> keys = typeMap.getKeys();

        for (final TypeVariable<?> key : keys) {
            if (key.getName().equals(name) && key.getGenericDeclaration().equals(ownerClass)) {
                return key;
            }
        }

        return null;
    }
}
