/*******************************************************************************
 * Copyright (c) 2010 Ubiquiti Networks, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.pgroup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class SimpleToolItemRenderer extends AbstractRenderer {

	public void paint(GC gc, Object value) {
		Object[] data = (Object[]) value;
		PGroupToolItem item = (PGroupToolItem) data[0];

		Rectangle rect = getBounds();
		int alpha = gc.getAlpha();
		Color bg = gc.getBackground();

		if( isHover() || item.getSelection() ) {
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
			gc.setAlpha(100);
			gc.fillRoundRectangle(rect.x, rect.y,rect.width,rect.height,3,3);
			gc.setBackground(bg);
			gc.setAlpha(alpha);
		}

		if (item.getText().length() > 0 && item.getImage() != null
				&& ! ((Boolean) data[1]).booleanValue()) {
			gc.drawImage(item.getImage(), rect.x + 2, 2 + (int)(rect.height / 2.0 - item.getImage().getImageData().height / 2.0));
			Point p = gc.textExtent(item.getText());
			gc.drawString(item.getText(), rect.x + 2 + item.getImage().getImageData().width + 2, rect.y + (int)(rect.height / 2.0 - p.y / 2.0), true);
		} else if (item.getImage() != null) {
			gc.drawImage(item.getImage(), rect.x + 2, 2 + (int)(rect.height / 2.0 - item.getImage().getImageData().height / 2.0));
		} else if (item.getText().length() > 0) {
			Point p = gc.textExtent(item.getText());
			gc.drawString(item.getText(), rect.x + 2, rect.y + (int)(rect.height / 2.0 - p.y / 2.0), true);
		}

		if( (item.getStyle() & SWT.DROP_DOWN) != 0 ) {
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.fillPolygon(new int[] { rect.x + rect.width - 2, rect.height / 2, rect.x + rect.width - 8, rect.height / 2, rect.x + rect.width - 5, rect.height / 2 + 4 }  );
		}

		gc.setAlpha(alpha);
		gc.setBackground(bg);
	}
}