
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import net.usikkert.kouinject.testbeans.notscanned.notloaded.NamedQualifierUsedWithoutNameBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.NoMatchingConstructorBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyMatchingConstructorsBean;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.TooManyQualifiersBean;
import net.usikkert.kouinject.testbeans.scanned.ConstructorBean;
import net.usikkert.kouinject.testbeans.scanned.EverythingBean;
import net.usikkert.kouinject.testbeans.scanned.FieldBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.ProviderBean;
import net.usikkert.kouinject.testbeans.scanned.SetterBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.abstractbean.AbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.Blue;
import net.usikkert.kouinject.testbeans.scanned.notloaded.QualifierBean;

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

        final List<Dependency> dependencies = beanData.getDependencies();
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
    public void getBeanDataShouldDetectMethodsAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(JavaBean.class, false);

        assertEquals(JavaBean.class, beanData.getBeanClass());

        final List<Dependency> dependencies = beanData.getDependencies();
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
    public void getBeanDataShouldDetectCorrectConstuctorAndDependenciesForInjection() {
        final BeanData beanData = handler.getBeanData(ConstructorBean.class, false);

        assertEquals(ConstructorBean.class, beanData.getBeanClass());

        final List<Dependency> dependencies = beanData.getDependencies();
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

        final List<Dependency> dependencies = beanData.getDependencies();
        assertEquals(8, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final Dependency dependency : dependencies) {
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

        final Dependency constructorBean = constructor.getDependencies().get(0);
        assertEquals("Green", constructorBean.getQualifier());
        assertFalse(constructorBean.isProvider());

        final Dependency constructorBeanProvider = constructor.getDependencies().get(1);
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

        final Dependency fieldBean = field.getDependency();
        assertEquals("red", fieldBean.getQualifier());
        assertFalse(fieldBean.isProvider());

        final FieldData providerField = fields.get(1);
        assertTrue(providerField.getField().isAnnotationPresent(Blue.class));

        final Dependency fieldBeanProvider = providerField.getDependency();
        assertEquals("Blue", fieldBeanProvider.getQualifier());
        assertTrue(fieldBeanProvider.isProvider());
    }

    @Test
    public void getBeanDataShouldDetectQualifiersInMethods() {
        final BeanData beanData = handler.getBeanData(QualifierBean.class, false);

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(1, methods.size());

        final MethodData method = methods.get(0);

        final Dependency setterB = method.getDependencies().get(0);
        assertEquals("Yellow", setterB.getQualifier());
        assertFalse(setterB.isProvider());

        final Dependency setterBProvider =  method.getDependencies().get(1);
        assertEquals("Blue", setterBProvider.getQualifier());
        assertTrue(setterBProvider.isProvider());
    }

    @Test
    public void getBeanDataShouldDetectDependenciesInProviders() {
        final BeanData beanData = handler.getBeanData(ProviderBean.class, false);

        assertEquals(ProviderBean.class, beanData.getBeanClass());

        final List<Dependency> dependencies = beanData.getDependencies();
        assertEquals(3, dependencies.size());
        assertNoQualifiers(dependencies);

        for (final Dependency dependency : dependencies) {
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
    public void getBeanDataShouldSupportIgnoringConstructor() {
        final BeanData beanData = handler.getBeanData(ConstructorBean.class, true);

        assertEquals(ConstructorBean.class, beanData.getBeanClass());

        final List<Dependency> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());

        final ConstructorData constructor = beanData.getConstructor();
        assertNull(constructor);
    }

    @Test
    public void getBeanDataShouldHandleClassesWithoutAnnotations() {
        final BeanData beanData = handler.getBeanData(ClassPathScanner.class, false);

        assertEquals(ClassPathScanner.class, beanData.getBeanClass());

        final List<Dependency> dependencies = beanData.getDependencies();
        assertEquals(0, dependencies.size());

        final ConstructorData constructor = beanData.getConstructor();
        assertNotNull(constructor);

        final List<FieldData> fields = beanData.getFields();
        assertEquals(0, fields.size());

        final List<MethodData> methods = beanData.getMethods();
        assertEquals(0, methods.size());
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

    private void assertNoQualifiers(final List<Dependency> dependencies) {
        for (final Dependency dependency : dependencies) {
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

    private boolean containsDependency(final List<Dependency> dependencies, final Class<?> beanClass) {
        for (final Dependency dependency : dependencies) {
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
}
