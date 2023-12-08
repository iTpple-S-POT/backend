package org.com.itpple.spot.server.dto.pot;

import lombok.Getter;
import org.com.itpple.spot.server.model.entity.Category;

@Getter
public record CategoryDTO (Long id, String name, String description){
    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }
}
