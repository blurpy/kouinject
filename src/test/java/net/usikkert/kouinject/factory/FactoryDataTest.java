
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

import java.util.Arrays;
import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link FactoryData}.
 *
 * @author Christian Ihle
 */
public class FactoryDataTest {

    private FactoryData factoryData;

    private BeanKey factoryKey;
    private FactoryPoint redFactoryPoint;
    private FactoryPoint greenFactoryPoint;
    private FactoryPoint blueFactoryPoint;

    @Before
    public void setUp() {
        factoryKey = new BeanKey(HelloBean.class);

        redFactoryPoint = createFactoryPoint(RedBean.class);
        greenFactoryPoint = createFactoryPoint(GreenBean.class);
        blueFactoryPoint = createFactoryPoint(BlueBean.class);

        final List<FactoryPoint> factoryPoints = Arrays.asList(redFactoryPoint, greenFactoryPoint, blueFactoryPoint);
        factoryData = new FactoryData(factoryKey, factoryPoints);
    }

    @Test
    public void getFactoryKeyShouldReturnKeyFromConstructor() {
        assertSame(factoryKey, factoryData.getFactoryKey());
    }

    @Test
    public void hasFactoryPointShouldReturnTrueWhenOneMatch() {
        assertTrue(factoryData.hasFactoryPoint(new BeanKey(RedBean.class)));
        assertTrue(factoryData.hasFactoryPoint(new BeanKey(GreenBean.class)));
        assertTrue(factoryData.hasFactoryPoint(new BeanKey(BlueBean.class)));
    }

    @Test
    public void hasFactoryPointShouldReturnFalseWhenNoMatch() {
        assertFalse(factoryData.hasFactoryPoint(new BeanKey(YellowBean.class)));
    }

    @Test(expected = IllegalStateException.class)
    public void hasFactoryPointShouldFailWhenMoreThanOneMatch() {
        factoryData.hasFactoryPoint(new BeanKey(ColorBean.class));
    }

    @Test
    public void getFactoryPointShouldFindTheCorrectFactoryPointFromBeanClass() {
        final FactoryPoint redLocalFactoryPoint = factoryData.getFactoryPoint(new BeanKey(RedBean.class));
        assertSame(redFactoryPoint, redLocalFactoryPoint);

        final FactoryPoint greenLocalFactoryPoint = factoryData.getFactoryPoint(new BeanKey(GreenBean.class));
        assertSame(greenFactoryPoint, greenLocalFactoryPoint);

        final FactoryPoint blueLocalFactoryPoint = factoryData.getFactoryPoint(new BeanKey(BlueBean.class));
        assertSame(blueFactoryPoint, blueLocalFactoryPoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFactoryPointShouldFailWhenNoMatch() {
        factoryData.getFactoryPoint(new BeanKey(YellowBean.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getFactoryPointShouldFailWhenMoreThanOneMatch() {
        factoryData.getFactoryPoint(new BeanKey(ColorBean.class));
    }

    private FactoryPoint createFactoryPoint(final Class<?> returnClass) {
        final FactoryPoint factoryPoint = mock(FactoryPoint.class);
        when(factoryPoint.getReturnType()).thenReturn(new BeanKey(returnClass));

        return factoryPoint;
    }
}
