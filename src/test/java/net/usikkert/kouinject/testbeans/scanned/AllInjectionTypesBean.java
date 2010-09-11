
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
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

package net.usikkert.kouinject.testbeans.scanned;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Component;

/**
 * Bean for testing all the different types of injection at the same time.
 *
 * @author Christian Ihle
 */
@Component
public class AllInjectionTypesBean {

    @Inject
    private HelloBean helloBean;

    @Inject
    private Provider<HelloBean> helloBeanProvider;

    @Inject
    private Collection<HelloBean> helloBeanCollection;

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public Provider<HelloBean> getHelloBeanProvider() {
        return helloBeanProvider;
    }

    public Collection<HelloBean> getHelloBeanCollection() {
        return helloBeanCollection;
    }
}
