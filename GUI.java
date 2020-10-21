import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

    private TextArea textarea = null;
    private File file = null;
    private FileChooser openFileDialog = null;
    Alert spellCheckDialog = null;
    private String WINDOW_NAME = "Spell Checker";

    @Override
    public void start(Stage stage) throws Exception {

        // initializes layout manager
        BorderPane root = new BorderPane();
        //
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setTop(this.createMenu(stage));
        root.setCenter(this.createTextArea());

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public MenuBar createMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // spellcheck menu item
        MenuItem spellCheck = new MenuItem("Check Spelling");

        spellCheck.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = getAlertMessage(textarea.getText());
                spellCheckDialog = new Alert(AlertType.CONFIRMATION);
                spellCheckDialog.setTitle("Spellchecker");
                spellCheckDialog.setHeaderText(null);
                spellCheckDialog.setContentText(message);
                spellCheckDialog.showAndWait();
            }

        });

        editMenu.getItems().addAll(spellCheck);

        // open menu item
        MenuItem open = new MenuItem("Open");
        // event handler for open menu item
        open.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                openFileDialog = new FileChooser();
                file = openFileDialog.showOpenDialog(stage);
                if (file != null) {
                    loadFileIntoTextArea(file);
                }
            }

        });

        // save menu item
        MenuItem save = new MenuItem("Save");
        // event handler for save menu item
        save.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser saveFileDialog = new FileChooser();

            @Override
            public void handle(ActionEvent event) {
                if (file == null) {
                    file = saveFileDialog.showSaveDialog(stage);
                }
                saveFile(file);
            }
        });

        // exit menu item
        MenuItem exit = new MenuItem("Exit");
        // event handler for exit menu item
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        fileMenu.getItems().addAll(open, save, exit);

        return menuBar;
    }

    private TextArea createTextArea() {
        this.textarea = new TextArea();
        this.textarea.setWrapText(true);
        return this.textarea;
    }

    private String getAlertMessage(String text){
        AppController controller = AppController.getInstance();
        List<String> list = controller.runSpellCheck(textarea.getText());
        String message = "Misspelled: ";
        message += list.get(0);
        message+="\n";
        list.remove(0);
        if (list.isEmpty())
            message+="Suggestions could not be found";
        else{
        for (String word : list) {
            message+=word;
            message+="; ";
        }
    }
    return message;
    }

    private void loadFileIntoTextArea(File file) {
        System.out.println("File name: " + file.getName());
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

    private void saveFile(File file) {
        String content = this.textarea.getText();

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public static void launchApp() {
        Application.launch();
    }

}