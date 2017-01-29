package com.onegolabs.wamanager.view;

import com.onegolabs.Messages;
import com.onegolabs.wamanager.model.Article;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Map;

/**
 * @author dmzhg
 */
public class TableColumnsConfigurationView {

    private final ListView<TableColumn<Article, ?>> columnsList = new ListView<>();
    private final VBox upDownVBox = new VBox();
    private final BorderPane saveCancelButtonPane = new BorderPane();
    private final Button save = new Button(Messages.getString("saveButton"));
    private final Button cancel = new Button(Messages.getString("cancelButton"));

    public TableColumnsConfigurationView(Map<Integer, TableColumn<Article, ?>> sortedColumns) {
        Stage stage = new Stage();
        stage.setTitle(Messages.getString("columnSettings"));
        stage.getIcons()
                .setAll(new Image(getClass().getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        final GridPane gp = new GridPane();
        columnsList.setCellFactory(new Callback<ListView<TableColumn<Article, ?>>, ListCell<TableColumn<Article, ?>>>() {

            @Override
            public ListCell<TableColumn<Article, ?>> call(ListView<TableColumn<Article, ?>> param) {
                ListCell<TableColumn<Article, ?>> cell = new ListCell<TableColumn<Article, ?>>() {

                    @Override
                    protected void updateItem(TableColumn<Article, ?> t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getText());
                        }
                    }

                };
                return cell;
            }
        });

        fillList(sortedColumns);
        initUpDownButtonsBox();
        initSaveCancelBox();
        gp.setPadding(new Insets(5, 5, 5, 5));
        ColumnConstraints columnsListConstraints = new ColumnConstraints();
        columnsListConstraints.setHalignment(HPos.CENTER);
        columnsListConstraints.setHgrow(Priority.ALWAYS);
        ColumnConstraints upDownVBoxConstraints = new ColumnConstraints(40);
        upDownVBoxConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints saveButtonConstraints = new ColumnConstraints();
        saveButtonConstraints.setHgrow(Priority.ALWAYS);
        ColumnConstraints cancelButtonConstraints = new ColumnConstraints();
        gp.getColumnConstraints().addAll(columnsListConstraints,
                upDownVBoxConstraints);
        gp.add(columnsList, 0, 0);
        gp.add(upDownVBox, 1, 0);
//        gp.setGridLinesVisible(true);
//        gp.add(saveCancelButtonPane, 0, 1);
        Scene scene = new Scene(gp);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void initUpDownButtonsBox() {
        Button up = new Button("↑");
        Button down = new Button("↓");
        upDownVBox.setSpacing(10);
        upDownVBox.setAlignment(Pos.CENTER);
        upDownVBox.setPadding(new Insets(5,0,5,5));
        upDownVBox.getChildren().addAll(up, down);
    }

    private void initSaveCancelBox() {
        saveCancelButtonPane.setLeft(save);
        saveCancelButtonPane.setRight(cancel);
    }

    private void fillList(Map<Integer, TableColumn<Article, ?>> sortedColumns) {
        sortedColumns.forEach((k, v) -> columnsList.getItems().add(k, v));
    }
}
