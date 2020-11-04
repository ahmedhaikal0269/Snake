package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private static Label snakeSize = new Label("Snake Size");
    private static ComboBox size = new ComboBox();

    private static Label fruitCount = new Label("No. of Fruits");
    private static ComboBox fruits = new ComboBox();

    private static Label headColor = new Label("Head Color");
    private static ColorPicker head = new ColorPicker();

    private static Label bodyColor = new Label("Body Color");
    private static ColorPicker body = new ColorPicker();

    private static Label fruitColor = new Label("Fruit Color");
    private static ColorPicker colorFruits = new ColorPicker();

    private static Label screenWidth = new Label("Width");
    private static ComboBox width = new ComboBox();

    private static Label screenHeight = new Label("Height");
    private static ComboBox height = new ComboBox();

    static Button start = new Button("Play");

    public void initialize(){
        snakeSize.setFont(new Font(15));
        snakeSize.setPrefSize(81, 25);

        size.setPrefSize(82,25);
        size.setItems(FXCollections.observableArrayList(2, 3, 4, 5, 6));
        size.setValue(4);

        fruitCount.setFont(new Font(15));
        fruitCount.setPrefSize(91, 25);

        fruits.setPrefSize(83,25);
        fruits.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
        fruits.setValue(2);

        headColor.setFont(new Font(15));
        headColor.setPrefSize(80, 25);

        bodyColor.setFont(new Font(15));
        bodyColor.setPrefSize(90, 25);

        screenWidth.setFont(new Font(15));
        screenWidth.setPrefSize(80, 25);

        width.setPrefSize(84,25);
        width.setItems(FXCollections.observableArrayList(400, 500, 600, 700));
        width.setValue(500);

        screenHeight.setFont(new Font(15));
        screenHeight.setPrefSize(89, 25);

        height.setPrefSize(84,25);
        height.setItems(FXCollections.observableArrayList(400, 500, 600, 700));
        height.setValue(500);

        start.setPrefSize(80,40);

    }
    public static int getSize() {
        return Integer.parseInt(size.getValue().toString());
    }

    public static int getFruits() {
        return Integer.parseInt(fruits.getValue().toString());
    }

    public static Color getHead() {
        return head.getValue();
    }

    public static Color getBody() {
        return body.getValue();
    }

    public static Color getColorFruits() {
        return colorFruits.getValue();
    }

    public static int getWidth() {
        return Integer.parseInt(width.getValue().toString());
    }

    public static int getHeight() {
        return Integer.parseInt(height.getValue().toString());
    }
    @Override
    public void start(final Stage primaryStage) throws Exception{

        initialize();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        //first row
        gridPane.add(snakeSize,0,0);
        gridPane.add(size,1,0);
        gridPane.add(fruitCount,2,0);
        gridPane.add(fruits,3,0);

        //second row
        gridPane.add(headColor,0,1);
        gridPane.add(head,1,1);
        gridPane.add(bodyColor,2,1);
        gridPane.add(body,3,1);

        //third row
        gridPane.add(screenWidth,0,2);
        gridPane.add(width,1,2);
        gridPane.add(screenHeight,2,2);
        gridPane.add(height,3,2);

        VBox mainPane = new VBox();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(10);
        mainPane.getChildren().addAll(gridPane, start);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Controller.handleStartButton(getHeight(), getWidth());
            }
        });

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(mainPane, 380, 220));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
