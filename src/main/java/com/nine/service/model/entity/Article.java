package com.nine.service.model.entity;

import io.swagger.v3.oas.models.links.Link;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "article")
public class Article extends AbstractPersistent{
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String body;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "article")
	@Setter(AccessLevel.NONE)
	private List<ArticleTag> tags;

	public void addTag(ArticleTag articleTag) {
		if (articleTag == null) {
			return;
		}
		if (CollectionUtils.isEmpty(this.tags)) {
			this.tags = new LinkedList<>();
		}
		if (this.tags.stream().anyMatch(tag -> tag.getName().equalsIgnoreCase(articleTag.getName()) || (articleTag.getId() != null && tag.getId() != null && tag.getId().equals(articleTag.getId())))) {
			return;
		}
		this.tags.add(articleTag);
		articleTag.setArticle(this);
	}
}
