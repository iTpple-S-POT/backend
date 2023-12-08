package org.com.itpple.spot.server.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class GeometryUtil {
    public static Point convertToPoint(Double lat, Double lon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(lat, lon);

        return geometryFactory.createPoint(coordinate);
    }
}
