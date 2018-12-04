package com.divinecode.specialutility.gui;

import com.divinecode.specialutility.SpecialUtility;
import com.divinecode.specialutility.options.OptionsValidator;
import com.divinecode.specialutility.options.SpecialUtilityOptions;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.regex.Pattern;

public class MainController extends AbstractController {
    @FXML private Button close;
    @FXML private Button info;

    @FXML private TextField inputJar;
    @FXML private TextField outputJar;
    @FXML private TextField specified;

    @FXML private Button selectInputJar;
    @FXML private Button selectOutputJar;

    @FXML private CheckBox removeFinals;

    @FXML private Button transform;

    @Override
    public void init() {
        setDraggable();
    }

    @FXML
    public void onClose(MouseEvent event) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setNode(getPane());
        transition.setOnFinished(handler -> {
            getStage().close();
            System.exit(0);
        });
        transition.play();
    }

    @FXML
    public void onTransform(MouseEvent event) {
        SpecialUtilityOptions options = new SpecialUtilityOptions();
        options.input = inputJar.getText();
        options.output = outputJar.getText();
        options.specified = specified.getText();
        options.noGui = true;
        options.removeFinals = removeFinals.isSelected();

        OptionsValidator validator = new OptionsValidator();
        validator.validate(options);

        Set<String[]> spec = SpecialUtility.parsePackages(options.specified);

        new Thread(() -> {
            try {
                transform.setDisable(true);
                new SpecialUtility(options.input, options.output, spec, options.removeFinals);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                transform.setDisable(false);
            }
        }).start();
    }

    @FXML
    public void onInfo(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/DivinaCode/SpecialUtility"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onInputJarSelect(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Input Jar");

        final File file = fileChooser.showOpenDialog(null);
        if (file == null) return;

        String path = file.getAbsolutePath();
        inputJar.setText(path);


        if (outputJar.getText() == null || outputJar.getText().isEmpty()) {
            String[] split = inputJar.getText().split(Pattern.quote(File.separator));
            String jarFileName = split[split.length - 1];

            jarFileName = jarFileName.substring(0, jarFileName.length() - 4) + " - Output.jar";

            split[split.length-1] = jarFileName;
            outputJar.setText(String.join(File.separator, split));
        }

    }

    @FXML
    public void onOutputJarSelect(MouseEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Select Output Directory");

        final File file = fileChooser.showDialog(null);
        if (file == null) return;

        String path = file.getAbsolutePath();
        outputJar.setText(path);
    }


}
