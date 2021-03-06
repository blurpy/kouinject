
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
@Named("organism")
@Component
public class OrganismBean {

    private HelloBean helloBean;

    private JavaBean javaBean;

    private CoffeeBean coffeeBean;

    private CarBean carBean;

    private SetterBean setterBeanInOrganismBean;

    @Inject
    private FieldBean fieldBean1InOrganismBean;

    @Inject
    private FieldBean fieldBean2InOrganismBean;

    private boolean methodsInjectedInOrganismBean;

    private boolean fieldsInjectedInOrganismBean = true;

    public HelloBean getHelloBeanInOrganismBean() {
        return helloBean;
    }

    @Inject
    @SuppressWarnings("unused")
    private void setHelloBean(final HelloBean helloBean) {
        this.helloBean = helloBean;

        checkInjections();
    }

    public JavaBean getJavaBeanInOrganismBean() {
        return javaBean;
    }

    @Inject
    void setJavaBean(final JavaBean javaBean) {
        this.javaBean = javaBean;

        checkInjections();
    }

    public CoffeeBean getCoffeeBeanInOrganismBean() {
        return coffeeBean;
    }

    @Inject
    protected void setCoffeeBean(final CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;

        checkInjections();
    }

    public CarBean getCarBeanInOrganismBean() {
        return carBean;
    }

    @Inject
    public void setCarBean(final CarBean carBean) {
        this.carBean = carBean;

        checkInjections();
    }

    public SetterBean getSetterBeanInOrganismBean() {
        return setterBeanInOrganismBean;
    }

    @Inject
    public void setSetterBeanInOrganismBean(final SetterBean setterBeanInOrganismBean) {
        this.setterBeanInOrganismBean = setterBeanInOrganismBean;

        checkInjections();
    }

    public FieldBean getFieldBean1InOrganismBean() {
        return fieldBean1InOrganismBean;
    }

    public FieldBean getFieldBean2InOrganismBean() {
        return fieldBean2InOrganismBean;
    }

    public boolean isFieldsThenMethodsInjectedInOrganismBean() {
        return fieldsInjectedInOrganismBean && methodsInjectedInOrganismBean;
    }

    public boolean isInjected() {
        return false;
    }

    private void checkInjections() {
        if (!isInjected()) {
            if (fieldBean1InOrganismBean == null || fieldBean2InOrganismBean == null) {
                fieldsInjectedInOrganismBean = false;
            }

            if (helloBean != null && setterBeanInOrganismBean != null) {
                methodsInjectedInOrganismBean = true;
            }
        }
    }
}
