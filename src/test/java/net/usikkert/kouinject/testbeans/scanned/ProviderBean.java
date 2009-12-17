
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

package net.usikkert.kouinject.testbeans.scanned;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Component;

/**
 * Bean used for testing injection of other beans using a {@link Provider}.
 * Also tests injection in private constructors and methods.
 *
 * @author Christian Ihle
 */
@Component
public class ProviderBean {

    @Inject
    private Provider<FieldBean> fieldBeanProvider;

    private Provider<SetterBean> setterBeanProvider;

    private Provider<ConstructorBean> constructorBeanProvider;

    public ProviderBean() {

    }

    @Inject
    @SuppressWarnings("unused")
    private ProviderBean(final Provider<ConstructorBean> constructorBeanProvider) {
        this.constructorBeanProvider = constructorBeanProvider;
    }

    public Provider<SetterBean> getSetterBeanProvider() {
        return setterBeanProvider;
    }

    @Inject
    @SuppressWarnings("unused")
    private void setSetterBeanProvider(final Provider<SetterBean> setterBeanProvider) {
        this.setterBeanProvider = setterBeanProvider;
    }

    public Provider<FieldBean> getFieldBeanProvider() {
        return fieldBeanProvider;
    }

    public Provider<ConstructorBean> getConstructorBeanProvider() {
        return constructorBeanProvider;
    }
}
