/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http\://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors\:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.nebula.widgets.geomap;

import org.eclipse.nebula.widgets.geomap.internal.URLService;

/**
 * This class encapsulates a tileserver, which has the concept
 * of a baseurl and a maximum zoom level. 
 */
public class TileServer extends URLService {

    private final int maxZoom;
    private boolean broken;

    public TileServer(String url, int maxZoom, String urlFormat) {
    	super(url, urlFormat);
        this.maxZoom = maxZoom;
    }

    public TileServer(String url, int maxZoom) {
    	parseUrl(url, "{0}/{1}/{2}.png");
    	this.maxZoom = maxZoom;
    }

    @Override
    protected Object[] getURLFormatArguments(Object ref) {
    	TileRef tile = (TileRef) ref;
    	return new Object[]{String.valueOf(tile.z), String.valueOf(tile.x), String.valueOf(tile.y)};
    }
    
    public String getTileURL(TileRef tile) {
    	return super.getServiceURL(tile);
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}