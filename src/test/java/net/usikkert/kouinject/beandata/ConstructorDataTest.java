
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

package net.usikkert.kouinject.beandata;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.misc.PrivateBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link ConstructorData}.
 *
 * @author Christian Ihle
 */
public class ConstructorDataTest {

    private BeanKey dependency;
    private Constructor<?> constructor;
    private ConstructorData constructorData;

    @Before
    public void setUp() throws NoSuchMethodException {
        constructor = PrivateBean.class.getDeclaredConstructor(HelloBean.class);
        dependency = new BeanKey(HelloBean.class);
        constructorData = new ConstructorData(constructor, Arrays.asList(dependency));
    }

    @Test
    public void getConstructorShouldReturnConstructorFromConstructorOfConstructorData() {
        assertSame(constructor, constructorData.getConstructor());
    }

    @Test
    public void getDependenciesShouldReturnDependencyFromConstructor() {
        final List<BeanKey> dependencies = constructorData.getDependencies();

        assertNotNull(dependencies);
        assertEquals(1, dependencies.size());
        assertSame(dependency, dependencies.get(0));
    }

    @Test
    public void createInstanceShouldWorkWhenUsedWithCorrectParameter() {
        final HelloBean helloBeanInstance = new HelloBean();

        final Object instance = constructorData.createInstance(new Object[] { helloBeanInstance });
        assertEquals(PrivateBean.class, instance.getClass());

        final PrivateBean privateBean = (PrivateBean) instance;
        assertSame(helloBeanInstance, privateBean.getHelloBeanFromConstructor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInstanceShouldFailIfWrongNumberOfParameters() {
        final HelloBean helloBeanInstance = new HelloBean();

        constructorData.createInstance(new Object[] { helloBeanInstance, helloBeanInstance });
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInstanceShouldFailWrongParameter() {
        final CarBean carBean = new CarBean();

        constructorData.createInstance(new Object[] { carBean });
    }
}
