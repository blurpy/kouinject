
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

package net.usikkert.kouinject.testbeans.notscanned.factory;

import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

/**
 * Bean for testing {@link net.usikkert.kouinject.factory.FactoryPointMethod}.
 *
 * @author Christian Ihle
 */
public class FactoryPointMethodBean {

    public void nothingMethod() {

    }

    public Object nullMethod() {
        return null;
    }

    public CoffeeBean coffeeBeanMethod() {
        return new CoffeeBean();
    }

    private RedBean privateRedBeanMethod() {
        return new RedBean();
    }

    private CarBean methodWithParameters(final HelloBean helloBean, final GreenBean greenBean) {
        if (helloBean == null || greenBean == null) {
            // Using different exception than used by the injector, to know who threw it
            throw new NumberFormatException("Got null");
        }

        return new CarBean();
    }
}
