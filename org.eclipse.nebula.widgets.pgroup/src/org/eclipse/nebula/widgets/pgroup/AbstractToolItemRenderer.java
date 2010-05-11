/*******************************************************************************
 * Copyright (c) 2010 Ubiquiti Networks, Inc. and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.pgroup;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public abstract class AbstractToolItemRenderer extends AbstractRenderer {

	public final void paint(GC gc, Object value) {
		Object[] tmp = (Object[]) value;
		doPaint(gc, (PGroupToolItem)tmp[0], ((Boolean)tmp[1]).booleanValue());
	}

	protected abstract void doPaint(GC gc, PGroupToolItem item, boolean min);

	public abstract Point[] calculateSizes(GC gc, PGroupToolItem item);
	public abstract Rectangle calcDropDownArea(Rectangle totalRect);
}
