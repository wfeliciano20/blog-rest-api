package com.williamfeliciano.blogrestapi.repository;

import com.williamfeliciano.blogrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
