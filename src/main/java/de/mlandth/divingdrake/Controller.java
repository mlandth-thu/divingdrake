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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AnimationTimer loop;
    private boolean pause = false;

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
    private Media md;
    //https://freesound.org/people/gt.torre/sounds/470931/
    private AudioClip swimAudio;
    //https://freesound.org/people/InspectorJ/sounds/398719/
    private AudioClip diveAudio;
    //https://freesound.org/people/Michel88/sounds/76971/
    private AudioClip deathAudio;

    ArrayList<Rectangle> obstacles = new ArrayList<>();

    //sets the time interval between spawning new obstacles
    private int spawningTime = 500;
    private final int minSpawningTime = 400;
    private final int orgSpawningTime = 500;
    private final int spawnFactor = 5;

    private int spawnInterval = 0;
    private int scoreInterval = 0;
    private int soundInterval = 0;
    private int checkInterval = 0;
    private int moveInterval = 0;

    private int rockCounter = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init pane
        Color backgroundColor = Color.rgb(50,200,200, 0.8);
        mainPane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        //Create Drake
        double moveDelta = 60;
        drake = new Drake(drakeView, moveDelta, moveDelta);
        //Create Drake view
        Image drakeImg = new Image(getClass().getResource("images/drake.png").toExternalForm());
        drakeView.setFill(new ImagePattern(drakeImg));

        //Add ObstacleController
        oc = new ObstacleController(mainPane, pHeight, pWidth, 0.9);
        //Add Media
        md = new Media();
        swimAudio = md.getMedia("sounds/swim.wav");
        diveAudio = md.getMedia("sounds/dive.wav");
        deathAudio = md.getMedia("sounds/death.wav");

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

    private void update() {
        gameTime++;
        incIntervals();
        //"windy" downforce from the north to the south
        double yDelta = 1;
        drake.moveDrake(0, yDelta);

        //check if the drake has passed an obstacle
        if(obstaclePassCheck(obstacles, drakeView)){
            if (scoreInterval > 200 && checkInterval > 50) {
                updateScore(score + 1);
                checkInterval = 0;
            }
        }

        //move all obstacles on the pane
        oc.moveObstacles(obstacles);
        if(spawnInterval > 200 && gameTime % spawningTime == 0) {
            //create new Log
            obstacles.addAll(oc.createLog(pWidth));

            //create new Rock
            if (rockCounter % 5 == 4) {
                obstacles.addAll(oc.createRock(pWidth + 150));
                oc.incMovingDistance();
                decSpawningTime();
            }

            //increase difficulty
            oc.incMovingDistance();
            decSpawningTime();
            rockCounter++;
        }

        //Check if the drakes collides and so is dead
        if(drake.checkDeath(obstacles, mainPane)){
            resetGame();
        }
    }

    private void updateScore(int s) {
        score = s;
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Defines what happens when the game is reset.
     */
    private void resetGame() {
        //Play death sound && soundInterval = 0
        playDeathSound();
        //Resets score && scoreInterval = 0
        updateScore(0);
        scoreInterval = 0;
        //Resets spawning values
        spawningTime = orgSpawningTime;
        spawnInterval = -100;
        rockCounter = 0;
        //Resets moving distance for obstacles
        oc.resetMovingDistance();
    }

    //uses the javafx built in obstacle collision check
    private boolean obstaclePassCheck(ArrayList<Rectangle> obstacles, Rectangle drake){
        for (Rectangle r: obstacles) {
            double drakeX = drake.getLayoutX() + drake.getX();
            double obstacleX = r.getLayoutX() + r.getX();
            double lowerBound = drakeX - 2;
            double higherBound = drakeX + 2;
            if(lowerBound <= obstacleX && obstacleX <= higherBound){
                return true;
            }
        }
        return false;
    }

    //TODO add diving to keys
    @FXML
    private void pressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (pause) {
                loop.start();
            } else {
                loop.stop();
            }
            pause = !pause;
        }
        else if(moveInterval > 7) {
            if(event.getCode() == KeyCode.SPACE){
                //dive
                //drake.dive();
            } else if (event.getCode() == KeyCode.W) {
                drake.swim(1);
                md.playMedia(swimAudio);
                moveInterval = 0;
            } else if (event.getCode() == KeyCode.S) {
                drake.swim(-1);
                md.playMedia(swimAudio);
                moveInterval = 0;
            }
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

    /**
     * responsible for playing the death sound only once
     */
    private void playDeathSound() {
        if (soundInterval > 200) {
            md.playMedia(deathAudio);
            soundInterval = 0;
        }
    }

    private void incIntervals() {
        spawnInterval++;
        soundInterval++;
        scoreInterval++;
        checkInterval++;
        moveInterval++;
    }

}