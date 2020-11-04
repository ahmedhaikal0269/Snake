package sample;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class snake {
  private static ArrayList<Circle> Snake;
  private static Circle head;
  private static Circle body;
  private static int radius;
  private static int distance;
  private enum Dir {left, right, up, down};
  private static Dir direction = Dir.right;

  public snake() {
    Snake = new ArrayList<Circle>();
    radius = 10;
    distance = 2 * radius;
    head = new Circle();
    body = new Circle();
  }

  public ArrayList createSnake(int size, Color headColor, Color bodyColor) {
    head.setRadius(radius);
    head.setFill(headColor);
    head.setCenterX((size - 1) * distance + radius);
    head.setCenterY(Main.getHeight() - 70);
    Snake.add(head);
    for (int i = 1; i < size; i++) {
      Snake.add(new Circle(head.getCenterX() - distance * i, head.getCenterY(), radius, bodyColor));
    }
    direction = Dir.right;
    return Snake;
  }

  public void move(){
    for (int i = Snake.size() - 1; i >= 1; i--) {
      Snake.get(i).setCenterX(Snake.get(i - 1).getCenterX());
      Snake.get(i).setCenterY(Snake.get(i - 1).getCenterY());
    }
  }

  public void grow() {
    body = new Circle(radius);
    body.setFill(Main.getBody());
    body.setCenterX(Snake.get(Snake.size() - 1).getCenterX());
    body.setCenterY(Snake.get(Snake.size() - 1).getCenterY());
    Snake.add(body);
    Controller.getGamePane().getChildren().add(body);
  }

  public void checkForCollision() {
    if ((Snake.get(0).getCenterX() <= 0)
        ||(Snake.get(0).getCenterX() >= Main.getWidth() - radius)
        ||(Snake.get(0).getCenterY() <= 0.25)
        ||(Snake.get(0).getCenterY() >= Main.getHeight() - (distance * 3))
        ||selfdestroy())
      Controller.setIsGameOver(true);
  }
  public static boolean selfdestroy(){
    for(int i = 1; i < Snake.size(); i++){
      if(Snake.get(i).getCenterX() == Snake.get(0).getCenterX()
          && Snake.get(i).getCenterY() == Snake.get(0).getCenterY())
        return true;
    }
    return false;
  }

  public void control(){
    Controller.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();

        switch (key){
          case UP:
            if (!(Snake.get(0).getCenterY() > Snake.get(1).getCenterY())) {
              direction = Dir.up;
            }
            break;

          case RIGHT:
            if (!(Snake.get(0).getCenterX() < Snake.get(1).getCenterX())) {
              direction = Dir.right;
            }
            break;

          case DOWN:
            if (!(Snake.get(0).getCenterY() < Snake.get(1).getCenterY())) {
              direction = Dir.down;
            }
            break;

          case LEFT:
            if (!(Snake.get(0).getCenterX() > Snake.get(1).getCenterX())) {
              direction = Dir.left;
            }
            break;

          case SPACE:
            Controller.pauseGame();
            break;
        }
      }
    });
  }

  public void movement(){
    switch (direction){
      case up:
        move();
        Snake.get(0).setCenterY(Snake.get(0).getCenterY() - distance);
        break;
      case down:
        move();
        Snake.get(0).setCenterY(Snake.get(0).getCenterY() + distance);
        break;
      case left:
        move();
        Snake.get(0).setCenterX(Snake.get(0).getCenterX() - distance);
        break;
      case right:
        move();
        Snake.get(0).setCenterX(Snake.get(0).getCenterX() + distance);
        break;
    }
  }

}
