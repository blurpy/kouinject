
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

import net.usikkert.kouinject.testbeans.scanned.profile.AcceptanceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.DataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.DevelopmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.EnvironmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.EnvironmentUsingBean;
import net.usikkert.kouinject.testbeans.scanned.profile.InMemoryDataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.JndiDataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProductionBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileABean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileACBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileBBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileCBean;

import org.junit.Test;

/**
 * Tests of injection in beans with profiles.
 *
 * @author Christian Ihle
 */
public class ProfileBeanInjectionTest {

    private static final String SCANNED = "net.usikkert.kouinject.testbeans.scanned";

    private Injector injector;

    @Test
    public void checkAcceptanceBean() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value()), SCANNED);

        final AcceptanceBean acceptanceBean = injector.getBean(AcceptanceBean.class);
        assertNotNull(acceptanceBean);

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());
    }

    @Test
    public void checkDevelopmentBean() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value()), SCANNED);

        final DevelopmentBean developmentBean = injector.getBean(DevelopmentBean.class);
        assertNotNull(developmentBean);

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithAcceptanceProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithDevelopmentProfile() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithProductionProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(ProductionBean.class, environmentBean.getClass());
    }

    @Test
    public void checkInMemoryDataSourceBean() {
        injector = new DefaultInjector(Arrays.asList(IN_MEMORY.value()), SCANNED);

        final InMemoryDataSourceBean inMemoryDataSourceBean = injector.getBean(InMemoryDataSourceBean.class);
        assertNotNull(inMemoryDataSourceBean);

        final DataSourceBean dataSourceBean = injector.getBean(DataSourceBean.class);
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkJndiDataSourceBean() {
        injector = new DefaultInjector(Arrays.asList(JNDI.value()), SCANNED);

        final JndiDataSourceBean jndiDataSourceBean = injector.getBean(JndiDataSourceBean.class);
        assertNotNull(jndiDataSourceBean);

        final DataSourceBean dataSourceBean = injector.getBean(DataSourceBean.class);
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkProductionBean() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value()), SCANNED);

        final ProductionBean productionBean = injector.getBean(ProductionBean.class);
        assertNotNull(productionBean);

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(ProductionBean.class, environmentBean.getClass());
    }

    @Test
    public void checkProfileABean() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_A.value()), SCANNED);

        final ProfileABean profileABean = injector.getBean(ProfileABean.class);
        assertNotNull(profileABean);
    }

    @Test
    public void checkProfileACBean() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_A.value()), SCANNED);

        final ProfileACBean profileACBean = injector.getBean(ProfileACBean.class);
        assertNotNull(profileACBean);
    }

    @Test
    public void checkProfileBBean() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_B.value()), SCANNED);

        final ProfileBBean profileBBean = injector.getBean(ProfileBBean.class);
        assertNotNull(profileBBean);
    }

    @Test
    public void checkProfileCBean() {
        injector = new DefaultInjector(Arrays.asList(PROFILE_C.value()), SCANNED);

        final ProfileCBean profileCBean = injector.getBean(ProfileCBean.class);
        assertNotNull(profileCBean);
    }
}
