package com.onegolabs.wamanager;

import com.onegolabs.SimpleService;
import com.onegolabs.wamanager.model.Article;
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
		mfrExpiryDatePicker
				.setOnAction(e -> LOGGER.info("Man. date selected: " + mfrExpiryDatePicker.getEditor().getText()));
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
		ColumnConstraints validUntilLabelConstraints = new ColumnConstraints(60);

		ColumnConstraints validUntilDatePickerConstraints = new ColumnConstraints(110);

		ColumnConstraints smallSplitterConstraints = new ColumnConstraints(10);

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
		validUntilDatePicker.setOnAction(e -> {
			LOGGER.info("Date selected: " + validUntilDatePicker.getEditor().getText());
			validUntilLabel.setText("Дата вскрытия:");
		});
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
		articlesTable.setPlaceholder(new Label(Messages.getString("noContentInTable")));
		TableColumn<Article, Integer> ID = new TableColumn<>(Messages.getString("column_ID"));
		TableColumn<Article, String> materialNumber = new TableColumn<>(Messages.getString("column_materialNumber"));
		TableColumn<Article, String> materialDescription = new TableColumn<>(Messages
				.getString("column_materialDescription"));
		TableColumn<Article, Boolean> weighed = new TableColumn<>(Messages.getString("column_weighed"));
		weighed.setCellValueFactory((TableColumn.CellDataFeatures<Article, Boolean> param) -> param.getValue()
																								   .weighedProperty());
		ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		materialNumber.setCellValueFactory(new PropertyValueFactory<>("materialNumber"));
		materialDescription.setCellValueFactory(new PropertyValueFactory<>("materialDescription"));
		weighed.setCellValueFactory(new PropertyValueFactory<>("weighed"));
		weighed.setCellFactory(CheckBoxTableCell.forTableColumn(weighed));
		data = FXCollections.observableArrayList(new Article(1, "559165", "ArticleDesc", true));
		data.addListener((ListChangeListener<Article>) change -> updateFilteredData());

		filteredData = FXCollections.observableArrayList();
		filteredData.addAll(data);

		articlesTable.getColumns().addAll(ID, materialNumber, materialDescription, weighed);
		articlesTable.setItems(filteredData);
		articlesTable.setVisible(true);
		articlesTable.setEditable(false);
		articlesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, article, t1) -> {
			LOGGER.info(t1.toString());
			updateInformation(t1);
		});
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
	 * @param article
	 * @return
	 */
	private boolean matchesFilter(Article article) {
		String filterString = quickSearchField.getText();
		if (filterString == null || filterString.isEmpty()) {
			// No filter --> Add all.
			return true;
		}
		String filterLower = filterString.toLowerCase();
		if (article.getMaterialDescription().toLowerCase().contains(filterLower)) {
			return true;
		}
		return false; // Does not match
	}

	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<Article, ?>> sortOrder = new ArrayList<>(articlesTable.getSortOrder());
		articlesTable.getSortOrder().clear();
		articlesTable.getSortOrder().addAll(sortOrder);
	}

	private void updateInformation(Article article) {
		fullArticleDescription.setText(article.toString());
		shortArticleDescription.setText(article.getMaterialDescription());
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
}
