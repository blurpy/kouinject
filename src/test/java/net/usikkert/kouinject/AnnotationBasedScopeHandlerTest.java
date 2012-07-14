
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

package net.usikkert.kouinject;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import net.usikkert.kouinject.testbeans.notscanned.notloaded.NoScopeOnMethodBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.SingletonScopeOnMethodBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyScopesBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyScopesOnMethodBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.UnknownScopeBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.UnknownScopeOnMethodBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedScopeHandler}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedScopeHandlerTest {

    private AnnotationBasedScopeHandler scopeHandler;

    @Before
    public void createScopeHandler() {
        scopeHandler = new AnnotationBasedScopeHandler();
    }

    @Test
    public void isSingletonShouldBeFalseOnBeanWithoutSingletonAnnotation() {
        assertFalse(scopeHandler.isSingleton(CarBean.class));
    }

    @Test
    public void isSingletonShouldBeTrueOnBeanWithSingletonAnnotation() {
        assertTrue(scopeHandler.isSingleton(SingletonBean.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isSingletonShouldThrowExceptionIfUnknownScopeOnBean() {
        assertFalse(scopeHandler.isSingleton(UnknownScopeBean.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isSingletonShouldThrowExceptionIfMoreThanOneScopeOnBean() {
        assertFalse(scopeHandler.isSingleton(TooManyScopesBean.class));
    }

    @Test
    public void isSingletonShouldBeFalseOnMethodWithoutSingletonAnnotation() throws NoSuchMethodException {
        assertFalse(scopeHandler.isSingleton(getMethod(NoScopeOnMethodBean.class, "methodWithNoScope")));
    }

    @Test
    public void isSingletonShouldBeTrueOnMethodWithSingletonAnnotation() throws NoSuchMethodException {
        assertTrue(scopeHandler.isSingleton(getMethod(SingletonScopeOnMethodBean.class, "methodWithSingletonScope")));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isSingletonShouldThrowExceptionIfUnknownScopeOnMethod() throws NoSuchMethodException {
        assertFalse(scopeHandler.isSingleton(getMethod(UnknownScopeOnMethodBean.class, "methodWithUnknownScope")));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isSingletonShouldThrowExceptionIfMoreThanOneScopeOnMethod() throws NoSuchMethodException {
        assertFalse(scopeHandler.isSingleton(getMethod(TooManyScopesOnMethodBean.class, "methodWithTooManyScopes")));
    }

    private Method getMethod(final Class<?> aClass, final String name) throws NoSuchMethodException {
        return aClass.getDeclaredMethod(name);
    }
}
