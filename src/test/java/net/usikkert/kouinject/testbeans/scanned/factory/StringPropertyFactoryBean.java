
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

package net.usikkert.kouinject.testbeans.scanned.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.usikkert.kouinject.annotation.Any;
import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Produces;
import net.usikkert.kouinject.factory.FactoryContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

/**
 * Test of using {@link FactoryContext} in a factory method.
 *
 * @author Christian Ihle
 */
@Component
public class StringPropertyFactoryBean {

    private static final String PROPERTY_FILE = "FactoryTestProperties.properties";

    private final Properties properties;

    public StringPropertyFactoryBean() {
        properties = new Properties();
        loadPropertyFile();
    }

    @Produces @Any
    public String createStringProperty(final FactoryContext factoryContext) {
        final String qualifier = factoryContext.getQualifier();
        final String property = properties.getProperty(qualifier);
        Validate.notNull(property, "Property '" + qualifier + "' was not found in " + PROPERTY_FILE);

        return property;
    }

    private void loadPropertyFile() {
        InputStream resourceAsStream = null;

        try {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
            Validate.notNull(resourceAsStream, "Could not find property file: " + PROPERTY_FILE);

            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load property file: " + PROPERTY_FILE, e);
        } finally {
            IOUtils.closeQuietly(resourceAsStream);
        }
    }
}
