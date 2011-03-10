
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
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileABean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileACBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileBBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileCBean;

import org.junit.Test;

/**
 * Test of {@link AnnotationBasedProfileHandler}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedProfileHandlerTest {

    private static final String PROFILE_A = "ProfileA";
    private static final String PROFILE_B = "ProfileB";
    private static final String PROFILE_C = "ProfileC";

    private AnnotationBasedProfileHandler profileHandler;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfNoProfileLocator() {
        new AnnotationBasedProfileHandler(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfProfileLocatorReturnsNull() {
        final ProfileLocator profileLocator = mock(ProfileLocator.class);
        when(profileLocator.getActiveProfiles()).thenReturn(null);

        new AnnotationBasedProfileHandler(profileLocator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfProfileLocatorReturnsAProfileWithNullValue() {
        loadProfiles(new String[] {null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfProfileLocatorReturnsAProfileWithEmptyValue() {
        loadProfiles(" ");
    }

    @Test
    public void shouldReturnTrueForBeansWithNoProfilesWithNoActiveProfiles() {
        loadProfiles();

        assertTrue(profileHandler.beanIsActive(HelloBean.class));
        assertTrue(profileHandler.beanIsActive(String.class));
        assertTrue(profileHandler.beanIsActive(ProviderBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithNoProfilesWithAllActiveProfiles() {
        loadProfiles(PROFILE_A, PROFILE_B, PROFILE_C);

        assertTrue(profileHandler.beanIsActive(HelloBean.class));
        assertTrue(profileHandler.beanIsActive(String.class));
        assertTrue(profileHandler.beanIsActive(ProviderBean.class));
    }

    @Test
    public void shouldHandleClassesWithNoAnnotations() {
        loadProfiles(PROFILE_A, PROFILE_B, PROFILE_C);

        assertTrue(profileHandler.beanIsActive(NoBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithTheSameActiveProfileA() {
        loadProfiles(PROFILE_A);

        assertTrue(profileHandler.beanIsActive(ProfileABean.class));
        assertTrue(profileHandler.beanIsActive(ProfileACBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithTheSameActiveProfileB() {
        loadProfiles(PROFILE_B);

        assertTrue(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithTheSameActiveProfileC() {
        loadProfiles(PROFILE_C);

        assertTrue(profileHandler.beanIsActive(ProfileCBean.class));
        assertTrue(profileHandler.beanIsActive(ProfileACBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithOneOfTheActiveProfiles() {
        loadProfiles(PROFILE_A, PROFILE_B);

        assertTrue(profileHandler.beanIsActive(ProfileABean.class));
        assertTrue(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldReturnTrueForBeansWithAllTheActiveProfiles() {
        loadProfiles(PROFILE_A, PROFILE_C);

        assertTrue(profileHandler.beanIsActive(ProfileACBean.class));
    }

    @Test
    public void shouldHandleUpperCase() {
        loadProfiles(PROFILE_A.toUpperCase());

        assertTrue(profileHandler.beanIsActive(ProfileABean.class));
        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldHandleLowerCase() {
        loadProfiles(PROFILE_A.toLowerCase());

        assertTrue(profileHandler.beanIsActive(ProfileABean.class));
        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldReturnFalseForBeansWithoutAnActiveProfileA() {
        loadProfiles(PROFILE_A);

        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
        assertFalse(profileHandler.beanIsActive(ProfileCBean.class));
    }

    @Test
    public void shouldReturnFalseForBeansWithoutAnActiveProfileB() {
        loadProfiles(PROFILE_B);

        assertFalse(profileHandler.beanIsActive(ProfileABean.class));
        assertFalse(profileHandler.beanIsActive(ProfileCBean.class));
        assertFalse(profileHandler.beanIsActive(ProfileACBean.class));
    }

    @Test
    public void shouldReturnFalseForBeansWithoutAnActiveProfileC() {
        loadProfiles(PROFILE_C);

        assertFalse(profileHandler.beanIsActive(ProfileABean.class));
        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldReturnFalseForBeansWithoutAnyOfTheActiveProfiles() {
        loadProfiles(PROFILE_A, PROFILE_C);

        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
    }

    @Test
    public void shouldReturnFalseForBeansWithProfileButNoActiveProfiles() {
        loadProfiles();

        assertFalse(profileHandler.beanIsActive(ProfileABean.class));
        assertFalse(profileHandler.beanIsActive(ProfileBBean.class));
        assertFalse(profileHandler.beanIsActive(ProfileCBean.class));
        assertFalse(profileHandler.beanIsActive(ProfileACBean.class));
    }

    @Test
    public void shouldNotBeAbleToModifyProfileListFromLocator() {
        final ProfileLocator profileLocator = mock(ProfileLocator.class);
        final List<String> profiles = new ArrayList<String>();
        profiles.add("profile");
        when(profileLocator.getActiveProfiles()).thenReturn(profiles);

        profileHandler = new AnnotationBasedProfileHandler(profileLocator);

        assertFalse(profileHandler.beanIsActive(ProfileABean.class));

        profiles.add(PROFILE_A);

        assertFalse(profileHandler.beanIsActive(ProfileABean.class));
    }

    private void loadProfiles(final String... profiles) {
        final ProfileLocator profileLocator = mock(ProfileLocator.class);
        when(profileLocator.getActiveProfiles()).thenReturn(Arrays.asList(profiles));

        profileHandler = new AnnotationBasedProfileHandler(profileLocator);
    }
}
