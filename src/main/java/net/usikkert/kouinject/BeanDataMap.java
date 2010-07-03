
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

package net.usikkert.kouinject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.Dependency;

import org.apache.commons.lang.Validate;

/**
 * Map with bean-data for all the beans possible to create.
 *
 * @author Christian Ihle
 */
public class BeanDataMap {

    private final Map<Dependency, BeanData> beanDataMap;

    /**
     * Creates a new instance of this bean-data map, ready to use.
     */
    public BeanDataMap() {
        this.beanDataMap = new HashMap<Dependency, BeanData>();
    }

    /**
     * Adds bean-data to the map.
     *
     * @param description The description of the bean-data.
     * @param beanData The bean-data to add.
     */
    public synchronized void addBeanData(final Dependency description, final BeanData beanData) {
        Validate.notNull(description, "Description can not be null");
        Validate.notNull(beanData, "BeanData can not be null");

        if (containsBeanData(description)) {
            throw new IllegalArgumentException("Cannot add already existing bean-data: " + description);
        }

        beanDataMap.put(description, beanData);
    }

    /**
     * Gets bean-data from the map.
     *
     * @param beanNeeded The description of the bean-data to get.
     * @return The bean-data, or {@link IllegalArgumentException} if not found.
     */
    public synchronized BeanData getBeanData(final Dependency beanNeeded) {
        return getBeanData(beanNeeded, true);
    }

    private BeanData getBeanData(final Dependency beanNeeded, final boolean throwEx) {
        Validate.notNull(beanNeeded, "Bean needed can not be null");

        final List<Dependency> matches = new ArrayList<Dependency>();
        final Iterator<Dependency> iterator = beanDataMap.keySet().iterator();

        while (iterator.hasNext()) {
            final Dependency bean = iterator.next();

            if (beanNeeded.canInject(bean)) {
                matches.add(bean);
            }
        }

        if (matches.size() == 0) {
            if (throwEx) {
                throw new IllegalArgumentException("No matching bean-data found for " + beanNeeded);
            }

            else {
                return null;
            }
        }

        else if (matches.size() > 1) {
            throw new IllegalStateException("Too many matching bean-data found for " + beanNeeded + " " + matches);
        }

        return beanDataMap.get(matches.get(0));
    }

    /**
     * Returns the size of the bean-data map.
     *
     * @return The size of the bean-data map.
     */
    public synchronized int size() {
        return beanDataMap.size();
    }

    /**
     * Checks if the bean-data exists in the map.
     *
     * @param description The description of the bean-data.
     * @return If the bean-data exists in the map.
     */
    public synchronized boolean containsBeanData(final Dependency description) {
        Validate.notNull(description, "Description can not be null");

        final BeanData beanData = getBeanData(description, false);
        return beanData != null;
    }

    /**
     * Searches for all the beans that matches the description, and returns
     * the keys. These keys can be used to get the actual bean-data.
     *
     * @param description Description of the beans to search for.
     * @return A collection of bean keys.
     */
    public synchronized Collection<Dependency> findBeanKeys(final Dependency description) {
        Validate.notNull(description, "Description can not be null");

        final Collection<Dependency> matches = new ArrayList<Dependency>();
        final Iterator<Dependency> iterator = beanDataMap.keySet().iterator();

        while (iterator.hasNext()) {
            final Dependency bean = iterator.next();

            if (description.canInject(bean)) {
                matches.add(bean);
            }
        }

        return matches;

    }
}
