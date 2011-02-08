
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Meta-data describing the dependency for a field.
 *
 * @author Christian Ihle
 */
public class FieldData implements InjectionPoint {

    private final Field field;

    private final BeanKey dependency;

    private final List<BeanKey> dependencies;

    /**
     * Creates a new instance of this field data.
     *
     * @param field The field this meta-data is for.
     * @param dependency The required dependency for this field.
     */
    public FieldData(final Field field, final BeanKey dependency) {
        Validate.notNull(field, "Field can not be null");
        Validate.notNull(dependency, "Dependency can not be null");

        this.field = field;
        this.dependency = dependency;
        this.dependencies = Arrays.asList(dependency);
    }

    /**
     * Gets the field this meta-data is for.
     *
     * @return The field.
     */
    public Field getField() {
        return field;
    }

    /**
     * Gets the required dependency for this field.
     *
     * @return The required dependency.
     */
    public BeanKey getDependency() {
        return dependency;
    }

    /**
     * Gets the required dependency for this field as a list.
     *
     * @return The required dependency.
     */
    @Override
    public List<BeanKey> getDependencies() {
        return dependencies;
    }

    /**
     * Sets a value for this field, on the object. Supports fields with any access modifier.
     *
     * @param object The object to set the field value in.
     * @param parameters A single parameter with the new value for this field in the object.
     */
    @Override
    public void inject(final Object object, final Object[] parameters) {
        Validate.notNull(object, "Object can not be null");
        Validate.notNull(parameters, "Parameters can not be null");
        Validate.isTrue(parameters.length == 1, "Can only inject 1 parameter into a field");

        final boolean originalAccessible = field.isAccessible();
        field.setAccessible(true);

        try {
            final Object value = parameters[0];
            field.set(object, value);
        }

        catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        finally {
            field.setAccessible(originalAccessible);
        }
    }

    @Override
    public String toString() {
        return "[field] " + field.toGenericString() + " - " + dependencies;
    }
}
