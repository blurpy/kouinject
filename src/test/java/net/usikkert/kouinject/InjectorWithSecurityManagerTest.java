
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

import static org.junit.Assert.*;

import java.security.AccessController;
import java.security.PrivilegedAction;

import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBeanUsingBean;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of using the injector with a security manager activated.
 *
 * <p>Activate logging with <code>-Djava.security.debug=failure,access</code>.</p>
 *
 * @author Christian Ihle
 */
public class InjectorWithSecurityManagerTest {

    private Injector injector;

    @BeforeClass
    public static void setupPolicyForSecurityManager() {
        System.setProperty("java.security.policy", "src/test/resources/kouinject.policy");
    }

    @Before
    public void setupSecurityManagerAndInjector() {
        System.setSecurityManager(new SecurityManager());
        injector = new DefaultInjector("net.usikkert.kouinject.testbeans.scanned");
    }

    /**
     * Need to remove the security manager, or else all the other tests will fail with maven.
     * Wrapping the call as a privileged action to avoid having to give permissions to all callers in the chain.
     */
    @After
    public void unsetSecurityManager() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                System.setSecurityManager(null);
                return null;
            }
        });
    }

    @Test
    public void getBeanShouldReturnAnInstanceOfABeanWithFieldInjections() {
        verifySecurityManager();

        final FieldBean bean = injector.getBean(FieldBean.class);
        assertNotNull(bean);
    }

    @Test
    public void getBeanShouldReturnAnInstanceOfABeanWithSetterInjections() {
        verifySecurityManager();

        final SetterBean bean = injector.getBean(SetterBean.class);
        assertNotNull(bean);
    }

    @Test
    public void getBeanShouldReturnAnInstanceOfABeanWithConstructorInjections() {
        verifySecurityManager();

        final ConstructorBean bean = injector.getBean(ConstructorBean.class);
        assertNotNull(bean);
    }

    @Test
    public void getBeanShouldReturnAnInstanceOfABeanCreatedByAFactory() {
        verifySecurityManager();

        final SimpleFactoryCreatedBeanUsingBean bean = injector.getBean(SimpleFactoryCreatedBeanUsingBean.class);
        assertNotNull(bean);
    }

    private void verifySecurityManager() {
        assertNotNull(System.getSecurityManager());
    }
}
