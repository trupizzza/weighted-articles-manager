package com.onegolabs.wamanager;

import com.onegolabs.Messages;
import com.onegolabs.SimpleService;
import com.onegolabs.wamanager.model.Article;
import com.onegolabs.wamanager.view.TableColumnsConfigurationView;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author dmzhg
 */
public class WAManager extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(WAManager.class);

    private Button refresh;
    private SplitPane mainSplitPane;
    private BorderPane topBorderPane;
    private GridPane topGridPane;
    private HBox topButtonBox;
    private GridPane bottomGridPane;
    private Button upload;
    private Stage window;
    private Button exit;
    private TableView<Article> articlesTable;
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
    private DatePicker validUntilDatePicker;
    private Label mfrExpiryDateLabel;
    private DatePicker mfrExpiryDatePicker;
    private GridPane bottomExpiryDatesPane;
    private StringConverter<LocalDate> dateConverter;
    private Callback<DatePicker, DateCell> cellFactory;
    private ObservableList<Article> data;
    private ObservableList<Article> filteredData;
    private MenuBar topMenu;
    private Menu viewMenu;
    private Menu helpMenu;
    private MenuItem aboutAppMenuItem;
    private Map<Integer, TableColumn<Article, ?>> sortedColumns = new TreeMap<>();

    private MenuItem configureColumnsMenuItem;
    public static void main(String[] args) {
        LauncherImpl.launchApplication(WAManager.class, WamPreLoader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        settings = WamPreLoader.getConfiguration();
        window = primaryStage;
        initGUI();
        window.show();
    }

    private void initGUI() {
        LOGGER.info("Initializing GUI...");
        initDateConverter();
        // init cell factory with restriction that you can't pick Date < Now
        initDateCellFactory();
        initRefreshButton();
        initUploadButton();
        initExitButton();
        initQuickSearchField();
        initArticlesTable();
        initPrimaryStage();
        initTopButtonBox();
        initViewMenu();
        initHelpMenu();
        initTopMenu();
        initTopGridPane();
        initTopBorderPane();
        initBottomGridPane();
        initMainSplitPane();
        initMainWindow();
        initQuickSearchLabel();
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
        initValidUntilLabel();
        initValidUntilDatePicker();
        initMfrExpiryDateLabel();
        initMfrExpiryDatePicker();

        initBottomExpiryDatesPane();
        initBottomGridPaneElements();

    }

    public Map<Integer, TableColumn<Article, ?>> getSortedColumns() {
        return sortedColumns;
    }

    private void initHelpMenu() {
        helpMenu = new Menu(Messages.getString("helpMenu"));
        aboutAppMenuItem = new MenuItem(Messages.getString("aboutApp"));
        helpMenu.setOnAction(e -> {
            showProgramInfo();
        });
        helpMenu.getItems().add(aboutAppMenuItem);

    }

    private void showProgramInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Messages.getString("aboutApp"));
        alert.setHeaderText(Messages.getString("title"));
        alert.setContentText("message");

        TextArea textArea = new TextArea(Messages.getString("aboutAppInfo"));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void initViewMenu() {
        viewMenu = new Menu(Messages.getString("viewMenu"));
        configureColumnsMenuItem = new MenuItem(Messages.getString("configureColumnsItem"));
        viewMenu.getItems().add(configureColumnsMenuItem);
        configureColumnsMenuItem.setOnAction(e -> {
            TableColumnsConfigurationView view = new TableColumnsConfigurationView(getSortedColumns());
        });
    }

    private void initTopGridPane() {
        topGridPane = new GridPane();
        initTopGridPaneConstraints();
        topGridPane.add(topMenu, 0, 0);
        topGridPane.add(topButtonBox, 0, 1);
    }

    private void initTopGridPaneConstraints() {
        ColumnConstraints topMenuConstraints = new ColumnConstraints();
        topMenuConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints topButtonBoxConstraints = new ColumnConstraints();

        topGridPane.getColumnConstraints().addAll(topMenuConstraints, topButtonBoxConstraints);
    }

    private void initTopMenu() {
        topMenu = new MenuBar();
        topMenu.getMenus().addAll(viewMenu, helpMenu);
    }

    private void initMfrExpiryDateLabel() {
        mfrExpiryDateLabel = new Label(Messages.getString("mfrExpiryDate"));
    }

    private void initDateCellFactory() {
        cellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        };
    }

    private void initDateConverter() {
        dateConverter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormatter.format(localDate);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String s) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(s, dateFormatter);
            }
        };
    }

    private void initMfrExpiryDatePicker() {
        mfrExpiryDatePicker = new DatePicker();
        mfrExpiryDatePicker.setEditable(false);
        mfrExpiryDatePicker.setPromptText(Messages.getString("datePlaceholder"));
        mfrExpiryDatePicker.setConverter(dateConverter);
        mfrExpiryDatePicker.setDayCellFactory(cellFactory);
    }

    private void initBottomExpiryDatesPane() {
        bottomExpiryDatesPane = new GridPane();
        initBottomExpiryDatesPaneConstraints();
        bottomExpiryDatesPane.add(validUntilLabel, 0, 0);
        bottomExpiryDatesPane.add(validUntilDatePicker, 1, 0);
        bottomExpiryDatesPane.add(mfrExpiryDateLabel, 3, 0);
        bottomExpiryDatesPane.add(mfrExpiryDatePicker, 4, 0);
    }

    private void initBottomExpiryDatesPaneConstraints() {
        ColumnConstraints validUntilLabelConstraints = new ColumnConstraints(60, 60, Double.MAX_VALUE);

        ColumnConstraints validUntilDatePickerConstraints = new ColumnConstraints(110);

        ColumnConstraints smallSplitterConstraints = new ColumnConstraints(0, 10, Double.MAX_VALUE);

        ColumnConstraints mfrExpiryDateFieldConstraints = new ColumnConstraints(180);

        ColumnConstraints mfrExpiryDatePickerConstraints = new ColumnConstraints(110);

        ColumnConstraints splitterConstraints = new ColumnConstraints(0, 0, Double.MAX_VALUE);
        splitterConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints scalesLabelConstraints = new ColumnConstraints(50);

        ColumnConstraints scalesDescriptionLabelConstraints = new ColumnConstraints(300);
        bottomExpiryDatesPane.getColumnConstraints().addAll(validUntilLabelConstraints,
                validUntilDatePickerConstraints,
                smallSplitterConstraints,
                mfrExpiryDateFieldConstraints,
                mfrExpiryDatePickerConstraints,
                splitterConstraints,
                scalesLabelConstraints,
                scalesDescriptionLabelConstraints);
    }

    private void initValidUntilDatePicker() {
        validUntilDatePicker = new DatePicker();
        validUntilDatePicker.setEditable(false);
        validUntilDatePicker.setPromptText(Messages.getString("datePlaceholder"));
        validUntilDatePicker.setConverter(dateConverter);
        validUntilDatePicker.setDayCellFactory(cellFactory);
    }

    private void initValidUntilLabel() {
        validUntilLabel = new Label(Messages.getString("validUntil"));
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
                shopLabelDescriptionConstraints,
                splitterConstraints,
                scalesLabelConstraints,
                scalesDescriptionLabelConstraints);
    }

    private void initScalesDescriptionLabel() {
        scalesDescriptionLabel = new Label();
        scalesDescriptionLabel.setText(settings.getProperty("aclas-001.name"));
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
        initBottomGridPaneConstraints();
        bottomGridPane.add(shortArticleDescription, 0, 0);
        bottomGridPane.add(fullArticleDescription, 0, 1);
        bottomGridPane.add(bottomExpiryDatesPane, 0, 2);
        bottomGridPane.add(shopAndScalesBottomPane, 0, 3);
    }

    private void initBottomGridPaneConstraints() {
        ColumnConstraints shortArticleDescriptionConstraints = new ColumnConstraints();
        shortArticleDescriptionConstraints.setHgrow(Priority.ALWAYS);
        RowConstraints shortArticleDescriptionRowConstraints = new RowConstraints();

        ColumnConstraints fullArticleDescriptionConstraints = new ColumnConstraints();
        RowConstraints fullArticleDescriptionRowConstraints = new RowConstraints();
        fullArticleDescriptionRowConstraints.setVgrow(Priority.ALWAYS);

        bottomGridPane.getColumnConstraints()
                .addAll(shortArticleDescriptionConstraints, fullArticleDescriptionConstraints);
        bottomGridPane.getRowConstraints().add(0, shortArticleDescriptionRowConstraints);
        bottomGridPane.getRowConstraints().add(1, fullArticleDescriptionRowConstraints);

    }

    private void initSplitter() {
        splitter = new Label();
    }

    private void initFullArticleDescriptionArea() {
        fullArticleDescription = new TextArea();
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
                quickSearchFieldConstraints,
                splitterConstraints,
                articlesCountInfoLabelConstraints,
                articlesCountLabelConstraints);
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
        quickSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData();
        });
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
        bottomSearchAndInfoPane.add(splitter, 2, 0);
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
        bottomGridPane.setVgap(5);
    }

    private void initTopButtonBox() {
        topButtonBox = new HBox();
        topButtonBox.setSpacing(5);
        topButtonBox.setPadding(new Insets(5, 5, 5, 5));
        topButtonBox.getChildren().addAll(refresh, upload, exit);
    }

    private void initTopBorderPane() {
        topBorderPane = new BorderPane();
        topBorderPane.setTop(topGridPane);
        topBorderPane.setLeft(new Label());
        topBorderPane.setCenter(articlesTable);
    }

    private void initPrimaryStage() {
        window.getIcons().add(new Image(getClass().getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
        window.setTitle(Messages.getString("title"));
    }

    private void initArticlesTable() {
        articlesTable = new TableView<>();
        articlesTable.setPlaceholder(new Label(Messages.getString("noContentInTable")));
        initArticlesTableColumns();


        data = FXCollections.observableArrayList(new Article(
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
                1,
                "Бутылка водки",
                "Гадость кислющая, но зачем-то же её пьют! Вот-таки странная то вещь творится на земле Русской!",
                1200,
                false,
                123,
                123,
                "01.01.2017",
                6,
                5));

        data.add(new Article(
                1,
                "Жепь \"Ебрило\"",
                "Щячло попячьться адинадин ОЛООлолОЛо Онотоле Негодуе!!!1!!один!!11",
                0.45,
                true,
                1223,
                13,
                "01.01.2016",
                1,
                2));
        data.addListener((ListChangeListener<Article>) change -> updateFilteredData());

        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(data);

        articlesTable.setItems(filteredData);
        articlesTable.setVisible(true);
        articlesTable.setEditable(false);
        articlesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, article, item) -> {
            LOGGER.info(item.getName());
            updateInformation(item);
        });
    }

    private void initArticlesTableColumns() {
        TableColumn<Article, Integer> column_id = new TableColumn<>(Messages.getString("column_ID"));
        TableColumn<Article, String> column_materialNumber = new TableColumn<>(Messages
                .getString("column_materialNumber"));
        TableColumn<Article, String> column_name = new TableColumn<>(Messages.getString("column_name"));
        TableColumn<Article, String> column_description = new TableColumn<>(Messages
                .getString("column_materialDescription"));
        TableColumn<Article, Boolean> column_weighed = new TableColumn<>(Messages.getString("column_weighed"));
        TableColumn<Article, Double> column_price = new TableColumn<>(Messages.getString("column_price"));
        TableColumn<Article, Integer> column_plu = new TableColumn<>(Messages.getString("column_plu"));
        TableColumn<Article, Double> column_labelId = new TableColumn<>(Messages.getString("column_labelId"));

        column_weighed.setCellValueFactory((TableColumn.CellDataFeatures<Article, Boolean> param) -> param.getValue()
                .weighedProperty());
        column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_materialNumber.setCellValueFactory(new PropertyValueFactory<>("materialNumber"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        column_weighed.setCellValueFactory(new PropertyValueFactory<>("weighed"));
        column_weighed.setCellFactory(CheckBoxTableCell.forTableColumn(column_weighed));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_plu.setCellValueFactory(new PropertyValueFactory<>("plu"));
        column_labelId.setCellValueFactory(new PropertyValueFactory<>("labelId"));

        articlesTable.getColumns().addAll(
                column_id,
                column_materialNumber,
                column_name,
                column_description,
                column_weighed,
                column_price,
                column_plu,
                column_labelId);

        // collect map of current sortedColumns to correct their order in settings
        orderColumns();
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Article article : data) {
            if (matchesFilter(article)) {
                filteredData.add(article);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the article matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @param article table row to filter
     */
    private boolean matchesFilter(Article article) {
        String filterString = quickSearchField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        String filterLower = filterString.toLowerCase();
        if (article.getDescription().toLowerCase().contains(filterLower)) {
            return true;
        } else if (article.getName().toLowerCase().contains(filterLower)) {
            return true;
        }
        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Article, ?>> sortOrder = new ArrayList<>(articlesTable.getSortOrder());
        articlesTable.getSortOrder().clear();
        articlesTable.getSortOrder().addAll(sortOrder);
    }

    /**
     * Changes layout for weighted article back and forth
     *
     * @param article article to change layout for
     */
    private void updateInformation(Article article) {
        adjustVisibilityAndSize(article);
        fullArticleDescription.setText(article.getDescription());
        shortArticleDescription.setText(article.getName());
        validUntilDatePicker
                .setValue(LocalDate.parse(article.getExpiryDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private void adjustVisibilityAndSize(Article article) {
        ColumnConstraints c;
        if (article.getWeighed()) {
            validUntilLabel.setText(Messages.getString("unpackingLabel"));
            c = new ColumnConstraints(90);
            bottomExpiryDatesPane.getColumnConstraints().remove(0);
            bottomExpiryDatesPane.getColumnConstraints().add(0, c);
            mfrExpiryDateLabel.setVisible(true);
            mfrExpiryDatePicker.setVisible(true);
        } else {
            validUntilLabel.setText(Messages.getString("validUntil"));
            c = new ColumnConstraints(60);
            bottomExpiryDatesPane.getColumnConstraints().remove(0);
            bottomExpiryDatesPane.getColumnConstraints().add(0, c);
            mfrExpiryDateLabel.setVisible(false);
            mfrExpiryDatePicker.setVisible(false);
        }
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
        refresh.setOnAction(e -> {
            LOGGER.info("Refresh happened!");
            SimpleService service = new SimpleService();
            ProgressView progressView = new ProgressView();
            service.setOnSucceeded(event -> progressView.getDialogStage().close());
            progressView.activateProgressBar(service);
            service.start();
        });
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

    public void orderColumns() {
        for (int i = 0; i < articlesTable.getColumns().size(); i++) {
            TableColumn<Article, ?> column = articlesTable.getColumns().get(i);
            sortedColumns.put(i, column);
        }
    }
}
