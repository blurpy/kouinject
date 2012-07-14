
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

import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.LastBean;
import net.usikkert.kouinject.testbeans.scanned.any.AnyBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.subpackage.OverridingSubpackageBean;
import net.usikkert.kouinject.testbeans.scanned.scope.SingletonBean;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * Test of {@link ClassPathScanner}.
 *
 * @author Christian Ihle
 */
public class ClassPathScannerTest {

    private static final String ALL = "net.usikkert.kouinject";
    private static final String SCANNED = "net.usikkert.kouinject.testbeans.scanned";
    private static final String SCANNED_ANY = "net.usikkert.kouinject.testbeans.scanned.any";
    private static final String SCANNED_SCOPE = "net.usikkert.kouinject.testbeans.scanned.scope";
    private static final String SCANNED_HIERARCHY = "net.usikkert.kouinject.testbeans.scanned.hierarchy";

    private ClassPathScanner scanner;

    @Before
    public void createScanner() {
        scanner = new ClassPathScanner();
    }

    @Test
    public void findClassesShouldDetectClassesInScannedPackage() {
        final Set<Class<?>> classes = scanner.findClasses(SCANNED);

        assertTrue(classes.contains(LastBean.class));
        assertFalse(classes.contains(TheInterfaceUser.class));
    }

    @Test
    public void findClassesShouldDetectClassesInSubPackages() {
        final Set<Class<?>> classes = scanner.findClasses(SCANNED);

        assertTrue(classes.contains(InterfaceBeanImpl.class));
        assertTrue(classes.contains(JavaBean.class));
        assertTrue(classes.contains(OverridingSubpackageBean.class));
    }

    @Test
    public void findClassesShouldDetectClassesInDifferentBasePackages() {
        final Set<Class<?>> classes = scanner.findClasses(SCANNED_ANY, SCANNED_SCOPE, SCANNED_HIERARCHY);

        assertTrue(classes.contains(AnyBean.class));
        assertTrue(classes.contains(SingletonBean.class));
        assertTrue(classes.contains(OverridingSubpackageBean.class));
        assertFalse(classes.contains(JavaBean.class));
        assertFalse(classes.contains(CarBean.class));
    }

    @Test
    public void findClassesShouldHandleArrays() {
        final Set<Class<?>> classes = scanner.findClasses(new String[] {SCANNED_ANY, SCANNED_SCOPE, SCANNED_HIERARCHY});

        assertTrue(classes.contains(AnyBean.class));
        assertTrue(classes.contains(SingletonBean.class));
        assertTrue(classes.contains(OverridingSubpackageBean.class));
        assertFalse(classes.contains(JavaBean.class));
        assertFalse(classes.contains(CarBean.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldRequireAtLeastOneBasePackage() {
        scanner.findClasses();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldRequireAtLeastOneBasePackageWithArray() {
        scanner.findClasses(new String[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldValidateBasePackagesNotNull() {
        scanner.findClasses(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldValidateBasePackagesNotNullInArray() {
        scanner.findClasses(new String[] {null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldValidateBasePackagesNotEmpty() {
        scanner.findClasses(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClassesShouldValidateBasePackagesNotEmptyInArray() {
        scanner.findClasses(new String[] {" "});
    }

    @Test
    public void findClassesShouldNotIncludeInterfaces() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isInterface());
        }
    }

    @Test
    public void findClassesShouldNotIncludeAnnotations() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isAnnotation());
        }
    }

    @Test
    public void findClassesShouldNotIncludeEnums() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isEnum());
        }
    }

    @Test
    public void findClassesShouldNotIncludeSyntheticClasses() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isSynthetic());
        }
    }

    @Test
    public void findClassesShouldNotIncludeAnonymousClasses() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isAnonymousClass());
        }
    }

    @Test
    public void findClassesShouldNotIncludeInnerClasses() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(class1.isMemberClass());
        }
    }

    @Test
    public void findClassesShouldNotIncludeAbstractClasses() {
        final Set<Class<?>> classes = scanner.findClasses(ALL);

        for (final Class<?> class1 : classes) {
            assertFalse(Modifier.isAbstract(class1.getModifiers()));
        }
    }

    @Test
    public void findClassesShouldUseClassLoaderFromClassIfContextClassLoaderIsNull() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        // Setting the default ClassLoader to null
        Thread.currentThread().setContextClassLoader(null);

        final ClassPathScanner classPathScanner = new ClassPathScanner();
        final Set<Class<?>> classes = classPathScanner.findClasses(SCANNED);

        assertTrue(classes.contains(LastBean.class));
        assertFalse(classes.contains(TheInterfaceUser.class));

        // Cleanup
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }

    @Test
    public void findClassesShouldFindClassesInJarFiles() {
        final URL jarFileUrl = getClass().getClassLoader().getResource("test-beans.jar");
        assertNotNull(jarFileUrl);

        final URLClassLoader jarClassLoader = new URLClassLoader(new URL[] { jarFileUrl });
        final ClassPathScanner classPathScanner = new ClassPathScanner(jarClassLoader);

        final Set<Class<?>> classes = classPathScanner.findClasses("net.usikkert.kouinject.testbeans.jar");
        assertNotNull(classes);
        assertEquals(8, classes.size());

        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.FirstLevel1JarBean"));
        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.SecondLevel1JarBean"));

        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2.FirstLevel2JarBean"));
        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2.SecondLevel2JarBean"));

        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2point2.ThirdLevel2JarBean"));
        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2point2.FourthLevel2JarBean"));

        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2point2.level3.FirstLevel3JarBean"));
        assertTrue(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.level2point2.level3.SecondLevel3JarBean"));

        // Just to be sure containsClass() doesn't always return true
        assertFalse(containsClass(classes, "net.usikkert.kouinject.testbeans.jar.ThirdLevel1JarBean"));
    }

    @Test
    public void findClassesShouldFindClassesInJarFilesThatCanBeInstantiated() throws IllegalAccessException, InstantiationException {
        final URL jarFileUrl = getClass().getClassLoader().getResource("test-beans.jar");
        assertNotNull(jarFileUrl);

        final URLClassLoader jarClassLoader = new URLClassLoader(new URL[] { jarFileUrl });
        final ClassPathScanner classPathScanner = new ClassPathScanner(jarClassLoader);

        final Set<Class<?>> classes = classPathScanner.findClasses("net.usikkert.kouinject.testbeans.jar");
        assertNotNull(classes);
        assertEquals(8, classes.size());

        for (final Class<?> aClass : classes) {
            final Object instance = aClass.newInstance();
            assertNotNull(instance);
        }
    }

    private boolean containsClass(final Set<Class<?>> classes, final String className) {
        for (final Class<?> aClass : classes) {
            if (aClass.getName().equals(className)) {
                return true;
            }
        }

        return false;
    }
}
