
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

package net.usikkert.kouinject.testbeans.notscanned.mock;

import static org.mockito.Mockito.*;

import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;

/**
 * Factory bean that creates mocks, for testing mock handling.
 *
 * @author Christian Ihle
 */
public class MockFactoryBean {

    @Produces
    public HelloBean createHelloBean() {
        return mock(HelloBean.class);
    }

    @Produces
    public AbstractBeanImpl createAbstractBean() {
        return mock(AbstractBeanImpl.class);
    }

    @Produces
    public InterfaceBean createInterfaceBean() {
        return mock(InterfaceBean.class);
    }
}
