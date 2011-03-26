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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.OneStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.TwoStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.WildcardFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.MiddleBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.SuperBean;

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

    @Test
    public void checkWildcardFactoryBean() {
        final WildcardFactoryBean bean = injector.getBean(WildcardFactoryBean.class);
        assertNotNull(bean);

        assertNotNull(bean.createChildBeanContainer());
        assertNotNull(bean.createMiddleBeanContainer());
        assertNotNull(bean.createSuperBeanContainer());
    }

    @Test
    public void checkAllContainerBeans() {
        final Collection<Container<?>> beans = injector.getBeans(new TypeLiteral<Container<?>>() {});
        assertNotNull(beans);
        assertEquals(3, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansExtendingSuperBean() {
        final Collection<Container<? extends SuperBean>> beans = injector.getBeans(new TypeLiteral<Container<? extends SuperBean>>() {});
        assertNotNull(beans);
        assertEquals(3, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansExtendingMiddleBean() {
        final Collection<Container<? extends MiddleBean>> beans = injector.getBeans(new TypeLiteral<Container<? extends MiddleBean>>() {});
        assertNotNull(beans);
        assertEquals(2, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertFalse(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansExtendingChildBean() {
        final Collection<Container<? extends ChildBean>> beans = injector.getBeans(new TypeLiteral<Container<? extends ChildBean>>() {});
        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertFalse(containsContainerBeanOf(MiddleBean.class, beans));
        assertFalse(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansThatAreSuperClassOfSuperBean() {
        final Collection<Container<? super SuperBean>> beans = injector.getBeans(new TypeLiteral<Container<? super SuperBean>>() {});
        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertFalse(containsContainerBeanOf(ChildBean.class, beans));
        assertFalse(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansThatAreSuperClassOfMiddleBean() {
        final Collection<Container<? super MiddleBean>> beans = injector.getBeans(new TypeLiteral<Container<? super MiddleBean>>() {});
        assertNotNull(beans);
        assertEquals(2, beans.size());

        assertFalse(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
    }

    @Test
    public void checkContainerBeansThatAreSuperClassOfChildBean() {
        final Collection<Container<? super ChildBean>> beans = injector.getBeans(new TypeLiteral<Container<? super ChildBean>>() {});
        assertNotNull(beans);
        assertEquals(3, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
    }

    private boolean containsContainerBeanOf(final Class<?> expectedClass, final Collection beans) {
        final Collection<Container<?>> containers = beans;

        for (final Container<?> container : containers) {
            if (container.getContained().getClass().equals(expectedClass)) {
                return true;
            }
        }

        return false;
    }
}
