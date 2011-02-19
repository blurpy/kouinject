
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

package net.usikkert.kouinject.factory;

import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;

/**
 * This interface represents a factory point like a method or a field.
 *
 * <p>A factory point creates new instances of beans, and defines their qualifiers
 * and scope. Methods may also inject parameters for usage when creating the beans.</p>
 *
 * @author Christian Ihle
 * @param <T> The type of class this factory point can create instances of.
 */
public interface FactoryPoint<T> {

    /**
     * The key describing the actual bean containing this factory member.
     *
     * @return The key for getting an instance of the factory with this factory point.
     */
    BeanKey getFactoryKey();

    /**
     * Creates a new instance of the return type of this factory point, and injects the parameters if there are any.
     *
     * @param factoryInstance The actual factory object to use when creating the bean with this factory point.
     *                        Use {@link #getFactoryKey()}.
     * @param factoryPointParameters Instances of the parameters to inject into the factory point.
     *                               Use {@link #getParameters()}.
     * @return An instance of {@link #getReturnType()}.
     */
    T create(final Object factoryInstance, final Object... factoryPointParameters);

    /**
     * Gets the keys to the parameters required to execute this factory point.
     *
     * @return A list of the required parameters.
     */
    List<BeanKey> getParameters();

    /**
     * The class and qualifier of the object created by this factory point.
     *
     * @return {@link BeanKey} for this factory point.
     */
    BeanKey getReturnType();

    /**
     * Returns if the object created by this factory point should be a singleton.
     *
     * @return If the object created by this factory point should be a singleton.
     */
    boolean isSingleton();
}
