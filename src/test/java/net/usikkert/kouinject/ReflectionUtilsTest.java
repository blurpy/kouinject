
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.usikkert.kouinject.testbeans.notscanned.SomeEnum;
import net.usikkert.kouinject.testbeans.notscanned.notloaded.FieldModifierBean;
import net.usikkert.kouinject.testbeans.scanned.GarageBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.ChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.MiddleBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.SuperBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.OverridingFirstAbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.OverridingChildBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.OverridingInterface;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.OverridingSecondAbstractBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.OverridingSuperBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.subpackage.OverridingSubpackageBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Yellow;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link ReflectionUtils}.
 *
 * @author Christian Ihle
 */
public class ReflectionUtilsTest {

    private ReflectionUtils reflectionUtils;

    @Before
    public void createReflectionUtils() {
        reflectionUtils = new ReflectionUtils();
    }

    @Test
    public void findAllMethodsShouldFindMethodsInCurrentClassAndSuperClassesExceptObject() {
        assertEquals(3, reflectionUtils.findAllMethods(OverridingFirstAbstractBean.class).size());
        assertEquals(5, reflectionUtils.findAllMethods(OverridingSecondAbstractBean.class).size());
        assertEquals(13, reflectionUtils.findAllMethods(OverridingSuperBean.class).size());
        assertEquals(22, reflectionUtils.findAllMethods(OverridingChildBean.class).size());
    }

    @Test
    public void interfacesCanNotBeOverridden() throws Exception {
        final Method overridingInterfaceMethod = OverridingInterface.class.getDeclaredMethod("publicMethod");
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSecondAbstractBeanMethod = OverridingSecondAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");

        assertFalse(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingInterfaceMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingInterfaceMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingInterfaceMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingInterfaceMethod));
    }

    @Test
    public void abstractClassMethodsCanOverrideEachOther() throws Exception {
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSecondAbstractBeanMethod = OverridingSecondAbstractBean.class.getDeclaredMethod("publicMethod");

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSecondAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingFirstAbstractBeanMethod));
    }

    @Test
    public void abstractClassMethodsCanBeOverridden() throws Exception {
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSecondAbstractBeanMethod = OverridingSecondAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSecondAbstractBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSuperBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingChildBeanMethod));

        assertFalse(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingFirstAbstractBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingSuperBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingChildBeanMethod));
    }

    @Test
    public void abstractMethodsMustBeOverridden() throws Exception {
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("abstractMethod");
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("abstractMethod");

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSuperBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingFirstAbstractBeanMethod));
    }

    @Test
    public void publicMethodsCanBeOverridden() throws Exception {
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSecondAbstractBeanMethod = OverridingSecondAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");

        assertFalse(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingFirstAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingFirstAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingFirstAbstractBeanMethod));

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSecondAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingSecondAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSecondAbstractBeanMethod));

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSuperBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingSuperBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));

        assertTrue(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingChildBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingSecondAbstractBeanMethod, overridingChildBeanMethod));
        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
    }

    @Test
    public void privateMethodsCannotBeOverridden() throws Exception {
        final Method overridingFirstAbstractBeanMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("privateMethod");
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("privateMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("privateMethod");

        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingFirstAbstractBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingFirstAbstractBeanMethod));

        assertFalse(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingSuperBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));

        assertFalse(reflectionUtils.isOverridden(overridingFirstAbstractBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
    }

    @Test
    public void staticMethodsCannotBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("staticMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("staticMethod");

        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void finalMethodsCannotBeOverriddenButCanOverride() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("finalMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("finalMethod");

        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void protectedMethodsCanBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("protectedMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("protectedMethod");

        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void defaultMethodsCanBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("defaultMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("defaultMethod");

        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void methodsInClassesInheritingFromSameSuperClassCannotOverrideEachOther() throws Exception {
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSubpackageBeanMethod = OverridingSubpackageBean.class.getDeclaredMethod("publicMethod");

        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSubpackageBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSubpackageBeanMethod, overridingChildBeanMethod));
    }

    @Test
    public void publicMethodsInClassesInDifferentPackagesCanBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method overridingSubpackageBeanMethod = OverridingSubpackageBean.class.getDeclaredMethod("publicMethod");

        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingSubpackageBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSubpackageBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void protectedMethodsInClassesInDifferentPackagesCanBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("protectedMethod");
        final Method overridingSubpackageBeanMethod = OverridingSubpackageBean.class.getDeclaredMethod("protectedMethod");

        assertTrue(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingSubpackageBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSubpackageBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void defaultMethodsInClassesInDifferentPackagesCannotBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("defaultMethod");
        final Method overridingSubpackageBeanMethod = OverridingSubpackageBean.class.getDeclaredMethod("defaultMethod");

        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingSubpackageBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingSubpackageBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void methodsMustHaveTheSameNameToBeOverridden() throws Exception {
        final Method overridingSuperBeanMethod = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method overridingChildBeanMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod2");

        assertFalse(reflectionUtils.isOverridden(overridingSuperBeanMethod, overridingChildBeanMethod));
        assertFalse(reflectionUtils.isOverridden(overridingChildBeanMethod, overridingSuperBeanMethod));
    }

    @Test
    public void methodsMustHaveTheSameParametersToBeOverridden() throws Exception {
        final Method superWithNoParam = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method superWithObjectParam = OverridingSuperBean.class.getDeclaredMethod("publicMethod", Object.class);
        final Method childWithNoParam = OverridingChildBean.class.getDeclaredMethod("publicMethod");
        final Method childWithStringParam = OverridingChildBean.class.getDeclaredMethod("publicMethod", String.class);
        final Method childWithObjectParam = OverridingChildBean.class.getDeclaredMethod("publicMethod", Object.class);

        assertTrue(reflectionUtils.isOverridden(superWithNoParam, childWithNoParam));
        assertFalse(reflectionUtils.isOverridden(superWithObjectParam, childWithNoParam));

        assertFalse(reflectionUtils.isOverridden(superWithNoParam, childWithStringParam));
        assertFalse(reflectionUtils.isOverridden(superWithObjectParam, childWithStringParam));

        assertFalse(reflectionUtils.isOverridden(superWithNoParam, childWithObjectParam));
        assertTrue(reflectionUtils.isOverridden(superWithObjectParam, childWithObjectParam));
    }

    @Test
    public void isSubClassOf() {
        assertTrue(reflectionUtils.isSubClassOf(ChildBean.class, MiddleBean.class));
        assertTrue(reflectionUtils.isSubClassOf(MiddleBean.class, SuperBean.class));
        assertTrue(reflectionUtils.isSubClassOf(SuperBean.class, Object.class));

        assertTrue(reflectionUtils.isSubClassOf(ChildBean.class, SuperBean.class));
        assertTrue(reflectionUtils.isSubClassOf(ChildBean.class, Object.class));

        assertFalse(reflectionUtils.isSubClassOf(SuperBean.class, SuperBean.class));

        assertFalse(reflectionUtils.isSubClassOf(MiddleBean.class, ChildBean.class));
        assertFalse(reflectionUtils.isSubClassOf(SuperBean.class, MiddleBean.class));
        assertFalse(reflectionUtils.isSubClassOf(Object.class, SuperBean.class));

        assertFalse(reflectionUtils.isSubClassOf(SuperBean.class, ChildBean.class));
        assertFalse(reflectionUtils.isSubClassOf(Object.class, ChildBean.class));
    }

    @Test
    public void methodModifiers() throws Exception {
        final Method publicMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");
        assertTrue(reflectionUtils.isPublic(publicMethod));

        final Method protectedMethod = OverridingChildBean.class.getDeclaredMethod("protectedMethod");
        assertTrue(reflectionUtils.isProtected(protectedMethod));

        final Method defaultMethod = OverridingChildBean.class.getDeclaredMethod("defaultMethod");
        assertTrue(reflectionUtils.isDefault(defaultMethod));

        final Method privateMethod = OverridingSuperBean.class.getDeclaredMethod("privateMethod");
        assertTrue(reflectionUtils.isPrivate(privateMethod));

        final Method finalMethod = OverridingChildBean.class.getDeclaredMethod("finalMethod");
        assertTrue(reflectionUtils.isFinal(finalMethod));

        final Method staticMethod = OverridingChildBean.class.getDeclaredMethod("staticMethod");
        assertTrue(reflectionUtils.isStatic(staticMethod));
    }

    @Test
    public void classModifiers() throws Exception {
        assertTrue(reflectionUtils.isAbstract(OverridingFirstAbstractBean.class));
    }

    @Test
    public void fieldModifiers() throws Exception {
        final Field publicField = FieldModifierBean.class.getDeclaredField("publicField");
        assertTrue(reflectionUtils.isPublic(publicField));

        final Field protectedField = FieldModifierBean.class.getDeclaredField("protectedField");
        assertTrue(reflectionUtils.isProtected(protectedField));

        final Field defaultField = FieldModifierBean.class.getDeclaredField("defaultField");
        assertTrue(reflectionUtils.isDefault(defaultField));

        final Field privateField = FieldModifierBean.class.getDeclaredField("privateField");
        assertTrue(reflectionUtils.isPrivate(privateField));

        final Field finalField = FieldModifierBean.class.getDeclaredField("finalField");
        assertTrue(reflectionUtils.isFinal(finalField));

        final Field staticField = FieldModifierBean.class.getDeclaredField("staticField");
        assertTrue(reflectionUtils.isStatic(staticField));
    }

    @Test
    public void isInTheSamePackage() throws Exception {
        final Method coffeeBeanMethod = CoffeeBean.class.getDeclaredMethod("getHelloBean");
        final Method javaBeanMethod = JavaBean.class.getDeclaredMethod("getHelloBean");
        final Method garageBeanMethod = GarageBean.class.getDeclaredMethod("getBlueCarBean");

        assertTrue(reflectionUtils.isInTheSamePackage(coffeeBeanMethod, javaBeanMethod));
        assertFalse(reflectionUtils.isInTheSamePackage(coffeeBeanMethod, garageBeanMethod));
    }

    @Test
    public void hasOverridableAccessModifiers() throws Exception {
        final Method publicMethod = OverridingChildBean.class.getDeclaredMethod("publicMethod");
        final Method publicSubPackageMethod = OverridingSubpackageBean.class.getDeclaredMethod("publicMethod");
        final Method protectedMethod = OverridingChildBean.class.getDeclaredMethod("protectedMethod");
        final Method defaultMethod = OverridingChildBean.class.getDeclaredMethod("defaultMethod");
        final Method defaultSubPackageMethod = OverridingSubpackageBean.class.getDeclaredMethod("defaultMethod");
        final Method privateMethod = OverridingSuperBean.class.getDeclaredMethod("privateMethod");
        final Method finalMethod = OverridingChildBean.class.getDeclaredMethod("finalMethod");
        final Method staticMethod = OverridingChildBean.class.getDeclaredMethod("staticMethod");

        assertFalse(reflectionUtils.hasOverridableAccessModifiers(finalMethod, null));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(staticMethod, null));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(privateMethod, null));

        assertFalse(reflectionUtils.hasOverridableAccessModifiers(publicMethod, staticMethod));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(publicMethod, privateMethod));

        assertTrue(reflectionUtils.hasOverridableAccessModifiers(defaultMethod, defaultMethod));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(defaultMethod, defaultSubPackageMethod));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(defaultSubPackageMethod, defaultMethod));
        assertFalse(reflectionUtils.hasOverridableAccessModifiers(defaultSubPackageMethod, publicMethod));

        assertTrue(reflectionUtils.hasOverridableAccessModifiers(defaultMethod, protectedMethod));
        assertTrue(reflectionUtils.hasOverridableAccessModifiers(protectedMethod, publicMethod));
        assertTrue(reflectionUtils.hasOverridableAccessModifiers(publicMethod, publicSubPackageMethod));
    }

    @Test
    public void hasTheSameName() throws Exception {
        final Method firstPublicMethod = OverridingFirstAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method secondPublicMethod = OverridingSecondAbstractBean.class.getDeclaredMethod("publicMethod");
        final Method staticMethod = OverridingChildBean.class.getDeclaredMethod("staticMethod");

        assertTrue(reflectionUtils.hasTheSameName(firstPublicMethod, secondPublicMethod));
        assertTrue(reflectionUtils.hasTheSameName(secondPublicMethod, firstPublicMethod));

        assertFalse(reflectionUtils.hasTheSameName(firstPublicMethod, staticMethod));
        assertFalse(reflectionUtils.hasTheSameName(staticMethod, firstPublicMethod));

        assertFalse(reflectionUtils.hasTheSameName(secondPublicMethod, staticMethod));
        assertFalse(reflectionUtils.hasTheSameName(staticMethod, secondPublicMethod));
    }

    @Test
    public void hasTheSameParameters() throws Exception {
        final Method superWithNoParam = OverridingSuperBean.class.getDeclaredMethod("publicMethod");
        final Method superWithObjectParam = OverridingSuperBean.class.getDeclaredMethod("publicMethod", Object.class);
        final Method childWithNoParam = OverridingChildBean.class.getDeclaredMethod("publicMethod");
        final Method childWithStringParam = OverridingChildBean.class.getDeclaredMethod("publicMethod", String.class);
        final Method childWithObjectParam = OverridingChildBean.class.getDeclaredMethod("publicMethod", Object.class);

        assertTrue(reflectionUtils.hasTheSameParameters(superWithNoParam, childWithNoParam));
        assertFalse(reflectionUtils.hasTheSameParameters(superWithObjectParam, childWithNoParam));

        assertFalse(reflectionUtils.hasTheSameParameters(superWithNoParam, childWithStringParam));
        assertFalse(reflectionUtils.hasTheSameParameters(superWithObjectParam, childWithStringParam));

        assertFalse(reflectionUtils.hasTheSameParameters(superWithNoParam, childWithObjectParam));
        assertTrue(reflectionUtils.hasTheSameParameters(superWithObjectParam, childWithObjectParam));
    }

    @Test
    public void isNormalClass() {
        assertTrue(reflectionUtils.isNormalClass(OverridingChildBean.class));
        assertFalse(reflectionUtils.isNormalClass(OverridingInterface.class));
        assertFalse(reflectionUtils.isNormalClass(OverridingFirstAbstractBean.class));
        assertFalse(reflectionUtils.isNormalClass(Yellow.class));
        assertFalse(reflectionUtils.isNormalClass(SomeEnum.class));
        assertFalse(reflectionUtils.isNormalClass(MemberClass.class));
    }

    /**
     * Class for testing detection of member classes.
     */
    private class MemberClass {
    }
}
