package org.com.itpple.spot.server.controller;

import javax.servlet.http.HttpServletRequest;
import org.com.itpple.spot.server.dto.PointDTO;
import org.com.itpple.spot.server.dto.pot.SearchCondition;
import org.com.itpple.spot.server.dto.pot.SearchCondition.CircleSearchRange;
import org.com.itpple.spot.server.dto.pot.SearchCondition.RectangleSearchRange;
import org.com.itpple.spot.server.dto.pot.SearchCondition.SearchRange;
import org.com.itpple.spot.server.dto.pot.SearchCondition.SearchType;
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
        var categoryId = Long.parseLong(request.getParameter("categoryId"));

        return SearchCondition.builder().searchRange(searchRange).searchType(searchType)
                .categoryId(categoryId).build();
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
