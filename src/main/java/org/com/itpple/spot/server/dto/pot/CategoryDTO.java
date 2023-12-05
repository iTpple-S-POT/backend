package org.com.itpple.spot.server.dto.pot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.com.itpple.spot.server.model.entity.Category;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryDTO {
    private Long id;
    private String category;
    private String description;

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }
}
