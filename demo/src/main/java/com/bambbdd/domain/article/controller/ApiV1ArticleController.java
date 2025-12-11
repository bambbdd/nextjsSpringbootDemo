package com.bambbdd.domain.article.controller;

import com.bambbdd.domain.article.entity.Article;
import com.bambbdd.domain.article.service.ArticleService;
import com.bambbdd.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse { // 요소가 많아질 수도 있으니 객체로 통합
        private final List<Article> articles;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleResponse { // 요소가 많아질 수도 있으니 객체로 통합
        private final Article article;
    }

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles() {
        List<Article> articles = articleService.getList();
        return RsData.of("S-1", "성공", new ArticlesResponse(articles));
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        return articleService.getArticle(id).map(article -> RsData.of(

                "S-1",
                "성공",
                new ArticleResponse(article)
        )).orElseGet(() -> RsData.of(

                "F-1",
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }
}
