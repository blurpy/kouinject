
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

import static net.usikkert.kouinject.testbeans.scanned.profile.Profiles.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.factory.AnnotationBasedFactoryPointHandler;
import net.usikkert.kouinject.factory.FactoryPointHandler;
import net.usikkert.kouinject.generics.TypeLiteral;
import net.usikkert.kouinject.profile.AnnotationBasedProfileHandler;
import net.usikkert.kouinject.profile.InputBasedProfileLocator;
import net.usikkert.kouinject.profile.ProfileHandler;
import net.usikkert.kouinject.profile.ProfileLocator;
import net.usikkert.kouinject.testbeans.BeanCount;
import net.usikkert.kouinject.testbeans.notscanned.ACloserMatchOfImplementationUser;
import net.usikkert.kouinject.testbeans.notscanned.FirstCircularBean;
import net.usikkert.kouinject.testbeans.notscanned.FirstInterfaceImpl;
import net.usikkert.kouinject.testbeans.notscanned.SecondCircularBean;
import net.usikkert.kouinject.testbeans.notscanned.SecondInterfaceImpl;
import net.usikkert.kouinject.testbeans.notscanned.TheInterface;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.notscanned.collection.CollectionInjectionWithNoMatchingBeans;
import net.usikkert.kouinject.testbeans.notscanned.collection.CollectionInjectionWithWildcard;
import net.usikkert.kouinject.testbeans.notscanned.collection.CollectionInjectionWithoutTypeArgument;
import net.usikkert.kouinject.testbeans.notscanned.collection.ListInjection;
import net.usikkert.kouinject.testbeans.notscanned.collection.SetInjection;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.CollectionProviderInjectionWithNoMatchingBeans;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.CollectionProviderInjectionWithWildcard;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.CollectionProviderInjectionWithoutTypeArgument;
import net.usikkert.kouinject.testbeans.notscanned.generics.circular.ActualCircularFactoryBean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance1Bean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance2Bean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance3Bean;
import net.usikkert.kouinject.testbeans.notscanned.mock.MockFactoryBean;
import net.usikkert.kouinject.testbeans.notscanned.provider.ProviderInjectionWithNoMatchingBeans;
import net.usikkert.kouinject.testbeans.notscanned.provider.ProviderInjectionWithWildcard;
import net.usikkert.kouinject.testbeans.notscanned.provider.ProviderInjectionWithoutTypeArgument;
import net.usikkert.kouinject.testbeans.scanned.BlueCarBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.RainbowBean;
import net.usikkert.kouinject.testbeans.scanned.array.ArrayClass;
import net.usikkert.kouinject.testbeans.scanned.array.SingletonArray;
import net.usikkert.kouinject.testbeans.scanned.collection.AppleBean;
import net.usikkert.kouinject.testbeans.scanned.collection.BananaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.CheeseBean;
import net.usikkert.kouinject.testbeans.scanned.collection.FishBean;
import net.usikkert.kouinject.testbeans.scanned.collection.Food;
import net.usikkert.kouinject.testbeans.scanned.collection.HamburgerBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HotDogBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.ProvidedHungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.collectionprovider.SingletonCollectionProviderBean;
import net.usikkert.kouinject.testbeans.scanned.component.SwingBean;
import net.usikkert.kouinject.testbeans.scanned.factory.CdRecorderBean;
import net.usikkert.kouinject.testbeans.scanned.factory.FactoryAndStandaloneBean;
import net.usikkert.kouinject.testbeans.scanned.factory.MiscQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.factory.OverriddenFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.RecorderBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SimpleFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.SingletonFactoryCreatedBean;
import net.usikkert.kouinject.testbeans.scanned.factory.TapeRecorderBean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder1.Folder1Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder2.Folder2Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder3.Folder3Bean;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Pentagon;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Shape;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Square;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Star;
import net.usikkert.kouinject.testbeans.scanned.generics.circular.Triangle;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.Dao;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.DaoControllerBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.DatabaseDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.ItemDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.MySqlDriver;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.OrderDaoBean;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Genre;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Horror;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.Movie;
import net.usikkert.kouinject.testbeans.scanned.generics.qualifier.factory.MovieCollectionBean;
import net.usikkert.kouinject.testbeans.scanned.generics.stuff.OneStuffBean;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.GenericBox;
import net.usikkert.kouinject.testbeans.scanned.generics.typevariable.Liquid;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.DevelopmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.EnvironmentBean;
import net.usikkert.kouinject.testbeans.scanned.profile.JndiDataSourceBean;
import net.usikkert.kouinject.testbeans.scanned.profile.LocalArchiveBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileABean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileACBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileBBean;
import net.usikkert.kouinject.testbeans.scanned.profile.ProfileCBean;
import net.usikkert.kouinject.testbeans.scanned.profile.RemoteArchiveBean;
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
 * Test of {@link DefaultBeanLoader}.
 *
 * @author Christian Ihle
 */
public class DefaultBeanLoaderTest {

    private DefaultBeanLoader beanLoader;

    @Before
    public void setupBeanLoader() {
        beanLoader = createBeanLoaderWithBasePackages("net.usikkert.kouinject.testbeans.scanned");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldDetectMissingDependencies() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(TheInterfaceUser.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(TheInterfaceUser.class);
    }

    @Test
    public void beanLoaderShouldHandleMocks() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(FieldBean.class));
        beans.add(new BeanKey(MockFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final FieldBean fieldBean = loader.getBean(FieldBean.class);
        assertNotNull(fieldBean);
        assertNotNull(fieldBean.getHelloBean());
        assertNotNull(fieldBean.getAbstractBean());
        assertNotNull(fieldBean.getInterfaceBean());

        final HelloBean helloBean = loader.getBean(HelloBean.class);
        assertNotNull(helloBean);
        assertTrue(helloBean.toString().contains("Mock for HelloBean"));

        final AbstractBeanImpl abstractBean = loader.getBean(AbstractBeanImpl.class);
        assertNotNull(abstractBean);
        assertTrue(abstractBean.toString().contains("Mock for AbstractBeanImpl"));

        final InterfaceBean interfaceBean = loader.getBean(InterfaceBean.class);
        assertNotNull(interfaceBean);
        assertTrue(interfaceBean.toString().contains("Mock for InterfaceBean"));
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependenciesShouldBeDetected() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(FirstCircularBean.class));
        beans.add(new BeanKey(SecondCircularBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(FirstCircularBean.class);
    }

    @Test(expected = IllegalStateException.class)
    public void tooManyMatchesForADependencyShouldBeDetected() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(FirstInterfaceImpl.class));
        beans.add(new BeanKey(SecondInterfaceImpl.class));
        beans.add(new BeanKey(TheInterfaceUser.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(TheInterfaceUser.class);
    }

    @Test
    public void severalBeansForAnInterfaceIsOKIfACloserMatchToImplIsRequested() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(FirstInterfaceImpl.class));
        beans.add(new BeanKey(SecondInterfaceImpl.class));
        beans.add(new BeanKey(ACloserMatchOfImplementationUser.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final ACloserMatchOfImplementationUser aCloserMatch = loader.getBean(ACloserMatchOfImplementationUser.class);

        assertTrue(aCloserMatch.getFirstInterfaceImplInterface() instanceof TheInterface);
        assertTrue(aCloserMatch.getSecondInterfaceImpl() instanceof TheInterface);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noMatchesForADependencyShouldBeDetected() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(TheInterfaceUser.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(TheInterfaceUser.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfQualifierIsUsedOnBeanWithoutQualifier() {
        beanLoader.getBean(RainbowBean.class, "pink");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfWrongQualifierIsUsedOnExactClass() {
        beanLoader.getBean(YellowBean.class, "brown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfWrongQualifierIsUsedOnInterface() {
        beanLoader.getBean(ColorBean.class, "brown");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailIfNoQualifierIsUsedOnInterface() {
        beanLoader.getBean(ColorBean.class);
    }

    @Test
    public void getBeanShouldReturnUniqueInstancesForPrototypeScopedBeans() {
        final HelloBean helloBean1 = beanLoader.getBean(HelloBean.class);
        final HelloBean helloBean2 = beanLoader.getBean(HelloBean.class);

        assertNotSame(helloBean1, helloBean2);
    }

    @Test
    public void getBeanShouldReturnTheSameInstanceForSingletonScopedBeans() {
        final SingletonBean singletonBean1 = beanLoader.getBean(SingletonBean.class);
        final SingletonBean singletonBean2 = beanLoader.getBean(SingletonBean.class);

        assertSame(singletonBean1, singletonBean2);
    }

    @Test
    public void getBeanShouldReturnTheSameInstanceForSingletonScopedBeansWhenUsingAny() {
        final SingletonBean singletonBean1 = beanLoader.getBean(SingletonBean.class);
        final SingletonBean singletonBean2 = beanLoader.getBean(SingletonBean.class, "any");

        assertSame(singletonBean1, singletonBean2);
    }

    @Test
    public void getBeanShouldReturnTheSameInstanceForQualifiedSingletonScopedBeans1() {
        final PinkSingletonBean singletonBean1 = beanLoader.getBean(PinkSingletonBean.class);
        final PinkSingletonBean singletonBean2 = beanLoader.getBean(PinkSingletonBean.class, "pink");
        final PinkSingletonBean singletonBean3 = beanLoader.getBean(PinkSingletonBean.class, "any");

        assertSame(singletonBean1, singletonBean2);
        assertSame(singletonBean2, singletonBean3);
    }

    @Test
    public void getBeanShouldReturnTheSameInstanceForQualifiedSingletonScopedBeans2() {
        final PinkSingletonBean singletonBean1 = beanLoader.getBean(PinkSingletonBean.class, "pink");
        final PinkSingletonBean singletonBean2 = beanLoader.getBean(PinkSingletonBean.class);
        final PinkSingletonBean singletonBean3 = beanLoader.getBean(PinkSingletonBean.class, "any");

        assertSame(singletonBean1, singletonBean2);
        assertSame(singletonBean2, singletonBean3);
    }

    @Test
    public void getBeanShouldReturnTheSameInstanceForQualifiedSingletonScopedBeans3() {
        final PinkSingletonBean singletonBean1 = beanLoader.getBean(PinkSingletonBean.class, "any");
        final PinkSingletonBean singletonBean2 = beanLoader.getBean(PinkSingletonBean.class, "pink");
        final PinkSingletonBean singletonBean3 = beanLoader.getBean(PinkSingletonBean.class);

        assertSame(singletonBean1, singletonBean2);
        assertSame(singletonBean2, singletonBean3);
    }

    @Test
    public void getBeanShouldHandleDifferentScopeAndQualifierWithInheritanceAtTheSameTime() {
        final Layer firstLayer1 = beanLoader.getBean(Layer.class, "first");
        final Layer firstLayer2 = beanLoader.getBean(FirstLayerBean.class);
        final Layer firstLayer3 = beanLoader.getBean(FirstLayerBean.class, "first");
        assertSame(firstLayer1, firstLayer2);
        assertSame(firstLayer2, firstLayer3);
        assertEquals(FirstLayerBean.class, firstLayer1.getClass());
        assertEquals(FirstLayerBean.class, firstLayer2.getClass());
        assertEquals(FirstLayerBean.class, firstLayer3.getClass());

        final Layer thirdLayer1 = beanLoader.getBean(Layer.class, "third");
        final Layer thirdLayer2 = beanLoader.getBean(FirstLayerBean.class, "third");
        final Layer thirdLayer3 = beanLoader.getBean(SecondLayerBean.class, "third");
        final Layer thirdLayer4 = beanLoader.getBean(ThirdLayerBean.class);
        final Layer thirdLayer5 = beanLoader.getBean(ThirdLayerBean.class, "third");
        assertNotSame(thirdLayer1, thirdLayer2);
        assertNotSame(thirdLayer2, thirdLayer3);
        assertNotSame(thirdLayer3, thirdLayer4);
        assertNotSame(thirdLayer4, thirdLayer5);
        assertEquals(ThirdLayerBean.class, thirdLayer1.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer2.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer3.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer4.getClass());
        assertEquals(ThirdLayerBean.class, thirdLayer5.getClass());

        final Layer fifthLayer1 = beanLoader.getBean(FifthLayerBean.class);
        final Layer fifthLayer2 = beanLoader.getBean(Layer.class, "fifth");
        final Layer fifthLayer3 = beanLoader.getBean(FirstLayerBean.class, "fifth");
        final Layer fifthLayer4 = beanLoader.getBean(SecondLayerBean.class, "fifth");
        final Layer fifthLayer5 = beanLoader.getBean(ThirdLayerBean.class, "fifth");
        final Layer fifthLayer6 = beanLoader.getBean(FourthLayerBean.class, "fifth");
        final Layer fifthLayer7 = beanLoader.getBean(FifthLayerBean.class, "fifth");
        final Layer fifthLayer8 = beanLoader.getBean(FifthLayerBean.class, "any");
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
    public void getOnAProviderShouldReturnUniqueInstancesForPrototypeScopedBeans() {
        final ProviderBean providerBean = beanLoader.getBean(ProviderBean.class);
        final Provider<FieldBean> fieldBeanProvider = providerBean.getFieldBeanProvider();

        final FieldBean fieldBean1 = fieldBeanProvider.get();
        final FieldBean fieldBean2 = fieldBeanProvider.get();

        assertNotSame(fieldBean1, fieldBean2);
    }

    @Test
    public void getOnAProviderShouldReturnTheSameInstanceForSingletonScopedBeans() {
        final SingletonProviderBean singletonProviderBean = beanLoader.getBean(SingletonProviderBean.class);
        final Provider<SingletonBean> singletonBeanProvider = singletonProviderBean.getSingletonBeanProvider();

        final SingletonBean singletonBean1 = singletonBeanProvider.get();
        final SingletonBean singletonBean2 = singletonBeanProvider.get();

        assertSame(singletonBean1, singletonBean2);
    }

    @Test
    public void prototypeBeanShouldSupportSingletonDependencies() {
        final PrototypeWithSingletonBean prototypeBean1 = beanLoader.getBean(PrototypeWithSingletonBean.class);
        final PrototypeWithSingletonBean prototypeBean2 = beanLoader.getBean(PrototypeWithSingletonBean.class);

        assertNotSame(prototypeBean1, prototypeBean2);

        assertNotNull(prototypeBean1.getSingletonBean1());
        assertNotNull(prototypeBean1.getSingletonBean2());
        assertSame(prototypeBean1.getSingletonBean1(), prototypeBean1.getSingletonBean2());

        assertNotNull(prototypeBean2.getSingletonBean1());
        assertNotNull(prototypeBean2.getSingletonBean2());
        assertSame(prototypeBean2.getSingletonBean1(), prototypeBean2.getSingletonBean2());

        assertSame(prototypeBean1.getSingletonBean1(), prototypeBean2.getSingletonBean2());
    }

    @Test
    public void singletonBeanShouldSupportPrototypeDependencies() {
        final SingletonWithPrototypeBean singletonBean1 = beanLoader.getBean(SingletonWithPrototypeBean.class);
        final SingletonWithPrototypeBean singletonBean2 = beanLoader.getBean(SingletonWithPrototypeBean.class);

        assertSame(singletonBean1, singletonBean2);

        assertNotNull(singletonBean1.getHelloBean1());
        assertNotNull(singletonBean1.getHelloBean2());
        assertNotSame(singletonBean1.getHelloBean1(), singletonBean1.getHelloBean2());
    }

    @Test
    public void prototypeBeansShouldNotBeInstantiatedMoreThanOnceForEachRequest() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(Instance1Bean.class));
        beans.add(new BeanKey(Instance2Bean.class));
        beans.add(new BeanKey(Instance3Bean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final Instance3Bean instance3Bean = loader.getBean(Instance3Bean.class);
        assertNotNull(instance3Bean);

        final Instance2Bean instance2Bean = instance3Bean.getInstance2Bean();
        assertNotNull(instance2Bean);

        final Instance1Bean instance1Bean = instance2Bean.getInstance1Bean();
        assertNotNull(instance1Bean);

        assertEquals(1, Instance1Bean.getInstanceCounter());
        assertEquals(1, Instance2Bean.getInstanceCounter());
        assertEquals(1, Instance3Bean.getInstanceCounter());

        loader.getBean(Instance3Bean.class);

        assertEquals(2, Instance1Bean.getInstanceCounter());
        assertEquals(2, Instance2Bean.getInstanceCounter());
        assertEquals(2, Instance3Bean.getInstanceCounter());
    }

    @Test
    public void getBeanWithBeanWithoutQualifierAndAnyQualifierShouldFindMatch() {
        final HelloBean bean = beanLoader.getBean(HelloBean.class, "any");

        assertNotNull(bean);
    }

    @Test
    public void getBeanWithBeanWithQualifierAndAnyQualifierShouldFindMatch() {
        final BlueBean bean = beanLoader.getBean(BlueBean.class, "any");

        assertNotNull(bean);
    }

    @Test
    public void getBeansWithNullQualifierShouldReturnOnlyBeansWithoutQualifier() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, null);

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED_WITHOUT_QUALIFIER.getNumberOfBeans(), beans.size());

        assertFalse(containsBean(BlueBean.class, beans)); // Bean with qualifier
        assertTrue(containsBean(HelloBean.class, beans)); // Bean without qualifier
    }

    @Test
    public void getBeansWithoutQualifierParameterShouldReturnOnlyBeansWithoutQualifier() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class);

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED_WITHOUT_QUALIFIER.getNumberOfBeans(), beans.size());

        assertFalse(containsBean(BlueBean.class, beans)); // Bean with qualifier
        assertTrue(containsBean(HelloBean.class, beans)); // Bean without qualifier
    }

    @Test
    public void getBeansWithObjectAndGreenQualifierShouldGiveOnlyBeansWithGreenQualifier() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "blue");

        assertNotNull(beans);
        assertEquals(2, beans.size());

        assertTrue(containsBean(BlueBean.class, beans));
        assertTrue(containsBean(BlueCarBean.class, beans));
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldReturnAllBeans() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());

        assertTrue(containsBean(BlueBean.class, beans)); // Bean with qualifier
        assertTrue(containsBean(HelloBean.class, beans)); // Bean without qualifier
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldReturnArrayBeans() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());

        assertTrue(containsBean(ArrayClass[].class, beans));
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldReturnBeansFromFactories() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());

        assertTrue(containsBean(OrangeBean.class, beans)); // Bean with qualifier created with factory
        assertTrue(containsBean(SimpleFactoryCreatedBean.class, beans)); // Bean without qualifier created with factory

        final OrangeBean orangeBean = getBean(OrangeBean.class, beans);
        assertTrue(orangeBean.isCreatedByFactory());

        final SimpleFactoryCreatedBean simpleFactoryCreatedBean = getBean(SimpleFactoryCreatedBean.class, beans);
        assertTrue(simpleFactoryCreatedBean.isCreatedByFactory());
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldReturnBeansWithActiveProfiles() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PROFILE_A.value(), PROFILE_B.value(), PROFILE_C.value(), DEVELOPMENT.value(), JNDI.value(), SWING.value()));

        final Collection<Object> beans = loader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED_WITH_PROFILED.getNumberOfBeans(), beans.size());

        assertTrue(containsBean(ProfileABean.class, beans));
        assertTrue(containsBean(ProfileBBean.class, beans));
        assertTrue(containsBean(ProfileCBean.class, beans));
        assertTrue(containsBean(ProfileACBean.class, beans));
        assertTrue(containsBean(DevelopmentBean.class, beans));
        assertTrue(containsBean(JndiDataSourceBean.class, beans));
        assertTrue(containsBean(SwingBean.class, beans));
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldReturnGenericBeans() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());

        boolean foundListOfOneStuffBean = false;

        // A bit more complicated than I had hoped for, but "bean instanceof List<OneStuffBean>" does not work.
        for (final Object bean : beans) {
            if (bean instanceof List<?>) {
                final List<?> listBean = (List<?>) bean;

                if (listBean.size() == 1) {
                    final Object firstListBeanObject = listBean.get(0);

                    if (firstListBeanObject instanceof OneStuffBean) {
                        foundListOfOneStuffBean = true;
                        break;
                    }
                }
            }
        }

        assertTrue(foundListOfOneStuffBean);
    }

    @Test
    public void getBeansWithObjectAndAnyQualifierShouldNotReturnBeansFromFactoryWithAnyQualifier() {
        final Collection<Object> beans = beanLoader.getBeans(Object.class, "any");

        assertNotNull(beans);
        assertEquals(BeanCount.SCANNED.getNumberOfBeans(), beans.size());

        for (final Object bean : beans) {
            assertFalse(bean.getClass().equals(String.class));
            assertFalse(bean.getClass().equals(Integer.class));
        }
    }

    @Test
    public void getBeansWithColorBeanAndAnyQualifierShouldReturnAllColorBeans() {
        final Collection<ColorBean> beans = beanLoader.getBeans(ColorBean.class, "any");

        assertNotNull(beans);
        assertEquals(6, beans.size());

        assertTrue(containsBean(BlueBean.class, beans));
        assertTrue(containsBean(GreenBean.class, beans));
        assertTrue(containsBean(RedBean.class, beans));
        assertTrue(containsBean(YellowBean.class, beans));
        assertTrue(containsBean(DarkYellowBean.class, beans));
        assertTrue(containsBean(OrangeBean.class, beans));

        final OrangeBean orangeBean = getBean(OrangeBean.class, beans);
        assertTrue(orangeBean.isCreatedByFactory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeansWithNoMatchesShouldThrowException() {
        beanLoader.getBeans(Object.class, "nomatch");
    }

    @Test
    public void injectionOfCollectionWithWildcardShouldWork() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionInjectionWithWildcard.class));
        beans.add(new BeanKey(HelloBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final CollectionInjectionWithWildcard bean = loader.getBean(CollectionInjectionWithWildcard.class);
        assertNotNull(bean);

        final Collection<? extends HelloBean> wildcardCollection = bean.getWildcardCollection();
        assertNotNull(wildcardCollection);
        assertEquals(1, wildcardCollection.size());

        final HelloBean helloBean = wildcardCollection.iterator().next();
        assertNotNull(helloBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionWithoutTypeArgumentShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionInjectionWithoutTypeArgument.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test
    public void injectionOfProviderWithWildcardShouldWork() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ProviderInjectionWithWildcard.class));
        beans.add(new BeanKey(HelloBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final ProviderInjectionWithWildcard bean = loader.getBean(ProviderInjectionWithWildcard.class);
        assertNotNull(bean);

        final Provider<? extends HelloBean> wildcardProvider = bean.getWildcardProvider();
        assertNotNull(wildcardProvider);

        final HelloBean helloBean = wildcardProvider.get();
        assertNotNull(helloBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfProviderWithoutTypeArgumentShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ProviderInjectionWithoutTypeArgument.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test
    public void injectionOfCollectionProviderWithWildcardShouldWork() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionProviderInjectionWithWildcard.class));
        beans.add(new BeanKey(HelloBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final CollectionProviderInjectionWithWildcard bean = loader.getBean(CollectionProviderInjectionWithWildcard.class);
        assertNotNull(bean);

        final CollectionProvider<? extends HelloBean> wildcardCollectionProvider = bean.getWildcardCollectionProvider();
        assertNotNull(wildcardCollectionProvider);

        final Collection<? extends HelloBean> helloBeans = wildcardCollectionProvider.get();
        assertNotNull(helloBeans);
        assertEquals(1, helloBeans.size());

        final HelloBean helloBean = helloBeans.iterator().next();
        assertNotNull(helloBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionProviderWithoutTypeArgumentShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionProviderInjectionWithoutTypeArgument.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfListShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ListInjection.class));
        beans.add(new BeanKey(HelloBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(ListInjection.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfSetShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(SetInjection.class));
        beans.add(new BeanKey(HelloBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(SetInjection.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionWithNoMatchingBeansShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionInjectionWithNoMatchingBeans.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(CollectionInjectionWithNoMatchingBeans.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionProviderWithNoMatchingBeansShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionProviderInjectionWithNoMatchingBeans.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final CollectionProviderInjectionWithNoMatchingBeans collectionProviderBean =
                loader.getBean(CollectionProviderInjectionWithNoMatchingBeans.class);
        final CollectionProvider<TheInterface> theInterfaceCollectionProvider =
                collectionProviderBean.getTheInterfaceCollectionProvider();

        // Fails at a later stage due to lazy-loading
        theInterfaceCollectionProvider.get();
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfProviderWithNoMatchingBeansShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ProviderInjectionWithNoMatchingBeans.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final ProviderInjectionWithNoMatchingBeans providerBean = loader.getBean(ProviderInjectionWithNoMatchingBeans.class);
        final Provider<TheInterface> theInterfaceProvider = providerBean.getTheInterfaceProvider();

        // Fails at a later stage due to lazy-loading
        theInterfaceProvider.get();
    }

    @Test
    public void findBeansShouldNotCareAboutOrderOfBasePackages() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder.folder3",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder2",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder1");

        final Collection<Object> beans = newBeanLoader.getBeans(Object.class);

        assertEquals(3, beans.size());

        assertTrue(containsBean(Folder1Bean.class, beans));
        assertTrue(containsBean(Folder2Bean.class, beans));
        assertTrue(containsBean(Folder3Bean.class, beans));
    }

    @Test
    public void findBeansShouldNotCareAboutOrderOfBasePackages2() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder.folder1",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder2",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder3");

        final Collection<Object> beans = newBeanLoader.getBeans(Object.class);

        assertEquals(3, beans.size());

        assertTrue(containsBean(Folder1Bean.class, beans));
        assertTrue(containsBean(Folder2Bean.class, beans));
        assertTrue(containsBean(Folder3Bean.class, beans));
    }

    // Should be same as specifying package 1-3, like above.
    @Test
    public void findBeansWithParentPackageShouldGiveSameResultAsSpecifyingEachSubPackage() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder");

        final Collection<Object> beans = newBeanLoader.getBeans(Object.class);

        assertEquals(3, beans.size());

        assertTrue(containsBean(Folder1Bean.class, beans));
        assertTrue(containsBean(Folder2Bean.class, beans));
        assertTrue(containsBean(Folder3Bean.class, beans));
    }

    @Test
    public void findBeansShouldHandleOnlyOneSubPackage() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder.folder2");

        final Collection<Object> beans = newBeanLoader.getBeans(Object.class);

        assertEquals(1, beans.size());
        assertTrue(containsBean(Folder2Bean.class, beans));
    }

    @Test
    public void findBeansShouldSupportDifferentParentPackages() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder.folder2",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder1",
                "net.usikkert.kouinject.testbeans.scanned.qualifier",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder3",
                "net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean",
                "net.usikkert.kouinject.testbeans.scanned.empty");

        final Collection<Object> beans = newBeanLoader.getBeans(Object.class, "any");

        assertEquals(9, beans.size());

        assertTrue(containsBean(Folder1Bean.class, beans));
        assertTrue(containsBean(Folder2Bean.class, beans));
        assertTrue(containsBean(Folder3Bean.class, beans));

        assertTrue(containsBean(AbstractBeanImpl.class, beans));

        assertTrue(containsBean(BlueBean.class, beans));
        assertTrue(containsBean(DarkYellowBean.class, beans));
        assertTrue(containsBean(GreenBean.class, beans));
        assertTrue(containsBean(RedBean.class, beans));
        assertTrue(containsBean(YellowBean.class, beans));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBeansShouldFailIfUnsatisfiedDependencies() {
        final BeanLoader newBeanLoader = createBeanLoaderWithBasePackages(
                "net.usikkert.kouinject.testbeans.scanned.folder.folder3",
                "net.usikkert.kouinject.testbeans.scanned.folder.folder1");

        newBeanLoader.getBeans(Object.class);
    }

    @Test
    public void collectionProviderShouldHandleScopeAndQualifierAtTheSameTime() {
        final ProvidedHungryQualifierBean providedHungryQualifierBean = beanLoader.getBean(ProvidedHungryQualifierBean.class);
        assertNotNull(providedHungryQualifierBean);

        final CollectionProvider<Food> fastFoodBeansProvider = providedHungryQualifierBean.getFastFoodBeans();
        assertNotNull(fastFoodBeansProvider);

        // Getting beans first time
        final Collection<Food> fastFoodBeans1 = fastFoodBeansProvider.get();
        assertNotNull(fastFoodBeans1);
        assertEquals(2, fastFoodBeans1.size());

        final HotDogBean hotDogBean1 = getBean(HotDogBean.class, fastFoodBeans1);
        assertNotNull(hotDogBean1);
        final HamburgerBean hamburgerBean1 = getBean(HamburgerBean.class, fastFoodBeans1);
        assertNotNull(hamburgerBean1);

        // Getting beans second time
        final Collection<Food> fastFoodBeans2 = fastFoodBeansProvider.get();
        assertNotNull(fastFoodBeans2);
        assertEquals(2, fastFoodBeans2.size());

        final HotDogBean hotDogBean2 = getBean(HotDogBean.class, fastFoodBeans2);
        assertNotNull(hotDogBean2);
        final HamburgerBean hamburgerBean2 = getBean(HamburgerBean.class, fastFoodBeans2);
        assertNotNull(hamburgerBean2);

        // HotDog is singleton
        assertSame(hotDogBean1, hotDogBean2);

        // Hamburger is prototype
        assertNotSame(hamburgerBean1, hamburgerBean2);
    }

    @Test
    public void collectionProviderShouldHandleScopeInUnqualifiedBeans() {
        final ProvidedHungryBean providedHungryBean = beanLoader.getBean(ProvidedHungryBean.class);
        assertNotNull(providedHungryBean);

        final CollectionProvider<Food> foodBeansInFieldProvider = providedHungryBean.getFoodBeansInField();
        assertNotNull(foodBeansInFieldProvider);

        // Getting beans first time
        final Collection<Food> foodBeansInField1 = foodBeansInFieldProvider.get();
        assertNotNull(foodBeansInField1);
        assertEquals(3, foodBeansInField1.size());

        final CheeseBean cheeseBean1 = getBean(CheeseBean.class, foodBeansInField1);
        assertNotNull(cheeseBean1);
        final FishBean fishBean1 = getBean(FishBean.class, foodBeansInField1);
        assertNotNull(fishBean1);
        final BananaBean bananaBean1 = getBean(BananaBean.class, foodBeansInField1);
        assertNotNull(bananaBean1);

        // Getting beans second time
        final Collection<Food> foodBeansInField2 = foodBeansInFieldProvider.get();
        assertNotNull(foodBeansInField2);
        assertEquals(3, foodBeansInField2.size());

        final CheeseBean cheeseBean2 = getBean(CheeseBean.class, foodBeansInField2);
        assertNotNull(cheeseBean2);
        final FishBean fishBean2 = getBean(FishBean.class, foodBeansInField2);
        assertNotNull(fishBean2);
        final BananaBean bananaBean2 = getBean(BananaBean.class, foodBeansInField2);
        assertNotNull(bananaBean2);

        // Cheese and fish are prototype
        assertNotSame(cheeseBean1, cheeseBean2);
        assertNotSame(fishBean1, fishBean2);

        // Banana is singleton
        assertSame(bananaBean1, bananaBean2);
    }

    @Test
    public void collectionProviderShouldHandleScopeAndQualifierAtTheSameTimeInInnerInjections() {
        final ProvidedHungryQualifierBean providedHungryQualifierBean = beanLoader.getBean(ProvidedHungryQualifierBean.class);
        assertNotNull(providedHungryQualifierBean);

        final CollectionProvider<Food> allFoodBeansProvider = providedHungryQualifierBean.getAllFoodBeans();
        assertNotNull(allFoodBeansProvider);

        // Getting beans first time
        final Collection<Food> allFoodBeans1 = allFoodBeansProvider.get();
        assertNotNull(allFoodBeans1);

        final AppleBean appleBean1 = getBean(AppleBean.class, allFoodBeans1);
        assertNotNull(appleBean1);
        final SingletonBean singletonBean1 = appleBean1.getSingletonBean();
        assertNotNull(singletonBean1);

        final CheeseBean cheeseBean1 = getBean(CheeseBean.class, allFoodBeans1);
        assertNotNull(cheeseBean1);
        final ColorBean redBean1 = cheeseBean1.getRedBean();
        assertNotNull(redBean1);
        assertEquals(RedBean.class, redBean1.getClass());

        // Getting beans second time
        final Collection<Food> allFoodBeans2 = allFoodBeansProvider.get();
        assertNotNull(allFoodBeans2);

        final AppleBean appleBean2 = getBean(AppleBean.class, allFoodBeans2);
        assertNotNull(appleBean2);
        final SingletonBean singletonBean2 = appleBean2.getSingletonBean();
        assertNotNull(singletonBean2);

        final CheeseBean cheeseBean2 = getBean(CheeseBean.class, allFoodBeans2);
        assertNotNull(cheeseBean2);
        final ColorBean redBean2 = cheeseBean2.getRedBean();
        assertNotNull(redBean2);
        assertEquals(RedBean.class, redBean2.getClass());

        assertSame(singletonBean1, singletonBean2);
        assertNotSame(redBean1, redBean2);
    }

    @Test
    public void collectionShouldHandleScopeAndQualifierAtTheSameTime() {
        // Getting beans first time
        final HungryQualifierBean hungryQualifierBean1 = beanLoader.getBean(HungryQualifierBean.class);
        assertNotNull(hungryQualifierBean1);

        final Collection<Food> fastFoodBeans1 = hungryQualifierBean1.getFastFoodBeans();
        assertNotNull(fastFoodBeans1);
        assertEquals(2, fastFoodBeans1.size());

        final HotDogBean hotDogBean1 = getBean(HotDogBean.class, fastFoodBeans1);
        assertNotNull(hotDogBean1);
        final HamburgerBean hamburgerBean1 = getBean(HamburgerBean.class, fastFoodBeans1);
        assertNotNull(hamburgerBean1);

        // Getting beans second time
        final HungryQualifierBean hungryQualifierBean2 = beanLoader.getBean(HungryQualifierBean.class);
        assertNotNull(hungryQualifierBean2);

        final Collection<Food> fastFoodBeans2 = hungryQualifierBean2.getFastFoodBeans();
        assertNotNull(fastFoodBeans2);
        assertEquals(2, fastFoodBeans2.size());

        final HotDogBean hotDogBean2 = getBean(HotDogBean.class, fastFoodBeans2);
        assertNotNull(hotDogBean2);
        final HamburgerBean hamburgerBean2 = getBean(HamburgerBean.class, fastFoodBeans2);
        assertNotNull(hamburgerBean2);

        // HotDog is singleton
        assertSame(hotDogBean1, hotDogBean2);

        // Hamburger is prototype
        assertNotSame(hamburgerBean1, hamburgerBean2);
    }

    @Test
    public void collectionShouldHandleScopeInUnqualifiedBeans() {
        // Getting beans first time
        final HungryBean hungryBean1 = beanLoader.getBean(HungryBean.class);
        assertNotNull(hungryBean1);

        final Collection<Food> foodBeansInField1 = hungryBean1.getFoodBeansInField();
        assertNotNull(foodBeansInField1);
        assertEquals(3, foodBeansInField1.size());

        final CheeseBean cheeseBean1 = getBean(CheeseBean.class, foodBeansInField1);
        assertNotNull(cheeseBean1);
        final FishBean fishBean1 = getBean(FishBean.class, foodBeansInField1);
        assertNotNull(fishBean1);
        final BananaBean bananaBean1 = getBean(BananaBean.class, foodBeansInField1);
        assertNotNull(bananaBean1);

        // Getting beans second time
        final HungryBean hungryBean2 = beanLoader.getBean(HungryBean.class);
        assertNotNull(hungryBean2);

        final Collection<Food> foodBeansInField2 = hungryBean2.getFoodBeansInField();
        assertNotNull(foodBeansInField2);
        assertEquals(3, foodBeansInField2.size());

        final CheeseBean cheeseBean2 = getBean(CheeseBean.class, foodBeansInField2);
        assertNotNull(cheeseBean2);
        final FishBean fishBean2 = getBean(FishBean.class, foodBeansInField2);
        assertNotNull(fishBean2);
        final BananaBean bananaBean2 = getBean(BananaBean.class, foodBeansInField2);
        assertNotNull(bananaBean2);

        // Cheese and fish are prototype
        assertNotSame(cheeseBean1, cheeseBean2);
        assertNotSame(fishBean1, fishBean2);

        // Banana is singleton
        assertSame(bananaBean1, bananaBean2);
    }

    @Test
    public void collectionShouldHandleScopeAndQualifierAtTheSameTimeInInnerInjections() {
        // Getting beans first time
        final HungryQualifierBean hungryQualifierBean1 = beanLoader.getBean(HungryQualifierBean.class);
        assertNotNull(hungryQualifierBean1);

        final Collection<Food> allFoodBeans1 = hungryQualifierBean1.getAllFoodBeans();
        assertNotNull(allFoodBeans1);

        final AppleBean appleBean1 = getBean(AppleBean.class, allFoodBeans1);
        assertNotNull(appleBean1);
        final SingletonBean singletonBean1 = appleBean1.getSingletonBean();
        assertNotNull(singletonBean1);

        final CheeseBean cheeseBean1 = getBean(CheeseBean.class, allFoodBeans1);
        assertNotNull(cheeseBean1);
        final ColorBean redBean1 = cheeseBean1.getRedBean();
        assertNotNull(redBean1);
        assertEquals(RedBean.class, redBean1.getClass());

        // Getting beans second time
        final HungryQualifierBean hungryQualifierBean2 = beanLoader.getBean(HungryQualifierBean.class);
        assertNotNull(hungryQualifierBean2);

        final Collection<Food> allFoodBeans2 = hungryQualifierBean2.getAllFoodBeans();
        assertNotNull(allFoodBeans2);

        final AppleBean appleBean2 = getBean(AppleBean.class, allFoodBeans2);
        assertNotNull(appleBean2);
        final SingletonBean singletonBean2 = appleBean2.getSingletonBean();
        assertNotNull(singletonBean2);

        final CheeseBean cheeseBean2 = getBean(CheeseBean.class, allFoodBeans2);
        assertNotNull(cheeseBean2);
        final ColorBean redBean2 = cheeseBean2.getRedBean();
        assertNotNull(redBean2);
        assertEquals(RedBean.class, redBean2.getClass());

        assertSame(singletonBean1, singletonBean2);
        assertNotSame(redBean1, redBean2);
    }

    @Test
    public void singletonCollectionProviderShouldGiveOnlyOneInstance() {
        final SingletonCollectionProviderBean singletonCollectionProviderBean1 = beanLoader.getBean(SingletonCollectionProviderBean.class);
        assertNotNull(singletonCollectionProviderBean1);

        final SingletonCollectionProviderBean singletonCollectionProviderBean2 = beanLoader.getBean(SingletonCollectionProviderBean.class);
        assertNotNull(singletonCollectionProviderBean2);

        assertSame(singletonCollectionProviderBean1, singletonCollectionProviderBean2);
    }

    @Test
    public void singletonCollectionProviderShouldGiveUniqueInstancesOfInjectedPrototype() {
        final SingletonCollectionProviderBean singletonCollectionProviderBean = beanLoader.getBean(SingletonCollectionProviderBean.class);
        assertNotNull(singletonCollectionProviderBean);

        final CollectionProvider<CarBean> blueCarBeanCollectionProvider = singletonCollectionProviderBean.getBlueCarBeanCollectionProvider();
        assertNotNull(blueCarBeanCollectionProvider);

        // First car bean
        final Collection<CarBean> carBeans1 = blueCarBeanCollectionProvider.get();
        assertNotNull(carBeans1);
        assertEquals(1, carBeans1.size());
        final CarBean carBean1 = carBeans1.iterator().next();
        assertNotNull(carBean1);
        assertEquals(BlueCarBean.class, carBean1.getClass());

        // Second car bean
        final Collection<CarBean> carBeans2 = blueCarBeanCollectionProvider.get();
        assertNotNull(carBeans2);
        assertEquals(1, carBeans2.size());
        final CarBean carBean2 = carBeans2.iterator().next();
        assertNotNull(carBean2);
        assertEquals(BlueCarBean.class, carBean2.getClass());

        assertNotSame(carBean1, carBean2);
    }

    @Test
    public void prototypeScopedFactoryCreatedBeanShouldReturnUniqueInstances() {
        final SimpleFactoryCreatedBean bean1 = beanLoader.getBean(SimpleFactoryCreatedBean.class);
        assertNotNull(bean1);
        assertTrue(bean1.isCreatedByFactory());

        final SimpleFactoryCreatedBean bean2 = beanLoader.getBean(SimpleFactoryCreatedBean.class);
        assertNotNull(bean2);
        assertTrue(bean2.isCreatedByFactory());

        assertNotSame(bean1, bean2);
    }

    @Test
    public void singletonScopedFactoryCreatedBeanShouldReturnSameInstance() {
        final SingletonFactoryCreatedBean bean1 = beanLoader.getBean(SingletonFactoryCreatedBean.class);
        assertNotNull(bean1);
        assertTrue(bean1.isCreatedByFactory());

        final SingletonFactoryCreatedBean bean2 = beanLoader.getBean(SingletonFactoryCreatedBean.class);
        assertNotNull(bean2);
        assertTrue(bean2.isCreatedByFactory());

        assertSame(bean1, bean2);
    }

    @Test(expected = IllegalStateException.class)
    public void getBeanWithOneMatchOfImplementationFromFactoryAndOneMatchOfImplementationFromInjectorShouldFail() {
        beanLoader.getBean(RecorderBean.class, "any");
    }

    @Test
    public void getBeansWithOneMatchOfImplementationFromFactoryAndOneMatchOfImplementationFromInjectorShouldWork() {
        final Collection<RecorderBean> recorderBeans = beanLoader.getBeans(RecorderBean.class, "any");

        assertNotNull(recorderBeans);
        assertEquals(2, recorderBeans.size());
        assertTrue(containsBean(CdRecorderBean.class, recorderBeans));
        assertTrue(containsBean(TapeRecorderBean.class, recorderBeans));
    }

    @Test(expected = IllegalArgumentException.class)
    public void overriddenFactoryMethodShouldNotCreateBean() {
        beanLoader.getBean(OverriddenFactoryCreatedBean.class, "any");
    }

    @Test
    public void shouldGetStringsFromStringFactory() {
        final String someProperty = beanLoader.getBean(String.class, "some.property");
        assertEquals("This is some property", someProperty);

        final String color = beanLoader.getBean(String.class, "color");
        assertEquals("Pink", color);

        final String someThing = beanLoader.getBean(String.class, "some.thing");
        assertEquals("This is something else", someThing);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldGetNoMatchForCollectionOfStringsWithAnyInStringFactory() {
        beanLoader.getBeans(String.class, "any");
    }

    @Test
    public void shouldTreatAnyLikeNormalQualifierInStringFactory() {
        try {
            beanLoader.getBean(String.class, "any");
            fail("Should have failed");
        } catch (RuntimeException e) {
            // The factory throws an exception, but it could have returned some value instead
            assertEquals("Property 'any' was not found in FactoryTestProperties.properties",
                    e.getCause().getCause().getMessage());
        }
    }

    @Test
    public void shouldTreatNoQualifierLikeNormalInStringFactory() {
        try {
            beanLoader.getBean(String.class);
            fail("Should have failed");
        } catch (RuntimeException e) {
            // The factory throws an exception, but it could have returned some value instead
            assertEquals("Qualifier can not be null", e.getCause().getCause().getMessage());
        }
    }

    @Test
    public void shouldGetIntegersFromIntegerFactory() {
        final Integer someInteger = beanLoader.getBean(Integer.class, "some.integer");
        assertEquals(Integer.valueOf(123), someInteger);

        final Integer price = beanLoader.getBean(Integer.class, "price");
        assertEquals(Integer.valueOf(50), price);

        final Integer someOtherInteger = beanLoader.getBean(Integer.class, "some.other.integer");
        assertEquals(Integer.valueOf(987654321), someOtherInteger);
    }

    @Test
    public void shouldFindBothInstancesOfBeanThatIsBothStandaloneAndFactoryCreated() {
        final Collection<FactoryAndStandaloneBean> beans = beanLoader.getBeans(FactoryAndStandaloneBean.class);
        assertNotNull(beans);
        assertEquals(2, beans.size());

        boolean foundStandalone = false;
        boolean foundFactoryCreated = false;

        for (final FactoryAndStandaloneBean bean : beans) {
            if (bean.isCreatedByFactory()) {
                foundFactoryCreated = true;
            } else {
                foundStandalone = true;
            }
        }

        assertTrue(foundStandalone);
        assertTrue(foundFactoryCreated);
    }

    @Test
    public void shouldFindBothInstancesOfBeanThatIsCreatedTwiceByFactory() {
        final Collection<MiscQualifierBean> beans = beanLoader.getBeans(MiscQualifierBean.class);
        assertNotNull(beans);
        assertEquals(2, beans.size());

        boolean foundMilk = false;
        boolean foundCookie = false;

        for (final MiscQualifierBean bean : beans) {
            if (bean.getQualifier().equals("cookie")) {
                foundCookie = true;
            } else if (bean.getQualifier().equals("milk")) {
                foundMilk = true;
            }
        }

        assertTrue(foundMilk);
        assertTrue(foundCookie);
    }

    @Test
    public void getBeanShouldReturnBeanWithOneOfActiveProfiles() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PROFILE_A.value(), PROFILE_B.value(), PROFILE_C.value()));

        final ProfileABean profileABean = loader.getBean(ProfileABean.class);
        assertNotNull(profileABean);

        final ProfileBBean profileBBean = loader.getBean(ProfileBBean.class);
        assertNotNull(profileBBean);

        final ProfileCBean profileCBean = loader.getBean(ProfileCBean.class);
        assertNotNull(profileCBean);

        final ProfileACBean profileACBean = loader.getBean(ProfileACBean.class);
        assertNotNull(profileACBean);
    }

    @Test
    public void getBeanShouldReturnBeanWithActiveProfile() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PROFILE_A.value()));

        final ProfileABean profileABean = loader.getBean(ProfileABean.class);
        assertNotNull(profileABean);
    }

    @Test
    public void getBeanShouldReturnBeanWhereOneOfSeveralProfilesMatchWithActiveProfile() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PROFILE_C.value()));

        final ProfileACBean profileACBean = loader.getBean(ProfileACBean.class);
        assertNotNull(profileACBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldNotReturnProfiledBeanWhenNoProfilesAreActive1() {
        beanLoader.getBean(ProfileABean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldNotReturnProfiledBeanWhenNoProfilesAreActive2() {
        beanLoader.getBean(EnvironmentBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldNotReturnProfiledBeanWhenNoProfilesAreActiveWithCombinedComponentAndProfile() {
        beanLoader.getBean(SwingBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldNotReturnProfiledBeanWhenDifferentProfileIsActive() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PROFILE_B.value()));

        loader.getBean(ProfileABean.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getBeanShouldFailIfProfilesWithConflictingBeansAreActiveAtTheSameTime() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(DEVELOPMENT.value(), PRODUCTION.value()));

        loader.getBean(EnvironmentBean.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanShouldFailToGetBeanWithProfileFromSuperClass() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(DEVELOPMENT.value()));

        loader.getBean(RemoteArchiveBean.class); // Should not inherit development profile from LocalArchiveBean
    }

    @Test
    public void getBeanShouldReturnSubClassWithCorrectProfile() {
        final DefaultBeanLoader loader = createBeanLoaderWithBasePackagesAndProfiles(
                Arrays.asList("net.usikkert.kouinject.testbeans.scanned"),
                Arrays.asList(PRODUCTION.value()));

        // RemoteArchiveBean is the only bean matching LocalArchiveBean that is available with the production profile
        final LocalArchiveBean localArchiveBean = loader.getBean(LocalArchiveBean.class);
        assertNotNull(localArchiveBean);
        assertEquals(RemoteArchiveBean.class, localArchiveBean.getClass());
    }

    @Test
    public void getBeanWithGenericsShouldHandleSingletonScopeCorrect() {
        final Dao<MySqlDriver> bean1 = beanLoader.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "order");
        assertNotNull(bean1);

        final Dao<MySqlDriver> bean2 = beanLoader.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "order");
        assertNotNull(bean2);

        assertSame(bean1, bean2);
    }

    @Test
    public void getBeansWithGenericsShouldHandleSingletonScopeCorrect() {
        final Collection<Dao<MySqlDriver>> collection1 = beanLoader.getBeans(new TypeLiteral<Dao<MySqlDriver>>() {}, "any");
        assertNotNull(collection1);

        final Collection<Dao<MySqlDriver>> collection2 = beanLoader.getBeans(new TypeLiteral<Dao<MySqlDriver>>() {}, "any");
        assertNotNull(collection2);

        final OrderDaoBean bean1 = getBean(OrderDaoBean.class, collection1);
        assertNotNull(bean1);

        final OrderDaoBean bean2 = getBean(OrderDaoBean.class, collection2);
        assertNotNull(bean2);

        assertSame(bean1, bean2);
    }

    @Test
    public void getBeanWithGenericsShouldHandleSingletonScopeCorrectWhenUsingBothQualifierAndAny() {
        final DaoControllerBean bean = beanLoader.getBean(DaoControllerBean.class);
        assertNotNull(bean);

        final Dao<MySqlDriver> orderDao = bean.getOrderDao();
        assertNotNull(orderDao);
        assertEquals(OrderDaoBean.class, orderDao.getClass());

        final Collection<Dao<? extends DatabaseDriver>> allDaos = bean.getAllDaos();
        assertNotNull(allDaos);

        final OrderDaoBean orderDaoFromCollection = getBean(OrderDaoBean.class, allDaos);
        assertNotNull(orderDaoFromCollection);

        assertSame(orderDao, orderDaoFromCollection);
    }

    @Test
    public void getBeanWithGenericsShouldHandlePrototypeScopeCorrect() {
        final Dao<MySqlDriver> bean1 = beanLoader.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "item");
        assertNotNull(bean1);

        final Dao<MySqlDriver> bean2 = beanLoader.getBean(new TypeLiteral<Dao<MySqlDriver>>() {}, "item");
        assertNotNull(bean2);

        assertNotSame(bean1, bean2);
    }

    @Test
    public void getBeansWithGenericsShouldHandlePrototypeScopeCorrect() {
        final Collection<Dao<MySqlDriver>> collection1 = beanLoader.getBeans(new TypeLiteral<Dao<MySqlDriver>>() {}, "any");
        assertNotNull(collection1);

        final Collection<Dao<MySqlDriver>> collection2 = beanLoader.getBeans(new TypeLiteral<Dao<MySqlDriver>>() {}, "any");
        assertNotNull(collection2);

        final ItemDaoBean bean1 = getBean(ItemDaoBean.class, collection1);
        assertNotNull(bean1);

        final ItemDaoBean bean2 = getBean(ItemDaoBean.class, collection2);
        assertNotNull(bean2);

        assertNotSame(bean1, bean2);
    }

    @Test
    public void getBeanWithGenericsFromFactoryShouldHandleSingletonScopeCorrect() {
        final Movie<Horror> bean1 = beanLoader.getBean(new TypeLiteral<Movie<Horror>>() {}, "ScaryMovie");
        assertNotNull(bean1);

        final Movie<Horror> bean2 = beanLoader.getBean(new TypeLiteral<Movie<Horror>>() {}, "ScaryMovie");
        assertNotNull(bean2);

        assertSame(bean1, bean2);
    }

    @Test
    public void getBeansWithGenericsFromFactoryShouldHandleSingletonScopeCorrect() {
        final Collection<Movie<Horror>> collection1 = beanLoader.getBeans(new TypeLiteral<Movie<Horror>>() {}, "any");
        assertNotNull(collection1);

        final Collection<Movie<Horror>> collection2 = beanLoader.getBeans(new TypeLiteral<Movie<Horror>>() {}, "any");
        assertNotNull(collection2);

        final Movie<?> bean1 = getMovie("Scary Movie", collection1);
        assertNotNull(bean1);

        final Movie<?> bean2 = getMovie("Scary Movie", collection2);
        assertNotNull(bean2);

        assertSame(bean1, bean2);
    }

    @Test
    public void getBeanWithGenericsFromFactoryShouldHandleSingletonScopeCorrectWhenUsingBothQualifierAndAny() {
        final MovieCollectionBean bean = beanLoader.getBean(MovieCollectionBean.class);
        assertNotNull(bean);

        final Movie<Horror> scaryMovie = bean.getScaryMovie();
        assertNotNull(scaryMovie);
        assertEquals("Scary Movie", scaryMovie.getTitle());

        final Collection<Movie<? extends Genre>> allMovies = bean.getAllMovies();
        assertNotNull(allMovies);

        final Movie<?> scaryMovieFromCollection = getMovie("Scary Movie", allMovies);
        assertNotNull(scaryMovieFromCollection);

        assertSame(scaryMovie, scaryMovieFromCollection);
    }

    @Test
    public void getBeanWithGenericsFromFactoryShouldHandlePrototypeScopeCorrect() {
        final Movie<Horror> bean1 = beanLoader.getBean(new TypeLiteral<Movie<Horror>>() {}, "Scream");
        assertNotNull(bean1);

        final Movie<Horror> bean2 = beanLoader.getBean(new TypeLiteral<Movie<Horror>>() {}, "Scream");
        assertNotNull(bean2);

        assertNotSame(bean1, bean2);
    }

    @Test
    public void getBeansWithGenericsFromFactoryShouldHandlePrototypeScopeCorrect() {
        final Collection<Movie<Horror>> collection1 = beanLoader.getBeans(new TypeLiteral<Movie<Horror>>() {}, "any");
        assertNotNull(collection1);

        final Collection<Movie<Horror>> collection2 = beanLoader.getBeans(new TypeLiteral<Movie<Horror>>() {}, "any");
        assertNotNull(collection2);

        final Movie<?> bean1 = getMovie("Scream", collection1);
        assertNotNull(bean1);

        final Movie<?> bean2 = getMovie("Scream", collection2);
        assertNotNull(bean2);

        assertNotSame(bean1, bean2);
    }

    @Test
    public void getBeanWithArrayShouldHandlePrototypeScopeCorrect() {
        final ArrayClass[] simpleArrays1 = beanLoader.getBean(ArrayClass[].class, "FirstSimpleArray");
        assertNotNull(simpleArrays1);

        final ArrayClass[] simpleArrays2 = beanLoader.getBean(ArrayClass[].class, "FirstSimpleArray");
        assertNotNull(simpleArrays2);

        assertNotSame(simpleArrays1, simpleArrays2);
    }

    @Test
    public void getBeanWithArrayShouldHandleSingletonScopeCorrect() {
        final SingletonArray[] singletonArrays1 = beanLoader.getBean(SingletonArray[].class);
        assertNotNull(singletonArrays1);

        final SingletonArray[] singletonArrays2 = beanLoader.getBean(SingletonArray[].class);
        assertNotNull(singletonArrays2);

        assertSame(singletonArrays1, singletonArrays2);
    }

    @Test
    public void getBeanWithGenericArrayShouldHandlePrototypeScopeCorrect() {
        final GenericBox<Liquid[]> box1 = beanLoader.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "FantaArray");
        assertNotNull(box1);

        final GenericBox<Liquid[]> box2 = beanLoader.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "FantaArray");
        assertNotNull(box2);

        assertNotSame(box1, box2);
    }

    @Test
    public void getBeanWithGenericArrayShouldHandleSingletonScopeCorrect() {
        final GenericBox<Liquid[]> box1 = beanLoader.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "PepsiArray");
        assertNotNull(box1);

        final GenericBox<Liquid[]> box2 = beanLoader.getBean(new TypeLiteral<GenericBox<Liquid[]>>() {}, "PepsiArray");
        assertNotNull(box2);

        assertSame(box1, box2);
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeanInjectingItselfInFactory() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(new TypeLiteral<Shape<Square>>() {});
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeansInjectingEachOtherInFactory1() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(new TypeLiteral<Shape<Triangle>>() {});
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeansInjectingEachOtherInFactory2() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(new TypeLiteral<Shape<Star>>() {});
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeansWithQualifiersInjectingEachOtherInFactory1() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(new TypeLiteral<Shape<Pentagon>>() {}, "FirstPentagon");
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeansWithQualifiersInjectingEachOtherInFactory2() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBean(new TypeLiteral<Shape<Pentagon>>() {}, "SecondPentagon");
    }

    @Test(expected = IllegalStateException.class)
    public void circularDependencyShouldBeDetectedWithGenericBeansWithQualifiersInjectingEachOtherInFactoryInACollection() {
        final HashSet<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ActualCircularFactoryBean.class));

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        loader.getBeans(new TypeLiteral<Shape<Pentagon>>() {}, "any");
    }

    private Movie<?> getMovie(final String movieTitle, final Collection<? extends Movie<?>> movies) {
        for (final Movie<?> movie : movies) {
            if (movie.getTitle().equals(movieTitle)) {
                return movie;
            }
        }

        return null;
    }

    private DefaultBeanLoader createBeanLoaderWithBasePackages(final String... basePackages) {
        return createBeanLoaderWithBasePackagesAndProfiles(Arrays.asList(basePackages), new ArrayList<String>());
    }

    private DefaultBeanLoader createBeanLoaderWithBasePackagesAndProfiles(final List<String> basePackages, final List<String> profiles) {
        final ClassLocator classLocator = new ClassPathScanner();
        final ProfileLocator profileLocator = new InputBasedProfileLocator(profiles);
        final ProfileHandler profileHandler = new AnnotationBasedProfileHandler(profileLocator);
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(classLocator, profileHandler,
                basePackages.toArray(new String[basePackages.size()]));
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        final FactoryPointHandler factoryPointHandler = new AnnotationBasedFactoryPointHandler();

        return new DefaultBeanLoader(beanDataHandler, beanLocator, factoryPointHandler);
    }

    private DefaultBeanLoader createBeanLoaderWithBeans(final Set<BeanKey> beans) {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        when(beanLocator.findBeans()).thenReturn(beans);
        final FactoryPointHandler factoryPointHandler = new AnnotationBasedFactoryPointHandler();

        return new DefaultBeanLoader(beanDataHandler, beanLocator, factoryPointHandler);
    }

    private boolean containsBean(final Class<?> bean, final Collection<?> collection) {
        return getBean(bean, collection) != null;
    }

    private <T> T getBean(final Class<T> bean, final Collection<?> collection) {
        for (final Object object : collection) {
            if (bean.equals(object.getClass())) {
                return (T) object;
            }
        }

        return null;
    }
}
