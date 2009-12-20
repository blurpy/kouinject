
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

package net.usikkert.kouinject;

import static org.junit.Assert.*;

import java.util.Set;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.beandata.Bean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

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
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator("net.usikkert.kouinject", classLocator);
        final Set<Bean> beans = beanLocator.findBeans();

        assertEquals(23, beans.size());

        for (final Bean bean : beans) {
            final Class<?> beanClass = bean.getBeanClass();
            assertNotNull(beanClass);
            assertTrue(beanClass.isAnnotationPresent(Component.class));
        }
    }

    @Test
    public void findBeansShouldDetectQualifiers() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator("net.usikkert.kouinject.testbeans.scanned.qualifier", classLocator);
        final Set<Bean> beans = beanLocator.findBeans();

        assertEquals(4, beans.size());

        assertTrue(containsBean(beans, BlueBean.class, "Blue"));
        assertTrue(containsBean(beans, GreenBean.class, "Green"));
        assertTrue(containsBean(beans, RedBean.class, "red"));
        assertTrue(containsBean(beans, YellowBean.class, "Yellow"));
    }

    @Test
    public void findBeansShouldIgnoreMissingQualifiers() {
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator("net.usikkert.kouinject.testbeans.scanned.coffee", classLocator);
        final Set<Bean> beans = beanLocator.findBeans();

        assertEquals(2, beans.size());

        assertTrue(containsBean(beans, CoffeeBean.class, null));
        assertTrue(containsBean(beans, JavaBean.class, null));
    }

    private boolean containsBean(final Set<Bean> beans, final Class<?> beanClass, final String qualifier) {
        for (final Bean bean : beans) {
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
