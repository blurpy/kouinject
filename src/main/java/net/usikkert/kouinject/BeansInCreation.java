
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

package net.usikkert.kouinject;

import java.util.ArrayList;
import java.util.Collection;

import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * Class for keeping control of beans while they are being created.
 *
 * @author Christian Ihle
 */
public class BeansInCreation {

    private final Collection<BeanKey> beansInCreation;

    /**
     * Creates a new instance of this beans in creation class, ready to use.
     */
    public BeansInCreation() {
        beansInCreation = new ArrayList<BeanKey>();
    }

    /**
     * Adds a new bean to the beans in creation list.
     *
     * @param bean The bean to add.
     */
    public synchronized void addBean(final BeanKey bean) {
        Validate.notNull(bean, "Bean can not be null");

        if (containsBean(bean)) {
            throw new IllegalStateException("Circular dependency - bean already in creation: " + bean);
        }

        beansInCreation.add(bean);
    }

    /**
     * Removes a bean from the beans in creation list.
     *
     * @param bean The bean to remove.
     */
    public synchronized void removeBean(final BeanKey bean) {
        Validate.notNull(bean, "Bean can not be null");

        beansInCreation.remove(bean);
    }

    /**
     * Checks if a bean is currently being created.
     *
     * @param bean The bean to check.
     * @return If the bean is in creation.
     */
    public synchronized boolean containsBean(final BeanKey bean) {
        Validate.notNull(bean, "Bean can not be null");

        return beansInCreation.contains(bean);
    }

    /**
     * Returns the number of beans currently in creation.
     *
     * @return The number of beans in creation.
     */
    public synchronized int size() {
        return beansInCreation.size();
    }
}
