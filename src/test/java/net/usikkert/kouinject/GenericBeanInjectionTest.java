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

package net.usikkert.kouinject;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.OneStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.TwoStuffBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of beans with generics.
 *
 * @author Christian Ihle
 */
public class GenericBeanInjectionTest {

    private Injector injector;

    @Before
    public void setupBeanLoader() {
        injector = new DefaultInjector("net.usikkert.kouinject.testbeans.scanned");
    }

    @Test
    public void checkListOfOneStuffBean() {
        final List<OneStuffBean> oneStuffBeans = injector.getBean(new TypeLiteral<List<OneStuffBean>>() {});

        assertNotNull(oneStuffBeans);
        assertEquals(1, oneStuffBeans.size());
        assertNotNull(oneStuffBeans.get(0));
    }

    @Test
    public void checkListOfSetOfOneStuffBean() {
        final List<Set<OneStuffBean>> oneStuffBeansList = injector.getBean(new TypeLiteral<List<Set<OneStuffBean>>>() {});
        assertNotNull(oneStuffBeansList);
        assertEquals(1, oneStuffBeansList.size());

        final Set<OneStuffBean> oneStuffBeansSet = oneStuffBeansList.get(0);
        assertNotNull(oneStuffBeansSet);
        assertEquals(1, oneStuffBeansSet.size());

        final OneStuffBean oneStuffBean = oneStuffBeansSet.iterator().next();
        assertNotNull(oneStuffBean);
    }

    @Test
    public void checkListOfTwoStuffBean() {
        final List<TwoStuffBean> twoStuffBeans = injector.getBean(new TypeLiteral<List<TwoStuffBean>>() {});

        assertNotNull(twoStuffBeans);
        assertEquals(1, twoStuffBeans.size());
        assertNotNull(twoStuffBeans.get(0));
    }

    @Test
    public void checkListOfSetOfTwoStuffBean() {
        final List<Set<TwoStuffBean>> twoStuffBeansList = injector.getBean(new TypeLiteral<List<Set<TwoStuffBean>>>() {});
        assertNotNull(twoStuffBeansList);
        assertEquals(1, twoStuffBeansList.size());

        final Set<TwoStuffBean> twoStuffBeansSet = twoStuffBeansList.get(0);
        assertNotNull(twoStuffBeansSet);
        assertEquals(1, twoStuffBeansSet.size());

        final TwoStuffBean twoStuffBean = twoStuffBeansSet.iterator().next();
        assertNotNull(twoStuffBean);
    }

    @Test
    public void checkListOfStuffBean() {
        final ListOfStuffBean bean = injector.getBean(ListOfStuffBean.class);
        assertNotNull(bean);

        final List<OneStuffBean> oneStuffBeans = bean.getOneStuffBeans();
        assertNotNull(oneStuffBeans);
        assertEquals(1, oneStuffBeans.size());
        assertNotNull(oneStuffBeans.get(0));

        final List<TwoStuffBean> twoStuffBeans = bean.getTwoStuffBeans();
        assertNotNull(twoStuffBeans);
        assertEquals(1, twoStuffBeans.size());
        assertNotNull(twoStuffBeans.get(0));

        final List<Set<OneStuffBean>> oneStuffBeansInSet = bean.getOneStuffBeansInSet();
        assertNotNull(oneStuffBeansInSet);
        assertEquals(1, oneStuffBeansInSet.size());
        final Set<OneStuffBean> oneStuffBeansSet = oneStuffBeansInSet.get(0);
        assertNotNull(oneStuffBeansSet);
        assertEquals(1, oneStuffBeansSet.size());
        assertNotNull(oneStuffBeansSet.iterator().next());

        final List<Set<TwoStuffBean>> twoStuffBeansInSet = bean.getTwoStuffBeansInSet();
        assertNotNull(twoStuffBeansInSet);
        assertEquals(1, twoStuffBeansInSet.size());
        final Set<TwoStuffBean> twoStuffBeansSet = twoStuffBeansInSet.get(0);
        assertNotNull(twoStuffBeansSet);
        assertEquals(1, twoStuffBeansSet.size());
        assertNotNull(twoStuffBeansSet.iterator().next());
    }

    @Test
    public void checkListOfStuffFactoryBean() {
        final ListOfStuffFactoryBean bean = injector.getBean(ListOfStuffFactoryBean.class);
        assertNotNull(bean);

        assertNotNull(bean.createOneStuffBeans());
        assertNotNull(bean.createTwoStuffBeans());
        assertNotNull(bean.createOneStuffBeansInSet());
        assertNotNull(bean.createTwoStuffBeansInSet());
    }
}
