
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Provider;

import net.usikkert.kouinject.AnnotationBasedQualifierHandler;
import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.TypeLiteral;
import net.usikkert.kouinject.beandata.BeanKey;
import net.usikkert.kouinject.beandata.CollectionBeanKey;
import net.usikkert.kouinject.beandata.CollectionProviderBeanKey;
import net.usikkert.kouinject.beandata.ProviderBeanKey;

import org.apache.commons.lang.Validate;

/**
 * Helper methods for extracting meta-data from beans, using reflection.
 *
 * @author Christian Ihle
 */
public class BeanHelper {

    private final AnnotationBasedQualifierHandler qualifierHandler = new AnnotationBasedQualifierHandler();
    private final GenericsHelper genericsHelper = new GenericsHelper();

    /**
     * Finds the return type of the factory method. The return type includes the qualifier on the method.
     *
     * @param factoryMethod The factory method to find the return type of.
     * @return The return type, as a {@link BeanKey}.
     */
    public BeanKey findFactoryReturnType(final Method factoryMethod) {
        Validate.notNull(factoryMethod, "Factory method can not be null");

        final Type genericReturnType = factoryMethod.getGenericReturnType();

        if (genericReturnType.equals(Void.TYPE)) {
            throw new UnsupportedOperationException("Can't return void from a factory method: " + factoryMethod);
        }

        final Annotation[] annotations = factoryMethod.getAnnotations();
        final String qualifier = qualifierHandler.getQualifier(factoryMethod, annotations);
        final TypeLiteral<Object> beanType = new TypeLiteral<Object>(genericReturnType) {};

        return new BeanKey(beanType, qualifier);
    }

    /**
     * Gets all the parameters of a method, with associated qualifiers, as a list of bean keys.
     *
     * @param method The method to find the parameters of.
     * @return The methods parameters as bean keys.
     */
    public List<BeanKey> findParameterKeys(final Method method) {
        Validate.notNull(method, "Method can not be null");

        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        return findParameterKeys(method, parameterTypes, genericParameterTypes, parameterAnnotations);
    }

    /**
     * Gets all the parameters of a constructor, with associated qualifiers, as a list of bean keys.
     *
     * @param constructor The constructor to find the parameters of.
     * @return The constructor parameters as bean keys.
     */
    public List<BeanKey> findParameterKeys(final Constructor<?> constructor) {
        Validate.notNull(constructor, "Constructor can not be null");

        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Type[] genericParameterTypes = constructor.getGenericParameterTypes();
        final Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();

        return findParameterKeys(constructor, parameterTypes, genericParameterTypes, parameterAnnotations);
    }

    /**
     * Gets the type from the field, with associated qualifier, as a bean key.
     *
     * @param field The field to find the type of.
     * @return The field as a bean key.
     */
    public BeanKey findFieldKey(final Field field) {
        Validate.notNull(field, "Field can not be null");

        final Class<?> type = field.getType();
        final Type genericType = field.getGenericType();
        final Annotation[] annotations = field.getAnnotations();

        return findParameterKey(field, type, genericType, annotations);
    }

    private List<BeanKey> findParameterKeys(final Object parameterOwner, final Class<?>[] parameterTypes,
                                            final Type[] genericParameterTypes, final Annotation[][] annotations) {
        final List<BeanKey> parameters = new ArrayList<BeanKey>();

        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterClass = parameterTypes[i];
            final Type parameterType = genericParameterTypes[i];

            final BeanKey parameter = findParameterKey(parameterOwner, parameterClass, parameterType, annotations[i]);
            parameters.add(parameter);
        }

        return parameters;
    }

    private BeanKey findParameterKey(final Object parameterOwner, final Class<?> parameterClass,
                                     final Type parameterType, final Annotation[] annotations) {
        final String qualifier = qualifierHandler.getQualifier(parameterOwner, annotations);

        if (isProvider(parameterClass)) {
            final TypeLiteral<?> beanTypeFromProvider = getBeanTypeFromGenericType(parameterOwner, parameterType);
            return new ProviderBeanKey(beanTypeFromProvider, qualifier);
        }

        else if (isCollection(parameterClass)) {
            final TypeLiteral<?> beanTypeFromCollection = getBeanTypeFromGenericType(parameterOwner, parameterType);
            return new CollectionBeanKey(beanTypeFromCollection, qualifier);
        }

        else if (isCollectionProvider(parameterClass)) {
            final TypeLiteral<?> beanTypeFromCollectionProvider = getBeanTypeFromGenericType(parameterOwner, parameterType);
            return new CollectionProviderBeanKey(beanTypeFromCollectionProvider, qualifier);
        }

        else {
            final TypeLiteral<?> beanType = new TypeLiteral<Object>(parameterType) {};
            return new BeanKey(beanType, qualifier);
        }
    }

    private TypeLiteral<?> getBeanTypeFromGenericType(final Object parameterOwner, final Type genericParameterType) {
        if (genericsHelper.isParameterizedType(genericParameterType)) {
            final Type genericArgumentAsType = genericsHelper.getGenericArgumentAsType(genericParameterType);
            return new TypeLiteral<Object>(genericArgumentAsType) {};
        }

        throw new IllegalArgumentException("Generic class used without type argument: " + parameterOwner);
    }

    private boolean isProvider(final Class<?> parameterType) {
        return Provider.class.equals(parameterType);
    }

    private boolean isCollection(final Class<?> parameterType) {
        return Collection.class.equals(parameterType);
    }

    private boolean isCollectionProvider(final Class<?> parameterType) {
        return CollectionProvider.class.equals(parameterType);
    }
}
