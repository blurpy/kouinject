
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

package net.usikkert.kouinject.testbeans.scanned.generics.wildcard;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;

/**
 * Testing injections of providers and collections with wildcards.
 *
 * @author Christian Ihle
 */
@Component
public class WildcardInjectionBean {

    @Inject
    private Provider<? extends Fanta> fantaProvider;

    @Inject
    private Collection<? extends Liquid> liquidCollection;

    @Inject
    private CollectionProvider<? extends Liquid> liquidCollectionProvider;

    public Provider<? extends Fanta> getFantaProvider() {
        return fantaProvider;
    }

    public Collection<? extends Liquid> getLiquidCollection() {
        return liquidCollection;
    }

    public CollectionProvider<? extends Liquid> getLiquidCollectionProvider() {
        return liquidCollectionProvider;
    }
}
