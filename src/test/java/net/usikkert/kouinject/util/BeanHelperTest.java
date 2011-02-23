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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.inject.Provider;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.notscanned.BeanHelperBean;
import net.usikkert.kouinject.testbeans.notscanned.SomeEnum;
import net.usikkert.kouinject.testbeans.notscanned.TheInterface;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.notscanned.date.DateBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.QualifierBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link BeanHelper}.
 *
 * @author Christian Ihle
 */
public class BeanHelperTest {

    private BeanHelper beanHelper;

    @Before
    public void createBeanHelper() {
        beanHelper = new BeanHelper();
    }

    @Test
    public void findFactoryReturnTypeShouldFindTheCorrectClass() throws NoSuchMethodException {
        final Method method = getMethod("helloBeanFactoryMethod");
        final BeanKey returnType = beanHelper.findFactoryReturnType(method);

        assertNotNull(returnType);
        checkRegularParameter(returnType, HelloBean.class, null);
    }

    @Test
    public void findFactoryReturnTypeShouldFindTheCorrectQualifier() throws NoSuchMethodException {
        final Method method = getMethod("scopedAndQualifiedFactoryMethod");
        final BeanKey returnType = beanHelper.findFactoryReturnType(method);

        assertNotNull(returnType);
        checkRegularParameter(returnType, JavaBean.class, "Blue");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void findFactoryReturnTypeShouldFailOnVoid() throws NoSuchMethodException {
        final Method method = getMethod("voidFactoryMethod");
        beanHelper.findFactoryReturnType(method);
    }

    @Test
    public void findParameterKeysForMethodShouldHandleMethodsWithNoParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithNoParameters");
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertTrue(parameters.isEmpty());
    }

    @Test
    public void findParameterKeysForMethodShouldFindSingleParameterWithoutQualifier() throws NoSuchMethodException {
        final Method method = getMethod("methodWithSingleParameter", TheInterface.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, TheInterface.class, null);
    }

    @Test
    public void findParameterKeysForMethodShouldHandlePrivateMethods() throws NoSuchMethodException {
        final Method method = getMethod("privateMethod", SomeEnum.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, SomeEnum.class, null);
    }

    @Test
    public void findParameterKeysForMethodShouldFindParameterWithQualifier() throws NoSuchMethodException {
        final Method method = getMethod("methodWithQualifiedParameter", TheInterfaceUser.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, TheInterfaceUser.class, "ping");
    }

    @Test
    public void findParameterKeysForMethodShouldFindSeveralParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithSeveralParameters", DateBean.class, ColorBean.class, CoffeeBean.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(3, parameters.size());

        checkRegularParameter(parameters, 1, DateBean.class, null);
        checkRegularParameter(parameters, 2, ColorBean.class, "Green");
        checkRegularParameter(parameters, 3, CoffeeBean.class, "excellent");
    }

    @Test
    public void findParameterKeysForMethodShouldFindProvider() throws NoSuchMethodException {
        final Method method = getMethod("methodWithProvider", Provider.class, Provider.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkProviderParameter(parameters, 1, RedBean.class, null);
        checkProviderParameter(parameters, 2, NoBean.class, "great");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfProviderIsMissingGenericType() throws NoSuchMethodException {
        final Method method = getMethod("methodWithProviderWithoutGenericType", Provider.class);
        beanHelper.findParameterKeys(method);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfProviderIsUsingGenericWildcard() throws NoSuchMethodException {
        final Method method = getMethod("methodWithProviderWithGenericWildCard", Provider.class);
        beanHelper.findParameterKeys(method);
    }

    @Test
    public void findParameterKeysForMethodShouldFindCollectionProvider() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollectionProvider", CollectionProvider.class, CollectionProvider.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkCollectionProviderParameter(parameters, 1, YellowBean.class, null);
        checkCollectionProviderParameter(parameters, 2, QualifierBean.class, "awesome");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfCollectionProviderIsMissingGenericType() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollectionProviderWithoutGenericType", CollectionProvider.class);
        beanHelper.findParameterKeys(method);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfCollectionProviderIsUsingGenericWildcard() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollectionProviderWithGenericWildCard", CollectionProvider.class);
        beanHelper.findParameterKeys(method);
    }

    @Test
    public void findParameterKeysForMethodShouldFindCollection() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollection", Collection.class, Collection.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(method);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkCollectionParameter(parameters, 1, GreenBean.class, null);
        checkCollectionParameter(parameters, 2, CarBean.class, "best");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfCollectionIsMissingGenericType() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollectionWithoutGenericType", Collection.class);
        beanHelper.findParameterKeys(method);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForMethodShouldFailIfCollectionIsUsingGenericWildcard() throws NoSuchMethodException {
        final Method method = getMethod("methodWithCollectionWithGenericWildCard", Collection.class);
        beanHelper.findParameterKeys(method);
    }

    @Test
    public void findFieldKeyShouldFindClassWithoutQualifier() throws NoSuchFieldException {
        final Field field = getField("field");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkRegularParameter(fieldKey, TheInterface.class, null);
    }

    @Test
    public void findFieldKeyShouldFindClassWithQualifier() throws NoSuchFieldException {
        final Field field = getField("fieldWithQualifier");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkRegularParameter(fieldKey, TheInterfaceUser.class, "ping");
    }

    @Test
    public void findFieldKeyShouldFindProviderWithoutQualifier() throws NoSuchFieldException {
        final Field field = getField("provider");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkProviderParameter(fieldKey, RedBean.class, null);
    }

    @Test
    public void findFieldKeyShouldFindProviderWithQualifier() throws NoSuchFieldException {
        final Field field = getField("providerWithQualifier");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkProviderParameter(fieldKey, NoBean.class, "great");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfProviderIsMissingGenericType() throws NoSuchFieldException {
        final Field field = getField("providerWithoutGenericType");
        beanHelper.findFieldKey(field);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfProviderIsUsingGenericWildCard() throws NoSuchFieldException {
        final Field field = getField("providerWithWildCard");
        beanHelper.findFieldKey(field);
    }

    @Test
    public void findFieldKeyShouldFindCollectionProviderWithoutQualifier() throws NoSuchFieldException {
        final Field field = getField("collectionProvider");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkCollectionProviderParameter(fieldKey, YellowBean.class, null);
    }

    @Test
    public void findFieldKeyShouldFindCollectionProviderWithQualifier() throws NoSuchFieldException {
        final Field field = getField("collectionProviderWithQualifier");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkCollectionProviderParameter(fieldKey, QualifierBean.class, "awesome");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfCollectionProviderIsMissingGenericType() throws NoSuchFieldException {
        final Field field = getField("collectionProviderWithoutGenericType");
        beanHelper.findFieldKey(field);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfCollectionProviderIsUsingGenericWildCard() throws NoSuchFieldException {
        final Field field = getField("collectionProviderWithWildCard");
        beanHelper.findFieldKey(field);
    }

    @Test
    public void findFieldKeyShouldFindCollectionWithoutQualifier() throws NoSuchFieldException {
        final Field field = getField("collection");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkCollectionParameter(fieldKey, GreenBean.class, null);
    }

    @Test
    public void findFieldKeyShouldFindCollectionWithQualifier() throws NoSuchFieldException {
        final Field field = getField("collectionWithQualifier");
        final BeanKey fieldKey = beanHelper.findFieldKey(field);

        assertNotNull(fieldKey);
        checkCollectionParameter(fieldKey, CarBean.class, "best");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfCollectionIsMissingGenericType() throws NoSuchFieldException {
        final Field field = getField("collectionWithoutGenericType");
        beanHelper.findFieldKey(field);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldKeyShouldFailIfCollectionIsUsingGenericWildCard() throws NoSuchFieldException {
        final Field field = getField("collectionWithWildCard");
        beanHelper.findFieldKey(field);
    }

    @Test
    public void findParameterKeysForConstructorShouldHandleConstructorsWithNoParameters() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor();
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertTrue(parameters.isEmpty());
    }

    @Test
    public void findParameterKeysForConstructorShouldFindSingleParameterWithoutQualifier() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(TheInterface.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, TheInterface.class, null);
    }

    @Test
    public void findParameterKeysForConstructorShouldHandlePrivateMethods() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(SomeEnum.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, SomeEnum.class, null);
    }

    @Test
    public void findParameterKeysForConstructorShouldFindParameterWithQualifier() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(TheInterfaceUser.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(1, parameters.size());

        checkRegularParameter(parameters, 1, TheInterfaceUser.class, "ping");
    }

    @Test
    public void findParameterKeysForConstructorShouldFindSeveralParameters() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(DateBean.class, ColorBean.class, CoffeeBean.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(3, parameters.size());

        checkRegularParameter(parameters, 1, DateBean.class, null);
        checkRegularParameter(parameters, 2, ColorBean.class, "Green");
        checkRegularParameter(parameters, 3, CoffeeBean.class, "excellent");
    }

    @Test
    public void findParameterKeysForConstructorShouldFindProvider() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Provider.class, Provider.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkProviderParameter(parameters, 1, RedBean.class, null);
        checkProviderParameter(parameters, 2, NoBean.class, "great");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfProviderIsMissingGenericType() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Provider.class, HelloBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfProviderIsUsingGenericWildcard() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Provider.class, JavaBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    @Test
    public void findParameterKeysForConstructorShouldFindCollectionProvider() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(CollectionProvider.class, CollectionProvider.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkCollectionProviderParameter(parameters, 1, YellowBean.class, null);
        checkCollectionProviderParameter(parameters, 2, QualifierBean.class, "awesome");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfCollectionProviderIsMissingGenericType() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(CollectionProvider.class, HelloBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfCollectionProviderIsUsingGenericWildcard() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(CollectionProvider.class, JavaBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    @Test
    public void findParameterKeysForConstructorShouldFindCollection() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Collection.class, Collection.class);
        final List<BeanKey> parameters = beanHelper.findParameterKeys(constructor);

        assertNotNull(parameters);
        assertEquals(2, parameters.size());

        checkCollectionParameter(parameters, 1, GreenBean.class, null);
        checkCollectionParameter(parameters, 2, CarBean.class, "best");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfCollectionIsMissingGenericType() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Collection.class, HelloBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findParameterKeysForConstructorShouldFailIfCollectionIsUsingGenericWildcard() throws NoSuchMethodException {
        final Constructor<?> constructor = getConstructor(Collection.class, JavaBean.class);
        beanHelper.findParameterKeys(constructor);
    }

    private void checkRegularParameter(final List<BeanKey> parameters, final int position,
                                       final Class<?> aClass, final String qualifier) {
        final BeanKey beanKey = getParameter(parameters, position);
        checkRegularParameter(beanKey, aClass, qualifier);
    }

    private void checkRegularParameter(final BeanKey parameter, final Class<?> aClass, final String qualifier) {
        assertFalse(parameter.isProvider());
        assertFalse(parameter.isCollection());
        assertFalse(parameter.isCollectionProvider());
        checkParameter(parameter, aClass, qualifier);
    }

    private void checkProviderParameter(final List<BeanKey> parameters, final int position,
                                        final Class<?> aClass, final String qualifier) {
        final BeanKey parameter = getParameter(parameters, position);
        checkProviderParameter(parameter, aClass, qualifier);
    }

    private void checkProviderParameter(final BeanKey parameter, final Class<?> aClass, final String qualifier) {
        assertTrue(parameter.isProvider());
        checkParameter(parameter, aClass, qualifier);
    }

    private void checkCollectionProviderParameter(final List<BeanKey> parameters, final int position,
                                                  final Class<?> aClass, final String qualifier) {
        final BeanKey parameter = getParameter(parameters, position);
        checkCollectionProviderParameter(parameter, aClass, qualifier);
    }

    private void checkCollectionProviderParameter(final BeanKey parameter, final Class<?> aClass, final String qualifier) {
        assertTrue(parameter.isCollectionProvider());
        checkParameter(parameter, aClass, qualifier);
    }

    private void checkCollectionParameter(final List<BeanKey> parameters, final int position,
                                          final Class<?> aClass, final String qualifier) {
        final BeanKey parameter = getParameter(parameters, position);
        checkCollectionParameter(parameter, aClass, qualifier);
    }

    private void checkCollectionParameter(final BeanKey parameter, final Class<?> aClass, final String qualifier) {
        assertTrue(parameter.isCollection());
        checkParameter(parameter, aClass, qualifier);
    }

    private void checkParameter(final BeanKey parameter, final Class<?> aClass, final String qualifier) {
        assertEquals(aClass, parameter.getBeanClass());
        assertEquals(qualifier, parameter.getQualifier());
    }

    private BeanKey getParameter(final List<BeanKey> parameters, final int position) {
        return parameters.get(position -1);
    }

    private Method getMethod(final String methodName, final Class<?>... parameters) throws NoSuchMethodException {
        return BeanHelperBean.class.getDeclaredMethod(methodName, parameters);
    }

    private Field getField(final String fieldName) throws NoSuchFieldException {
        return BeanHelperBean.class.getDeclaredField(fieldName);
    }

    private Constructor<?> getConstructor(final Class<?>... parameters) throws NoSuchMethodException {
        return BeanHelperBean.class.getDeclaredConstructor(parameters);
    }
}