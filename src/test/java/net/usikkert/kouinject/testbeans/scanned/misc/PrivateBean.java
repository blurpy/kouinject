
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

package net.usikkert.kouinject.testbeans.scanned.misc;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

/**
 * A bean for testing injections in private members.
 *
 * @author Christian Ihle
 */
@Component
public class PrivateBean {

    @Inject
    private HelloBean helloBeanFromField;

    private HelloBean helloBeanFromSetter;
    private final HelloBean helloBeanFromConstructor;

    @Inject
    private PrivateBean(final HelloBean helloBeanFromConstructor) {
        this.helloBeanFromConstructor = helloBeanFromConstructor;
    }

    @Inject
    private void setHelloBeanFromSetter(final HelloBean helloBeanFromSetter) {
        this.helloBeanFromSetter = helloBeanFromSetter;
    }

    public HelloBean getHelloBeanFromField() {
        return helloBeanFromField;
    }

    public HelloBean getHelloBeanFromSetter() {
        return helloBeanFromSetter;
    }

    public HelloBean getHelloBeanFromConstructor() {
        return helloBeanFromConstructor;
    }
}