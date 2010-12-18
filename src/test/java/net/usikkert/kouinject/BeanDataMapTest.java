
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
import java.util.Collection;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.InjectionPoint;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;

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
        final BeanKey bean = new BeanKey(Object.class);

        map.addBeanData(bean, beanData);

        assertEquals(1, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBeanDataShouldFailToAddTheSameBeanTwice() {
        assertEquals(0, map.size());

        final BeanData beanData = createBeanData(Object.class);
        final BeanKey bean = new BeanKey(Object.class);

        map.addBeanData(bean, beanData);
        map.addBeanData(bean, beanData);
    }

    @Test
    public void addBeanDataShouldAcceptSeveralDifferentBeans() {
        assertEquals(0, map.size());

        final BeanData setterBean = createBeanData(SetterBean.class);
        final BeanKey setterBeanDependency = new BeanKey(SetterBean.class);

        map.addBeanData(setterBeanDependency, setterBean);
        assertEquals(1, map.size());

        final BeanData fieldBean = createBeanData(FieldBean.class);
        final BeanKey fieldBeanDependency = new BeanKey(FieldBean.class);

        map.addBeanData(fieldBeanDependency, fieldBean);
        assertEquals(2, map.size());

        final BeanData helloBean = createBeanData(HelloBean.class);
        final BeanKey helloBeanDependency = new BeanKey(HelloBean.class);

        map.addBeanData(helloBeanDependency, helloBean);
        assertEquals(3, map.size());
    }

    @Test
    public void getBeanDataShouldFindTheCorrectBeanData() {
        addTestBeans();

        final BeanData setterBean = map.getBeanData(new BeanKey(SetterBean.class));
        assertNotNull(setterBean);
        assertTrue(setterBean.getBeanClass().equals(SetterBean.class));

        final BeanData fieldBean = map.getBeanData(new BeanKey(FieldBean.class));
        assertNotNull(fieldBean);
        assertTrue(fieldBean.getBeanClass().equals(FieldBean.class));

        final BeanData helloBean = map.getBeanData(new BeanKey(HelloBean.class));
        assertNotNull(helloBean);
        assertTrue(helloBean.getBeanClass().equals(HelloBean.class));

        final BeanData greenBean = map.getBeanData(new BeanKey(GreenBean.class, "Green"));
        assertNotNull(greenBean);
        assertTrue(greenBean.getBeanClass().equals(GreenBean.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanDataShouldFailIfNoMatchFound() {
        addTestBeans();

        map.getBeanData(new BeanKey(CarBean.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getBeanDataShouldFailIfTooManyMatches() {
        addTestBeans();

        map.getBeanData(new BeanKey(Object.class));
    }

    @Test
    public void containsBeanDataShouldDetectAddedBeanData() {
        addTestBeans();

        assertTrue(map.containsBeanData(new BeanKey(SetterBean.class)));
        assertTrue(map.containsBeanData(new BeanKey(FieldBean.class)));
        assertTrue(map.containsBeanData(new BeanKey(HelloBean.class)));
        assertTrue(map.containsBeanData(new BeanKey(GreenBean.class, "Green")));
    }

    @Test
    public void containsBeanDataShouldHandleMissingBeanData() {
        addTestBeans();

        assertFalse(map.containsBeanData(new BeanKey(CarBean.class)));
    }

    @Test
    public void findBeanKeysWithoutQualifierShouldReturnBeansWithoutQualifier() {
        addTestBeans();

        final Collection<BeanKey> beanKeys = map.findBeanKeys(new BeanKey(Object.class));

        assertNotNull(beanKeys);
        assertEquals(3, beanKeys.size());
        assertTrue(beanKeys.contains(new BeanKey(SetterBean.class)));
        assertTrue(beanKeys.contains(new BeanKey(FieldBean.class)));
        assertTrue(beanKeys.contains(new BeanKey(HelloBean.class)));
    }

    @Test
    public void findBeanKeysWithQualifierShouldReturnBeansWithQualifier() {
        addTestBeans();

        final Collection<BeanKey> beanKeys = map.findBeanKeys(new BeanKey(Object.class, "Green"));

        assertNotNull(beanKeys);
        assertEquals(1, beanKeys.size());
        assertTrue(beanKeys.contains(new BeanKey(GreenBean.class, "Green")));
    }

    @Test
    public void findBeanKeysShouldHandleNoMatches() {
        addTestBeans();

        final Collection<BeanKey> beanKeys = map.findBeanKeys(new BeanKey(CarBean.class));

        assertNotNull(beanKeys);
        assertEquals(0, beanKeys.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanKeyForBeanDataShouldFailIfNoMatches() {
        addTestBeans();

        map.getBeanKeyForBeanData(createBeanData(NoBean.class));
    }

    @Test
    public void getBeanKeyForBeanDataShouldReturnTheCorrectKey() {
        addTestBeans();

        final BeanKey setterBeanKey = map.getBeanKeyForBeanData(map.getBeanData(new BeanKey(SetterBean.class)));
        assertBeanKey(setterBeanKey, SetterBean.class, null);

        final BeanKey fieldBeanKey = map.getBeanKeyForBeanData(map.getBeanData(new BeanKey(FieldBean.class)));
        assertBeanKey(fieldBeanKey, FieldBean.class, null);

        final BeanKey helloBeanKey = map.getBeanKeyForBeanData(map.getBeanData(new BeanKey(HelloBean.class)));
        assertBeanKey(helloBeanKey, HelloBean.class, null);

        final BeanKey greenBeanKey = map.getBeanKeyForBeanData(map.getBeanData(new BeanKey(GreenBean.class)));
        assertBeanKey(greenBeanKey, GreenBean.class, "Green");
    }

    private void assertBeanKey(final BeanKey beanKey, final Class<?> expectedClass, final String expectedQualifier) {
        assertNotNull(beanKey);
        assertEquals(expectedClass, beanKey.getBeanClass());
        assertEquals(expectedQualifier, beanKey.getQualifier());
    }

    private void addTestBeans() {
        final BeanData setterBean = createBeanData(SetterBean.class);
        final BeanKey setterBeanDependency = new BeanKey(SetterBean.class);
        map.addBeanData(setterBeanDependency, setterBean);

        final BeanData fieldBean = createBeanData(FieldBean.class);
        final BeanKey fieldBeanDependency = new BeanKey(FieldBean.class);
        map.addBeanData(fieldBeanDependency, fieldBean);

        final BeanData helloBean = createBeanData(HelloBean.class);
        final BeanKey helloBeanDependency = new BeanKey(HelloBean.class);
        map.addBeanData(helloBeanDependency, helloBean);

        final BeanData greenBean = createBeanData(GreenBean.class);
        final BeanKey greenBeanDependency = new BeanKey(GreenBean.class, "Green");
        map.addBeanData(greenBeanDependency, greenBean);
    }

    private BeanData createBeanData(final Class<?> beanClass) {
        return new BeanData(beanClass, null, new ArrayList<InjectionPoint>(), false);
    }
}
