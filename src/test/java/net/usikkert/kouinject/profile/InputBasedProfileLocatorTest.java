
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

package net.usikkert.kouinject.profile;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test of {@link InputBasedProfileLocator}.
 *
 * @author Christian Ihle
 */
public class InputBasedProfileLocatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfListOfProfilesIsNull() {
        new InputBasedProfileLocator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfAProfileIsNull() {
        new InputBasedProfileLocator(Arrays.asList("profileA", null, "profileC"));
    }

    @Test
    public void shouldHandleEmptyList() {
        final InputBasedProfileLocator locator = new InputBasedProfileLocator(new ArrayList<String>());

        final List<String> activeProfiles = locator.getActiveProfiles();
        assertNotNull(activeProfiles);
        assertTrue(activeProfiles.isEmpty());
    }

    @Test
    public void shouldReturnTheSameProfilesAsUsedInConstructor1() {
        final InputBasedProfileLocator locator = new InputBasedProfileLocator(
                Arrays.asList("profile1", "profile2", "otherprofile"));

        final List<String> activeProfiles = locator.getActiveProfiles();
        assertNotNull(activeProfiles);
        assertEquals(3, activeProfiles.size());

        assertTrue(activeProfiles.contains("profile1"));
        assertTrue(activeProfiles.contains("profile2"));
        assertTrue(activeProfiles.contains("otherprofile"));
    }

    @Test
    public void shouldReturnTheSameProfilesAsUsedInConstructor2() {
        final InputBasedProfileLocator locator = new InputBasedProfileLocator(Arrays.asList("profile"));

        final List<String> activeProfiles = locator.getActiveProfiles();
        assertNotNull(activeProfiles);
        assertEquals(1, activeProfiles.size());

        assertTrue(activeProfiles.contains("profile"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToModifyReturnedProfileList() {
        final ArrayList<String> profiles = new ArrayList<String>();
        profiles.add(("profile"));

        final InputBasedProfileLocator locator = new InputBasedProfileLocator(profiles);

        final List<String> activeProfiles = locator.getActiveProfiles();
        assertNotNull(activeProfiles);
        assertEquals(1, activeProfiles.size());

        activeProfiles.add("new");
    }

    @Test
    public void shouldNotBeAbleToModifyInputProfileList() {
        final ArrayList<String> profiles = new ArrayList<String>();
        profiles.add(("profile"));

        final InputBasedProfileLocator locator = new InputBasedProfileLocator(profiles);

        final List<String> activeProfiles1 = locator.getActiveProfiles();
        assertNotNull(activeProfiles1);
        assertEquals(1, activeProfiles1.size());
        assertTrue(activeProfiles1.contains("profile"));

        profiles.add("new");

        final List<String> activeProfiles2 = locator.getActiveProfiles();
        assertNotNull(activeProfiles2);
        assertEquals(1, activeProfiles2.size());
        assertTrue(activeProfiles2.contains("profile"));
    }
}
