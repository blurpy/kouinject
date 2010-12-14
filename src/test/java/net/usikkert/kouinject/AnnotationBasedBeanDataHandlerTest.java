
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import net.usikkert.kouinject.beandata.BeanData;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.ConstructorData;
import net.usikkert.kouinject.beandata.FieldData;
import net.usikkert.kouinject.beandata.MethodData;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.CollectionProviderInjectionWithWildcard;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.CollectionProviderInjectionWithoutTypeArgument;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.ProvidedHungryBean;
import net.usikkert.kouinject.testbeans.notscanned.collectionprovider.ProvidedHungryQualifierBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.NamedQualifierUsedWithoutNameBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.NoMatchingConstructorBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyMatchingConstructorsBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyQualifiersBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.FinalBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.StaticBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.collection.Food;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryBean;
import net.usikkert.kouinject.testbeans.scanned.collection.HungryQualifierBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding2.pets.CatBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.QualifierBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Blue;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link AnnotationBasedBeanDataHandler}.
 *
 * @author Christian Ihle
 */
public class AnnotationBasedBeanDataHandlerTest {

    private AnnotationBasedBeanDataHandler handler;

    @Before
    public void createHandler() {
        handler = new AnnotationBasedBeanDataHandler();
    }

    @Test
    public void getBeanDataShouldDetectFieldsAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(FieldBean.class, false);

        assertEquals(FieldBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        assertTrue(containsDependency(dependencies, HelloBean.class));
        assertTrue(containsDependency(dependencies, AbstractBean.class));
        assertTrue(containsDependency(dependencies, InterfaceBean.class));

        final List<FieldData> fields = beanData.getFields();
        assertEquals(3, fields.size());

        for (final FieldData field : fields) {
            assertTrue(field.getField().isAnnotationPresent(Inject.class));
        }

        assertTrue(containsField(fields, HelloBean.class));
        assertTrue(containsField(fields, AbstractBean.class));
        assertTrue(containsField(fields, InterfaceBean.class));
    }

    @Test
    public void getBeanDataShouldDetectInheritedFieldsAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(ChildBean.class, false);

        assertEquals(ChildBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        assertTrue(containsDependency(dependencies, FieldBean.class));
        assertTrue(containsDependency(dependencies, HelloBean.class));
        assertTrue(containsDependency(dependencies, CoffeeBean.class));

        final List<FieldData> fields = beanData.getFields();
        assertEquals(3, fields.size());

        for (final FieldData field : fields) {
            assertTrue(field.getField().isAnnotationPresent(Inject.class));
        }

        assertTrue(containsField(fields, FieldBean.class));
        assertTrue(containsField(fields, HelloBean.class));
        assertTrue(containsField(fields, CoffeeBean.class));
    }

    @Test
    public void getBeanDataShouldDetectMethodsAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(JavaBean.class, false);

        assertEquals(JavaBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(2, dependencies.size());
        assertNoQualifiers(dependencies);

        assertTrue(containsDependency(dependencies, HelloBean.class));
        assertTrue(containsDependency(dependencies, FieldBean.class));

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(1, methods.size());

        for (final MethodData method : methods) {
            assertTrue(method.getMethod().isAnnotationPresent(Inject.class));
            assertTrue(containsMethodParameter(method, HelloBean.class));
            assertTrue(containsMethodParameter(method, FieldBean.class));
        }
    }

    @Test
    public void getBeanDataShouldDetectInheritedAndOverriddenMethodsAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(CatBean.class, false);

        assertEquals(CatBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(20, dependencies.size());
        assertNoQualifiers(dependencies);

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(12, methods.size());

        // all beans have private methods, none overridden
        assertEquals(4, containsMethod(methods, "setHelloBean", HelloBean.class));

        // default method overridden in 2 beans in different packages
        assertEquals(2, containsMethod(methods, "setJavaBean", JavaBean.class));

        // protected method overridden in last bean
        assertEquals(1, containsMethod(methods, "setCoffeeBean", CoffeeBean.class));

        // public method overridden in last bean
        assertEquals(1, containsMethod(methods, "setCarBean", CarBean.class));

        // inherited public methods
        assertEquals(1, containsMethod(methods, "setSetterBeanInOrganismBean", SetterBean.class));
        assertEquals(1, containsMethod(methods, "setSetterBeanInAnimalBean", SetterBean.class));
        assertEquals(1, containsMethod(methods, "setSetterBeanInPetBean", SetterBean.class));
        assertEquals(1, containsMethod(methods, "setSetterBeanInCatBean", SetterBean.class));

        final List<FieldData> fields = beanData.getFields();
        assertEquals(8, fields.size());

        // inherited fields
        assertEquals(1, containsField(fields, "fieldBean1InOrganismBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean1InAnimalBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean1InPetBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean1InCatBean", FieldBean.class));

        assertEquals(1, containsField(fields, "fieldBean2InOrganismBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean2InAnimalBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean2InPetBean", FieldBean.class));
        assertEquals(1, containsField(fields, "fieldBean2InCatBean", FieldBean.class));
    }

    @Test
    public void getBeanDataShouldDetectCorrectConstructorAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(ConstructorBean.class, false);

        assertEquals(ConstructorBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(2, dependencies.size());
        assertNoQualifiers(dependencies);

        assertTrue(containsDependency(dependencies, HelloBean.class));
        assertTrue(containsDependency(dependencies, SetterBean.class));

        final ConstructorData constructor = beanData.getConstructor();
        assertTrue(constructor.getConstructor().isAnnotationPresent(Inject.class));

        assertTrue(containsConstructorParameter(constructor, HelloBean.class));
        assertTrue(containsConstructorParameter(constructor, SetterBean.class));
    }

    @Test
    public void getBeanDataShouldDetectConstructorAndFieldsAndMethodsAtTheSameTime() {
        final BeanData beanData = handler.getBeanData(EverythingBean.class, false);

        assertEquals(EverythingBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(8, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final BeanKey dependency : dependencies) {
            assertFalse(dependency.isProvider());
        }

        final ConstructorData constructor = beanData.getConstructor();
        assertTrue(constructor.getConstructor().isAnnotationPresent(Inject.class));
        assertEquals(5, constructor.getConstructor().getParameterTypes().length);

        final List<FieldData> fields = beanData.getFields();
        assertEquals(1, fields.size());

        for (final FieldData field : fields) {
            assertTrue(field.getField().isAnnotationPresent(Inject.class));
        }

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(2, methods.size());

        for (final MethodData method : methods) {
            assertTrue(method.getMethod().isAnnotationPresent(Inject.class));
        }
    }

    @Test
    public void getBeanDataShouldDetectQualifiersInConstructors() {
        final BeanData beanData = handler.getBeanData(QualifierBean.class, false);

        assertEquals(QualifierBean.class, beanData.getBeanClass());

        final ConstructorData constructor = beanData.getConstructor();
        assertEquals(2, constructor.getDependencies().size());

        final BeanKey constructorBean = constructor.getDependencies().get(0);
        assertEquals("Green", constructorBean.getQualifier());
        assertFalse(constructorBean.isProvider());

        final BeanKey constructorBeanProvider = constructor.getDependencies().get(1);
        assertEquals("orange", constructorBeanProvider.getQualifier());
        assertTrue(constructorBeanProvider.isProvider());
    }

    @Test
    public void getBeanDataShouldDetectQualifiersInFields() {
        final BeanData beanData = handler.getBeanData(QualifierBean.class, false);

        final List<FieldData> fields = beanData.getFields();
        assertEquals(2, fields.size());

        final FieldData field = fields.get(0);
        assertTrue(field.getField().isAnnotationPresent(Named.class));

        final BeanKey fieldBean = field.getDependency();
        assertEquals("red", fieldBean.getQualifier());
        assertFalse(fieldBean.isProvider());

        final FieldData providerField = fields.get(1);
        assertTrue(providerField.getField().isAnnotationPresent(Blue.class));

        final BeanKey fieldBeanProvider = providerField.getDependency();
        assertEquals("Blue", fieldBeanProvider.getQualifier());
        assertTrue(fieldBeanProvider.isProvider());
    }

    @Test
    public void getBeanDataShouldDetectQualifiersInMethods() {
        final BeanData beanData = handler.getBeanData(QualifierBean.class, false);

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(1, methods.size());

        final MethodData method = methods.get(0);

        final BeanKey setterB = method.getDependencies().get(0);
        assertEquals("Yellow", setterB.getQualifier());
        assertFalse(setterB.isProvider());

        final BeanKey setterBProvider =  method.getDependencies().get(1);
        assertEquals("Blue", setterBProvider.getQualifier());
        assertTrue(setterBProvider.isProvider());
    }

    @Test
    public void getBeanDataShouldDetectDependenciesInProviders() {
        final BeanData beanData = handler.getBeanData(ProviderBean.class, false);

        assertEquals(ProviderBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final BeanKey dependency : dependencies) {
            assertTrue(dependency.isProvider());
        }

        assertTrue(containsDependency(dependencies, ConstructorBean.class));
        assertTrue(containsDependency(dependencies, FieldBean.class));
        assertTrue(containsDependency(dependencies, SetterBean.class));

        assertTrue(containsConstructorParameter(beanData.getConstructor(), Provider.class));
        assertTrue(containsField(beanData.getFields(), Provider.class));
        assertTrue(containsMethodParameter(beanData.getMethods().get(0), Provider.class));
    }

    @Test
    public void getBeanDataShouldDetectDependenciesInCollections() {
        final BeanData beanData = handler.getBeanData(HungryBean.class, false);

        assertEquals(HungryBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final BeanKey dependency : dependencies) {
            assertTrue(dependency.isCollection());
            assertTrue(dependency.getBeanClass().equals(Food.class));
        }

        assertTrue(containsConstructorParameter(beanData.getConstructor(), Collection.class));
        assertTrue(containsField(beanData.getFields(), Collection.class));
        assertTrue(containsMethodParameter(beanData.getMethods().get(0), Collection.class));
    }

    @Test
    public void getBeanDataShouldDetectQualifiersForCollections() {
        final BeanData beanData = handler.getBeanData(HungryQualifierBean.class, false);

        assertEquals(HungryQualifierBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());

        for (final BeanKey dependency : dependencies) {
            assertTrue(dependency.isCollection());
            assertTrue(dependency.getBeanClass().equals(Food.class));
        }

        assertTrue(containsConstructorParameter(beanData.getConstructor(), Collection.class));
        assertTrue(containsField(beanData.getFields(), Collection.class));
        assertTrue(containsMethodParameter(beanData.getMethods().get(0), Collection.class));

        final FieldData fastFoodField = beanData.getFields().get(0);
        assertTrue(fastFoodField.getField().isAnnotationPresent(Named.class));
        final BeanKey fastFoodDependency = fastFoodField.getDependency();
        assertEquals("fastFood", fastFoodDependency.getQualifier());
        assertTrue(fastFoodDependency.isCollection());

        final MethodData roundFoodMethod = beanData.getMethods().get(0);
        final BeanKey roundFoodDependency = roundFoodMethod.getDependencies().get(0);
        assertEquals("roundFood", roundFoodDependency.getQualifier());
        assertTrue(roundFoodDependency.isCollection());

        final ConstructorData allFoodConstructor = beanData.getConstructor();
        final BeanKey allFoodDependency = allFoodConstructor.getDependencies().get(0);
        assertEquals("Any", allFoodDependency.getQualifier());
        assertTrue(allFoodDependency.isCollection());
    }

    @Test
    public void getBeanDataShouldDetectDependenciesInCollectionProviders() {
        final BeanData beanData = handler.getBeanData(ProvidedHungryBean.class, false);

        assertEquals(ProvidedHungryBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final BeanKey dependency : dependencies) {
            assertTrue(dependency.isCollectionProvider());
            assertTrue(dependency.getBeanClass().equals(Food.class));
        }

        assertTrue(containsConstructorParameter(beanData.getConstructor(), CollectionProvider.class));
        assertTrue(containsField(beanData.getFields(), CollectionProvider.class));
        assertTrue(containsMethodParameter(beanData.getMethods().get(0), CollectionProvider.class));
    }

    @Test
    public void getBeanDataShouldDetectQualifiersForCollectionProviders() {
        final BeanData beanData = handler.getBeanData(ProvidedHungryQualifierBean.class, false);

        assertEquals(ProvidedHungryQualifierBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());

        for (final BeanKey dependency : dependencies) {
            assertTrue(dependency.isCollectionProvider());
            assertTrue(dependency.getBeanClass().equals(Food.class));
        }

        assertTrue(containsConstructorParameter(beanData.getConstructor(), CollectionProvider.class));
        assertTrue(containsField(beanData.getFields(), CollectionProvider.class));
        assertTrue(containsMethodParameter(beanData.getMethods().get(0), CollectionProvider.class));

        final FieldData fastFoodField = beanData.getFields().get(0);
        assertTrue(fastFoodField.getField().isAnnotationPresent(Named.class));
        final BeanKey fastFoodDependency = fastFoodField.getDependency();
        assertEquals("fastFood", fastFoodDependency.getQualifier());
        assertTrue(fastFoodDependency.isCollectionProvider());

        final MethodData roundFoodMethod = beanData.getMethods().get(0);
        final BeanKey roundFoodDependency = roundFoodMethod.getDependencies().get(0);
        assertEquals("roundFood", roundFoodDependency.getQualifier());
        assertTrue(roundFoodDependency.isCollectionProvider());

        final ConstructorData allFoodConstructor = beanData.getConstructor();
        final BeanKey allFoodDependency = allFoodConstructor.getDependencies().get(0);
        assertEquals("Any", allFoodDependency.getQualifier());
        assertTrue(allFoodDependency.isCollectionProvider());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanDataShouldFailIfCollectionProviderIsUsedWithWildcard() {
        handler.getBeanData(CollectionProviderInjectionWithWildcard.class, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBeanDataShouldFailIfCollectionProviderIsUsedWithoutTypeArgument() {
        handler.getBeanData(CollectionProviderInjectionWithoutTypeArgument.class, false);
    }

    @Test
    public void getBeanDataShouldSupportIgnoringConstructor() {
        final BeanData beanData = handler.getBeanData(ConstructorBean.class, true);

        assertEquals(ConstructorBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());

        final ConstructorData constructor = beanData.getConstructor();
        assertNull(constructor);
    }

    @Test
    public void getBeanDataShouldHandleClassesWithoutAnnotations() {
        final BeanData beanData = handler.getBeanData(ClassPathScanner.class, false);

        assertEquals(ClassPathScanner.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());

        final ConstructorData constructor = beanData.getConstructor();
        assertNotNull(constructor);

        final List<FieldData> fields = beanData.getFields();
        assertEquals(0, fields.size());

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(0, methods.size());
    }

    @Test
    public void getBeanDataShouldIgnoreStatics() {
        final BeanData beanData = handler.getBeanData(StaticBean.class, false);

        assertEquals(StaticBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());
    }

    @Test
    public void getBeanDataShouldIgnoreFinalFields() {
        final BeanData beanData = handler.getBeanData(FinalBean.class, false);

        assertEquals(FinalBean.class, beanData.getBeanClass());

        final List<BeanKey> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());
    }

    @Test
    public void getBeanDataShouldDetectSingletons() {
        final BeanData beanData = handler.getBeanData(SingletonBean.class, false);

        assertTrue(beanData.isSingleton());
    }

    @Test
    public void getBeanDataShouldDetectNonSingletons() {
        final BeanData beanData = handler.getBeanData(CarBean.class, false);

        assertFalse(beanData.isSingleton());
    }

    @Test(expected = RuntimeException.class)
    public void getBeanDataShouldAbortIfNoMatchingConstructorIsFound() {
        handler.getBeanData(NoMatchingConstructorBean.class, false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getBeanDataShouldAbortIfTooManyMatchingConstructorsAreFound() {
        handler.getBeanData(TooManyMatchingConstructorsBean.class, false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getBeanDataShouldAbortIfTooManyQualifiersAreUsedOnTheSameField() {
        handler.getBeanData(TooManyQualifiersBean.class, false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getBeanDataShouldAbortIfNamedQualifierIsUsedWithoutName() {
        handler.getBeanData(NamedQualifierUsedWithoutNameBean.class, false);
    }

    private void assertNoQualifiers(final List<BeanKey> dependencies) {
        for (final BeanKey dependency : dependencies) {
            assertNull(dependency.getQualifier());
        }
    }

    private boolean containsConstructorParameter(final ConstructorData constructor, final Class<?> beanClass) {
        for (final Class<?> parameter : constructor.getConstructor().getParameterTypes()) {
            if (parameter.equals(beanClass)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsMethodParameter(final MethodData method, final Class<?> beanClass) {
        for (final Class<?> parameter : method.getMethod().getParameterTypes()) {
            if (parameter.equals(beanClass)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDependency(final List<BeanKey> dependencies, final Class<?> beanClass) {
        for (final BeanKey dependency : dependencies) {
            if (dependency.getBeanClass().equals(beanClass)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsField(final List<FieldData> fields, final Class<?> beanClass) {
        for (final FieldData field : fields) {
            if (field.getField().getType().equals(beanClass)) {
                return true;
            }
        }

        return false;
    }

    private int containsField(final List<FieldData> fields, final String fieldName, final Class<?> beanClass) {
        int counter = 0;

        for (final FieldData fieldData : fields) {
            final Field field = fieldData.getField();
            if (field.getName().equals(fieldName) && field.getType().equals(beanClass)) {
                counter++;
            }
        }

        return counter;
    }

    private int containsMethod(final List<MethodData> methods, final String methodName, final Class<?> beanClass) {
        int counter = 0;

        for (final MethodData methodData : methods) {
            final Method method = methodData.getMethod();

            if (method.getName().equals(methodName) && method.getParameterTypes().length == 1) {
                if (method.getParameterTypes()[0].equals(beanClass)) {
                    counter++;
                }
            }
        }


        return counter;
    }
}
