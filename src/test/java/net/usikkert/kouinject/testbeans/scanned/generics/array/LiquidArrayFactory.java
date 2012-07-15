
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

package net.usikkert.kouinject.testbeans.scanned.generics.array;

import javax.inject.Named;
import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.GenericBox;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pepsi;

/**
 * A factory for creating arrays with liquids.
 *
 * @author Christian Ihle
 */
@Component
public class LiquidArrayFactory {

    @Produces
    public Pepsi[] createPepsiArray(final Pepsi pepsi) {
        return new Pepsi[] { pepsi };
    }

    @Produces
    public Fanta[] createFantaArray(final Fanta fanta) {
        return new Fanta[] { fanta };
    }

    @Produces @Singleton @Named("PepsiArray")
    public GenericBox<Liquid[]> createBoxOfPepsiArray(final Pepsi[] pepsiArray) {
        return new GenericBox<Liquid[]>(pepsiArray);
    }

    @Produces @Named("FantaArray")
    public GenericBox<Liquid[]> createBoxOfFantaArray(final Fanta[] fantaArray) {
        return new GenericBox<Liquid[]>(fantaArray);
    }
}
