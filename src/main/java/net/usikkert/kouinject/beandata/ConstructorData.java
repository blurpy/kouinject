
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.usikkert.kouinject.util.Validate;

/**
 * Meta-data describing the dependencies for a constructor.
 *
 * @author Christian Ihle
 */
public class ConstructorData {

    private final Constructor<?> constructor;

    private final List<Dependency> dependencies;

    /**
     * Creates a new instance of this constructor data.
     *
     * @param constructor The constructor this meta-data is for.
     * @param dependencies The required dependencies for this constructor.
     */
    public ConstructorData(final Constructor<?> constructor, final List<Dependency> dependencies) {
        Validate.notNull(constructor, "Constructor can not be null");
        Validate.notNull(dependencies, "Dependencies can not be null");

        this.constructor = constructor;
        this.dependencies = dependencies;
    }

    /**
     * Gets the constructor this meta-data is for.
     *
     * @return The constructor.
     */
    public Constructor<?> getConstructor() {
        return constructor;
    }

    /**
     * Gets the required dependencies for this constructor.
     *
     * @return The required dependencies.
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    /**
     * Invokes this constructor with the specified parameters, and returns the new instance.
     * Supports constructor with any access modifier.
     *
     * @param parameters The parameters for the constructor.
     * @return A new instance of the class the constructor belongs to.
     */
    public Object createInstance(final Object[] parameters) {
        Validate.notNull(parameters, "Parameters can not be null");

        final boolean originalAccessible = constructor.isAccessible();
        constructor.setAccessible(true);

        try {
            final Object newInstance = constructor.newInstance(parameters);
            return newInstance;
        }

        catch (final InstantiationException e) {
            throw new RuntimeException(e);
        }

        catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        finally {
            constructor.setAccessible(originalAccessible);
        }
    }

    @Override
    public String toString() {
        return constructor.toGenericString();
    }
}
