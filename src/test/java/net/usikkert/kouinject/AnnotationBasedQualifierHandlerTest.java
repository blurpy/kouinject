
/***************************************************************************
 *   Copyright 2009-2010 by Christian Ihle                                 *
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

import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.inject.Named;

import net.usikkert.kouinject.testbeans.scanned.qualifier.Green;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Yellow;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedQualifierHandler}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedQualifierHandlerTest {

    @Green
    public final Object green = new Object();

    @Named("red")
    public final Object red = new Object();

    public final Object none = new Object();

    @Yellow
    @Named("pink")
    public final Object yellowAndPink = new Object();

    @Named
    public final Object anonymous = new Object();

    private AnnotationBasedQualifierHandler qualifierHandler;

    @Before
    public void createQualifierHandler() {
        qualifierHandler = new AnnotationBasedQualifierHandler();
    }

    @Test
    public void getQualifierShouldReturnNameOfQualifierAnnotation() throws Exception {
        final Field greenField = getClass().getField("green");
        final Annotation[] greenAnnotations = greenField.getAnnotations();

        final String qualifier = qualifierHandler.getQualifier(greenField, greenAnnotations);

        assertEquals("Green", qualifier);
    }

    @Test
    public void getQualifierShouldReturnValueOfNamedQualifier() throws Exception {
        final Field redField = getClass().getField("red");
        final Annotation[] redAnnotations = redField.getAnnotations();

        final String qualifier = qualifierHandler.getQualifier(redField, redAnnotations);

        assertEquals("red", qualifier);
    }

    @Test
    public void getQualifierShouldReturnNullWhenNoQualifier() throws Exception {
        final Field noneField = getClass().getField("none");
        final Annotation[] noneAnnotations = noneField.getAnnotations();

        final String qualifier = qualifierHandler.getQualifier(noneField, noneAnnotations);

        assertEquals(null, qualifier);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getQualifierShouldThrowExceptionWithTwoQualifiers() throws Exception {
        final Field yellowAndPinkField = getClass().getField("yellowAndPink");
        final Annotation[] yellowAndPinkAnnotations = yellowAndPinkField.getAnnotations();

        qualifierHandler.getQualifier(yellowAndPinkField, yellowAndPinkAnnotations);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getQualifierShouldThrowExceptionWithNamedQualifierWithoutValue() throws Exception {
        final Field anonymousField = getClass().getField("anonymous");
        final Annotation[] anonymousAnnotations = anonymousField.getAnnotations();

        qualifierHandler.getQualifier(anonymousField, anonymousAnnotations);
    }
}
