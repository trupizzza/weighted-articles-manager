package com.onegolabs.wamanager;

import com.onegolabs.wamanager.model.TempData;
import com.sun.javafx.application.LauncherImpl;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dmzhg
 */
public class WAManager extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(WAManager.class);

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
		LauncherImpl.launchApplication(WAManager.class, MyPreLoader.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		settings = MyPreLoader.getConfiguration();
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
		Scene scene = new Scene(mainSplitPane);
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
		TableColumn<TempData, String> code = new TableColumn<>(Messages.getString("code"));
		TableColumn<TempData, String> name = new TableColumn<>(Messages.getString("name"));
		code.setCellValueFactory(new PropertyValueFactory<>("code"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		ObservableList<TempData> data = FXCollections.observableArrayList(new TempData(1, "First"));
		data.add(new TempData(2, "Second"));
		data.add(new TempData(3, "Third"));
		data.add(new TempData(4, "Fourth"));
		articlesTable.getColumns().addAll(code, name);
		articlesTable.setItems(data);
		articlesTable.setVisible(true);
		articlesTable.setMinSize(300, 300);
		articlesTable.setEditable(false);
		articlesTable.setPrefHeight(500);
		articlesTable.setPrefWidth(882);
		articlesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, tempData, t1) -> {
			if (1 == 1) {
				articlesTable.setBackground((new Background(new BackgroundFill(
						Color.YELLOW,
						CornerRadii.EMPTY,
						Insets.EMPTY))));
			}
		});
		articlesTable.setRowFactory(tv -> new TableRow<TempData>() {
			@Override
			public void updateItem(TempData item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else if (item.getCode() == 1) {
						setStyle("-fx-background-color: tomato;");
				} else {
					setStyle("");
				}
			}
		});
	}

	private void initExitButton() {
		ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/exit.png"),
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
		ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/refresh.png"),
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
		ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/upload.png"),
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
