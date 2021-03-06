
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

import java.lang.reflect.Type;
import java.util.List;

import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.notscanned.factory.InjectFactoryBean;
import net.usikkert.kouinject.testbeans.notscanned.factory.StaticFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ChildFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ChildFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.DifferentTypesFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.DifferentTypesFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryParameterFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FirstMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MultipleFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.OneParameterFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.OrangeFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ParameterFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ParentFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.PrivateFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.PrivateFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SecondMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SingletonFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ThirdMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ThreeParametersFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.FantaBottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.LiquidDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.OrangeBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedFactoryPointHandler}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedFactoryPointHandlerTest {

    private AnnotationBasedFactoryPointHandler handler;

    @Before
    public void setUp() {
        handler = new AnnotationBasedFactoryPointHandler();
    }

    @Test
    public void getFactoryPointsShouldHandleNoFactoryMethods() {
        final BeanKey factory = new BeanKey(HelloBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertTrue(factoryPoints.isEmpty());
    }

    @Test
    public void getFactoryPointsShouldFindSingleFactoryMethod() {
        final BeanKey factory = new BeanKey(SimpleFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);

        checkBeanKey(factoryPoint.getReturnType(), SimpleFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint.getFactoryKey(), SimpleFactoryBean.class, null);
        assertFalse(factoryPoint.isSingleton());
        assertTrue(factoryPoint.getParameters().isEmpty());
    }

    @Test
    public void getFactoryPointsShouldFindMultipleFactoryMethods() {
        final BeanKey factory = new BeanKey(MultipleFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(3, factoryPoints.size());

        final FactoryPoint<?> factoryPoint1 = getFactoryPointByName(factoryPoints, "createFirstBean");
        checkBeanKey(factoryPoint1.getReturnType(), FirstMultipleFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint1.getFactoryKey(), MultipleFactoryBean.class, null);
        assertFalse(factoryPoint1.isSingleton());
        assertTrue(factoryPoint1.getParameters().isEmpty());

        final FactoryPoint<?> factoryPoint2 = getFactoryPointByName(factoryPoints, "createSecondBean");
        checkBeanKey(factoryPoint2.getReturnType(), SecondMultipleFactoryCreatedBean.class, "second");
        checkBeanKey(factoryPoint2.getFactoryKey(), MultipleFactoryBean.class, null);
        assertFalse(factoryPoint2.isSingleton());
        assertTrue(factoryPoint2.getParameters().isEmpty());

        final FactoryPoint<?> factoryPoint3 = getFactoryPointByName(factoryPoints, "createThirdBean");
        checkBeanKey(factoryPoint3.getReturnType(), ThirdMultipleFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint3.getFactoryKey(), MultipleFactoryBean.class, null);
        assertTrue(factoryPoint3.isSingleton());
        assertTrue(factoryPoint3.getParameters().isEmpty());
    }

    @Test
    public void getFactoryPointsShouldFindPrivateFactoryMethods() {
        final BeanKey factory = new BeanKey(PrivateFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);

        checkBeanKey(factoryPoint.getReturnType(), PrivateFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint.getFactoryKey(), PrivateFactoryBean.class, null);
        assertFalse(factoryPoint.isSingleton());
        assertTrue(factoryPoint.getParameters().isEmpty());
    }

    @Test
    public void getFactoryPointsShouldNotReturnMethodsWithoutProducesAnnotation() {
        final BeanKey factory = new BeanKey(EverythingBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertTrue(factoryPoints.isEmpty());
    }

    @Test
    public void getFactoryPointsShouldKeepTheQualifierOnTheFactoryKey() {
        final BeanKey factory = new BeanKey(SimpleFactoryBean.class, "banana");

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);
        checkBeanKey(factoryPoint.getFactoryKey(), SimpleFactoryBean.class, "banana");
    }

    @Test
    public void getFactoryPointsShouldFindSingletonScopeOnTheMethod() {
        final BeanKey factory = new BeanKey(SingletonFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);
        assertTrue(factoryPoint.isSingleton());
    }

    @Test
    public void getFactoryPointsShouldFindTheQualifierOfTheMethod() {
        final BeanKey factory = new BeanKey(OrangeFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);
        checkBeanKey(factoryPoint.getReturnType(), OrangeBean.class, "orange");
    }

    @Test
    public void getFactoryPointsShouldFindTheCorrectParameters() {
        final BeanKey factory = new BeanKey(ParameterFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(3, factoryPoints.size());

        // 1
        final FactoryPoint<?> factoryPoint1 = getFactoryPointByName(factoryPoints, "createBeanWithOneParameter");
        checkBeanKey(factoryPoint1.getReturnType(), OneParameterFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint1.getFactoryKey(), ParameterFactoryBean.class, null);
        assertFalse(factoryPoint1.isSingleton());

        assertEquals(1, factoryPoint1.getParameters().size());
        checkBeanKey(factoryPoint1.getParameters().get(0), HelloBean.class, null);

        // 2
        final FactoryPoint<?> factoryPoint2 = getFactoryPointByName(factoryPoints, "createBeanWithThreeParameters");
        checkBeanKey(factoryPoint2.getReturnType(), ThreeParametersFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint2.getFactoryKey(), ParameterFactoryBean.class, null);
        assertFalse(factoryPoint2.isSingleton());

        assertEquals(3, factoryPoint2.getParameters().size());
        checkBeanKey(factoryPoint2.getParameters().get(0), ColorBean.class, "Blue");
        checkBeanKey(factoryPoint2.getParameters().get(1), CoffeeBean.class, null);
        checkBeanKey(factoryPoint2.getParameters().get(2), RedBean.class, "red");

        // 3
        final FactoryPoint<?> factoryPoint3 = getFactoryPointByName(factoryPoints, "createBeanWithFactoryParameter");
        checkBeanKey(factoryPoint3.getReturnType(), FactoryParameterFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint3.getFactoryKey(), ParameterFactoryBean.class, null);
        assertFalse(factoryPoint3.isSingleton());

        assertEquals(1, factoryPoint3.getParameters().size());
        checkBeanKey(factoryPoint3.getParameters().get(0), OneParameterFactoryCreatedBean.class, null);
    }

    @Test
    public void getFactoryPointsShouldFindDifferentParameterTypes() {
        final BeanKey factory = new BeanKey(DifferentTypesFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(1, factoryPoints.size());

        final FactoryPoint<?> factoryPoint = factoryPoints.get(0);
        checkBeanKey(factoryPoint.getReturnType(), DifferentTypesFactoryCreatedBean.class, null);
        checkBeanKey(factoryPoint.getFactoryKey(), DifferentTypesFactoryBean.class, null);
        assertFalse(factoryPoint.isSingleton());

        assertEquals(4, factoryPoint.getParameters().size());

        final BeanKey parameter1 = factoryPoint.getParameters().get(0);
        checkBeanKey(parameter1, HelloBean.class, null);
        assertTrue(parameter1.isBeanForCreation());

        final BeanKey parameter2 = factoryPoint.getParameters().get(1);
        checkBeanKey(parameter2, FieldBean.class, null);
        assertTrue(parameter2.isCollectionProvider());

        final BeanKey parameter3 = factoryPoint.getParameters().get(2);
        checkBeanKey(parameter3, CoffeeBean.class, null);
        assertTrue(parameter3.isProvider());

        final BeanKey parameter4 = factoryPoint.getParameters().get(3);
        checkBeanKey(parameter4, JavaBean.class, null);
        assertTrue(parameter4.isCollection());
    }

    @Test
    public void getFactoryPointsShouldHandleOverriddenMethods() {
        final BeanKey factory = new BeanKey(ChildFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertEquals(2, factoryPoints.size());

        final FactoryPoint<?> factoryPoint1 = getFactoryPointByName(factoryPoints, "createParentBean");
        checkBeanKey(factoryPoint1.getReturnType(), ParentFactoryCreatedBean.class, "parent");
        checkBeanKey(factoryPoint1.getFactoryKey(), ChildFactoryBean.class, null);
        assertFalse(factoryPoint1.isSingleton());
        assertTrue(factoryPoint1.getParameters().isEmpty());

        final FactoryPoint<?> factoryPoint2 = getFactoryPointByName(factoryPoints, "createChildBean");
        checkBeanKey(factoryPoint2.getReturnType(), ChildFactoryCreatedBean.class, "child");
        checkBeanKey(factoryPoint2.getFactoryKey(), ChildFactoryBean.class, null);
        assertFalse(factoryPoint2.isSingleton());
        assertTrue(factoryPoint2.getParameters().isEmpty());
    }

    @Test
    public void getFactoryPointsShouldIgnoreStaticMethods() {
        final BeanKey factory = new BeanKey(StaticFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertTrue(factoryPoints.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getFactoryPointsShouldFailIfInjectAnnotationPresent() {
        final BeanKey factory = new BeanKey(InjectFactoryBean.class);

        handler.getFactoryPoints(factory);
    }

    @Test
    public void getFactoryPointsShouldReturnInvokableFactoryPoints() {
        final BeanKey factory = new BeanKey(ParameterFactoryBean.class);

        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);

        final FactoryPoint<OneParameterFactoryCreatedBean> factoryPoint =
                (FactoryPoint<OneParameterFactoryCreatedBean>) factoryPoints.get(0);

        final OneParameterFactoryCreatedBean bean = factoryPoint.create(new ParameterFactoryBean(), new HelloBean());
        assertNotNull(bean);

    }

    @Test
    public void getFactoryPointsShouldReplaceTypeVariables() {
        final List<FactoryPoint<?>> factoryPoints = handler.getFactoryPoints(new BeanKey(FantaBottle.class));
        assertEquals(2, factoryPoints.size());

        final FactoryPoint<?> factoryPoint1 = getFactoryPointByName(factoryPoints, "createContainer");
        final Type factoryPoint1ReturnType = factoryPoint1.getReturnType().getBeanType();
        final Type factoryPoint1ExpectedType = new TypeLiteral<Container<Fanta>>() {}.getGenericType();
        assertTrue(factoryPoint1ReturnType.equals(factoryPoint1ExpectedType));

        final FactoryPoint<?> factoryPoint2 = getFactoryPointByName(factoryPoints, "createDualVariableBean");
        final Type factoryPoint2ReturnType = factoryPoint2.getReturnType().getBeanType();
        final Type factoryPoint2ExpectedType = new TypeLiteral<LiquidDualVariableBean<Fanta>>() {}.getGenericType();
        assertTrue(factoryPoint2ReturnType.equals(factoryPoint2ExpectedType));
    }

    private FactoryPoint<?> getFactoryPointByName(final List<FactoryPoint<?>> factoryPoints, final String name) {
        for (final FactoryPoint<?> factoryPoint : factoryPoints) {
            final FactoryPointMethod<?> factoryPointMethod = (FactoryPointMethod<?>) factoryPoint;

            if (factoryPointMethod.getMethod().getName().equals(name)) {
                return factoryPoint;
            }
        }

        assertNotNull(null);
        return null;
    }

    private void checkBeanKey(final BeanKey key, final Class<?> aClass, final String qualifier) {
        assertNotNull(key);
        assertEquals(aClass, key.getBeanClass());
        assertEquals(qualifier, key.getQualifier());
    }
}
