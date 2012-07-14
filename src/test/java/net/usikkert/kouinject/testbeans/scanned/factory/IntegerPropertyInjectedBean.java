
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

package net.usikkert.kouinject.testbeans.scanned.factory;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;

/**
 * Test of having an alternative bean created by a factory with Any, in addition to the string bean.
 *
 * @author Christian Ihle
 */
@Component
public class IntegerPropertyInjectedBean {

    @Inject @Named("some.integer")
    private Integer someInteger;

    @Inject @Named("price")
    private Integer price;

    @Inject @Named("some.other.integer")
    private Integer someOtherInteger;

    public Integer getSomeInteger() {
        return someInteger;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getSomeOtherInteger() {
        return someOtherInteger;
    }
}
