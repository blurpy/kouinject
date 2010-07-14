
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

import java.util.Collection;

import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link DefaultInjector}.
 *
 * <p>These tests are just to verify that the injector works. More thorough tests
 * are done elsewhere.</p>
 *
 * @author Christian Ihle
 */
public class DefaultInjectorTest {

    private DefaultInjector injector;

    @Before
    public void createInjector() {
        injector = new DefaultInjector("net.usikkert.kouinject.testbeans.scanned");
    }

    @Test
    public void getBeanShouldFindBeanAvailableInTheBasePackage() {
        final HelloBean helloBean = injector.getBean(HelloBean.class);

        assertNotNull(helloBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldThrowExceptionIfRequestedBeanIsUnavailableInTheBasePackage() {
        injector.getBean(TheInterfaceUser.class);
    }

    @Test
    public void getBeanWithQualifierShouldFindCorrectBean() {
        final ColorBean colorBean = injector.getBean(ColorBean.class, "red");

        assertNotNull(colorBean);
        assertEquals(RedBean.class, colorBean.getClass());
    }

    @Test
    public void getBeansWithoutQualifierShouldFindBeansWithoutQualifier() {
        final Collection<Object> beans = injector.getBeans(Object.class);

        assertNotNull(beans);
        assertEquals(28, beans.size());
    }

    @Test
    public void getBeansWithAnyQualifierShouldFindAllBeans() {
        final Collection<Object> beans = injector.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(41, beans.size());
    }
}
