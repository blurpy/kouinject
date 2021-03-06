
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;

import net.usikkert.kouinject.util.ReflectionUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Finds classes by scanning the classpath. Classes are searched for in the file system and in
 * jar-files.
 *
 * TODO: refactor into smaller pieces.
 *
 * @author Christian Ihle
 */
public class ClassPathScanner implements ClassLocator {

    private static final Logger LOG = Logger.getLogger(ClassPathScanner.class.getName());

    private final ReflectionUtils reflectionUtils = new ReflectionUtils();

    private final ClassLoader classLoader;

    /**
     * Creates a new classpath scanner using the threads context classloader, or the classloader for this class.
     */
    public ClassPathScanner() {
        this.classLoader = getClassLoader();
    }

    /**
     * Creates a new classpath scanner using the specified classloader.
     *
     * @param classLoader The classloader to use to scan for classes, and load the classes.
     */
    public ClassPathScanner(final ClassLoader classLoader) {
        Validate.notNull(classLoader, "Class loader can not be null");
        this.classLoader = classLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Class<?>> findClasses(final String... basePackages) {
        Validate.notNull(basePackages, "Base packages can not be null");

        final long start = System.currentTimeMillis();
        final Set<Class<?>> classes = findClassesFromSetOfBasePackages(basePackages);
        final long stop = System.currentTimeMillis();

        LOG.fine("Time spent scanning classpath: " + (stop - start) + " ms");
        LOG.fine("Classes found: " + classes.size());

        return classes;
    }

    private Set<Class<?>> findClassesFromSetOfBasePackages(final String... basePackages) {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        final Set<String> basePackageSet = convertBasePackagesToSet(basePackages);

        for (final String basePackage : basePackageSet) {
            classes.addAll(findClassesFromBasePackage(basePackage));
        }

        return classes;
    }

    private Set<String> convertBasePackagesToSet(final String... basePackages) {
        Validate.notNull(basePackages, "Base packages can not be null");
        Validate.isTrue(basePackages.length > 0, "Must have at least one base package");
        final Set<String> basePackageSet = new HashSet<String>();

        for (final String basePackage : basePackages) {
            Validate.isTrue(StringUtils.isNotBlank(basePackage), "Base package can not be empty");
            basePackageSet.add(basePackage);
        }

        return basePackageSet;
    }

    private Set<Class<?>> findClassesFromBasePackage(final String basePackage) {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        final String path = basePackage.replace('.', '/');

        try {
            final Enumeration<URL> resources = classLoader.getResources(path);

            if (resources != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        // hasMoreElements requires java.io.FilePermission "read" to find anything
                        while (resources.hasMoreElements()) {
                            classes.addAll(getClassesFromResource(basePackage, path, resources));
                        }

                        return null;
                    }
                });
            }
        }

        catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return classes;
    }

    private Set<Class<?>> getClassesFromResource(final String basePackage, final String path, final Enumeration<URL> resources) {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        final String filePath = getFilePath(resources.nextElement());

        if (filePath != null) {
            if (isJarFilePath(filePath)) {
                final String jarPath = getJarPath(filePath);
                classes.addAll(getFromJARFile(jarPath, path));
            }

            else {
                classes.addAll(getFromDirectory(new File(filePath), basePackage));
            }
        }

        return classes;
    }

    private Set<Class<?>> getFromDirectory(final File directory, final String packageName) {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        if (directory.exists()) {
            final File[] files = directory.listFiles();

            for (final File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(getFromDirectory(file, packageName + "." + file.getName()));
                }

                else if (isClass(file.getName())) {
                    final String className = packageName + '.' + stripFilenameExtension(file.getName());
                    final Class<?> clazz = loadClass(className);
                    addClass(clazz, classes);
                }
            }
        }

        return classes;
    }

    private Set<Class<?>> getFromJARFile(final String jar, final String packageName) {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        JarInputStream jarFile = null;

        try {
            jarFile = new JarInputStream(new FileInputStream(jar));
            JarEntry jarEntry;

            do {
                jarEntry = jarFile.getNextJarEntry();

                if (jarEntry != null) {
                    final String fileName = jarEntry.getName();

                    if (isClass(fileName)) {
                        final String className = stripFilenameExtension(fileName);

                        if (className.startsWith(packageName)) {
                            final Class<?> clazz = loadClass(className.replace('/', '.'));
                            addClass(clazz, classes);

                        }
                    }
                }
            } while (jarEntry != null);
        }

        catch (final IOException e) {
            throw new RuntimeException(e);
        }

        finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                }

                catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return classes;
    }

    /**
     * Gets the best possible classloader for scanning after classes. Usually it's the current
     * thread's context classloader, but if that's not available then the classloader for this class
     * is used instead.
     *
     * @return A usable classloader.
     */
    private ClassLoader getClassLoader() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        if (contextClassLoader != null) {
            return contextClassLoader;
        }

        else {
            return getClass().getClassLoader();
        }
    }

    private String getFilePath(final URL url) {
        final String filePath = url.getFile();

        if (filePath != null) {
            return fixWindowsSpace(filePath);
        }

        return null;
    }

    private boolean isJarFilePath(final String filePath) {
        return (filePath.indexOf("!") > 0) && (filePath.indexOf(".jar") > 0);
    }

    private String fixWindowsSpace(final String filePath) {
        if (filePath.indexOf("%20") > 0) {
            return filePath.replaceAll("%20", " ");
        }

        return filePath;
    }

    private String getJarPath(final String filePath) {
        final String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
        return fixWindowsJarPath(jarPath);
    }

    private String fixWindowsJarPath(final String jarPath) {
        if (jarPath.indexOf(":") >= 0) {
            return jarPath.substring(1);
        }

        return jarPath;
    }

    private static String stripFilenameExtension(final String filename) {
        if (filename == null) {
            return null;
        }

        final int dotIndex = filename.lastIndexOf(".");

        if (dotIndex == -1) {
            return filename;
        }

        return filename.substring(0, dotIndex);
    }

    private boolean isClass(final String fileName) {
        return fileName.endsWith(".class");
    }

    private void addClass(final Class<?> clazz, final Set<Class<?>> classes) {
        if (reflectionUtils.isNormalClass(clazz)) {
            classes.add(clazz);
        }
    }

    private Class<?> loadClass(final String className) {
        try {
            return Class.forName(className, false, classLoader);
        }

        catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
