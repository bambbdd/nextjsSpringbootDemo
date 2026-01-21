package com.bambbdd.domain.article.service;

import com.bambbdd.domain.article.entity.Article;
import com.bambbdd.domain.article.repository.ArticleRepository;
import com.bambbdd.global.rsData.RsData;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() { // 다건
        return articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id) { // 단건
        return articleRepository.findById(id);
    }

    @Transactional
    public RsData<Article> create(String subject, String content) {
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        return RsData.of(
                "S-2",
                "게시물이 생성되었습니다.",
                article
        );
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public RsData<Article> modify(Article article, @NotBlank String subject, @NotBlank String subject1, @NotBlank String content) {
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);

        return RsData.of(
                "S-3",
                "%d번 게시물이 수정되었습니다.".formatted(article.getId()),
                article
        );
    }

    public RsData<Article> delete(Long id) {
        articleRepository.deleteById(id);

        return RsData.of(
                "S-4",
                "%d번 게시물이 삭제되었습니다.".formatted(id)
        );
    }
}
