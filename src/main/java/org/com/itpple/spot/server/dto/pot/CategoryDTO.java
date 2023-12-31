package org.com.itpple.spot.server.dto.pot;

import org.com.itpple.spot.server.entity.Category;

public record CategoryDTO(Long id, String name, String description) {

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }
}
