import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.io.File;

public class Counter extends Application{

    private Timer timer;
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    private SimpleDoubleProperty time = new SimpleDoubleProperty(0);
    private int hrs,seconds,mins;
    private static double xOffset = 0;
    private static double yOffset = 0;
    private BooleanProperty pressed = new SimpleBooleanProperty(false);
    private BooleanProperty play = new SimpleBooleanProperty(true);

    @Override
    public void start(Stage primaryStage)throws Exception{
        Stage stage = new Stage();
              stage.getIcons().add(new Image(Counter.class.getResourceAsStream("counter.png")));
        StackPane stackPane = new StackPane();

        Scene scene = new Scene(stackPane,200,200);
                  stackPane.setStyle("-fx-background-color: transparent");
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox(10);
        CounterLabel[] labels = new CounterLabel[5];

            for (int i = 0; i < labels.length; ++i){
                labels[i] = new CounterLabel();
                labels[i].getTheLabel().setStyle("-fx-text-fill: lime;-fx-font-size: 34;");
                if(i%2 == 0)labels[i].getTheLabel().setText("00");
                else labels[i].getTheLabel().setText(":");
                hBox.getChildren().add(labels[i].getTheLabel());
            }

                   borderPane.setStyle("-fx-background-radius: 100;-fx-background-color: black;");
        editLabel(scene, labels[0].getTheLabel(), labels[0],labels);
        editLabel(scene, labels[2].getTheLabel(), labels[2],labels);
        editLabel(scene, labels[4].getTheLabel(), labels[4],labels);



        Button[] button = new Button[4];

        for(int i = 0; i < button.length; i++){
            button[i] = new Button();
            button[i].setPrefSize(100,100);

        }

        button[0].setStyle("-fx-background-radius: 150 0 0 0 ;-fx-text-alignment: LEFT;");
        button[0].setOnAction(event -> {

            hBox.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(hBox);

        });

        button[1].setStyle("-fx-background-radius:  0 0 150 0 ");
        button[1].setAlignment(Pos.TOP_RIGHT);
        button[2].setStyle("-fx-background-radius: 0 0 0 150 ");
               borderPane.setTop(button[0]);
               borderPane.setRight(button[1]);
               borderPane.setLeft(button[2]);



               final ProgressBar progressBar = new ProgressBar(0);
                                 progressBar.setPrefSize(200,20);
                                 progressBar.setStyle("");

               time.addListener(new ChangeListener<Number>() {
                   @Override
                   public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                       progressBar.setProgress(newValue.doubleValue()/60);
                   }
               });
              scene.setFill(Color.TRANSPARENT);
              stage.setScene(scene);
              stage.initStyle(StageStyle.TRANSPARENT);
        stackPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        stackPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });


        Circle circle = new Circle();
               circle.setCenterX(0);
               circle.setCenterX(0);
               circle.setRadius(75);
               hBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(borderPane,circle,hBox);
        stage.sizeToScene();
        stage.show();

    }


    private void editLabel(Scene scene,Label label,CounterLabel label1,CounterLabel[] labels){

        label.setOnMousePressed(event -> {
            if(!label1.isSelected()) {
                label1.setSelected(true);

                editLabel(label,scene,labels);
            }
            else if(label1.isSelected()){
                label1.setSelected(false);
            }});





    }

    private void play(Label label){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> label.setVisible(false)),
                            new KeyFrame(Duration.seconds(.5),event -> label.setVisible(true)));
                timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void editLabel(Label label,Scene scene,CounterLabel[] labels){
        ArrayList<String> digits = new ArrayList<>();

        scene.setOnKeyPressed(event -> {

            if((digits.size() == 2 || digits.size() == 1) && event.getCode() == KeyCode.BACK_SPACE){
                if(digits.size() == 2){
                    digits.remove(1);
                }else if(digits.size() == 1){
                    digits.remove(0);
                }
            }
            if(digits.size() == 2){
                event.consume();
            }else{
                if(event.getCode() == KeyCode.DIGIT0){
                    digits.add("0");
                }
                else if(event.getCode() == KeyCode.DIGIT1){
                digits.add("1");
            }else if(event.getCode() == KeyCode.DIGIT2){
                digits.add("2");
            }else if(event.getCode() == KeyCode.DIGIT3){
                digits.add("3");
            }else if(event.getCode() == KeyCode.DIGIT4){
                digits.add("4");
            }else if(event.getCode() == KeyCode.DIGIT5){
                digits.add("5");
            }else if(event.getCode() == KeyCode.DIGIT6){
                digits.add("6");
            }else if(event.getCode() == KeyCode.DIGIT7){
                digits.add("7");
            }else if(event.getCode() == KeyCode.DIGIT8){
                digits.add("8");
            }else if(event.getCode() == KeyCode.DIGIT9){
                digits.add("9");
            }else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.KP_RIGHT){
                    if (labels[0].isSelected()){
                        labels[2].setSelected(true);
                        labels[0].setSelected(false);
                        editLabel(labels[2].getTheLabel(),scene,labels);
                    }else if(labels[2].isSelected()){
                        labels[4].setSelected(true);
                        labels[2].setSelected(false);
                        editLabel(labels[4].getTheLabel(),scene,labels);
                    }
                }
                else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.KP_LEFT){
                    if (labels[4].isSelected()){
                        labels[2].setSelected(true);
                        labels[4].setSelected(false);
                        editLabel(labels[2].getTheLabel(),scene,labels);
                    }else if(labels[2].isSelected()){
                        labels[0].setSelected(true);
                        labels[2].setSelected(false);
                        editLabel(labels[0].getTheLabel(),scene,labels);
                    }
                }else if(event.getCode() == KeyCode.ENTER){
                    if(labels[0].isSelected()){
                        labels[0].setSelected(false);
                    }else if(labels[2].isSelected()){
                        labels[2].setSelected(false);
                    }else if(labels[4].isSelected()) {
                        labels[4].setSelected(false);
                    }
                }else if(event.getCode() == KeyCode.S) {
                    SimpleIntegerProperty clicked = new SimpleIntegerProperty(0);
                    if (clicked.get() == 0) {
                        clicked.set(1);
                        turnOnCountDown(labels,scene);
                    } else if (clicked.get() == 1) {
                        clicked.set(0);
                        turnOf();
                    }
                }}
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(digits.size() == 0){
                        label.setText(label.getText());
                    }else{
                        if(digits.size() == 1){
                            label.setText("0" + digits.get(0));
                        }else if(digits.size() == 2){
                            StringBuilder stringBuilder = new StringBuilder();
                            for(String s : digits){
                                stringBuilder.append(s);
                            }
                            label.setText(stringBuilder.toString());

                        }
                    }
                }
            });
        });
    }


    public static void main(String args[]){
        Application.launch(args);
    }

    private TimerTask timerTask(CounterLabel[] labels){
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                seconds++;
                if(seconds/60 == 1){
                    seconds = 0;
                    mins++;
                }if(mins/60 == 1){
                    mins = 0;
                    hrs++;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        labels[0].getTheLabel().setText(Integer.toString(hrs));
                        labels[2].getTheLabel().setText(Integer.toString(mins));
                        labels[4].getTheLabel().setText(Integer.toString(seconds));
                    }
                });
            }
        };
        timerTask.run();
        return timerTask;
    }


    private TimerTask countDown(CounterLabel[] labels,Scene scene){
        seconds = Integer.parseInt(labels[4].getTheLabel().getText());
        mins = Integer.parseInt(labels[2].getTheLabel().getText());
        hrs = Integer.parseInt(labels[0].getTheLabel().getText());
        System.out.println(seconds + "\t" + mins + "\t" + hrs);
        TimerTask timerTask;
        if(seconds == 0 && mins == 0 && hrs == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setContentText("Count down cant be started at 00");
                  alert.show();
        }else{
            timerTask = new TimerTask(){
            @Override
            public void run() {
                Thread thread;
                if(seconds == 0 && mins != 0) {
                    --mins;
                    seconds = 60;
                }else if(seconds != 0 && mins == 0){
                    seconds = seconds;
                }else if(seconds == 0 && mins == 0 && hrs != 0){
                    --hrs;
                    mins = 59;
                    seconds = 60;
                }
                seconds--;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        labels[0].getTheLabel().setText(Integer.toString(hrs));
                        labels[2].getTheLabel().setText(Integer.toString(mins));
                        labels[4].getTheLabel().setText(Integer.toString(seconds));
                    }
                });
                if(labels[0].getTheLabel().getText().matches("0") && labels[2].getTheLabel().getText().matches("0")
                        && labels[4].getTheLabel().getText().matches("10")){
                    Timeline timeline = new Timeline();
                    timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> labels[0].getTheLabel().setVisible(false)),
                            new KeyFrame(Duration.seconds(1),event -> labels[2].getTheLabel().setVisible(false)),
                            new KeyFrame(Duration.seconds(1),event -> labels[4].getTheLabel().setVisible(false)),
                            new KeyFrame(Duration.seconds(.5),event -> labels[0].getTheLabel().setVisible(true)),
                            new KeyFrame(Duration.seconds(.5),event -> labels[2].getTheLabel().setVisible(true)),
                            new KeyFrame(Duration.seconds(.5),event -> labels[4].getTheLabel().setVisible(true)));
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();


                }else if(labels[0].getTheLabel().getText().matches("0") && labels[2].getTheLabel().getText().matches("0")
                        && labels[4].getTheLabel().getText().matches("1")){
                    turnOf();
                    cancel();
                    Media media = new Media(new File(System.getProperty("user.home")+"/Downloads/Digital-phone-ringing.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setCycleCount(10);
                    mediaPlayer.play();

                }
                scene.setOnKeyPressed(event -> event.consume());
            }

            };

            return timerTask;
        }
        return null;
    }
    private void turnOnCountDown(CounterLabel[] labels,Scene scene){
        timer = new Timer();
        TimerTask timerTask = countDown(labels,scene);
        timer.scheduleAtFixedRate(timerTask,1000,1000);
        timerTask.run();

        switchedOn.set(!switchedOn.get());
    }


    private void turnOn(CounterLabel[] labels){
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask(labels),1000,1000);
        switchedOn.set(!switchedOn.get());
    }

    private void turnOf(){
        timer.cancel();
        switchedOn.set(!switchedOn.get());

    }

    private class CounterLabel extends Parent{
        private javafx.scene.control.Label label;
        private SimpleBooleanProperty selected;
        private Timeline timeline;

        protected CounterLabel(){
            this.label = new Label("00");
            this.selected = new SimpleBooleanProperty(false);
            this.timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> label.setVisible(false)),
                    new KeyFrame(Duration.seconds(.5),event -> label.setVisible(true)));
            this.timeline.setCycleCount(Animation.INDEFINITE);
        }

        protected Label getTheLabel(){
            return this.label;
        }

        protected boolean isSelected(){
            return this.selected.get();
        }

        protected void setSelected(Boolean selected){
            this.selected.set(selected);
            if(this.selected.get()){
                timeline.play();
            }else {
                timeline.stop();
                label.setVisible(true);
            }

        }




    }

}