
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

package net.usikkert.kouinject.beandata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Meta-data describing the dependencies for a method.
 *
 * @author Christian Ihle
 */
public class MethodData implements InjectionPoint {

    private final Method method;

    private final List<BeanKey> dependencies;

    /**
     * Creates a new instance of this method data.
     *
     * @param method The method this meta-data is for.
     * @param dependencies The required dependencies for this method.
     */
    public MethodData(final Method method, final List<BeanKey> dependencies) {
        Validate.notNull(method, "Method can not be null");
        Validate.notNull(dependencies, "Dependencies can not be null");

        this.method = method;
        this.dependencies = dependencies;
    }

    /**
     * Gets the method this meta-data is for.
     *
     * @return The method.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the required dependencies for this method.
     *
     * @return The required dependencies.
     */
    @Override
    public List<BeanKey> getDependencies() {
        return dependencies;
    }

    /**
     * Invokes this method on the object with the specified parameters. Supports method with any access modifier.
     *
     * @param object The object to invoke the method on.
     * @param parameters The parameters for the method.
     */
    @Override
    public void inject(final Object object, final Object[] parameters) {
        Validate.notNull(object, "Object can not be null");
        Validate.notNull(parameters, "Parameters can not be null");

        final boolean originalAccessible = method.isAccessible();
        setAccessible(true);

        try {
            method.invoke(object, parameters);
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

    @Override
    public String toString() {
        return "[method] " + method.toGenericString() + " - " + dependencies;
    }
}
