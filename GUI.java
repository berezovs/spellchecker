import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
    private final int WINDOW_HEIGHT = 600, WINDOW_WIDTH = 500;
    private String WINDOW_NAME = "Spell Checker";
    private TextArea textarea = null;


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        root.setPadding(new Insets(5, 5, 5, 5));
        root.setTop(this.createMenu(stage));
        root.setCenter(this.createTextArea());

        Scene scene = new Scene(root, WINDOW_HEIGHT, WINDOW_WIDTH);

        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public MenuBar createMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");

        open.setOnAction(new OpenFileEventHandler(stage));
        save.setOnAction(new FileSaverEventHandler(stage));
        exit.setOnAction(new ExitApplicationEventHandler());

        MenuItem spellCheck = new MenuItem("Check Spelling");
        spellCheck.setOnAction(new SpellCheckEventHandler());

        fileMenu.getItems().addAll(open, save, exit);
        editMenu.getItems().addAll(spellCheck);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        return menuBar;
    }

    private TextArea createTextArea() {
        this.textarea = new TextArea();
        this.textarea.setWrapText(true);
        return this.textarea;
    }


    public static void launchApp() {
        Application.launch();
    }

    private class OpenFileEventHandler implements EventHandler<ActionEvent> {
        Stage stage = null;

        public OpenFileEventHandler(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser openFileDialog = new FileChooser();
            File file = openFileDialog.showOpenDialog(stage);
            if (file != null) {
                loadFileIntoTextArea(file);
            }
        }

        private void loadFileIntoTextArea(File file) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    textarea.appendText(scanner.nextLine());
                }
                scanner.close();
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    private class FileSaverEventHandler implements EventHandler<ActionEvent> {
        Stage stage = null;

        public FileSaverEventHandler(Stage stage) {
            this.stage = stage;
        }

        FileChooser saveFileDialog = new FileChooser();

        @Override
        public void handle(ActionEvent event) {

            File file = saveFileDialog.showSaveDialog(stage);
            if (file != null)
                saveFile(file);

        }

        private void saveFile(File file) {
            String content = textarea.getText();

            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(content);
                writer.close();

            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }

        }
    }

    private class ExitApplicationEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Platform.exit();
        }
    }


    //event handler for the Spell Check menu item
    private class SpellCheckEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            AppController.getInstance().startSpellCheck(textarea.getText());
            String alertMessage = AppController.getInstance().getNextSuggestion();

            Optional<ButtonType> result = getSpellCheckAlertWindow(alertMessage).showAndWait();

            while (result.get() == ButtonType.OK && !alertMessage.equals("")) {
                alertMessage = AppController.getInstance().getNextSuggestion();
                alertMessage = alertMessage.trim();


                if (alertMessage.equals("")) {
                    getSpellCheckAlertWindow("End of spellcheck").showAndWait();
                    break;
                }
                getSpellCheckAlertWindow(alertMessage).showAndWait();
            }
        }

        private Alert getSpellCheckAlertWindow(String message) {
            Alert spellCheckDialog = new Alert(AlertType.CONFIRMATION);
            spellCheckDialog.setTitle("Spellchecker");
            spellCheckDialog.setHeaderText(null);
            spellCheckDialog.setContentText(message);

            return spellCheckDialog;
        }
    }

}