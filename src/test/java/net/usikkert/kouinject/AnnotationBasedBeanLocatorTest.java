
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

import java.util.Set;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.BeanCount;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.any.AnyBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.AnimalBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.OrganismBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.PetBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.pets.CatBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.DarkYellowBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;
import net.usikkert.kouinject.testbeans.scanned.scope.PrototypeWithSingletonBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedBeanLocator}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanLocatorTest {

    private ClassLocator classLocator;

    @Before
    public void createClassLocator() {
        classLocator = new ClassPathScanner();
    }

    @Test
    public void findBeansShouldReturnAllComponents() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(classLocator, "net.usikkert.kouinject");
        final Set<BeanKey> beans = beanLocator.findBeans();

        assertEquals(BeanCount.ALL.getNumberOfBeans(), beans.size());

        for (final BeanKey bean : beans) {
            final Class<?> beanClass = bean.getBeanClass();
            assertNotNull(beanClass);
            assertTrue(beanClass.isAnnotationPresent(Component.class));
        }
    }

    @Test
    public void findBeansShouldDetectQualifiers() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(classLocator, "net.usikkert.kouinject.testbeans.scanned.qualifier");
        final Set<BeanKey> beans = beanLocator.findBeans();

        assertEquals(5, beans.size());

        assertTrue(containsBean(beans, BlueBean.class, "Blue"));
        assertTrue(containsBean(beans, GreenBean.class, "Green"));
        assertTrue(containsBean(beans, RedBean.class, "red"));
        assertTrue(containsBean(beans, YellowBean.class, "Yellow"));
        assertTrue(containsBean(beans, DarkYellowBean.class, "darkYellow"));
    }

    @Test
    public void findBeansShouldIgnoreMissingQualifiers() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(classLocator, "net.usikkert.kouinject.testbeans.scanned.coffee");
        final Set<BeanKey> beans = beanLocator.findBeans();

        assertEquals(2, beans.size());

        assertTrue(containsBean(beans, CoffeeBean.class, null));
        assertTrue(containsBean(beans, JavaBean.class, null));
    }

    @Test
    public void findBeansShouldHandleMultipleBasePackages() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(
                classLocator,
                "net.usikkert.kouinject.testbeans.scanned.coffee",
                "net.usikkert.kouinject.testbeans.scanned.any",
                "net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2");
        final Set<BeanKey> beans = beanLocator.findBeans();

        assertEquals(7, beans.size());

        assertTrue(containsBean(beans, CoffeeBean.class, null));
        assertTrue(containsBean(beans, JavaBean.class, null));
        assertTrue(containsBean(beans, AnyBean.class, null));
        assertTrue(containsBean(beans, CatBean.class, "cat"));
        assertTrue(containsBean(beans, AnimalBean.class, "animal"));
        assertTrue(containsBean(beans, OrganismBean.class, "organism"));
        assertTrue(containsBean(beans, PetBean.class, "pet"));

        assertFalse(containsBean(beans, ChildBean.class, null));
        assertFalse(containsBean(beans, PrototypeWithSingletonBean.class, null));
        assertFalse(containsBean(beans, HelloBean.class, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldValidateClassLocatorNotNull() {
        new AnnotationBasedBeanLocator(null, "package");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldValidateBasePackagesNotNull() {
        new AnnotationBasedBeanLocator(classLocator, null);
    }

    private boolean containsBean(final Set<BeanKey> beans, final Class<?> beanClass, final String qualifier) {
        for (final BeanKey bean : beans) {
            if (bean.getBeanClass().equals(beanClass)) {
                if (qualifier == null && bean.getQualifier() == null) {
                    return true;
                }

                else if (qualifier != null && qualifier.equals(bean.getQualifier())) {
                    return true;
                }
            }
        }

        return false;
    }
}
