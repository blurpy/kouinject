
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

package net.usikkert.kouinject.generics;

import java.util.Collection;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Provider;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;

/**
 * An abstract class with various fields using it.
 *
 * @author Christian Ihle
 */
public abstract class TypeVariableBean<T> {

    @Named("justT")
    private T standaloneT;

    @Named("providerT")
    private Provider<T> providerWithT;

    @Named("collectionT")
    private Collection<T> collectionWithT;

    @Named("collectionProviderT")
    private CollectionProvider<T> collectionProviderWithT;

    private T[] arrayT;
    private Set<Container<T[]>> setOfContainersOfArrayT;
    private Container<T> containerOfT;
    private Set<Container<T>> setOfContainersOfT;
    private Set<Container<? extends T>> setOfContainersOfExtendsT;
    private Set<Container<? super T>> setOfContainersOfSuperT;

    public TypeVariableBean() {

    }

    public TypeVariableBean(@Named("constructorT") final T t) {

    }

    public void methodWithT(@Named("methodT") final T t) {

    }

    @Named("factoryT")
    public T factoryWithT() {
        return null;
    }
}
