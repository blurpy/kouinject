
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

package net.usikkert.kouinject;

import org.apache.commons.lang.Validate;

/**
 * Represents wrapper for a temporary state of a bean created by the bean loader.
 *
 * @author Christian Ihle
 */
public class CreatedBean {

    private final Object instance;
    private final boolean singleton;
    private final String qualifier;

    /**
     * Creates a new wrapper for a bean instance.
     *
     * @param instance An instance of a bean.
     * @param singleton If the bean is a singleton.
     * @param qualifier The qualifier of the bean. May be <code>null</code>.
     */
    public CreatedBean(final Object instance, final boolean singleton, final String qualifier) {
        Validate.notNull(instance, "Bean instance can not be null");

        this.instance = instance;
        this.singleton = singleton;
        this.qualifier = qualifier;
    }

    /**
     * Gets the instance of this created bean.
     *
     * @return The instance of this bean.
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * Checks if the bean is a singleton.
     *
     * @return If the bean is a singleton.
     */
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * Gets the qualifier of the bean. May be <code>null</code>.
     *
     * @return The qualifier of the bean.
     */
    public String getQualifier() {
        return qualifier;
    }
}
