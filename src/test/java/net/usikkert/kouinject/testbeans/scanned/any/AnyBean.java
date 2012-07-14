
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

package net.usikkert.kouinject.testbeans.scanned.any;

import java.util.Collection;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.HelloBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.ColorBean;
import net.usikkert.kouinject.testbeans.scanned.qualifier.RedBean;

/**
 * Bean for testing that @Any can be used to get beans with or without a qualifier.
 *
 * @author Christian Ihle
 */
@Component
public class AnyBean {

    @Inject @Any
    private HelloBean helloBean;

    private RedBean redBean;
    private final Collection<HelloBean> helloBeanCollection;
    private final Collection<RedBean> theRedBeanCollection;
    private Collection<ColorBean> colorBeans;

    @Inject
    public AnyBean(@Any final Collection<HelloBean> helloBeanCollection,
                    @Any final Collection<RedBean> theRedBeanCollection) {
        this.helloBeanCollection = helloBeanCollection;
        this.theRedBeanCollection = theRedBeanCollection;
    }

    @Inject
    public void setAnyBeans(@Any final RedBean theRedBean, @Any final Collection<ColorBean> theColorBeans) {
        this.redBean = theRedBean;
        this.colorBeans = theColorBeans;
    }

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public RedBean getRedBean() {
        return redBean;
    }

    public Collection<HelloBean> getHelloBeanCollection() {
        return helloBeanCollection;
    }

    public Collection<RedBean> getTheRedBeanCollection() {
        return theRedBeanCollection;
    }

    public Collection<ColorBean> getColorBeans() {
        return colorBeans;
    }
}
