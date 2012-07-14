
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.testbeans.scanned.generics.Container;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Circle;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.CircularFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Pentagon;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Shape;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.ShapeSheetBean;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Square;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Star;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Triangle;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.CollectionFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.CollectionUsingBean;
import net.usikkert.kouinject.testbeans.scanned.generics.collection.Stone;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.BlogDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.Dao;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.DaoControllerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.DatabaseDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.ItemDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.MySqlDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.OracleDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.OrderDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.PersonDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Action;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Comedy;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Drama;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Genre;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Horror;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Movie;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.MovieCollectionBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.MovieFactoryBean;
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
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ArrayBottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ArrayContainer;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Bottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Box;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.CardboardBox;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ClosetBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ConcreteDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.DualVariableInterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.FantaBottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.GenericBox;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.LiquidArrayFactory;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.LiquidDualVariableBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pepsi;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.PepsiArrayBottle;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.PepsiArrayContainer;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pizza;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.PizzaBoxBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Shoe;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.ShoeBoxBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableOnePointTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.VariableTwo;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.RubberWheel;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.Wheel;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.WildcardFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.generics.wildcard.WildcardInjectionBean;
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
        assertEquals(6, beans.size());

        assertTrue(containsContainerBeanOf(ChildBean.class, beans));
        assertTrue(containsContainerBeanOf(MiddleBean.class, beans));
        assertTrue(containsContainerBeanOf(SuperBean.class, beans));
        assertTrue(containsContainerBeanOf(RubberWheel.class, beans));
        assertTrue(containsContainerBeanOf(Fanta.class, beans));
        assertTrue(containsContainerBeanOf(Pepsi[].class, beans));
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

        final AbstractMiddleThingListenerBean<MiddleThing> bean3 =
                injector.getBean(new TypeLiteral<AbstractMiddleThingListenerBean<MiddleThing>>() {});
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

        final Provider<Fanta> providerT = fantaBottle.getProviderT();
        assertNotNull(providerT);
        final Fanta providerFanta = providerT.get();
        assertNotNull(providerFanta);

        final Collection<Fanta> collectionT = fantaBottle.getCollectionT();
        assertNotNull(collectionT);
        assertEquals(1, collectionT.size());
        final Fanta collectionTFanta = collectionT.iterator().next();
        assertNotNull(collectionTFanta);

        final CollectionProvider<Fanta> collectionProviderT = fantaBottle.getCollectionProviderT();
        assertNotNull(collectionProviderT);
        final Collection<Fanta> collectionProviderTFantaCollection = collectionProviderT.get();
        assertNotNull(collectionProviderTFantaCollection);
        assertEquals(1, collectionProviderTFantaCollection.size());
        final Fanta collectionProviderTFantaCollectionFanta = collectionProviderTFantaCollection.iterator().next();
        assertNotNull(collectionProviderTFantaCollectionFanta);

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

    @Test
    public void checkClosetBean() {
        final ClosetBean bean = injector.getBean(ClosetBean.class);

        final Box<? extends Pizza> pizzaBox = bean.getPizzaBox();
        assertNotNull(pizzaBox);
        assertEquals(PizzaBoxBean.class, pizzaBox.getClass());

        final Box<? extends Shoe> shoeBox = bean.getShoeBox();
        assertNotNull(shoeBox);
        assertEquals(ShoeBoxBean.class, shoeBox.getClass());

        final Collection<GenericBox<Liquid[]>> boxesOfLiquid = bean.getBoxesOfLiquid();
        assertNotNull(boxesOfLiquid);
        assertEquals(2, boxesOfLiquid.size());

        boolean foundFanta = false;
        boolean foundPepsi = false;

        for (final GenericBox<Liquid[]> genericBox : boxesOfLiquid) {
            assertNotNull(genericBox);
            final Liquid[] content = genericBox.getContent();
            assertEquals(1, content.length);
            final Liquid liquid = content[0];

            if (liquid.getClass().equals(Fanta.class)) {
                foundFanta = true;
            }

            else if (liquid.getClass().equals(Pepsi.class)) {
                foundPepsi = true;
            }
        }

        assertTrue(foundFanta);
        assertTrue(foundPepsi);
    }

    @Test
    public void checkPizzaBoxBean() {
        final PizzaBoxBean pizzaBoxBean = injector.getBean(PizzaBoxBean.class);
        assertNotNull(pizzaBoxBean);

        final CardboardBox<Pizza> cardboardBox = injector.getBean(new TypeLiteral<CardboardBox<Pizza>>() {});
        assertNotNull(cardboardBox);
        assertEquals(PizzaBoxBean.class, cardboardBox.getClass());

        final Box<Pizza> box = injector.getBean(new TypeLiteral<Box<Pizza>>() {});
        assertNotNull(box);
        assertEquals(PizzaBoxBean.class, box.getClass());
    }

    @Test
    public void checkShoeBoxBean() {
        final ShoeBoxBean shoeBoxBean = injector.getBean(ShoeBoxBean.class);
        assertNotNull(shoeBoxBean);

        final CardboardBox<Shoe> cardboardBox = injector.getBean(new TypeLiteral<CardboardBox<Shoe>>() {});
        assertNotNull(cardboardBox);
        assertEquals(ShoeBoxBean.class, cardboardBox.getClass());

        final Box<Shoe> box = injector.getBean(new TypeLiteral<Box<Shoe>>() {});
        assertNotNull(box);
        assertEquals(ShoeBoxBean.class, box.getClass());
    }

    @Test
    public void checkWildcardInjectionBean() {
        final WildcardInjectionBean bean = injector.getBean(WildcardInjectionBean.class);

        final Provider<? extends Fanta> fantaProvider = bean.getFantaProvider();
        assertNotNull(fantaProvider);
        final Fanta fanta = fantaProvider.get();
        assertNotNull(fanta);

        final Collection<? extends Liquid> liquidCollection = bean.getLiquidCollection();
        assertNotNull(liquidCollection);
        assertEquals(2, liquidCollection.size());
        assertTrue(containsBean(liquidCollection, Fanta.class));
        assertTrue(containsBean(liquidCollection, Pepsi.class));

        final CollectionProvider<? extends Liquid> liquidCollectionProvider = bean.getLiquidCollectionProvider();
        assertNotNull(liquidCollectionProvider);
        final Collection<? extends Liquid> liquids = liquidCollectionProvider.get();
        assertNotNull(liquids);
        assertEquals(2, liquids.size());
        assertTrue(containsBean(liquids, Fanta.class));
        assertTrue(containsBean(liquids, Pepsi.class));
    }

    @Test
    public void checkDaoControllerBean() {
        final DaoControllerBean bean = injector.getBean(DaoControllerBean.class);

        final Dao<OracleDriver> blogDao = bean.getBlogDao();
        assertNotNull(blogDao);
        assertEquals(BlogDaoBean.class, blogDao.getClass());

        final Dao<MySqlDriver> itemDao = bean.getItemDao();
        assertNotNull(itemDao);
        assertEquals(ItemDaoBean.class, itemDao.getClass());

        final Dao<MySqlDriver> orderDao = bean.getOrderDao();
        assertNotNull(orderDao);
        assertEquals(OrderDaoBean.class, orderDao.getClass());

        final Dao<MySqlDriver> personDao = bean.getPersonDao();
        assertNotNull(personDao);
        assertEquals(PersonDaoBean.class, personDao.getClass());

        final Collection<Dao<MySqlDriver>> mySqlDaos = bean.getMySqlDaos();
        assertNotNull(mySqlDaos);
        assertEquals(3, mySqlDaos.size());
        assertTrue(containsBean(mySqlDaos, ItemDaoBean.class));
        assertTrue(containsBean(mySqlDaos, OrderDaoBean.class));
        assertTrue(containsBean(mySqlDaos, PersonDaoBean.class));

        final Collection<Dao<? extends DatabaseDriver>> allDaos = bean.getAllDaos();
        assertNotNull(allDaos);
        assertEquals(4, allDaos.size());
        assertTrue(containsBean(allDaos, ItemDaoBean.class));
        assertTrue(containsBean(allDaos, OrderDaoBean.class));
        assertTrue(containsBean(allDaos, PersonDaoBean.class));
        assertTrue(containsBean(allDaos, BlogDaoBean.class));
    }

    @Test
    public void checkBlogDaoBean() {
        final Dao<OracleDriver> blogDao = injector.getBean(new TypeLiteral<Dao<OracleDriver>>() {}, "blog");
        assertNotNull(blogDao);
        assertEquals(BlogDaoBean.class, blogDao.getClass());
    }

    @Test
    public void checkItemDaoBean() {
        final Dao<MySqlDriver> itemDao = injector.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "item");
        assertNotNull(itemDao);
        assertEquals(ItemDaoBean.class, itemDao.getClass());
    }

    @Test
    public void checkOrderDaoBean() {
        final Dao<MySqlDriver> orderDao = injector.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "order");
        assertNotNull(orderDao);
        assertEquals(OrderDaoBean.class, orderDao.getClass());
    }

    @Test
    public void checkPersonDaoBean() {
        final Dao<MySqlDriver> personDao = injector.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "person");
        assertNotNull(personDao);
        assertEquals(PersonDaoBean.class, personDao.getClass());
    }

    @Test
    public void checkMovieCollectionBean() {
        final MovieCollectionBean bean = injector.getBean(MovieCollectionBean.class);
        assertNotNull(bean);

        final Movie<Comedy> fockersMovie = bean.getFockersMovie();
        assertNotNull(fockersMovie);
        assertEquals("Meet the Fockers", fockersMovie.getTitle());

        final Movie<Horror> scaryMovie = bean.getScaryMovie();
        assertNotNull(scaryMovie);
        assertEquals("Scary Movie", scaryMovie.getTitle());

        final Movie<Horror> screamMovie = bean.getScreamMovie();
        assertNotNull(screamMovie);
        assertEquals("Scream", screamMovie.getTitle());

        final Movie<Action> dieHardMovie = bean.getDieHardMovie();
        assertNotNull(dieHardMovie);
        assertEquals("Action: DieHard", dieHardMovie.getTitle());

        final Movie<Action> roboCopMovie = bean.getRoboCopMovie();
        assertNotNull(roboCopMovie);
        assertEquals("Action: RoboCop", roboCopMovie.getTitle());

        final Movie<Drama> americanBeautyMovie = bean.getAmericanBeautyMovie();
        assertNotNull(americanBeautyMovie);
        assertEquals("Drama: AmericanBeauty", americanBeautyMovie.getTitle());

        final Collection<Movie<Horror>> horrorMovies = bean.getHorrorMovies();
        assertNotNull(horrorMovies);
        assertEquals(2, horrorMovies.size());
        assertTrue(containsMovie(horrorMovies, "Scary Movie"));
        assertTrue(containsMovie(horrorMovies, "Scream"));

        final Collection<Movie<? extends Genre>> allMovies = bean.getAllMovies();
        assertNotNull(allMovies);
        assertEquals(3, allMovies.size());
        assertTrue(containsMovie(allMovies, "Scary Movie"));
        assertTrue(containsMovie(allMovies, "Scream"));
        assertTrue(containsMovie(allMovies, "Meet the Fockers"));
    }

    @Test
    public void checkMovieFactoryBean() {
        final MovieFactoryBean bean = injector.getBean(MovieFactoryBean.class);
        assertNotNull(bean);

        assertNotNull(bean.createScream());
        assertNotNull(bean.createScaryMovie());
        assertNotNull(bean.createMeetTheFockers());
    }

    @Test
    public void checkFockersMovie() {
        final Movie<Comedy> fockersMovie = injector.getBean(new TypeLiteral<Movie<Comedy>>() {}, "MeetFockers");

        assertNotNull(fockersMovie);
        assertEquals("Meet the Fockers", fockersMovie.getTitle());
    }

    @Test
    public void checkScreamMovie() {
        final Movie<Horror> screamMovie = injector.getBean(new TypeLiteral<Movie<Horror>>() {}, "Scream");

        assertNotNull(screamMovie);
        assertEquals("Scream", screamMovie.getTitle());
    }

    @Test
    public void checkScaryMovie() {
        final Movie<Horror> scaryMovie = injector.getBean(new TypeLiteral<Movie<Horror>>() {}, "ScaryMovie");

        assertNotNull(scaryMovie);
        assertEquals("Scary Movie", scaryMovie.getTitle());
    }

    @Test
    public void checkShapeSheetBean() {
        final ShapeSheetBean bean = injector.getBean(ShapeSheetBean.class);

        // Circle has no contained shape - not circular
        final Shape<Circle> circleShape = bean.getCircleShape();
        assertNotNull(circleShape);
        assertEquals(Circle.class, circleShape.getShapeClass());
        assertNull(circleShape.getContainedShape());

        // Square has circle as contained shape - not circular,
        // but would give false alert without support for generics in circular detection
        final Shape<Square> squareShape = bean.getSquareShape();
        assertNotNull(squareShape);
        assertEquals(Square.class, squareShape.getShapeClass());
        assertNotNull(squareShape.getContainedShape());
        assertEquals(Circle.class, squareShape.getContainedShape().getShapeClass());

        // Star has triangle as contained shape - circular, solved using a provider on the triangle injection
        final Shape<Star> starShape = bean.getStarShape();
        assertNotNull(starShape);
        assertEquals(Star.class, starShape.getShapeClass());
        assertNotNull(starShape.getContainedShape());
        assertEquals(Triangle.class, starShape.getContainedShape().getShapeClass());

        // Triangle has star as contained shape - circular
        final Shape<Triangle> triangleShape = bean.getTriangleShape();
        assertNotNull(triangleShape);
        assertEquals(Triangle.class, triangleShape.getShapeClass());
        assertNotNull(triangleShape.getContainedShape());
        assertEquals(Star.class, triangleShape.getContainedShape().getShapeClass());

        // First pentagon has second pentagon as contained shape - circular, with different qualifiers
        final Shape<Pentagon> firstPentagonShape = bean.getFirstPentagonShape();
        assertNotNull(firstPentagonShape);
        assertEquals(Pentagon.class, firstPentagonShape.getShapeClass());
        assertNotNull(firstPentagonShape.getContainedShape());
        assertEquals(Pentagon.class, firstPentagonShape.getContainedShape().getShapeClass());

        // Second pentagon has first pentagon as contained shape - circular, with different qualifiers,
        // solved using a provider on the first pentagon
        final Shape<Pentagon> secondPentagonShape = bean.getSecondPentagonShape();
        assertNotNull(secondPentagonShape);
        assertEquals(Pentagon.class, secondPentagonShape.getShapeClass());
        assertNotNull(secondPentagonShape.getContainedShape());
        assertEquals(Pentagon.class, secondPentagonShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkCircularFactoryBean() {
        final CircularFactoryBean bean = injector.getBean(CircularFactoryBean.class);
        assertNotNull(bean);

        assertNotNull(bean.createCircleShape());
        assertNotNull(bean.createSquareShape(null));
        assertNotNull(bean.createStarShape(null));
        assertNotNull(bean.createTriangleShape(null));
        assertNotNull(bean.createFirstPentagonShape(null));
        assertNotNull(bean.createSecondPentagonShape(null));
    }

    @Test
    public void checkCircleShape() {
        final Shape<Circle> circleShape = injector.getBean(new TypeLiteral<Shape<Circle>>() {});

        assertNotNull(circleShape);
        assertEquals(Circle.class, circleShape.getShapeClass());
        assertNull(circleShape.getContainedShape());
    }

    @Test
    public void checkSquareShape() {
        final Shape<Square> squareShape = injector.getBean(new TypeLiteral<Shape<Square>>() {});

        assertNotNull(squareShape);
        assertEquals(Square.class, squareShape.getShapeClass());
        assertNotNull(squareShape.getContainedShape());
        assertEquals(Circle.class, squareShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkStarShape() {
        final Shape<Star> starShape = injector.getBean(new TypeLiteral<Shape<Star>>() {});

        assertNotNull(starShape);
        assertEquals(Star.class, starShape.getShapeClass());
        assertNotNull(starShape.getContainedShape());
        assertEquals(Triangle.class, starShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkTriangleShape() {
        final Shape<Triangle> triangleShape = injector.getBean(new TypeLiteral<Shape<Triangle>>() {});

        assertNotNull(triangleShape);
        assertEquals(Triangle.class, triangleShape.getShapeClass());
        assertNotNull(triangleShape.getContainedShape());
        assertEquals(Star.class, triangleShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkFirstPentagonShape() {
        final Shape<Pentagon> firstPentagonShape = injector.getBean(new TypeLiteral<Shape<Pentagon>>() {}, "FirstPentagon");

        assertNotNull(firstPentagonShape);
        assertEquals(Pentagon.class, firstPentagonShape.getShapeClass());
        assertNotNull(firstPentagonShape.getContainedShape());
        assertEquals(Pentagon.class, firstPentagonShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkSecondPentagonShape() {
        final Shape<Pentagon> secondPentagonShape = injector.getBean(new TypeLiteral<Shape<Pentagon>>() {}, "SecondPentagon");

        assertNotNull(secondPentagonShape);
        assertEquals(Pentagon.class, secondPentagonShape.getShapeClass());
        assertNotNull(secondPentagonShape.getContainedShape());
        assertEquals(Pentagon.class, secondPentagonShape.getContainedShape().getShapeClass());
    }

    @Test
    public void checkPepsiArrayBottle() {
        final PepsiArrayBottle bean = injector.getBean(PepsiArrayBottle.class);
        assertNotNull(bean);

        final Pepsi[] arrayT = bean.getArrayT();
        assertNotNull(arrayT);
        assertEquals(1, arrayT.length);
        assertNotNull(arrayT[0]);

        final Provider<Pepsi[]> providerArrayT = bean.getProviderArrayT();
        assertNotNull(providerArrayT);

        final Pepsi[] pepsiArray = providerArrayT.get();
        assertNotNull(pepsiArray);
        assertEquals(1, pepsiArray.length);
        assertNotNull(pepsiArray[0]);

        assertNotNull(bean.createContainerOfArrayT());

        final ArrayBottle<Pepsi> pepsiArrayBottle = injector.getBean(new TypeLiteral<ArrayBottle<Pepsi>>() {});
        assertNotNull(pepsiArrayBottle);
        assertEquals(PepsiArrayBottle.class, pepsiArrayBottle.getClass());
    }

    @Test
    public void checkPepsiArrayContainer() {
        final PepsiArrayContainer bean = injector.getBean(PepsiArrayContainer.class);

        final Container<Pepsi[]> pepsiArrayContainer = bean.getPepsiArrayContainer();
        final Pepsi[] containedInPepsiArrayContainer = pepsiArrayContainer.getContained();
        assertEquals(1, containedInPepsiArrayContainer.length);
        assertNotNull(containedInPepsiArrayContainer[0]);

        final Pepsi[] pepsiArray = bean.getPepsiArray();
        assertEquals(1, pepsiArray.length);
        assertNotNull(pepsiArray[0]);

        final Container<Pepsi[]> arrayContainer = bean.getArrayContainer();
        final Pepsi[] containedInArrayContainer = arrayContainer.getContained();
        assertEquals(1, containedInArrayContainer.length);
        assertNotNull(containedInArrayContainer[0]);

        final Pepsi[] array = bean.getArray();
        assertEquals(1, array.length);
        assertNotNull(array[0]);

        final ArrayContainer<Pepsi> beanWithPepsi = injector.getBean(new TypeLiteral<ArrayContainer<Pepsi>>() {});
        assertNotNull(beanWithPepsi);
        assertEquals(PepsiArrayContainer.class, beanWithPepsi.getClass());
    }

    @Test
    public void checkContainerOfPepsiArray() {
        final Container<Pepsi[]> bean = injector.getBean(new TypeLiteral<Container<Pepsi[]>>() {});
        assertNotNull(bean);

        final Pepsi[] contained = bean.getContained();
        assertNotNull(contained);
        assertEquals(1, contained.length);
        assertNotNull(contained[0]);
    }

    @Test
    public void checkPepsiArray() {
        final Pepsi[] bean = injector.getBean(Pepsi[].class);

        assertNotNull(bean);
        assertEquals(1, bean.length);

        final Pepsi pepsi = bean[0];
        assertNotNull(pepsi);
    }

    @Test
    public void checkFantaArray() {
        final Fanta[] bean = injector.getBean(Fanta[].class);

        assertNotNull(bean);
        assertEquals(1, bean.length);

        final Fanta fanta = bean[0];
        assertNotNull(fanta);
    }

    @Test
    public void checkLiquidArrayFactory() {
        final LiquidArrayFactory bean = injector.getBean(LiquidArrayFactory.class);
        assertNotNull(bean);

        assertNotNull(bean.createPepsiArray(null));
        assertNotNull(bean.createFantaArray(null));
        assertNotNull(bean.createBoxOfFantaArray(null));
        assertNotNull(bean.createBoxOfPepsiArray(null));
    }

    @Test
    public void checkBoxOfFantaArray() {
        final GenericBox<Liquid[]> bean = injector.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "FantaArray");
        assertNotNull(bean);

        final Liquid[] content = bean.getContent();
        assertNotNull(content);
        assertEquals(1, content.length);

        final Liquid liquid = content[0];
        assertNotNull(liquid);
        assertEquals(Fanta.class, liquid.getClass());
    }

    @Test
    public void checkBoxOfPepsiArray() {
        final GenericBox<Liquid[]> bean = injector.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "PepsiArray");
        assertNotNull(bean);

        final Liquid[] content = bean.getContent();
        assertNotNull(content);
        assertEquals(1, content.length);

        final Liquid liquid = content[0];
        assertNotNull(liquid);
        assertEquals(Pepsi.class, liquid.getClass());
    }

    private boolean containsMovie(final Collection<? extends Movie<?>> movies, final String movieTitle) {
        for (final Movie<?> movie : movies) {
            if (movie.getTitle().equals(movieTitle)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsBean(final Collection<?> beans, final Class<?> beanClass) {
        for (final Object bean : beans) {
            if (bean.getClass().equals(beanClass)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsContainerBeanOf(final Class<?> expectedClass, final Collection<? extends Container<?>> containers) {
        for (final Container<?> container : containers) {
            if (container.getContained().getClass().equals(expectedClass)) {
                return true;
            }
        }

        return false;
    }
}
