package com.nine.service.model.mapper;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.ArticleTag;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

public abstract class ArticleMapperDecorator implements ArticleMapper{
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final ArticleMapper delegate;

	public ArticleMapperDecorator(ArticleMapper delegate) {
		this.delegate = delegate;
	}

	public Article articleDtoRequestToArticle(ArticleDtoRequest articleDtoRequest) {
		Article result = this.delegate.articleDtoRequestToArticle(articleDtoRequest);
		if (CollectionUtils.isEmpty(articleDtoRequest.getTags())) {
			return result;
		}
		articleDtoRequest.getArticleTagList().stream().forEach(tag -> tag.addArticle(result));
		result.setDate(new Date());
		return result;
	}

	public ArticleDtoResponse articleToArticleDtoResponse(Article article){
		ArticleDtoResponse articleDtoRequest = this.delegate.articleToArticleDtoResponse(article);
		articleDtoRequest.setTags(
				article.getTags().stream().map(ArticleTag::getName).collect(Collectors.toList())
		);
		articleDtoRequest.setDate(DATE_FORMAT.format(article.getDate()));
		return articleDtoRequest;
	}

}
