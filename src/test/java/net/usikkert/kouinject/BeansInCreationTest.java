
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

import net.usikkert.kouinject.beandata.Dependency;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link BeansInCreation}.
 *
 * @author Christian Ihle
 */
public class BeansInCreationTest {

    private BeansInCreation beansInCreation;
    private Dependency helloBean;
    private Dependency fieldBean;
    private Dependency setterBean;

    @Before
    public void createBeansInCreation() {
        beansInCreation = new BeansInCreation();
        helloBean = new Dependency(HelloBean.class);
        setterBean = new Dependency(SetterBean.class);
        fieldBean = new Dependency(FieldBean.class);
    }

    @Test
    public void addBeanShouldPutTheBeanInTheList() {
        assertEquals(0, beansInCreation.size());

        assertFalse(beansInCreation.containsBean(helloBean));
        assertFalse(beansInCreation.containsBean(setterBean));
        assertFalse(beansInCreation.containsBean(fieldBean));

        beansInCreation.addBean(helloBean);
        assertEquals(1, beansInCreation.size());

        beansInCreation.addBean(setterBean);
        assertEquals(2, beansInCreation.size());

        beansInCreation.addBean(fieldBean);
        assertEquals(3, beansInCreation.size());

        assertTrue(beansInCreation.containsBean(helloBean));
        assertTrue(beansInCreation.containsBean(setterBean));
        assertTrue(beansInCreation.containsBean(fieldBean));
    }

    @Test(expected = IllegalStateException.class)
    public void addBeanShouldFailIfBeanAlreadyExists() {
        assertEquals(0, beansInCreation.size());
        assertFalse(beansInCreation.containsBean(helloBean));

        beansInCreation.addBean(helloBean);
        assertEquals(1, beansInCreation.size());
        assertTrue(beansInCreation.containsBean(helloBean));

        beansInCreation.addBean(helloBean);
    }

    @Test
    public void removeBeanShouldRemoveTheBeanFromTheList() {
        beansInCreation.addBean(helloBean);
        beansInCreation.addBean(setterBean);
        beansInCreation.addBean(fieldBean);

        assertEquals(3, beansInCreation.size());
        assertTrue(beansInCreation.containsBean(helloBean));

        beansInCreation.removeBean(helloBean);

        assertEquals(2, beansInCreation.size());
        assertFalse(beansInCreation.containsBean(helloBean));
    }
}
