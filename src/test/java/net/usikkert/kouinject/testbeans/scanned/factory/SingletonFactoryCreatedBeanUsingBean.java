
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

import javax.inject.Inject;

/**
 * A bean using a singleton bean created by a factory.
 *
 * @author Christian Ihle
 */
//@Component TODO fails until factory support is implemented
public class SingletonFactoryCreatedBeanUsingBean {

    @Inject
    private SingletonFactoryCreatedBean singletonFactoryCreatedBean1;

    @Inject
    private SingletonFactoryCreatedBean singletonFactoryCreatedBean2;

    public SingletonFactoryCreatedBean getSingletonFactoryCreatedBean1() {
        return singletonFactoryCreatedBean1;
    }

    public SingletonFactoryCreatedBean getSingletonFactoryCreatedBean2() {
        return singletonFactoryCreatedBean2;
    }
}