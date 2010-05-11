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

/**
 * Base implementation for rendering ToolItems in the header
 */
public abstract class AbstractToolItemRenderer extends AbstractRenderer {
	private boolean min;

	public static final int DEFAULT = 1;
	public static final int MIN     = 2;

	public void setMin(boolean min) {
		this.min = min;
	}

	public boolean isMin() {
		return min;
	}

	public abstract void paint(GC gc, Object value);

	public abstract Point calculateSize(GC gc, PGroupToolItem item, int type);
	public abstract Rectangle calculateDropDownArea(Rectangle totalRect);
}
