
/***************************************************************************
 *   Copyright 2006-2009 by Christian Ihle                                 *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package net.usikkert.kouinject.testbeans.scanned;

import net.usikkert.kouinject.annotation.Component;
import net.usikkert.kouinject.annotation.Inject;

/**
 *
 * @author Christian Ihle
 */
@Component
public class SetterBean
{
	private FieldBean fieldBean;

	@Inject
	public void setFieldBean( final FieldBean fieldBean )
	{
		this.fieldBean = fieldBean;
	}

	public FieldBean getFieldBean()
	{
		return fieldBean;
	}
}
