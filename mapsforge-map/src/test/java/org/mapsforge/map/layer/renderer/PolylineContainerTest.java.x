/*
 * Copyright 2015 Ludwig M Brinckmann
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
package org.mapsforge.map.layer.renderer;

import org.junit.Assert;
import org.junit.Test;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.model.Tile;
import org.mapsforge.core.util.MercatorProjection;
import org.mapsforge.map.reader.Way;


public class PolylineContainerTest {

	private static final int[] TILES_SIZES= {100, 256, 512, 1024};

	@Test
	public void constructorTest() {
		LatLong[][] coordinates = new LatLong[1][2];
		coordinates[0][0] = new LatLong(90, -180);
		coordinates[0][1] = new LatLong(-90, 180);

		Way way = new Way((byte) 0, null, coordinates, null);

		for (int tileSize : TILES_SIZES) {
			for (byte zoomlevel = 0; zoomlevel < 25; zoomlevel += 1) {
				Tile tile = new Tile(0, 0, zoomlevel, tileSize);
				PolylineContainer p = new PolylineContainer(way, tile);
				Point[][] abs = p.getCoordinatesAbsolute();
				Assert.assertEquals(new Point(0, 0), abs[0][0]);
				long mapSize = MercatorProjection.getMapSize(zoomlevel, tileSize);
				Assert.assertEquals(new Point(mapSize, mapSize), abs[0][1]);
			}
		}
	}

}
