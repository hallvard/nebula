/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    hal - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.geomap.jface.example;

import org.eclipse.swt.SWT;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.examples.AbstractExampleTab;
import org.eclipse.nebula.widgets.geomap.OsmTileServer;
import org.eclipse.nebula.widgets.geomap.PointD;
import org.eclipse.nebula.widgets.geomap.TileServer;
import org.eclipse.nebula.widgets.geomap.jface.GeoMapViewer;
import org.eclipse.nebula.widgets.geomap.jface.LabelImageProvider;
import org.eclipse.nebula.widgets.geomap.jface.LocationProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class GeoMapViewerExampleTab extends AbstractExampleTab {

	private GeoMapViewer geoMapViewer;

	public Control createControl(Composite parent) {
		geoMapViewer = new GeoMapViewer(parent, SWT.NONE);
		configureMapViewer();
		return geoMapViewer.getControl();
	}

	public String[] createLinks() {
		return new String[] {
				"<a href=\"http://www.eclipse.org/nebula/widgets/geomap/geomap.php\">GeoMap Home Page</a>",
				"<a href=\"http://www.eclipse.org/nebula/widgets/geomap/snippets.php\">Snippets</a>",
				"<a href=\"https://bugs.eclipse.org/bugs/buglist.cgi?query_format=advanced&short_desc_type=allwordssubstr&short_desc=&classification=Technology&product=Nebula&component=GeoMap&long_desc_type=allwordssubstr&long_desc=&bug_file_loc_type=allwordssubstr&bug_file_loc=&status_whiteboard_type=allwordssubstr&status_whiteboard=&keywords_type=allwords&keywords=&emailtype1=substring&email1=&emailtype2=substring&email2=&bugidtype=include&bug_id=&votes=&chfieldfrom=&chfieldto=Now&chfieldvalue=&cmdtype=doit&order=Reuse+same+sort+as+last+time&field0-0-0=noop&type0-0-0=noop&value0-0-0=\">Bugs</a>" };
	}

	@Override
	public void createParameters(Composite parent) {
        GridLayoutFactory.swtDefaults().margins(0,0).numColumns(1).applyTo(parent);

		Group group = new Group(parent, SWT.NONE);
		group.setText("Interaction");
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		group.setLayout(new GridLayout(2, false));

		addLabel("Tile server: ", group);
		final Combo tileServerControl = new Combo(group, SWT.NONE);
		tileServerControl.setItems(new String[]{"http://tile.openstreetmap.org/{0}/{1}/{2}.png", "http://mt1.google.com/vt/lyrs=m@129&hl=en&s=Galileo&z={0}&x={1}&y={2}"});
		tileServerControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		tileServerControl.select(0);
		tileServerControl.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String selection = tileServerControl.getItem(tileServerControl.getSelectionIndex());
				geoMapViewer.getGeoMap().setTileServer(new TileServer(selection, 15));
			}
		});

		addLabel("Move selection mode: ", group);
		final Combo moveSelectionModeControl = new Combo(group, SWT.READ_ONLY);
		moveSelectionModeControl.setItems(new String[]{"Cannot move selection", "Allow, check readonly on mouse down", "Allow, just try to set new location"});
		moveSelectionModeControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		moveSelectionModeControl.select(1);
		moveSelectionModeControl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				geoMapViewer.setMoveSelectionMode(moveSelectionModeControl.getSelectionIndex());
			}
		});
		
		addLabel("Clip rule: ", group);
		final Combo clipRuleControl = new Combo(group, SWT.READ_ONLY);
		clipRuleControl.setItems(new String[]{"Don't clip", "Clip on element position", "Clip in image bounds"});
		clipRuleControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		clipRuleControl.select(1);
		clipRuleControl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				geoMapViewer.setClipRule(clipRuleControl.getSelectionIndex());
			}
		});
		
		addLabel("Contents: ", group);
		ListViewer contentList = new ListViewer(group, SWT.NONE);
		contentList.getControl().setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		contentList.setContentProvider(new ArrayContentProvider());
		contentList.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ContributorLocation) {
					return element.toString();
				}
				return super.getText(element);
			}
		});
		contentList.setInput(contributorLocations);
		contentList.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				geoMapViewer.setSelection(event.getSelection(), true);
			}
		});
	}
	
	private void addLabel(String text, Composite composite) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
	}
	
	@Override
	public boolean getInitialHorizontalFill() {
		return true;
	}

	@Override
	public boolean getInitialVerticalFill() {
		return true;
	}

	private static class ContributorLocation {
		public final String name;
		public PointD location;
		public final String locationText;
		public final boolean committer;
		public ContributorLocation(String name, PointD location, String locationText, boolean committer) {
			super();
			this.name = name;
			this.location = location;
			this.locationText = locationText;
			this.committer = committer;
		}
		public String toString() {
			return name + ", " + locationText + " @ " + location.x + ", " + location.y;
		}
	}
	
	private ContributorLocation[] contributorLocations = {
			new ContributorLocation("Hallvard Traetteberg", new PointD(10.4234,63.4242), 	"Trondheim, Norway", 	true),
			new ContributorLocation("Stepan Rutz",		 	new PointD(6.8222,50.9178), 	"Frechen, Germany", 	false),
			new ContributorLocation("Wim Jongman", 			new PointD(4.6410,52.3894),		"Haarlem, Netherlands", true),
			new ContributorLocation("Dirk Fauth", 			new PointD(9.1858,48.7775), 	"Stuttgart, Germany", 	true),
			new ContributorLocation("Tom Schindl", 			new PointD(11.4000,47.2671), 	"Innsbruck, Austria", 	true),
			new ContributorLocation("Matthew Hall",			new PointD(-111.97,40.54), 		"Riverton, Utah, USA", 	true),
			new ContributorLocation("Justin Dolezy",		new PointD(-0.34,51.48), 		"Richmond, Surrey, UK", true),
			new ContributorLocation("Edwin Park",			new PointD(-74.07,40.76), 		"Hoboken, New Jersey, USA", true),
			new ContributorLocation("Mickael Istria",		new PointD(5.7349,45.1872),	 	"Grenoble, France", 	true),
	};
	
	private int indexOfLocation(Object element) {
		for (int i = 0; i < contributorLocations.length; i++) {
			if (element == contributorLocations[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private void configureMapViewer() {
		geoMapViewer.getGeoMap().setTileServer(OsmTileServer.TILESERVERS[0]);
//		geoMapViewer.getGeoMap().setTileServer(GoogleTileServer.TILESERVERS[0]);
		
		geoMapViewer.setLabelProvider(new LabelImageProvider() {
			
			private RGB contributorColor = new RGB(255, 250, 200);
			private RGB committerColor = new RGB(200, 255, 200);
			
			@Override
			public Image getImage(Object element) {
				setFillColor(((ContributorLocation) element).committer ? committerColor : contributorColor);
				return super.getImage(element);
			}
			@Override
			public String getText(Object element) {
				return ((ContributorLocation) element).name;
			}
			@Override
			public Object getToolTip(Object element) {
				if (element instanceof ContributorLocation) {
					return element.toString();
				}
				return null;
			}
		});
		geoMapViewer.setLocationProvider(new LocationProvider() {
			public PointD getLonLat(Object element) {
				int pos = indexOfLocation(element);
				return pos >= 0 ? contributorLocations[pos].location : null;
			}
			public boolean setLonLat(Object element, double lon, double lat) {
				int pos = indexOfLocation(element);
				if (pos < 0) {
					return false;
				}
				contributorLocations[pos].location = new PointD(lon, lat);
				return true;			}
		});
		geoMapViewer.setContentProvider(new ArrayContentProvider());
		geoMapViewer.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				geoMapViewer.setInput(contributorLocations);
			}
		});
	}
}
