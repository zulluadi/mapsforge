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

package org.mapsforge.core.model;

import java.io.Serializable;

/**
 * Encapsulates the parameters of a rotation: angle and pivot point.
 */

public class Rotation implements Serializable {
	private static final long serialVersionUID = 1L;

	public final float degrees;
	public final float px;
	public final float py;
	public final double radians;

	public Rotation(float degrees, float px, float py) {
		this.degrees = degrees;
		this.radians = Math.toRadians(degrees);
		this.px = px;
		this.py = py;
	}

	/**
	 * Returns a new @Rotation object with angle reversed.
	 * @return
	 */
	public Rotation reverseRotation() {
		return new Rotation(-this.degrees, px, py);
	}

	/**
	 * Rotates a point with this rotation by first normalizing to the pivot point and adding the
	 * offset of the pivot again after the rotation around the pivot has been performed.
	 * @param p the input point.
	 * @return the rotated point.
	 */
	public Point rotate(Point p) {
		return rotate(p.x, p.y);
	}

	/**
	 * Rotates a point with this rotation by first normalizing to the pivot point and adding the
	 * offset of the pivot again after the rotation around the pivot has been performed.
	 * @param x pivot point x.
	 * @param y pivot point y.
	 * @return the rotated point.
	 */
	public Point rotate(double x, double y) {
		double cosTheta = Math.cos(this.radians);
		double sinTheta = Math.sin(this.radians);
		double rotatedX = (x - this.px) * cosTheta - (y - this.py) * sinTheta + this.px;
		double rotatedY = (x - this.px) * sinTheta + (y - this.py) * cosTheta + this.py;
		return new Point(rotatedX, rotatedY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Rotation)) {
			return false;
		}
		Rotation other = (Rotation) obj;
		if (Float.floatToIntBits(this.px) != Float.floatToIntBits(other.px)) {
			return false;
		}
		if (Float.floatToIntBits(this.py) != Float.floatToIntBits(other.py)) {
			return false;
		}
		if (Double.doubleToLongBits(this.radians) != Double.doubleToLongBits(other.radians)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Float.floatToIntBits(this.px);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Float.floatToIntBits(this.py);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.radians);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("radians=");
		stringBuilder.append(this.radians);
		stringBuilder.append("px=");
		stringBuilder.append(this.px);
		stringBuilder.append(", py=");
		stringBuilder.append(this.py);
		return stringBuilder.toString();
	}
}
