package com.nine.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nine.service.model.entity.ArticleTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDtoRequest {

	private String title;

	private String body;

	private List<String> tags;

	@JsonIgnore
	private List<ArticleTag> articleTagList;

	public List<ArticleTag> getArticleTagList() {
		if(CollectionUtils.isEmpty(this.articleTagList)){
			this.articleTagList = new LinkedList<>();
		}
		return articleTagList;
	}
}
