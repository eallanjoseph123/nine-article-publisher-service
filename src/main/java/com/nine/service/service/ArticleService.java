package com.nine.service.service;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.dto.RelatedArticlesDtoResponse;
import com.nine.service.model.entity.AbstractPersistent;
import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.ArticleTag;
import com.nine.service.model.mapper.ArticleMapper;
import com.nine.service.repository.ArticleDao;
import com.nine.service.repository.ArticleTagDao;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleService {

	private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	private final ArticleDao articleDao;

	private final ArticleTagDao articleTagDao;

	@Transactional
	public ArticleDtoResponse createArticle(ArticleDtoRequest dtoRequest) {
		if(dtoRequest == null){
			throw new IllegalArgumentException("dtoRequest is null.");
		}
		initialiseTags(dtoRequest);
		Article newArticle = ArticleMapper.INSTANCE.articleDtoRequestToArticle(dtoRequest);
		Article createdArticle = this.articleDao.save(newArticle);
		ArticleDtoResponse response = ArticleMapper.INSTANCE.articleToArticleDtoResponse(createdArticle);
		return response;
	}

	private void initialiseTags(ArticleDtoRequest dtoRequest) {
		if (CollectionUtils.isEmpty(dtoRequest.getTags())) {
			return;
		}
		List<ArticleTag> articleTagList = dtoRequest.getTags().stream().map(tag -> {
			Optional<ArticleTag> articleTag = articleTagDao.findByName(tag);
			return articleTag.isPresent() ? articleTag.get() : new ArticleTag(tag);
		}).collect(Collectors.toList());

		dtoRequest.getArticleTagList().addAll(articleTagList);

	}

	@Transactional(readOnly = true)
	public ArticleDtoResponse getArticleById(Long id) {
		if(id == null){
			throw new IllegalArgumentException("invalid article id.");
		}
		Optional<Article> article = articleDao.findById(id);
		if(!article.isPresent()){
			throw new IllegalArgumentException("articleId is not exists.");
		}
		return ArticleMapper.INSTANCE.articleToArticleDtoResponse(article.get());
	}

	@Transactional(readOnly = true)
	public RelatedArticlesDtoResponse getArticlesByTagNameAndDate(String tagName, String dateRequest){

		if(StringUtils.isBlank(tagName)){
			throw new IllegalArgumentException("tagName is not valid.");
		}

		if(StringUtils.isBlank(dateRequest)){
			throw new IllegalArgumentException("date is not valid.");
		}

		RelatedArticlesDtoResponse response = new RelatedArticlesDtoResponse();

		Optional<ArticleTag> articleTag = this.articleTagDao.findByName(tagName);

		if(!articleTag.isPresent()){
			throw new IllegalArgumentException("tagName is not exists.");
		}

		ArticleTag tag = articleTag.get();

		Set<String> relatedTags = tag.getArticles().stream()
				.flatMap(article -> article.getTags().stream())
				.map(existingTag-> existingTag.getName()).collect(Collectors.toSet());


		Date date = convertDateStringToDateObject(dateRequest);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);

		Date tomorrowDate = calendar.getTime();

		List<Article> articles = tag.getArticles().stream()
				.filter(article -> article.getDate().compareTo(date) >= 0 && article.getDate().compareTo(tomorrowDate) < 0)
				.collect(Collectors.toList());

		articles.sort((tag1, tag2) -> tag2.getDate().compareTo(tag1.getDate()));

		List<Long> last10ArticlesEntered = articles.size() < 10 ? articles.stream().map(AbstractPersistent::getId).collect(Collectors.toList())
														 : articles.subList(0, 10).stream().map(AbstractPersistent::getId).collect(Collectors.toList());

		response.setTag(tagName);
		response.setRelatedTags(relatedTags);
		response.setArticles(last10ArticlesEntered);
		return response;
	}

	private static Date convertDateStringToDateObject(String dateRequest) {
		try {
			return SIMPLE_DATE_FORMAT.parse(dateRequest);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
