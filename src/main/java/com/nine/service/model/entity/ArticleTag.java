package com.nine.service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "article_tag")
public class ArticleTag extends AbstractPersistent{
	private String name;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="article_tag_article",
			joinColumns = { @JoinColumn(name = "article_id") },
			inverseJoinColumns = { @JoinColumn(name = "article_tag_id") })
	private List<Article> articles;

	public ArticleTag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addArticle(Article article) {
		if (article == null) {
			throw new IllegalArgumentException("Attempting to add null article");
		}

		if (CollectionUtils.isEmpty(articles)) {
			this.articles = new LinkedList<>();
		}

		if(isCollectionContainsEntity(this.articles, article)){
			return;
		}

		this.articles.add(article);
		article.addTag(this);
	}

}
