
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

package net.usikkert.kouinject.testbeans.scanned.generics.typevariable;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;

/**
 * Bean for testing injection of both generic arrays with a type variable and a concrete array.
 *
 * @author Christian Ihle
 */
@Component
public class PepsiArrayContainer extends ArrayContainer<Pepsi> {

    @Inject
    private Container<Pepsi[]> pepsiArrayContainer;

    public Container<Pepsi[]> getPepsiArrayContainer() {
        return pepsiArrayContainer;
    }
}
