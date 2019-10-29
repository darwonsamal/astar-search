package Business;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.util.ArrayList;

public class Cave
{

    private int x;
    private int y;
    private int caveID;

    private StringProperty caveName;
    private double g;
    private double heuristic;

    private Cave parentCave;
    private ArrayList<Cave> children = new ArrayList<>();

    public Cave(int x, int y, int caveID)
    {
        this.x = x;
        this.y = y;
        this.caveID = caveID;
        caveName = new SimpleStringProperty();
        updateCaveData();

    }

    private void updateCaveData()
    {
        caveName.setValue("Cave ID: " + Integer.toString(caveID)  + " G: " + String.format("%.3f", this.g) + " Heuristic: " + String.format("%.3f", this.heuristic));
    }

    public double getGCosts()
    {
        return g;
    }

    public StringProperty getCaveName() {
        return caveName;
    }

    public double calculateGCostsTo(Cave e)
    {
        double distance = Math.hypot(this.getX() - e.getX(), this.getY() - e.getY());
        return (e.g + distance);
    }

    public void setGCostsTo(Cave e)
    {
        double distance = Math.hypot(this.getX() - e.getX(), this.getY() - e.getY());

        this.g = (e.g + distance);
    }

    public void setHeuristicCostToGoal(Cave e)
    {
        double distance = Math.hypot(this.getX() - e.getX(), this.getY() - e.getY());
        this.heuristic = distance;
    }

    public double getTotalCost()
    {
        updateCaveData();
        return g + heuristic;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getCaveID()
    {
        return caveID;
    }


    public void setParentCave(Cave parentCave)
    {
        this.parentCave = parentCave;

    }

    public Cave getParentCave()
    {
        return parentCave;
    }

    public void addChildCave(Cave e)
    {
        this.children.add(e);
    }

    public ArrayList<Cave> getChildren()
    {
        return children;
    }

    @Override
    public String toString() {
        return "Cave{" +
                "x=" + x +
                ", y=" + y +
                ", caveID=" + caveID +
                '}';
    }

    public boolean equals(Cave e)
    {

        if(e == null)
        {
            return false;
        }


        if(this.x == e.getX() && this.y == e.getY() && this.caveID == e.caveID)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
