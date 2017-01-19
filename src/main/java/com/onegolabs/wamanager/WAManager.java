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
import javafx.stage.Stage;

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
	private Configuration settings;

	public static void main(String[] args) {
		launch(args);
	}

	public Configuration getSettings() {
		return settings;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		initGUI();
		window.show();

	}

	private void initGUI() {
		initRefreshButton();
		initUploadButton();
		initExitButton();
		initArticlesTable();
		initPrimaryStage();
		initTopButtonBox();
		initTopBorderPane();
		initBottomBorderPane();
		initMainSplitPane();
		initMainWindow();
	}

	private void initMainWindow() {
		Scene scene = new Scene(mainSplitPane, 640, 480);
		window.setMaximized(true);
		window.setScene(scene);
	}

	private void initMainSplitPane() {
		mainSplitPane = new SplitPane(topBorderPane, bottomBorderPane);
		mainSplitPane.setOrientation(Orientation.VERTICAL);
		mainSplitPane.setDividerPositions(0.75f);
	}

	private void initBottomBorderPane() {
		bottomBorderPane = new BorderPane();
	}

	private void initTopButtonBox() {
		topButtonBox = new HBox();
		topButtonBox.setMaxHeight(100);
		topButtonBox.setSpacing(5);
		topButtonBox.setPadding(new Insets(5, 5, 5, 5));
		topButtonBox.getChildren().add(refresh);
		topButtonBox.getChildren().add(upload);
		topButtonBox.getChildren().add(exit);
	}

	private void initTopBorderPane() {
		topBorderPane = new BorderPane();
		topBorderPane.setTop(topButtonBox);
		topBorderPane.setCenter(articlesTable);
	}

	private void initPrimaryStage() {
		window.getIcons().add(new Image(getClass().getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
		window.setTitle(Messages.getString("title"));
	}

	private void initArticlesTable() {
		articlesTable = new TableView<>();
		TableColumn code = new TableColumn("Code");
		TableColumn name = new TableColumn("Name");
		code.setCellValueFactory(new PropertyValueFactory<TempData, String>("code"));
		name.setCellValueFactory(new PropertyValueFactory<TempData, String>("name"));
		final ObservableList<TempData> data = FXCollections.observableArrayList(new TempData(1, "First"));
		articlesTable.getColumns().addAll(code, name);
		articlesTable.setItems(data);
		articlesTable.setVisible(true);
		articlesTable.setMinSize(300, 300);
		articlesTable.setEditable(false);
		articlesTable.setPrefHeight(500);
		articlesTable.setPrefWidth(882);
	}

	private void initExitButton() {
		ImageView icon = new ImageView(new Image(
				getClass().getResourceAsStream("/icons/exit.png"),
				30,
				30,
				true,
				true));
		exit = new Button(Messages.getString("exit"), icon);
		exit.setContentDisplay(ContentDisplay.TOP);
		exit.setText(Messages.getString("exit"));
		exit.setMinHeight(60);
		exit.setMinWidth(60);
		exit.setOnAction(e -> {
			System.out.println("Exit happened!");
			window.close();
		});
	}

	private void initRefreshButton() {
		ImageView icon = new ImageView(new Image(
				getClass().getResourceAsStream("/icons/refresh.png"),
				30,
				30,
				true,
				true));
		refresh = new Button(Messages.getString("refresh"), icon);
		refresh.setContentDisplay(ContentDisplay.TOP);
		refresh.setMinHeight(60);
		refresh.setMinWidth(60);
		refresh.setOnAction(e -> System.out.println("Refresh happened!"));
	}

	private void initUploadButton() {
		ImageView icon = new ImageView(new Image(
				getClass().getResourceAsStream("/icons/upload.png"),
				30,
				30,
				true,
				true));
		upload = new Button(Messages.getString("upload"), icon);
		upload.setContentDisplay(ContentDisplay.TOP);
		upload.setMinHeight(60);
		upload.setMinWidth(60);
		upload.setOnAction(e -> System.out.println("Upload happened!"));
	}
}
