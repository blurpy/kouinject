
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
import net.usikkert.kouinject.testbeans.scanned.profile.ArchiveBean;
import net.usikkert.kouinject.testbeans.scanned.profile.DataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.DevelopmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.EnvironmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.EnvironmentUsingBean;
import net.usikkert.kouinject.testbeans.scanned.profile.InMemoryDataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.JndiDataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.LocalArchiveBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProductionBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileABean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileACBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileBBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileCBean;
import net.usikkert.kouinject.testbeans.scanned.profile.RemoteArchiveBean;

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
    public void checkAcceptanceBeanWithInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value(), IN_MEMORY.value()), SCANNED);

        final AcceptanceBean acceptanceBean = injector.getBean(AcceptanceBean.class);
        assertNotNull(acceptanceBean);

        final DataSourceBean dataSourceBean = acceptanceBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());
    }

    @Test
    public void checkAcceptanceBeanWithJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value(), JNDI.value()), SCANNED);

        final AcceptanceBean acceptanceBean = injector.getBean(AcceptanceBean.class);
        assertNotNull(acceptanceBean);

        final DataSourceBean dataSourceBean = acceptanceBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());
    }

    @Test
    public void checkDevelopmentBeanWithInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value(), IN_MEMORY.value()), SCANNED);

        final DevelopmentBean developmentBean = injector.getBean(DevelopmentBean.class);
        assertNotNull(developmentBean);

        final DataSourceBean dataSourceBean = developmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());
    }

    @Test
    public void checkDevelopmentBeanWithJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value(), JNDI.value()), SCANNED);

        final DevelopmentBean developmentBean = injector.getBean(DevelopmentBean.class);
        assertNotNull(developmentBean);

        final DataSourceBean dataSourceBean = developmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithAcceptanceAndInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value(), IN_MEMORY.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithAcceptanceAndJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value(), JNDI.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(AcceptanceBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithDevelopmentAndInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value(), IN_MEMORY.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithDevelopmentAndJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value(), JNDI.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(DevelopmentBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithProductionAndInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value(), IN_MEMORY.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(ProductionBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());
    }

    @Test
    public void checkEnvironmentUsingBeanWithProductionAndJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value(), JNDI.value()), SCANNED);

        final EnvironmentUsingBean environmentUsingBean = injector.getBean(EnvironmentUsingBean.class);
        assertNotNull(environmentUsingBean);

        final EnvironmentBean environmentBean = environmentUsingBean.getEnvironmentBean();
        assertNotNull(environmentBean);
        assertEquals(ProductionBean.class, environmentBean.getClass());

        final DataSourceBean dataSourceBean = environmentBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());
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
    public void checkLocalArchiveBean() {
        injector = new DefaultInjector(Arrays.asList(DEVELOPMENT.value()), SCANNED);

        final LocalArchiveBean localArchiveBean = injector.getBean(LocalArchiveBean.class);
        assertNotNull(localArchiveBean);

        final ArchiveBean archiveBean = injector.getBean(ArchiveBean.class);
        assertNotNull(archiveBean);
        assertEquals(LocalArchiveBean.class, archiveBean.getClass());
    }

    @Test
    public void checkProductionBeanWithInMemoryProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value(), IN_MEMORY.value()), SCANNED);

        final ProductionBean productionBean = injector.getBean(ProductionBean.class);
        assertNotNull(productionBean);

        final DataSourceBean dataSourceBean = productionBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(InMemoryDataSourceBean.class, dataSourceBean.getClass());

        final EnvironmentBean environmentBean = injector.getBean(EnvironmentBean.class);
        assertNotNull(environmentBean);
        assertEquals(ProductionBean.class, environmentBean.getClass());
    }

    @Test
    public void checkProductionBeanWithJndiProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value(), JNDI.value()), SCANNED);

        final ProductionBean productionBean = injector.getBean(ProductionBean.class);
        assertNotNull(productionBean);

        final DataSourceBean dataSourceBean = productionBean.getDataSourceBean();
        assertNotNull(dataSourceBean);
        assertEquals(JndiDataSourceBean.class, dataSourceBean.getClass());

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

    @Test
    public void checkRemoteArchiveBeanWithProductionProfile() {
        injector = new DefaultInjector(Arrays.asList(PRODUCTION.value()), SCANNED);

        final RemoteArchiveBean remoteArchiveBean = injector.getBean(RemoteArchiveBean.class);
        assertNotNull(remoteArchiveBean);

        final ArchiveBean archiveBean = injector.getBean(ArchiveBean.class);
        assertNotNull(archiveBean);
        assertEquals(RemoteArchiveBean.class, archiveBean.getClass());
    }

    @Test
    public void checkRemoteArchiveBeanWithAcceptanceProfile() {
        injector = new DefaultInjector(Arrays.asList(ACCEPTANCE.value()), SCANNED);

        final RemoteArchiveBean remoteArchiveBean = injector.getBean(RemoteArchiveBean.class);
        assertNotNull(remoteArchiveBean);

        final ArchiveBean archiveBean = injector.getBean(ArchiveBean.class);
        assertNotNull(archiveBean);
        assertEquals(RemoteArchiveBean.class, archiveBean.getClass());
    }
}
