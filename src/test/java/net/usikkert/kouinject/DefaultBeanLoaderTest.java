
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
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanKey;
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
import net.usikkert.kouinject.testbeans.notscanned.date.DateBean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance1Bean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance2Bean;
import net.usikkert.kouinject.testbeans.notscanned.instance.Instance3Bean;
import net.usikkert.kouinject.testbeans.notscanned.provider.ProviderInjectionWithWildcard;
import net.usikkert.kouinject.testbeans.notscanned.provider.ProviderInjectionWithoutTypeArgument;
import net.usikkert.kouinject.testbeans.scanned.BlueCarBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.RainbowBean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder1.Folder1Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder2.Folder2Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder3.Folder3Bean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
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

    @Test
    public void addBeanShouldMakeBeanAvailableButNotAutowire() {
        final NoBean noBean = new NoBean();
        beanLoader.addBean(noBean);

        final NoBean noBeanFromBeanLoader = beanLoader.getBean(NoBean.class);
        assertNotNull(noBeanFromBeanLoader);
        assertNull(noBeanFromBeanLoader.getHelloBean());
        assertNull(noBeanFromBeanLoader.getCoffeeBean());
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

        final DefaultBeanLoader loader = createBeanLoaderWithBeans(beans);

        final HelloBean helloBean = mock(HelloBean.class);
        loader.addBean(helloBean);

        final AbstractBeanImpl abstractBean = mock(AbstractBeanImpl.class);
        loader.addBean(abstractBean);

        final InterfaceBean interfaceBean = mock(InterfaceBean.class);
        loader.addBean(interfaceBean);

        final FieldBean fieldBean = loader.getBean(FieldBean.class);

        assertSame(helloBean, fieldBean.getHelloBean());
        assertSame(abstractBean, fieldBean.getAbstractBean());
        assertSame(interfaceBean, fieldBean.getInterfaceBean());
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

    // TODO fix failing test
    @Test
    public void getBeanShouldReturnTheSameInstanceForQualifiedSingletonScopedBeans() {
        final PinkSingletonBean singletonBean1 = beanLoader.getBean(PinkSingletonBean.class);
        final PinkSingletonBean singletonBean2 = beanLoader.getBean(PinkSingletonBean.class, "pink");
        final PinkSingletonBean singletonBean3 = beanLoader.getBean(PinkSingletonBean.class, "any");

        assertSame(singletonBean1, singletonBean2);
        assertSame(singletonBean2, singletonBean3);
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
    public void getBeansWithColorBeanAndAnyQualifierShouldReturnAllColorBeans() {
        final Collection<ColorBean> beans = beanLoader.getBeans(ColorBean.class, "any");

        assertNotNull(beans);
        assertEquals(5, beans.size());

        assertTrue(containsBean(BlueBean.class, beans));
        assertTrue(containsBean(GreenBean.class, beans));
        assertTrue(containsBean(RedBean.class, beans));
        assertTrue(containsBean(YellowBean.class, beans));
        assertTrue(containsBean(DarkYellowBean.class, beans));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeansWithNoMatchesShouldThrowException() {
        beanLoader.getBeans(Object.class, "nomatch");
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionWithWildcardShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionInjectionWithWildcard.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfCollectionWithoutTypeArgumentShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(CollectionInjectionWithoutTypeArgument.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfProviderWithWildcardShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ProviderInjectionWithWildcard.class));

        createBeanLoaderWithBeans(beans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void injectionOfProviderWithoutTypeArgumentShouldFail() {
        final Set<BeanKey> beans = new HashSet<BeanKey>();
        beans.add(new BeanKey(ProviderInjectionWithoutTypeArgument.class));

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
    public void addBeanShouldMakeAnyObjectAvailableForInjection() {
        final DefaultBeanLoader newBeanLoader =
                createBeanLoaderWithBasePackages("net.usikkert.kouinject.testbeans.notscanned.date");

        final Date date = new Date();
        newBeanLoader.addBean(date);

        final DateBean dateBean = newBeanLoader.getBean(DateBean.class);

        assertNotNull(dateBean);
        assertSame(date, dateBean.getDate());
    }

    private DefaultBeanLoader createBeanLoaderWithBasePackages(final String... basePackages) {
        final ClassLocator classLocator = new ClassPathScanner();
        final BeanLocator beanLocator = new AnnotationBasedBeanLocator(
                classLocator, basePackages);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();

        return new DefaultBeanLoader(beanDataHandler, beanLocator);
    }

    private DefaultBeanLoader createBeanLoaderWithBeans(final Set<BeanKey> beans) {
        final BeanLocator beanLocator = mock(BeanLocator.class);
        final BeanDataHandler beanDataHandler = new AnnotationBasedBeanDataHandler();
        when(beanLocator.findBeans()).thenReturn(beans);

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
