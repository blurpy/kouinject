
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

package net.usikkert.kouinject.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.usikkert.kouinject.AnnotationBasedQualifierHandler;
import net.usikkert.kouinject.beandata.BeanKey;

import org.apache.commons.lang.Validate;

/**
 * Helper methods for extracting meta-data from beans, using reflection.
 *
 * @author Christian Ihle
 */
public class BeanHelper {

    private final AnnotationBasedQualifierHandler qualifierHandler = new AnnotationBasedQualifierHandler();

    /**
     * Finds the return type of the factory method. The return type includes the qualifier on the method.
     *
     * @param factoryMethod The factory method to find the return type of.
     * @return The return type, as a {@link BeanKey}.
     */
    public BeanKey findFactoryReturnType(final Method factoryMethod) {
        Validate.notNull(factoryMethod, "Factory method can not be null");

        final Class<?> returnType = factoryMethod.getReturnType();

        if (returnType.equals(Void.TYPE)) {
            throw new UnsupportedOperationException("Can't return void from a factory method: " + factoryMethod);
        }

        final Annotation[] annotations = factoryMethod.getAnnotations();
        final String qualifier = qualifierHandler.getQualifier(factoryMethod, annotations);

        return new BeanKey(returnType, qualifier);
    }
}
