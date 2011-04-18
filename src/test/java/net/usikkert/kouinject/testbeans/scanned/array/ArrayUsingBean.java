
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

package net.usikkert.kouinject.testbeans.scanned.array;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;

/**
 * A bean for testing injection of arrays.
 *
 * @author Christian Ihle
 */
@Component
public class ArrayUsingBean {

    @Inject @Named("FirstSimpleArray")
    private ArrayClass[] arrayClasses;

    @Inject @Named("SecondSimpleArray")
    private ArrayInterface[] arrayInterfaces;

    @Inject
    private SingletonArray[] singletonArrays;

    public ArrayClass[] getArrayClasses() {
        return arrayClasses;
    }

    public ArrayInterface[] getArrayInterfaces() {
        return arrayInterfaces;
    }

    public SingletonArray[] getSingletonArrays() {
        return singletonArrays;
    }
}
