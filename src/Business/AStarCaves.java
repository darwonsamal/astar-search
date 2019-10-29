package Business;


import java.util.ArrayList;
import java.util.Collections;
import Data.*;

public class AStarCaves
{

    private SortedCaveList open = new SortedCaveList();
    private SortedCaveList closed = new SortedCaveList();

    private boolean flag = true;
    private boolean complete = false;

    private Cave startCave;
    private Cave endCave;
    private Cave currentCave;


    public AStarCaves()
    {
    }

    public void setStartAndEndCave(Cave startCave, Cave endCave)
    {
        this.startCave = startCave;
        this.endCave = endCave;
        open.emptyList();
        closed.emptyList();
        open.add(startCave);
        complete = false;
    }


    public ArrayList<Cave> generateSolutionPath()
    {
        return aStarsHelper();
    }


    public ArrayList<Cave> aStarsHelper()
    {
        Cave tempEndCave = endCave;

            while (this.flag)
            {
                currentCave = open.pop();


                if (currentCave.equals(tempEndCave))
                {
                    closed.emptyList();
                    open.emptyList();

                    return printSolutionPath(tempEndCave);


                }

                closed.add(currentCave);

                ArrayList<Cave> children = currentCave.getChildren();

                checkChildren(children);
            }

      return null;
    }

    public void checkChildren(ArrayList<Cave> children)
    {
        Cave tempEndCave = endCave;

        for (Cave currentChild : children)
        {
            if (!closed.contains(currentChild))
            {
                if (!open.contains(currentChild))
                {
                    currentChild.setParentCave(currentCave);
                    currentChild.setHeuristicCostToGoal(tempEndCave);
                    currentChild.setGCostsTo(currentCave);
                    open.add(currentChild);
                }
                else
                {
                    if (currentChild.getGCosts() > currentChild.calculateGCostsTo(currentCave))
                    {
                        currentChild.setParentCave(currentCave);
                        currentChild.setGCostsTo(currentCave);
                    }
                }
            }
        }
    }



    private ArrayList<Cave> printSolutionPath(Cave endCave)
    {

        Cave solutionCave = endCave;
        ArrayList<Cave> solutionPath = new ArrayList<>();

        while (solutionCave != null)
        {
            
            solutionPath.add(solutionCave);
            solutionCave = solutionCave.getParentCave();
        }

        Collections.reverse(solutionPath);

        return  solutionPath;
    }

}
