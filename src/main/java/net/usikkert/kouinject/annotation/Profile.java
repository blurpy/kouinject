
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for identifying profiles.
 *
 * <p>Profiles are used to include and exclude certain beans at runtime. Beans with no profile annotation
 * are always included. Beans with active profiles are also included. And beans with profiles that are not active
 * will be excluded.</p>
 *
 * <p>This annotation can not be used directly on beans. Instead you have to define custom annotations
 * for each profile. The class name of the custom annotation will be used as the name of the profile.</p>
 *
 * <p>Example of a custom profile annotation for the profile <code>production</code>:</p>
 *
 * <pre>
 *   &#064;Documented
 *   &#064;Retention(RetentionPolicy.RUNTIME)
 *   &#064;Target(ElementType.TYPE)
 *   &#064;Profile
 *   public @interface Production {}
 * </pre>
 *
 * <p>Used on a bean like this:</p>
 *
 * <pre>
 *   &#064;Component
 *   &#064;Production
 *   public class ProductionBean implements SameInterfaceAsDevelopmentBean {}
 * </pre>
 *
 * @author Christian Ihle
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Profile {

}
