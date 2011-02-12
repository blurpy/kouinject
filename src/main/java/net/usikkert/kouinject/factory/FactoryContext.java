
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

/**
 * Contains details about the requested injection. This interface can be injected in
 * any factory bean method annotated with {@link net.usikkert.kouinject.annotation.Produces}.
 *
 * <p>Example:</p>
 *
 * <pre>
 *   &#064;Component
 *   public class SomeOtherBean {
 *     &#064;Inject &#064;Named("green")
 *     private SomeBean someBean;
 *   }
 *
 *   &#064;Component
 *   public class FactoryBean {
 *     &#064;Produces &#064;Any
 *     public SomeBean createSomeBean(FactoryContext factoryContext) {
 *       String qualifier = factoryContext.getQualifier(); // Returns "green"
 *       ... // Create instance using qualifier
 *       return someBean;
 *     }
 *   }
 * </pre>
 *
 * @author Christian Ihle
 */
public interface FactoryContext {

    /**
     * Returns the qualifier used on the field/parameter to be injected into by the factory method
     * this context is available to.
     *
     * @return The qualifier.
     */
    String getQualifier();
}
