package org.com.itpple.spot.server.util;

import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.PointDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Slf4j
public class GeometryUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point convertToPoint(PointDTO pointDTO) {
        Coordinate coordinate = new Coordinate(pointDTO.lat(), pointDTO.lon());

        return geometryFactory.createPoint(coordinate);
    }
    public static Polygon convertToPolygon(PointDTO[] pointDTOs) {
        if(pointDTOs.length < 3) {
            throw new RuntimeException("pointDTOs length must be greater than 3");
        };
        Coordinate[] coordinates = new Coordinate[pointDTOs.length];
        for (int i = 0; i < pointDTOs.length; i++) {
            coordinates[i] = new Coordinate(pointDTOs[i].lat(), pointDTOs[i].lon());
        }
        return geometryFactory.createPolygon(coordinates);
    }

    public static PointDTO convertToPointDTO(Point point) {
        return new PointDTO(point.getX(), point.getY());
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
