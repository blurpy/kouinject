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

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.testbeans.notscanned.factory.FactoryBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link BeanHelper}.
 *
 * @author Christian Ihle
 */
public class BeanHelperTest {

    private BeanHelper beanHelper;

    @Before
    public void createBeanHelper() {
        beanHelper = new BeanHelper();
    }

    @Test
    public void findFactoryReturnTypeShouldFindTheCorrectClass() throws NoSuchMethodException {
        final BeanKey returnType = beanHelper.findFactoryReturnType(getMethod("helloBeanFactoryMethod"));

        assertNotNull(returnType);
        assertEquals(HelloBean.class, returnType.getBeanClass());
        assertNull(returnType.getQualifier());
    }

    @Test
    public void findFactoryReturnTypeShouldFindTheCorrectQualifier() throws NoSuchMethodException {
        final BeanKey returnType = beanHelper.findFactoryReturnType(getMethod("scopedAndQualifiedFactoryMethod"));

        assertNotNull(returnType);
        assertEquals(JavaBean.class, returnType.getBeanClass());
        assertEquals("Blue", returnType.getQualifier());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void findFactoryReturnTypeShouldFailOnVoid() throws NoSuchMethodException {
        beanHelper.findFactoryReturnType(getMethod("voidFactoryMethod"));
    }

    private Method getMethod(final String methodName) throws NoSuchMethodException {
        return FactoryBean.class.getDeclaredMethod(methodName);
    }
}
