
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
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

import org.junit.Test;

/**
 * Test of {@link BeanKey}.
 *
 * @author Christian Ihle
 */
public class BeanKeyTest {

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameObject() {
        final BeanKey green = new BeanKey(GreenBean.class, "green");
        final BeanKey green2 = green;

        assertSame(green, green2);
        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameBeanClassAndSameQualifier() {
        final BeanKey green = new BeanKey(GreenBean.class, "green");
        final BeanKey green2 = new BeanKey(GreenBean.class, "green");

        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameBeanClassAndSameQualifierWithDifferentCase() {
        final BeanKey green = new BeanKey(GreenBean.class, "GreEn");
        final BeanKey green2 = new BeanKey(GreenBean.class, "gReen");

        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForSameBeanClassAndNullQualifier() {
        final BeanKey green = new BeanKey(GreenBean.class);
        final BeanKey green2 = new BeanKey(GreenBean.class);

        assertTrue(green.equals(green2));
        assertTrue(green2.equals(green));
        assertTrue(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentTypes() {
        final BeanKey green = new BeanKey(GreenBean.class);
        final Object object = new Object();

        assertFalse(green.equals(object));
        assertFalse(object.equals(green));
        assertFalse(green.hashCode() == object.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForNull() {
        final BeanKey green = new BeanKey(GreenBean.class);

        assertFalse(green.equals(null));
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentBeanClasses() {
        final BeanKey green = new BeanKey(GreenBean.class);
        final BeanKey yellow = new BeanKey(YellowBean.class);

        assertFalse(green.equals(yellow));
        assertFalse(yellow.equals(green));
        assertFalse(green.hashCode() == yellow.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForSameBeanClassWithDifferentQualifier() {
        final BeanKey green = new BeanKey(GreenBean.class, "green1");
        final BeanKey green2 = new BeanKey(GreenBean.class, "green2");

        assertFalse(green.equals(green2));
        assertFalse(green2.equals(green));
        assertFalse(green.hashCode() == green2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForSameBeanClassWithOneNullQualifier() {
        final BeanKey green = new BeanKey(GreenBean.class, "green");
        final BeanKey green2 = new BeanKey(GreenBean.class);

        assertFalse(green.equals(green2));
        assertFalse(green2.equals(green));
        assertFalse(green.hashCode() == green2.hashCode());
    }

    @Test
    public void canInjectShouldBeFalseForNull() {
        final BeanKey green = new BeanKey(GreenBean.class);

        assertFalse(green.canInject(null));
    }

    @Test
    public void canInjectShouldAcceptSameClassAndSuperClassWhenNoQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorBean = new BeanKey(ColorBean.class);
        final BeanKey theObjectBean = new BeanKey(Object.class);

        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertTrue(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldOnlyAcceptSameClassWhenNoQualifierEvenIfBeansHaveQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorBean = new BeanKey(ColorBean.class, "colorfull");
        final BeanKey theObjectBean = new BeanKey(Object.class, "dull");

        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertFalse(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldAcceptSuperClassWhenQualifierMatch() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorBean = new BeanKey(ColorBean.class);
        final BeanKey theObjectBean = new BeanKey(Object.class);

        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertTrue(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldAcceptSameClassWhenQualifierMatch() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorBean = new BeanKey(ColorBean.class, "green");
        final BeanKey theObjectBean = new BeanKey(Object.class);

        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertFalse(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldAcceptSameClassWhenQualifierMatchWithDifferentCase() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorBean = new BeanKey(ColorBean.class, "greeN");
        final BeanKey theObjectBean = new BeanKey(Object.class);

        final BeanKey theColorField = new BeanKey(ColorBean.class, "Green");

        assertFalse(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldDenyBeansThatMatchWithClassIfQualifierIsMissing() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorBean = new BeanKey(ColorBean.class);
        final BeanKey theObjectBean = new BeanKey(Object.class);

        final BeanKey theColorField = new BeanKey(ColorBean.class, "red");

        assertFalse(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectShouldDenyBeansThatMatchWithClassIfQualifierIsDifferent() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorBean = new BeanKey(ColorBean.class, "colorfull");
        final BeanKey theObjectBean = new BeanKey(Object.class, "dull");

        final BeanKey theColorField = new BeanKey(ColorBean.class, "red");

        assertFalse(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInject(theColorBean));
        assertFalse(theColorField.canInject(theObjectBean));
    }

    @Test
    public void canInjectSubclassWhenNoQualifiers() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertTrue(theColorField.canInject(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenSameQualifiers() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertTrue(theColorField.canInject(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenFieldHasAnyQualifierAndBeanHasNone() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorField = new BeanKey(ColorBean.class, "any");

        assertTrue(theColorField.canInject(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenFieldHasAnyQualifierAndBeanHasOtherQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "any");

        assertTrue(theColorField.canInject(theGreenBean));
    }

    @Test
    public void cantInjectSubclassWhenBeanHasAnyQualifierAndFieldHasNone() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "any");
        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertFalse(theColorField.canInject(theGreenBean));
    }

    @Test
    public void cantInjectSubclassWhenBeanHasAnyQualifierAndFieldHasOtherQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "any");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertFalse(theColorField.canInject(theGreenBean));
    }

    @Test
    public void defaultBeanKey() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "none");
        assertFalse(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertTrue(theGreenBean.isBeanForCreation());
    }

    @Test
    public void providerBeanKey() {
        final BeanKey theGreenBean = new ProviderBeanKey(GreenBean.class, "none");
        assertTrue(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }

    @Test
    public void collectionBeanKey() {
        final BeanKey theGreenBean = new CollectionBeanKey(GreenBean.class, "none");
        assertFalse(theGreenBean.isProvider());
        assertTrue(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }

    @Test
    public void collectionProviderBeanKey() {
        final BeanKey theGreenBean = new CollectionProviderBeanKey(GreenBean.class, "none");
        assertFalse(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertTrue(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }
}
