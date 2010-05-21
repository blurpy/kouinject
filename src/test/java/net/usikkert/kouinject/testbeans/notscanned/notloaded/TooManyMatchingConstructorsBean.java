
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

package net.usikkert.kouinject.testbeans.notscanned.notloaded;

import javax.inject.Inject;

import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;

/**
 * Bean for testing what happens when 2 constructors are marked for injection.
 *
 * @author Christian Ihle
 */
public class TooManyMatchingConstructorsBean {

    private final ConstructorBean constructorBean;

    private final CoffeeBean coffeeBean;

    @Inject
    public TooManyMatchingConstructorsBean(final ConstructorBean constructorBean) {
        this.constructorBean = constructorBean;
        coffeeBean = null;
    }

    @Inject
    public TooManyMatchingConstructorsBean(final ConstructorBean constructorBean, final CoffeeBean coffeeBean) {
        this.constructorBean = constructorBean;
        this.coffeeBean = coffeeBean;
    }

    public ConstructorBean getConstructorBean() {
        return constructorBean;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }
}
