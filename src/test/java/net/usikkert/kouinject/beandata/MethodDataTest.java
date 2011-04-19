
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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link MethodData}.
 *
 * @author Christian Ihle
 */
public class MethodDataTest {

    private HelloBean helloBeanFromSetter;

    private BeanKey dependency;
    private Method method;
    private MethodData methodData;

    @Before
    public void setUp() throws NoSuchMethodException {
        method = MethodDataTest.class.getDeclaredMethod("setHelloBeanFromSetter", HelloBean.class);
        dependency = new BeanKey(HelloBean.class);
        methodData = new MethodData(method, Arrays.asList(dependency));
    }

    @Test
    public void getMethodShouldReturnMethodFromConstructor() {
        assertSame(method, methodData.getMethod());
    }

    @Test
    public void getDependenciesShouldReturnDependencyFromConstructor() {
        final List<BeanKey> dependencies = methodData.getDependencies();

        assertNotNull(dependencies);
        assertEquals(1, dependencies.size());
        assertSame(dependency, dependencies.get(0));
    }

    @Test
    public void injectShouldWorkWhenUsedWithCorrectParameter() {
        final HelloBean helloBeanInstance = new HelloBean();
        assertNull(helloBeanFromSetter);

        methodData.inject(this, new Object[] { helloBeanInstance });

        assertSame(helloBeanInstance, helloBeanFromSetter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectShouldFailIfWrongNumberOfParameters() {
        final HelloBean helloBeanInstance = new HelloBean();
        assertNull(helloBeanFromSetter);

        methodData.inject(this, new Object[] { helloBeanInstance, helloBeanInstance });
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectShouldFailWrongParameter() {
        final CarBean carBean = new CarBean();
        assertNull(helloBeanFromSetter);

        methodData.inject(this, new Object[] { carBean });
    }

    private void setHelloBeanFromSetter(final HelloBean helloBeanFromSetter) {
        this.helloBeanFromSetter = helloBeanFromSetter;
    }
}
