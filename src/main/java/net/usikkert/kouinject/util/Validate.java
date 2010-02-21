
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

package net.usikkert.kouinject.util;

/**
 * Utility for validation of parameters.
 *
 * @author Christian Ihle
 */
public final class Validate {

    /**
     * Private constructor. Only static methods here.
     */
    private Validate() {

    }

    /**
     * Validates that the object is not null.
     *
     * @param object The object to validate.
     * @param message The message to use in the exception.
     * @throws IllegalArgumentException If the object is null.
     */
    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the string is not empty, or only spaces.
     *
     * @param string The string to validate.
     * @param message The message to use in the exception.
     * @throws IllegalArgumentException If the string is empty.
     */
    public static void notEmpty(final String string, final String message) {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the expression is true.
     *
     * @param expression The expression to validate.
     * @param message The message to use in the exception.
     * @throws IllegalArgumentException If the expression is false.
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the expression is false.
     *
     * @param expression The expression to validate.
     * @param message The message to use in the exception.
     * @throws IllegalArgumentException If the expression is true.
     */
    public static void isFalse(final boolean expression, final String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
