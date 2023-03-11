package com.nine.service.repository;

import com.nine.service.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Article, Long> {
}
