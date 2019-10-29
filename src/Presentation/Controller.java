package Presentation;

import Business.AStarCaves;
import Business.Cave;
import Data.cavParser;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Controller
{

    @FXML
    private TableView<Cave> openTable;


    @FXML
    private TableView<Cave> solutionTable;

    @FXML
    private TableColumn<Cave, String> childrenList;


    @FXML
    private TableColumn<Cave, String> solutionList;

    @FXML
    private Pane pane;

    @FXML
    private Label lblTotalScore;

    @FXML
    private ChoiceBox<String> cavFiles;

    @FXML
    private Label lblCurrentCave;


    private String input;

    private ArrayList<Cave> caves;

    private ArrayList<Cave> solutionPath;

    private Cave startCave;

    private Cave endCave;

    private int i;

    private AStarCaves aStarCaves;

    private ObservableList<Cave> openCaveData = FXCollections.observableArrayList();

    private ObservableList<Cave> solutionCaveData = FXCollections.observableArrayList();


    public Controller()
    {
    }

    @FXML
    private void initialize()
    {
        aStarCaves = new AStarCaves();
        cavFiles.setItems(FXCollections.observableArrayList(
                "Cave files 1", "Cave files 2", "Cave files 3"));


    }


    public void loadCavFile()  throws FileNotFoundException, IOException
    {

        try
        {

            pane.getChildren().removeAll(pane.getChildren());

            solutionCaveData.removeAll(solutionCaveData);
            openCaveData.removeAll(openCaveData);

            lblTotalScore.setText("");
            lblCurrentCave.setText("");

            input = cavFiles.getValue();

            if(input.equals("Cave files 1"))
            {
                input = "input1.cav";
            }
            else if(input.equals("Cave files 2"))
            {
                input = "input.cav";
            }
            else if(input.equals("Cave files 3"))
            {
                input = "input3.cav";
            }
            else
            {
               throw new Exception("Select Cav file!");
            }


            cavParser data = cavParser.getInstance(input);

            caves = data.buildCaves();

            startCave = caves.get(0);

            endCave = caves.get(caves.size() - 1);

            aStarCaves.setStartAndEndCave(startCave, endCave);

            solutionPath = aStarCaves.generateSolutionPath();


            for(int i = 0; i < caves.size(); i++)
            {

                Cave e = caves.get(i);

                if(e.equals(startCave))
                {
                    Label startCaveLabel = new Label("START CAVE");
                    pane.getChildren().add(startCaveLabel);
                    startCaveLabel.relocate((pane.getPrefWidth() / 2) + ((e.getX() * 10) - 30), (pane.getPrefHeight() / 2) + ((e.getY() * 10) - 5));
                    startCaveLabel.rotateProperty().setValue(180);


                }

                if(e.equals(endCave))
                {
                    Label endCaveLabel = new Label("END CAVE");
                    pane.getChildren().add(endCaveLabel);
                    endCaveLabel.relocate((pane.getPrefWidth() / 2) + ((e.getX() * 10) - 30), (pane.getPrefHeight() / 2) + ((e.getY() * 10) - 5));
                    endCaveLabel.rotateProperty().setValue(180);

                }


                Circle circle = new Circle(4, Color.GREEN);
                circle.setCenterX((pane.getPrefWidth() / 2) + (e.getX() * 10));
                circle.setCenterY((pane.getPrefHeight() / 2) + (e.getY() * 10));

                pane.getChildren().add(circle);


                generateLines(e);

            }

            i = 0;
            aStarCaves.setStartAndEndCave(startCave, endCave);


        }
        catch (Exception ee)
        {
            System.out.println(ee.fillInStackTrace());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Cave Files");
           alert.setHeaderText("Cave Files Error!");
           alert.setContentText("Must select a cave file first!");
           alert.showAndWait();
        }
    }


    public void generateLines(Cave e)
    {
        for(Cave ee : e.getChildren())
        {
            double currentCaveX = (pane.getPrefWidth() / 2) + (e.getX() * 10);
            double currentCaveY = (pane.getPrefHeight() / 2) + (e.getY() * 10);

            double currentChildX = (pane.getPrefWidth() / 2) + (ee.getX() * 10);
            double currentChildY = (pane.getPrefHeight() / 2) + (ee.getY() * 10);

            Line line = new Line(currentCaveX, currentCaveY, currentChildX, currentChildY);



            Point2D start = new Point2D(currentCaveX, currentCaveY);


            double midPointX = start.midpoint(currentChildX, currentChildY).getX();

            double midPointY = start.midpoint(currentChildX, currentChildY).getY();


            Line connectedLine = new Line(midPointX, midPointY, currentChildX, currentChildY);
            connectedLine.setStroke(Color.PURPLE);
            connectedLine.setStrokeWidth(3);


            pane.getChildren().add(connectedLine);
            pane.getChildren().add(line);
        }
    }

    public void stepThrough()
    {
        solutionPath = aStarCaves.generateSolutionPath();
        openCaveData.removeAll(openCaveData);

        if((i < solutionPath.size()))
        {

            Circle circle = new Circle(4, Color.RED);


            circle.setCenterX((pane.getPrefWidth() / 2) + (solutionPath.get(i).getX() * 10));
            circle.setCenterY((pane.getPrefHeight() / 2) + (solutionPath.get(i).getY() * 10));

            if(!(i + 1 > solutionPath.size() - 1))
            {
                double currentCaveX = (pane.getPrefWidth() / 2) + (solutionPath.get(i).getX() * 10);
                double currentCaveY = (pane.getPrefHeight() / 2) + (solutionPath.get(i).getY() * 10);

                double nextCaveX = (pane.getPrefWidth() / 2) + (solutionPath.get(i + 1).getX() * 10);
                double nextCaveY = (pane.getPrefHeight() / 2) + (solutionPath.get(i + 1).getY() * 10);

                Line redLine = new Line(currentCaveX, currentCaveY, nextCaveX, nextCaveY);
                redLine.setStroke(Color.RED);

                if(pane.getChildren().contains(redLine))
                {
                    pane.getChildren().remove(redLine);
                }

                Point2D currentCave = new Point2D(currentCaveX, currentCaveY);


                double CurrentCaveMidPointX = currentCave.midpoint(nextCaveX, nextCaveY).getX();

                double CurrentCaveMidPointY = currentCave.midpoint(nextCaveX, nextCaveY).getY();


                Line currentCaveConnectedLine = new Line(CurrentCaveMidPointX, CurrentCaveMidPointY, nextCaveX, nextCaveY);

                Cave currentTempCave = solutionPath.get(i);




                if(pane.getChildren().contains(currentCaveConnectedLine))
                {

                    pane.getChildren().remove(currentCaveConnectedLine);


                }
                currentCaveConnectedLine.setStroke(Color.RED);
                currentCaveConnectedLine.setStrokeWidth(3);


                pane.getChildren().add(currentCaveConnectedLine);
                pane.getChildren().add(redLine);

                if(solutionPath.get(i + 1).getChildren().contains(currentTempCave))
                {

                    Point2D nextCave = new Point2D(nextCaveX, nextCaveY);


                    double nextCaveMidPointX = nextCave.midpoint(nextCaveX, nextCaveY).getX();

                    double nextCaveMidPointY = nextCave.midpoint(nextCaveX, nextCaveY).getY();

                    Line nextCaveConnectedLine = new Line(nextCaveMidPointX, nextCaveMidPointY, currentCaveX, currentCaveY);

                    if(pane.getChildren().contains(nextCaveConnectedLine))
                    {
                        pane.getChildren().remove(nextCaveConnectedLine);
                    }

                    nextCaveConnectedLine.setStroke(Color.RED);
                    nextCaveConnectedLine.setStrokeWidth(3);

                    pane.getChildren().add(nextCaveConnectedLine);

                }

            }

            Circle tempCave = circle;

            if(pane.getChildren().contains(tempCave))
            {
                pane.getChildren().remove(tempCave);
            }

            pane.getChildren().add(circle);

            lblTotalScore.setText(Double.toString(solutionPath.get(i).getTotalCost()));
            lblCurrentCave.setText(solutionPath.get(i).toString());
            solutionCaveData.add(solutionPath.get(i));
            solutionTable.setItems(solutionCaveData);
            solutionList.setCellValueFactory(cellData -> cellData.getValue().getCaveName());

            openCaveData.addAll(solutionPath.get(i).getChildren());
            openTable.setItems(openCaveData);
            childrenList.setCellValueFactory(cellData -> cellData.getValue().getCaveName());
        }
        i++;
        aStarCaves.setStartAndEndCave(startCave, endCave);
    }

    public void stepThroughAll()
    {

        for(int i = 0; i < solutionPath.size(); i++)
        {
            stepThrough();
        }
    }

}
