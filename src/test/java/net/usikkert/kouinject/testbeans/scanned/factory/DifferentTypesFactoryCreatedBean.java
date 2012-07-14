
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

import java.util.Collection;

import javax.inject.Provider;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

/**
 * A bean with different types of parameters created using a factory.
 *
 * @author Christian Ihle
 */
public class DifferentTypesFactoryCreatedBean {

    private boolean createdByFactory;
    private HelloBean helloBean;
    private CollectionProvider<FieldBean> fieldBeanCollectionProvider;
    private Provider<CoffeeBean> coffeeBeanProvider;
    private Collection<JavaBean> javaBeanCollection;

    public boolean isCreatedByFactory() {
        return createdByFactory;
    }

    public void setCreatedByFactory(final boolean createdByFactory) {
        this.createdByFactory = createdByFactory;
    }

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public void setHelloBean(final HelloBean helloBean) {
        this.helloBean = helloBean;
    }

    public CollectionProvider<FieldBean> getFieldBeanCollectionProvider() {
        return fieldBeanCollectionProvider;
    }

    public void setFieldBeanCollectionProvider(final CollectionProvider<FieldBean> fieldBeanCollectionProvider) {
        this.fieldBeanCollectionProvider = fieldBeanCollectionProvider;
    }

    public Provider<CoffeeBean> getCoffeeBeanProvider() {
        return coffeeBeanProvider;
    }

    public void setCoffeeBeanProvider(final Provider<CoffeeBean> coffeeBeanProvider) {
        this.coffeeBeanProvider = coffeeBeanProvider;
    }

    public Collection<JavaBean> getJavaBeanCollection() {
        return javaBeanCollection;
    }

    public void setJavaBeanCollection(final Collection<JavaBean> javaBeanCollection) {
        this.javaBeanCollection = javaBeanCollection;
    }
}
