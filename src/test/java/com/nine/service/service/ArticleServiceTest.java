package com.nine.service.service;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.dto.RelatedArticlesDtoResponse;
import com.nine.service.model.entity.AbstractPersistent;
import com.nine.service.model.entity.Article;
import com.nine.service.model.entity.ArticleTag;
import com.nine.service.repository.ArticleDao;
import com.nine.service.repository.ArticleTagDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Tag("Unit")
@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

	@InjectMocks
	private ArticleService articleService;

	@Mock
	private ArticleDao articleDao;

	@Mock
	private ArticleTagDao articleTagDao;


	@Test
	public void createArticle_success(){
		List<String> tags = Arrays.asList("test1","test2");
		ArticleDtoRequest dtoRequest = ArticleDtoRequest.builder()
				.body("test body")
				.title("title")
				.tags(tags)
				.build();

		Article newArticle = new Article();
		newArticle.setDate(new Date());
		newArticle.setId(12L);
		newArticle.addTag(new ArticleTag("test1"));
		newArticle.addTag(new ArticleTag("test2"));

		when(this.articleDao.save(any())).thenReturn(newArticle);
		when(this.articleTagDao.findByName(anyString())).thenReturn(Optional.empty());

		ArticleDtoResponse response = articleService.createArticle(dtoRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(newArticle.getId(), response.getId());
		Assert.assertEquals(tags, response.getTags());
		Mockito.verify(articleDao, Mockito.times(1)).save(any(Article.class));
	}

	@Test
	public void createArticle_whenTagIsExists_shouldArticleReUseIt(){
		List<String> tags = Arrays.asList("test1","test2");
		ArticleDtoRequest dtoRequest = ArticleDtoRequest.builder()
				.body("test body")
				.title("title")
				.tags(tags)
				.build();

		Article newArticle = new Article();
		newArticle.setDate(new Date());
		newArticle.setId(12L);
		newArticle.addTag(new ArticleTag("test1"));
		newArticle.addTag(new ArticleTag("test2"));

		ArticleTag existingArticleTag = new ArticleTag();
		existingArticleTag.setName("test2");
		existingArticleTag.setId(30L);

		when(this.articleDao.save(any())).thenReturn(newArticle);
		when(this.articleTagDao.findByName(eq("test1"))).thenReturn(Optional.empty());
		when(this.articleTagDao.findByName(eq("test2"))).thenReturn(Optional.of(existingArticleTag));

		ArticleDtoResponse response = articleService.createArticle(dtoRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(newArticle.getId(), response.getId());
		Assert.assertEquals(tags, response.getTags());
		Mockito.verify(articleDao, Mockito.times(1)).save(any(Article.class));
	}

	@Test
	public void getArticleById_whenValidIdProvided_shouldReturnArticle(){
		List<String> tags = Arrays.asList("test1","test2");
		Long id = 12L;


		ArticleDtoRequest dtoRequest = ArticleDtoRequest.builder()
				.body("test body")
				.title("title")
				.tags(tags)
				.build();


		Article newArticle = new Article();
		newArticle.setDate(new Date());
		newArticle.setId(12L);
		newArticle.addTag(new ArticleTag("test1"));
		newArticle.addTag(new ArticleTag("test2"));


		when(this.articleDao.findById(id)).thenReturn(Optional.of(newArticle));

		ArticleDtoResponse response = articleService.getArticleById(id);

		Assert.assertNotNull(response);
		Assert.assertEquals(id, response.getId());
		Mockito.verify(articleDao, Mockito.times(1)).findById(id);
	}

	@Test
	public void getArticlesByTagNameAndDate_success(){

		ArticleTag testTag1 = new ArticleTag("test1");
		ArticleTag testTag2 = new ArticleTag("test1");

		Article article1 = new Article();
		article1.setDate(new Date());
		article1.setId(12L);

		article1.addTag(testTag1);
		article1.addTag(testTag2);

		when(this.articleTagDao.findByName(anyString())).thenReturn(Optional.of(testTag1));

		RelatedArticlesDtoResponse response = articleService.getArticlesByTagNameAndDate("test1", "20160922");

		Assert.assertNotNull(response);
	}

	@Test
	public void getArticlesByTagNameAndDate_whenMoreThan10Articles_shouldReturnTheSortedArticlesIds() throws ParseException {

		ArticleTag testTag1 = new ArticleTag("test1");
		Random random = new Random();
		String mockDate = "2016-09-22";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		for(int x = 0 ; x < 20; x++){
			Article article = new Article();
			String newMockDate = mockDate+" "+"05:30:"+random.nextInt(49+x);
			Date date = dateFormat.parse(newMockDate);
			article.setDate(date);
			article.setId(Long.valueOf(x));
			testTag1.addArticle(article);
		}

		List<Article> expectedArticles = new LinkedList<>(testTag1.getArticles());

		when(this.articleTagDao.findByName(anyString())).thenReturn(Optional.of(testTag1));

		RelatedArticlesDtoResponse response = articleService.getArticlesByTagNameAndDate("test1", "20160922");

		Assert.assertNotNull(response);

		expectedArticles.sort((tag1, tag2) -> tag2.getDate().compareTo(tag1.getDate()));

		List<Long> expectedSortedArticleIds = expectedArticles.subList(0, 10).stream().map(AbstractPersistent::getId).collect(Collectors.toList());

		Assert.assertEquals(expectedSortedArticleIds, response.getArticles());
	}

}
