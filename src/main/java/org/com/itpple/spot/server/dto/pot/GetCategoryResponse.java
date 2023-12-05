package org.com.itpple.spot.server.dto.pot;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.com.itpple.spot.server.model.entity.Category;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCategoryResponse {

        private List<CategoryDTO> categoryList;


        public static GetCategoryResponse of(Collection<Category> categoryList) {
                return new GetCategoryResponse(categoryList.stream().map(CategoryDTO::fromEntity).toList());
        }



}
