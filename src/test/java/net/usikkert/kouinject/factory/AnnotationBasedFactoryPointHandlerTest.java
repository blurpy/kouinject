
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

import static org.junit.Assert.*;

import java.util.List;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedFactoryPointHandler}.
 *
 * @author Christian Ihle
 */
@Ignore("Not implemented")
public class AnnotationBasedFactoryPointHandlerTest {

    private AnnotationBasedFactoryPointHandler handler;

    @Before
    public void setUp() {
        handler = new AnnotationBasedFactoryPointHandler();
    }

    @Test
    public void getFactoryPointsShouldHandleNoFactoryMethods() {
        final BeanKey factory = new BeanKey(HelloBean.class);

        final List<FactoryPoint> factoryPoints = handler.getFactoryPoints(factory);

        assertNotNull(factoryPoints);
        assertTrue(factoryPoints.isEmpty());
    }

    @Test
    public void getFactoryPointsShouldFindSingleFactoryMethod() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindMultipleFactoryMethods() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldNotReturnMethodsWithoutProducesAnnotation() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindTheReturnTypeOfTheMethod() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldSetTheFactoryKey() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindTheScopeOfTheMethod() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindTheQualifierOfTheMethod() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindTheCorrectParameters() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindQualifiersOnTheParameters() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindProviderParameters() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindCollectionProviderParameters() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFindCollectionParameters() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldHandleOverriddenMethods() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldIgnoreStaticMethods() {
        fail("Not implemented");
    }

    @Test
    public void getFactoryPointsShouldFailIfInjectAnnotationPresent() {
        fail("Not implemented");
    }
}
