
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * Map with all the registered factory points and the beans they can create.
 *
 * @author Christian Ihle
 */
public class FactoryMap {

    /** Map with the factory point as value, and the bean it creates as key. */
    private final Map<BeanKey, FactoryPoint> factoryMap;

    /**
     * Creates a new instance of this factory map, ready to use.
     */
    public FactoryMap() {
        this.factoryMap = new HashMap<BeanKey, FactoryPoint>();
    }

    /**
     * Adds a factory point to the map.
     *
     * @param factoryPoint The factory point to add.
     */
    public synchronized void addFactoryPoint(final FactoryPoint factoryPoint) {
        Validate.notNull(factoryPoint, "FactoryPoint can not be null");

        final BeanKey returnType = factoryPoint.getReturnType();

        if (containsFactoryPoint(returnType)) {
            throw new IllegalArgumentException("Cannot add already existing factory point: " + factoryPoint);
        }

        factoryMap.put(returnType, factoryPoint);
    }

    /**
     * Gets a factory point from the map.
     *
     * @param beanNeeded The type of bean to get a factory point for.
     * @return The factory point for that bean, or {@link IllegalArgumentException} if not found.
     */
    public synchronized FactoryPoint getFactoryPoint(final BeanKey beanNeeded) {
        return getFactoryPoint(beanNeeded, true);
    }

    private FactoryPoint getFactoryPoint(final BeanKey beanNeeded, final boolean throwEx) {
        Validate.notNull(beanNeeded, "Bean needed can not be null");

        final List<BeanKey> matches = new ArrayList<BeanKey>();

        for (final BeanKey bean : factoryMap.keySet()) {
            if (beanNeeded.canInject(bean)) {
                matches.add(bean);
            }
        }

        if (matches.isEmpty()) {
            if (throwEx) {
                throw new IllegalArgumentException("No matching factory point found for " + beanNeeded);
            }

            else {
                return null;
            }
        }

        else if (matches.size() > 1) {
            throw new IllegalStateException("Too many matching factory points found for " + beanNeeded + " " + matches);
        }

        return factoryMap.get(matches.get(0));
    }

    /**
     * Returns the number of factory points in the map.
     *
     * @return The size of the factory map.
     */
    public synchronized int size() {
        return factoryMap.size();
    }

    /**
     * Checks if the factory point exists in the map.
     *
     * @param beanNeeded The type of bean to check if there is a factory point for.
     * @return If the factory point exists in the map.
     */
    public synchronized boolean containsFactoryPoint(final BeanKey beanNeeded) {
        final FactoryPoint factoryPoint = getFactoryPoint(beanNeeded, false);
        return factoryPoint != null;
    }

    /**
     * Searches for all the factory points that can create matching beans, and returns
     * the keys. These keys can be used to get the actual factory points.
     *
     * @param beansNeeded The types of beans to get factory point keys for.
     * @return A collection of factory point keys.
     */
    public synchronized Collection<BeanKey> findFactoryPointKeys(final BeanKey beansNeeded) {
        Validate.notNull(beansNeeded, "Beans needed can not be null");

        final Collection<BeanKey> matches = new ArrayList<BeanKey>();

        for (final BeanKey bean : factoryMap.keySet()) {
            if (beansNeeded.canInject(bean)) {
                matches.add(bean);
            }
        }

        return matches;
    }
}
