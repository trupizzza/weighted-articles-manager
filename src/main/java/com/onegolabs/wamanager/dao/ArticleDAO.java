package com.onegolabs.wamanager.dao;

import com.onegolabs.wamanager.model.Article;

import java.util.List;

/**
 * @author dmzhg
 */
public interface ArticleDAO {

	List<Article> getAllArticles();

	Article getArticle(int rollNo);

	void updateArticle(Article student);

	void deleteArticle(Article student);
}
