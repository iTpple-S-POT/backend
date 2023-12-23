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

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private static final GeometricShapeFactory shapeFactory = new GeometricShapeFactory(geometryFactory);

    public static Point convertToPoint(PointDTO pointDTO) {
        Coordinate coordinate = new Coordinate(pointDTO.lat(), pointDTO.lon());

        return geometryFactory.createPoint(coordinate);
    }

    public static Polygon createCircle(PointDTO pointDTO, double radius) {
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(pointDTO.lat(), pointDTO.lon()));
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }


    public static Polygon convertToPolygon(PointDTO[] pointDTOs) {
        if(pointDTOs.length < 3) {
            throw new RuntimeException("pointDTOs length must be greater than 3");
        }
        Coordinate[] coordinates = new Coordinate[pointDTOs.length];
        for (int i = 0; i < pointDTOs.length; i++) {
            coordinates[i] = new Coordinate(pointDTOs[i].lat(), pointDTOs[i].lon());
        }

        return geometryFactory.createPolygon(prepareToMakePolygon(coordinates));
    }

    /**
     * polygon을 만들기 위해서는 반드시 시계방향으로 정렬된 좌표가 필요하다.
     * 또한 polygon의 마지막 점은 첫번째 점과 같아야 한다.
     * */
    private static Coordinate[] prepareToMakePolygon(Coordinate[] origin) {
        if (origin == null || origin.length < 3) {
            throw new IllegalArgumentException("Input array must contain at least three points.");
        }
        var copy = Arrays.copyOf(origin, origin.length);

        double cx = 0.0;
        double cy = 0.0;

        for (Coordinate point : copy) {
            cx += point.getX();
            cy += point.getY();
        }

        cx /= copy.length;
        cy /= copy.length;

        double finalCy = cy;
        double finalCx = cx;
        Arrays.sort(copy, (p1, p2) -> {
            double angle1 = Math.atan2(p1.getY() - finalCy, p1.getX() - finalCx);
            double angle2 = Math.atan2(p2.getY() - finalCy, p2.getX() - finalCx);
            return Double.compare(angle1, angle2);
        });

        copy = Arrays.copyOf(copy, copy.length + 1);
        copy[copy.length - 1] = copy[0];

        return copy;
    }

    public static PointDTO convertToPointDTO(Point point) {
        return new PointDTO(point.getX(), point.getY());
    }

    //테스트 때 검증을 위해서만 사용하고, 그 외의 상황에서는 위의 method들을 사용할 것
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
