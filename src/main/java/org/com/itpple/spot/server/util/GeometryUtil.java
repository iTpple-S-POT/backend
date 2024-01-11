package org.com.itpple.spot.server.util;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.PointDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.util.GeometricShapeFactory;

@Slf4j
public class GeometryUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),
            4326);
    private static final GeometricShapeFactory shapeFactory = new GeometricShapeFactory(
            geometryFactory);

    public static Point createPoint(PointDTO location) {
        Coordinate coordinate = new Coordinate(location.lat(), location.lon());

        return geometryFactory.createPoint(coordinate);
    }

    /**
     * 주의해야 할 점
     * shapeFactory.setSize()는 meter 단위가 아니다. degree 단위이다.
     * 목적에 맞게 미터단위반경을 위해 아래의 공식을 사용한다.
     * Length in meters of 1° of latitude = always 111.32 km
     * Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
     * https://gis.stackexchange.com/questions/268639/jts-geometricshapefactory-generate-an-ellipse-properly
     * */
    public static Polygon createCircle(PointDTO location, double diameterInMeters) {
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(location.lat(), location.lon()));
        shapeFactory.setWidth(diameterInMeters / (40075000 * Math.cos(Math.toRadians(location.lat())) / 360));
        shapeFactory.setHeight(diameterInMeters/111320d);
        return shapeFactory.createEllipse();
    }


    public static Polygon createPolygon(PointDTO[] locations) {
        if (locations.length < 3) {
            throw new RuntimeException("pointDTOs length must be greater than 3");
        }
        Coordinate[] coordinates = new Coordinate[locations.length];
        for (int i = 0; i < locations.length; i++) {
            coordinates[i] = new Coordinate(locations[i].lat(), locations[i].lon());
        }

        return geometryFactory.createPolygon(prepareToMakePolygon(coordinates));
    }

    /**
     * polygon을 만들기 위해서는 반드시 시계방향으로 정렬된 좌표가 필요하다.
     * 또한 polygon의 마지막 점은 첫번째 점과 같아야 한다.
     * 입력에서는 중복되지 않은 점만 사용을 권장한다.
     */
    private static Coordinate[] prepareToMakePolygon(Coordinate[] origin) {
        if (origin == null || origin.length < 3) {
            throw new IllegalArgumentException("Input array must contain at least three points.");
        }

        var coordinatesForPolygon = Arrays.copyOf(origin, origin.length);

        double cx = 0.0;
        double cy = 0.0;

        for (var point : coordinatesForPolygon) {
            cx += point.getX();
            cy += point.getY();
        }

        cx /= coordinatesForPolygon.length;
        cy /= coordinatesForPolygon.length;

        sortByArcTan2(coordinatesForPolygon, cx, cy);

        coordinatesForPolygon = Arrays.copyOf(coordinatesForPolygon, coordinatesForPolygon.length + 1);
        coordinatesForPolygon[coordinatesForPolygon.length - 1] = coordinatesForPolygon[0];

        return coordinatesForPolygon;
    }


    /**
     * 원본을 수정하므로 유의해서 사용해야 한다.
     */
    private static void sortByArcTan2(Coordinate[] target, double cx, double cy) {
        Arrays.sort(target, (p1, p2) -> {
            double angle1 = Math.atan2(p1.getY() - cy, p1.getX() - cx);
            double angle2 = Math.atan2(p2.getY() - cy, p2.getX() - cx);
            return Double.compare(angle1, angle2);
        });
    }

    public static PointDTO toPointDTO(Point point) {
        return new PointDTO(point.getX(), point.getY());
    }

    //테스트 때 검증을 위해서만 사용하고, 그 외의 상황에서는 위의 method들을 사용할 것
    //wellKnownText is https://en.wikipedia.org/wiki/Well-known_text_representation_of_geometry
    public static Point wktToGeometry(String wellKnownText) {
        try {
            var result = new WKTReader().read(wellKnownText);
            return (Point) result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
