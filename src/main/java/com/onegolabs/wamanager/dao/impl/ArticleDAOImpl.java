package com.onegolabs.wamanager.dao.impl;

import com.onegolabs.wamanager.dao.ArticleDAO;
import com.onegolabs.wamanager.model.Article;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @author dmzhg
 */
public class ArticleDAOImpl implements ArticleDAO {

    private Connection connection;
    private Statement statement;

    @Override
    public List<Article> getAllArticles() {
        return null;
    }

    @Override
    public Article getArticle(int rollNo) {
        return null;
    }

    @Override
    public void updateArticle(Article student) {

    }

    @Override
    public void deleteArticle(Article student) {

    }
}
