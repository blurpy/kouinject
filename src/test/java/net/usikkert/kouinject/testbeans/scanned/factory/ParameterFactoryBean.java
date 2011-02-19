
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

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Blue;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

/**
 * Test of factory points with parameters.
 *
 * @author Christian Ihle
 */
@Component
public class ParameterFactoryBean {

    @Produces
    public OneParameterFactoryCreatedBean createBeanWithOneParameter(final HelloBean helloBean) {
        final OneParameterFactoryCreatedBean bean = new OneParameterFactoryCreatedBean();

        bean.setHelloBean(helloBean);
        bean.setCreatedByFactory(true);

        return bean;
    }

    @Produces
    public ThreeParametersFactoryCreatedBean createBeanWithThreeParameters(@Blue final ColorBean colorBean,
                                                                           final CoffeeBean coffeeBean,
                                                                           @Named("red") final RedBean redBean) {
        final ThreeParametersFactoryCreatedBean bean = new ThreeParametersFactoryCreatedBean();

        bean.setColorBean(colorBean);
        bean.setCoffeeBean(coffeeBean);
        bean.setRedBean(redBean);
        bean.setCreatedByFactory(true);

        return bean;
    }
}
