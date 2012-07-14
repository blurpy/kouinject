
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

package net.usikkert.kouinject.testbeans.scanned.generics.wildcard;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.MiddleBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.SuperBean;

/**
 * A factory bean for creating beans that may be injected using generics with a wildcard.
 *
 * @author Christian Ihle
 */
@Component
public class WildcardFactoryBean {

    @Produces
    public Container<ChildBean> createChildBeanContainer() {
        return new Container<ChildBean>(new ChildBean());
    }

    @Produces
    public Container<MiddleBean> createMiddleBeanContainer() {
        return new Container<MiddleBean>(new MiddleBean());
    }

    @Produces
    public Container<SuperBean> createSuperBeanContainer() {
        return new Container<SuperBean>(new SuperBean());
    }

    @Produces
    public Container<? extends Wheel> createWheelContainer() {
        return new Container<Wheel>(new RubberWheel());
    }
}
