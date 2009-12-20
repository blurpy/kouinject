
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

package net.usikkert.kouinject.beandata;

/**
 * Description of a unique bean.
 *
 * @author Christian Ihle
 */
public class Bean {

    private final Class<?> beanClass;

    private final String qualifier;

    /**
     * Creates a new bean for the given class with the given qualifier.
     *
     * @param beanClass The actual class this bean is for.
     * @param qualifier The required qualifier for this bean.
     */
    public Bean(final Class<?> beanClass, final String qualifier) {
        this.beanClass = beanClass;
        this.qualifier = qualifier;
    }

    /**
     * The actual class this bean is for.
     *
     * @return The actual bean class.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * Gets the required qualifier for this bean.
     *
     * <p>A qualifier combined with the class helps identify if this is the correct bean to
     * inject in a dependency. If the qualifier is null, then the dependency must have a null
     * qualifier as well.</p>
     *
     * @return The required qualifier.
     */
    public String getQualifier() {
        return qualifier;
    }
}
