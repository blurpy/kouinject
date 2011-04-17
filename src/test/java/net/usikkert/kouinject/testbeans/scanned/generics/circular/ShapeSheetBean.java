
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

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Component;

/**
 * A bean that injects the various generic beans that have circular dependencies.
 *
 * @author Christian Ihle
 */
@Component
public class ShapeSheetBean {

    @Inject
    private Shape<Circle> circleShape;

    @Inject
    private Shape<Square> squareShape;

    @Inject
    private Shape<Triangle> triangleShape;

    @Inject
    private Shape<Star> starShape;

    @Inject @Named("FirstPentagon")
    private Shape<Pentagon> firstPentagonShape;

    @Inject @Named("SecondPentagon")
    private Shape<Pentagon> secondPentagonShape;

    public Shape<Circle> getCircleShape() {
        return circleShape;
    }

    public Shape<Square> getSquareShape() {
        return squareShape;
    }

    public Shape<Triangle> getTriangleShape() {
        return triangleShape;
    }

    public Shape<Star> getStarShape() {
        return starShape;
    }

    public Shape<Pentagon> getFirstPentagonShape() {
        return firstPentagonShape;
    }

    public Shape<Pentagon> getSecondPentagonShape() {
        return secondPentagonShape;
    }
}
