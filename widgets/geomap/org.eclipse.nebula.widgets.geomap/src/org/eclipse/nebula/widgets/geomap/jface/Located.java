/*******************************************************************************
 * Copyright (c) 2012 Hallvard Tr¾tteberg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Hallvard Tr¾tteberg - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.geomap.jface;

import org.eclipse.nebula.widgets.geomap.PointD;

/**
 * Interface to provide a location for an object, as a PointD with longitude, latitude coordinates.
 * 
 */
public interface Located {
	/*
	 * Returns the longitude, latitude for this object
	 * or null if this object doesn't have a location.
	 */
	public PointD getLonLat();

	/*
	 * Set the longitude, latitude for this object.
	 * Returns true if the change occurred, i.e. the operation was legal.
	 * Use setLonLat(getLonLat().x, getLonLat().y) to check without side-effect.
	 */
	public boolean setLonLat(double lon, double lat);
	
	public abstract class Static implements Located {

		public boolean setLonLat(double lon, double lat) {
			return false;
		}
		
	}
}
