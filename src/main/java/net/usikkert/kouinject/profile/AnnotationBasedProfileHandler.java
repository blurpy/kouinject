
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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.usikkert.kouinject.annotation.Profile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * An implementation of {@link ProfileHandler} using the {@link Profile}
 * annotation to find the profiles on a bean.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedProfileHandler implements ProfileHandler {

    private static final Class<Profile> PROFILE_ANNOTATION = Profile.class;

    private final List<String> activeProfiles;

    /**
     * Creates a new profile handler using the active profiles returned by the profile locator.
     *
     * @param profileLocator The locator to use to get the currently active profiles.
     */
    public AnnotationBasedProfileHandler(final ProfileLocator profileLocator) {
        Validate.notNull(profileLocator, "Profile locator can not be null");

        final List<String> profiles = profileLocator.getActiveProfiles();
        validateActiveProfiles(profiles);
        activeProfiles = Collections.unmodifiableList(new ArrayList<String>(profiles));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean beanIsActive(final Class<?> beanClass) {
        Validate.notNull(beanClass, "Bean class can not be null");

        final List<String> beanProfiles = getBeanProfiles(beanClass);
        return beanHasActiveProfile(beanProfiles);
    }

    private List<String> getBeanProfiles(final Class<?> beanClass) {
        final List<String> beanProfiles = new ArrayList<String>();
        final Annotation[] annotations = beanClass.getAnnotations();

        for (final Annotation annotation : annotations) {
            final Class<? extends Annotation> annotationType = annotation.annotationType();

            if (annotationType.isAnnotationPresent(PROFILE_ANNOTATION)) {
                beanProfiles.add(annotationType.getSimpleName());
            }
        }

        return beanProfiles;
    }

    private boolean beanHasActiveProfile(final List<String> beanProfiles) {
        if (beanProfiles.isEmpty()) {
            return true;
        }

        for (final String beanProfile : beanProfiles) {
            for (final String activeProfile : activeProfiles) {
                if (beanProfile.equalsIgnoreCase(activeProfile)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void validateActiveProfiles(final List<String> activeProfilesForValidation) {
        Validate.notNull(activeProfilesForValidation, "Active profiles can not be null");

        for (final String activeProfile : activeProfilesForValidation) {
            Validate.isTrue(StringUtils.isNotBlank(activeProfile), "Active profile can not be empty");
        }
    }
}
