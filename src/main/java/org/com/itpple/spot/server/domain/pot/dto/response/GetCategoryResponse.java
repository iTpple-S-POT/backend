package org.com.itpple.spot.server.domain.pot.dto.response;

import java.util.Collection;
import java.util.List;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.dto.CategoryDTO;


public record GetCategoryResponse(List<CategoryDTO> categoryList) {

    public static GetCategoryResponse of(Collection<Category> categoryList) {
        return new GetCategoryResponse(categoryList.stream().map(CategoryDTO::fromEntity).toList());
    }

}
