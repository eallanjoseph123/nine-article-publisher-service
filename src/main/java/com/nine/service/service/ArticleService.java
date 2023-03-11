package com.nine.service.service;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.entity.Article;
import com.nine.service.model.mapper.ArticleMapper;
import com.nine.service.repository.ArticleDao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

	private final ArticleDao articleDao;

	@Transactional
	public ArticleDtoResponse createArticle(ArticleDtoRequest dtoRequest) {
		Article newArticle = ArticleMapper.INSTANCE.articleDtoToArticle(dtoRequest);
		Article createdArticle = this.articleDao.save(newArticle);
		ArticleDtoResponse response = ArticleMapper.INSTANCE.articleToArticleDto(createdArticle);
		return response;
	}

	@Transactional(readOnly = true)
	public ArticleDtoResponse getArticleById(Long id) {
		Optional<Article> article = articleDao.findById(id);
		return ArticleMapper.INSTANCE.articleToArticleDto(article.get());
	}
}
