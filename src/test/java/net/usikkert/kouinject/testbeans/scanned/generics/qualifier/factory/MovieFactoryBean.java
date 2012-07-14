
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

package net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory;

import javax.inject.Named;
import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.factory.FactoryContext;

import org.apache.commons.lang.Validate;

/**
 * A factory for creating generic movie beans.
 *
 * @author Christian Ihle
 */
@Component
public class MovieFactoryBean {

    @Produces @Singleton @Named("ScaryMovie")
    public Movie<Horror> createScaryMovie() {
        return new BluRay<Horror>("Scary Movie");
    }

    @Produces @Named("Scream")
    public Movie<Horror> createScream() {
        return new Dvd<Horror>("Scream");
    }

    @Produces @Named("MeetFockers")
    public Movie<Comedy> createMeetTheFockers() {
        return new BluRay<Comedy>("Meet the Fockers");
    }

    @Produces @Any
    public Movie<Action> createAnyActionMovie(final FactoryContext factoryContext) {
        final String title = factoryContext.getQualifier();
        Validate.notNull(title);

        return new BluRay<Action>("Action: " + title);
    }

    @Produces @Any
    public Movie<Drama> createAnyDramaMovie(final FactoryContext factoryContext) {
        final String title = factoryContext.getQualifier();
        Validate.notNull(title);

        return new Dvd<Drama>("Drama: " + title);
    }
}
