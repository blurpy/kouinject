
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

package net.usikkert.kouinject.factory;

import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;

/**
 * Implementation of {@link FactoryPointHandler} that uses annotations and reflection to scan beans
 * for meta-data about factory points.
 *
 * <p>Factory points are detected based on the {@link net.usikkert.kouinject.annotation.Produces} annotation.
 * Scope, qualifier, and dependencies are also detected.</p>
 *
 * @author Christian Ihle
 */
public class AnnotationBasedFactoryPointHandler implements FactoryPointHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FactoryPoint> getFactoryPoints(final BeanKey factoryBean) {
        return null;
    }
}
