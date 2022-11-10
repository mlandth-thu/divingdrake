package de.mlandth.divingdrake;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer loop;

    @FXML
    public AnchorPane mainPane;
    @FXML
    private Rectangle drakeView;
    @FXML
    private Text scoreView;

    private final double pHeight = 900;
    private final double pWidth = 1800;

    private int gameTime = 0;
    private int score = 0;

    private Drake drake;
    private ObstacleController oc;

    ArrayList<Rectangle> obstacles = new ArrayList<>();

    //sets the time interval between spawning new obstacles
    private int spawningTime = 500;
    private final int minSpawningTime = 400;
    private final int orgSpawningTime = 500;
    private final int spawnFactor = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init pane
        Color backgroundColor = Color.rgb(50,200,200, 0.8);
        mainPane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        //Create Drake
        double moveDelta = 75;
        drake = new Drake(drakeView,moveDelta, moveDelta);
        //Create Drake view
        Image drakeImg = new Image(getClass().getResource("images/drake.png").toExternalForm());
        drakeView.setFill(new ImagePattern(drakeImg));

        //Add ObstacleController
        oc = new ObstacleController(mainPane, pHeight, pWidth, 0.9);
        //Create GameLoop
        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        //First time adding obstacles
        obstacles.addAll(oc.createLog(pWidth * 0.75));
        obstacles.addAll(oc.createLog(pWidth * 0.5));
        obstacles.addAll(oc.createLog(pWidth));

        //START the game
        loop.start();
    }

    //TODO
    private void update() {
        gameTime++;
        //"windy" downforce from the north to the south
        double yDelta = 1;
        drake.moveDrake(0, yDelta);

        //check if the drake has passed an obstacle
        if(obstaclePassCheck(obstacles, drakeView)){
            updateScore(score + 1);
        }

        //move all obstacles on the pane
        oc.moveObstacles(obstacles);
        if(gameTime % spawningTime == 0){
            obstacles.addAll(oc.createLog(pWidth));
            oc.incMovingDistance();
            decSpawningTime();
        }

        if(drake.checkDeath(obstacles, mainPane)){
            resetGame();
        }
    }

    private void updateScore(int s) {
        score = s;
        scoreView.setText(String.valueOf(score));
    }

    //TODO

    /**
     * Defines what happens when the game is reset.
     */
    private void resetGame() {
        updateScore(0);
        spawningTime = orgSpawningTime;
        oc.resetMovingDistance();
    }

    private boolean obstaclePassCheck(ArrayList<Rectangle> obstacles, Rectangle drake){
        for (Rectangle r: obstacles) {
            int birdPositionX = (int) (drake.getLayoutX() + drake.getX());
            if(((int)(r.getLayoutX() + r.getX()) == birdPositionX)){
                return true;
            }
        }
        return false;
    }

    //TODO add diving to keys
    @FXML
    private void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE){
            //dive
            //drake.dive();
        } else if (event.getCode() == KeyCode.W) {
            drake.swim(1);
        } else if (event.getCode() == KeyCode.S) {
            drake.swim(-1);
        }
    }

    /**
     * Decreases the time interval between spawning a new obstacle
     */
    private void decSpawningTime() {
        if(spawningTime >= minSpawningTime + spawnFactor) {
            spawningTime -= spawnFactor;
        } else {
            spawningTime = minSpawningTime;
        }
        //ugly debug
        //System.out.println("spawning time: "+spawningTime+"[ms]");
    }

}