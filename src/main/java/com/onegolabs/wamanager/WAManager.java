package com.onegolabs.wamanager;

import com.onegolabs.wamanager.model.TempData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dmzhg
 */
public class WAManager extends Application {

    public static ResourceBundle r;

    private Button refresh;
    private SplitPane mainSplitPane;
    private BorderPane topBorderPane;
    private HBox topButtonBox;
    private BorderPane bottomBorderPane;
    private Button upload;
    private Stage window;
    private Button exit;
    private TableView<TempData> articlesTable;

    public static void main(String[] args) {
        r = ResourceBundle.getBundle("language", new Locale("ru"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.getIcons().add(new Image(getClass()
                .getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
        primaryStage.setTitle(r.getString("title"));
        initRefreshButton();
        initUploadButton();
        initExitButton();
        initArticlesTable();
        topBorderPane = new BorderPane();
        topButtonBox = new HBox();
        topButtonBox.setMaxHeight(100);
        topButtonBox.setSpacing(5);
        topButtonBox.setPadding(new Insets(5, 5, 5, 5));
        topButtonBox.getChildren().add(refresh);
        topButtonBox.getChildren().add(upload);
        topButtonBox.getChildren().add(exit);
        topBorderPane.setTop(topButtonBox);
        topBorderPane.setCenter(articlesTable);
        bottomBorderPane = new BorderPane();
        mainSplitPane = new SplitPane(topBorderPane, bottomBorderPane);
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        mainSplitPane.setDividerPositions(0.75f);
        Scene scene = new Scene(mainSplitPane, 640, 480);
        SplitPane.Divider a = new SplitPane.Divider();
        window.setScene(scene);
        window.show();
    }

    private void initArticlesTable() {
        articlesTable = new TableView<>();
        TableColumn code = new TableColumn("Code");
        TableColumn name = new TableColumn("Name");
        code.setCellValueFactory(new PropertyValueFactory<TempData, String>("code"));
        name.setCellValueFactory(new PropertyValueFactory<TempData, String>("name"));
        final ObservableList<TempData> data = FXCollections.observableArrayList(
                new TempData(1, "First"));
        articlesTable.getColumns().addAll(code, name);
        articlesTable.setItems(data);
        articlesTable.setVisible(true);
        articlesTable.setMinSize(300, 300);
        articlesTable.setEditable(false);
        articlesTable.setPrefHeight(500);
        articlesTable.setPrefWidth(882);
    }

    private void initExitButton() {
        ImageView icon = new ImageView
                (new Image(getClass()
                        .getResourceAsStream("/icons/exit.png"), 30, 30, true, true));
        exit = new Button(r.getString("exit"), icon);
        exit.setContentDisplay(ContentDisplay.TOP);
        exit.setText(r.getString("exit"));
        exit.setMinHeight(60);
        exit.setMinWidth(60);
        exit.setOnAction(e -> {
            System.out.println("Exit happened!");
            window.close();
        });
    }

    private void initRefreshButton() {
        ImageView icon = new ImageView
                (new Image(getClass()
                        .getResourceAsStream("/icons/refresh.png"), 30, 30, true, true));
        refresh = new Button(r.getString("refresh"), icon);
        refresh.setContentDisplay(ContentDisplay.TOP);
        refresh.setMinHeight(60);
        refresh.setMinWidth(60);
        refresh.setOnAction(e -> System.out.println("Refresh happened!"));
    }

    private void initUploadButton() {
        ImageView icon = new ImageView
                (new Image(getClass()
                        .getResourceAsStream("/icons/upload.png"), 30, 30, true, true));
        upload = new Button(r.getString("upload"), icon);
        upload.setContentDisplay(ContentDisplay.TOP);
        upload.setMinHeight(60);
        upload.setMinWidth(60);
        upload.setOnAction(e -> System.out.println("Upload happened!"));
    }
}
