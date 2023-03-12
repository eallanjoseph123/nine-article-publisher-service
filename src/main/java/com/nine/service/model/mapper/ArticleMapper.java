package com.nine.service.model.mapper;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.entity.Article;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(ArticleMapperDecorator.class)
public interface ArticleMapper {

	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	@Mappings({
			@Mapping(target = "tags", ignore = true),
			@Mapping(target = "date", ignore = true)
	})
	Article articleDtoRequestToArticle(ArticleDtoRequest articleDtoRequest);

	@Mappings({
			@Mapping(target = "tags", ignore = true),
			@Mapping(target = "date", ignore = true)
	}) ArticleDtoResponse articleToArticleDtoResponse(Article article);
}
