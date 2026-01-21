package com.bambbdd.domain.article.controller;

import com.bambbdd.domain.article.entity.Article;
import com.bambbdd.domain.article.service.ArticleService;
import com.bambbdd.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                "%d번 게시물은 존재하지 않습니다.".formatted(id)
        ));
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String subject;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class WriteResponse { // 요소가 많아질 수도 있으니 객체로 통합
        private final Article article;
    }

    @PostMapping("")
    public RsData<WriteResponse> write(@Valid @RequestBody WriteRequest writeRequest) {
        RsData<Article> writeRs = articleService.create(writeRequest.getSubject(), writeRequest.getContent());

        if ( writeRs.isFail() ) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriteResponse(writeRs.getData())
        );
    }

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String subject;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class ModifyResponse { // 요소가 많아질 수도 있으니 객체로 통합
        private final Article article;
    }

    @PatchMapping("/{id}")
    public RsData modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id) {
        Optional<Article> opArticle = articleService.findById(id);

        if ( opArticle.isEmpty() ) return RsData.of(
                "F-1",
                "%d번 게시물은 존재하지 않습니다.".formatted(id)
        );

        // 회원 권한 체크 canModify();

        RsData<Article> modifyRs = articleService.modify(opArticle.get(), modifyRequest.getSubject(), modifyRequest.getSubject(), modifyRequest.getContent());

        return RsData.of(
                modifyRs.getResultCode(),
                modifyRs.getMsg(),
                new ModifyResponse(modifyRs.getData())
        );
    }
}
