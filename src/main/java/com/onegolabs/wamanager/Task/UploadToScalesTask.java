package com.onegolabs.wamanager.task;

import com.onegolabs.Messages;
import com.onegolabs.wamanager.context.Context;
import com.onegolabs.wamanager.exception.UploadingException;
import com.onegolabs.wamanager.scales.ScalesLoader;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.BreakIterator;
import java.util.ArrayList;

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
       // loadArticles();
        updateProgress(100, 100);
        updateMessage(Messages.getString("status.finishing"));
        loader.endUpload();
        updateMessage(Messages.getString("status.disconnecting"));
        loader.disconnect();
        return null;
    }

//    private void loadArticles() {
//        for (int i = 0; i < editor.validArticles.size(); i++) {
//
//            int msg = 0;
//
//            Article article = editor.validArticles.get(i);
//            String messageText = article.getDescription() != null ? article.getDescription() : "";
//
//            String[] messageLines = getWrappedText(messageText, loader);
//            if (messageLines != null) {
//                msg = loader.uploadMessage(messageLines);
//            }
//            int label = article.getLabelId();
//            int plu = article.getPlu();
//            int code = article.getMaterialNumber();
//            String name = article.getName();
//            double price = article.getPrice();
//            int life = article.getExpDaysToScale();
//            boolean htbw = article.getWeighed();
//            int discount = article.getDiscount();
//
//            loader.uploadArticle(plu, code, name, price, life, msg, htbw, label, discount);
//            updateProgress(i, editor.validArticles.size());
//        }
//    }

    @Override
    protected void failed() {
        LOGGER.info("Error during downloading!");
        throw UploadingException.wrap(this.getException());
    }

    @Override
    protected void succeeded() {
        String resourceString = Messages.getString("status.finished");
        LOGGER.info(resourceString);
//        if (getContext().isSilentModeOn()) {
//            LOGGER.info("Uploading in silent mode is done!");
//            System.exit(0);
//        }
    }

    private String[] getWrappedText(String txt, ScalesLoader loader) {
        int rows = loader.getMessageRows();
        int cols = loader.getMessageColumns();

        if (txt == null || txt.isEmpty() || rows < 1 || cols < 1) {
            return null;
        }

        ArrayList<String> lines = new ArrayList<String>();
        BreakIterator boundary = BreakIterator.getLineInstance();
        boundary.setText(txt);

        String curr = "", part = "";
        int start = boundary.first(), end, ln = 0;
        boolean doBreak = false, done = false;

        do {
            if (part.isEmpty()) {
                if ((end = boundary.next()) == BreakIterator.DONE) {
                    doBreak = !curr.isEmpty();
                    done = true;
                } else {
                    part = txt.substring(start, end);
                    start = end;
                }
            }

            if (!part.isEmpty()) {
                if (curr.length() + part.length() > cols) {
                    if (curr.isEmpty()) {
                        curr = part.substring(0, cols);
                        part = part.substring(cols);
                    }
                    doBreak = true;
                } else {
                    if (part.endsWith("\n")) {
                        part = part.substring(0, part.length() - 1);
                        doBreak = true;
                    }
                    curr += part;
                    part = "";
                }
            }

            if (doBreak) {
                lines.add(curr);
                curr = "";
                doBreak = false;

                if (++ln >= rows) {
                    done = true;
                }
            }
        } while (!done);

        String[] linesArr = new String[lines.size()];
        return lines.toArray(linesArr);
    }
}
