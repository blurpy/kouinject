
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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

package net.usikkert.kouinject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Qualifier;

/**
 * Class for finding the qualifier of a class/field/parameter.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedQualifierHandler {

    private static final Class<Qualifier> QUALIFIER_ANNOTATION = Qualifier.class;

    /**
     * Gets the qualifier from the set of annotations.
     * The qualifier is any annotation inheriting from {@link Qualifier}.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>There can be at most 1 qualifier annotation.</li>
     *   <li>If no qualifier annotations are found, then <code>null</code> is returned.</li>
     *   <li>If {@link Named} qualifier is found, it must have a value. The value is returned.</li>
     *   <li>If any other {@link Qualifier} is found, the name of the annotation is returned.</li>
     * </ul>
     *
     * @param owner The class/field or parameter that owns the annotations.
     * @param annotations The annotations to check for a qualifier.
     * @return The qualifier found in the annotations, or <code>null</code>.
     */
    public String getQualifier(final Object owner, final Annotation[] annotations) {
        final List<String> matches = new ArrayList<String>();

        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(QUALIFIER_ANNOTATION)) {
                matches.add(getQualifier(owner, annotation));
            }
        }

        if (matches.size() == 0) {
            return null;
        }

        else if (matches.size() > 1) {
            throw new UnsupportedOperationException(
                    "Wrong number of qualifier annotations found on " + owner + " " + matches);
        }

        return matches.get(0);
    }

    private String getQualifier(final Object owner, final Annotation annotation) {
        if (annotation instanceof Named) {
            final Named named = (Named) annotation;
            final String value = named.value();

            if (valueIsEmpty(value)) {
                throw new UnsupportedOperationException(
                        "Named qualifier annotation used without a value on " + owner);
            }

            return value;
        }

        else {
            return annotation.annotationType().getSimpleName();
        }
    }

    private boolean valueIsEmpty(final String value) {
        return value == null || value.trim().length() == 0;
    }
}
