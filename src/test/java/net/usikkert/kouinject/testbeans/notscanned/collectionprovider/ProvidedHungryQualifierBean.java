
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

package net.usikkert.kouinject.testbeans.notscanned.collectionprovider;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.collection.Food;

/**
 * Bean used for testing correct dependency injection in collection providers, with qualifiers.
 *
 * @author Christian Ihle
 */
@Component
public class ProvidedHungryQualifierBean {

    @Inject
    @Named("fastFood")
    private CollectionProvider<Food> fastFoodBeans;

    private final CollectionProvider<Food> allFoodBeans;

    private CollectionProvider<Food> roundFoodBeans;

    @Inject
    public ProvidedHungryQualifierBean(@Any final CollectionProvider<Food> allFoodBeans) {
        this.allFoodBeans = allFoodBeans;
    }

    public CollectionProvider<Food> getFastFoodBeans() {
        return fastFoodBeans;
    }

    public CollectionProvider<Food> getAllFoodBeans() {
        return allFoodBeans;
    }

    public CollectionProvider<Food> getRoundFoodBeans() {
        return roundFoodBeans;
    }

    @Inject
    public void setRoundFoodBeans(@Named("roundFood") final CollectionProvider<Food> roundFoodBeans) {
        this.roundFoodBeans = roundFoodBeans;
    }
}
