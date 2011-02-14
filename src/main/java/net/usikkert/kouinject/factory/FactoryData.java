
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

import java.util.ArrayList;
import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * Describes meta-data for a user-created factory bean class with members that can be used to create
 * instances of other beans.
 *
 * @author Christian Ihle
 */
public class FactoryData {

    /** The key describing the actual bean for this factory. */
    private final BeanKey factoryKey;

    /** A list of methods or fields in this factory class that can create beans. */
    private final List<FactoryPoint> factoryPoints;

    /**
     * Creates a new instance with meta-data for a factory.
     *
     * @param factoryKey The key describing the actual bean for this factory.
     * @param factoryPoints A list of methods or fields in this factory class that can create beans.
     */
    public FactoryData(final BeanKey factoryKey, final List<FactoryPoint> factoryPoints) {
        Validate.notNull(factoryKey, "Factory key can not be null");
        Validate.notNull(factoryPoints, "Factory points can not be null");

        this.factoryKey = factoryKey;
        this.factoryPoints = factoryPoints;
    }

    /**
     * Gets the key describing the actual bean for this factory.
     *
     * @return The key describing the actual bean for this factory.
     */
    public BeanKey getFactoryKey() {
        return factoryKey;
    }

    /**
     * Checks if this factory has a factory point that can create an instance of the needed bean.
     *
     * @param beanNeeded The bean that needs to be created.
     * @return If this factory has a factory point that can create the needed bean.
     * @throws IllegalStateException If too many matches are found.
     */
    public boolean hasFactoryPoint(final BeanKey beanNeeded) {
        final FactoryPoint factoryPoint = getFactoryPoint(beanNeeded, false);
        return factoryPoint != null;
    }

    /**
     * Gets the factory point that can create an instance of the needed bean.
     *
     * @param beanNeeded The bean that needs to be created.
     * @return The factory point that can create the needed bean.
     * @throws IllegalArgumentException If no matching factory point is found.
     * @throws IllegalStateException If too many matches are found.
     */
    public FactoryPoint getFactoryPoint(final BeanKey beanNeeded) {
        return getFactoryPoint(beanNeeded, true);
    }

    private FactoryPoint getFactoryPoint(final BeanKey beanNeeded, final boolean throwEx) {
        Validate.notNull(beanNeeded, "Can't find factory point for null");

        final List<FactoryPoint> matches = new ArrayList<FactoryPoint>();

        for (final FactoryPoint factoryPoint : factoryPoints) {
            final BeanKey returnType = factoryPoint.getReturnType();

            // TODO returnType.canCreate(beanNeeded) with special handling of @Any?
            if (beanNeeded.canInject(returnType)) {
                matches.add(factoryPoint);
            }
        }

        if (matches.isEmpty()) {
            if (throwEx) {
                throw new IllegalArgumentException("No matching factory point found for " + beanNeeded);
            }

            else {
                return null;
            }
        }

        else if (matches.size() > 1) {
            throw new IllegalStateException("Too many matching factory points found for " + beanNeeded + " " + matches);
        }

        return matches.get(0);
    }
}
