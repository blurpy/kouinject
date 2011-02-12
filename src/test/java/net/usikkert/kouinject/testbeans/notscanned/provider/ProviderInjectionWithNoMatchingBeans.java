
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

package net.usikkert.kouinject.testbeans.notscanned.provider;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.notscanned.TheInterface;

/**
 * Bean for testing error handling when trying to inject a {@link Provider} of beans with no matches.
 *
 * @author Christian Ihle
 */
@Component
public class ProviderInjectionWithNoMatchingBeans {

    @Inject
    private Provider<TheInterface> theInterfaceProvider;

    public Provider<TheInterface> getTheInterfaceProvider() {
        return theInterfaceProvider;
    }
}