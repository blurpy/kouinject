
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

package net.usikkert.kouinject.testbeans.scanned.generics.typevariable;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;

/**
 * A bottle of liquid.
 *
 * @author Christian Ihle
 */
public class Bottle<T extends Liquid> {

    @Inject
    private T t;

    private T methodT;

    public Bottle() {

    }

    public T getMethodT() {
        return methodT;
    }

    @Inject
    public void setMethodT(final T methodT) {
        this.methodT = methodT;
    }

    @Produces
    public Container<T> createContainer() {
        return new Container<T>(t);
    }

    @Produces
    public LiquidDualVariableBean<T> createDualVariableBean() {
        return new LiquidDualVariableBean<T>(t);
    }
}
