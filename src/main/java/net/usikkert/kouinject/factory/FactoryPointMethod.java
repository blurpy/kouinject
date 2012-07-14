
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

package net.usikkert.kouinject.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * An implementation of {@link FactoryPoint} for representing factory methods.
 *
 * @author Christian Ihle
 * @param <T> The type of class this factory point can create instances of.
 */
public class FactoryPointMethod<T> implements FactoryPoint<T> {

    private final Method method;
    private final BeanKey factoryKey;
    private final BeanKey returnType;
    private final List<BeanKey> parameters;
    private final boolean singleton;

    /**
     * Creates a new instance with all the requirements for invoking a factory method.
     *
     * @param method The factory method.
     * @param factoryKey The key to the actual factory bean containing this method.
     * @param returnType The return type of this factory method.
     * @param parameters The required parameters for the method to be invoked.
     * @param singleton If the bean created by this factory method should be a singleton.
     */
    public FactoryPointMethod(final Method method, final BeanKey factoryKey, final BeanKey returnType,
                              final List<BeanKey> parameters, final boolean singleton) {
        Validate.notNull(method, "Method can not be null");
        Validate.notNull(factoryKey, "Factory key can not be null");
        Validate.notNull(returnType, "Return type can not be null");
        Validate.notNull(parameters, "Parameters can not be null");

        this.method = method;
        this.factoryKey = factoryKey;
        this.returnType = returnType;
        this.parameters = Collections.unmodifiableList(parameters);
        this.singleton = singleton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BeanKey getFactoryKey() {
        return factoryKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(final Object factoryInstance, final Object... factoryPointParameters) {
        Validate.notNull(factoryInstance, "Factory instance can not be null");
        Validate.notNull(factoryPointParameters, "Factory point parameters can not be null");

        for (final Object parameter : factoryPointParameters) {
            Validate.notNull(parameter, "Parameter can not be null");
        }

        final boolean originalAccessible = method.isAccessible();
        setAccessible(true);

        try {
            final T returnValue = (T) method.invoke(factoryInstance, factoryPointParameters);

            if (returnValue == null) {
                throw new UnsupportedOperationException("Factory method returned null: " + method);
            }

            return returnValue;
        }

        catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        finally {
            setAccessible(originalAccessible);
        }
    }

    private void setAccessible(final boolean accessible) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                // Requires java.lang.reflect.ReflectPermission "suppressAccessChecks"
                method.setAccessible(accessible);
                return null;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BeanKey> getParameters() {
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BeanKey getReturnType() {
        return returnType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * Gets the actual reflection method this factory method will use to create beans.
     *
     * @return The reflection method.
     */
    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "[factory point method] " + method.toGenericString() + " - " + parameters;
    }
}
