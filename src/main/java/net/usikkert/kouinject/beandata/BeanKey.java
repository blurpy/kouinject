
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

package net.usikkert.kouinject.beandata;

import java.lang.reflect.Type;

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

    private final Type beanType;

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
        this.beanType = beanClass;
        this.qualifier = qualifier;
    }

    /**
     * Gets the actual bean class for this key. If this is e.g. a {@link javax.inject.Provider},
     * then this bean class is the generic type argument of the provider.
     *
     * @return The bean class for this key.
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * Gets the actual generic type for this key. If this is e.g. a {@link javax.inject.Provider},
     * then this generic type is the generic type argument of the provider.
     *
     * @return The generic type for this key.
     */
    public Type getBeanType() {
        return beanType;
    }

    /**
     * If this bean key is for a {@link javax.inject.Provider}.
     *
     * @return If this is for a provider. False for the default implementation.
     */
    public boolean isProvider() {
        return false;
    }

    /**
     * If this bean key is for a {@link java.util.Collection} of beans.
     *
     * @return If this is for a collection of beans. False for the default implementation.
     */
    public boolean isCollection() {
        return false;
    }

    /**
     * If this bean key is for a {@link net.usikkert.kouinject.CollectionProvider} of beans.
     *
     * @return If this is for a collection provider of beans. False for the default implementation.
     */
    public boolean isCollectionProvider() {
        return false;
    }

    /**
     * If this bean key can be used to create a correct instance of the bean it represents.
     * Providers and collections aren't beans.
     *
     * @return If this key is for a bean that can be instantiated.
     */
    public boolean isBeanForCreation() {
        return !isCollection() && !isProvider() && !isCollectionProvider();
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
            if (isTheSameQualifier(qualifier, bean.getQualifier())) {
                return true;
            }

            else if (hasTheAnyQualifier()) {
                return true;
            }
        }

        return false;
    }

    /**
     * If this key represents a field or parameter marked for injection,
     * could the bean, if created by a factory, be injected?
     *
     * <p>The same rules as {@link #canInject(BeanKey)} apply, with one exception. Factories may also
     * specify the <code>any</code> qualifier to create beans for any injection point of a matching type.</p>
     *
     * @param factoryCreatedBean The factory-created bean to check.
     * @return If the factory-created bean can be injected into this key.
     */
    public boolean canInjectFromFactory(final BeanKey factoryCreatedBean) {
        if (factoryCreatedBean == null) {
            return false;
        }

        if (canInject(factoryCreatedBean)) {
            return true;
        }

        return beanClass.isAssignableFrom(factoryCreatedBean.getBeanClass())
                && factoryCreatedBean.hasTheAnyQualifier();
    }

    /**
     * Checks if this bean key has the <code>any</code> qualifier.
     *
     * @return If this bean key has the <code>any</code> qualifier.
     */
    public boolean hasTheAnyQualifier() {
        return isTheSameQualifier(qualifier, ANY_QUALIFIER);
    }

    private boolean isTheSameQualifier(final String actualQualifier, final String expectedQualifier) {
        if (actualQualifier == null && expectedQualifier == null) {
            return true;
        }

        return actualQualifier != null && actualQualifier.equalsIgnoreCase(expectedQualifier);
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
            toStringBuilder.append("[q=").append(qualifier).append("] ");
        }

        toStringBuilder.append(beanType.toString());

        return toStringBuilder.toString();
    }
}
