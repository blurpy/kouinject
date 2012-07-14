
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

package net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

/**
 * Bean for testing injection in overridden methods.
 *
 * @author Christian Ihle
 */
@Named("pet")
@Component
public class PetBean extends AnimalBean {

    private HelloBean helloBean;

    private JavaBean javaBean;

    private CoffeeBean coffeeBean;

    private CarBean carBean;

    private SetterBean setterBeanInPetBean;

    @Inject
    private FieldBean fieldBean1InPetBean;

    @Inject
    private FieldBean fieldBean2InPetBean;

    private boolean methodsInjectedInPetBean;

    private boolean fieldsInjectedInPetBean = true;

    public HelloBean getHelloBeanInPetBean() {
        return helloBean;
    }

    @Inject
    @SuppressWarnings("unused")
    private void setHelloBean(final HelloBean helloBean) {
        this.helloBean = helloBean;

        checkInjections();
    }

    public JavaBean getJavaBeanInPetBean() {
        return javaBean;
    }

    @Inject
    @Override
    void setJavaBean(final JavaBean javaBean) {
        this.javaBean = javaBean;

        checkInjections();
    }

    public CoffeeBean getCoffeeBeanInPetBean() {
        return coffeeBean;
    }

    @Inject
    @Override
    protected void setCoffeeBean(final CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;

        checkInjections();
    }

    public CarBean getCarBeanInPetBean() {
        return carBean;
    }

    @Inject
    @Override
    public void setCarBean(final CarBean carBean) {
        this.carBean = carBean;

        checkInjections();
    }

    public SetterBean getSetterBeanInPetBean() {
        return setterBeanInPetBean;
    }

    @Inject
    public void setSetterBeanInPetBean(final SetterBean setterBeanInPetBean) {
        this.setterBeanInPetBean = setterBeanInPetBean;

        checkInjections();
    }

    public FieldBean getFieldBean1InPetBean() {
        return fieldBean1InPetBean;
    }

    public FieldBean getFieldBean2InPetBean() {
        return fieldBean2InPetBean;
    }

    public boolean isFieldsThenMethodsInjectedInPetBean() {
        return fieldsInjectedInPetBean && methodsInjectedInPetBean;
    }

    @Override
    public boolean isInjected() {
        return helloBean != null
             || javaBean != null
             || coffeeBean != null
             || carBean != null
             || setterBeanInPetBean != null
             || fieldBean1InPetBean != null
             || fieldBean2InPetBean != null;
    }

    private void checkInjections() {
        if (isFieldsThenMethodsInjectedInAnimalBean()) {
            if (fieldBean1InPetBean == null || fieldBean2InPetBean == null) {
                fieldsInjectedInPetBean = false;
            }

            if (helloBean != null && setterBeanInPetBean != null && javaBean != null) {
                methodsInjectedInPetBean = true;
            }
        }
    }
}
