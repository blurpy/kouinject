
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

package net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

/**
 * Bean for testing injection in overridden methods.
 *
 * @author Christian Ihle
 */
@Named("animal")
@Component
public class AnimalBean extends OrganismBean {

    private HelloBean helloBean;

    private JavaBean javaBean;

    private CoffeeBean coffeeBean;

    private CarBean carBean;

    private SetterBean setterBeanInAnimalBean;

    private boolean methodsInjectedInAnimalBean;

    public HelloBean getHelloBeanInAnimalBean() {
        return helloBean;
    }

    @Inject
    @SuppressWarnings("unused")
    private void setHelloBean(final HelloBean helloBean) {
        this.helloBean = helloBean;

        checkMethodInjections();
    }

    public JavaBean getJavaBeanInAnimalBean() {
        return javaBean;
    }

    @Inject
    @Override
    void setJavaBean(final JavaBean javaBean) {
        this.javaBean = javaBean;

        checkMethodInjections();
    }

    public CoffeeBean getCoffeeBeanInAnimalBean() {
        return coffeeBean;
    }

    @Inject
    @Override
    protected void setCoffeeBean(final CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;

        checkMethodInjections();
    }

    public CarBean getCarBeanInAnimalBean() {
        return carBean;
    }

    @Inject
    @Override
    public void setCarBean(final CarBean carBean) {
        this.carBean = carBean;

        checkMethodInjections();
    }

    public SetterBean getSetterBeanInAnimalBean() {
        return setterBeanInAnimalBean;
    }

    @Inject
    public void setSetterBeanInAnimalBean(final SetterBean setterBeanInAnimalBean) {
        this.setterBeanInAnimalBean = setterBeanInAnimalBean;

        checkMethodInjections();
    }

    public boolean isMethodsInjectedInAnimalBean() {
        return methodsInjectedInAnimalBean;
    }

    private void checkMethodInjections() {
        if (isMethodsInjectedInOrganismBean() && helloBean != null && setterBeanInAnimalBean != null) {
            methodsInjectedInAnimalBean = true;
        }
    }
}
