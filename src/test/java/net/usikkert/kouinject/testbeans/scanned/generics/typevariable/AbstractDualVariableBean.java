
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

/**
 * An abstract class with a type variable that is passed from the implementation to the interface.
 *
 * <p>Using the type variable name "S" for the first variable her, while it is the name of the second
 * variable in the interface. This is to verify that there wont be name issues when the injector resolves
 * variables to actual types.</p>
 *
 * @author Christian Ihle
 */
public abstract class AbstractDualVariableBean<S> implements DualVariableInterfaceBean<S, VariableTwo> {

    @Override
    public VariableTwo getSecond() {
        return new VariableTwo();
    }

    @Override
    public void doSecond(final VariableTwo variableTwo) {

    }
}
