package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class data {
  private static String playerName = "";
  private static int currentScore;
  private static int saveHighScore;
  private static ArrayList<String> list;
  private static String highScore;


  public data(){
    list = new ArrayList<>();
    playerName = "";
    saveHighScore = 0;
    currentScore = Controller.getCurrentScore();
    highScore = "";
  }

  //read from file
  public void load(){

    try {
      File scoreFile = new File("C:/Users/ahmed/IdeaProjects/Snake/highScores.dat");
      if(scoreFile.exists()){
        FileReader fileReader = new FileReader(scoreFile);
        Scanner scan  = new Scanner (fileReader);
        playerName = "";
        while(scan.hasNext()){
          playerName = scan.next();
          currentScore = scan.nextInt();
        }
        highScore = playerName + ":" + currentScore;
        Controller.setHighScoreFound(true);
        System.out.println(playerName + ":" + currentScore);
      }
      else
        Controller.setHighScoreFound(false);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //write to file
  public void save(String name, int score){
    try{
      File scoreFile = new File("C:/Users/ahmed/IdeaProjects/Snake/highScores.dat");
      FileWriter fileWriter = new FileWriter("C:/Users/ahmed/IdeaProjects/Snake/highScores.dat");
      BufferedWriter writer = new BufferedWriter(fileWriter);
      if(scoreFile.exists())
      {
        System.out.println("file exists");
        playerName = name;
        writer.write(name);
        writer.newLine();
        writer.write(Integer.toString(score));
        writer.flush();
        writer.close();
        System.out.println("data written");
      }
      else {
        scoreFile.createNewFile();
        playerName = name;
        writer.write(name);
        writer.newLine();
        writer.write(Integer.toString(score));
        writer.flush();
        writer.close();
        System.out.println("file created and data written");
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("shit happens!!!");
    }
  }


  public boolean checkEligibility(int score){
    if(score > currentScore){
      highScore = playerName + ":" + score;
      return true;
    }

    return false;
  }

  public String getHighScore() {
    return highScore;
  }

  public void setHighScore(String highScore) {
    data.highScore = highScore;
  }
}
