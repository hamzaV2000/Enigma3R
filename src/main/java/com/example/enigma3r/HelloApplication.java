package com.example.enigma3r;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    AnchorPane pane;
    //main matrix for rotors,input,reflection
    Label rotars[][];


    @Override
    public void start(Stage stage) throws IOException {
        this.pane = new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        pane.setPrefWidth(800);
        pane.setPrefHeight(800);

        Button b1 =new Button("SetUp");
        b1.setLayoutX(500);
        b1.setLayoutY(500);
        pane.getChildren().add(b1);

        TextField tf=new TextField();
        tf.setLayoutX(500);
        tf.setLayoutY(530);
        pane.getChildren().add(tf);

        Button b2 =new Button("Encode");
        b2.setLayoutX(500);
        b2.setLayoutY(560);
        pane.getChildren().add(b2);

        TextField tf2=new TextField();
        tf2.setLayoutX(500);
        tf2.setLayoutY(590);
        pane.getChildren().add(tf2);

        Label label=new Label();
        label.setLayoutX(450);
        label.setLayoutY(200);
        label.setFont(new Font(40));
        label.setTextFill(Color.DARKGREEN);
        label.setStyle("-fx-border-color: black;");
        pane.getChildren().add(label);

        b2.setOnAction(actionEvent -> {
            if(tf2.getText().length()==0)
            {
                tf2.setText(label.getText());
                label.setText("");
                b2.setDisable(true);
            }
            else{
                tf2.setText(tf2.getText().toUpperCase());
                Encode(tf2,label);
            }

        });

        Scene scene = new Scene(pane, 800, 800);
        char[][] Matrix = {
                {'A',' ', 'A', 'E', ' ', 'A', 'A', ' ', 'A', 'B','A'},
                {'B',' ', 'B', 'K', ' ', 'B', 'J', ' ', 'B', 'D','B'},
                {'C',' ', 'C', 'M', ' ', 'C', 'D', ' ', 'C', 'F','C'},
                {'D',' ', 'D', 'F', ' ', 'D', 'K', ' ', 'D', 'H','D'},
                {'E',' ', 'E', 'L', '^', 'E', 'S', ' ', 'E', 'J','E'},
                {'F',' ', 'F', 'G', ' ', 'F', 'I', ' ', 'F', 'L','F'},
                {'G',' ', 'G', 'D', ' ', 'G', 'R', ' ', 'G', 'C','G'},
                {'D',' ', 'H', 'Q', ' ', 'H', 'U', ' ', 'H', 'P','H'},
                {'I',' ', 'I', 'V', ' ', 'I', 'X', ' ', 'I', 'R','I'},
                {'J',' ', 'J', 'Z', ' ', 'J', 'B', ' ', 'J', 'T','J'},
                {'K',' ', 'K', 'N', ' ', 'K', 'L', ' ', 'K', 'X','K'},
                {'G',' ', 'L', 'T', ' ', 'L', 'H', ' ', 'L', 'V','L'},
                {'M',' ', 'M', 'O', ' ', 'M', 'W', ' ', 'M', 'Z','M'},
                {'K',' ', 'N', 'W', ' ', 'N', 'T', ' ', 'N', 'N','N'},
                {'M',' ', 'O', 'Y', ' ', 'O', 'M', ' ', 'O', 'Y','O'},
                {'I',' ', 'P', 'H', ' ', 'P', 'C', ' ', 'P', 'E','P'},
                {'E','^', 'Q', 'X', ' ', 'Q', 'Q', ' ', 'Q', 'I','Q'},
                {'B',' ', 'R', 'U', ' ', 'R', 'G', ' ', 'R', 'W','R'},
                {'F',' ', 'S', 'S', ' ', 'S', 'Z', ' ', 'S', 'G','S'},
                {'T',' ', 'T', 'P', ' ', 'T', 'N', ' ', 'T', 'A','T'},
                {'C',' ', 'U', 'A', ' ', 'U', 'P', ' ', 'U', 'K','U'},
                {'V',' ', 'V', 'I', ' ', 'V', 'Y', '^', 'V', 'M','V'},
                {'V',' ', 'W', 'B', ' ', 'W', 'F', ' ', 'W', 'U','W'},
                {'J',' ', 'X', 'R', ' ', 'X', 'V', ' ', 'X', 'S','X'},
                {'A',' ', 'Y', 'C', ' ', 'Y', 'O', ' ', 'Y', 'Q','Y'},
                {'T',' ', 'Z', 'J', ' ', 'Z', 'E', ' ', 'Z', 'O','Z'}
        };
        b1.setOnAction(actionEvent -> {
           setUp(tf.getText().toUpperCase());
           b2.setDisable(false);
        });
        drawRotars(Matrix);


        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
    public void Encode(TextField tf,Label l){
        char c=tf.getText().charAt(0);
        //rotate the first rotor 1 step on each input
        rotateTheRotar(0,false);
        char indexChar=rotars[c-'A'][9].getText().charAt(0);
        boolean flag=true;
        int direction=-2;
        int col=8;
        int y=-1;

        while(flag&&col>=0)
        {
            for(int i=0;i<26;i++)
            {
                //handle the search in the reflection column
                if(i==y)
                {
                    y=-1;
                    continue;
                }
                if(rotars[i][col].getText().charAt(0)==indexChar)
                {
                    System.out.println(indexChar);
                    
                    //we reached the result
                    if(col>=10)
                    {
                        if(tf.getText().length()>0)
                            tf.setText(tf.getText().substring(1));
                        else
                            l.setText("");
                        l.setText(l.getText()+indexChar);
                        flag=false;
                        continue;
                    }
                    //handle the index out of bound in the way back
                    if(col+2>10)
                        col=8;
                    
                    //in the way forward we decrease 2 column to get the next char
                    indexChar=rotars[i][col+direction].getText().charAt(0);
                    
                    //we decrease or increase 1 step over the column based on the direction sign
                    col+=(direction+(direction/2));
                    
                    if(col>10)
                        col=10;
                    //reached the end so get backward 
                    if(col<0)
                    {
                        col=0;
                        y=i;
                        direction=2;
                    }
                    //exit the for loop since we we found what we want
                    i=27;
                }
            }
        }

    }

    private void setUp(String s) {
        new Thread(() ->{
            //keep searching until you find the first character of the setup word
            while(rotars[0][2].getText().charAt(0)!=s.charAt(0)) {
                rotateTheRotar(2,true);
                try {
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //keep searching until you find the second character of the setup word
            while(rotars[0][5].getText().charAt(0)!=s.charAt(1)) {
                rotateTheRotar(1,true);
                try {
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //keep searching until you find the 3rd character of the setup word
            while(rotars[0][8].getText().charAt(0)!=s.charAt(2)) {
                rotateTheRotar(0,true);
                try {
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void drawRotars(char[][] matrix) {
        rotars = new Label[matrix.length][matrix[0].length];
        int x = 50;
        int y = 50;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                rotars[i][j] = new Label("" + matrix[i][j]);
                Label l = rotars[i][j];
                //l.setPrefWidth(20);
                if(j==matrix[0].length-1) {
                    l.setLayoutX(x * 1.2);
                    l.setTextFill(Color.RED);
                }

                else
                    l.setLayoutX(x);
                if(j==0) {
                    l.setTextFill(Color.BLUE);
                }
                if(j==2||j==5||j==8) {
                    l.setTextFill(Color.PURPLE);
                }



                l.setLayoutY(y);
                l.setFont(new Font(20));
                pane.getChildren().add(l);
                x += 22;

            }
            x = 50;
            y += 25;
        }


    }

    public void rotateTheRotar(int r,boolean setup) {
        int temp=r;
        r=Math.abs(r-2);
        r*=3;
        r++;
        Label []firstRow={rotars[0][r],rotars[0][r+1],rotars[0][r+2]};

       // cheking the notch to rotate the other rotor

        if(firstRow[0].getText().charAt(0)=='^'&&!setup)
        {
            Platform.runLater(() -> {
                rotateTheRotar((temp+1)%3,false);
            });

        }


        //save last row y values so we swap them with the first row
        double y0=rotars[25][r].getLayoutY(),y1=rotars[25][r+1].getLayoutY(),y2=rotars[25][r+2].getLayoutY();


        //swapping at the main matrix moving elements up
        for(int i=1;i<rotars.length;i++)
            for(int j=r;j<r+3;j++)
               rotars[i-1][j]=rotars[i][j];



        //using the y values that we got rom the last row before the rotation
        firstRow[0].setLayoutY(y0+25);

        firstRow[1].setLayoutY(y1+25);

        firstRow[2].setLayoutY(y2+25);

        //putting the first row in the last
        rotars[25][r]=firstRow[0];
        rotars[25][r+1]=firstRow[1];
        rotars[25][r+2]=firstRow[2];

        //rotation animation
        TranslateTransition transition[]= new TranslateTransition[78];
        int t=0;
        for(int i=0;i<rotars.length;i++)
            for(int j=r;j<r+3;j++)
            {
                transition[t]=new TranslateTransition(Duration.millis(100),rotars[i][j]);
                transition[t].setByY(-25);
                t++;
            }
        ParallelTransition parallelTransition=new ParallelTransition();
        parallelTransition.getChildren().addAll(transition);
        parallelTransition.play();

       new Thread(() -> {
            String bip = "src\\main\\resources\\com\\example\\enigma3r\\click.mp3";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.seek(Duration.seconds(12));
            mediaPlayer.play();
        }).start();



    }
}