
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
     * Creates a new dependency for the specified bean class, with provider set to false,
     * and no qualifier.
     *
     * @param beanClass The actual bean class dependency.
     */
    public Dependency(final Class<?> beanClass) {
        this(beanClass, null);
    }

    /**
     * Creates a new dependency for the specified bean class, with provider set to false.
     *
     * @param beanClass The actual bean class dependency.
     * @param qualifier The qualifier for this dependency.
     */
    public Dependency(final Class<?> beanClass, final String qualifier) {
        this(beanClass, false, qualifier);
    }

    /**
     * Creates a new dependency for the specified bean class.
     *
     * @param beanClass The actual bean class dependency.
     * @param isProvider If the actual bean class is for a provider.
     * @param qualifier The qualifier for this dependency.
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
     * Gets the qualifier for this dependency.
     *
     * <p>A qualifier combined with the class helps identify the bean to inject.
     * See {@link #canInject(Dependency)} for details regarding qualifier rules.</p>
     *
     * @return The qualifier.
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * Checks if the bean can be injected into this dependency.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>The bean must be of the same class or a superclass.</li>
     *   <li>The qualifier must be identical, even if it's <code>null</code>.</li>
     *   <li>Except if the class is an exact match and this qualifier is <code>null</code>.</li>
     * </ul>
     *
     * @param bean The bean to check.
     * @return If the bean can be injected into this dependency.
     */
    public boolean canInject(final Dependency bean) {
        if (bean == null) {
            return false;
        }

        if (bean == this) {
            return true;
        }

        if (beanClass.equals(bean.getBeanClass()) && qualifier == null) {
            return true;
        }

        if (beanClass.isAssignableFrom(bean.getBeanClass())) {
            if (qualifier == null && bean.getQualifier() == null) {
                return true;
            }

            else if (qualifier != null && qualifier.equals(bean.getQualifier())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks equality based on {@link #getBeanClass()} and {@link #getQualifier()}.
     *
     * @param obj The dependency to compare with this.
     * @return If the dependency is equal to this.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Dependency)) {
            return false;
        }

        final Dependency dependency = (Dependency) obj;

        if (beanClass.equals(dependency.getBeanClass())) {
            if (qualifier == null && dependency.getQualifier() == null) {
                return true;
            }

            else if (qualifier != null && qualifier.equals(dependency.getQualifier())) {
                return true;
            }

        }

        return false;
    }

    @Override
    public int hashCode() {
        if (qualifier != null) {
            return beanClass.hashCode() + qualifier.hashCode();
        }

        else {
            return beanClass.hashCode();
        }
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
