
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

package net.usikkert.kouinject.testbeans.notscanned.factory;

import java.util.Collection;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import net.usikkert.kouinject.CollectionProvider;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.testbeans.notscanned.SomeEnum;
import net.usikkert.kouinject.testbeans.notscanned.TheInterface;
import net.usikkert.kouinject.testbeans.notscanned.TheInterfaceUser;
import net.usikkert.kouinject.testbeans.notscanned.date.DateBean;
import net.usikkert.kouinject.testbeans.scanned.CarBean;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.CoffeeBean;
import net.usikkert.kouinject.testbeans.scanned.coffee.JavaBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.NoBean;
import net.usikkert.kouinject.testbeans.scanned.notloaded.QualifierBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Blue;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.Green;
import net.usikkert.kouinject.testbeans.scanned.qualifier.GreenBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.YellowBean;

/**
 * A factory bean with test methods.
 *
 * TODO rename
 *
 * @author Christian Ihle
 */
public class FactoryBean {

    private TheInterface field;

    @Named("ping")
    private TheInterfaceUser fieldWithQualifier;


    private Provider<RedBean> provider;

    @Named("great")
    private Provider<NoBean> providerWithQualifier;

    private Provider providerWithoutGenericType;

    private Provider<?> providerWithWildCard;


    private CollectionProvider<YellowBean> collectionProvider;

    @Named("awesome")
    private CollectionProvider<QualifierBean> collectionProviderWithQualifier;

    private CollectionProvider collectionProviderWithoutGenericType;

    private CollectionProvider<?> collectionProviderWithWildCard;


    private Collection<GreenBean> collection;

    @Named("best")
    private Collection<CarBean> collectionWithQualifier;

    private Collection collectionWithoutGenericType;

    private Collection<?> collectionWithWildCard;

    @Produces
    public void voidFactoryMethod() {

    }

    @Produces
    public HelloBean helloBeanFactoryMethod() {
        return null;
    }

    @Produces @Blue @Singleton
    public JavaBean scopedAndQualifiedFactoryMethod() {
        return null;
    }

    public void methodWithNoParameters() {

    }

    public void methodWithSingleParameter(final TheInterface theInterface) {

    }

    private void privateMethod(final SomeEnum someEnum) {

    }

    public void methodWithQualifiedParameter(@Named("ping") final TheInterfaceUser theInterfaceUser) {

    }

    public void methodWithSeveralParameters(final DateBean dateBean,
                                            @Green final ColorBean colorBean,
                                            @Named("excellent") final CoffeeBean coffeeBean) {

    }

    public void methodWithProvider(final Provider<RedBean> redBeanProvider,
                                   @Named("great") final Provider<NoBean> noBeanProvider) {

    }

    public void methodWithProviderWithoutGenericType(final Provider provider) {

    }

    public void methodWithProviderWithGenericWildCard(final Provider<?> provider) {

    }

    public void methodWithCollectionProvider(final CollectionProvider<YellowBean> yellowBeanCollectionProvider,
                                             @Named("awesome") final CollectionProvider<QualifierBean> qualifierBeanCollectionProvider) {

    }

    public void methodWithCollectionProviderWithoutGenericType(final CollectionProvider collectionProvider) {

    }

    public void methodWithCollectionProviderWithGenericWildCard(final CollectionProvider<?> collectionProvider) {

    }

    public void methodWithCollection(final Collection<GreenBean> greenBeanCollection,
                                     @Named("best") final Collection<CarBean> carBeanCollection) {

    }

    public void methodWithCollectionWithoutGenericType(final Collection collection) {

    }

    public void methodWithCollectionWithGenericWildCard(final Collection<?> collection) {

    }
}
