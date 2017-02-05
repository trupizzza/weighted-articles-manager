package com.onegolabs.wamanager.dao.impl;

import com.onegolabs.resources.Queries;
import com.onegolabs.util.DiscountUtils;
import com.onegolabs.util.WAMUtils;
import com.onegolabs.wamanager.dao.ArticleDAO;
import com.onegolabs.wamanager.dao.ArticleTableColumns;
import com.onegolabs.wamanager.dbconnection.ConnectionFactory;
import com.onegolabs.wamanager.model.Article;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmzhg
 */
public class ArticleDAOImpl implements ArticleDAO {

    private Connection connection;
    private Statement statement;
    private PreparedStatement pStatement;
    private ResultSet rs;
    private String query;

    @Override
    public List<Article> getAllArticles() throws SQLException {
        query = Queries.getQuery("main");
        connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
        rs = statement.executeQuery(query);
        return parseArticles(rs);
    }

    private List<Article> parseArticles(ResultSet rs) throws SQLException {
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article article = new Article();
            article.setDescription(rs.getString(ArticleTableColumns.MATDSC));
            article.setId(rs.getInt(ArticleTableColumns.GID));
            article.setMaterialNumber(rs.getInt(ArticleTableColumns.MATNO));
            article.setName(rs.getString(ArticleTableColumns.MATNAM));
            double materialPrice = rs.getBigDecimal(ArticleTableColumns.PRICE)
                                     .setScale(2, BigDecimal.ROUND_UNNECESSARY)
                                     .doubleValue();
            article.setPrice(materialPrice);
            article.setWeighed(WAMUtils.getBooleanFlagValue(rs.getString(ArticleTableColumns.HASTOBEWEIGHTED)));
            article.setPlu(rs.getInt(ArticleTableColumns.SCALEPLU));
            article.setExpiryDaysCount(rs.getInt(ArticleTableColumns.SCALEEXPIRYDAYS));
            article.setExpiryDateShop(rs.getString(ArticleTableColumns.EXPDATSHOP));
            article.setLabelId(rs.getInt(ArticleTableColumns.LABEL));
            article.setAddNo1(rs.getInt(ArticleTableColumns.ADDNO1));
            article.setMorePrices(WAMUtils.getBooleanFlagValue(rs.getString(ArticleTableColumns.MOREPRICES)));
            try {
                int lifeBoxTotal = Integer.parseInt(rs.getString(ArticleTableColumns.LIFE_BOT_TOTAL));
                article.setLifeBoxTotal(lifeBoxTotal);
            } catch (NumberFormatException nfe) {
                article.setLifeBoxTotal(0);
            }
            if (article.getWeighed()) {
                article.setManufacturerExpiryDate(rs.getString(ArticleTableColumns.EXPDATSHOP2));
                article.setExpiryDate(rs.getString(ArticleTableColumns.EXPDATE));
            } else {
                article.setExpiryDate(rs.getString(ArticleTableColumns.EXPDATSHOP));
            }
            article.setExpDaysToScale(rs.getInt(ArticleTableColumns.EXPDAYS2SCALE));
            article.setDiscount(calculateDiscount(article));
            articles.add(article);
        }
        return articles;
    }

    private int calculateDiscount(Article article) {
        BigDecimal discountMultiplier = DiscountUtils.getDiscountMultiplier(article.getAddNo1(),
                article.getMorePrices());
        discountMultiplier = discountMultiplier.round(new MathContext(2));
        return discountMultiplier.subtract(discountMultiplier.setScale(0, RoundingMode.FLOOR))
                                 .movePointRight(discountMultiplier.scale())
                                 .intValue();
    }

    @Override
    public Article getArticleByMaterialId(int matId) {
        return null;
    }


    @Override
    public void updateArticleForCalculation(Article article) throws SQLException {
        query = Queries.getQuery("updateArticle");
        connection = ConnectionFactory.getConnection();
        pStatement = connection.prepareStatement(query);
        pStatement.setString(1, article.getDescription());
        pStatement.setString(2, article.getExpiryDateShop());
        pStatement.setString(3, article.getManufacturerExpiryDate());
        pStatement.setInt(4, article.getId());
        rs = statement.executeQuery(query);
    }
}
