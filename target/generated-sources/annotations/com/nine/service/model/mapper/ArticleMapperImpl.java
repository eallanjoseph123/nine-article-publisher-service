package com.nine.service.model.mapper;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T09:13:07+1100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Ubuntu)"
)
public class ArticleMapperImpl extends ArticleMapperDecorator implements ArticleMapper {

    private final ArticleMapper delegate;

    public ArticleMapperImpl() {
        this( new ArticleMapperImpl_() );
    }

    private ArticleMapperImpl(ArticleMapperImpl_ delegate) {
        super( delegate );
        this.delegate = delegate;
    }
}
