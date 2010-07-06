
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
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

package net.usikkert.kouinject;

import static org.junit.Assert.*;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link SingletonMap}.
 *
 * @author Christian Ihle
 */
public class SingletonMapTest {

    private SingletonMap map;

    @Before
    public void createSingletonMap() {
        map = new SingletonMap();
    }

    @Test
    public void addSingletonShouldPutTheBeanInTheSingletonMap() {
        assertEquals(0, map.size());

        final Object beanToAdd = new Object();
        final BeanKey bean = new BeanKey(Object.class);

        map.addSingleton(bean, beanToAdd);

        assertEquals(1, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSingletonShouldFailToAddTheSameBeanTwice() {
        assertEquals(0, map.size());

        final Object beanToAdd = new Object();
        final BeanKey bean = new BeanKey(Object.class);

        map.addSingleton(bean, beanToAdd);
        map.addSingleton(bean, beanToAdd);
    }

    @Test
    public void addSingletonShouldAcceptSeveralDifferentBeans() {
        assertEquals(0, map.size());

        final SetterBean setterBean = new SetterBean();
        final BeanKey setterBeanDependency = new BeanKey(SetterBean.class);

        map.addSingleton(setterBeanDependency, setterBean);
        assertEquals(1, map.size());

        final FieldBean fieldBean = new FieldBean();
        final BeanKey fieldBeanDependency = new BeanKey(FieldBean.class);

        map.addSingleton(fieldBeanDependency, fieldBean);
        assertEquals(2, map.size());

        final HelloBean helloBean = new HelloBean();
        final BeanKey helloBeanDependency = new BeanKey(HelloBean.class);

        map.addSingleton(helloBeanDependency, helloBean);
        assertEquals(3, map.size());
    }

    @Test
    public void getSingletonShouldFindTheCorrectSingleton() {
        addTestBeans();

        final Object setterBean = map.getSingleton(new BeanKey(SetterBean.class), true);
        assertTrue(setterBean instanceof SetterBean);

        final Object fieldBean = map.getSingleton(new BeanKey(FieldBean.class), true);
        assertTrue(fieldBean instanceof FieldBean);

        final Object helloBean = map.getSingleton(new BeanKey(HelloBean.class), true);
        assertTrue(helloBean instanceof HelloBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSingletonShouldFailIfNoMatchFoundIfThrowExIsTrue() {
        addTestBeans();

        map.getSingleton(new BeanKey(CarBean.class), true);
    }

    @Test
    public void getSingletonShouldReturnNullIfNoMatchFoundIfThrowExIsFalse() {
        addTestBeans();

        final Object carBean = map.getSingleton(new BeanKey(CarBean.class), false);

        assertNull(carBean);
    }

    @Test(expected = IllegalStateException.class)
    public void getSingletonShouldFailIfTooManyMatches() {
        addTestBeans();

        map.getSingleton(new BeanKey(Object.class), true);
    }

    @Test
    public void containsSingletonShouldDetectAddedBeans() {
        addTestBeans();

        assertTrue(map.containsSingleton(new BeanKey(SetterBean.class)));
        assertTrue(map.containsSingleton(new BeanKey(FieldBean.class)));
        assertTrue(map.containsSingleton(new BeanKey(HelloBean.class)));
    }

    @Test
    public void containsSingletonShouldHandleMissingBeans() {
        addTestBeans();

        assertFalse(map.containsSingleton(new BeanKey(CarBean.class)));
    }

    private void addTestBeans() {
        final SetterBean setterBean = new SetterBean();
        final BeanKey setterBeanDependency = new BeanKey(SetterBean.class);
        map.addSingleton(setterBeanDependency, setterBean);

        final FieldBean fieldBean = new FieldBean();
        final BeanKey fieldBeanDependency = new BeanKey(FieldBean.class);
        map.addSingleton(fieldBeanDependency, fieldBean);

        final HelloBean helloBean = new HelloBean();
        final BeanKey helloBeanDependency = new BeanKey(HelloBean.class);
        map.addSingleton(helloBeanDependency, helloBean);
    }
}
