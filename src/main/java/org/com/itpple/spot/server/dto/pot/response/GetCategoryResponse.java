package org.com.itpple.spot.server.dto.pot.response;

import java.util.Collection;
import java.util.List;
import org.com.itpple.spot.server.dto.pot.CategoryDTO;
import org.com.itpple.spot.server.entity.Category;


public record GetCategoryResponse(List<CategoryDTO> categoryList) {

    public static GetCategoryResponse of(Collection<Category> categoryList) {
        return new GetCategoryResponse(categoryList.stream().map(CategoryDTO::fromEntity).toList());
    }

}
