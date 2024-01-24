package org.com.itpple.spot.server.domain.pot.advice;

import javax.servlet.http.HttpServletRequest;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.api.PotController;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.CircleSearchRange;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.RectangleSearchRange;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchType;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PotController.class)
public class PotControllerAdvice {

    @ModelAttribute("searchCondition")
    public SearchCondition getSearchCondition(HttpServletRequest request) {
        var type = request.getParameter("type");
        if (type == null) {
            return SearchCondition.builder().build();
        }
        var searchType = SearchType.valueOf(type);
        var searchRange = getSearchRange(searchType, request);
        var categoryId = StringUtils.isBlank(request.getParameter("categoryId")) ? null
                : Long.parseLong(request.getParameter("categoryId"));
        var hashtagId = StringUtils.isBlank(request.getParameter("hashtagId")) ? null
                : Long.parseLong(request.getParameter("hashtagId"));

        return SearchCondition.builder().searchRange(searchRange).searchType(searchType)
                .categoryId(categoryId).hashtagId(hashtagId).build();
    }

    private SearchRange getSearchRange(SearchType searchType, HttpServletRequest request) {
        switch (searchType) {
            case CIRCLE:
                var radius = Double.parseDouble(request.getParameter("diameterInMeters"));
                var lat = Double.parseDouble(request.getParameter("lat"));
                var lng = Double.parseDouble(request.getParameter("lon"));
                return new CircleSearchRange(radius, new PointDTO(lat, lng));
            case RECTANGLE:
                var locations = request.getParameterValues("locations");
                var locationArray = new PointDTO[locations.length];
                for (int i = 0; i < locations.length; i++) {
                    var location = locations[i].split(",");
                    locationArray[i] = new PointDTO(Double.parseDouble(location[0]),
                            Double.parseDouble(location[1]));
                }
                return new RectangleSearchRange(locationArray);
            default:
                return null;
        }
    }
}
