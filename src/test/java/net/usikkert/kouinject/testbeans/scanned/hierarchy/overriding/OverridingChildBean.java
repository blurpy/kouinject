
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

package net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding;

/**
 * Class for testing overriding.
 *
 * @author Christian Ihle
 */
public class OverridingChildBean extends OverridingSuperBean {

    @Override
    public void publicMethod() {

    }

    public void publicMethod(final String s) {

    }

    @Override
    public void publicMethod(final Object o) {

    }

    public void publicMethod2() {

    }

    public void privateMethod() {
        // verify that public method cannot override private method with same name
    }

    public static void staticMethod() {

    }

    @Override
    public final void finalMethod() {

    }

    @Override
    protected void protectedMethod() {

    }

    @Override
    void defaultMethod() {

    }
}
