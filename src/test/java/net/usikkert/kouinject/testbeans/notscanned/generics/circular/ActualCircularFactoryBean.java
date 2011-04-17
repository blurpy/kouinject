
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

package net.usikkert.kouinject.testbeans.notscanned.generics.circular;

import javax.inject.Named;

import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Pentagon;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Shape;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.ShapeImpl;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Square;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Star;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Triangle;

/**
 * A factory for testing generic circular dependencies that should fail.
 *
 * @author Christian Ihle
 */
public class ActualCircularFactoryBean {

    // Injects a star that injects a triangle that ...
    @Produces
    public Shape<Triangle> createTriangleShape(final Shape<Star> starShape) {
        return new ShapeImpl<Triangle>(Triangle.class, starShape);
    }

    // Injects a triangle that injects a star that ...
    @Produces
    public Shape<Star> createStarShape(final Shape<Triangle> triangleShape) {
        return new ShapeImpl<Star>(Star.class, triangleShape);
    }

    // Injects an instance of itself
    @Produces
    public ShapeImpl<Square> createSquareShape(final Shape<Square> squareShape) {
        return new ShapeImpl<Square>(Square.class, squareShape);
    }

    // Injects second pentagon that injects the first pentagon that ...
    @Produces @Named("FirstPentagon")
    public Shape<Pentagon> createFirstPentagonShape(@Named("SecondPentagon") final Shape<Pentagon> pentagonShape) {
        return new ShapeImpl<Pentagon>(Pentagon.class, pentagonShape);
    }

    // Injects first pentagon that injects the second pentagon that ...
    @Produces @Named("SecondPentagon")
    public Shape<Pentagon> createSecondPentagonShape(@Named("FirstPentagon") final Shape<Pentagon> pentagonShape) {
        return new ShapeImpl<Pentagon>(Pentagon.class, pentagonShape);
    }
}
