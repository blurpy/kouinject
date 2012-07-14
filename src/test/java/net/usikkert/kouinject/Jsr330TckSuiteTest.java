
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

package net.usikkert.kouinject;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.factory.AnnotationBasedFactoryPointHandler;
import net.usikkert.kouinject.factory.FactoryPointHandler;

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
 * @author Christian Ihle
 */
public class Jsr330TckSuiteTest {

    public static Test suite() {
        final BeanLocator beanLocator = new BeanLocator() {
            @Override
            public Set<BeanKey> findBeans() {
                final Set<BeanKey> beans = new HashSet<BeanKey>();

                beans.add(new BeanKey(Convertible.class));
                beans.add(new BeanKey(V8Engine.class));
                beans.add(new BeanKey(Cupholder.class));
                beans.add(new BeanKey(Tire.class));
                beans.add(new BeanKey(SpareTire.class, "spare"));
                beans.add(new BeanKey(FuelTank.class));
                beans.add(new BeanKey(Seat.class));
                beans.add(new BeanKey(DriversSeat.class, "Drivers"));

                return beans;
            }
        };

        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final FactoryPointHandler factoryPointHandler = new AnnotationBasedFactoryPointHandler();
        final BeanLoader beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator, factoryPointHandler);

        final Car car = beanLoader.getBean(Car.class);

        final boolean supportsStatic = false;
        final boolean supportsPrivate = true;

        return Tck.testsFor(car, supportsStatic, supportsPrivate);
    }
}
