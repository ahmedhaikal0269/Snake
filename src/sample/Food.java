package sample;

import java.util.ArrayList;

public class Food{
  private static ArrayList<Fruit> foodList;


  public Food(int number){
    for(int i = 0; i < number; i++)
      foodList.add(new Fruit());
  }
}
