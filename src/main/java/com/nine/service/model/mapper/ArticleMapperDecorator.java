package com.nine.service.model.mapper;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.ArticleTag;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public abstract class ArticleMapperDecorator implements ArticleMapper{

	private final ArticleMapper delegate;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public ArticleMapperDecorator(ArticleMapper delegate) {
		this.delegate = delegate;
	}

	public Article articleDtoToArticle(ArticleDtoRequest articleDtoRequest){
		Article result = this.delegate.articleDtoToArticle(articleDtoRequest);
		if(CollectionUtils.isEmpty(articleDtoRequest.getTags())){
			return result;
		}
		articleDtoRequest.getTags().stream()
				.map(tag-> {
					ArticleTag articleTag = new ArticleTag();
					articleTag.setName(tag);
					articleTag.setArticle(result);
					return articleTag;
				}).collect(Collectors.toList());
		return result;
	}

	public ArticleDtoResponse articleToArticleDto(Article article){
		ArticleDtoResponse articleDtoRequest = this.delegate.articleToArticleDto(article);
		articleDtoRequest.setTags(
				article.getTags().stream().map(ArticleTag::getName).collect(Collectors.toList())
		);

		articleDtoRequest.setDate(dateFormat.format(article.getDate()));
		return articleDtoRequest;
	}

}
