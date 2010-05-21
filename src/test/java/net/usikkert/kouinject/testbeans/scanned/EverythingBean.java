
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
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

package net.usikkert.kouinject.testbeans.scanned;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBeanImpl;

/**
 * Bean used for testing correct dependency injection.
 *
 * @author Christian Ihle
 */
@Component
public class EverythingBean {

    private final ConstructorBean constructorBean;

    private final SetterBean setterBean;

    private final FieldBean fieldBean;

    private final HelloBean helloBean;

    @Inject
    private CoffeeBean coffeeBean;

    private JavaBean javaBean;

    private final InterfaceBeanImpl interfaceBeanImpl;

    private AbstractBeanImpl abstractBeanImpl;

    @Inject
    public EverythingBean(final ConstructorBean constructorBean, final SetterBean setterBean,
            final FieldBean fieldBean, final HelloBean helloBean, final InterfaceBeanImpl interfaceBeanImpl) {
        this.constructorBean = constructorBean;
        this.setterBean = setterBean;
        this.fieldBean = fieldBean;
        this.helloBean = helloBean;
        this.interfaceBeanImpl = interfaceBeanImpl;
    }

    @Inject
    public void setJavaBean(final JavaBean javaBean) {
        this.javaBean = javaBean;
    }

    public ConstructorBean getConstructorBean() {
        return constructorBean;
    }

    public SetterBean getSetterBean() {
        return setterBean;
    }

    public FieldBean getFieldBean() {
        return fieldBean;
    }

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public JavaBean getJavaBean() {
        return javaBean;
    }

    public AbstractBeanImpl getAbstractBeanImpl() {
        return abstractBeanImpl;
    }

    @Inject
    public void setAbstractBeanImpl(final AbstractBeanImpl abstractBeanImpl) {
        this.abstractBeanImpl = abstractBeanImpl;
    }

    public InterfaceBeanImpl getInterfaceBeanImpl() {
        return interfaceBeanImpl;
    }
}
