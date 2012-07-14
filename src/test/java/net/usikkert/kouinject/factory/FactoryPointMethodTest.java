
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

package net.usikkert.kouinject.factory;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.notscanned.factory.FactoryPointMethodBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link FactoryPointMethod}.
 *
 * @author Christian Ihle
 */
public class FactoryPointMethodTest {

    private String nothingMethod;
    private BeanKey beanKey;
    private List<BeanKey> parameters;

    @Before
    public void setUp() {
        nothingMethod = "nothingMethod";
        beanKey = new BeanKey(Object.class);
        parameters = new ArrayList<BeanKey>();
    }

    @Test
    public void createShouldReturnInstance() throws NoSuchMethodException {
        final Method method = getMethod("coffeeBeanMethod");

        final FactoryPointMethod<CoffeeBean> fpMethod =
                new FactoryPointMethod<CoffeeBean>(method, beanKey, beanKey, parameters, false);

        final CoffeeBean coffeeBean = fpMethod.create(new FactoryPointMethodBean());
        assertNotNull(coffeeBean);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullReturnValueShouldFail() throws NoSuchMethodException {
        final Method method = getMethod("nullMethod");

        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, beanKey, parameters, false);

        fpMethod.create(new FactoryPointMethodBean());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithVoidMethodShouldFail() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);

        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, beanKey, parameters, false);

        fpMethod.create(new FactoryPointMethodBean());
    }

    @Test
    public void createOnPrivateMethodShouldWorkAndAccessibleValueShouldBeOriginalValue() throws NoSuchMethodException {
        final Method method = getMethod("privateRedBeanMethod");
        assertFalse(method.isAccessible());

        final FactoryPointMethod<RedBean> fpMethod =
                new FactoryPointMethod<RedBean>(method, beanKey, beanKey, parameters, false);

        final RedBean redBean = fpMethod.create(new FactoryPointMethodBean());
        assertNotNull(redBean);
        assertFalse(method.isAccessible());
    }

    @Test
    public void createShouldWorkWithMethodParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithParameters", HelloBean.class, GreenBean.class);

        final FactoryPointMethod<CarBean> fpMethod =
                new FactoryPointMethod<CarBean>(method, beanKey, beanKey, parameters, false);

        final CarBean carBean = fpMethod.create(new FactoryPointMethodBean(), new HelloBean(), new GreenBean());
        assertNotNull(carBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldFailWithWrongParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithParameters", HelloBean.class, GreenBean.class);

        final FactoryPointMethod<CarBean> fpMethod =
                new FactoryPointMethod<CarBean>(method, beanKey, beanKey, parameters, false);

        fpMethod.create(new FactoryPointMethodBean(), new HelloBean(), new CoffeeBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldFailWithMissingParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithParameters", HelloBean.class, GreenBean.class);

        final FactoryPointMethod<CarBean> fpMethod =
                new FactoryPointMethod<CarBean>(method, beanKey, beanKey, parameters, false);

        fpMethod.create(new FactoryPointMethodBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShouldFailWithNullParameters() throws NoSuchMethodException {
        final Method method = getMethod("methodWithParameters", HelloBean.class, GreenBean.class);

        final FactoryPointMethod<CarBean> fpMethod =
                new FactoryPointMethod<CarBean>(method, beanKey, beanKey, parameters, false);

        fpMethod.create(new FactoryPointMethodBean(), new HelloBean(), null);
    }

    @Test
    public void createOnPrivateMethodShouldSetAccessibleToOriginalValueOnFail() throws NoSuchMethodException {
        final Method method = getMethod("methodWithParameters", HelloBean.class, GreenBean.class);
        assertFalse(method.isAccessible());

        final FactoryPointMethod<CarBean> fpMethod =
                new FactoryPointMethod<CarBean>(method, beanKey, beanKey, parameters, false);

        try {
            fpMethod.create(new FactoryPointMethodBean());
            fail("Should have failed on create");
        }

        catch (IllegalArgumentException e) {
            // OK
        }

        assertFalse(method.isAccessible());
    }

    @Test
    public void getParametersShouldReturnSameContentAsParameter() throws NoSuchMethodException {
        final BeanKey beanKey1 = new BeanKey(Object.class);
        final BeanKey beanKey2 = new BeanKey(HelloBean.class);
        final BeanKey beanKey3 = new BeanKey(JavaBean.class);

        parameters.add(beanKey1);
        parameters.add(beanKey2);
        parameters.add(beanKey3);

        final Method method = getMethod(nothingMethod);
        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, beanKey, parameters, false);

        final List<BeanKey> fpMethodParameters = fpMethod.getParameters();

        assertNotNull(fpMethodParameters);
        assertEquals(3, fpMethodParameters.size());
        assertTrue(fpMethodParameters.contains(beanKey1));
        assertTrue(fpMethodParameters.contains(beanKey2));
        assertTrue(fpMethodParameters.contains(beanKey3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getParametersShouldReturnUnmodifiableList() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);
        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, beanKey, parameters, true);

        final List<BeanKey> fpMethodParameters = fpMethod.getParameters();
        assertNotNull(fpMethodParameters);

        // Returns a different list than the one sent in as a parameter
        assertNotSame(parameters, fpMethodParameters);

        // And it's unmodifiable
        fpMethodParameters.add(new BeanKey(Object.class));
    }

    @Test
    public void getReturnTypeShouldReturnParameter() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);
        final BeanKey returnType = new BeanKey(JavaBean.class);

        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, returnType, parameters, true);

        assertSame(returnType, fpMethod.getReturnType());
    }

    @Test
    public void getFactoryKeyShouldReturnParameter() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);
        final BeanKey factoryKey = new BeanKey(EverythingBean.class);

        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, factoryKey, beanKey, parameters, true);

        assertSame(factoryKey, fpMethod.getFactoryKey());
    }

    @Test
    public void isSingletonShouldReturnParameter() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);

        final FactoryPointMethod<?> singleton = new FactoryPointMethod(method, beanKey, beanKey, parameters, true);
        assertTrue(singleton.isSingleton());

        final FactoryPointMethod<?> prototype = new FactoryPointMethod(method, beanKey, beanKey, parameters, false);
        assertFalse(prototype.isSingleton());
    }

    @Test
    public void getMethodShouldReturnParameter() throws NoSuchMethodException {
        final Method method = getMethod(nothingMethod);

        final FactoryPointMethod<?> fpMethod = new FactoryPointMethod(method, beanKey, beanKey, parameters, false);

        assertSame(method, fpMethod.getMethod());
    }

    private Method getMethod(final String name, final Class<?>... methodParameters) throws NoSuchMethodException {
        return FactoryPointMethodBean.class.getDeclaredMethod(name, methodParameters);
    }
}
