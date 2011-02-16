
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

package net.usikkert.kouinject.factory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link FactoryMap}.
 *
 * @author Christian Ihle
 */
public class FactoryMapTest {

    private FactoryMap map;

    @Before
    public void createFactoryMap() {
        map = new FactoryMap();
    }

    @Test
    public void addFactoryPointShouldPutTheFactoryPointInTheMap() {
        assertEquals(0, map.size());

        map.addFactoryPoint(createFactoryPoint(Object.class));

        assertEquals(1, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addFactoryPointShouldFailToAddTheSameFactoryPointTwice() {
        assertEquals(0, map.size());

        final FactoryPoint factoryPoint = createFactoryPoint(Object.class);

        map.addFactoryPoint(factoryPoint);
        map.addFactoryPoint(factoryPoint);
    }

    @Test
    public void addFactoryPointShouldAcceptSeveralDifferentFactoryPoints() {
        assertEquals(0, map.size());

        map.addFactoryPoint(createFactoryPoint(SetterBean.class));
        assertEquals(1, map.size());

        map.addFactoryPoint(createFactoryPoint(FieldBean.class));
        assertEquals(2, map.size());

        map.addFactoryPoint(createFactoryPoint(HelloBean.class));
        assertEquals(3, map.size());
    }

    @Test
    public void getFactoryPointShouldFindTheCorrectFactoryPoint() {
        addTestFactoryPoints();

        final FactoryPoint setterBean = map.getFactoryPoint(new BeanKey(SetterBean.class));
        assertNotNull(setterBean);
        assertTrue(setterBean.getReturnType().getBeanClass().equals(SetterBean.class));

        final FactoryPoint fieldBean = map.getFactoryPoint(new BeanKey(FieldBean.class));
        assertNotNull(fieldBean);
        assertTrue(fieldBean.getReturnType().getBeanClass().equals(FieldBean.class));

        final FactoryPoint helloBean = map.getFactoryPoint(new BeanKey(HelloBean.class));
        assertNotNull(helloBean);
        assertTrue(helloBean.getReturnType().getBeanClass().equals(HelloBean.class));

        final FactoryPoint greenBean = map.getFactoryPoint(new BeanKey(GreenBean.class, "Green"));
        assertNotNull(greenBean);
        assertTrue(greenBean.getReturnType().getBeanClass().equals(GreenBean.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFactoryPointShouldFailIfNoMatchFound() {
        addTestFactoryPoints();

        map.getFactoryPoint(new BeanKey(CarBean.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getFactoryPointShouldFailIfTooManyMatches() {
        addTestFactoryPoints();

        map.getFactoryPoint(new BeanKey(Object.class));
    }

    @Test
    public void containsFactoryPointShouldDetectAddedFactoryPoints() {
        addTestFactoryPoints();

        assertTrue(map.containsFactoryPoint(new BeanKey(SetterBean.class)));
        assertTrue(map.containsFactoryPoint(new BeanKey(FieldBean.class)));
        assertTrue(map.containsFactoryPoint(new BeanKey(HelloBean.class)));
        assertTrue(map.containsFactoryPoint(new BeanKey(GreenBean.class, "Green")));
    }

    @Test
    public void containsFactoryPointShouldHandleMissingFactoryPoint() {
        addTestFactoryPoints();

        assertFalse(map.containsFactoryPoint(new BeanKey(CarBean.class)));
    }

    @Test
    public void findFactoryPointKeysWithoutQualifierShouldReturnBeanKeysWithoutQualifier() {
        addTestFactoryPoints();

        final Collection<BeanKey> beanKeys = map.findFactoryPointKeys(new BeanKey(Object.class));

        assertNotNull(beanKeys);
        assertEquals(3, beanKeys.size());
        assertTrue(beanKeys.contains(new BeanKey(SetterBean.class)));
        assertTrue(beanKeys.contains(new BeanKey(FieldBean.class)));
        assertTrue(beanKeys.contains(new BeanKey(HelloBean.class)));
    }

    @Test
    public void findFactoryPointKeysWithQualifierShouldReturnBeanKeysWithQualifier() {
        addTestFactoryPoints();

        final Collection<BeanKey> beanKeys = map.findFactoryPointKeys(new BeanKey(Object.class, "Green"));

        assertNotNull(beanKeys);
        assertEquals(1, beanKeys.size());
        assertTrue(beanKeys.contains(new BeanKey(GreenBean.class, "Green")));
    }

    @Test
    public void findFactoryPointKeysShouldHandleNoMatches() {
        addTestFactoryPoints();

        final Collection<BeanKey> beanKeys = map.findFactoryPointKeys(new BeanKey(CarBean.class));

        assertNotNull(beanKeys);
        assertEquals(0, beanKeys.size());
    }

    private void addTestFactoryPoints() {
        map.addFactoryPoint(createFactoryPoint(SetterBean.class));
        map.addFactoryPoint(createFactoryPoint(FieldBean.class));
        map.addFactoryPoint(createFactoryPoint(HelloBean.class));
        map.addFactoryPoint(createFactoryPoint(GreenBean.class, "Green"));
    }

    private FactoryPoint createFactoryPoint(final Class<?> beanClass) {
        final FactoryPoint factoryPoint = mock(FactoryPoint.class);
        when(factoryPoint.getReturnType()).thenReturn(new BeanKey(beanClass, null));

        return factoryPoint;
    }

    private FactoryPoint createFactoryPoint(final Class<?> beanClass, final String qualifier) {
        final FactoryPoint factoryPoint = mock(FactoryPoint.class);
        when(factoryPoint.getReturnType()).thenReturn(new BeanKey(beanClass, qualifier));

        return factoryPoint;
    }
}
