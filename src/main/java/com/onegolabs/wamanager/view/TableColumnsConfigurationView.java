package com.onegolabs.wamanager.view;

import com.onegolabs.Messages;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author dmzhg
 */
public class TableColumnsConfigurationView {

	public TableColumnsConfigurationView() {
		Stage stage = new Stage();
		stage.setTitle(Messages.getString("loadingArticlesIntoScales"));
		stage.getIcons()
			 .setAll(new Image(getClass().getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);

		final GridPane gp = new GridPane();
		gp.setPadding(new Insets(2, 5, 2, 5));
		ColumnConstraints progressBarConstraints = new ColumnConstraints();
		progressBarConstraints.setHalignment(HPos.CENTER);
		ColumnConstraints statusLabelConstraints = new ColumnConstraints();
		statusLabelConstraints.setHalignment(HPos.CENTER);
		gp.getColumnConstraints().addAll(progressBarConstraints, statusLabelConstraints);

		Scene scene = new Scene(gp, 450, 450);
		stage.setScene(scene);
		stage.showAndWait();
	}
}
