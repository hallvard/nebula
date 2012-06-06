/*******************************************************************************
 * Copyright (c) 2006-2009 Nicolas Richeton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors :
 *    Nicolas Richeton (nicolas.richeton@gmail.com) - initial API and implementation
 *******************************************************************************/

package org.eclipse.nebula.widgets.geomap.animation;

import org.eclipse.nebula.cwt.animation.effects.AbstractEffect;
import org.eclipse.nebula.cwt.animation.movement.IMovement;
import org.eclipse.nebula.widgets.geomap.GeoMap;
import org.eclipse.swt.graphics.Point;

public class CenterOn extends AbstractEffect {

	private final Point startPosition, endPosition;
	private int endZoom;

	private final GeoMap geoMap;

	private CenterOn(GeoMap geoMap, Point startPosition, Point endPosition, int endZoom, long duration, IMovement movement, Runnable onStop, Runnable onCancel) {
		super(duration, movement, onStop, onCancel);
		
		this.geoMap = geoMap;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.endZoom = endZoom;

		easingFunction.init(0, 1, (int) duration);
	}
	
	private static double duration(Point from, Point to) {
		double dx = to.x - from.x, dy = to.y - from.y;
		double duration = Math.log10(Math.sqrt(dx * dx + dy * dy)) * 200;
		System.out.println("Duration: " + duration);
		return duration;
	}
	
	public CenterOn(GeoMap geoMap, Point endPosition, IMovement movement, Runnable onStop, Runnable onCancel) {
		this(geoMap, geoMap.getCenterPosition(), endPosition, geoMap.getZoom(), (int) duration(geoMap.getCenterPosition(), endPosition), movement, onStop, onCancel);
	}

	private long lastTime = -1;
	
	public void applyEffect(final long currentTime) {
		if (geoMap.isDisposed()) {
			return;
		}
		double d = easingFunction.getValue((int) currentTime);
		int dz = endZoom - geoMap.getZoom();
		int nextX = (int) (startPosition.x + (endPosition.x - startPosition.x) * d) >> dz, nextY = (int) (startPosition.y + (endPosition.y - startPosition.y) * d) >> dz;
		Point mapPosition = geoMap.getMapPosition(), currentPosition = geoMap.getCenterPosition();
		int dx = nextX - currentPosition.x, dy = nextY - currentPosition.y;
		if (lastTime >= 0) {
			long dt = currentTime - lastTime;
			int dd = (int) Math.sqrt(dx * dx + dy * dy);
//			System.out.println("dt=" + dt + " dd=" + dd + " dt*10/dd=" + dt * 10.0 / dd);
			if (dd > dt && dt * 10 / dd < 4) {
				System.out.println("Zoom out: " + dt / dd);
				geoMap.zoomOut(new Point(currentPosition.x - mapPosition.x, currentPosition.y - mapPosition.y));
				nextX >>= 1;
				nextY >>= 1;
			} else if (dd > 10 && dz > 0) {
				System.out.println("Zoom in: " + dd);
				geoMap.zoomIn(new Point(currentPosition.x - mapPosition.x, currentPosition.y - mapPosition.y));
				nextX <<= 1;
				nextY <<= 1;
			}
		}
		geoMap.setCenterPosition(new Point(nextX, nextY));
		lastTime = currentTime;
		geoMap.redraw();
	}
}
