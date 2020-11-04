package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Controller {

  private static Pane gamePane;
  private static Pane scorePane;
  private static VBox mainPane;
  private static final Scene scene = Main.start.getScene();
  private static Stage gameStage;
  private static boolean isGameOver;
  private static boolean isGamePaused;
  private static boolean highScoreFound = false;
  private static AnimationTimer snakeTimer;
  private static AnimationTimer paneTimer;
  private static final int UPDATE_SCREEN = 30;
  private static int speed = 5;
  private static Label currentScoreLabel;

  private static final int foodCount = Main.getFruits();
  private static int foodOnLand = 0;
  private static int foodEaten = 0;
  private static int currentScore = 0;
  private static String playerName = "";

  private static Fruit myApple;
  private static Data myData;
  private static Snake snake1;

  //==================================================================================================================

  public static void handleStartButton(int height, int width){
    scorePane = new Pane();
    scorePane.setStyle( "-fx-background-color: BISQUE");
    scorePane.setMinHeight(20);
    currentScoreLabel = new Label("score: " + currentScore);
    currentScoreLabel.setFont(new Font("", 15));
    currentScoreLabel.setStyle("-fx-font-weight: bold");
    currentScoreLabel.setTextFill(Color.BLACK);
    scorePane.getChildren().add(currentScoreLabel);

    gamePane = new Pane();
    gamePane.setStyle( "-fx-background-color: BLACK");
    gamePane.setMinHeight(Main.getHeight() - 50);

    mainPane = new VBox();
    mainPane.setSpacing(0);
    mainPane.setAlignment(Pos.TOP_CENTER);
    mainPane.getChildren().addAll(scorePane, gamePane);

    scene.setRoot(mainPane);
    Window window = scene.getWindow();
    gameStage = (Stage) window;
    gameStage.setTitle("Snake Game");
    gameStage.setWidth(width);
    gameStage.setHeight(height);
    gameStage.setScene(scene);
    gameStage.show();
    gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent windowEvent) {
        isGamePaused = true;
        snakeTimer.stop();
        paneTimer.stop();
        windowEvent.consume();
        closeGame();
      }
    });

    snake1 = new Snake(Main.getSize(),Main.getHead(), Main.getBody());

    myApple = new Fruit();
    while(foodOnLand < foodCount) {
      myApple.createFruit();
      foodOnLand++;
    }
    isGameOver = false;
    isGamePaused = false;

    myData = new Data();
    myData.load();

    startGame();
  }

  public static void startGame(){
    snakeTimer = new AnimationTimer() {
      long lastTick = 0;
      @Override
      public void handle(long now) {
        if(now - lastTick > 1000000000 / speed){
          snakeTick();
          lastTick = now;
        }
      }
    };
    snakeTimer.start();

    paneTimer = new AnimationTimer() {
      long lastTick = 0;
      @Override
      public void handle(long now) {
        if(now - lastTick > 1000000000 / UPDATE_SCREEN){
          screenTick();
          lastTick = now;
        }
      }
    };
    paneTimer.start();
  }

  public static void pauseGame(){
    if(!isIsGamePaused()){
      isGamePaused = true;
      snakeTimer.stop();
      paneTimer.stop();
    }
    else{
      isGamePaused = false;
      snakeTimer.start();
      paneTimer.start();
    }
  }

  public static void closeGame(){
    Confirm_Box options = new Confirm_Box();
    boolean answer = options.display("Exit Game?");
    if(answer)
      gameStage.close();
    else
      endGameScreen();
  }

  public static void snakeTick(){
    if(!isGameOver) {
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
          KeyCode key = keyEvent.getCode();

          switch (key){
            case UP:
              if (!(snake1.getSnake(snake1).get(0).getCenterY() > snake1.getSnake(snake1).get(1).getCenterY())) {
                snake1.setDirection(4);
              }
              break;

            case RIGHT:
              if (!(snake1.getSnake(snake1).get(0).getCenterX() < snake1.getSnake(snake1).get(1).getCenterX())) {
                snake1.setDirection(3);
              }
              break;

            case DOWN:
              if (!(snake1.getSnake(snake1).get(0).getCenterY() < snake1.getSnake(snake1).get(1).getCenterY())) {
                snake1.setDirection(2);
              }
              break;

            case LEFT:
              if (!(snake1.getSnake(snake1).get(0).getCenterX() > snake1.getSnake(snake1).get(1).getCenterX())) {
                snake1.setDirection(1);
              }
              break;

            case SPACE:
              Controller.pauseGame();
              break;
          }
        }
      });
      snake1.moveSnake();
      boolean collision = snake1.checkForCollision();
      if(collision)
        isGameOver = true;
    }
  }

  public static void screenTick(){
    if(isGameOver){
      paneTimer.stop();
      snakeTimer.stop();
      foodOnLand = 0;
      if(myData.checkEligibility(currentScore)){
        playerName = highScoreScreen();
      }
      else
        endGameScreen();
    }
    else{
      boolean fruitFound = myApple.detectFruit();
      if(fruitFound){
        snake1.grow();
        foodEaten++;
        if(foodEaten % 5 == 0)
          speed++;
        updateScore();
      }
    }
  }

  private static void updateScore(){
    currentScore = foodEaten * 7;
    scorePane.getChildren().remove(currentScoreLabel);
    currentScoreLabel.setText("score: " + currentScore);
    scorePane.getChildren().add(currentScoreLabel);
  }

  public static String highScoreScreen(){

    VBox highScoreBox = new VBox();
    highScoreBox.setAlignment(Pos.CENTER);
    highScoreBox.setSpacing(10);

    Text high_score = new Text("A New High Score!");
    high_score.setStyle("-fx-font-weight: bold");
    high_score.setFont(new Font("", 25));
    high_score.setFill(Color.RED);

    final TextField getPlayerName = new TextField();
    getPlayerName.setPromptText("Enter Your Name");
    Button enter = new Button("Enter");
    enter.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        playerName = getPlayerName.getText();
        myData.setHighScore(playerName + ":" + currentScore);
        myData.save(playerName, currentScore);
        endGameScreen();
      }
    });

    HBox getName = new HBox();
    getName.setSpacing(20);
    getName.setAlignment(Pos.CENTER);
    getName.getChildren().addAll(getPlayerName, enter);

    highScoreBox.getChildren().addAll(high_score, getName);

    scene.setRoot(highScoreBox);

    return playerName;
  }

  public static void endGameScreen(){
    VBox endPane = new VBox();
    endPane.setAlignment(Pos.CENTER);
    endPane.setSpacing(10);

    Text game_over = new Text("Game Over");
    game_over.setStyle("-fx-font-weight: bold");
    game_over.setFont(new Font("", 25));
    game_over.setFill(Color.RED);
    endPane.getChildren().add(game_over);

    Text highScore = new Text(myData.getHighScore());
    highScore.setFont(new Font("", 20));
    highScore.setStyle("-fx-font-weight: bold");
    highScore.setLayoutX(10);
    highScore.setLayoutY(60);
    highScore.setFill(Color.BLUE);
    endPane.getChildren().add(highScore);

    Label score = new Label("score: " + currentScore);
    score.setFont(new Font("", 20));
    score.setLayoutX(10);
    score.setLayoutY(90);
    score.setTextFill(Color.GREEN);
    endPane.getChildren().add(score);

    Button continue_same = new Button("continue");
    continue_same.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        handleStartButton(Main.getHeight(), Main.getWidth());
        while(foodOnLand < foodCount) {
          myApple.createFruit();
          foodOnLand++;
        }
      }
    });
    continue_same.setPrefSize(100,20);
    endPane.getChildren().add(continue_same);

    Button restart = new Button("restart");
    restart.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        speed = 5;
        foodEaten = 0;
        handleStartButton(Main.getHeight(), Main.getWidth());
        while(foodOnLand < foodCount) {
          myApple.createFruit();
          foodOnLand++;
        }
      }
    });
    restart.setPrefSize(100,20);
    endPane.getChildren().add(restart);

    Button close = new Button("close");
    close.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        closeGame();
      }
    });
    close.setPrefSize(100,20);
    endPane.getChildren().add(close);

    scene.setRoot(endPane);
  }

  //================================= Getters and Setters =====================================

  public static void setIsGameOver(boolean isGameOver) {
    Controller.isGameOver = isGameOver;
  }

  public static boolean isIsGamePaused() {
    return isGamePaused;
  }

  public static void setIsGamePaused(boolean isGamePaused) {
    Controller.isGamePaused = isGamePaused;
  }

  public static Scene getScene() {
    return scene;
  }

  public static Pane getGamePane() {
    return gamePane;
  }

  public static int getCurrentScore() { currentScore = foodEaten * 7; return currentScore; }

  public static void setHighScoreFound(boolean newHighScore) {
    highScoreFound = newHighScore;
  }

  public static Snake getPlayer() { return snake1; }
}