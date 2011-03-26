
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

package net.usikkert.kouinject.testbeans.scanned.generics.stuff;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;

/**
 * A bean injecting different lists.
 *
 * @author Christian Ihle
 */
@Component
public class ListOfStuffBean {

    @Inject
    private List<OneStuffBean> oneStuffBeans;

    @Inject
    private List<TwoStuffBean> twoStuffBeans;

    @Inject
    private List<Set<OneStuffBean>> oneStuffBeansInSet;

    @Inject
    private List<Set<TwoStuffBean>> twoStuffBeansInSet;

    public List<OneStuffBean> getOneStuffBeans() {
        return oneStuffBeans;
    }

    public List<TwoStuffBean> getTwoStuffBeans() {
        return twoStuffBeans;
    }

    public List<Set<OneStuffBean>> getOneStuffBeansInSet() {
        return oneStuffBeansInSet;
    }

    public List<Set<TwoStuffBean>> getTwoStuffBeansInSet() {
        return twoStuffBeansInSet;
    }
}
