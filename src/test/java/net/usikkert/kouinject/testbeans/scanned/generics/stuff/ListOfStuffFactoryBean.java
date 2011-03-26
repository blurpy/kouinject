
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;

/**
 * A factory bean that creates different lists.
 *
 * @author Christian Ihle
 */
@Component
public class ListOfStuffFactoryBean {

    @Produces
    public ArrayList<OneStuffBean> createOneStuffBeans() {
        final ArrayList<OneStuffBean> oneStuffBeans = new ArrayList<OneStuffBean>();
        oneStuffBeans.add(new OneStuffBean());

        return oneStuffBeans;
    }

    @Produces
    public List<TwoStuffBean> createTwoStuffBeans() {
        final List<TwoStuffBean> twoStuffBeans = new ArrayList<TwoStuffBean>();
        twoStuffBeans.add(new TwoStuffBean());

        return twoStuffBeans;
    }

    @Produces
    public List<Set<OneStuffBean>> createOneStuffBeansInSet() {
        final List<Set<OneStuffBean>> oneStuffBeanList = new ArrayList<Set<OneStuffBean>>();
        final Set<OneStuffBean> oneStuffBeans = new HashSet<OneStuffBean>();
        oneStuffBeanList.add(oneStuffBeans);
        oneStuffBeans.add(new OneStuffBean());

        return oneStuffBeanList;
    }

    @Produces
    public ArrayList<Set<TwoStuffBean>> createTwoStuffBeansInSet() {
        final ArrayList<Set<TwoStuffBean>> twoStuffBeanList = new ArrayList<Set<TwoStuffBean>>();
        final HashSet<TwoStuffBean> twoStuffBeans = new HashSet<TwoStuffBean>();
        twoStuffBeanList.add(twoStuffBeans);
        twoStuffBeans.add(new TwoStuffBean());

        return twoStuffBeanList;
    }
}
