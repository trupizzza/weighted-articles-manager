package com.onegolabs.wamanager;

import com.onegolabs.wamanager.model.TempData;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
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
    private GridPane bottomGridPane;
    private Button upload;
    private Stage window;
    private Button exit;
    private TableView<TempData> articlesTable;
    private Configuration settings;
    private GridPane bottomSearchAndInfoPane;
    private Label quickSearchLabel;
    private TextField quickSearchField;
    private Label articlesCountInfoLabel;
    private Label articlesCountLabel;
    private Label shortArticleDescription;
    private TextArea fullArticleDescription;
    private Label splitter;
    private Label shopLabel;
    private Label shopDescriptionLabel;
    private Label scalesLabel;
    private Label scalesDescriptionLabel;
    private GridPane shopAndScalesBottomPane;
    private Label validUntilLabel;
    private Label validUntilField;

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
        LOGGER.info("Initializing GUI...");
        initRefreshButton();
        initUploadButton();
        initExitButton();
        initArticlesTable();
        initPrimaryStage();
        initTopButtonBox();
        initTopBorderPane();
        initBottomGridPane();
        initMainSplitPane();
        initMainWindow();
        initQuickSearchLabel();
        initQuickSearchField();
        initSplitter();
        initArticlesCountInfoLabel();
        initArticlesCountLabel();
        initBottomSearchAndInfoPane();
        topBorderPane.setBottom(bottomSearchAndInfoPane);
        topBorderPane.minWidthProperty().bind(window.getScene().widthProperty());
        bottomGridPane.minWidthProperty().bind(window.getScene().widthProperty());

        //LOWER PANEL
        initShoreArticleDescriptionLabel();
        initFullArticleDescriptionArea();
        initShopLabel();
        initShopDescriptionLabel();
        initScalesLabel();
        initScalesDescriptionLabel();
        initShopAndScalesBottomPane();
        initBottomGridPaneElements();


        bottomGridPane.add(new HBox(), 0, 2);
        bottomGridPane.add(new HBox(), 0, 2);

    }

    private void initShopAndScalesBottomPane() {
        shopAndScalesBottomPane = new GridPane();
        shopAndScalesBottomPane.setPadding(new Insets(5, 5, 5, 5));
        shopAndScalesBottomPane.setStyle("-fx-border-color: gray");
        initShopAndScalesBottomPaneConstraints();
        shopAndScalesBottomPane.add(shopLabel, 0, 0);
        shopAndScalesBottomPane.add(shopDescriptionLabel, 1, 0);
        shopAndScalesBottomPane.add(splitter, 2, 0);
        shopAndScalesBottomPane.add(scalesLabel, 3, 0);
        shopAndScalesBottomPane.add(scalesDescriptionLabel, 4, 0);
    }

    private void initShopAndScalesBottomPaneConstraints() {
        ColumnConstraints shopLabelConstraints = new ColumnConstraints(60);

        ColumnConstraints shopLabelDescriptionConstraints = new ColumnConstraints(150, 200, 200);

        ColumnConstraints splitterConstraints = new ColumnConstraints(0, 0, Double.MAX_VALUE);
        splitterConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints scalesLabelConstraints = new ColumnConstraints(50);

        ColumnConstraints scalesDescriptionLabelConstraints = new ColumnConstraints(300);

        shopAndScalesBottomPane.getColumnConstraints().addAll(shopLabelConstraints,
                shopLabelDescriptionConstraints, splitterConstraints,
                scalesLabelConstraints, scalesDescriptionLabelConstraints);
    }

    private void initScalesDescriptionLabel() {
        scalesDescriptionLabel = new Label();
        // TODO: replace hardcode with implementation
        scalesDescriptionLabel.setText(" Aclas LB1.25 @ Onego Labs via OLE driver (TCP/IP)");
    }

    private void initScalesLabel() {
        scalesLabel = new Label(Messages.getString("scales"));
    }

    private void initShopDescriptionLabel() {
        shopDescriptionLabel = new Label();
        // TODO: replace hardcode with implementation
        shopDescriptionLabel.setText("Narvskaya 96, Saint-Petersburg");
    }

    private void initShopLabel() {
        shopLabel = new Label(Messages.getString("shop"));

    }

    private void initBottomGridPaneElements() {
        initBottomGridPaneconstraints();
        bottomGridPane.add(shortArticleDescription, 0, 0);
        bottomGridPane.add(fullArticleDescription, 0, 1);
        bottomGridPane.add(shopAndScalesBottomPane, 0, 2);
    }

    private void initBottomGridPaneconstraints() {
        ColumnConstraints shortArticleDescriptionConstraints = new ColumnConstraints();
        shortArticleDescriptionConstraints.setHgrow(Priority.ALWAYS);
        ColumnConstraints fullArticleDescriptionConstraints = new ColumnConstraints();
        bottomGridPane.getColumnConstraints().addAll(shortArticleDescriptionConstraints, fullArticleDescriptionConstraints);

    }

    private void initSplitter() {
        splitter = new Label();
    }

    private void initFullArticleDescriptionArea() {
        fullArticleDescription = new TextArea();
        fullArticleDescription.setMaxWidth(Double.POSITIVE_INFINITY);
    }

    private void initShoreArticleDescriptionLabel() {
        shortArticleDescription = new Label(Messages.getString("selectedArticleShortDesc"));
        shortArticleDescription.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
        shortArticleDescription.setPadding(new Insets(0, 5, 5, 5));
    }

    private void initBottomSearchAndInfoGridPaneConstraints() {
        ColumnConstraints quickSearchLabelConstraints = new ColumnConstraints(100);

        ColumnConstraints quickSearchFieldConstraints = new ColumnConstraints(130);

        ColumnConstraints splitterConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        splitterConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints articlesCountInfoLabelConstraints = new ColumnConstraints(100);

        ColumnConstraints articlesCountLabelConstraints = new ColumnConstraints(50);
        articlesCountInfoLabelConstraints.setHalignment(HPos.RIGHT);

        bottomSearchAndInfoPane.getColumnConstraints().addAll(quickSearchLabelConstraints,
                quickSearchFieldConstraints, splitterConstraints,
                articlesCountInfoLabelConstraints, articlesCountLabelConstraints);
    }

    private void initArticlesCountLabel() {
        // TODO: replace hardcode with implementation
        articlesCountLabel = new Label("666");
        articlesCountLabel.setGraphicTextGap(3);
        articlesCountLabel.setStyle("-fx-font-weight: bold");
        articlesCountLabel.setPadding(new Insets(0, 0, 0, 8));
    }

    private void initArticlesCountInfoLabel() {
        articlesCountInfoLabel = new Label(Messages.getString("amountInfoLabel"));
        articlesCountInfoLabel.setGraphicTextGap(3);
    }

    private void initQuickSearchField() {
        quickSearchField = new TextField();
        quickSearchField.setPromptText(Messages.getString("enterArticleCode"));
    }

    private void initQuickSearchLabel() {
        quickSearchLabel = new Label(Messages.getString("quickSearchLabel"));
        quickSearchLabel.setGraphicTextGap(3);
    }

    private void initBottomSearchAndInfoPane() {
        bottomSearchAndInfoPane = new GridPane();
        bottomSearchAndInfoPane.setMaxHeight(25);
        bottomSearchAndInfoPane.minWidthProperty().bind(window.getScene().widthProperty());
        bottomSearchAndInfoPane.setPadding(new Insets(5, 5, 5, 5));
        initBottomSearchAndInfoGridPaneConstraints();
        bottomSearchAndInfoPane.add(quickSearchLabel, 0, 0);
        bottomSearchAndInfoPane.add(quickSearchField, 1, 0);
        bottomGridPane.add(splitter, 2, 0);
        bottomSearchAndInfoPane.add(articlesCountInfoLabel, 3, 0);
        bottomSearchAndInfoPane.add(articlesCountLabel, 4, 0);
    }

    private void initMainWindow() {
        Scene scene = new Scene(mainSplitPane);
        window.setMinWidth(640);
        window.setMinHeight(480);
        window.setMaximized(true);
        window.setScene(scene);
    }

    private void initMainSplitPane() {
        mainSplitPane = new SplitPane(topBorderPane, bottomGridPane);
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        mainSplitPane.setDividerPositions(0.75f);
    }

    private void initBottomGridPane() {
        bottomGridPane = new GridPane();
        bottomGridPane.setPadding(new Insets(5, 5, 5, 5));
    }

    private void initTopButtonBox() {
        topButtonBox = new HBox();
        topButtonBox.setSpacing(5);
        topButtonBox.setPadding(new Insets(5, 5, 5, 5));
        topButtonBox.getChildren().addAll(refresh, upload, exit);
    }

    private void initTopBorderPane() {
        topBorderPane = new BorderPane();
        topBorderPane.setTop(topButtonBox);
        topBorderPane.setLeft(new Label());
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
        TableColumn<TempData, String> description = new TableColumn<>(Messages.getString("description"));

        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ObservableList<TempData> data = FXCollections.observableArrayList(new TempData(1, "First", "Description 1"));

        data.add(new TempData(2, "Second", "Description 2"));
        data.add(new TempData(3, "Third", "Description 3"));
        data.add(new TempData(4, "Fourth", "Description 4"));
        articlesTable.getColumns().addAll(code, name, description);
        articlesTable.setItems(data);
        articlesTable.setVisible(true);
        articlesTable.setEditable(false);
        articlesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, tempData, t1) -> {
            LOGGER.info(t1.toString());
            updateInformation(t1);
        });
    }

    private void updateInformation(TempData t1) {
        fullArticleDescription.setText(t1.toString());
        shortArticleDescription.setText(t1.getName());
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
