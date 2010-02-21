
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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test of {@link Validate}.
 *
 * @author Christian Ihle
 */
public class ValidateTest {

    @Test
    public void notNullShouldAcceptAnObject() {
        Validate.notNull(new Object(), "notNull should not fail");
    }

    @Test
    public void notNullShouldFailWithNullAndIncludeMessageInException() {
        try {
            Validate.notNull(null, "notNull should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("notNull should fail", e.getMessage());
        }
    }

    @Test
    public void isTrueShouldAcceptTrue() {
        Validate.isTrue(true, "isTrue should not fail");
    }

    @Test
    public void isTrueShouldFailWithFalseAndIncludeMessageInException() {
        try {
            Validate.isTrue(false, "isTrue should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("isTrue should fail", e.getMessage());
        }
    }

    @Test
    public void isFalseShouldAcceptFalse() {
        Validate.isFalse(false, "isFalse should not fail");
    }

    @Test
    public void isFalseShouldFailWithTrueAndIncludeMessageInException() {
        try {
            Validate.isFalse(true, "isFalse should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("isFalse should fail", e.getMessage());
        }
    }

    @Test
    public void notEmptyShouldAcceptStringWithContent() {
        Validate.notEmpty("not empty", "notEmpty should not fail");
    }

    @Test
    public void notEmptyShouldThrowExceptionIfStringIsNull() {
        try {
            Validate.notEmpty(null, "notEmpty should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("notEmpty should fail", e.getMessage());
        }
    }

    @Test
    public void notEmptyShouldThrowExceptionIfStringIsEmpty() {
        try {
            Validate.notEmpty("", "notEmpty should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("notEmpty should fail", e.getMessage());
        }
    }

    @Test
    public void notEmptyShouldThrowExceptionIfStringIsOnlySpaces() {
        try {
            Validate.notEmpty(" ", "notEmpty should fail");
            fail("Should have got an exception");
        }

        catch (final IllegalArgumentException e) {
            assertEquals("notEmpty should fail", e.getMessage());
        }
    }
}
