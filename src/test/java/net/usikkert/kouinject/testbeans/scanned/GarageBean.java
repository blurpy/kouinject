
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
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

package net.usikkert.kouinject.testbeans.scanned;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Blue;

/**
 * Bean for testing injection of a bean without qualifier,
 * and injection of a subclass of that bean with a qualifier.
 *
 * @author Christian Ihle
 */
@Component
public class GarageBean {

    @Inject
    private CarBean carBean;

    @Inject
    private Provider<CarBean> carBeanProvider;

    @Inject @Blue
    private CarBean blueCarBean;

    @Inject @Blue
    private Provider<CarBean> blueCarBeanProvider;

    public CarBean getCarBean() {
        return carBean;
    }

    public Provider<CarBean> getCarBeanProvider() {
        return carBeanProvider;
    }

    public CarBean getBlueCarBean() {
        return blueCarBean;
    }

    public Provider<CarBean> getBlueCarBeanProvider() {
        return blueCarBeanProvider;
    }
}
