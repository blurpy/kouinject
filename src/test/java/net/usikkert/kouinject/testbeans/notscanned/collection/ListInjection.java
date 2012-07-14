
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

package net.usikkert.kouinject.testbeans.notscanned.collection;

import java.util.List;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

/**
 * Bean for testing error handling when trying to inject a list instead of a collection.
 *
 * @author Christian Ihle
 */
@Component
public class ListInjection {

    @Inject
    private List<HelloBean> listOfHelloBeans;

    public List<HelloBean> getListOfHelloBeans() {
        return listOfHelloBeans;
    }
}
