
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
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
    public void equalsAndHashcodeShouldBeFalseForDifferentClasses() {
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
    public void equalsAndHashcodeShouldBeTrueForTheSameBeanType() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});

        assertTrue(helloBeanKey1.equals(helloBeanKey2));
        assertTrue(helloBeanKey2.equals(helloBeanKey1));
        assertTrue(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeTrueForTheSameBeanTypeWithTheSameQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hello");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hello");

        assertTrue(helloBeanKey1.equals(helloBeanKey2));
        assertTrue(helloBeanKey2.equals(helloBeanKey1));
        assertTrue(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForTheSameBeanTypeWithDifferentQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hello");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hi");

        assertFalse(helloBeanKey1.equals(helloBeanKey2));
        assertFalse(helloBeanKey2.equals(helloBeanKey1));
        assertFalse(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForTheSameBeanTypeWithOneNullQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hi");

        assertFalse(helloBeanKey1.equals(helloBeanKey2));
        assertFalse(helloBeanKey2.equals(helloBeanKey1));
        assertFalse(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentBeanType() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<ArrayList<HelloBean>>() {});

        assertFalse(helloBeanKey1.equals(helloBeanKey2));
        assertFalse(helloBeanKey2.equals(helloBeanKey1));
        assertFalse(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentBeanSubType() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<RedBean>>() {});

        assertFalse(helloBeanKey1.equals(helloBeanKey2));
        assertFalse(helloBeanKey2.equals(helloBeanKey1));
        assertFalse(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void equalsAndHashcodeShouldBeFalseForDifferentBeanSubTypeWithEqualQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "hello");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<RedBean>>() {}, "hello");

        assertFalse(helloBeanKey1.equals(helloBeanKey2));
        assertFalse(helloBeanKey2.equals(helloBeanKey1));
        assertFalse(helloBeanKey1.hashCode() == helloBeanKey2.hashCode());
    }

    @Test
    public void canInjectShouldBeFalseForNull() {
        final BeanKey green = new BeanKey(GreenBean.class);

        assertFalse(green.canInject(null));

        assertFalse(green.canInjectFromFactory(null));
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

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
        assertFalse(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
        assertFalse(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
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

        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
        assertFalse(theColorField.canInjectFromFactory(theColorBean));
        assertFalse(theColorField.canInjectFromFactory(theObjectBean));
    }

    @Test
    public void canInjectSubclassWhenNoQualifiers() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertTrue(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenSameQualifiers() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertTrue(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenFieldHasAnyQualifierAndBeanHasNone() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);
        final BeanKey theColorField = new BeanKey(ColorBean.class, "any");

        assertTrue(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void canInjectSubclassWhenFieldHasAnyQualifierAndBeanHasOtherQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "any");

        assertTrue(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void cantInjectSubclassWhenBeanHasAnyQualifierAndFieldHasNoneExceptIfFactory() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "any");
        final BeanKey theColorField = new BeanKey(ColorBean.class);

        assertFalse(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void cantInjectSubclassWhenBeanHasAnyQualifierAndFieldHasOtherQualifierExceptIfFactory() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "any");
        final BeanKey theColorField = new BeanKey(ColorBean.class, "green");

        assertFalse(theColorField.canInject(theGreenBean));

        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void canInjectEqualArrays() {
        final BeanKey bean1 = new BeanKey(HelloBean[].class);
        final BeanKey bean2 = new BeanKey(HelloBean[].class);

        assertTrue(bean1.canInject(bean2));
        assertTrue(bean1.canInjectFromFactory(bean2));
    }

    @Test
    public void canInjectSubclassArray() {
        final BeanKey theGreenBean = new BeanKey(GreenBean[].class);
        final BeanKey theColorField = new BeanKey(ColorBean[].class);

        assertTrue(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theGreenBean));

        assertFalse(theGreenBean.canInject(theColorField));
        assertFalse(theGreenBean.canInjectFromFactory(theColorField));
    }

    @Test
    public void cantInjectDifferentArrays() {
        final BeanKey bean1 = new BeanKey(HelloBean[].class);
        final BeanKey bean2 = new BeanKey(GreenBean[].class);

        assertFalse(bean1.canInject(bean2));
        assertFalse(bean1.canInjectFromFactory(bean2));

        assertFalse(bean2.canInject(bean1));
        assertFalse(bean2.canInjectFromFactory(bean1));
    }

    @Test
    public void canInjectMatchingArraysWithEqualQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean[].class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean[].class, "green");

        assertTrue(theColorField.canInject(theGreenBean));
        assertTrue(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void cantInjectMatchingArraysWithWrongQualifier() {
        final BeanKey theGreenBean = new BeanKey(GreenBean[].class, "green");
        final BeanKey theColorField = new BeanKey(ColorBean[].class, "red");

        assertFalse(theColorField.canInject(theGreenBean));
        assertFalse(theColorField.canInjectFromFactory(theGreenBean));
    }

    @Test
    public void canInjectShouldReturnTrueForEqualTypes() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));
    }

    @Test
    public void canInjectShouldReturnTrueForEqualTypesWhenThisHasNoQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "other");

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        assertFalse(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnTrueForEqualTypesWithSameQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "qualifier");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "qualifier");

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));
    }

    @Test
    public void canInjectShouldReturnTrueForEqualTypesWithAnyQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "any");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "other");

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        // Special treatment when injecting from factory with @Any
        assertTrue(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnFalseForEqualTypesWithDifferentQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "one");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "two");

        assertFalse(helloBeanKey1.canInject(helloBeanKey2));
        assertFalse(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        assertFalse(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnTrueForTypesThatInherit() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<ArrayList<HelloBean>>() {});

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        assertFalse(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnTrueForTypesThatInheritWithSameQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "qualifier");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<ArrayList<HelloBean>>() {}, "qualifier");

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        assertFalse(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnTrueForTypesThatInheritWithAnyQualifier() {
        final BeanKey helloBeanKey1 = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "any");
        final BeanKey helloBeanKey2 = new BeanKey(new TypeLiteral<ArrayList<HelloBean>>() {}, "other");

        assertTrue(helloBeanKey1.canInject(helloBeanKey2));
        assertTrue(helloBeanKey1.canInjectFromFactory(helloBeanKey2));

        assertFalse(helloBeanKey2.canInject(helloBeanKey1));
        assertFalse(helloBeanKey2.canInjectFromFactory(helloBeanKey1));
    }

    @Test
    public void canInjectShouldReturnFalseForDifferentSubTypes() {
        final BeanKey helloBeanKey = new BeanKey(new TypeLiteral<List<HelloBean>>() {});
        final BeanKey redBeanKey = new BeanKey(new TypeLiteral<List<RedBean>>() {});

        assertFalse(redBeanKey.canInject(helloBeanKey));
        assertFalse(redBeanKey.canInjectFromFactory(helloBeanKey));

        assertFalse(helloBeanKey.canInject(redBeanKey));
        assertFalse(helloBeanKey.canInjectFromFactory(redBeanKey));
    }

    @Test
    public void canInjectShouldReturnFalseForDifferentSubTypesWithTheSameQualifier() {
        final BeanKey helloBeanKey = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "qualifier");
        final BeanKey redBeanKey = new BeanKey(new TypeLiteral<List<RedBean>>() {}, "qualifier");

        assertFalse(redBeanKey.canInject(helloBeanKey));
        assertFalse(redBeanKey.canInjectFromFactory(helloBeanKey));

        assertFalse(helloBeanKey.canInject(redBeanKey));
        assertFalse(helloBeanKey.canInjectFromFactory(redBeanKey));
    }

    @Test
    public void canInjectShouldReturnFalseForDifferentSubTypesWithTheAnyQualifier() {
        final BeanKey helloBeanKey = new BeanKey(new TypeLiteral<List<HelloBean>>() {}, "any");
        final BeanKey redBeanKey = new BeanKey(new TypeLiteral<List<RedBean>>() {}, "any");

        assertFalse(redBeanKey.canInject(helloBeanKey));
        assertFalse(redBeanKey.canInjectFromFactory(helloBeanKey));

        assertFalse(helloBeanKey.canInject(redBeanKey));
        assertFalse(helloBeanKey.canInjectFromFactory(redBeanKey));
    }

    @Test
    public void hasTheAnyQualifierShouldReturnFalseOnNull() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class);

        assertNull(theGreenBean.getQualifier());
        assertFalse(theGreenBean.hasTheAnyQualifier());
    }

    @Test
    public void hasTheAnyQualifierShouldReturnFalseWhenNotAny() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "green");

        assertEquals("green", theGreenBean.getQualifier());
        assertFalse(theGreenBean.hasTheAnyQualifier());
    }

    @Test
    public void hasTheAnyQualifierShouldReturnTrueWhenAny() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "any");
        assertEquals("any", theGreenBean.getQualifier());
        assertTrue(theGreenBean.hasTheAnyQualifier());

        final BeanKey theColorBean = new BeanKey(ColorBean.class, "ANY");
        assertEquals("ANY", theColorBean.getQualifier());
        assertTrue(theColorBean.hasTheAnyQualifier());
    }

    @Test
    public void defaultBeanKey() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "none");
        assertFalse(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertTrue(theGreenBean.isBeanForCreation());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void defaultBeanKeyShouldFailToGetActualBeanKey() {
        final BeanKey theGreenBean = new BeanKey(GreenBean.class, "none");
        theGreenBean.getActualBeanKey();
    }

    @Test
    public void providerBeanKey() {
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new ProviderBeanKey(greenBean, "none");

        assertTrue(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void providerBeanKeyShouldFailToGetActualBeanKey() {
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new ProviderBeanKey(greenBean, "none");

        theGreenBean.getActualBeanKey();
    }

    @Test
    public void collectionBeanKey() {
        final TypeLiteral<Collection<GreenBean>> greenBeanCollection = new TypeLiteral<Collection<GreenBean>>() {};
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new CollectionBeanKey(greenBeanCollection, greenBean, "none");

        assertFalse(theGreenBean.isProvider());
        assertTrue(theGreenBean.isCollection());
        assertFalse(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }

    @Test
    public void collectionBeanKeyShouldReturnActualBeanKey() {
        final TypeLiteral<Collection<GreenBean>> greenBeanCollection = new TypeLiteral<Collection<GreenBean>>() {};
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new CollectionBeanKey(greenBeanCollection, greenBean, "none");

        final BeanKey actualBeanKey = theGreenBean.getActualBeanKey();
        assertNotNull(actualBeanKey);
        assertEquals(greenBeanCollection.getGenericType(), actualBeanKey.getBeanType());
        assertEquals(greenBeanCollection.getGenericClass(), actualBeanKey.getBeanClass());
        assertEquals("none", actualBeanKey.getQualifier());
    }

    @Test
    public void collectionProviderBeanKey() {
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new CollectionProviderBeanKey(greenBean, "none");

        assertFalse(theGreenBean.isProvider());
        assertFalse(theGreenBean.isCollection());
        assertTrue(theGreenBean.isCollectionProvider());
        assertFalse(theGreenBean.isBeanForCreation());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void collectionProviderBeanKeyShouldFailToGetActualBeanKey() {
        final TypeLiteral<GreenBean> greenBean = new TypeLiteral<GreenBean>() {};
        final BeanKey theGreenBean = new CollectionProviderBeanKey(greenBean, "none");

        theGreenBean.getActualBeanKey();
    }

    @Test
    public void getBeanTypeShouldReturnClassWhenUsedWithConstructorForClass() {
        final BeanKey beanKey = new BeanKey(String.class);

        assertEquals(String.class, beanKey.getBeanClass());
        assertEquals(String.class, beanKey.getBeanType());
    }

    @Test
    public void getBeanTypeShouldReturnClassWhenUsedWithConstructorForClassAndQualifier() {
        final BeanKey beanKey = new BeanKey(String.class, "q");

        assertEquals(String.class, beanKey.getBeanClass());
        assertEquals(String.class, beanKey.getBeanType());
    }

    @Test
    public void constructorWithTypeShouldSetTypeAndClass() {
        final TypeLiteral<List<String>> beanType = new TypeLiteral<List<String>>() {};
        final BeanKey beanKey = new BeanKey(beanType);

        assertSame(beanType.getGenericClass(), beanKey.getBeanClass());
        assertSame(beanType.getGenericType(), beanKey.getBeanType());
    }

    @Test
    public void constructorWithTypeAndQualifierShouldSetTypeAndClassAndQualifier() {
        final TypeLiteral<List<String>> beanType = new TypeLiteral<List<String>>() {};
        final BeanKey beanKey = new BeanKey(beanType, "q");

        assertSame(beanType.getGenericClass(), beanKey.getBeanClass());
        assertSame(beanType.getGenericType(), beanKey.getBeanType());
        assertEquals("q", beanKey.getQualifier());
    }

    @Test
    public void constructorWithTypeAndQualifierShouldSetTypeAndClassAndQualifierForProvider() {
        final TypeLiteral<List<String>> beanType = new TypeLiteral<List<String>>() {};
        final BeanKey beanKey = new ProviderBeanKey(beanType, "q");

        assertSame(beanType.getGenericClass(), beanKey.getBeanClass());
        assertSame(beanType.getGenericType(), beanKey.getBeanType());
        assertEquals("q", beanKey.getQualifier());
    }

    @Test
    public void constructorWithTypeAndQualifierShouldSetTypeAndClassAndQualifierForCollection() {
        final TypeLiteral<Collection<String>> collectionBeanType = new TypeLiteral<Collection<String>>() {};
        final TypeLiteral<String> beanType = new TypeLiteral<String>() {};
        final BeanKey beanKey = new CollectionBeanKey(collectionBeanType, beanType, "q");

        assertSame(beanType.getGenericClass(), beanKey.getBeanClass());
        assertSame(beanType.getGenericType(), beanKey.getBeanType());
        assertEquals("q", beanKey.getQualifier());
    }

    @Test
    public void constructorWithTypeAndQualifierShouldSetTypeAndClassAndQualifierForCollectionProvider() {
        final TypeLiteral<List<String>> beanType = new TypeLiteral<List<String>>() {};
        final BeanKey beanKey = new CollectionProviderBeanKey(beanType, "q");

        assertSame(beanType.getGenericClass(), beanKey.getBeanClass());
        assertSame(beanType.getGenericType(), beanKey.getBeanType());
        assertEquals("q", beanKey.getQualifier());
    }
}
