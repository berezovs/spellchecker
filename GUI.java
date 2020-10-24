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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class GUI extends Application {
    private TextArea textarea = null;
    private static SpellCheckManager managerComponent;


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        root.setPadding(new Insets(5, 5, 5, 5));
        root.setTop(this.createMenu(stage));
        root.setCenter(this.createTextArea());

        int WINDOW_WIDTH = 500;
        int WINDOW_HEIGHT = 600;
        Scene scene = new Scene(root, WINDOW_HEIGHT, WINDOW_WIDTH);

        String WINDOW_NAME = "Spell Checker";
        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.show();
    }


    //constructs and returns a menubar
    private MenuBar createMenu(Stage stage) {
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

    //creates an instance of a textarea
    private TextArea createTextArea() {
        this.textarea = new TextArea();
        this.textarea.setWrapText(true);
        return this.textarea;
    }


    //entry point for the class
    public static void launchApp(SpellCheckManager component) {
        managerComponent = component;
        Application.launch();
    }


    //eventhandler class for the Open menu item
    private class OpenFileEventHandler implements EventHandler<ActionEvent> {
        private Stage stage = null;

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


    //event handler class for the Save File menu item
    private class FileSaverEventHandler implements EventHandler<ActionEvent> {
        private Stage stage = null;
        private final FileChooser saveFileDialog = new FileChooser();

        public FileSaverEventHandler(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(ActionEvent actionEvent) {

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


    //Event handler class for the Exit menu item
    private static class ExitApplicationEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Platform.exit();
        }
    }


    //Event handler for the Spell Check menu item
    private class SpellCheckEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            managerComponent.startSpellCheck(textarea.getText());
            String alertMessage = managerComponent.getNextSuggestion();

            Optional<ButtonType> result = getSpellCheckAlertWindow(alertMessage).showAndWait();

            while (result.get() == ButtonType.OK && !alertMessage.equals("")) {
                alertMessage = managerComponent.getNextSuggestion();
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