
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

package net.usikkert.kouinject.testbeans.scanned.folder.folder3;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.testbeans.scanned.folder.folder1.Folder1Bean;
import net.usikkert.kouinject.testbeans.scanned.folder.folder2.Folder2Bean;

import javax.inject.Inject;

/**
 * Bean used for testing correct dependency injection when using multiple base packages.
 *
 * @author Christian Ihle
 */
@Component
public class Folder3Bean {

    @Inject
    private Folder1Bean folder1Bean;

    @Inject
    private Folder2Bean folder2Bean;

    public Folder1Bean getFolder1Bean() {
        return folder1Bean;
    }

    public Folder2Bean getFolder2Bean() {
        return folder2Bean;
    }
}
