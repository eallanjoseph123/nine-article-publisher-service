package com.nine.service.repository;

import com.nine.service.model.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ArticleTagDao extends JpaRepository<ArticleTag, Long> {
	Optional<ArticleTag> findByName(String name);
}
