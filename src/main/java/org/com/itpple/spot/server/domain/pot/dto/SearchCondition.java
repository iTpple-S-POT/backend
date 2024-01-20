package org.com.itpple.spot.server.domain.pot.dto;

import static org.com.itpple.spot.server.global.util.GeometryUtil.createCircle;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createPolygon;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.locationtech.jts.geom.Polygon;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class SearchCondition {
    @NotNull
    private SearchType searchType;

    @Valid
    @NotNull
    private SearchRange searchRange;

    @Min(1)
    private Long categoryId;

    @Min(1)
    private Long hashtagId;


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
    public static class CircleSearchRange implements SearchRange {

        @Min(1)
        @Max(3001)
        private double diameterInMeters;
        @NotNull
        private PointDTO center;

        public SearchType searchType() {
            return SearchType.CIRCLE;
        }

        public Polygon polygon() {
            return createCircle(center, diameterInMeters);
        }
    }


    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    public static class RectangleSearchRange implements SearchRange {

        @NotEmpty
        @Size(min = 4,max = 4)
        private PointDTO[] locations;

        public SearchType searchType() {
            return SearchType.RECTANGLE;
        }

        public Polygon polygon() {
            return createPolygon(locations);
        }
    }
}
