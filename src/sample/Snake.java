package sample;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake {
  private ArrayList<Circle> snake;
  private int radius;
  private int distance;
  private enum Dir {Left, Right, Up, Down};
  private Dir direction;

  public Snake(int snakeSize, Color headColor, Color bodyColor) {
    radius = 10;
    distance = 2 * radius;
    snake = new ArrayList<Circle>();
    Circle head = new Circle(radius);
    head.setFill(headColor);
    head.setCenterX((snakeSize - 1) * distance + radius);
    head.setCenterY(Main.getHeight() - 70);
    snake.add(head);
    for (int i = 1; i < snakeSize; i++) {
      snake.add(new Circle(head.getCenterX() - distance * i, head.getCenterY(), radius, bodyColor));
    }
    direction = Dir.Right;
    for(int i = 0; i < snake.size(); i++){
      Controller.getGamePane().getChildren().add(snake.get(i));
    }
  }

  public ArrayList<Circle> getSnake(Snake s) {
    return s.snake;
  }

  public void grow() {
    Circle bodyNode = new Circle(radius);
    bodyNode.setFill(Main.getBody());
    bodyNode.setCenterX(snake.get(snake.size() - 1).getCenterX());
    bodyNode.setCenterY(snake.get(snake.size() - 1).getCenterY());
    snake.add(bodyNode);
    Controller.getGamePane().getChildren().add(bodyNode);
  }

  public boolean checkForCollision() {
    if ((snake.get(0).getCenterX() <= 0)
        ||(snake.get(0).getCenterX() >= Main.getWidth() - radius)
        ||(snake.get(0).getCenterY() <= 0.25)
        ||(snake.get(0).getCenterY() >= Main.getHeight() - (distance * 3))
        || selfDestroy())
      return true;
      //Controller.setIsGameOver(true);
    return false;
  }

  public boolean selfDestroy(){
    for(int i = 1; i < snake.size(); i++){
      if(snake.get(i).getCenterX() == snake.get(0).getCenterX()
          && snake.get(i).getCenterY() == snake.get(0).getCenterY())
        return true;
    }
    return false;
  }

  public void setDirection(int option){
    switch (option){
      case 1:
        direction = Dir.Left;
        break;
      case 2:
        direction = Dir.Down;
        break;
      case 3:
        direction = Dir.Right;
        break;
      case 4:
        direction = Dir.Up;
        break;
    }
  }

  public void moveSnake(){
    for (int i = snake.size() - 1; i >= 1; i--) {
      snake.get(i).setCenterX(snake.get(i - 1).getCenterX());
      snake.get(i).setCenterY(snake.get(i - 1).getCenterY());
    }
    switch (direction){
      case Up:
        snake.get(0).setCenterY(snake.get(0).getCenterY() - distance);
        break;
      case Down:
        snake.get(0).setCenterY(snake.get(0).getCenterY() + distance);
        break;
      case Left:
        snake.get(0).setCenterX(snake.get(0).getCenterX() - distance);
        break;
      case Right:
        snake.get(0).setCenterX(snake.get(0).getCenterX() + distance);
        break;
    }
  }

}
