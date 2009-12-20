
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

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedBeanLocator}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanLocatorTest {

    private AnnotationBasedBeanLocator beanLocator;

    @Before
    public void createBeanLocator() {
        final ClassLocator classLocator = new ClassPathScanner();
        beanLocator = new AnnotationBasedBeanLocator("net.usikkert.kouinject", classLocator);
    }

    @Test
    public void findBeansShouldReturnAllComponents() {
        final Set<Bean> beans = beanLocator.findBeans();

        assertEquals(19, beans.size());

        for (final Bean bean : beans) {
            final Class<?> beanClass = bean.getBeanClass();
            assertNotNull(beanClass);
            assertTrue(beanClass.isAnnotationPresent(Component.class));
        }
    }
}
