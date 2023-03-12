package com.nine.service.repository;

import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Long> {

	@Query("select at from ArticleTag at join at.articles a where at.name = :tagName and a.date >= :date and a.date < :tomorrowDate")
	Optional<ArticleTag> findByTagNameAndDate(String tagName, Date date, Date tomorrowDate);

}
