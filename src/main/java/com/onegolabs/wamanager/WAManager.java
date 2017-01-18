package com.onegolabs.wamanager;

import com.onegolabs.wamanager.model.Article;
import com.onegolabs.wamanager.model.TempData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dmzhg
 */
public class WAManager extends Application {

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
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale locale = Locale.getDefault();
        ResourceBundle myResources = ResourceBundle.getBundle("com.onegolabs.wamanager",
                locale);
        window = primaryStage;

        // TODO: localize correctly from resources
        primaryStage.setTitle(myResources.getString("title"));
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

        Scene scene = new Scene(mainSplitPane, 640, 480);
        //mainSplitPane.setDividerPosition(0, 0.70);
        primaryStage.setScene(scene);
        primaryStage.show();
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
        exit = new Button();
        // TODO: localize correctly from resources
        exit.setText("Exit");
        exit.setMinHeight(60);
        exit.setMinWidth(60);
        exit.setOnAction(e -> {
            System.out.println("Exit happened!");
            window.close();
        });
    }

    private void initRefreshButton() {
        refresh = new Button();
        refresh.setLineSpacing(0.5);
        // TODO: localize correctly from resources
        refresh.setText(Locale.getDefault().getDisplayName());
        refresh.setMinHeight(60);
        refresh.setMinWidth(60);
        refresh.setOnAction(e -> System.out.println("Refresh happened!"));
    }

    private void initUploadButton() {
        upload = new Button();
        // TODO: localize correctly from resources
        upload.setText("Upload");
        upload.setMinHeight(60);
        upload.setMinWidth(60);
        upload.setOnAction(e -> System.out.println("Upload happened!"));
    }
}
