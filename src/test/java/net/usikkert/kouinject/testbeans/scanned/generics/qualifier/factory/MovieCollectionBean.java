
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

package net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;

/**
 * A bean injecting different generic beans with qualifiers created by a factory.
 *
 * @author Christian Ihle
 */
@Component
public class MovieCollectionBean {

    @Inject @Named("ScaryMovie")
    private Movie<Horror> scaryMovie;

    @Inject @Named("Scream")
    private Movie<Horror> screamMovie;

    @Inject @Named("MeetFockers")
    private Movie<Comedy> fockersMovie;

    @Inject @Any
    private Collection<Movie<Horror>> horrorMovies;

    @Inject @Any
    private Collection<Movie<? extends Genre>> allMovies;

    public Movie<Horror> getScaryMovie() {
        return scaryMovie;
    }

    public Movie<Horror> getScreamMovie() {
        return screamMovie;
    }

    public Movie<Comedy> getFockersMovie() {
        return fockersMovie;
    }

    public Collection<Movie<Horror>> getHorrorMovies() {
        return horrorMovies;
    }

    public Collection<Movie<? extends Genre>> getAllMovies() {
        return allMovies;
    }
}
