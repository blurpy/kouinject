
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
 * A factory created bean using a standalone bean, using a factory created bean, and so on.
 *
 * @author Christian Ihle
 */
public class NestedFactoryCreatedBean {

    private final FactoryCreatedBeanUsingBean factoryCreatedBeanUsingBean;

    public NestedFactoryCreatedBean(final FactoryCreatedBeanUsingBean factoryCreatedBeanUsingBean) {
        this.factoryCreatedBeanUsingBean = factoryCreatedBeanUsingBean;
    }

    public FactoryCreatedBeanUsingBean getFactoryCreatedBeanUsingBean() {
        return factoryCreatedBeanUsingBean;
    }
}
