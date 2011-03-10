
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * An implementation of {@link ProfileLocator} that accepts the active profiles as a collection of strings.
 *
 * @author Christian Ihle
 */
public class InputBasedProfileLocator implements ProfileLocator {

    private final Collection<String> activeProfiles;

    /**
     * Creates a new profile locator using the collection of profiles as the currently active profiles for the injector.
     *
     * @param activeProfiles The active profiles to use.
     */
    public InputBasedProfileLocator(final Collection<String> activeProfiles) {
        Validate.notNull(activeProfiles, "Active profiles can not be null");

        for (final String activeProfile : activeProfiles) {
            Validate.isTrue(StringUtils.isNotBlank(activeProfile), "Active profile can not be empty");
        }

        this.activeProfiles = Collections.unmodifiableSet(new HashSet<String>(activeProfiles));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getActiveProfiles() {
        return activeProfiles;
    }
}
