
/***************************************************************************
 *   Copyright 2009 by Christian Ihle                                      *
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

package net.usikkert.kouinject.testbeans.scanned;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Inject;

/**
 *
 * @author Christian Ihle
 */
@Component
public class ConstructorBean
{
	private final SetterBean setterBean;

	private final HelloBean helloBean;

	@Inject
	public ConstructorBean( final SetterBean setterBean, final HelloBean helloBean )
	{
		this.setterBean = setterBean;
		this.helloBean = helloBean;
	}

	public ConstructorBean()
	{
		setterBean = null;
		helloBean = null;
	}

	public HelloBean getHelloBean()
	{
		return helloBean;
	}

	public SetterBean getSetterBean()
	{
		return setterBean;
	}
}