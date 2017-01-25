package com.onegolabs.wamanager;

import javafx.concurrent.Service;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressForm {

	private final Stage dialogStage;
	private final ProgressBar progressBar = new ProgressBar();
	private final Label statusLabel = new Label();

	public ProgressForm() {
		dialogStage = new Stage();
		dialogStage.setTitle(Messages.getString("loadingArticlesIntoScales"));
		dialogStage.getIcons()
				   .setAll(new Image(getClass().getResourceAsStream("/icons/app_icon.png"), 300, 300, false, false));
		dialogStage.setResizable(false);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.setOnCloseRequest(Event::consume);

		progressBar.prefWidthProperty().bind(dialogStage.widthProperty());
		progressBar.setProgress(-1F);

		final GridPane gp = new GridPane();
		gp.setPadding(new Insets(2, 5, 2, 5));
		ColumnConstraints progressBarConstraints = new ColumnConstraints();
		progressBarConstraints.setHalignment(HPos.CENTER);
		ColumnConstraints statusLabelConstraints = new ColumnConstraints();
		statusLabelConstraints.setHalignment(HPos.CENTER);
		gp.getColumnConstraints().addAll(progressBarConstraints, statusLabelConstraints);
		gp.add(progressBar, 0, 0);
		gp.add(statusLabel, 0, 1);

		Scene scene = new Scene(gp, 450, 30);
		dialogStage.setScene(scene);
	}

	public void activateProgressBar(final Service<?> service) {
		progressBar.progressProperty().bind(service.progressProperty());
		statusLabel.textProperty().bind(service.messageProperty());
		dialogStage.show();
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}
}