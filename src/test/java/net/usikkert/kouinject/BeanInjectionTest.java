
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
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Date;

import javax.inject.Provider;

import net.usikkert.kouinject.factory.FactoryContext;
import net.usikkert.kouinject.factory.FactoryContextImpl;
import net.usikkert.kouinject.testbeans.BeanCount;
import net.usikkert.kouinject.testbeans.scanned.AllInjectionTypesBean;
import net.usikkert.kouinject.testbeans.scanned.BlueCarBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.FinalBean;
import net.usikkert.kouinject.testbeans.scanned.FirstCircularDependencyBean;
import net.usikkert.kouinject.testbeans.scanned.GarageBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.LastBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.RainbowBean;
import net.usikkert.kouinject.testbeans.scanned.SecondCircularDependencyBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.StaticBean;
import net.usikkert.kouinject.testbeans.scanned.any.AnyBean;
import net.usikkert.kouinject.testbeans.scanned.array.ArrayClass;
import net.usikkert.kouinject.testbeans.scanned.array.ArrayFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.array.ArrayInterface;
import net.usikkert.kouinject.testbeans.scanned.array.ArrayUsingBean;
import net.usikkert.kouinject.testbeans.scanned.array.SingletonArray;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.AppleBean;
import net.usikkert.kouinject.testbeans.scanned.collection.BananaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.CheeseBean;
import net.usikkert.kouinject.testbeans.scanned.collection.FishBean;
import net.usikkert.kouinject.testbeans.scanned.collection.Food;
import net.usikkert.kouinject.testbeans.scanned.collection.HamburgerBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HotDogBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.HavingCollectionProviderBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.MaxAndMinCollectionProviderBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.QualifiedCollectionProviderBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.SingletonCollectionProviderBean;
import net.usikkert.kouinject.testbeans.scanned.component.CustomServiceBean;
import net.usikkert.kouinject.testbeans.scanned.date.DateBean;
import net.usikkert.kouinject.testbeans.scanned.date.DateFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.CdRecorderBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ChildFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ChildFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ConstructorFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.DifferentMembersFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.DifferentTypesFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.DifferentTypesFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryAndStandaloneBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryAndStandaloneBeanFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryCreatedBeanUsingBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryParameterFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FieldFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FirstMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.IntegerPropertyFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.IntegerPropertyInjectedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.LotsOfInjectionsFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MiscFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MiscQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MiscQualifierBeanFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MultipleFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.NestedFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.OneParameterFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.OrangeFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ParameterFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ParentFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ParentFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.PrivateFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.PrivateFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.RecorderBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SecondMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SetterFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBeanUsingBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SingletonFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SingletonFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SingletonFactoryCreatedBeanUsingBean;
import net.usikkert.kouinject.testbeans.scanned.factory.StringPropertyFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.StringPropertyInjectedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.TapeRecorderBean;
import net.usikkert.kouinject.testbeans.scanned.factory.TapeRecorderFactoryBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ThirdMultipleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.ThreeParametersFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder1.Folder1Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder2.Folder2Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder3.Folder3Bean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Fanta;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Pepsi;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.AnimalBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.OrganismBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.PetBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.pets.CatBean;
import net.usikkert.kouinject.testbeans.scanned.misc.PrivateBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.DarkYellowBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.OrangeBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;
import net.usikkert.kouinject.testbeans.scanned.scope.PinkSingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.PrototypeWithSingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonProviderBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonWithPrototypeBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FifthLayerBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FirstLayerBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FourthLayerBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.Layer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.SecondLayerBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.ThirdLayerBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of correct injection in all the beans.
 *
 * @author Christian Ihle
 */
public class BeanInjectionTest {

    private Injector injector;

    @Before
    public void setupBeanLoader() {
        injector = new DefaultInjector("net.usikkert.kouinject.testbeans.scanned");
    }

    @Test
    public void checkAbstractBean() {
        final AbstractBean abstractBean = injector.getBean(AbstractBean.class);
        assertNotNull(abstractBean);

        final AbstractBeanImpl abstractBeanImpl = injector.getBean(AbstractBeanImpl.class);
        assertNotNull(abstractBeanImpl);
    }

    @Test
    public void checkAllInjectionTypesBean() {
        final AllInjectionTypesBean allInjectionTypesBean = injector.getBean(AllInjectionTypesBean.class);

        // Standalone bean
        final HelloBean helloBean = allInjectionTypesBean.getHelloBean();
        assertNotNull(helloBean);

        final Collection<HelloBean> helloBeanCollection = allInjectionTypesBean.getHelloBeanCollection();
        assertNotNull(helloBeanCollection);
        assertEquals(1, helloBeanCollection.size());
        assertNotNull(helloBeanCollection.iterator().next());

        final Provider<HelloBean> helloBeanProvider = allInjectionTypesBean.getHelloBeanProvider();
        assertNotNull(helloBeanProvider);

        final HelloBean helloBeanFromProvider = helloBeanProvider.get();
        assertNotNull(helloBeanFromProvider);

        final CollectionProvider<HelloBean> helloBeanCollectionProvider = allInjectionTypesBean.getHelloBeanCollectionProvider();
        assertNotNull(helloBeanCollectionProvider);

        final Collection<HelloBean> helloBeanCollectionFromProvider = helloBeanCollectionProvider.get();
        assertNotNull(helloBeanCollectionFromProvider);
        assertEquals(1, helloBeanCollectionFromProvider.size());
        assertNotNull(helloBeanCollectionFromProvider.iterator().next());

        // Factory created bean
        final SimpleFactoryCreatedBean factoryBean = allInjectionTypesBean.getSimpleFactoryCreatedBean();
        assertNotNull(factoryBean);

        final Collection<SimpleFactoryCreatedBean> factoryBeanCollection = allInjectionTypesBean.getSimpleFactoryCreatedBeanCollection();
        assertNotNull(factoryBeanCollection);
        assertEquals(1, factoryBeanCollection.size());
        assertNotNull(factoryBeanCollection.iterator().next());

        final Provider<SimpleFactoryCreatedBean> factoryBeanProvider = allInjectionTypesBean.getSimpleFactoryCreatedBeanProvider();
        assertNotNull(factoryBeanProvider);

        final SimpleFactoryCreatedBean factoryBeanFromProvider = factoryBeanProvider.get();
        assertNotNull(factoryBeanFromProvider);

        final CollectionProvider<SimpleFactoryCreatedBean> factoryBeanCollectionProvider = allInjectionTypesBean.getSimpleFactoryCreatedBeanCollectionProvider();
        assertNotNull(factoryBeanCollectionProvider);

        final Collection<SimpleFactoryCreatedBean> factoryBeanCollectionFromProvider = factoryBeanCollectionProvider.get();
        assertNotNull(factoryBeanCollectionFromProvider);
        assertEquals(1, factoryBeanCollectionFromProvider.size());
        assertNotNull(factoryBeanCollectionFromProvider.iterator().next());
    }

    @Test
    public void checkAnimalBean() {
        final AnimalBean animalBean = injector.getBean(AnimalBean.class);
        assertNotNull(animalBean);
        assertTrue(animalBean.getClass().equals(AnimalBean.class));

        assertNotNull(animalBean.getHelloBeanInOrganismBean()); // private, not overridden
        assertNull(animalBean.getJavaBeanInOrganismBean()); // default, overridden
        assertNull(animalBean.getCoffeeBeanInOrganismBean()); // protected, overridden
        assertNull(animalBean.getCarBeanInOrganismBean()); // public, overridden

        assertNotNull(animalBean.getHelloBeanInAnimalBean());
        assertNotNull(animalBean.getJavaBeanInAnimalBean());
        assertNotNull(animalBean.getCoffeeBeanInAnimalBean());
        assertNotNull(animalBean.getCarBeanInAnimalBean());

        assertNotNull(animalBean.getSetterBeanInOrganismBean());
        assertNotNull(animalBean.getSetterBeanInAnimalBean());

        assertNotNull(animalBean.getFieldBean1InOrganismBean());
        assertNotNull(animalBean.getFieldBean1InAnimalBean());

        assertNotNull(animalBean.getFieldBean2InOrganismBean());
        assertNotNull(animalBean.getFieldBean2InAnimalBean());

        assertTrue(animalBean.isFieldsThenMethodsInjectedInAnimalBean());
    }

    @Test
    public void checkAnyBean() {
        final AnyBean anyBean = injector.getBean(AnyBean.class);

        assertNotNull(anyBean.getHelloBean());
        assertNotNull(anyBean.getRedBean());

        final Collection<ColorBean> colorBeans = anyBean.getColorBeans();
        assertNotNull(colorBeans);
        assertEquals(6, colorBeans.size());

        final Collection<HelloBean> helloBeanCollection = anyBean.getHelloBeanCollection();
        assertNotNull(helloBeanCollection);
        assertEquals(1, helloBeanCollection.size());

        final Collection<RedBean> theRedBeanCollection = anyBean.getTheRedBeanCollection();
        assertNotNull(theRedBeanCollection);
        assertEquals(1, theRedBeanCollection.size());
    }

    @Test
    public void checkAppleBean() {
        final AppleBean appleBean1 = injector.getBean(AppleBean.class);
        assertNotNull(appleBean1);
        assertNotNull(appleBean1.getSingletonBean());

        final AppleBean appleBean2 = injector.getBean(AppleBean.class, "roundfood");
        assertNotNull(appleBean2);

        final AppleBean appleBean3 = injector.getBean(AppleBean.class, "any");
        assertNotNull(appleBean3);

        final Food appleBean4 = injector.getBean(Food.class, "RoundFood");
        assertNotNull(appleBean4);
        assertTrue(appleBean4 instanceof AppleBean);
    }

    @Test
    public void checkArrayFactoryBean() {
        final ArrayFactoryBean bean = injector.getBean(ArrayFactoryBean.class);

        assertNotNull(bean);
        assertNotNull(bean.createFirstSimpleArray());
        assertNotNull(bean.createSecondSimpleArray());
        assertNotNull(bean.createSingletonArray());
    }

    @Test
    public void checkArrayUsingBean() {
        final ArrayUsingBean bean = injector.getBean(ArrayUsingBean.class);
        assertNotNull(bean);

        final ArrayClass[] firstSimpleArray = bean.getFirstSimpleArray();
        assertNotNull(firstSimpleArray);
        assertEquals(2, firstSimpleArray.length);
        assertEquals("First array 1", firstSimpleArray[0].getName());
        assertEquals("First array 2", firstSimpleArray[1].getName());

        final ArrayInterface[] secondSimpleArray = bean.getSecondSimpleArray();
        assertNotNull(secondSimpleArray);
        assertTrue(secondSimpleArray instanceof ArrayClass[]);
        assertEquals(2, secondSimpleArray.length);
        assertEquals("Second array 1", secondSimpleArray[0].getName());
        assertEquals("Second array 2", secondSimpleArray[1].getName());

        final SingletonArray[] singletonArray = bean.getSingletonArray();
        assertNotNull(singletonArray);
        assertEquals(2, singletonArray.length);
        assertEquals("Singleton array 1", singletonArray[0].getName());
        assertEquals("Singleton array 2", singletonArray[1].getName());

        final Provider<ArrayClass[]> secondSimpleArrayProvider = bean.getSecondSimpleArrayProvider();
        assertNotNull(secondSimpleArrayProvider);
        final ArrayClass[] arrayClasses = secondSimpleArrayProvider.get();
        assertNotNull(arrayClasses);
        assertEquals(2, arrayClasses.length);
        assertEquals("Second array 1", arrayClasses[0].getName());
        assertEquals("Second array 2", arrayClasses[1].getName());

        final Collection<ArrayClass[]> allArrayClasses = bean.getAllArrayClasses();
        assertNotNull(allArrayClasses);
        assertEquals(2, allArrayClasses.size());

        for (final ArrayClass[] classes : allArrayClasses) {
            assertNotNull(classes);
            assertEquals(2, classes.length);
        }
    }

    @Test
    public void checkFirstSimpleArray() {
        final ArrayClass[] bean = injector.getBean(ArrayClass[].class, "FirstSimpleArray");

        assertNotNull(bean);
        assertEquals(2, bean.length);
        assertEquals("First array 1", bean[0].getName());
        assertEquals("First array 2", bean[1].getName());
    }

    @Test
    public void checkSecondSimpleArray() {
        final ArrayClass[] bean = injector.getBean(ArrayClass[].class, "SecondSimpleArray");

        assertNotNull(bean);
        assertEquals(2, bean.length);
        assertEquals("Second array 1", bean[0].getName());
        assertEquals("Second array 2", bean[1].getName());
    }

    @Test
    public void checkSingletonArray() {
        final SingletonArray[] bean = injector.getBean(SingletonArray[].class);

        assertNotNull(bean);
        assertEquals(2, bean.length);
        assertEquals("Singleton array 1", bean[0].getName());
        assertEquals("Singleton array 2", bean[1].getName());
    }

    @Test
    public void checkBananaBean() {
        final BananaBean bananaBean = injector.getBean(BananaBean.class);
        assertNotNull(bananaBean);
    }

    @Test
    public void checkBlueBean() {
        final BlueBean blueBean1 = injector.getBean(BlueBean.class);
        assertNotNull(blueBean1);

        final BlueBean blueBean2 = injector.getBean(BlueBean.class, "Blue");
        assertNotNull(blueBean2);

        final ColorBean blueBean3 = injector.getBean(ColorBean.class, "Blue");
        assertTrue(blueBean3 instanceof BlueBean);
    }

    @Test
    public void checkBlueCarBean() {
        final BlueCarBean blueCarBean1 = injector.getBean(BlueCarBean.class);
        assertNotNull(blueCarBean1);

        final BlueCarBean blueCarBean2 = injector.getBean(BlueCarBean.class, "Blue");
        assertNotNull(blueCarBean2);

        final CarBean blueCarBean3 = injector.getBean(CarBean.class, "Blue");
        assertTrue(blueCarBean3 instanceof BlueCarBean);
    }

    @Test
    public void checkCatBean() {
        final CatBean catBean = injector.getBean(CatBean.class);
        assertNotNull(catBean);
        assertTrue(catBean.getClass().equals(CatBean.class));

        assertNotNull(catBean.getHelloBeanInOrganismBean()); // private, not overridden
        assertNull(catBean.getJavaBeanInOrganismBean()); // default, overridden
        assertNull(catBean.getCoffeeBeanInOrganismBean()); // protected, overridden
        assertNull(catBean.getCarBeanInOrganismBean()); // public, overridden

        assertNotNull(catBean.getHelloBeanInAnimalBean()); // private, not overridden
        assertNull(catBean.getJavaBeanInAnimalBean()); // default, overridden
        assertNull(catBean.getCoffeeBeanInAnimalBean()); // protected, overridden
        assertNull(catBean.getCarBeanInAnimalBean()); // public, overridden

        assertNotNull(catBean.getHelloBeanInPetBean()); // private, not overridden
        assertNotNull(catBean.getJavaBeanInPetBean()); // default in different package, not overridden
        assertNull(catBean.getCoffeeBeanInPetBean()); // protected, overridden
        assertNull(catBean.getCarBeanInPetBean()); // public, overridden

        assertNotNull(catBean.getHelloBeanInCatBean());
        assertNotNull(catBean.getJavaBeanInCatBean());
        assertNotNull(catBean.getCoffeeBeanInCatBean());
        assertNotNull(catBean.getCarBeanInCatBean());

        assertNotNull(catBean.getSetterBeanInOrganismBean());
        assertNotNull(catBean.getSetterBeanInAnimalBean());
        assertNotNull(catBean.getSetterBeanInPetBean());
        assertNotNull(catBean.getSetterBeanInCatBean());

        assertNotNull(catBean.getFieldBean1InOrganismBean());
        assertNotNull(catBean.getFieldBean1InAnimalBean());
        assertNotNull(catBean.getFieldBean1InPetBean());
        assertNotNull(catBean.getFieldBean1InCatBean());

        assertNotNull(catBean.getFieldBean2InOrganismBean());
        assertNotNull(catBean.getFieldBean2InAnimalBean());
        assertNotNull(catBean.getFieldBean2InPetBean());
        assertNotNull(catBean.getFieldBean2InCatBean());

        assertTrue(catBean.isFieldsThenMethodsInjectedInCatBean());
    }

    @Test
    public void checkCarBean() {
        final CarBean carBean = injector.getBean(CarBean.class);

        assertNotNull(carBean);
        assertEquals(CarBean.class, carBean.getClass());
    }

    @Test
    public void checkCdRecorderBean() {
        final CdRecorderBean cdRecorderBean1 = injector.getBean(CdRecorderBean.class);
        assertNotNull(cdRecorderBean1);

        final CdRecorderBean cdRecorderBean2 = injector.getBean(CdRecorderBean.class, "cd");
        assertNotNull(cdRecorderBean2);

        final RecorderBean cdRecorderBean3 = injector.getBean(RecorderBean.class, "cd");
        assertNotNull(cdRecorderBean3);
        assertEquals(CdRecorderBean.class, cdRecorderBean3.getClass());
    }

    @Test
    public void checkCheeseBean() {
        final CheeseBean cheeseBean = injector.getBean(CheeseBean.class);

        assertNotNull(cheeseBean);
        assertNotNull(cheeseBean.getRedBean());
        assertEquals(RedBean.class, cheeseBean.getRedBean().getClass());
    }

    @Test
    public void checkChildBean() {
        final ChildBean childBean = injector.getBean(ChildBean.class);

        assertNotNull(childBean.getFieldBean());
        assertNotNull(childBean.getHelloBean());
        assertNotNull(childBean.getCoffeeBean());
    }

    @Test
    public void checkChildFactoryBean() {
        final ChildFactoryBean childFactoryBean = injector.getBean(ChildFactoryBean.class);
        assertNotNull(childFactoryBean);

        final ParentFactoryBean parentFactoryBean = injector.getBean(ParentFactoryBean.class);
        assertNotNull(parentFactoryBean);
        assertEquals(ChildFactoryBean.class, parentFactoryBean.getClass());

        // Verifies expected method inheritance. The same result should also be returned through the bean loader
        final ChildFactoryCreatedBean childBean = childFactoryBean.createChildBean();
        assertTrue(childBean.isCreatedByChild());
        assertFalse(childBean.isCreatedByParent());

        final ParentFactoryCreatedBean parentBean = childFactoryBean.createParentBean();
        assertFalse(parentBean.isCreatedByChild());
        assertTrue(parentBean.isCreatedByParent());

        try {
            childFactoryBean.createOverriddenBean();
            fail("Should have failed");
        } catch (UnsupportedOperationException e) {
            // OK
        }
    }

    @Test
    public void checkChildFactoryCreatedBean() {
        final ChildFactoryCreatedBean bean1 = injector.getBean(ChildFactoryCreatedBean.class);
        assertNotNull(bean1);
        assertTrue(bean1.isCreatedByChild());
        assertFalse(bean1.isCreatedByParent());

        final ChildFactoryCreatedBean bean2 = injector.getBean(ChildFactoryCreatedBean.class, "child");
        assertNotNull(bean2);
        assertTrue(bean2.isCreatedByChild());
        assertFalse(bean2.isCreatedByParent());

        final ChildFactoryCreatedBean bean3 = injector.getBean(ChildFactoryCreatedBean.class, "any");
        assertNotNull(bean3);
        assertTrue(bean3.isCreatedByChild());
        assertFalse(bean3.isCreatedByParent());
    }

    @Test
    public void checkCoffeeBean() {
        final CoffeeBean coffeeBean = injector.getBean(CoffeeBean.class);

        assertNotNull(coffeeBean.getHelloBean());
        assertNotNull(coffeeBean.getJavaBean());
    }

    @Test
    public void checkConstructorBean() {
        final ConstructorBean constructorBean = injector.getBean(ConstructorBean.class);

        assertNotNull(constructorBean.getHelloBean());
        assertNotNull(constructorBean.getSetterBean());
    }

    @Test
    public void checkConstructorFactoryCreatedBean() {
        final ConstructorFactoryCreatedBean bean = injector.getBean(ConstructorFactoryCreatedBean.class);
        assertNotNull(bean);
    }

    @Test
    public void checkDate() {
        final Date date = injector.getBean(Date.class);
        assertNotNull(date);
    }

    @Test
    public void checkDateBean() {
        final DateBean dateBean = injector.getBean(DateBean.class);

        assertNotNull(dateBean);
        assertNotNull(dateBean.getDate());
    }

    @Test
    public void checkDateFactoryBean() {
        final DateFactoryBean dateFactoryBean = injector.getBean(DateFactoryBean.class);
        assertNotNull(dateFactoryBean);

        final Date date = dateFactoryBean.createDate();
        assertNotNull(date);
    }

    @Test
    public void checkDarkYellowBean() {
        final DarkYellowBean darkYellowBean1 = injector.getBean(DarkYellowBean.class);
        assertNotNull(darkYellowBean1);

        final DarkYellowBean darkYellowBean2 = injector.getBean(DarkYellowBean.class, "darkYellow");
        assertNotNull(darkYellowBean2);

        final YellowBean darkYellowBean3 = injector.getBean(YellowBean.class, "darkYellow");
        assertNotNull(darkYellowBean3);
        assertTrue(darkYellowBean3.getClass().equals(DarkYellowBean.class));

        final ColorBean darkYellowBean4 = injector.getBean(ColorBean.class, "darkYellow");
        assertNotNull(darkYellowBean4);
        assertTrue(darkYellowBean4.getClass().equals(DarkYellowBean.class));
    }

    @Test
    public void checkDifferentMembersFactoryBean() {
        final DifferentMembersFactoryBean bean = injector.getBean(DifferentMembersFactoryBean.class);
        assertNotNull(bean);

        assertNotNull(bean.createConstructorFactoryCreatedBean());
        assertNotNull(bean.createFieldFactoryCreatedBean());
        assertNotNull(bean.createSetterFactoryCreatedBean());
    }

    @Test
    public void checkDifferentTypesFactoryBean() {
        final DifferentTypesFactoryBean bean = injector.getBean(DifferentTypesFactoryBean.class);
        assertNotNull(bean);

        final DifferentTypesFactoryCreatedBean createdBean = bean.createBean(null, null, null, null);
        assertNotNull(createdBean);
        assertTrue(createdBean.isCreatedByFactory());
        assertNull(createdBean.getHelloBean());
    }

    @Test
    public void checkDifferentTypesFactoryCreatedBean() {
        final DifferentTypesFactoryCreatedBean bean = injector.getBean(DifferentTypesFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());

        assertNotNull(bean.getHelloBean());
        assertNotNull(bean.getCoffeeBeanProvider().get());
        assertEquals(1, bean.getJavaBeanCollection().size());
        assertEquals(1, bean.getFieldBeanCollectionProvider().get().size());
    }

    @Test
    public void checkEverythingBean() {
        final EverythingBean everythingBean = injector.getBean(EverythingBean.class);

        assertNotNull(everythingBean.getCoffeeBean());
        assertNotNull(everythingBean.getConstructorBean());
        assertNotNull(everythingBean.getFieldBean());
        assertNotNull(everythingBean.getHelloBean());
        assertNotNull(everythingBean.getJavaBean());
        assertNotNull(everythingBean.getSetterBean());
        assertNotNull(everythingBean.getInterfaceBeanImpl());
        assertNotNull(everythingBean.getAbstractBeanImpl());
    }

    @Test
    public void checkFactoryAndStandaloneBean() {
        final FactoryAndStandaloneBean standalone1 = injector.getBean(FactoryAndStandaloneBean.class, "standalone");
        assertNotNull(standalone1);
        assertFalse(standalone1.isCreatedByFactory());

        final FactoryAndStandaloneBean standalone2 = injector.getBean(FactoryAndStandaloneBean.class, "standalone");
        assertNotNull(standalone2);
        assertFalse(standalone2.isCreatedByFactory());

        // Standalone has prototype scope
        assertNotSame(standalone1, standalone2);

        final FactoryAndStandaloneBean factory1 = injector.getBean(FactoryAndStandaloneBean.class, "factory");
        assertNotNull(factory1);
        assertTrue(factory1.isCreatedByFactory());

        final FactoryAndStandaloneBean factory2 = injector.getBean(FactoryAndStandaloneBean.class, "factory");
        assertNotNull(factory2);
        assertTrue(factory2.isCreatedByFactory());

        // Factory has singleton scope
        assertSame(factory1, factory2);
    }

    @Test
    public void checkFactoryAndStandaloneBeanFactoryBean() {
        final FactoryAndStandaloneBeanFactoryBean bean = injector.getBean(FactoryAndStandaloneBeanFactoryBean.class);
        assertNotNull(bean);

        final FactoryAndStandaloneBean factoryAndStandaloneBean = bean.createBean();
        assertNotNull(factoryAndStandaloneBean);
        assertTrue(factoryAndStandaloneBean.isCreatedByFactory());
    }

    @Test
    public void checkFactoryCreatedBeanUsingBean() {
        final FactoryCreatedBeanUsingBean bean = injector.getBean(FactoryCreatedBeanUsingBean.class);
        assertNotNull(bean);

        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = bean.getSimpleFactoryCreatedBean();
        assertNotNull(simpleFactoryCreatedBean);
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());

        final FactoryParameterFactoryCreatedBean factoryParameterFactoryCreatedBean = bean.getFactoryParameterFactoryCreatedBean();
        assertNotNull(factoryParameterFactoryCreatedBean);
        assertTrue(factoryParameterFactoryCreatedBean.isCreatedByFactory());

        final OneParameterFactoryCreatedBean oneParameterFactoryCreatedBean = factoryParameterFactoryCreatedBean.getOneParameterFactoryCreatedBean();
        assertNotNull(oneParameterFactoryCreatedBean);
        assertTrue(oneParameterFactoryCreatedBean.isCreatedByFactory());
        assertNotNull(oneParameterFactoryCreatedBean.getHelloBean());
    }

    @Test
    public void checkFactoryParameterFactoryCreatedBean() {
        final FactoryParameterFactoryCreatedBean bean = injector.getBean(FactoryParameterFactoryCreatedBean.class);
        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());

        final OneParameterFactoryCreatedBean oneParameterFactoryCreatedBean = bean.getOneParameterFactoryCreatedBean();
        assertNotNull(oneParameterFactoryCreatedBean);
        assertTrue(oneParameterFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void checkFanta() {
        final Fanta fanta = injector.getBean(Fanta.class);
        assertNotNull(fanta);
    }

    @Test
    public void checkFieldBean() {
        final FieldBean fieldBean = injector.getBean(FieldBean.class);

        assertNotNull(fieldBean.getHelloBean());
        assertNotNull(fieldBean.getAbstractBean());
        assertNotNull(fieldBean.getInterfaceBean());
    }

    @Test
    public void checkFieldFactoryCreatedBean() {
        final FieldFactoryCreatedBean bean = injector.getBean(FieldFactoryCreatedBean.class);
        assertNotNull(bean);
    }

    @Test
    public void checkFifthLayer() {
        final Layer fifthLayer1 = injector.getBean(Layer.class, "fifth");
        final Layer fifthLayer2 = injector.getBean(FirstLayerBean.class, "fifth");
        final Layer fifthLayer3 = injector.getBean(SecondLayerBean.class, "fifth");
        final Layer fifthLayer4 = injector.getBean(ThirdLayerBean.class, "fifth");
        final Layer fifthLayer5 = injector.getBean(FourthLayerBean.class, "fifth");
        final Layer fifthLayer6 = injector.getBean(FifthLayerBean.class);
        final Layer fifthLayer7 = injector.getBean(FifthLayerBean.class, "fifth");
        final Layer fifthLayer8 = injector.getBean(FifthLayerBean.class, "any");

        assertSame(fifthLayer1, fifthLayer2);
        assertSame(fifthLayer2, fifthLayer3);
        assertSame(fifthLayer3, fifthLayer4);
        assertSame(fifthLayer4, fifthLayer5);
        assertSame(fifthLayer5, fifthLayer6);
        assertSame(fifthLayer6, fifthLayer7);
        assertSame(fifthLayer7, fifthLayer8);

        assertEquals(FifthLayerBean.class, fifthLayer1.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer2.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer3.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer4.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer5.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer6.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer7.getClass());
        assertEquals(FifthLayerBean.class, fifthLayer8.getClass());
    }

    @Test
    public void checkFinalBean() {
        final FinalBean finalBean = injector.getBean(FinalBean.class);

        assertNull(finalBean.getFieldBean());
    }

    @Test
    public void checkFirstCircularDependencyBean() {
        final FirstCircularDependencyBean firstCircularDependencyBean = injector.getBean(FirstCircularDependencyBean.class);

        assertNotNull(firstCircularDependencyBean.getSecondCircularDependencyBean());
    }

    @Test
    public void checkFirstLayer() {
        final Layer firstLayer1 = injector.getBean(Layer.class, "first");
        final Layer firstLayer2 = injector.getBean(FirstLayerBean.class);
        final Layer firstLayer3 = injector.getBean(FirstLayerBean.class, "first");

        assertSame(firstLayer1, firstLayer2);
        assertSame(firstLayer2, firstLayer3);

        assertEquals(FirstLayerBean.class, firstLayer1.getClass());
        assertEquals(FirstLayerBean.class, firstLayer2.getClass());
        assertEquals(FirstLayerBean.class, firstLayer3.getClass());
    }

    @Test
    public void checkFirstMultipleFactoryCreatedBean() {
        final FirstMultipleFactoryCreatedBean bean = injector.getBean(FirstMultipleFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
    }

    @Test
    public void checkFishBean() {
        final FishBean fishBean = injector.getBean(FishBean.class);
        assertNotNull(fishBean);
    }

    @Test
    public void checkFolder1Bean() {
        final Folder1Bean folder1Bean = injector.getBean(Folder1Bean.class);

        assertNotNull(folder1Bean);
    }

    @Test
    public void checkFolder2Bean() {
        final Folder2Bean folder2Bean = injector.getBean(Folder2Bean.class);

        assertNotNull(folder2Bean);
    }

    @Test
    public void checkFolder3Bean() {
        final Folder3Bean folder3Bean = injector.getBean(Folder3Bean.class);

        assertNotNull(folder3Bean);
        assertNotNull(folder3Bean.getFolder1Bean());
        assertNotNull(folder3Bean.getFolder2Bean());
    }

    @Test
    public void checkGarageBean() {
        final GarageBean garageBean = injector.getBean(GarageBean.class);

        final CarBean carBean = garageBean.getCarBean();
        assertNotNull(carBean);
        assertTrue(carBean.getClass().equals(CarBean.class));

        final Provider<CarBean> carBeanProvider = garageBean.getCarBeanProvider();
        assertNotNull(carBeanProvider);

        final CarBean carBeanFromProvider = carBeanProvider.get();
        assertNotNull(carBeanFromProvider);
        assertTrue(carBeanFromProvider.getClass().equals(CarBean.class));

        final CarBean blueCarBean = garageBean.getBlueCarBean();
        assertNotNull(blueCarBean);
        assertTrue(blueCarBean.getClass().equals(BlueCarBean.class));

        final Provider<CarBean> blueCarBeanProvider = garageBean.getBlueCarBeanProvider();
        assertNotNull(blueCarBeanProvider);

        final CarBean blueCarBeanFromProvider = blueCarBeanProvider.get();
        assertNotNull(blueCarBeanFromProvider);
        assertTrue(blueCarBeanFromProvider.getClass().equals(BlueCarBean.class));
    }

    @Test
    public void checkGreenBean() {
        final GreenBean greenBean1 = injector.getBean(GreenBean.class);
        assertNotNull(greenBean1);

        final GreenBean greenBean2 = injector.getBean(GreenBean.class, "Green");
        assertNotNull(greenBean2);

        final ColorBean greenBean3 = injector.getBean(ColorBean.class, "Green");
        assertTrue(greenBean3 instanceof GreenBean);
    }

    @Test
    public void checkHamburgerBean() {
        final HamburgerBean hamburgerBean1 = injector.getBean(HamburgerBean.class);
        assertNotNull(hamburgerBean1);

        final HamburgerBean hamburgerBean2 = injector.getBean(HamburgerBean.class, "fastfood");
        assertNotNull(hamburgerBean2);

        final HamburgerBean hamburgerBean3 = injector.getBean(HamburgerBean.class, "any");
        assertNotNull(hamburgerBean3);
    }

    @Test
    public void checkHavingCollectionProviderBean() {
        final HavingCollectionProviderBean havingCollectionProviderBean = injector.getBean(HavingCollectionProviderBean.class);
        assertNotNull(havingCollectionProviderBean);

        final QualifiedCollectionProviderBean qualifiedCollectionProviderBean = havingCollectionProviderBean.getQualifiedCollectionProviderBean();
        assertNotNull(qualifiedCollectionProviderBean);

        final CollectionProvider<AppleBean> appleBeanCollectionProvider = qualifiedCollectionProviderBean.getAppleBeanCollectionProvider();
        assertNotNull(appleBeanCollectionProvider);

        final Collection<AppleBean> appleBeans = appleBeanCollectionProvider.get();
        assertNotNull(appleBeans);
        assertEquals(1, appleBeans.size());
    }

    @Test
    public void checkHelloBean() {
        final HelloBean helloBean = injector.getBean(HelloBean.class);

        assertNotNull(helloBean);
    }

    @Test
    public void checkHotDogBean() {
        final HotDogBean hotDogBean1 = injector.getBean(HotDogBean.class);
        assertNotNull(hotDogBean1);

        final HotDogBean hotDogBean2 = injector.getBean(HotDogBean.class, "fastfood");
        assertNotNull(hotDogBean2);

        final HotDogBean hotDogBean3 = injector.getBean(HotDogBean.class, "any");
        assertNotNull(hotDogBean3);
    }

    @Test
    public void checkHungryBean() {
        final HungryBean hungryBean = injector.getBean(HungryBean.class);

        assertNotNull(hungryBean);

        final Collection<Food> foodBeansInField = hungryBean.getFoodBeansInField();
        assertNotNull(foodBeansInField);
        assertEquals(3, foodBeansInField.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInField));
        assertTrue(containsBean(FishBean.class, foodBeansInField));
        assertTrue(containsBean(BananaBean.class, foodBeansInField));

        final Collection<Food> foodBeansInConstructor = hungryBean.getFoodBeansInConstructor();
        assertNotNull(foodBeansInConstructor);
        assertEquals(3, foodBeansInConstructor.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInConstructor));
        assertTrue(containsBean(FishBean.class, foodBeansInConstructor));
        assertTrue(containsBean(BananaBean.class, foodBeansInConstructor));

        final Collection<Food> foodBeansInSetter = hungryBean.getFoodBeansInSetter();
        assertNotNull(foodBeansInSetter);
        assertEquals(3, foodBeansInSetter.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInSetter));
        assertTrue(containsBean(FishBean.class, foodBeansInSetter));
        assertTrue(containsBean(BananaBean.class, foodBeansInSetter));
    }

    @Test
    public void checkHungryQualifierBean() {
        final HungryQualifierBean hungryQualifierBean = injector.getBean(HungryQualifierBean.class);

        assertNotNull(hungryQualifierBean);

        final Collection<Food> fastFoodBeans = hungryQualifierBean.getFastFoodBeans();
        assertNotNull(fastFoodBeans);
        assertEquals(2, fastFoodBeans.size());
        assertTrue(containsBean(HotDogBean.class, fastFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, fastFoodBeans));

        final Collection<Food> allFoodBeans = hungryQualifierBean.getAllFoodBeans();
        assertNotNull(allFoodBeans);
        assertEquals(6, allFoodBeans.size());
        assertTrue(containsBean(CheeseBean.class, allFoodBeans));
        assertTrue(containsBean(FishBean.class, allFoodBeans));
        assertTrue(containsBean(BananaBean.class, allFoodBeans));
        assertTrue(containsBean(HotDogBean.class, allFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, allFoodBeans));
        assertTrue(containsBean(AppleBean.class, allFoodBeans));

        final Collection<Food> roundFoodBeans = hungryQualifierBean.getRoundFoodBeans();
        assertNotNull(roundFoodBeans);
        assertEquals(1, roundFoodBeans.size());
        assertTrue(containsBean(AppleBean.class, roundFoodBeans));
    }

    @Test
    public void checkIntegerPropertyFactoryBean() {
        final IntegerPropertyFactoryBean integerPropertyFactoryBean = injector.getBean(IntegerPropertyFactoryBean.class);
        assertNotNull(integerPropertyFactoryBean);

        final FactoryContext factoryContext = new FactoryContextImpl("some.integer");

        final Integer integerProperty = integerPropertyFactoryBean.createIntegerProperty(factoryContext);
        assertTrue(integerProperty.equals(123));

        // Prototype scope
        final IntegerPropertyFactoryBean integerPropertyFactoryBean2 = injector.getBean(IntegerPropertyFactoryBean.class);
        assertNotSame(integerPropertyFactoryBean, integerPropertyFactoryBean2);
    }

    @Test
    public void checkIntegerPropertyInjectedBean() {
        final IntegerPropertyInjectedBean integerPropertyInjectedBean = injector.getBean(IntegerPropertyInjectedBean.class);
        assertNotNull(integerPropertyInjectedBean);

        assertTrue(integerPropertyInjectedBean.getPrice().equals(50));
        assertTrue(integerPropertyInjectedBean.getSomeInteger().equals(123));
        assertTrue(integerPropertyInjectedBean.getSomeOtherInteger().equals(987654321));
    }

    @Test
    public void checkInterfaceBean() {
        final InterfaceBean interfaceBean = injector.getBean(InterfaceBean.class);
        assertNotNull(interfaceBean);

        final InterfaceBeanImpl interfaceBeanImpl = injector.getBean(InterfaceBeanImpl.class);
        assertNotNull(interfaceBeanImpl);
    }

    @Test
    public void checkJavaBean() {
        final JavaBean javaBean = injector.getBean(JavaBean.class);

        assertNotNull(javaBean.getFieldBean());
        assertNotNull(javaBean.getHelloBean());
    }

    @Test
    public void checkLastBean() {
        final LastBean lastBean = injector.getBean(LastBean.class);

        assertNotNull(lastBean.getEverythingBean());
    }

    @Test
    public void checkLong50() {
        final Long bean = injector.getBean(Long.class, "50");

        assertNotNull(bean);
        assertEquals(Long.valueOf(50L), bean);
    }

    @Test
    public void checkLotsOfInjectionsFactoryBean() {
        final LotsOfInjectionsFactoryBean bean = injector.getBean(LotsOfInjectionsFactoryBean.class);
        assertNotNull(bean);

        final Long long50 = bean.create50();
        assertEquals(Long.valueOf(50L), long50);

        assertNotNull(bean.getConstructorBean());
        assertNotNull(bean.getFieldBean());
        assertNotNull(bean.getSetterBean());

        assertNotNull(bean.getConstructorFactoryCreatedBean());
        assertNotNull(bean.getFieldFactoryCreatedBean());
        assertNotNull(bean.getSetterFactoryCreatedBean());
    }

    @Test
    public void checkMaxAndMinCollectionProviderBean() {
        final MaxAndMinCollectionProviderBean maxAndMinCollectionProviderBean = injector.getBean(MaxAndMinCollectionProviderBean.class);

        // HelloBean
        final CollectionProvider<HelloBean> helloBeanCollectionProvider = maxAndMinCollectionProviderBean.getHelloBeanCollectionProvider();
        assertNotNull(helloBeanCollectionProvider);

        final Collection<HelloBean> helloBeans = helloBeanCollectionProvider.get();
        assertNotNull(helloBeans);
        assertEquals(1, helloBeans.size());
        assertNotNull(helloBeans.iterator().next());

        // All beans
        final CollectionProvider<Object> allBeansCollectionProvider = maxAndMinCollectionProviderBean.getAllBeansCollectionProvider();
        assertNotNull(allBeansCollectionProvider);

        final Collection<Object> allBeans = allBeansCollectionProvider.get();
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), allBeans.size());

        for (final Object bean : allBeans) {
            assertNotNull(bean);
        }
    }

    @Test
    public void checkMiscFactoryBean() {
        final MiscFactoryBean bean = injector.getBean(MiscFactoryBean.class);
        assertNotNull(bean);

        final NestedFactoryCreatedBean nestedFactoryCreatedBean = bean.createBean(mock(Provider.class));
        assertNotNull(nestedFactoryCreatedBean);
        assertNull(nestedFactoryCreatedBean.getFactoryCreatedBeanUsingBean());
    }

    @Test
    public void checkMiscQualifierBean() {
        final MiscQualifierBean milk = injector.getBean(MiscQualifierBean.class, "milk");
        assertNotNull(milk);
        assertEquals("milk", milk.getQualifier());

        final MiscQualifierBean cookie = injector.getBean(MiscQualifierBean.class, "cookie");
        assertNotNull(cookie);
        assertEquals("cookie", cookie.getQualifier());
    }

    @Test
    public void checkMiscQualifierBeanFactoryBean() {
        final MiscQualifierBeanFactoryBean bean = injector.getBean(MiscQualifierBeanFactoryBean.class);
        assertNotNull(bean);

        final MiscQualifierBean milk = bean.createBeanWithMilk();
        assertNotNull(milk);
        assertEquals("milk", milk.getQualifier());

        final MiscQualifierBean cookie = bean.createBeanWithCookie();
        assertNotNull(cookie);
        assertEquals("cookie", cookie.getQualifier());
    }

    @Test
    public void checkMultipleFactoryBean() {
        final MultipleFactoryBean bean = injector.getBean(MultipleFactoryBean.class);
        assertNotNull(bean);

        final FirstMultipleFactoryCreatedBean firstBean = bean.createFirstBean();
        assertNotNull(firstBean);
        assertTrue(firstBean.isCreatedByFactory());

        final SecondMultipleFactoryCreatedBean secondBean = bean.createSecondBean();
        assertNotNull(secondBean);
        assertTrue(secondBean.isCreatedByFactory());

        final ThirdMultipleFactoryCreatedBean thirdBean = bean.createThirdBean();
        assertNotNull(thirdBean);
        assertTrue(thirdBean.isCreatedByFactory());
    }

    @Test
    public void checkNestedFactoryCreatedBean() {
        // Factory created bean, level 1
        final NestedFactoryCreatedBean bean = injector.getBean(NestedFactoryCreatedBean.class);
        assertNotNull(bean);

        // Standalone bean, level 2
        final FactoryCreatedBeanUsingBean factoryCreatedBeanUsingBean = bean.getFactoryCreatedBeanUsingBean();
        assertNotNull(factoryCreatedBeanUsingBean);

        // Factory created bean, level 3
        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = factoryCreatedBeanUsingBean.getSimpleFactoryCreatedBean();
        assertNotNull(simpleFactoryCreatedBean);
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());

        // Factory created bean, level 3
        final FactoryParameterFactoryCreatedBean factoryParameterFactoryCreatedBean = factoryCreatedBeanUsingBean.getFactoryParameterFactoryCreatedBean();
        assertNotNull(factoryParameterFactoryCreatedBean);
        assertTrue(factoryParameterFactoryCreatedBean.isCreatedByFactory());

        // Factory created bean, level 4
        final OneParameterFactoryCreatedBean oneParameterFactoryCreatedBean = factoryParameterFactoryCreatedBean.getOneParameterFactoryCreatedBean();
        assertNotNull(oneParameterFactoryCreatedBean);
        assertTrue(oneParameterFactoryCreatedBean.isCreatedByFactory());

        // Standalone bean, level 5
        assertNotNull(oneParameterFactoryCreatedBean.getHelloBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNoBean() {
        injector.getBean(NoBean.class);
    }

    @Test
    public void checkOneParameterFactoryCreatedBean() {
        final OneParameterFactoryCreatedBean bean = injector.getBean(OneParameterFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
        assertNotNull(bean.getHelloBean());
    }

    @Test
    public void checkOrangeBean() {
        final OrangeBean orangeBean1 = injector.getBean(OrangeBean.class);
        assertNotNull(orangeBean1);
        assertTrue(orangeBean1.isCreatedByFactory());

        final OrangeBean orangeBean2 = injector.getBean(OrangeBean.class, "orange");
        assertNotNull(orangeBean2);
        assertTrue(orangeBean2.isCreatedByFactory());

        final ColorBean orangeBean3 = injector.getBean(ColorBean.class, "orange");
        assertNotNull(orangeBean3);
        assertTrue(orangeBean3.getClass().equals(OrangeBean.class));
        assertTrue(((OrangeBean) orangeBean3).isCreatedByFactory());
    }

    @Test
    public void checkOrangeFactoryBean() {
        final OrangeFactoryBean orangeFactoryBean = injector.getBean(OrangeFactoryBean.class);
        assertNotNull(orangeFactoryBean);

        final OrangeBean orangeBean = orangeFactoryBean.createOrangeBean();
        assertNotNull(orangeBean);
        assertTrue(orangeBean.isCreatedByFactory());
    }

    @Test
    public void checkOrganismBean() {
        final OrganismBean organismBean = injector.getBean(OrganismBean.class);
        assertNotNull(organismBean);
        assertTrue(organismBean.getClass().equals(OrganismBean.class));

        assertNotNull(organismBean.getHelloBeanInOrganismBean());
        assertNotNull(organismBean.getJavaBeanInOrganismBean());
        assertNotNull(organismBean.getCoffeeBeanInOrganismBean());
        assertNotNull(organismBean.getCarBeanInOrganismBean());

        assertNotNull(organismBean.getSetterBeanInOrganismBean());

        assertNotNull(organismBean.getFieldBean1InOrganismBean());

        assertNotNull(organismBean.getFieldBean2InOrganismBean());

        assertTrue(organismBean.isFieldsThenMethodsInjectedInOrganismBean());
    }

    @Test
    public void checkParameterFactoryBean() {
        final ParameterFactoryBean bean = injector.getBean(ParameterFactoryBean.class);
        assertNotNull(bean);

        final OneParameterFactoryCreatedBean beanWithOneParameter = bean.createBeanWithOneParameter(null);
        assertNotNull(beanWithOneParameter);
        assertTrue(beanWithOneParameter.isCreatedByFactory());
        assertNull(beanWithOneParameter.getHelloBean());

        final ThreeParametersFactoryCreatedBean beanWithThreeParameters = bean.createBeanWithThreeParameters(null, null, null);
        assertNotNull(beanWithThreeParameters);
        assertTrue(beanWithThreeParameters.isCreatedByFactory());
        assertNull(beanWithThreeParameters.getCoffeeBean());

        final FactoryParameterFactoryCreatedBean beanWithFactoryParameter = bean.createBeanWithFactoryParameter(null);
        assertNotNull(beanWithFactoryParameter);
        assertTrue(beanWithFactoryParameter.isCreatedByFactory());
        assertNull(beanWithFactoryParameter.getOneParameterFactoryCreatedBean());
    }

    @Test
    public void checkParentFactoryCreatedBean() {
        final ParentFactoryCreatedBean bean1 = injector.getBean(ParentFactoryCreatedBean.class);
        assertNotNull(bean1);
        assertTrue(bean1.isCreatedByParent());
        assertFalse(bean1.isCreatedByChild());

        final ParentFactoryCreatedBean bean2 = injector.getBean(ParentFactoryCreatedBean.class, "parent");
        assertNotNull(bean2);
        assertTrue(bean2.isCreatedByParent());
        assertFalse(bean2.isCreatedByChild());

        final ParentFactoryCreatedBean bean3 = injector.getBean(ParentFactoryCreatedBean.class, "any");
        assertNotNull(bean3);
        assertTrue(bean3.isCreatedByParent());
        assertFalse(bean3.isCreatedByChild());
    }

    @Test
    public void checkPepsi() {
        final Pepsi pepsi = injector.getBean(Pepsi.class);
        assertNotNull(pepsi);
    }

    @Test
    public void checkPetBean() {
        final PetBean petBean = injector.getBean(PetBean.class);
        assertNotNull(petBean);
        assertTrue(petBean.getClass().equals(PetBean.class));

        assertNotNull(petBean.getHelloBeanInOrganismBean()); // private, not overridden
        assertNull(petBean.getJavaBeanInOrganismBean()); // default, overridden
        assertNull(petBean.getCoffeeBeanInOrganismBean()); // protected, overridden
        assertNull(petBean.getCarBeanInOrganismBean()); // public, overridden

        assertNotNull(petBean.getHelloBeanInAnimalBean()); // private, not overridden
        assertNull(petBean.getJavaBeanInAnimalBean()); // default, overridden
        assertNull(petBean.getCoffeeBeanInAnimalBean()); // protected, overridden
        assertNull(petBean.getCarBeanInAnimalBean()); // public, overridden

        assertNotNull(petBean.getHelloBeanInPetBean());
        assertNotNull(petBean.getJavaBeanInPetBean());
        assertNotNull(petBean.getCoffeeBeanInPetBean());
        assertNotNull(petBean.getCarBeanInPetBean());

        assertNotNull(petBean.getSetterBeanInOrganismBean());
        assertNotNull(petBean.getSetterBeanInAnimalBean());
        assertNotNull(petBean.getSetterBeanInPetBean());

        assertNotNull(petBean.getFieldBean1InOrganismBean());
        assertNotNull(petBean.getFieldBean1InAnimalBean());
        assertNotNull(petBean.getFieldBean1InPetBean());

        assertNotNull(petBean.getFieldBean2InOrganismBean());
        assertNotNull(petBean.getFieldBean2InAnimalBean());
        assertNotNull(petBean.getFieldBean2InPetBean());

        assertTrue(petBean.isFieldsThenMethodsInjectedInPetBean());
    }

    @Test
    public void checkPinkSingletonBean() {
        final PinkSingletonBean pinkSingletonBean = injector.getBean(PinkSingletonBean.class);
        assertNotNull(pinkSingletonBean);
    }

    @Test
    public void checkPrivateBean() {
        final PrivateBean bean = injector.getBean(PrivateBean.class);
        assertNotNull(bean);

        final HelloBean helloBeanFromConstructor = bean.getHelloBeanFromConstructor();
        assertNotNull(helloBeanFromConstructor);

        final HelloBean helloBeanFromField = bean.getHelloBeanFromField();
        assertNotNull(helloBeanFromField);

        final HelloBean helloBeanFromSetter = bean.getHelloBeanFromSetter();
        assertNotNull(helloBeanFromSetter);
    }

    @Test
    public void checkPrivateFactoryBean() {
        final PrivateFactoryBean bean = injector.getBean(PrivateFactoryBean.class);
        assertNotNull(bean);
    }

    @Test
    public void checkPrivateFactoryCreatedBean() {
        final PrivateFactoryCreatedBean bean = injector.getBean(PrivateFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
    }

    @Test
    public void checkPrototypeWithSingletonBean() {
        final PrototypeWithSingletonBean prototypeWithSingletonBean = injector.getBean(PrototypeWithSingletonBean.class);
        assertNotNull(prototypeWithSingletonBean);

        final SingletonBean singletonBean1 = prototypeWithSingletonBean.getSingletonBean1();
        assertNotNull(singletonBean1);

        final SingletonBean singletonBean2 = prototypeWithSingletonBean.getSingletonBean2();
        assertNotNull(singletonBean2);
    }

    @Test
    public void checkProvidedHungryBean() {
        final ProvidedHungryBean providedHungryBean = injector.getBean(ProvidedHungryBean.class);
        assertNotNull(providedHungryBean);

        // Field
        final CollectionProvider<Food> foodBeansInFieldProvider = providedHungryBean.getFoodBeansInField();
        assertNotNull(foodBeansInFieldProvider);

        final Collection<Food> foodBeansInField = foodBeansInFieldProvider.get();
        assertNotNull(foodBeansInField);
        assertEquals(3, foodBeansInField.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInField));
        assertTrue(containsBean(FishBean.class, foodBeansInField));
        assertTrue(containsBean(BananaBean.class, foodBeansInField));

        // Constructor
        final CollectionProvider<Food> foodBeansInConstructorProvider = providedHungryBean.getFoodBeansInConstructor();
        assertNotNull(foodBeansInConstructorProvider);

        final Collection<Food> foodBeansInConstructor = foodBeansInConstructorProvider.get();
        assertNotNull(foodBeansInConstructor);
        assertEquals(3, foodBeansInConstructor.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInConstructor));
        assertTrue(containsBean(FishBean.class, foodBeansInConstructor));
        assertTrue(containsBean(BananaBean.class, foodBeansInConstructor));

        // Setter
        final CollectionProvider<Food> foodBeansInSetterProvider = providedHungryBean.getFoodBeansInSetter();
        assertNotNull(foodBeansInSetterProvider);

        final Collection<Food> foodBeansInSetter = foodBeansInSetterProvider.get();
        assertNotNull(foodBeansInSetter);
        assertEquals(3, foodBeansInSetter.size());
        assertTrue(containsBean(CheeseBean.class, foodBeansInSetter));
        assertTrue(containsBean(FishBean.class, foodBeansInSetter));
        assertTrue(containsBean(BananaBean.class, foodBeansInSetter));
    }

    @Test
    public void checkProvidedHungryQualifierBean() {
        final ProvidedHungryQualifierBean providedHungryQualifierBean = injector.getBean(ProvidedHungryQualifierBean.class);
        assertNotNull(providedHungryQualifierBean);

        // Fast food
        final CollectionProvider<Food> fastFoodBeansProvider = providedHungryQualifierBean.getFastFoodBeans();
        assertNotNull(fastFoodBeansProvider);

        final Collection<Food> fastFoodBeans = fastFoodBeansProvider.get();
        assertNotNull(fastFoodBeans);
        assertEquals(2, fastFoodBeans.size());
        assertTrue(containsBean(HotDogBean.class, fastFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, fastFoodBeans));

        // All food
        final CollectionProvider<Food> allFoodBeansProvider = providedHungryQualifierBean.getAllFoodBeans();
        assertNotNull(allFoodBeansProvider);

        final Collection<Food> allFoodBeans = allFoodBeansProvider.get();
        assertNotNull(allFoodBeans);
        assertEquals(6, allFoodBeans.size());
        assertTrue(containsBean(CheeseBean.class, allFoodBeans));
        assertTrue(containsBean(FishBean.class, allFoodBeans));
        assertTrue(containsBean(BananaBean.class, allFoodBeans));
        assertTrue(containsBean(HotDogBean.class, allFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, allFoodBeans));
        assertTrue(containsBean(AppleBean.class, allFoodBeans));

        // Round food
        final CollectionProvider<Food> roundFoodBeansProvider = providedHungryQualifierBean.getRoundFoodBeans();
        assertNotNull(roundFoodBeansProvider);

        final Collection<Food> roundFoodBeans = roundFoodBeansProvider.get();
        assertNotNull(roundFoodBeans);
        assertEquals(1, roundFoodBeans.size());
        assertTrue(containsBean(AppleBean.class, roundFoodBeans));
    }

    @Test
    public void checkProviderBean() {
        final ProviderBean providerBean = injector.getBean(ProviderBean.class);

        final Provider<ConstructorBean> constructorBeanProvider = providerBean.getConstructorBeanProvider();
        assertNotNull(constructorBeanProvider);
        assertNotNull(constructorBeanProvider.get());

        final Provider<FieldBean> fieldBeanProvider = providerBean.getFieldBeanProvider();
        assertNotNull(fieldBeanProvider);
        assertNotNull(fieldBeanProvider.get());

        final Provider<SetterBean> setterBeanProvider = providerBean.getSetterBeanProvider();
        assertNotNull(setterBeanProvider);
        assertNotNull(setterBeanProvider.get());
    }

    @Test
    public void checkQualifiedCollectionProviderBean() {
        final QualifiedCollectionProviderBean qualifiedCollectionProviderBean1 = injector.getBean(QualifiedCollectionProviderBean.class);
        assertNotNull(qualifiedCollectionProviderBean1);

        final QualifiedCollectionProviderBean qualifiedCollectionProviderBean2 = injector.getBean(QualifiedCollectionProviderBean.class, "qualifier");
        assertNotNull(qualifiedCollectionProviderBean2);

        final QualifiedCollectionProviderBean qualifiedCollectionProviderBean3 = injector.getBean(QualifiedCollectionProviderBean.class, "any");
        assertNotNull(qualifiedCollectionProviderBean3);

        assertNotSame(qualifiedCollectionProviderBean1, qualifiedCollectionProviderBean2);
        assertNotSame(qualifiedCollectionProviderBean2, qualifiedCollectionProviderBean3);
    }

    @Test
    public void checkRainbowBean() {
        final RainbowBean rainbowBean = injector.getBean(RainbowBean.class);

        assertTrue(rainbowBean.getBlueBean() instanceof BlueBean);
        assertTrue(rainbowBean.getGreenBean() instanceof GreenBean);
        assertTrue(rainbowBean.getRedBean() instanceof RedBean);
        assertTrue(rainbowBean.getYellowBean() instanceof YellowBean);
    }

    @Test
    public void checkRedBean() {
        final RedBean redBean1 = injector.getBean(RedBean.class);
        assertNotNull(redBean1);

        final RedBean redBean2 = injector.getBean(RedBean.class, "red");
        assertNotNull(redBean2);

        final ColorBean redBean3 = injector.getBean(ColorBean.class, "red");
        assertTrue(redBean3 instanceof RedBean);
    }

    @Test
    public void checkSecondCircularDependencyBean() {
        final SecondCircularDependencyBean secondCircularDependencyBean = injector.getBean(SecondCircularDependencyBean.class);

        final Provider<FirstCircularDependencyBean> firstCircularDependencyBeanProvider
            = secondCircularDependencyBean.getFirstCircularDependencyBeanProvider();
        assertNotNull(firstCircularDependencyBeanProvider);
        assertNotNull(firstCircularDependencyBeanProvider.get());
    }

    @Test
    public void checkSecondMultipleFactoryCreatedBean() {
        final SecondMultipleFactoryCreatedBean bean = injector.getBean(SecondMultipleFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
    }

    @Test
    public void checkCustomServiceBean() {
        final CustomServiceBean bean = injector.getBean(CustomServiceBean.class);
        assertNotNull(bean);
    }

    @Test
    public void checkSetterBean() {
        final SetterBean setterBean = injector.getBean(SetterBean.class);

        assertNotNull(setterBean);
        assertNotNull(setterBean.getFieldBean());
    }

    @Test
    public void checkSetterFactoryCreatedBean() {
        final SetterFactoryCreatedBean bean = injector.getBean(SetterFactoryCreatedBean.class);
        assertNotNull(bean);
    }

    @Test
    public void checkSimpleFactoryBean() {
        final SimpleFactoryBean simpleFactoryBean = injector.getBean(SimpleFactoryBean.class);
        assertNotNull(simpleFactoryBean);

        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = simpleFactoryBean.createBean();
        assertNotNull(simpleFactoryCreatedBean);
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void checkSimpleFactoryCreatedBean() {
        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = injector.getBean(SimpleFactoryCreatedBean.class);

        assertNotNull(simpleFactoryCreatedBean);
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void checkSimpleFactoryCreatedBeanUsingBean() {
        final SimpleFactoryCreatedBeanUsingBean simpleFactoryCreatedBeanUsingBean = injector.getBean(SimpleFactoryCreatedBeanUsingBean.class);
        assertNotNull(simpleFactoryCreatedBeanUsingBean);

        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = simpleFactoryCreatedBeanUsingBean.getSimpleFactoryCreatedBean();
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void checkSingletonBean() {
        final SingletonBean singletonBean = injector.getBean(SingletonBean.class);

        assertNotNull(singletonBean);
    }

    @Test
    public void checkSingletonCollectionProviderBean() {
        final SingletonCollectionProviderBean singletonCollectionProviderBean = injector.getBean(SingletonCollectionProviderBean.class);
        assertNotNull(singletonCollectionProviderBean);

        final CollectionProvider<CarBean> blueCarBeanCollectionProvider = singletonCollectionProviderBean.getBlueCarBeanCollectionProvider();
        assertNotNull(blueCarBeanCollectionProvider);

        final Collection<CarBean> carBeans = blueCarBeanCollectionProvider.get();
        assertNotNull(carBeans);
        assertEquals(1, carBeans.size());

        final CarBean carBean = carBeans.iterator().next();
        assertNotNull(carBean);
        assertEquals(BlueCarBean.class, carBean.getClass());
    }

    @Test
    public void checkSingletonFactoryBean() {
        final SingletonFactoryBean singletonFactoryBean = injector.getBean(SingletonFactoryBean.class);
        assertNotNull(singletonFactoryBean);

        final SingletonFactoryCreatedBean singletonBean = singletonFactoryBean.createSingletonBean();
        assertNotNull(singletonBean);
        assertTrue(singletonBean.isCreatedByFactory());
    }

    @Test
    public void checkSingletonFactoryCreatedBean() {
        final SingletonFactoryCreatedBean singletonFactoryCreatedBean = injector.getBean(SingletonFactoryCreatedBean.class);

        assertNotNull(singletonFactoryCreatedBean);
        assertTrue(singletonFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void checkSingletonFactoryCreatedBeanUsingBean() {
        final SingletonFactoryCreatedBeanUsingBean singletonFactoryCreatedBeanUsingBean = injector.getBean(SingletonFactoryCreatedBeanUsingBean.class);
        assertNotNull(singletonFactoryCreatedBeanUsingBean);

        final SingletonFactoryCreatedBean singletonFactoryCreatedBean1 = singletonFactoryCreatedBeanUsingBean.getSingletonFactoryCreatedBean1();
        assertNotNull(singletonFactoryCreatedBean1);

        final SingletonFactoryCreatedBean singletonFactoryCreatedBean2 = singletonFactoryCreatedBeanUsingBean.getSingletonFactoryCreatedBean2();
        assertNotNull(singletonFactoryCreatedBean2);

        // Singleton
        assertSame(singletonFactoryCreatedBean1, singletonFactoryCreatedBean2);
        assertTrue(singletonFactoryCreatedBean1.isCreatedByFactory());
    }

    @Test
    public void checkSingletonProviderBean() {
        final SingletonProviderBean singletonProviderBean = injector.getBean(SingletonProviderBean.class);
        assertNotNull(singletonProviderBean);

        final Provider<SingletonBean> singletonBeanProvider = singletonProviderBean.getSingletonBeanProvider();
        assertNotNull(singletonBeanProvider);

        final SingletonBean singletonBean = singletonBeanProvider.get();
        assertNotNull(singletonBean);
    }

    @Test
    public void checkSingletonWithPrototypeBean() {
        final SingletonWithPrototypeBean singletonWithPrototypeBean = injector.getBean(SingletonWithPrototypeBean.class);

        assertNotNull(singletonWithPrototypeBean);
        assertNotNull(singletonWithPrototypeBean.getHelloBean1());
        assertNotNull(singletonWithPrototypeBean.getHelloBean2());
    }

    @Test
    public void checkStaticBean() {
        assertNull(StaticBean.getFieldBean());
        assertNull(StaticBean.getSetterBean());

        final StaticBean staticBean = injector.getBean(StaticBean.class);
        assertNotNull(staticBean);
        assertNull(staticBean.getFieldBean());
        assertNull(staticBean.getSetterBean());
    }

    @Test
    public void checkStringPropertyFactoryBean() {
        final StringPropertyFactoryBean stringPropertyFactoryBean = injector.getBean(StringPropertyFactoryBean.class);
        assertNotNull(stringPropertyFactoryBean);

        final FactoryContext factoryContext = new FactoryContextImpl("some.property");

        final String stringProperty = stringPropertyFactoryBean.createStringProperty(factoryContext);
        assertEquals("This is some property", stringProperty);

        // Singleton scope
        final StringPropertyFactoryBean stringPropertyFactoryBean2 = injector.getBean(StringPropertyFactoryBean.class);
        assertSame(stringPropertyFactoryBean, stringPropertyFactoryBean2);
    }

    @Test
    public void checkStringPropertyInjectedBean() {
        final StringPropertyInjectedBean stringPropertyInjectedBean = injector.getBean(StringPropertyInjectedBean.class);
        assertNotNull(stringPropertyInjectedBean);

        assertEquals("Pink", stringPropertyInjectedBean.getColor());
        assertEquals("This is some property", stringPropertyInjectedBean.getSomeProperty());
        assertEquals("This is something else", stringPropertyInjectedBean.getSomeThing());
    }

    @Test
    public void checkTapeRecorderBean() {
        final TapeRecorderBean tapeRecorderBean1 = injector.getBean(TapeRecorderBean.class);
        assertNotNull(tapeRecorderBean1);
        assertTrue(tapeRecorderBean1.isCreatedByFactory());

        final TapeRecorderBean tapeRecorderBean2 = injector.getBean(TapeRecorderBean.class, "tape");
        assertNotNull(tapeRecorderBean2);

        final RecorderBean tapeRecorderBean3 = injector.getBean(RecorderBean.class, "tape");
        assertNotNull(tapeRecorderBean3);
        assertEquals(TapeRecorderBean.class, tapeRecorderBean3.getClass());
    }

    @Test
    public void checkTapeRecorderFactoryBean() {
        final TapeRecorderFactoryBean bean = injector.getBean(TapeRecorderFactoryBean.class);
        assertNotNull(bean);

        final TapeRecorderBean tapeRecorderBean = bean.createBean();
        assertNotNull(tapeRecorderBean);
        assertTrue(tapeRecorderBean.isCreatedByFactory());
    }

    @Test
    public void checkThirdLayer() {
        final Layer thirdLayer1 = injector.getBean(Layer.class, "third");
        final Layer thirdLayer2 = injector.getBean(FirstLayerBean.class, "third");
        final Layer thirdLayer3 = injector.getBean(SecondLayerBean.class, "third");
        final Layer thirdLayer4 = injector.getBean(ThirdLayerBean.class);
        final Layer thirdLayer5 = injector.getBean(ThirdLayerBean.class, "third");

        assertNotSame(thirdLayer1, thirdLayer2);
        assertNotSame(thirdLayer2, thirdLayer3);
        assertNotSame(thirdLayer3, thirdLayer4);
        assertNotSame(thirdLayer4, thirdLayer5);

        assertEquals(ThirdLayerBean.class, thirdLayer1.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer2.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer3.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer4.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer5.getClass());
    }

    @Test
    public void checkThirdMultipleFactoryCreatedBean() {
        final ThirdMultipleFactoryCreatedBean bean = injector.getBean(ThirdMultipleFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());
    }

    @Test
    public void checkThreeParametersFactoryCreatedBean() {
        final ThreeParametersFactoryCreatedBean bean = injector.getBean(ThreeParametersFactoryCreatedBean.class);

        assertNotNull(bean);
        assertTrue(bean.isCreatedByFactory());

        assertNotNull(bean.getCoffeeBean());
        assertNotNull(bean.getRedBean());

        final ColorBean colorBean = bean.getColorBean();
        assertNotNull(colorBean);
        assertEquals(BlueBean.class, colorBean.getClass());
    }

    @Test
    public void checkYellowBean() {
        final YellowBean yellowBean1 = injector.getBean(YellowBean.class);
        assertNotNull(yellowBean1);
        assertTrue(yellowBean1.getClass().equals(YellowBean.class));

        final YellowBean yellowBean2 = injector.getBean(YellowBean.class, "Yellow");
        assertNotNull(yellowBean2);
        assertTrue(yellowBean2.getClass().equals(YellowBean.class));

        final ColorBean yellowBean3 = injector.getBean(ColorBean.class, "Yellow");
        assertNotNull(yellowBean3);
        assertTrue(yellowBean3.getClass().equals(YellowBean.class));
    }

    private boolean containsBean(final Class<?> bean, final Collection<?> collection) {
        for (final Object object : collection) {
            if (bean.equals(object.getClass())) {
                return true;
            }
        }

        return false;
    }
}
