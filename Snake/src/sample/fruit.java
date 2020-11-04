package sample;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class fruit {

  private static final ArrayList<Circle> food = new ArrayList<Circle>();
  private static int radius;
  private static int distance;
  private static Random random;
  private static int foodCount;
  private static int foodOnLand;
  private int foodEaten;

  public fruit() {
    radius = 10;
    distance = 2 * radius;
    random = new Random();
    foodCount = Main.getFruits();
    foodOnLand = 0;
    foodEaten = 0;
  }
  public static void createFruit() {
    Circle apple = new Circle(radius);
    apple.setCenterX(radius + distance * random.nextInt(20));
    apple.setCenterY(radius + distance * random.nextInt(20));
    apple.setFill(sample.fruit.randomColor(random.nextInt(9)));
    food.add(apple);
    Controller.getGamePane().getChildren().add(apple);
  }

  public static boolean detectFruit(){
    for (int i = 0; i < Controller.getMySnake().size(); i++) {
      for(int j = 0; j < food.size(); j++){
        if (Controller.getMySnake().get(i).getCenterX() == food.get(j).getCenterX() &&
            Controller.getMySnake().get(i).getCenterY() == food.get(j).getCenterY()) {
          relocateFood(food.get(j));
          return true;
        }
      }
    }
    return false;
  }

  public static void relocateFood(Circle newFood) {
    newFood.setCenterX(radius + distance * random.nextInt(20));
    newFood.setCenterY(radius + distance * random.nextInt(20));
    newFood.setFill(randomColor(random.nextInt(9)));

    for (Circle circle : Controller.getMySnake()) {
      if (newFood.getCenterX() == circle.getCenterX() &&
          newFood.getCenterY() == circle.getCenterY())
        relocateFood(newFood);
    }
  }

  public static Color randomColor(int num) {
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
