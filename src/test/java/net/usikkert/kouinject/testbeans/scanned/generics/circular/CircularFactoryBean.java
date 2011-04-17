
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

package net.usikkert.kouinject.testbeans.scanned.generics.circular;

import javax.inject.Provider;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;

/**
 * A factory for testing injection of generic circular dependencies.
 *
 * @author Christian Ihle
 */
@Component
public class CircularFactoryBean {

    @Produces
    public Shape<Circle> createCircleShape() {
        return new ShapeImpl<Circle>(Circle.class, (Shape<?>) null);
    }

    @Produces
    public Shape<Square> createSquareShape(final Shape<Circle> circleShape) {
        return new ShapeImpl<Square>(Square.class, circleShape);
    }

    @Produces
    public Shape<Triangle> createTriangleShape(final Shape<Star> starShape) {
        return new ShapeImpl<Triangle>(Triangle.class, starShape);
    }

    @Produces
    public Shape<Star> createStarShape(final Provider<Shape<Triangle>> triangleShapeProvider) {
        return new ShapeImpl<Star>(Star.class, triangleShapeProvider);
    }
}
