
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

import net.usikkert.kouinject.annotation.Produces;

/**
 * Test of inheritance in factory beans.
 *
 * @author Christian Ihle
 */
public class ParentFactoryBean {

    // Not overridden
    @Produces @Named("parent")
    public ParentFactoryCreatedBean createParentBean() {
        final ParentFactoryCreatedBean bean = new ParentFactoryCreatedBean();
        bean.setCreatedByParent(true);

        return bean;
    }

    // Overridden by method with @Produces
    @Named("parent")
    public ChildFactoryCreatedBean createChildBean() {
        throw new UnsupportedOperationException("Overridden method should never be called");
    }

    // Overridden by method without @Produces
    @Produces @Named("parent")
    public OverriddenFactoryCreatedBean createOverriddenBean() {
        throw new UnsupportedOperationException("Overridden method should never be called");
    }
}