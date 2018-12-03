package com.divinecode.specialutility.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationGui extends Application {
    private final GuiManager guiManager = new GuiManager();
    private Stage stage;

    @Override
    public void start(Stage rootStage) throws Exception {
        this.stage = rootStage;

        final Triple<AnchorPane, FXMLLoader, MainController> main =
                guiManager.addScene(MainController.class, "/assets/gui/MainGui.fxml", rootStage);

        rootStage.setTitle("SpecialUtility");
        rootStage.initStyle(StageStyle.TRANSPARENT);
        rootStage.setResizable(false);
        rootStage.setScene(main.getThird().getScene());
        rootStage.getScene().setFill(Color.TRANSPARENT);
        rootStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
