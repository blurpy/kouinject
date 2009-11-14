
/***************************************************************************
 *   Copyright 2006-2009 by Christian Ihle                                 *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package net.usikkert.kouinject;

import static org.junit.Assert.*;

import java.util.Set;

import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.scanned.LastBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Test of {@link ClassPathScanner}.
 *
 * TODO Add enums, anonymous classes and so on for this test.
 *
 * @author Christian Ihle
 */
public class ClassPathScannerTest
{
    private static final String ALL = "net.usikkert.kouinject";
	private static final String SCANNED = "net.usikkert.kouinject.testbeans.scanned";

	private ClassPathScanner scanner;

	@Before
	public void createScanner()
	{
		scanner = new ClassPathScanner();
	}

	@Test
	public void findClassesShouldDetectClassesInScannedPackage()
	{
		final Set<Class<?>> classes = scanner.findClasses( SCANNED );

		assertTrue( classes.contains( LastBean.class ) );
		assertFalse( classes.contains( TheInterfaceUser.class ) );
	}

	@Test
	public void findClassesShouldNotIncludeInterfaces()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isInterface() );
		}
	}

	@Test
	public void findClassesShouldNotIncludeAnnotations()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isAnnotation() );
		}
	}

	@Test
	public void findClassesShouldNotIncludeEnums()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isEnum() );
		}
	}

	@Test
	public void findClassesShouldNotIncludeSyntheticClasses()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isSynthetic() );
		}
	}

	@Test
	public void findClassesShouldNotIncludeAnonymousClasses()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isAnonymousClass() );
		}
	}

	@Test
	public void findClassesShouldNotIncludeInnerClasses()
	{
		final Set<Class<?>> classes = scanner.findClasses( ALL );

		for ( final Class<?> class1 : classes )
		{
			assertFalse( class1.isMemberClass() );
		}
	}
}
