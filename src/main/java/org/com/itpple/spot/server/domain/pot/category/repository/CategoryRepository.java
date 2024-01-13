package org.com.itpple.spot.server.domain.pot.category.repository;

import org.com.itpple.spot.server.domain.pot.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
