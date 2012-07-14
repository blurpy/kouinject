
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

package net.usikkert.kouinject;

import static net.usikkert.kouinject.testbeans.scanned.profile.Profiles.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.testbeans.BeanCount;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.Dao;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.ItemDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.MySqlDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.OrderDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.PersonDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.OneStuffBean;
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

    @Test
    public void getBeanShouldFindGenericBean() {
        final List<OneStuffBean> bean = injector.getBean(new TypeLiteral<List<OneStuffBean>>() {});
        assertNotNull(bean);
        assertEquals(1, bean.size());

        final OneStuffBean oneStuffBean = bean.get(0);
        assertNotNull(oneStuffBean);
    }

    @Test
    public void getBeanShouldFindGenericBeanWithQualifier() {
        final Dao<MySqlDriver> bean = injector.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "item");
        assertNotNull(bean);
        assertEquals(ItemDaoBean.class, bean.getClass());
    }

    @Test
    public void getBeansShouldFindGenericBean() {
        final Collection<List<OneStuffBean>> beans = injector.getBeans(new TypeLiteral<List<OneStuffBean>>() {});
        assertNotNull(beans);
        assertEquals(1, beans.size());

        final List<OneStuffBean> oneStuffBeans = beans.iterator().next();
        assertNotNull(oneStuffBeans);
        assertEquals(1, oneStuffBeans.size());

        final OneStuffBean oneStuffBean = oneStuffBeans.get(0);
        assertNotNull(oneStuffBean);
    }

    @Test
    public void getBeansShouldFindGenericBeansWithQualifier() {
        final Collection<Dao<MySqlDriver>> beans = injector.getBeans(new TypeLiteral<Dao<MySqlDriver>>() {}, "any");
        assertNotNull(beans);
        assertEquals(3, beans.size());

        assertTrue(containsBean(beans, ItemDaoBean.class));
        assertTrue(containsBean(beans, PersonDaoBean.class));
        assertTrue(containsBean(beans, OrderDaoBean.class));
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
        injector = new DefaultInjector(Arrays.asList(PROFILE_A.value(), PROFILE_B.value(), PROFILE_C.value(),
                PRODUCTION.value(), IN_MEMORY.value(), SWING.value()),
                "net.usikkert.kouinject.testbeans.scanned");

        final Collection<Object> beans = injector.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED_WITH_PROFILED.getNumberOfBeans(), beans.size());
    }

    private boolean containsBean(final Collection<?> collection, final Class<?> beanClass) {
        for (final Object object : collection) {
            if (object.getClass().equals(beanClass)) {
                return true;
            }
        }

        return false;
    }
}
