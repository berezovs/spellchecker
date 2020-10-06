import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

    private TextArea textarea = null;
    private File file = null;
    private FileChooser openFileDialog = null;

    @Override
    public void start(Stage stage) throws Exception {

        // initializes layout manager
        BorderPane root = new BorderPane();
        //
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setTop(this.createMenu(stage));
        root.setCenter(this.createTextArea());

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("SpellChecker");
        stage.setScene(scene);
        stage.show();
    }

    public MenuBar createMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().addAll(fileMenu, editMenu);

        MenuItem open = new MenuItem("Open");
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

        MenuItem save = new MenuItem("Save");
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
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Exit");
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