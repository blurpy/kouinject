
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

package net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.pets;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.PetBean;

/**
 * Bean for testing injection in overridden methods.
 *
 * @author Christian Ihle
 */
@Named("cat")
@Component
public class CatBean extends PetBean {

    private HelloBean helloBean;

    private JavaBean javaBean;

    private CoffeeBean coffeeBean;

    private CarBean carBean;

    private SetterBean setterBeanInCatBean;

    @Inject
    private FieldBean fieldBean1InCatBean;

    @Inject
    private FieldBean fieldBean2InCatBean;

    private boolean methodsInjectedInCatBean;

    private boolean fieldsInjectedInCatBean = true;

    public HelloBean getHelloBeanInCatBean() {
        return helloBean;
    }

    @Inject
    @SuppressWarnings("unused")
    private void setHelloBean(final HelloBean helloBean) {
        this.helloBean = helloBean;

        checkInjections();
    }

    public JavaBean getJavaBeanInCatBean() {
        return javaBean;
    }

    @Inject
    @SuppressWarnings("all")
    void setJavaBean(final JavaBean javaBean) {
        this.javaBean = javaBean;

        checkInjections();
    }

    public CoffeeBean getCoffeeBeanInCatBean() {
        return coffeeBean;
    }

    @Inject
    @Override
    protected void setCoffeeBean(final CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;

        checkInjections();
    }

    public CarBean getCarBeanInCatBean() {
        return carBean;
    }

    @Inject
    @Override
    public void setCarBean(final CarBean carBean) {
        this.carBean = carBean;

        checkInjections();
    }

    public SetterBean getSetterBeanInCatBean() {
        return setterBeanInCatBean;
    }

    @Inject
    public void setSetterBeanInCatBean(final SetterBean setterBeanInCatBean) {
        this.setterBeanInCatBean = setterBeanInCatBean;

        checkInjections();
    }

    public FieldBean getFieldBean1InCatBean() {
        return fieldBean1InCatBean;
    }

    public FieldBean getFieldBean2InCatBean() {
        return fieldBean2InCatBean;
    }

    public boolean isFieldsThenMethodsInjectedInCatBean() {
        return fieldsInjectedInCatBean && methodsInjectedInCatBean;
    }

    @Override
    public boolean isInjected() {
        return helloBean != null
             || javaBean != null
             || coffeeBean != null
             || carBean != null
             || setterBeanInCatBean != null
             || fieldBean1InCatBean != null
             || fieldBean2InCatBean != null;
    }

    private void checkInjections() {
        if (isFieldsThenMethodsInjectedInPetBean()) {
            if (fieldBean1InCatBean == null || fieldBean2InCatBean == null) {
                fieldsInjectedInCatBean = false;
            }

            if (helloBean != null && setterBeanInCatBean != null && javaBean != null
                    && coffeeBean != null && carBean != null) {
                methodsInjectedInCatBean = true;
            }
        }
    }
}
