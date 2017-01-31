package com.onegolabs.wamanager.dao.impl;

import com.onegolabs.wamanager.dao.ArticleDAO;
import com.onegolabs.wamanager.dbconnection.ConnectionFactory;
import com.onegolabs.wamanager.model.Article;

import java.sql.*;
import java.util.List;

/**
 * @author dmzhg
 */
public class ArticleDAOImpl implements ArticleDAO {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    @Override
    public List<Article> getAllArticles() {
//        String query = "UPDATE MATERIAL SET MATDESC = ?, EXPDATSHOP = ?, EXPDATSHOP2 = ? WHERE GID = ?";
//        Connection con = ConnectionFactory.getConnection();
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery(query);
        return null;
    }

    @Override
    public Article getArticleByMaterialId(int matId) {
        return null;
    }


    @Override
    public void updateArticleForCalculation(Article article) throws SQLException {
        String query = "UPDATE MATERIAL SET MATDESC = ?, EXPDATSHOP = ?, EXPDATSHOP2 = ? WHERE GID = ?";
        Connection con = ConnectionFactory.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    }
}
