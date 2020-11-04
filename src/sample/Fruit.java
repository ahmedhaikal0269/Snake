package sample;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Fruit {

  private static ArrayList<Circle> food;
  private int radius;
  private int distance;
  private static Random random;
  private int foodCount;

  public Fruit() {
    food = new ArrayList<Circle>();
    radius = 10;
    distance = 2 * radius;
    random = new Random();
    foodCount = Main.getFruits();
  }
  public void createFruit() {
    Circle apple = new Circle(radius);
    apple.setCenterX(radius + distance * random.nextInt(20));
    apple.setCenterY(radius + distance * random.nextInt(20));
    apple.setFill(randomColor(random.nextInt(9)));
    food.add(apple);
    Controller.getGamePane().getChildren().add(apple);
  }

  public boolean detectFruit(){
    for (int i = 0; i < Controller.getPlayer().getSnake(Controller.getPlayer()).size(); i++) {
      for(int j = 0; j < food.size(); j++){
        if (Controller.getPlayer().getSnake(Controller.getPlayer()).get(i).getCenterX()
            == food.get(j).getCenterX() &&
            Controller.getPlayer().getSnake(Controller.getPlayer()).get(i).getCenterY()
            == food.get(j).getCenterY()) {
          relocateFood(food.get(j));
          return true;
        }
      }
    }
    return false;
  }

  public void relocateFood(Circle newFood) {
    newFood.setCenterX(radius + distance * random.nextInt(20));
    newFood.setCenterY(radius + distance * random.nextInt(20));
    newFood.setFill(randomColor(random.nextInt(9)));

    for (Circle circle : Controller.getPlayer().getSnake(Controller.getPlayer())) {
      if (newFood.getCenterX() == circle.getCenterX() &&
          newFood.getCenterY() == circle.getCenterY())
        relocateFood(newFood);
    }
  }

  public Color randomColor(int num) {
    switch (num){
      case 1:
        return Color.GREEN;
      case 2:
        return Color.BLUE;
      case 3:
        return Color.RED;
      case 4:
        return Color.GRAY;
      case 5:
        return Color.GOLD;
      case 6:
        return Color.YELLOW;
      case 7:
        return Color.ORANGE;
      case 8:
        return Color.BROWN;
      case 9:
        return Color.BEIGE;
    }
    return Color.VIOLET;
  }
}
