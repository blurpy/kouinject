
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

package net.usikkert.kouinject.testbeans.scanned.generics.qualifier;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;

/**
 * A bean for testing injection of generic beans with qualifiers.
 *
 * @author Christian Ihle
 */
@Component
public class DaoControllerBean {

    private final Dao<MySqlDriver> orderDao;
    private final Dao<MySqlDriver> personDao;
    private final Dao<MySqlDriver> itemDao;
    private final Dao<OracleDriver> blogDao;
    private Collection<Dao<MySqlDriver>> mySqlDaos;
    private Collection<Dao<? extends DatabaseDriver>> allDaos;

    @Inject
    public DaoControllerBean(@Named("order") final Dao<MySqlDriver> orderDao,
                             @Named("person") final Dao<MySqlDriver> personDao,
                             @Named("item") final Dao<MySqlDriver> itemDao,
                             @Named("blog") final Dao<OracleDriver> blogDao) {
        this.orderDao = orderDao;
        this.personDao = personDao;
        this.itemDao = itemDao;
        this.blogDao = blogDao;
    }

    @Inject
    public void setCollections(@Any final Collection<Dao<MySqlDriver>> mySqlDaos,
                               @Any final Collection<Dao<? extends DatabaseDriver>> allDaos) {
        this.mySqlDaos = mySqlDaos;
        this.allDaos = allDaos;
    }

    public Dao<MySqlDriver> getOrderDao() {
        return orderDao;
    }

    public Dao<MySqlDriver> getPersonDao() {
        return personDao;
    }

    public Dao<MySqlDriver> getItemDao() {
        return itemDao;
    }

    public Dao<OracleDriver> getBlogDao() {
        return blogDao;
    }

    public Collection<Dao<MySqlDriver>> getMySqlDaos() {
        return mySqlDaos;
    }

    public Collection<Dao<? extends DatabaseDriver>> getAllDaos() {
        return allDaos;
    }
}
