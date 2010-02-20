
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.usikkert.kouinject.beandata.Dependency;

/**
 * Map with cached singleton beans.
 *
 * @author Christian Ihle
 */
public class SingletonMap {

    private final Map<Dependency, Object> singletons;

    /**
     * Creates a new instance of this singleton map, ready to use.
     */
    public SingletonMap() {
        this.singletons = new HashMap<Dependency, Object>();
    }

    /**
     * Adds a new singleton to the cache.
     *
     * @param description The description of the singleton.
     * @param singleton The bean to add as as singleton.
     */
    public synchronized void addSingleton(final Dependency description, final Object singleton) {
        if (singletonAlreadyExists(description)) {
            throw new IllegalArgumentException("Cannot add already existing singleton: " + description);
        }

        singletons.put(description, singleton);
    }

    /**
     * Gets a singleton from the cache.
     *
     * @param singletonNeeded The description of the singleton to get.
     * @param throwEx If an exception should be thrown if no singleton was found.
     * @return The singleton, or <code>null</code>, or {@link IllegalArgumentException}.
     */
    public synchronized Object getSingleton(final Dependency singletonNeeded, final boolean throwEx) {
        final List<Dependency> matches = new ArrayList<Dependency>();
        final Iterator<Dependency> iterator = singletons.keySet().iterator();

        while (iterator.hasNext()) {
            final Dependency singleton = iterator.next();

            if (singletonNeeded.canInject(singleton)) {
                matches.add(singleton);
            }
        }

        if (matches.size() == 0) {
            if (throwEx) {
                throw new IllegalArgumentException("No matching singleton found for " + singletonNeeded);
            }

            else {
                return null;
            }
        }

        else if (matches.size() > 1) {
            throw new IllegalStateException("Too many matching singletons found for " + singletonNeeded + " " + matches);
        }

        return singletons.get(matches.get(0));
    }

    /**
     * Returns the number of singletons in the cache.
     *
     * @return The number of singletons.
     */
    public synchronized int size() {
        return singletons.size();
    }

    private boolean singletonAlreadyExists(final Dependency bean) {
        final Object existingBean = getSingleton(bean, false);
        return existingBean != null;
    }
}
