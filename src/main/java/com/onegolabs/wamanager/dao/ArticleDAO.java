package com.onegolabs.wamanager.dao;

import com.onegolabs.wamanager.model.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * @author dmzhg
 */
public interface ArticleDAO {

	List<Article> getAllArticles();

	Article getArticleByMaterialId(int matId);

	void updateArticleForCalculation(Article article) throws SQLException;
}
