package org.com.itpple.spot.server.repository;

import org.com.itpple.spot.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
