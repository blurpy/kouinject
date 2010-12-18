
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

import static org.junit.Assert.*;

import java.util.Collection;

import javax.inject.Provider;

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
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.AppleBean;
import net.usikkert.kouinject.testbeans.scanned.collection.BananaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.CheeseBean;
import net.usikkert.kouinject.testbeans.scanned.collection.FishBean;
import net.usikkert.kouinject.testbeans.scanned.collection.Food;
import net.usikkert.kouinject.testbeans.scanned.collection.HamburgerBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HotdogBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder1.Folder1Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder2.Folder2Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder3.Folder3Bean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.AnimalBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.OrganismBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.PetBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.pets.CatBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.BlueBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.DarkYellowBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;
import net.usikkert.kouinject.testbeans.scanned.scope.PinkSingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.PrototypeWithSingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonProviderBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonWithPrototypeBean;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FifthLayer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FirstLayer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.FourthLayer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.Layer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.SecondLayer;
import net.usikkert.kouinject.testbeans.scanned.scope.inheritance.ThirdLayer;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of correct injection in all the beans.
 *
 * @author Christian Ihle
 */
public class BeanInjectionTest {

    private DefaultBeanLoader beanLoader;

    @Before
    public void setupBeanLoader() {
        beanLoader = createBeanLoaderWithBasePackages("net.usikkert.kouinject.testbeans.scanned");
    }

    @Test
    public void checkAbstractBean() {
        final AbstractBean abstractBean = beanLoader.getBean(AbstractBean.class);
        assertNotNull(abstractBean);

        final AbstractBeanImpl abstractBeanImpl = beanLoader.getBean(AbstractBeanImpl.class);
        assertNotNull(abstractBeanImpl);
    }

    @Test
    public void checkAllInjectionTypesBean() {
        final AllInjectionTypesBean allInjectionTypesBean = beanLoader.getBean(AllInjectionTypesBean.class);

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
    }

    @Test
    public void checkAnimalBean() {
        final AnimalBean animalBean = beanLoader.getBean(AnimalBean.class);
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
        final AnyBean anyBean = beanLoader.getBean(AnyBean.class);

        assertNotNull(anyBean.getHelloBean());
        assertNotNull(anyBean.getRedBean());

        final Collection<ColorBean> colorBeans = anyBean.getColorBeans();
        assertNotNull(colorBeans);
        assertEquals(5, colorBeans.size());

        final Collection<HelloBean> helloBeanCollection = anyBean.getHelloBeanCollection();
        assertNotNull(helloBeanCollection);
        assertEquals(1, helloBeanCollection.size());

        final Collection<RedBean> theRedBeanCollection = anyBean.getTheRedBeanCollection();
        assertNotNull(theRedBeanCollection);
        assertEquals(1, theRedBeanCollection.size());
    }

    @Test
    public void checkAppleBean() {
        final AppleBean appleBean1 = beanLoader.getBean(AppleBean.class);
        assertNotNull(appleBean1);

        final AppleBean appleBean2 = beanLoader.getBean(AppleBean.class, "roundfood");
        assertNotNull(appleBean2);

        final AppleBean appleBean3 = beanLoader.getBean(AppleBean.class, "any");
        assertNotNull(appleBean3);

        final Food appleBean4 = beanLoader.getBean(Food.class, "RoundFood");
        assertNotNull(appleBean4);
        assertTrue(appleBean4 instanceof AppleBean);
    }

    @Test
    public void checkBananaBean() {
        final BananaBean bananaBean = beanLoader.getBean(BananaBean.class);
        assertNotNull(bananaBean);
    }

    @Test
    public void checkBlueBean() {
        final BlueBean blueBean1 = beanLoader.getBean(BlueBean.class);
        assertNotNull(blueBean1);

        final BlueBean blueBean2 = beanLoader.getBean(BlueBean.class, "Blue");
        assertNotNull(blueBean2);

        final ColorBean blueBean3 = beanLoader.getBean(ColorBean.class, "Blue");
        assertTrue(blueBean3 instanceof BlueBean);
    }

    @Test
    public void checkBlueCarBean() {
        final BlueCarBean blueCarBean1 = beanLoader.getBean(BlueCarBean.class);
        assertNotNull(blueCarBean1);

        final BlueCarBean blueCarBean2 = beanLoader.getBean(BlueCarBean.class, "Blue");
        assertNotNull(blueCarBean2);

        final CarBean blueCarBean3 = beanLoader.getBean(CarBean.class, "Blue");
        assertTrue(blueCarBean3 instanceof BlueCarBean);
    }

    @Test
    public void checkCatBean() {
        final CatBean catBean = beanLoader.getBean(CatBean.class);
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
        final CarBean carBean = beanLoader.getBean(CarBean.class);

        assertNotNull(carBean);
        assertEquals(CarBean.class, carBean.getClass());
    }

    @Test
    public void checkCheeseBean() {
        final CheeseBean cheeseBean = beanLoader.getBean(CheeseBean.class);
        assertNotNull(cheeseBean);
    }

    @Test
    public void checkChildBean() {
        final ChildBean childBean = beanLoader.getBean(ChildBean.class);

        assertNotNull(childBean.getFieldBean());
        assertNotNull(childBean.getHelloBean());
        assertNotNull(childBean.getCoffeeBean());
    }

    @Test
    public void checkCoffeeBean() {
        final CoffeeBean coffeeBean = beanLoader.getBean(CoffeeBean.class);

        assertNotNull(coffeeBean.getHelloBean());
        assertNotNull(coffeeBean.getJavaBean());
    }

    @Test
    public void checkConstructorBean() {
        final ConstructorBean constructorBean = beanLoader.getBean(ConstructorBean.class);

        assertNotNull(constructorBean.getHelloBean());
        assertNotNull(constructorBean.getSetterBean());
    }

    @Test
    public void checkDarkYellowBean() {
        final DarkYellowBean darkYellowBean1 = beanLoader.getBean(DarkYellowBean.class);
        assertNotNull(darkYellowBean1);

        final DarkYellowBean darkYellowBean2 = beanLoader.getBean(DarkYellowBean.class, "darkYellow");
        assertNotNull(darkYellowBean2);

        final YellowBean darkYellowBean3 = beanLoader.getBean(YellowBean.class, "darkYellow");
        assertNotNull(darkYellowBean3);
        assertTrue(darkYellowBean3.getClass().equals(DarkYellowBean.class));

        final ColorBean darkYellowBean4 = beanLoader.getBean(ColorBean.class, "darkYellow");
        assertNotNull(darkYellowBean4);
        assertTrue(darkYellowBean4.getClass().equals(DarkYellowBean.class));
    }

    @Test
    public void checkEverythingBean() {
        final EverythingBean everythingBean = beanLoader.getBean(EverythingBean.class);

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
    public void checkFieldBean() {
        final FieldBean fieldBean = beanLoader.getBean(FieldBean.class);

        assertNotNull(fieldBean.getHelloBean());
        assertNotNull(fieldBean.getAbstractBean());
        assertNotNull(fieldBean.getInterfaceBean());
    }

    @Test
    public void checkFifthLayer() {
        final Layer fifthLayer1 = beanLoader.getBean(Layer.class, "fifth");
        final Layer fifthLayer2 = beanLoader.getBean(FirstLayer.class, "fifth");
        final Layer fifthLayer3 = beanLoader.getBean(SecondLayer.class, "fifth");
        final Layer fifthLayer4 = beanLoader.getBean(ThirdLayer.class, "fifth");
        final Layer fifthLayer5 = beanLoader.getBean(FourthLayer.class, "fifth");
        final Layer fifthLayer6 = beanLoader.getBean(FifthLayer.class);
        final Layer fifthLayer7 = beanLoader.getBean(FifthLayer.class, "fifth");
        final Layer fifthLayer8 = beanLoader.getBean(FifthLayer.class, "any");

        assertSame(fifthLayer1, fifthLayer2);
        assertSame(fifthLayer2, fifthLayer3);
        assertSame(fifthLayer3, fifthLayer4);
        assertSame(fifthLayer4, fifthLayer5);
        assertSame(fifthLayer5, fifthLayer6);
        assertSame(fifthLayer6, fifthLayer7);
        assertSame(fifthLayer7, fifthLayer8);

        assertEquals(FifthLayer.class, fifthLayer1.getClass());
        assertEquals(FifthLayer.class, fifthLayer2.getClass());
        assertEquals(FifthLayer.class, fifthLayer3.getClass());
        assertEquals(FifthLayer.class, fifthLayer4.getClass());
        assertEquals(FifthLayer.class, fifthLayer5.getClass());
        assertEquals(FifthLayer.class, fifthLayer6.getClass());
        assertEquals(FifthLayer.class, fifthLayer7.getClass());
        assertEquals(FifthLayer.class, fifthLayer8.getClass());
    }

    @Test
    public void checkFinalBean() {
        final FinalBean finalBean = beanLoader.getBean(FinalBean.class);

        assertNull(finalBean.getFieldBean());
    }

    @Test
    public void checkFirstCircularDependencyBean() {
        final FirstCircularDependencyBean firstCircularDependencyBean = beanLoader.getBean(FirstCircularDependencyBean.class);

        assertNotNull(firstCircularDependencyBean.getSecondCircularDependencyBean());
    }

    @Test
    public void checkFirstLayer() {
        final Layer firstLayer1 = beanLoader.getBean(Layer.class, "first");
        final Layer firstLayer2 = beanLoader.getBean(FirstLayer.class);
        final Layer firstLayer3 = beanLoader.getBean(FirstLayer.class, "first");

        assertSame(firstLayer1, firstLayer2);
        assertSame(firstLayer2, firstLayer3);

        assertEquals(FirstLayer.class, firstLayer1.getClass());
        assertEquals(FirstLayer.class, firstLayer2.getClass());
        assertEquals(FirstLayer.class, firstLayer3.getClass());
    }

    @Test
    public void checkFishBean() {
        final FishBean fishBean = beanLoader.getBean(FishBean.class);
        assertNotNull(fishBean);
    }

    @Test
    public void checkFolder1Bean() {
        final Folder1Bean folder1Bean = beanLoader.getBean(Folder1Bean.class);

        assertNotNull(folder1Bean);
    }

    @Test
    public void checkFolder2Bean() {
        final Folder2Bean folder2Bean = beanLoader.getBean(Folder2Bean.class);

        assertNotNull(folder2Bean);
    }

    @Test
    public void checkFolder3Bean() {
        final Folder3Bean folder3Bean = beanLoader.getBean(Folder3Bean.class);

        assertNotNull(folder3Bean);
        assertNotNull(folder3Bean.getFolder1Bean());
        assertNotNull(folder3Bean.getFolder2Bean());
    }

    @Test
    public void checkGarageBean() {
        final GarageBean garageBean = beanLoader.getBean(GarageBean.class);

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
        final GreenBean greenBean1 = beanLoader.getBean(GreenBean.class);
        assertNotNull(greenBean1);

        final GreenBean greenBean2 = beanLoader.getBean(GreenBean.class, "Green");
        assertNotNull(greenBean2);

        final ColorBean greenBean3 = beanLoader.getBean(ColorBean.class, "Green");
        assertTrue(greenBean3 instanceof GreenBean);
    }

    @Test
    public void checkHamburgerBean() {
        final HamburgerBean hamburgerBean1 = beanLoader.getBean(HamburgerBean.class);
        assertNotNull(hamburgerBean1);

        final HamburgerBean hamburgerBean2 = beanLoader.getBean(HamburgerBean.class, "fastfood");
        assertNotNull(hamburgerBean2);

        final HamburgerBean hamburgerBean3 = beanLoader.getBean(HamburgerBean.class, "any");
        assertNotNull(hamburgerBean3);
    }

    @Test
    public void checkHelloBean() {
        final HelloBean helloBean = beanLoader.getBean(HelloBean.class);

        assertNotNull(helloBean);
    }

    @Test
    public void checkHotdogBean() {
        final HotdogBean hotdogBean1 = beanLoader.getBean(HotdogBean.class);
        assertNotNull(hotdogBean1);

        final HotdogBean hotdogBean2 = beanLoader.getBean(HotdogBean.class, "fastfood");
        assertNotNull(hotdogBean2);

        final HotdogBean hotdogBean3 = beanLoader.getBean(HotdogBean.class, "any");
        assertNotNull(hotdogBean3);
    }

    @Test
    public void checkHungryBean() {
        final HungryBean hungryBean = beanLoader.getBean(HungryBean.class);

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
        final HungryQualifierBean hungryQualifierBean = beanLoader.getBean(HungryQualifierBean.class);

        assertNotNull(hungryQualifierBean);

        final Collection<Food> fastFoodBeans = hungryQualifierBean.getFastFoodBeans();
        assertNotNull(fastFoodBeans);
        assertEquals(2, fastFoodBeans.size());
        assertTrue(containsBean(HotdogBean.class, fastFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, fastFoodBeans));

        final Collection<Food> allFoodBeans = hungryQualifierBean.getAllFoodBeans();
        assertNotNull(allFoodBeans);
        assertEquals(6, allFoodBeans.size());
        assertTrue(containsBean(CheeseBean.class, allFoodBeans));
        assertTrue(containsBean(FishBean.class, allFoodBeans));
        assertTrue(containsBean(BananaBean.class, allFoodBeans));
        assertTrue(containsBean(HotdogBean.class, allFoodBeans));
        assertTrue(containsBean(HamburgerBean.class, allFoodBeans));
        assertTrue(containsBean(AppleBean.class, allFoodBeans));

        final Collection<Food> roundFoodBeans = hungryQualifierBean.getRoundFoodBeans();
        assertNotNull(roundFoodBeans);
        assertEquals(1, roundFoodBeans.size());
        assertTrue(containsBean(AppleBean.class, roundFoodBeans));
    }

    @Test
    public void checkInterfaceBean() {
        final InterfaceBean interfaceBean = beanLoader.getBean(InterfaceBean.class);
        assertNotNull(interfaceBean);

        final InterfaceBeanImpl interfaceBeanImpl = beanLoader.getBean(InterfaceBeanImpl.class);
        assertNotNull(interfaceBeanImpl);
    }

    @Test
    public void checkJavaBean() {
        final JavaBean javaBean = beanLoader.getBean(JavaBean.class);

        assertNotNull(javaBean.getFieldBean());
        assertNotNull(javaBean.getHelloBean());
    }

    @Test
    public void checkLastBean() {
        final LastBean lastBean = beanLoader.getBean(LastBean.class);

        assertNotNull(lastBean.getEverythingBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNoBean() {
        beanLoader.getBean(NoBean.class);
    }

    @Test
    public void checkOrganismBean() {
        final OrganismBean organismBean = beanLoader.getBean(OrganismBean.class);
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
    public void checkPetBean() {
        final PetBean petBean = beanLoader.getBean(PetBean.class);
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
        final PinkSingletonBean pinkSingletonBean = beanLoader.getBean(PinkSingletonBean.class);
        assertNotNull(pinkSingletonBean);
    }

    @Test
    public void checkPrototypeWithSingletonBean() {
        final PrototypeWithSingletonBean prototypeWithSingletonBean = beanLoader.getBean(PrototypeWithSingletonBean.class);
        assertNotNull(prototypeWithSingletonBean);

        final SingletonBean singletonBean1 = prototypeWithSingletonBean.getSingletonBean1();
        assertNotNull(singletonBean1);

        final SingletonBean singletonBean2 = prototypeWithSingletonBean.getSingletonBean2();
        assertNotNull(singletonBean2);
    }

    @Test
    public void checkProvidedHungryBean() {
        final ProvidedHungryBean providedHungryBean = beanLoader.getBean(ProvidedHungryBean.class);
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
        final ProvidedHungryQualifierBean providedHungryQualifierBean = beanLoader.getBean(ProvidedHungryQualifierBean.class);
        assertNotNull(providedHungryQualifierBean);

        // Fast food
        final CollectionProvider<Food> fastFoodBeansProvider = providedHungryQualifierBean.getFastFoodBeans();
        assertNotNull(fastFoodBeansProvider);

        final Collection<Food> fastFoodBeans = fastFoodBeansProvider.get();
        assertNotNull(fastFoodBeans);
        assertEquals(2, fastFoodBeans.size());
        assertTrue(containsBean(HotdogBean.class, fastFoodBeans));
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
        assertTrue(containsBean(HotdogBean.class, allFoodBeans));
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
        final ProviderBean providerBean = beanLoader.getBean(ProviderBean.class);

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
    public void checkRainbowBean() {
        final RainbowBean rainbowBean = beanLoader.getBean(RainbowBean.class);

        assertTrue(rainbowBean.getBlueBean() instanceof BlueBean);
        assertTrue(rainbowBean.getGreenBean() instanceof GreenBean);
        assertTrue(rainbowBean.getRedBean() instanceof RedBean);
        assertTrue(rainbowBean.getYellowBean() instanceof YellowBean);
    }

    @Test
    public void checkRedBean() {
        final RedBean redBean1 = beanLoader.getBean(RedBean.class);
        assertNotNull(redBean1);

        final RedBean redBean2 = beanLoader.getBean(RedBean.class, "red");
        assertNotNull(redBean2);

        final ColorBean redBean3 = beanLoader.getBean(ColorBean.class, "red");
        assertTrue(redBean3 instanceof RedBean);
    }

    @Test
    public void checkSecondCircularDependencyBean() {
        final SecondCircularDependencyBean secondCircularDependencyBean = beanLoader.getBean(SecondCircularDependencyBean.class);

        final Provider<FirstCircularDependencyBean> firstCircularDependencyBeanProvider
            = secondCircularDependencyBean.getFirstCircularDependencyBeanProvider();
        assertNotNull(firstCircularDependencyBeanProvider);
        assertNotNull(firstCircularDependencyBeanProvider.get());
    }

    @Test
    public void checkSetterBean() {
        final SetterBean setterBean = beanLoader.getBean(SetterBean.class);

        assertNotNull(setterBean);
        assertNotNull(setterBean.getFieldBean());
    }

    @Test
    public void checkSingletonBean() {
        final SingletonBean singletonBean = beanLoader.getBean(SingletonBean.class);

        assertNotNull(singletonBean);
    }

    @Test
    public void checkSingletonProviderBean() {
        final SingletonProviderBean singletonProviderBean = beanLoader.getBean(SingletonProviderBean.class);
        assertNotNull(singletonProviderBean);

        final Provider<SingletonBean> singletonBeanProvider = singletonProviderBean.getSingletonBeanProvider();
        assertNotNull(singletonBeanProvider);

        final SingletonBean singletonBean = singletonBeanProvider.get();
        assertNotNull(singletonBean);
    }

    @Test
    public void checkSingletonWithPrototypeBean() {
        final SingletonWithPrototypeBean singletonWithPrototypeBean = beanLoader.getBean(SingletonWithPrototypeBean.class);

        assertNotNull(singletonWithPrototypeBean);
        assertNotNull(singletonWithPrototypeBean.getHelloBean1());
        assertNotNull(singletonWithPrototypeBean.getHelloBean2());
    }

    @Test
    public void checkStaticBean() {
        assertNull(StaticBean.getFieldBean());
        assertNull(StaticBean.getSetterBean());

        final StaticBean staticBean = beanLoader.getBean(StaticBean.class);
        assertNotNull(staticBean);
        assertNull(staticBean.getFieldBean());
        assertNull(staticBean.getSetterBean());
    }

    @Test
    public void checkThirdLayer() {
        final Layer thirdLayer1 = beanLoader.getBean(Layer.class, "third");
        final Layer thirdLayer2 = beanLoader.getBean(FirstLayer.class, "third");
        final Layer thirdLayer3 = beanLoader.getBean(SecondLayer.class, "third");
        final Layer thirdLayer4 = beanLoader.getBean(ThirdLayer.class);
        final Layer thirdLayer5 = beanLoader.getBean(ThirdLayer.class, "third");

        assertNotSame(thirdLayer1, thirdLayer2);
        assertNotSame(thirdLayer2, thirdLayer3);
        assertNotSame(thirdLayer3, thirdLayer4);
        assertNotSame(thirdLayer4, thirdLayer5);

        assertEquals(ThirdLayer.class, thirdLayer1.getClass());
        assertEquals(ThirdLayer.class, thirdLayer2.getClass());
        assertEquals(ThirdLayer.class, thirdLayer3.getClass());
        assertEquals(ThirdLayer.class, thirdLayer4.getClass());
        assertEquals(ThirdLayer.class, thirdLayer5.getClass());
    }

    @Test
    public void checkYellowBean() {
        final YellowBean yellowBean1 = beanLoader.getBean(YellowBean.class);
        assertNotNull(yellowBean1);
        assertTrue(yellowBean1.getClass().equals(YellowBean.class));

        final YellowBean yellowBean2 = beanLoader.getBean(YellowBean.class, "Yellow");
        assertNotNull(yellowBean2);
        assertTrue(yellowBean2.getClass().equals(YellowBean.class));

        final ColorBean yellowBean3 = beanLoader.getBean(ColorBean.class, "Yellow");
        assertNotNull(yellowBean3);
        assertTrue(yellowBean3.getClass().equals(YellowBean.class));
    }

    private DefaultBeanLoader createBeanLoaderWithBasePackages(final String... basePackages) {
        final ClassLocator classLocator = new ClassPathScanner();
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(
                classLocator, basePackages);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();

        return new DefaultBeanLoader(beanDataHandler, beanLocator);
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
