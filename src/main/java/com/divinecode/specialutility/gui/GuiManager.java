package com.divinecode.specialutility.gui;

import com.divinecode.specialutility.Starter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {
    private Map<Class<? extends AbstractController>, Triple<AnchorPane, FXMLLoader, ? extends AbstractController>> scenes = new HashMap<>();

    public <T extends AbstractController> @NotNull Triple<AnchorPane, FXMLLoader, T> addScene(@NotNull final Class<T> controllerClass,
                                                                                                  @NotNull final String path,
                                                                                                  @NotNull final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(Starter.class.getResource(path));
        final AnchorPane pane = loader.load();
        final T controller = loader.getController();
        final Scene scene = new Scene(loader.getRoot());

        controller.setScene(scene);
        controller.setLoader(loader);
        controller.setStage(stage);
        controller.setPane(pane);
        controller.setGuiManager(this);

        final Triple<AnchorPane, FXMLLoader, T> triple = new Triple<>(pane, loader, controller);
        scenes.put(controllerClass, triple);

        controller.init();

        return triple;
    }

    public @Nullable <T extends AbstractController> Triple<AnchorPane, FXMLLoader, T> getScene(Class<T> sceneClass) {
        return (Triple<AnchorPane, FXMLLoader, T>) scenes.get(sceneClass);
    }

    public @NotNull Map<Class<? extends AbstractController>, Triple<AnchorPane, FXMLLoader, ? extends AbstractController>> getScenes() {
        return scenes;
    }

}
