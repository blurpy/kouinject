
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

package net.usikkert.kouinject.testbeans.scanned.generics.circular;

import javax.inject.Provider;

/**
 * An implementation of the generic Shape interface.
 *
 * @author Christian Ihle
 */
public class ShapeImpl<T> implements Shape<T> {

    private final Class<T> shapeClass;
    private Shape<?> containedShape;
    private Provider<? extends Shape<?>> containedShapeProvider;

    public ShapeImpl(final Class<T> shapeClass, final Shape<?> containedShape) {
        this.shapeClass = shapeClass;
        this.containedShape = containedShape;
    }

    public ShapeImpl(final Class<T> shapeClass, final Provider<? extends Shape<?>> containedShapeProvider) {
        this.shapeClass = shapeClass;
        this.containedShapeProvider = containedShapeProvider;
    }

    @Override
    public Class<T> getShapeClass() {
        return shapeClass;
    }

    @Override
    public Shape<?> getContainedShape() {
        if (containedShapeProvider != null) {
            return containedShapeProvider.get();
        }

        else {
            return containedShape;
        }
    }
}
