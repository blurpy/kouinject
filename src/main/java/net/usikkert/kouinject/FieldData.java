
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

import java.lang.reflect.Field;

import net.usikkert.kouinject.util.Validate;

/**
 * Meta-data describing the dependency for a field.
 *
 * @author Christian Ihle
 */
public class FieldData {

    private final Field field;

    private final Dependency dependency;

    /**
     * Creates a new instance of this field data.
     *
     * @param field The field this meta-data is for.
     * @param dependency The required dependency for this field.
     */
    public FieldData(final Field field, final Dependency dependency) {
        Validate.notNull(field, "Field can not be null");
        Validate.notNull(dependency, "Dependency can not be null");

        this.field = field;
        this.dependency = dependency;
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
    public Dependency getDependency() {
        return dependency;
    }

    /**
     * Sets a value for this field, on the object. Supports fields with any access modifier.
     *
     * @param object The object to set the field value in.
     * @param value The new value for this field in the object.
     */
    public void setFieldValue(final Object object, final Object value) {
        Validate.notNull(object, "Object can not be null");
        Validate.notNull(value, "Value can not be null");

        final boolean originalAccessible = field.isAccessible();
        field.setAccessible(true);

        try {
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
        return field.toGenericString();
    }
}