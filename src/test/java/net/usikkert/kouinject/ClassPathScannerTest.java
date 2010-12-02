
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

import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.LastBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.interfacebean.InterfaceBeanImpl;
import net.usikkert.kouinject.testbeans.scanned.hierarchy.overriding.subpackage.OverridingSubpackageBean;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test of {@link ClassPathScanner}.
 *
 * @author Christian Ihle
 */
public class ClassPathScannerTest {

    private static final String ALL = "net.usikkert.kouinject";
    private static final String SCANNED = "net.usikkert.kouinject.testbeans.scanned";

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
}
