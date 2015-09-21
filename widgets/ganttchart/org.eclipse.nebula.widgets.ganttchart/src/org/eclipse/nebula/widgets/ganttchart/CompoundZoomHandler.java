/*******************************************************************************
 * Copyright (c) 2013 Dirk Fauth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.ganttchart;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;

public class CompoundZoomHandler implements IZoomHandler {

	private List<IZoomHandler> handler = new ArrayList<IZoomHandler>();
	
	public void addHandler(IZoomHandler handler) {
		this.handler.add(handler);
	}
	
	public void removeHandler(IViewPortHandler handler) {
		this.handler.remove(handler);
	}
	
	public void zoomIn() {
		for (IZoomHandler vph : this.handler) {
			vph.zoomIn();
		}
	}
	
	public void zoomIn(boolean fromMouseWheel, Point mouseLoc) {
		for (IZoomHandler vph : this.handler) {
			vph.zoomIn(fromMouseWheel, mouseLoc);
		}
	}

	public void zoomOut() {
		for (IZoomHandler vph : this.handler) {
			vph.zoomOut();
		}
	}
	
	public void zoomOut(boolean fromMouseWheel, Point mouseLoc) {
		for (IZoomHandler vph : this.handler) {
			vph.zoomOut(fromMouseWheel, mouseLoc);
		}
	}

	public void resetZoom() {
		for (IZoomHandler vph : this.handler) {
			vph.resetZoom();
		}
	}

}
