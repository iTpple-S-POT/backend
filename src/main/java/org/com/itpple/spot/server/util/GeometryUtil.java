package org.com.itpple.spot.server.util;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Slf4j
public class GeometryUtil {

    public static Point convertToPoint(Double lat, Double lon) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(lat, lon);

        return geometryFactory.createPoint(coordinate);
    }

    //wellKnownText is https://en.wikipedia.org/wiki/Well-known_text_representation_of_geometry
    public static Point wktToGeometry(String wellKnownText) {
        try {
            var result =new WKTReader().read(wellKnownText);
            return (Point) result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
