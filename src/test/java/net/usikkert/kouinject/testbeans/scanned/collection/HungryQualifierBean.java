
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

package net.usikkert.kouinject.testbeans.scanned.collection;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;

/**
 * Bean used for testing correct dependency injection in collections, with qualifiers.
 *
 * @author Christian Ihle
 */
@Component
public class HungryQualifierBean {

    @Inject
    @Named("fastFood")
    private Collection<Food> fastFoodBeans;

    private final Collection<Food> allFoodBeans;

    private Collection<Food> roundFoodBeans;

    @Inject
    public HungryQualifierBean(@Any final Collection<Food> allFoodBeans) {
        this.allFoodBeans = allFoodBeans;
    }

    public Collection<Food> getFastFoodBeans() {
        return fastFoodBeans;
    }

    public Collection<Food> getAllFoodBeans() {
        return allFoodBeans;
    }

    public Collection<Food> getRoundFoodBeans() {
        return roundFoodBeans;
    }

    @Inject
    public void setRoundFoodBeans(@Named("roundFood") final Collection<Food> roundFoodBeans) {
        this.roundFoodBeans = roundFoodBeans;
    }
}
