package Data;

import Business.Cave;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class cavParser
{

    private static cavParser ourInstance;
    private static String fileName;

    private cavParser()
    {
    }

    public static cavParser getInstance(String file)
    {

        if(ourInstance == null)
        {
            ourInstance = new cavParser();
        }
        fileName = file;
        return ourInstance;
    }



    public boolean[][] buildConnectivityMatrix(int noOfCaves, String[] data) throws FileNotFoundException, IOException
    {

        //Build connectivity matrix

        //Declare the array
        boolean[][] connected = new boolean[noOfCaves][];

        for (int row= 0; row < noOfCaves; row++){
            connected[row] = new boolean[noOfCaves];
        }
        //Now read in the data - the starting point in the array is after the coordinates
        int col = 0;
        int row = 0;

        for (int point = (noOfCaves*2)+1 ; point < data.length; point++){
            //Work through the array

            if (data[point].equals("1"))
                connected[row][col] = true;
            else
                connected[row][col] = false;

            row++;
            if (row == noOfCaves){
                row=0;
                col++;
            }
        }
        //Connected now has the adjacency matrix within it
        return connected;
    }

    public ArrayList<Cave> buildCaves() throws FileNotFoundException, IOException
    {
        // Open input.cav
        ArrayList<Cave> caves = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        //Read the line of comma separated text from the file
        String buffer = br.readLine();
      //  System.out.println("Raw data : " + buffer);

        br.close();

        //Convert the data to an array
        String[] data = buffer.split(",");

        //Now extract data from the array - note that we need to convert from String to int as we go
        int noOfCaves = Integer.parseInt(data[0]);
        System.out.println ("There are " + noOfCaves + " caves.");

        int caveID = 1;
        //Get coordinates

        for (int count = 1; count < ((noOfCaves*2)+1); count=count+2){
            //System.out.println("Cave at " + data[count] +"," +data[count+1]);
            int x = Integer.parseInt(data[count]);
            int y = Integer.parseInt(data[count + 1]);

            Cave cave = new Cave(x, y, caveID);

            caves.add(cave);
            caveID++;
        }

        boolean[][] connectedMatrix = this.buildConnectivityMatrix(noOfCaves, data);

        generateChildrenForCaves(caves, connectedMatrix);


        return caves;
    }

    public void generateChildrenForCaves(ArrayList<Cave> caves, boolean[][] connectedMatrix)
    {

        for(int i = 0; i < caves.size(); i++)
        {
            Cave tempCave = caves.get(i);
           // System.out.println("current cave: " +tempCave.toString());
            for(int j = 0; j < caves.size(); j++)
            {
                Cave possibleChild = caves.get(j);
                boolean areConnected = connectedMatrix[tempCave.getCaveID() - 1][possibleChild.getCaveID() - 1];

                if(!tempCave.equals(possibleChild) && areConnected)
                {
                   // System.out.println("child: " + possibleChild.toString());
                    tempCave.addChildCave(possibleChild);
                }
            }
        }
    }

}
