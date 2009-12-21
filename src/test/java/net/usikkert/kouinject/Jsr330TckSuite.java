
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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

package net.usikkert.kouinject;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;

import net.usikkert.kouinject.beandata.Dependency;

import org.atinject.tck.Tck;
import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Convertible;
import org.atinject.tck.auto.DriversSeat;
import org.atinject.tck.auto.FuelTank;
import org.atinject.tck.auto.Seat;
import org.atinject.tck.auto.Tire;
import org.atinject.tck.auto.V8Engine;
import org.atinject.tck.auto.accessories.Cupholder;
import org.atinject.tck.auto.accessories.SpareTire;

/**
 * Test for setting up the official JSR-330 Technology Compatibility Kit
 * using KouInject.
 *
 * TODO rename so the tests run automatically when the TCK passes.
 *
 * @author Christian Ihle
 */
public class Jsr330TckSuite {

    public static Test suite() {
        final BeanLocator beanLocator = new BeanLocator() {
            @Override
            public Set<Dependency> findBeans() {
                final HashSet<Dependency> beans = new HashSet<Dependency>();

                beans.add(new Dependency(Convertible.class));
                beans.add(new Dependency(V8Engine.class));
                beans.add(new Dependency(Cupholder.class));
                beans.add(new Dependency(Tire.class));
                beans.add(new Dependency(SpareTire.class, "spare"));
                beans.add(new Dependency(FuelTank.class));
                beans.add(new Dependency(Seat.class));
                beans.add(new Dependency(DriversSeat.class, "Drivers"));

                return beans;
            }
        };

        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final BeanLoader beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator);
        beanLoader.loadBeans();

        final Car car = beanLoader.getBean(Car.class);
        return Tck.testsFor(car, true, true);
    }
}
