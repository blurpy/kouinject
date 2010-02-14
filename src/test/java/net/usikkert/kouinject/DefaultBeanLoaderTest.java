
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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

import java.util.HashSet;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.Dependency;
import net.usikkert.kouinject.testbeans.notscanned.ACloserMatchOfImplementationUser;
import net.usikkert.kouinject.testbeans.notscanned.FirstCircularBean;
import net.usikkert.kouinject.testbeans.notscanned.FirstInterfaceImpl;
import net.usikkert.kouinject.testbeans.notscanned.SecondCircularBean;
import net.usikkert.kouinject.testbeans.notscanned.SecondInterfaceImpl;
import net.usikkert.kouinject.testbeans.notscanned.TheInterface;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.BlueCarBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.FirstCircularDependencyBean;
import net.usikkert.kouinject.testbeans.scanned.GarageBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.LastBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.RainbowBean;
import net.usikkert.kouinject.testbeans.scanned.SecondCircularDependencyBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
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

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link DefaultBeanLoader}.
 *
 * @author Christian Ihle
 */
public class DefaultBeanLoaderTest {

    private DefaultBeanLoader beanLoader;

    @Before
    public void setupBeanLoader() {
        final ClassLocator classLocator = new ClassPathScanner();
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(
                "net.usikkert.kouinject.testbeans.scanned", classLocator);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        beanLoader = new DefaultBeanLoader(beanDataHandler, beanLocator);
    }

    @Test
    public void checkAbstractBean() {
        beanLoader.loadBeans();

        final AbstractBean abstractBean = beanLoader.getBean(AbstractBean.class);
        assertNotNull(abstractBean);

        final AbstractBeanImpl abstractBeanImpl = beanLoader.getBean(AbstractBeanImpl.class);
        assertNotNull(abstractBeanImpl);
    }

    @Test
    public void checkAnimalBean() {
        beanLoader.loadBeans();

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
    public void checkBlueBean() {
        beanLoader.loadBeans();

        final BlueBean blueBean1 = beanLoader.getBean(BlueBean.class);
        assertNotNull(blueBean1);

        final BlueBean blueBean2 = beanLoader.getBean(BlueBean.class, "Blue");
        assertNotNull(blueBean2);

        final ColorBean blueBean3 = beanLoader.getBean(ColorBean.class, "Blue");
        assertTrue(blueBean3 instanceof BlueBean);
    }

    @Test
    public void checkCatBean() {
        beanLoader.loadBeans();

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
    public void checkChildBean() {
        beanLoader.loadBeans();

        final ChildBean childBean = beanLoader.getBean(ChildBean.class);

        assertNotNull(childBean.getFieldBean());
        assertNotNull(childBean.getHelloBean());
        assertNotNull(childBean.getCoffeeBean());
    }

    @Test
    public void checkCoffeeBean() {
        beanLoader.loadBeans();

        final CoffeeBean coffeeBean = beanLoader.getBean(CoffeeBean.class);

        assertNotNull(coffeeBean.getHelloBean());
        assertNotNull(coffeeBean.getJavaBean());
    }

    @Test
    public void checkConstructorBean() {
        beanLoader.loadBeans();

        final ConstructorBean constructorBean = beanLoader.getBean(ConstructorBean.class);

        assertNotNull(constructorBean.getHelloBean());
        assertNotNull(constructorBean.getSetterBean());
    }

    @Test
    public void checkDarkYellowBean() {
        beanLoader.loadBeans();

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
        beanLoader.loadBeans();

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
        beanLoader.loadBeans();

        final FieldBean fieldBean = beanLoader.getBean(FieldBean.class);

        assertNotNull(fieldBean.getHelloBean());
        assertNotNull(fieldBean.getAbstractBean());
        assertNotNull(fieldBean.getInterfaceBean());
    }

    @Test
    public void checkFirstCircularDependencyBean() {
        beanLoader.loadBeans();

        final FirstCircularDependencyBean firstCircularDependencyBean = beanLoader.getBean(FirstCircularDependencyBean.class);

        assertNotNull(firstCircularDependencyBean.getSecondCircularDependencyBean());
    }

    @Test
    public void checkGarageBean() {
        beanLoader.loadBeans();

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
        beanLoader.loadBeans();

        final GreenBean greenBean1 = beanLoader.getBean(GreenBean.class);
        assertNotNull(greenBean1);

        final GreenBean greenBean2 = beanLoader.getBean(GreenBean.class, "Green");
        assertNotNull(greenBean2);

        final ColorBean greenBean3 = beanLoader.getBean(ColorBean.class, "Green");
        assertTrue(greenBean3 instanceof GreenBean);
    }

    @Test
    public void checkHelloBean() {
        beanLoader.loadBeans();

        final HelloBean helloBean = beanLoader.getBean(HelloBean.class);
        assertNotNull(helloBean);
    }

    @Test
    public void checkInterfaceBean() {
        beanLoader.loadBeans();

        final InterfaceBean interfaceBean = beanLoader.getBean(InterfaceBean.class);
        assertNotNull(interfaceBean);

        final InterfaceBeanImpl interfaceBeanImpl = beanLoader.getBean(InterfaceBeanImpl.class);
        assertNotNull(interfaceBeanImpl);
    }

    @Test
    public void checkJavaBean() {
        beanLoader.loadBeans();

        final JavaBean javaBean = beanLoader.getBean(JavaBean.class);

        assertNotNull(javaBean.getFieldBean());
        assertNotNull(javaBean.getHelloBean());
    }

    @Test
    public void checkLastBean() {
        beanLoader.loadBeans();

        final LastBean lastBean = beanLoader.getBean(LastBean.class);

        assertNotNull(lastBean.getEverythingBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNoBean() {
        beanLoader.loadBeans();

        beanLoader.getBean(NoBean.class);
    }

    @Test
    public void checkOrganismBean() {
        beanLoader.loadBeans();

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
        beanLoader.loadBeans();

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
    public void checkProviderBean() {
        beanLoader.loadBeans();

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
        beanLoader.loadBeans();

        final RainbowBean rainbowBean = beanLoader.getBean(RainbowBean.class);

        assertTrue(rainbowBean.getBlueBean() instanceof BlueBean);
        assertTrue(rainbowBean.getGreenBean() instanceof GreenBean);
        assertTrue(rainbowBean.getRedBean() instanceof RedBean);
        assertTrue(rainbowBean.getYellowBean() instanceof YellowBean);
    }

    @Test
    public void checkRedBean() {
        beanLoader.loadBeans();

        final RedBean redBean1 = beanLoader.getBean(RedBean.class);
        assertNotNull(redBean1);

        final RedBean redBean2 = beanLoader.getBean(RedBean.class, "red");
        assertNotNull(redBean2);

        final ColorBean redBean3 = beanLoader.getBean(ColorBean.class, "red");
        assertTrue(redBean3 instanceof RedBean);
    }

    @Test
    public void checkSecondCircularDependencyBean() {
        beanLoader.loadBeans();

        final SecondCircularDependencyBean secondCircularDependencyBean = beanLoader.getBean(SecondCircularDependencyBean.class);

        final Provider<FirstCircularDependencyBean> firstCircularDependencyBeanProvider
            = secondCircularDependencyBean.getFirstCircularDependencyBeanProvider();
        assertNotNull(firstCircularDependencyBeanProvider);
        assertNotNull(firstCircularDependencyBeanProvider.get());
    }

    @Test
    public void checkSetterBean() {
        beanLoader.loadBeans();

        final SetterBean setterBean = beanLoader.getBean(SetterBean.class);

        assertNotNull(setterBean.getFieldBean());
    }

    @Test
    public void checkYellowBean() {
        beanLoader.loadBeans();

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

    @Test
    public void addBeanShouldMakeBeanAvailableButNotAutowire() {
        beanLoader.loadBeans();

        final NoBean noBean = new NoBean();
        beanLoader.addBean(noBean);

        final NoBean noBeanFromBeanLoader = beanLoader.getBean(NoBean.class);
        assertNotNull(noBeanFromBeanLoader);
        assertNull(noBeanFromBeanLoader.getHelloBean());
        assertNull(noBeanFromBeanLoader.getCoffeeBean());
    }

    @Test
    public void autowireShouldInjectFieldsInBean() {
        beanLoader.loadBeans();

        final NoBean noBean = new NoBean();
        beanLoader.autowire(noBean);

        assertNotNull(noBean.getHelloBean());
        assertNotNull(noBean.getCoffeeBean());
    }

    @Test
    public void beanLoaderShouldHandleMocks() {
        final HelloBean helloBean = mock(HelloBean.class);
        beanLoader.addBean(helloBean);

        final AbstractBeanImpl abstractBean = mock(AbstractBeanImpl.class);
        beanLoader.addBean(abstractBean);

        final InterfaceBean interfaceBean = mock(InterfaceBean.class);
        beanLoader.addBean(interfaceBean);

        final FieldBean fieldBean = new FieldBean();
        beanLoader.autowire(fieldBean);

        assertSame(helloBean, fieldBean.getHelloBean());
        assertSame(abstractBean, fieldBean.getAbstractBean());
        assertSame(interfaceBean, fieldBean.getInterfaceBean());
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependenciesShouldBeDetected() {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final DefaultBeanLoader loader = new DefaultBeanLoader(beanDataHandler, beanLocator);

        final Set<Dependency> beans = new HashSet<Dependency>();
        beans.add(new Dependency(FirstCircularBean.class));
        beans.add(new Dependency(SecondCircularBean.class));

        when(beanLocator.findBeans()).thenReturn(beans);

        loader.loadBeans();
    }

    @Test(expected = IllegalStateException.class)
    public void tooManyMatchesForADependencyShouldBeDetected() {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final DefaultBeanLoader loader = new DefaultBeanLoader(beanDataHandler, beanLocator);

        final Set<Dependency> beans = new HashSet<Dependency>();
        beans.add(new Dependency(FirstInterfaceImpl.class));
        beans.add(new Dependency(SecondInterfaceImpl.class));

        when(beanLocator.findBeans()).thenReturn(beans);

        loader.loadBeans();

        final TheInterfaceUser theInterfaceUser = new TheInterfaceUser();
        loader.autowire(theInterfaceUser);
    }

    @Test
    public void severalBeansForAnInterfaceIsOKIfACloserMatchToImplIsRequested() {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final DefaultBeanLoader loader = new DefaultBeanLoader(beanDataHandler, beanLocator);

        final Set<Dependency> beans = new HashSet<Dependency>();
        beans.add(new Dependency(FirstInterfaceImpl.class));
        beans.add(new Dependency(SecondInterfaceImpl.class));

        when(beanLocator.findBeans()).thenReturn(beans);

        loader.loadBeans();

        final ACloserMatchOfImplementationUser aCloserMatch = new ACloserMatchOfImplementationUser();
        loader.autowire(aCloserMatch);

        assertTrue(aCloserMatch.getFirstInterfaceImplInterface() instanceof TheInterface);
        assertTrue(aCloserMatch.getSecondInterfaceImpl() instanceof TheInterface);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noMatchesForADependencyShouldBeDetected() {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final DefaultBeanLoader loader = new DefaultBeanLoader(beanDataHandler, beanLocator);

        final Set<Dependency> beans = new HashSet<Dependency>();
        beans.add(new Dependency(TheInterfaceUser.class));

        when(beanLocator.findBeans()).thenReturn(beans);

        loader.loadBeans();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfQualifierIsUsedOnBeanWithoutQualifier() {
        beanLoader.loadBeans();

        beanLoader.getBean(RainbowBean.class, "pink");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfWrongQualifierIsUsedOnExactClass() {
        beanLoader.loadBeans();

        beanLoader.getBean(YellowBean.class, "brown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfWrongQualifierIsUsedOnInterface() {
        beanLoader.loadBeans();

        beanLoader.getBean(ColorBean.class, "brown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfNoQualifierIsUsedOnInterface() {
        beanLoader.loadBeans();

        beanLoader.getBean(ColorBean.class);
    }
}
