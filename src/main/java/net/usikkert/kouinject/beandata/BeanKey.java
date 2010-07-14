
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

package net.usikkert.kouinject.beandata;

import org.apache.commons.lang.Validate;

/**
 * Represents a bean key for acquiring a real bean instance or the bean data required
 * to create an instance.
 *
 * @author Christian Ihle
 */
public class BeanKey {

    /** Value of qualifier used to mark that an injection point accepts beans with any or no qualifier. */
    private static final String ANY_QUALIFIER = "any";

    private final Class<?> beanClass;

    private final String qualifier;

    /**
     * Creates a new bean key for the specified bean class, with provider set to false,
     * and no qualifier.
     *
     * @param beanClass The actual bean class for this key.
     */
    public BeanKey(final Class<?> beanClass) {
        this(beanClass, null);
    }

    /**
     * Creates a new bean key for the specified bean class, with provider set to false.
     *
     * @param beanClass The actual bean class for this key.
     * @param qualifier The qualifier for this key.
     */
    public BeanKey(final Class<?> beanClass, final String qualifier) {
        Validate.notNull(beanClass, "Bean class can not be null");

        this.beanClass = beanClass;
        this.qualifier = qualifier;
    }

    /**
     * Gets the actual bean class for this key. If this is a {@link Provider},
     * then this bean class is the generic type argument of the provider.
     *
     * @return The bean class for this key.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * If this bean key is for a {@link Provider}.
     *
     * @return If this is for a provider. False for the default implementation.
     */
    public boolean isProvider() {
        return false;
    }

    /**
     * If this bean key is for a collection of beans.
     *
     * @return If this is for a collection of beans. False for the default implementation.
     */
    public boolean isCollection() {
        return false;
    }

    /**
     * Gets the qualifier for this bean key.
     *
     * <p>A qualifier combined with the class helps identify the bean to inject.
     * See {@link #canInject(BeanKey)} for details regarding qualifier rules.</p>
     *
     * @return The qualifier.
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * If this key represents a field or parameter marked for injection,
     * could the bean be injected?
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>The bean must be of the same class or a superclass.</li>
     *   <li>The qualifier must be identical, even if it's <code>null</code>.</li>
     *   <li>Except if the class is an exact match and this qualifier is <code>null</code>.</li>
     *   <li>Or this qualifier is <code>any</code>.</li>
     * </ul>
     *
     * @param bean The bean to check.
     * @return If the bean can be injected into this key.
     */
    public boolean canInject(final BeanKey bean) {
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

            else if (qualifier != null && qualifier.equalsIgnoreCase(bean.getQualifier())) {
                return true;
            }

            else if (qualifier != null && qualifier.equalsIgnoreCase(ANY_QUALIFIER)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks equality based on {@link #getBeanClass()} and {@link #getQualifier()}.
     *
     * @param obj The key to compare with this.
     * @return If the key is equal to this.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BeanKey)) {
            return false;
        }

        final BeanKey beanKey = (BeanKey) obj;

        if (beanClass.equals(beanKey.getBeanClass())) {
            if (qualifier == null && beanKey.getQualifier() == null) {
                return true;
            }

            else if (qualifier != null && qualifier.equalsIgnoreCase(beanKey.getQualifier())) {
                return true;
            }

        }

        return false;
    }

    @Override
    public int hashCode() {
        if (qualifier != null) {
            return beanClass.hashCode() + qualifier.toLowerCase().hashCode();
        }

        else {
            return beanClass.hashCode();
        }
    }

    @Override
    public String toString() {
        final StringBuilder toStringBuilder = new StringBuilder();

        if (qualifier != null) {
            toStringBuilder.append("[q=" + qualifier + "] ");
        }

        toStringBuilder.append(beanClass.toString());

        return toStringBuilder.toString();
    }
}