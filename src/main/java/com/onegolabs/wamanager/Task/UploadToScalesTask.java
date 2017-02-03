package com.onegolabs.wamanager.task;

import com.onegolabs.resources.Messages;
import com.onegolabs.util.WAMUtils;
import com.onegolabs.wamanager.context.Context;
import com.onegolabs.wamanager.exception.scales.ScalesCommandException;
import com.onegolabs.wamanager.model.Article;
import com.onegolabs.wamanager.scales.ScalesLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dmzhg
 */
public class UploadToScalesTask extends Task<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadToScalesTask.class);

    private ScalesLoader loader;

    @Override
    protected Void call() throws Exception {
        loader = Context.getContext().getScalesLoader();
        updateProgress(0, 100);
        updateMessage(Messages.getString("status.connecting"));
        loader.connect();
        updateMessage(Messages.getString("status.cleanup"));
        loader.beginUpload();
        loader.clearAll();
        updateMessage(Messages.getString("status.uploading"));
        loadArticles();
        updateProgress(100, 100);
        updateMessage(Messages.getString("status.finishing"));
        loader.endUpload();
        updateMessage(Messages.getString("status.disconnecting"));
        loader.disconnect();
        return null;
    }

    private void loadArticles() {
        ObservableList<Article> data = FXCollections.observableArrayList(new Article(
                1,
                "Мыло \"Черепашка\"",
                "Волшебное мыло - вкусы \"Грифовая\", \"Трионикс\"",
                500.0,
                true,
                123,
                123,
                "22.12.2012",
                666,
                555));

        data.add(new Article(
                559165,
                "Бутылка водки \"ЖурилоFF\"",
                "Гадость кислющая, но зачем-то же её пьют! Вот-таки странная то вещь творится на земле Русской!",
                1200,
                false,
                123,
                123,
                "01.01.2017",
                6,
                5));

        data.add(new Article(
                555999,
                "Жепь \"Ебрило\"",
                "Щячло попячьться адинадин ОЛООлолОЛо Онотоле Негодуе!!!1!!один!!11",
                0.45,
                true,
                1223,
                13,
                "01.01.2016",
                1,
                2));

        for (int i = 0; i < data.size(); i++) {

            int msg = 0;

            Article article = data.get(i);
            String messageText = article.getDescription();
            if (messageText == null) {
                messageText = "";
            }

            String[] messageLines = WAMUtils.getWrappedText(messageText, loader);
            if (messageLines != null) {
                msg = loader.uploadMessage(messageLines);
            }

            int label = article.getLabelId();
            int plu = article.getPlu();
            int code = article.getMaterialNumber();
            String name = article.getName();
            double price = article.getPrice();
            int life = article.getExpiryDaysCount();
            boolean htbw = article.getWeighed();
            int discount = 0;
//            int discount = article.getDiscount();
            loader.uploadArticle(plu, code, name, price, life, msg, htbw, label, discount);

            updateProgress(0, i);
        }
    }

    @Override
    protected void failed() {
        LOGGER.error("Exception occurred while uploading!");
        throw ScalesCommandException.wrap(getException());
    }

    @Override
    protected void succeeded() {
    }
}
