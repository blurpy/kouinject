
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

package net.usikkert.kouinject.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the class using this annotation is a component to be handled by the container. Also called a bean.
 *
 * <p>Used on a class like this:</p>
 *
 * <pre>
 *   &#064;Component
 *   public class NewsService {}
 * </pre>
 *
 * <p>It's also possible to specify custom component annotations. They function exactly like the
 * regular <code>@Component</code>, but allows you to annotate your beans with more meaningful names.</p>
 *
 * <p>Example of a custom component annotation:</p>
 *
 * <pre>
 *   &#064;Documented
 *   &#064;Retention(RetentionPolicy.RUNTIME)
 *   &#064;Target(ElementType.TYPE)
 *   &#064;Component
 *   public @interface Service {}
 * </pre>
 *
 * <p>It can be used like this:</p>
 *
 * <pre>
 *   &#064;Service
 *   public class NewsService {}
 * </pre>
 *
 * @author Christian Ihle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

}
