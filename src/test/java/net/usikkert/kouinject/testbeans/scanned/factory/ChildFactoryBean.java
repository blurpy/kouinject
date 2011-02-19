
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

package net.usikkert.kouinject.testbeans.scanned.factory;

import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.notscanned.factory.OverriddenFactoryCreatedBean;

/**
 * Test of inheritance in factory beans.
 *
 * @author Christian Ihle
 */
@Component
public class ChildFactoryBean extends ParentFactoryBean {

    // Overrides method without @Produces
    @Override
    @Produces @Named("child")
    public ChildFactoryCreatedBean createChildBean() {
        final ChildFactoryCreatedBean bean = new ChildFactoryCreatedBean();
        bean.setCreatedByChild(true);

        return bean;
    }

    // Overrides method with @Produces
    @Override
    @Named("child")
    public OverriddenFactoryCreatedBean createOverriddenBean() {
        throw new UnsupportedOperationException("Method without @Produces overriding method" +
                " with @Produces should never be called");
    }
}
