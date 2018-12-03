package com.divinecode.specialutility.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class AbstractController {
    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;
    private AnchorPane pane;
    private GuiManager guiManager;

    protected double xOffset;
    protected double yOffset;

    public abstract void init();

    public void setDraggable() {
        scene.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

}
