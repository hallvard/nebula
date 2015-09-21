/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.xviewer.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerText;
import org.eclipse.nebula.widgets.xviewer.XViewerTreeReport;
import org.eclipse.nebula.widgets.xviewer.util.internal.images.XViewerImageCache;

/**
 * @author Donald G. Dunne
 */
public class ViewTableReportAction extends Action {

   private final XViewer xViewer;

   public ViewTableReportAction(XViewer xViewer) {
      super(XViewerText.get("action.viewTableReport")); //$NON-NLS-1$
      this.xViewer = xViewer;
   }

   @Override
   public ImageDescriptor getImageDescriptor() {
      return XViewerImageCache.getImageDescriptor("report.gif"); //$NON-NLS-1$
   }

   @Override
   public void run() {
      if (xViewer.getXViewerFactory().getXViewerTreeReport(xViewer) != null) {
         xViewer.getXViewerFactory().getXViewerTreeReport(xViewer).open();
      } else {
         new XViewerTreeReport(xViewer).open();
      }
   }

}
