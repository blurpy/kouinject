
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

package net.usikkert.kouinject.testbeans.scanned.notloaded;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

/**
 * Bean for testing qualifiers.
 *
 * @author Christian Ihle
 */
public class QualifierBean {

    @Inject @Named("red")
    private FieldBean fieldBean;

    @Inject @Blue
    private Provider<FieldBean> fieldBeanProvider;

    private final ConstructorBean constructorBean;

    private final Provider<JavaBean> javaBeanProvider;

    private SetterBean setterBean;

    private Provider<HelloBean> helloBeanProvider;

    @Inject @Green
    public QualifierBean(final ConstructorBean constructorBean, final Provider<JavaBean> javaBeanProvider) {
        this.constructorBean = constructorBean;
        this.javaBeanProvider = javaBeanProvider;
    }

    public SetterBean getSetterBean() {
        return setterBean;
    }

    @Inject @Yellow
    public void setSetterBean(final SetterBean setterBean) {
        this.setterBean = setterBean;
    }

    public FieldBean getFieldBean() {
        return fieldBean;
    }

    public ConstructorBean getConstructorBean() {
        return constructorBean;
    }

    public Provider<FieldBean> getFieldBeanProvider() {
        return fieldBeanProvider;
    }

    public Provider<HelloBean> getHelloBeanProvider() {
        return helloBeanProvider;
    }

    @Inject @Blue
    public void setHelloBeanProvider(final Provider<HelloBean> helloBeanProvider) {
        this.helloBeanProvider = helloBeanProvider;
    }

    public Provider<JavaBean> getJavaBeanProvider() {
        return javaBeanProvider;
    }
}
