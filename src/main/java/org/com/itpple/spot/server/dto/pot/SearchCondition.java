package org.com.itpple.spot.server.dto.pot;

import static org.com.itpple.spot.server.util.GeometryUtil.createCircle;
import static org.com.itpple.spot.server.util.GeometryUtil.createPolygon;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.com.itpple.spot.server.dto.Location;
import org.locationtech.jts.geom.Polygon;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class SearchCondition {
    @NotNull
    private SearchType searchType;

    @Valid
    @NotNull
    private SearchRange searchRange;

    @Min(1)
    private Long categoryId;


    public Polygon polygon() {
        return searchRange.polygon();
    }

    public SearchType searchType() {
        return searchRange.searchType();
    }

    public enum SearchType {
        CIRCLE,
        RECTANGLE,
    }

    public interface SearchRange {

        SearchType searchType();

        Polygon polygon();
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class CircleSearchRange implements SearchRange {

        @Min(1)
        private double radius;
        @NotNull
        private Location center;

        public SearchType searchType() {
            return SearchType.CIRCLE;
        }

        public Polygon polygon() {
            return createCircle(center, radius);
        }
    }


    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class RectangleSearchRange implements SearchRange {

        @NotEmpty
        @Size(min = 4,max = 4)
        private Location[] locations;

        public SearchType searchType() {
            return SearchType.RECTANGLE;
        }

        public Polygon polygon() {
            return createPolygon(locations);
        }
    }
}