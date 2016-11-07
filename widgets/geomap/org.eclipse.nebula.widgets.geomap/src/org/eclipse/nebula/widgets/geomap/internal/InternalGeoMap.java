/*******************************************************************************
 * Copyright (c) 2008, 2012 Stepan Rutz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stepan Rutz - initial implementation
 *    Hallvard Trætteberg - further cleanup and development
 *******************************************************************************/

package org.eclipse.nebula.widgets.geomap.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>License is EPL (Eclipse Public License) http://www.eclipse.org/legal/epl-v10.html.  Contact at stepan.rutz@gmx.de</p>
 *
 * @author stepan.rutz, hal
 * @version $Revision$
 */
public class InternalGeoMap extends Canvas implements GeoMapPositioned, GeoMapHelperListener {
    
    void redraw(TileRef tile) {
    	redraw();
    }

    /**
     * The helper object for loading images.
     */
    protected GeoMapHelper geoMapHelper;

    /**
     * Initializes a new <code>InternalGeoMap</code>.
     * @param parent SWT parent <code>Composite</code>
     * @param style SWT style as in <code>Canvas</code>, since this class inherits from it. Double buffering is always enabed.
     * @param mapPosition initial mapPosition.
     * @param zoom initial map zoom
     * @param cacheSize initial cache size, eg number of tile-images that are kept in cache
     * to prevent reloading from the network.
     */
    protected InternalGeoMap(Composite parent, int style, Point mapPosition, int zoom, int cacheSize) {    
        super(parent, SWT.DOUBLE_BUFFERED | style);
        geoMapHelper = new GeoMapHelper(parent.getDisplay(), mapPosition, zoom, cacheSize);
        geoMapHelper.addGeoMapHelperListener(this);

        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
               InternalGeoMap.this.geoMapHelper.dispose();
            }
        });
        addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                InternalGeoMap.this.paintControl(e);
            }
        });
    }

	public void tileUpdated(TileRef tileRef) {
		redraw();
	}

    private void paintControl(PaintEvent e) {
    	geoMapHelper.paint(e.gc, new Rectangle(e.x, e.y, e.width, e.height), getSize());
    }

    /**
     * Adds an InternalGeoMapListener    
     * @param listener
     */
    public void addInternalGeoMapListener(InternalGeoMapListener listener) {
    	geoMapHelper.addInternalGeoMapListener(listener);
    }

    /**
     * Removes an InternalGeoMapListener    
     * @param listener
     */
    public void removeInternalGeoMapListener(InternalGeoMapListener listener) {
    	geoMapHelper.removeInternalGeoMapListener(listener);
    }

    // GeoMapPositioned methods

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.geomap.internal.GeoMapPositioned#getMapPosition()
	 */
	public Point getMapPosition() {
		return geoMapHelper.getMapPosition();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.geomap.internal.GeoMapPositioned#setMapPosition(int, int)
	 */
	public void setMapPosition(int x, int y) {
		geoMapHelper.setMapPosition(x, y);
		redraw();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.geomap.internal.GeoMapPositioned#getZoom()
	 */
	public int getZoom() {
		return geoMapHelper.getZoom();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.geomap.internal.GeoMapPositioned#getMaxZoom()
	 */
	public int getMaxZoom() {
		return geoMapHelper.getMaxZoom();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.geomap.internal.GeoMapPositioned#setZoom(int)
	 */
	public void setZoom(int zoom) {
        if (zoom == getZoom()) {
        	return;
        }
		geoMapHelper.setZoom(zoom);
		redraw();
	}
}
