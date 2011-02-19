
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

package net.usikkert.kouinject.testbeans.scanned.factory;

import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

/**
 * A bean with parameters created using a factory.
 *
 * @author Christian Ihle
 */
public class ThreeParametersFactoryCreatedBean {

    private boolean createdByFactory;
    private ColorBean colorBean;
    private CoffeeBean coffeeBean;
    private RedBean redBean;

    public boolean isCreatedByFactory() {
        return createdByFactory;
    }

    public void setCreatedByFactory(final boolean createdByFactory) {
        this.createdByFactory = createdByFactory;
    }

    public ColorBean getColorBean() {
        return colorBean;
    }

    public void setColorBean(final ColorBean colorBean) {
        this.colorBean = colorBean;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(final CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
    }

    public RedBean getRedBean() {
        return redBean;
    }

    public void setRedBean(final RedBean redBean) {
        this.redBean = redBean;
    }
}
