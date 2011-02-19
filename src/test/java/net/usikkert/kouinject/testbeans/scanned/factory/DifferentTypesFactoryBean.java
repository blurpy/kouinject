
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

import java.util.Collection;

import javax.inject.Provider;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

/**
 * Test of factory points with different types of parameters.
 *
 * @author Christian Ihle
 */
@Component
public class DifferentTypesFactoryBean {

    @Produces
    public DifferentTypesFactoryCreatedBean createBean(final HelloBean helloBean,
                                                       final CollectionProvider<FieldBean> fieldBeanCollectionProvider,
                                                       final Provider<CoffeeBean> coffeeBeanProvider,
                                                       final Collection<JavaBean> javaBeanCollection) {
        final DifferentTypesFactoryCreatedBean bean = new DifferentTypesFactoryCreatedBean();

        bean.setHelloBean(helloBean);
        bean.setCoffeeBeanProvider(coffeeBeanProvider);
        bean.setFieldBeanCollectionProvider(fieldBeanCollectionProvider);
        bean.setJavaBeanCollection(javaBeanCollection);
        bean.setCreatedByFactory(true);

        return bean;
    }
}
