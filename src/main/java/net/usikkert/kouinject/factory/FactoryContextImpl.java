
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

package net.usikkert.kouinject.factory;

/**
 * A basic implementation of {@link FactoryContext}.
 *
 * @author Christian Ihle
 */
public class FactoryContextImpl implements FactoryContext {

    private final String qualifier;

    /**
     * Creates a new factory context with the specified qualifier from the injection point.
     *
     * @param qualifier The qualifier from the injection point.
     */
    public FactoryContextImpl(final String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQualifier() {
        return qualifier;
    }
}
