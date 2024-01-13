package org.com.itpple.spot.server.domain.pot.dto;

import org.com.itpple.spot.server.domain.category.entity.Category;

public record CategoryDTO(Long id, String name, String description) {

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }
}
