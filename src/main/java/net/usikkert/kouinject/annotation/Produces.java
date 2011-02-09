
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

package net.usikkert.kouinject.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for defining a factory method in a bean.
 *
 * <p>The factory method will be called by the injector every time a bean matching the return class and qualifier
 * of the method needs to be instantiated. The method may also define scope of the created bean, and have input
 * parameters that will be injected. Any number of different factory methods may be defined in the same bean.</p>
 *
 * <pre>
 *   &#064;Component
 *   public class FactoryBean {
 *     &#064;Produces &#064;Singleton &#064;Named("yellow")
 *     public SomeBeanInterface createSomeBean(SomeOtherBean someOtherBean) {
 *       ... // Create instance using someOtherBean
 *       return someBean;
 *     }
 *   }
 * </pre>
 *
 * @author Christian Ihle
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Produces {

}
