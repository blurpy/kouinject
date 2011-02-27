
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

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;

/**
 * Factory bean with injections of regular beans and factory beans in fields, setters, and constructor.
 *
 * @author Christian Ihle
 */
@Component
public class LotsOfInjectionsFactoryBean {

    @Inject
    private FieldBean fieldBean;

    @Inject
    private FieldFactoryCreatedBean fieldFactoryCreatedBean;

    private final ConstructorBean constructorBean;
    private final ConstructorFactoryCreatedBean constructorFactoryCreatedBean;

    private SetterBean setterBean;
    private SetterFactoryCreatedBean setterFactoryCreatedBean;

    @Inject
    public LotsOfInjectionsFactoryBean(final ConstructorBean constructorBean,
                                       final ConstructorFactoryCreatedBean constructorFactoryCreatedBean) {
        this.constructorBean = constructorBean;
        this.constructorFactoryCreatedBean = constructorFactoryCreatedBean;
    }

    @Inject
    public void setSetterBeans(final SetterBean setterBean,
                               final SetterFactoryCreatedBean setterFactoryCreatedBean) {
        this.setterBean = setterBean;
        this.setterFactoryCreatedBean = setterFactoryCreatedBean;
    }

    @Produces @Named("50")
    public Long create50() {
        return 50L;
    }

    public FieldBean getFieldBean() {
        return fieldBean;
    }

    public FieldFactoryCreatedBean getFieldFactoryCreatedBean() {
        return fieldFactoryCreatedBean;
    }

    public ConstructorBean getConstructorBean() {
        return constructorBean;
    }

    public ConstructorFactoryCreatedBean getConstructorFactoryCreatedBean() {
        return constructorFactoryCreatedBean;
    }

    public SetterBean getSetterBean() {
        return setterBean;
    }

    public SetterFactoryCreatedBean getSetterFactoryCreatedBean() {
        return setterFactoryCreatedBean;
    }
}
