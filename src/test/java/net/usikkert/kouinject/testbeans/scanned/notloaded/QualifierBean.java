
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
import net.usikkert.kouinject.testbeans.scanned.SetterBean;

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

    private final Provider<ConstructorBean> constructorBeanProvider;

    private SetterBean setterBean;

    private Provider<SetterBean> setterBeanProvider;

    @Inject
    public QualifierBean(@Green final ConstructorBean constructorBean,
                         @Named("orange") final Provider<ConstructorBean> constructorBeanProvider) {
        this.constructorBean = constructorBean;
        this.constructorBeanProvider = constructorBeanProvider;
    }

    @Inject
    public void setBeans(@Yellow final SetterBean setterB,
                         @Blue final Provider<SetterBean> setterBProvider) {
        this.setterBean = setterB;
        this.setterBeanProvider = setterBProvider;
    }

    public FieldBean getFieldBean() {
        return fieldBean;
    }

    public Provider<FieldBean> getFieldBeanProvider() {
        return fieldBeanProvider;
    }

    public ConstructorBean getConstructorBean() {
        return constructorBean;
    }

    public Provider<ConstructorBean> getConstructorBeanProvider() {
        return constructorBeanProvider;
    }

    public SetterBean getSetterBean() {
        return setterBean;
    }

    public Provider<SetterBean> getSetterBeanProvider() {
        return setterBeanProvider;
    }
}
