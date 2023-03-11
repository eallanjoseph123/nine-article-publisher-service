package com.nine.service.service;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.entity.Article;
import com.nine.service.model.mapper.ArticleMapper;
import com.nine.service.repository.ArticleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("Unit")
@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

	@InjectMocks
	private ArticleService articleService;

	@Mock
	private ArticleDao articleDao;


	@Test
	public void createArticle_success(){
		List<String> tags = Arrays.asList("test1","test2");
		ArticleDtoRequest dtoRequest = ArticleDtoRequest.builder()
				.body("test body")
				.title("title")
				.tags(tags)
				.build();

		Article newArticle = ArticleMapper.INSTANCE.articleDtoToArticle(dtoRequest);
		newArticle.setDate(new Date());
		newArticle.setId(12L);

		when(this.articleDao.save(any())).thenReturn(newArticle);

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


		Article newArticle = ArticleMapper.INSTANCE.articleDtoToArticle(dtoRequest);
		newArticle.setDate(new Date());
		newArticle.setId(12L);


		when(this.articleDao.findById(id)).thenReturn(Optional.of(newArticle));

		ArticleDtoResponse response = articleService.getArticleById(id);

		Assert.assertNotNull(response);
		Assert.assertEquals(id, response.getId());
		Mockito.verify(articleDao, Mockito.times(1)).findById(id);
	}

}
