package com.nine.service.model.mapper;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.dto.ArticleDtoResponse.ArticleDtoResponseBuilder;
import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.Article.ArticleBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T09:13:07+1100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Ubuntu)"
)
public class ArticleMapperImpl_ implements ArticleMapper {

    @Override
    public Article articleDtoRequestToArticle(ArticleDtoRequest articleDtoRequest) {
        if ( articleDtoRequest == null ) {
            return null;
        }

        ArticleBuilder article = Article.builder();

        article.title( articleDtoRequest.getTitle() );
        article.body( articleDtoRequest.getBody() );

        return article.build();
    }

    @Override
    public ArticleDtoResponse articleToArticleDtoResponse(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDtoResponseBuilder articleDtoResponse = ArticleDtoResponse.builder();

        articleDtoResponse.id( article.getId() );
        articleDtoResponse.title( article.getTitle() );
        articleDtoResponse.body( article.getBody() );

        return articleDtoResponse.build();
    }
}
