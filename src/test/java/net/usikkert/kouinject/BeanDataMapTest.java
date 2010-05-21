
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

import java.util.ArrayList;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.Dependency;
import net.usikkert.kouinject.beandata.InjectionPoint;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link BeanDataMap}.
 *
 * @author Christian Ihle
 */
public class BeanDataMapTest {

    private BeanDataMap map;

    @Before
    public void createBeanDataMap() {
        map = new BeanDataMap();
    }

    @Test
    public void addBeanDataShouldPutTheBeanInTheMap() {
        assertEquals(0, map.size());

        final BeanData beanData = createBeanData(Object.class);
        final Dependency bean = new Dependency(Object.class);

        map.addBeanData(bean, beanData);

        assertEquals(1, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBeanDataShouldFailToAddTheSameBeanTwice() {
        assertEquals(0, map.size());

        final BeanData beanData = createBeanData(Object.class);
        final Dependency bean = new Dependency(Object.class);

        map.addBeanData(bean, beanData);
        map.addBeanData(bean, beanData);
    }

    @Test
    public void addBeanDataShouldAcceptSeveralDifferentBeans() {
        assertEquals(0, map.size());

        final BeanData setterBean = createBeanData(SetterBean.class);
        final Dependency setterBeanDependency = new Dependency(SetterBean.class);

        map.addBeanData(setterBeanDependency, setterBean);
        assertEquals(1, map.size());

        final BeanData fieldBean = createBeanData(FieldBean.class);
        final Dependency fieldBeanDependency = new Dependency(FieldBean.class);

        map.addBeanData(fieldBeanDependency, fieldBean);
        assertEquals(2, map.size());

        final BeanData helloBean = createBeanData(HelloBean.class);
        final Dependency helloBeanDependency = new Dependency(HelloBean.class);

        map.addBeanData(helloBeanDependency, helloBean);
        assertEquals(3, map.size());
    }

    @Test
    public void getBeanDataShouldFindTheCorrectBeanData() {
        addTestBeans();

        final BeanData setterBean = map.getBeanData(new Dependency(SetterBean.class));
        assertNotNull(setterBean);
        assertTrue(setterBean.getBeanClass().equals(SetterBean.class));

        final BeanData fieldBean = map.getBeanData(new Dependency(FieldBean.class));
        assertNotNull(fieldBean);
        assertTrue(fieldBean.getBeanClass().equals(FieldBean.class));

        final BeanData helloBean = map.getBeanData(new Dependency(HelloBean.class));
        assertNotNull(helloBean);
        assertTrue(helloBean.getBeanClass().equals(HelloBean.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanDataShouldFailIfNoMatchFound() {
        addTestBeans();

        map.getBeanData(new Dependency(CarBean.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getBeanDataShouldFailIfTooManyMatches() {
        addTestBeans();

        map.getBeanData(new Dependency(Object.class));
    }

    @Test
    public void containsBeanDataShouldDetectAddedBeanData() {
        addTestBeans();

        assertTrue(map.containsBeanData(new Dependency(SetterBean.class)));
        assertTrue(map.containsBeanData(new Dependency(FieldBean.class)));
        assertTrue(map.containsBeanData(new Dependency(HelloBean.class)));
    }

    @Test
    public void containsBeanDataShouldHandleMissingBeanData() {
        addTestBeans();

        assertFalse(map.containsBeanData(new Dependency(CarBean.class)));
    }

    private void addTestBeans() {
        final BeanData setterBean = createBeanData(SetterBean.class);
        final Dependency setterBeanDependency = new Dependency(SetterBean.class);
        map.addBeanData(setterBeanDependency, setterBean);

        final BeanData fieldBean = createBeanData(FieldBean.class);
        final Dependency fieldBeanDependency = new Dependency(FieldBean.class);
        map.addBeanData(fieldBeanDependency, fieldBean);

        final BeanData helloBean = createBeanData(HelloBean.class);
        final Dependency helloBeanDependency = new Dependency(HelloBean.class);
        map.addBeanData(helloBeanDependency, helloBean);
    }

    private BeanData createBeanData(final Class<?> beanClass) {
        return new BeanData(beanClass, null, new ArrayList<InjectionPoint>(), false);
    }
}
