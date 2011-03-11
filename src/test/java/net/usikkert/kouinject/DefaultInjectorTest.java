
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

package net.usikkert.kouinject;

import static net.usikkert.kouinject.testbeans.scanned.profile.Profiles.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import net.usikkert.kouinject.testbeans.BeanCount;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileABean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileACBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
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
        assertEquals(BeanCount.SCANNED_WITHOUT_QUALIFIER.getNumberOfBeans(), beans.size());
    }

    @Test
    public void getBeansWithAnyQualifierShouldFindAllBeans() {
        final Collection<Object> beans = injector.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());
    }

    @Test
    public void injectorShouldSupportLoadingBeansFromMultipleBasePackages() {
        final Injector newInjector = new DefaultInjector(
                "net.usikkert.kouinject.testbeans.scanned.qualifier",
                "net.usikkert.kouinject.testbeans.scanned.collection");

        assertNotNull(newInjector.getBean(BlueBean.class));
        assertNotNull(newInjector.getBean(HungryBean.class));

        try {
            newInjector.getBean(HelloBean.class);
            fail("Should not get an instance of a bean class not scanned");
        }

        catch (IllegalArgumentException e) {
            // ok
        }
    }

    @Test
    public void getBeanShouldFindFactoryCreatedBean() {
        final SimpleFactoryCreatedBean bean = injector.getBean(SimpleFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailToGetProfiledBeanWhenNoProfilesActive() {
        injector.getBean(ProfileABean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailToGetProfiledBeanWhenDifferentProfileIsActive() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_B.value()), "net.usikkert.kouinject.testbeans.scanned");

        injector.getBean(ProfileABean.class);
    }

    @Test
    public void getBeanShouldFindBeansWithActiveProfile() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_A.value()), "net.usikkert.kouinject.testbeans.scanned");

        final ProfileABean profileABean = injector.getBean(ProfileABean.class);
        assertNotNull(profileABean);

        final ProfileACBean profileACBean = injector.getBean(ProfileACBean.class);
        assertNotNull(profileACBean);
    }

    @Test
    public void getBeansShouldFindBeansWithActiveProfile() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_A.value(), PROFILE_B.value(), PROFILE_C.value(), PRODUCTION.value()),
                "net.usikkert.kouinject.testbeans.scanned");

        final Collection<Object> beans = injector.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED_WITH_PROFILED.getNumberOfBeans(), beans.size());
    }
}
