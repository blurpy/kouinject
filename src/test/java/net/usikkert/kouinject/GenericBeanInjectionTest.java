
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.CollectionFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.CollectionUsingBean;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.Stone;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.ListOfStuffFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.OneStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.TwoStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.AbstractMiddleThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.AbstractStartThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.FirstStartThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.MiddleThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.SecondStartThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StartThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThing;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.StopThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.ThingListenerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.thing.ThingManagerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.AbstractDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Bottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ConcreteDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.DualVariableInterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.FantaBottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.LiquidDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOnePointTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.RubberWheel;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.Wheel;
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
        assertNotNull(bean.createWheelContainer());
    }

    @Test
    public void checkAllContainerBeans() {
        final Collection<Container<?>> beans = injector.getBeans(new TypeLiteral<Container<?>>() {});
        assertNotNull(beans);
        assertEquals(5, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
        assertTrue(containsContainerBeanOf(RubberWheel.class, beans));
        assertTrue(containsContainerBeanOf(Fanta.class, beans));
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

    @Test
    public void checkCollectionFactoryBean() {
        final CollectionFactoryBean bean = injector.getBean(CollectionFactoryBean.class);
        assertNotNull(bean);

        final Collection<Stone> stoneCollection = bean.createStoneCollection();
        assertNotNull(stoneCollection);
        assertEquals(2, stoneCollection.size());
    }

    @Test
    public void checkCollectionUsingBean() {
        final CollectionUsingBean bean = injector.getBean(CollectionUsingBean.class);
        assertNotNull(bean);

        final Collection<Stone> stoneCollection = bean.getStoneCollection();
        assertNotNull(stoneCollection);
        assertEquals(2, stoneCollection.size());

        final Iterator<Stone> iterator = stoneCollection.iterator();
        assertEquals(Integer.valueOf(15), iterator.next().getWeight());
        assertEquals(Integer.valueOf(30), iterator.next().getWeight());
    }

    @Test
    public void checkStoneCollection() {
        final Collection<Stone> bean = injector.getBean(new TypeLiteral<Collection<Stone>>() {});
        assertNotNull(bean);

        assertNotNull(bean);
        assertEquals(2, bean.size());

        final Iterator<Stone> iterator = bean.iterator();
        assertEquals(Integer.valueOf(15), iterator.next().getWeight());
        assertEquals(Integer.valueOf(30), iterator.next().getWeight());
    }

    @Test
    public void checkThingManagerBean() {
        final ThingManagerBean bean = injector.getBean(ThingManagerBean.class);
        assertNotNull(bean);

        final Collection<ThingListenerBean<StartThing>> startThingListeners = bean.getStartThingListeners();
        assertNotNull(startThingListeners);
        assertEquals(2, startThingListeners.size());

        assertTrue(containsBean(startThingListeners, FirstStartThingListenerBean.class));
        assertTrue(containsBean(startThingListeners, SecondStartThingListenerBean.class));

        for (final ThingListenerBean<StartThing> startThingListener : startThingListeners) {
            final StartThing startThing = startThingListener.getThing();
            assertNotNull(startThing);
        }

        final ThingListenerBean<MiddleThing> middleThingListener = bean.getMiddleThingListener();
        assertNotNull(middleThingListener);
        assertEquals(MiddleThingListenerBean.class, middleThingListener.getClass());
        final MiddleThing middleThing = middleThingListener.getThing();
        assertNotNull(middleThing);

        final ThingListenerBean<StopThing> stopThingListener = bean.getStopThingListener();
        assertNotNull(stopThingListener);
        assertEquals(StopThingListenerBean.class, stopThingListener.getClass());
        final StopThing stopThing = stopThingListener.getThing();
        assertNotNull(stopThing);
    }

    @Test
    public void checkFirstStartThingListenerBean() {
        final FirstStartThingListenerBean bean = injector.getBean(FirstStartThingListenerBean.class);
        assertNotNull(bean);

        final StartThing startThing = bean.getThing();
        assertNotNull(startThing);
    }

    @Test
    public void checkSecondStartThingListenerBean() {
        final SecondStartThingListenerBean bean1 = injector.getBean(SecondStartThingListenerBean.class);
        assertNotNull(bean1);

        final StartThing startThing = bean1.getThing();
        assertNotNull(startThing);
    }

    @Test
    public void checkStartThingListenerBeans() {
        final Collection<AbstractStartThingListenerBean> beans1 = injector.getBeans(AbstractStartThingListenerBean.class);
        assertNotNull(beans1);
        assertEquals(2, beans1.size());
        assertTrue(containsBean(beans1, FirstStartThingListenerBean.class));
        assertTrue(containsBean(beans1, SecondStartThingListenerBean.class));

        final Collection<ThingListenerBean<StartThing>> beans2 = injector.getBeans(new TypeLiteral<ThingListenerBean<StartThing>>() {});
        assertNotNull(beans2);
        assertEquals(2, beans2.size());
        assertTrue(containsBean(beans2, FirstStartThingListenerBean.class));
        assertTrue(containsBean(beans2, SecondStartThingListenerBean.class));
    }

    @Test
    public void checkMiddleThingListenerBean() {
        final MiddleThingListenerBean bean = injector.getBean(MiddleThingListenerBean.class);
        assertNotNull(bean);

        final MiddleThing middleThing = bean.getThing();
        assertNotNull(middleThing);

        final ThingListenerBean<MiddleThing> bean2 = injector.getBean(new TypeLiteral<ThingListenerBean<MiddleThing>>() {});
        assertNotNull(bean2);
        assertEquals(MiddleThingListenerBean.class, bean2.getClass());

        final AbstractMiddleThingListenerBean<MiddleThing> bean3 = injector.getBean(new TypeLiteral<AbstractMiddleThingListenerBean<MiddleThing>>() {});
        assertNotNull(bean3);
        assertEquals(MiddleThingListenerBean.class, bean3.getClass());
    }

    @Test
    public void checkStopThingListenerBean() {
        final StopThingListenerBean bean1 = injector.getBean(StopThingListenerBean.class);
        assertNotNull(bean1);

        final StopThing stopThing = bean1.getThing();
        assertNotNull(stopThing);

        final ThingListenerBean<StopThing> bean2 = injector.getBean(new TypeLiteral<ThingListenerBean<StopThing>>() {});
        assertNotNull(bean2);
        assertEquals(StopThingListenerBean.class, bean2.getClass());
    }

    @Test
    public void checkConcreteDualVariableBean() {
        final ConcreteDualVariableBean bean1 = injector.getBean(ConcreteDualVariableBean.class);
        assertNotNull(bean1);

        final AbstractDualVariableBean<VariableOnePointTwo> bean2 =
                injector.getBean(new TypeLiteral<AbstractDualVariableBean<VariableOnePointTwo>>() {});
        assertNotNull(bean2);
        assertEquals(ConcreteDualVariableBean.class, bean2.getClass());

        final DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo> bean3 =
                injector.getBean(new TypeLiteral<DualVariableInterfaceBean<VariableOnePointTwo, VariableTwo>>() {});
        assertNotNull(bean3);
        assertEquals(ConcreteDualVariableBean.class, bean3.getClass());

        final VariableOnePointTwo first = bean3.getFirst();
        assertNotNull(first);
        bean3.doFirst(new VariableOnePointTwo());

        final VariableTwo second = bean3.getSecond();
        assertNotNull(second);
        bean3.doSecond(new VariableTwo());
    }

    @Test
    public void checkContainerWithWheel() {
        final Container<? extends Wheel> bean = injector.getBean(new TypeLiteral<Container<? extends Wheel>>() {});
        assertNotNull(bean);

        final Wheel contained = bean.getContained();
        assertNotNull(contained);
        assertEquals(RubberWheel.class, contained.getClass());
    }

    @Test
    public void checkFantaBottle() {
        final FantaBottle fantaBottle = injector.getBean(FantaBottle.class);
        assertNotNull(fantaBottle);

        final Fanta t = fantaBottle.getT();
        assertNotNull(t);

        final Fanta methodT = fantaBottle.getMethodT();
        assertNotNull(methodT);

        final Bottle<Fanta> bottleOfFanta = injector.getBean(new TypeLiteral<Bottle<Fanta>>() {});
        assertNotNull(bottleOfFanta);
        assertEquals(FantaBottle.class, bottleOfFanta.getClass());
    }

    @Test
    public void checkContainerOfFanta() {
        final Container<Fanta> bean = injector.getBean(new TypeLiteral<Container<Fanta>>() {});
        assertNotNull(bean);

        final Fanta contained = bean.getContained();
        assertNotNull(contained);
    }

    @Test
    public void checkLiquidDualVariableBean() {
        final LiquidDualVariableBean<Fanta> bean1 = injector.getBean(new TypeLiteral<LiquidDualVariableBean<Fanta>>() {});
        assertNotNull(bean1);

        final Fanta first = bean1.getFirst();
        assertNotNull(first);
        bean1.doFirst(new Fanta());

        final VariableTwo second = bean1.getSecond();
        assertNotNull(second);
        bean1.doSecond(new VariableTwo());

        final AbstractDualVariableBean<Fanta> bean2 = injector.getBean(new TypeLiteral<AbstractDualVariableBean<Fanta>>() {});
        assertNotNull(bean2);
        assertEquals(LiquidDualVariableBean.class, bean2.getClass());

         final DualVariableInterfaceBean<Fanta, VariableTwo> bean3 =
                 injector.getBean(new TypeLiteral<DualVariableInterfaceBean<Fanta, VariableTwo>>() {});
        assertNotNull(bean3);
        assertEquals(LiquidDualVariableBean.class, bean3.getClass());
    }

    private boolean containsBean(final Collection<?> beans, final Class<?> beanClass) {
        for (final Object bean : beans) {
            if (bean.getClass().equals(beanClass)) {
                return true;
            }
        }

        return false;
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
