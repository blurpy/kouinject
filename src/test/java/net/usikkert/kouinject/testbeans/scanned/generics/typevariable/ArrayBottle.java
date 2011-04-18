
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
import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;

/**
 * A bottle for testing injecting and producing beans with type variable arrays.
 *
 * @author Christian Ihle
 */
public class ArrayBottle<T extends Liquid> {

    @Inject
    private T[] arrayT;

    @Inject
    private Provider<T[]> providerArrayT;

    @Produces
    public Container<T[]> createContainerOfArrayT() {
        return new Container<T[]>(arrayT);
    }

    public T[] getArrayT() {
        return arrayT;
    }

    public Provider<T[]> getProviderArrayT() {
        return providerArrayT;
    }
}
