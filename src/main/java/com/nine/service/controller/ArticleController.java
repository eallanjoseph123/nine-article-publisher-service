package com.nine.service.controller;

import com.nine.service.model.dto.ArticleDtoRequest;
import com.nine.service.model.dto.ArticleDtoResponse;
import com.nine.service.model.dto.RelatedArticlesDtoResponse;
import com.nine.service.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@AllArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@Operation(summary = "create article")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "2XX", description = "Successfully sent",
					content = @Content),
			@ApiResponse(responseCode = "4XX", description = "Invalid Data",
					content = @Content),
			@ApiResponse(responseCode = "5XX", description = "Server Error",
					content = @Content) })
	@PostMapping
	public ArticleDtoResponse createArticle(@RequestBody ArticleDtoRequest dtoRequest){
		return this.articleService.createArticle(dtoRequest);
	}

	@Operation(summary = "get article by article ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "2XX", description = "Successfully sent",
					content = @Content),
			@ApiResponse(responseCode = "4XX", description = "Invalid Data",
					content = @Content),
			@ApiResponse(responseCode = "5XX", description = "Server Error",
					content = @Content) })
	@GetMapping("{articleId}")
	public ArticleDtoResponse getArticleById(@PathVariable Long articleId){
		return this.articleService.getArticleById(articleId);
	}

	@Operation(summary = "get article by tag and date")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "2XX", description = "Successfully sent",
					content = @Content),
			@ApiResponse(responseCode = "4XX", description = "Invalid Data",
					content = @Content),
			@ApiResponse(responseCode = "5XX", description = "Server Error",
					content = @Content) })
	@GetMapping("{tagName}/{dateRequest}")
	public RelatedArticlesDtoResponse getArticlesByTagNameAndDate(@PathVariable String tagName, @PathVariable String dateRequest){
		return this.articleService.getArticlesByTagNameAndDate(tagName, dateRequest);
	}
}
