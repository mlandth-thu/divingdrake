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
    private Text score;

    private int gameTime = 0;
    private int scoreCounter = 0;

    private Drake drake;
    private ObstacleController oc;

    ArrayList<Rectangle> obstacles = new ArrayList<>();


    //TODO change values
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

        double pHeight = 800;
        double pWidth = 1600;

        oc = new ObstacleController(mainPane, pHeight, pWidth);

        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };


        obstacles.addAll(oc.createLog());

        loop.start();
    }

    //TODO
    private void update() {
        gameTime++;
        double yDelta = 1;
        drake.moveDrake(0, yDelta);

        if(obstaclePassCheck(obstacles, drakeView)){
            scoreCounter++;
            score.setText(String.valueOf(scoreCounter));
        }

        oc.moveObstacles(obstacles);
        if(gameTime % 500 == 0){
            obstacles.addAll(oc.createLog());
        }

        if(drake.checkDeath(obstacles, mainPane)){
            resetGame();
        }
    }

    //TODO
    private void resetGame() {

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

    //TODO
    @FXML
    void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE){
            //dive
            //drake.dive();
        } else if (event.getCode() == KeyCode.W) {
            drake.swim(1);
        } else if (event.getCode() == KeyCode.S) {
            drake.swim(-1);
        }
    }

}