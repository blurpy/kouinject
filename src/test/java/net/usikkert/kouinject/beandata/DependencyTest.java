
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

import org.junit.Test;

/**
 * Test of {@link Dependency}.
 *
 * @author Christian Ihle
 */
public class DependencyTest {

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameObject() {
        final Dependency green = new Dependency(GreenBean.class, false, "green");
        final Dependency green2 = green;

        assertSame(green, green2);
        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameBeanClassAndSameQualifier() {
        final Dependency green = new Dependency(GreenBean.class, false, "green");
        final Dependency green2 = new Dependency(GreenBean.class, false, "green");

        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameBeanClassAndNullQualifier() {
        final Dependency green = new Dependency(GreenBean.class, false, null);
        final Dependency green2 = new Dependency(GreenBean.class, false, null);

        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentTypes() {
        final Dependency green = new Dependency(GreenBean.class, false, null);
        final Object object = new Object();

        assertFalse(green.equals(object));
        assertFalse(object.equals(green));
        assertFalse(green.hashCode() == object.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForNull() {
        final Dependency green = new Dependency(GreenBean.class, false, null);

        assertFalse(green.equals(null));
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentBeanClasses() {
        final Dependency green = new Dependency(GreenBean.class, false, null);
        final Dependency yellow = new Dependency(YellowBean.class, false, null);

        assertFalse(green.equals(yellow));
        assertFalse(yellow.equals(green));
        assertFalse(green.hashCode() == yellow.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForSameBeanClassWithDifferentQualifier() {
        final Dependency green = new Dependency(GreenBean.class, false, "green1");
        final Dependency green2 = new Dependency(GreenBean.class, false, "green2");

        assertFalse(green.equals(green2));
        assertFalse(green2.equals(green));
        assertFalse(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForSameBeanClassWithOneNullQualifier() {
        final Dependency green = new Dependency(GreenBean.class, false, "green");
        final Dependency green2 = new Dependency(GreenBean.class, false, null);

        assertFalse(green.equals(green2));
        assertFalse(green2.equals(green));
        assertFalse(green.hashCode() == green2.hashCode());
    }

    @Test
    public void canInjectShouldBeFalseForNull() {
        final Dependency green = new Dependency(GreenBean.class, false, null);

        assertFalse(green.canInject(null));
    }

    @Test
    public void canInjectShouldAcceptSameClassAndSuperClassWhenNoQualifier() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, null);
        final Dependency theColorBean = new Dependency(ColorBean.class, false, null);
        final Dependency theObjectBean = new Dependency(Object.class, false, null);

        final Dependency theColorField = new Dependency(ColorBean.class, false, null);

        assertTrue(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldOnlyAcceptSameClassWhenNoQualifierEvenIfBeansHaveQualifier() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, "green");
        final Dependency theColorBean = new Dependency(ColorBean.class, false, "colorfull");
        final Dependency theObjectBean = new Dependency(Object.class, false, "dull");

        final Dependency theColorField = new Dependency(ColorBean.class, false, null);

        assertFalse(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldAcceptSuperClassWhenQualifierMatch() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, "green");
        final Dependency theColorBean = new Dependency(ColorBean.class, false, null);
        final Dependency theObjectBean = new Dependency(Object.class, false, null);

        final Dependency theColorField = new Dependency(ColorBean.class, false, "green");

        assertTrue(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldAcceptSameClassWhenQualifierMatch() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, null);
        final Dependency theColorBean = new Dependency(ColorBean.class, false, "green");
        final Dependency theObjectBean = new Dependency(Object.class, false, null);

        final Dependency theColorField = new Dependency(ColorBean.class, false, "green");

        assertFalse(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldDenyBeansThatMatchWithClassIfQualifierIsMissing() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, null);
        final Dependency theColorBean = new Dependency(ColorBean.class, false, null);
        final Dependency theObjectBean = new Dependency(Object.class, false, null);

        final Dependency theColorField = new Dependency(ColorBean.class, false, "red");

        assertFalse(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldDenyBeansThatMatchWithClassIfQualifierIsDifferent() {
        final Dependency theGreenBean = new Dependency(GreenBean.class, false, "green");
        final Dependency theColorBean = new Dependency(ColorBean.class, false, "colorfull");
        final Dependency theObjectBean = new Dependency(Object.class, false, "dull");

        final Dependency theColorField = new Dependency(ColorBean.class, false, "red");

        assertFalse(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }
}
