package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Confirm_Box {
  private boolean answer;

  public boolean display(String message) {
    final Stage closeWindow = new Stage();
    closeWindow.initModality(Modality.APPLICATION_MODAL);
    closeWindow.setTitle("Exit Game");
    closeWindow.setMinWidth(300);

    Label msg = new Label("Are you sure you want to close?");

    Button yesButton = new Button("Yes");
    Button noButton = new Button("No");

    yesButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        answer = true;
        closeWindow.close();
      }
    });

    noButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        answer = false;
        closeWindow.close();
        if(Controller.isIsGamePaused()){
          Controller.startGame();
          Controller.setIsGamePaused(false);
        }
      }
    });

    HBox buttons = new HBox();
    buttons.setSpacing(20);
    buttons.setAlignment(Pos.CENTER);
    buttons.getChildren().addAll(yesButton, noButton);

    VBox layout = new VBox();
    layout.setSpacing(15);
    layout.setAlignment(Pos.CENTER);
    layout.setMinHeight(100);
    layout.getChildren().addAll(msg, buttons);

    Scene scene = new Scene(layout);
    closeWindow.setScene(scene);
    closeWindow.showAndWait();

    return answer;
  }
}
