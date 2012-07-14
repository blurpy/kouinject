
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

package net.usikkert.kouinject.testbeans.scanned.factory;

/**
 * A bean created using a child factory.
 *
 * @author Christian Ihle
 */
public class ChildFactoryCreatedBean {

    private boolean createdByParent;
    private boolean createdByChild;

    public boolean isCreatedByChild() {
        return createdByChild;
    }

    public void setCreatedByChild(final boolean createdByChild) {
        this.createdByChild = createdByChild;
    }

    public boolean isCreatedByParent() {
        return createdByParent;
    }

    public void setCreatedByParent(final boolean createdByParent) {
        this.createdByParent = createdByParent;
    }
}
