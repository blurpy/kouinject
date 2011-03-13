
/***************************************************************************
 *   Copyright 2009-2011 by Christian Ihle                                 *
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

package net.usikkert.kouinject.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class with utility methods for the reflection api.
 *
 * @author Christian Ihle
 */
public class ReflectionUtils {

    public List<Method> findAllMethods(final Class<?> clazz) {
        final List<Method> methods = new ArrayList<Method>();

        if (clazz.equals(Object.class)) {
            return methods;
        }

        final Method[] declaredMethods = clazz.getDeclaredMethods();
        methods.addAll(Arrays.asList(declaredMethods));

        if (!clazz.getSuperclass().equals(Object.class)) {
            methods.addAll(findAllMethods(clazz.getSuperclass()));
        }

        return methods;
    }

    /**
     * Gets all members from a class and its superclasses.
     *
     * <p>The members are sorted from superclass to subclass, with fields
     * and methods in the superclass first, then fields and methods in subclasses.</p>
     *
     * @param clazz The class to find the members in.
     * @return List with all the members found.
     */
    public List<Member> findAllMembers(final Class<?> clazz) {
        final List<Member> members = new ArrayList<Member>();

        if (clazz.equals(Object.class)) {
            return members;
        }

        final Field[] declaredFields = clazz.getDeclaredFields();
        members.addAll(Arrays.asList(declaredFields));

        final Method[] declaredMethods = clazz.getDeclaredMethods();
        members.addAll(Arrays.asList(declaredMethods));

        if (!clazz.getSuperclass().equals(Object.class)) {
            members.addAll(0, findAllMembers(clazz.getSuperclass()));
        }

        return members;
    }

    public boolean isOverridden(final Method method, final List<Method> candidates) {
        for (final Method candidate : candidates) {
            if (isOverridden(method, candidate)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a method has been overridden by a candidate.
     *
     * <p>Rules for overriding:</p>
     * <ul>
     *   <li>Candidate must be in a subclass of the class with method.</li>
     *   <li>The method names must be the same.</li>
     *   <li>The parameters must be the same.</li>
     *   <li>The return type must be the same or a subclass.</li>
     *   <li>Access modifier must be same or less restrictive.</li>
     *   <li>Method can not be private, final or static.</li>
     *   <li>Candidate can not be private or static.</li>
     *   <li>Candidate must be in the same package as a method with default access.</li>
     * </ul>
     *
     * @param method The method that will be checked if it's been overridden.
     * @param candidate The method to check if it's overriding.
     * @return If method has been overridden by candidate.
     */
    public boolean isOverridden(final Method method, final Method candidate) {
        if (!hasOverridableAccessModifiers(method, candidate)) {
            return false;
        }

        if (!isSubClassOf(candidate.getDeclaringClass(), method.getDeclaringClass())) {
            return false;
        }

        if (!hasTheSameName(method, candidate)) {
            return false;
        }

        if (!hasTheSameParameters(method, candidate)) {
            return false;
        }

        return true;
    }

    public boolean hasOverridableAccessModifiers(final Method method, final Method candidate) {
        if (isFinal(method) || isPrivate(method) || isStatic(method)
                || isPrivate(candidate) || isStatic(candidate)) {
            return false;
        }

        if (isDefault(method)) {
            return isInTheSamePackage(method, candidate);
        }

        return true;
    }

    public boolean isSubClassOf(final Class<?> subclass, final Class<?> superclass) {
        if (subclass.getSuperclass() != null) {
            if (subclass.getSuperclass().equals(superclass)) {
                return true;
            }

            return isSubClassOf(subclass.getSuperclass(), superclass);
        }

        return false;
    }

    public boolean hasTheSameName(final Method method, final Method candidate) {
        return method.getName().equals(candidate.getName());
    }

    public boolean hasTheSameParameters(final Method method, final Method candidate) {
        final Class<?>[] methodParameters = method.getParameterTypes();
        final Class<?>[] candidateParameters = candidate.getParameterTypes();

        if (methodParameters.length != candidateParameters.length) {
            return false;
        }

        for (int i = 0; i < methodParameters.length; i++) {
            final Class<?> methodParameter = methodParameters[i];
            final Class<?> candidateParameter = candidateParameters[i];

            if (!methodParameter.equals(candidateParameter)) {
                return false;
            }
        }

        return true;
    }

    public boolean isInTheSamePackage(final Method method, final Method candidate) {
        final Package methodPackage = method.getDeclaringClass().getPackage();
        final Package candidatePackage = candidate.getDeclaringClass().getPackage();

        return methodPackage.equals(candidatePackage);
    }

    public boolean isStatic(final Member member) {
        return Modifier.isStatic(member.getModifiers());
    }

    public boolean isFinal(final Member member) {
        return Modifier.isFinal(member.getModifiers());
    }

    public boolean isPrivate(final Member member) {
        return Modifier.isPrivate(member.getModifiers());
    }

    public boolean isDefault(final Member member) {
        return !isPublic(member) && !isProtected(member) && !isPrivate(member);
    }

    public boolean isProtected(final Member member) {
        return Modifier.isProtected(member.getModifiers());
    }

    public boolean isPublic(final Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    public boolean isAbstract(final Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public boolean isNormalClass(final Class<?> clazz) {
        return !clazz.isAnonymousClass() && !clazz.isMemberClass() && !clazz.isSynthetic()
        && !clazz.isAnnotation() && !clazz.isEnum() && !clazz.isInterface() && !isAbstract(clazz);
    }
}
