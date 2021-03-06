
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

package net.usikkert.kouinject.testbeans.scanned.generics.thing;

import java.util.Collection;

import javax.inject.Inject;

import net.usikkert.kouinject.annotation.Component;

/**
 * A bean for testing injection of beans with generics.
 *
 * @author Christian Ihle
 */
@Component
public class ThingManagerBean {

    private final Collection<ThingListenerBean<StartThing>> startThingListeners;

    @Inject
    private ThingListenerBean<MiddleThing> middleThingListener;

    private ThingListenerBean<StopThing> stopThingListener;

    @Inject
    public ThingManagerBean(final Collection<ThingListenerBean<StartThing>> startThingListeners) {
        this.startThingListeners = startThingListeners;
    }

    @Inject
    public void setStopThingListener(final ThingListenerBean<StopThing> stopThingListener) {
        this.stopThingListener = stopThingListener;
    }

    public ThingListenerBean<StopThing> getStopThingListener() {
        return stopThingListener;
    }

    public Collection<ThingListenerBean<StartThing>> getStartThingListeners() {
        return startThingListeners;
    }

    public ThingListenerBean<MiddleThing> getMiddleThingListener() {
        return middleThingListener;
    }
}
