
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

package net.usikkert.kouinject.testbeans;

/**
 * Enum with information about the number of test beans of different kinds.
 *
 * @author Christian Ihle
 */
public enum BeanCount {

    // All components
    ALL(140),

    // All components and those created by factories
    SCANNED(144),

    // All components and those created by factories, without a qualifier
    SCANNED_WITHOUT_QUALIFIER(108),

    // All from SCANNED, plus those with profiles that can be activated at the same time
    SCANNED_WITH_PROFILED(153);

    private final int numberOfBeans;

    private BeanCount(final int numberOfBeans) {
        this.numberOfBeans = numberOfBeans;
    }

    public int getNumberOfBeans() {
        return numberOfBeans;
    }
}
