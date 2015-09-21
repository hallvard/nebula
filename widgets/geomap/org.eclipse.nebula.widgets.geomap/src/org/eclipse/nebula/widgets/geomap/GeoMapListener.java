package org.eclipse.nebula.widgets.geomap;

/**
 * Interface for listening to changes to the state of the GeoMap UI
 */
public interface GeoMapListener {

	/**
	 * Called whenever the center of the GeoMap changes, e.g. when panning.
	 * This will happen in general for calls to
	 * {@link GeoMap#setMapPosition}, {@link GeoMap#translateMapPosition} and {@link GeoMap#setCenterPosition}
	 * A call to higher-level methods like {@link GeoMap#zoomTo} may result in many firings.
	 * @param geoMap the geoMap that has changed
	 */
	public void centerChanged(GeoMap geoMap);
	
	/**
	 * Called whenever the zoom level changes.
	 * This will happen in general for calls to
	 * {@link GeoMap#setZoom}, {@link GeoMap#zoomIn} and {@link GeoMap#zoomOut}
	 * A call to higher-level methods like {@link GeoMap#zoomTo} may result in many firings.
	 * @param geoMap the geoMap that has changed
	 */
	public void zoomChanged(GeoMap geoMap);
}
