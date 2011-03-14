
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
import net.usikkert.kouinject.testbeans.scanned.profile.LocalArchiveBean;
import net.usikkert.kouinject.testbeans.scanned.profile.RemoteArchiveBean;
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

        map.addBeanData(createBeanData(Object.class));

        assertEquals(1, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBeanDataShouldFailToAddTheSameBeanTwice() {
        assertEquals(0, map.size());

        final BeanData beanData = createBeanData(Object.class);

        map.addBeanData(beanData);
        map.addBeanData(beanData);
    }

    @Test
    public void addBeanDataShouldAcceptSeveralDifferentBeans() {
        assertEquals(0, map.size());

        map.addBeanData(createBeanData(SetterBean.class));
        assertEquals(1, map.size());

        map.addBeanData(createBeanData(FieldBean.class));
        assertEquals(2, map.size());

        map.addBeanData(createBeanData(HelloBean.class));
        assertEquals(3, map.size());
    }

    @Test
    public void addBeanDataShouldHandleAddingDataForBeansWithInheritanceRegardlessOfOrder1() {
        assertEquals(0, map.size());

        map.addBeanData(createBeanData(LocalArchiveBean.class));
        map.addBeanData(createBeanData(RemoteArchiveBean.class));

        assertEquals(2, map.size());
    }

    @Test
    public void addBeanDataShouldHandleAddingDataForBeansWithInheritanceRegardlessOfOrder2() {
        assertEquals(0, map.size());

        map.addBeanData(createBeanData(RemoteArchiveBean.class));
        map.addBeanData(createBeanData(LocalArchiveBean.class));

        assertEquals(2, map.size());
    }

    @Test
    public void getBeanDataShouldFindTheCorrectBeanData() {
        addTestBeans();

        final BeanKey setterBeanKey = new BeanKey(SetterBean.class);
        final BeanData setterBean = map.getBeanData(setterBeanKey);
        assertNotNull(setterBean);
        assertTrue(setterBean.getBeanKey().equals(setterBeanKey));

        final BeanKey fieldBeanKey = new BeanKey(FieldBean.class);
        final BeanData fieldBean = map.getBeanData(fieldBeanKey);
        assertNotNull(fieldBean);
        assertTrue(fieldBean.getBeanKey().equals(fieldBeanKey));

        final BeanKey helloBeanKey = new BeanKey(HelloBean.class);
        final BeanData helloBean = map.getBeanData(helloBeanKey);
        assertNotNull(helloBean);
        assertTrue(helloBean.getBeanKey().equals(helloBeanKey));

        final BeanKey greenBeanKey = new BeanKey(GreenBean.class, "Green");
        final BeanData greenBean = map.getBeanData(greenBeanKey);
        assertNotNull(greenBean);
        assertTrue(greenBean.getBeanKey().equals(greenBeanKey));
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
    public void getBeanDataShouldHandleGettingSubclassWhenSuperclassAlsoAvailable() {
        map.addBeanData(createBeanData(LocalArchiveBean.class));
        map.addBeanData(createBeanData(RemoteArchiveBean.class));

        final BeanKey beanKey = new BeanKey(RemoteArchiveBean.class);
        final BeanData beanData = map.getBeanData(beanKey);

        assertNotNull(beanData);
        assertTrue(beanData.getBeanKey().equals(beanKey));
    }

    @Test(expected = IllegalStateException.class)
    public void getBeanDataShouldFailToGetSuperclassWhenSubclassAlsoAvailable() {
        map.addBeanData(createBeanData(LocalArchiveBean.class));
        map.addBeanData(createBeanData(RemoteArchiveBean.class));

        final BeanKey beanKey = new BeanKey(LocalArchiveBean.class);
        map.getBeanData(beanKey);
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

    private void addTestBeans() {
        map.addBeanData(createBeanData(SetterBean.class));
        map.addBeanData(createBeanData(FieldBean.class));
        map.addBeanData(createBeanData(HelloBean.class));
        map.addBeanData(createBeanData(GreenBean.class, "Green"));
    }

    private BeanData createBeanData(final Class<?> beanClass) {
        return createBeanData(beanClass, null);
    }

    private BeanData createBeanData(final Class<?> beanClass, final String qualifier) {
        return new BeanData(new BeanKey(beanClass, qualifier), null, new ArrayList<InjectionPoint>(), false);
    }
}
