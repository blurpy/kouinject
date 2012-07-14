
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

package net.usikkert.kouinject.beandata;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link FieldData}.
 *
 * @author Christian Ihle
 */
public class FieldDataTest {

    private HelloBean helloBeanField;

    private Field field;
    private BeanKey dependency;
    private FieldData fieldData;

    @Before
    public void setUp() throws NoSuchFieldException {
        field = FieldDataTest.class.getDeclaredField("helloBeanField");
        dependency = new BeanKey(HelloBean.class);
        fieldData = new FieldData(field, dependency);
    }

    @Test
    public void getFieldShouldReturnFieldFromConstructor() {
        assertSame(field, fieldData.getField());
    }

    @Test
    public void getDependencyShouldReturnDependencyFromConstructor() {
        assertSame(dependency, fieldData.getDependency());

        final List<BeanKey> dependencies = fieldData.getDependencies();
        assertNotNull(dependencies);
        assertEquals(1, dependencies.size());
        assertSame(dependency, dependencies.get(0));
    }

    @Test
    public void injectShouldWorkWhenUsedWithCorrectParameter() {
        final HelloBean helloBeanInstance = new HelloBean();
        assertNull(helloBeanField);

        fieldData.inject(this, new Object[] { helloBeanInstance });

        assertSame(helloBeanInstance, helloBeanField);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectShouldFailIfWrongNumberOfParameters() {
        final HelloBean helloBeanInstance = new HelloBean();
        assertNull(helloBeanField);

        fieldData.inject(this, new Object[] { helloBeanInstance, helloBeanInstance });
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectShouldFailWrongParameter() {
        final CarBean carBean = new CarBean();
        assertNull(helloBeanField);

        fieldData.inject(this, new Object[] { carBean });
    }
}
