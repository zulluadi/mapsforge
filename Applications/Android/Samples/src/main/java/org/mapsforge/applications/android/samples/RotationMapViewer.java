/*
 * Copyright © 2015 Ludwig M Brinckmann
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.applications.android.samples;

import android.view.View;
import android.widget.Button;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.layer.debug.TileCoordinatesLayer;
import org.mapsforge.map.layer.debug.TileGridLayer;
import org.mapsforge.map.layer.renderer.TileRendererLayer;

/**
 * A map viewer that draws the labels onto a single separate layer. The LabelLayer remains
 * experimental code and has some notable speed issues. Its use in production is currently not
 * recommended.
 */
public class RotationMapViewer extends RenderTheme4 {

	private float rotation;

	@Override
	protected void createControls() {
		// Rotate button
		Button rotateButton = (Button) findViewById(R.id.rotateButton);
		rotateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rotation += 15f;
				RotationMapViewer.this.mapView.rotate(rotation);
				RotationMapViewer.this.redrawLayers();
			}
		});

	}

	@Override
	protected void createLayers() {
		TileRendererLayer tileRendererLayer = AndroidUtil.createTileRendererLayer(this.tileCaches.get(0),
				this.mapView.getModel().mapViewPosition, getMapFile(), getRenderTheme(), false, false);
		mapView.getLayerManager().getLayers().add(tileRendererLayer);
		org.mapsforge.map.layer.labels.LabelLayer labelLayer = new org.mapsforge.map.layer.labels.LabelLayer(AndroidGraphicFactory.INSTANCE, tileRendererLayer.getLabelStore());
		mapView.getLayerManager().getLayers().add(labelLayer);
		// add a grid layer and a layer showing tile coordinates
		mapView.getLayerManager().getLayers()
				.add(new TileGridLayer(AndroidGraphicFactory.INSTANCE, this.mapView.getModel().displayModel));
		mapView.getLayerManager().getLayers()
				.add(new TileCoordinatesLayer(AndroidGraphicFactory.INSTANCE, this.mapView.getModel().displayModel));
		mapView.getFpsCounter().setVisible(true);

	}

	@Override
	protected float getScreenRatio() {
		// just to get the cache bigger right now.
		return 2f;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.rotation;
	}

}
