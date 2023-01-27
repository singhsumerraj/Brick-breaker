package com.example.brickbreaker;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane scene;

    double deltax = 0.5;

    double deltay = 0.5;

    ArrayList<Rectangle> all_bricks = new ArrayList<>();

    private Rectangle slider;

    private Button left, right;

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(7), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            // We need to move the ball
            circle.setLayoutY(circle.getLayoutY() + deltay);
            circle.setLayoutX(circle.getLayoutX() + deltax);

            // We need to check for collisions here every time


            // Collision with walls, which is collision with scene
            check_collision_with_wall();

            // Collision with bricks
            check_collision_with_bricks();

            // Collision with slider
            check_collision_with_slider();

            // See if all the bricks are collide, then exit the game
            if(all_bricks.isEmpty()) {
                System.exit(10);
            }
        }
    }));

    public void check_collision_with_bricks() {
        all_bricks.removeIf(current_brick -> check_collision_with_single_brick(current_brick));
    }

    public boolean check_collision_with_single_brick(Rectangle brick) {
        if(circle.getBoundsInParent().intersects(brick.getBoundsInParent())) {
            Bounds bounds = brick.getBoundsInLocal();
            boolean bottomside = circle.getLayoutY() - circle.getRadius() <= bounds.getMaxY();
            boolean topside = circle.getLayoutY() - circle.getRadius() >= bounds.getMinY();
            boolean rightside = circle.getLayoutX() - circle.getRadius() <= bounds.getMaxX();
            boolean leftside = circle.getLayoutX() + circle.getRadius() >= bounds.getMinX();

            if(rightside || leftside) {
                deltax *= -1;
            }

            if(topside || bottomside) {
                deltay *= -1;
            }

            scene.getChildren().remove(brick);
            return true;
        }
        return false;
    }
    public void check_collision_with_slider() {
        if(circle.getBoundsInParent().intersects(slider.getBoundsInParent())) {
            deltay *= -1;
        }
    }

    public void check_collision_with_wall() {
        Bounds bounds = scene.getBoundsInLocal();
        boolean rightside = circle.getLayoutX() + circle.getRadius() >= bounds.getMaxX();
        boolean leftside = circle.getLayoutX() - circle.getRadius() <= bounds.getMinX();
        boolean topside = circle.getLayoutY() - circle.getRadius() <= bounds.getMinY();
        boolean bottomside  = circle.getLayoutY() + circle.getRadius() >= bounds.getMaxY();

        if(rightside || leftside) {
            deltax *= -1;
        }

        if(topside) {
            deltay *= -1;
        }

        // Uncomment this code, this is just for testing purpose.
        if(bottomside) {
            deltay *= -1;
        }

//        if(bottomside) {
//            System.exit(99);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        adding_slider();
        create_bricks();
        adding_buttons();
    }

    public void adding_buttons() {
        left = new Button("left");
        right = new Button("right");
        left.setLayoutY(350);
        right.setLayoutY(350);

        left.setLayoutX(20);
        right.setLayoutX(540);

        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(slider.getLayoutX() > -230) {
                    slider.setLayoutX(slider.getLayoutX() - 20);
                }
            }
        });

        right.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(slider.getLayoutX() < 272) {
                    slider.setLayoutX(slider.getLayoutX() + 20);
                }
            }
        });

        scene.getChildren().add(left);
        scene.getChildren().add(right);
    }

    public void adding_slider() {
        slider = new Rectangle(250, 375, 60, 15);
        slider.setFill(Color.BLACK);
        scene.getChildren().add(slider);
    }

    public void create_bricks() {

        int counter = 1;
        for(int i=255; i>0; i-=50) {
            for(int j=508; j>=0; j-=30) {
                if(counter%2==1) {
                    Rectangle rect = new Rectangle(j, i, 40, 40);

                    if(counter%3==0) {
                        rect.setFill(Color.ORANGE);
                    }
                    else if(counter%3==1) {
                        rect.setFill(Color.GREENYELLOW);
                    }
                    else {
                        rect.setFill(Color.ORANGERED);
                    }
                    scene.getChildren().add(rect);
                    all_bricks.add(rect);
                }
                counter++;
            }
        }
    }
}
