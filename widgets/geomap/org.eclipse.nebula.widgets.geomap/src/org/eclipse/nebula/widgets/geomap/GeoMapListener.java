package org.eclipse.nebula.widgets.geomap;

/**
 * Interface for listening to changes to the state of the GeoMap UI
 */
public interface GeoMapListener {

	/**
	 * Called whenever the center of the GeoMap changes, e.g. when panning.
	 */
	public void centerChanged();
	
	/**
	 * Called whenever the zoom level changes.
	 */
	public void zoomChanged();
}
