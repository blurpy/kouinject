
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

import net.usikkert.kouinject.util.Validate;

/**
 * Represents a dependency marked for injection.
 *
 * @author Christian Ihle
 */
public class Dependency {

    private final Class<?> beanClass;

    private final boolean isProvider;

    private final String qualifier;

    /**
     * Creates a new dependency for the specified bean class.
     *
     * @param beanClass The actual bean class dependency.
     * @param isProvider If the actual bean class is for a provider.
     * @param qualifier The required qualifier for this dependency.
     */
    public Dependency(final Class<?> beanClass, final boolean isProvider, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        this.beanClass = beanClass;
        this.isProvider = isProvider;
        this.qualifier = qualifier;
    }

    /**
     * Gets the actual bean class dependency. If this is a {@link Provider},
     * then this bean class is the generic type argument of the provider.
     *
     * @return The bean class dependency.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * If this dependency is for a {@link Provider}.
     *
     * @return If this is for a provider.
     */
    public boolean isProvider() {
        return isProvider;
    }

    /**
     * Gets the required qualifier for this dependency.
     *
     * <p>A qualifier combined with the type helps identify the implementation to inject.
     * If the qualifier is null, then the injected bean must have a null qualifier as well.</p>
     *
     * @return The required qualifier.
     */
    public String getQualifier() {
        return qualifier;
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder();

        if (isProvider) {
            toStringBuilder.append("[provider] ");
        }

        if (qualifier != null) {
            toStringBuilder.append("[q=" + qualifier + "] ");
        }

        toStringBuilder.append(beanClass.toString());

        return toStringBuilder.toString();
    }
}