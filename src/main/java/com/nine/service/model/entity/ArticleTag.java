package com.nine.service.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "article_tag")
public class ArticleTag extends AbstractPersistent{
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "articleId", referencedColumnName = "id", nullable = false)
	@Setter(AccessLevel.NONE)
	private Article article;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArticle(Article article) {
		if (article == null || (this.article != null && (article == this.article || this.article.getId() != null && article.getId() != null && this.article.getId().equals(article.getId())))) {
			return;
		}
		this.article = article;
		this.article.addTag(this);
	}
}
