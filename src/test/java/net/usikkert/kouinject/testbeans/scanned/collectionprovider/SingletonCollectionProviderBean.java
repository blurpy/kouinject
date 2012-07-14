
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

package net.usikkert.kouinject.testbeans.scanned.collectionprovider;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Blue;

/**
 * Bean used for testing collection provider in a singleton.
 *
 * @author Christian Ihle
 */
@Singleton
@Component
public class SingletonCollectionProviderBean {

    @Inject @Blue
    private CollectionProvider<CarBean> blueCarBeanCollectionProvider;

    public CollectionProvider<CarBean> getBlueCarBeanCollectionProvider() {
        return blueCarBeanCollectionProvider;
    }
}
