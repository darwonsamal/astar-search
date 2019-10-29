package Data;

import Business.Cave;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SortedCaveList
{
    PriorityQueue<Cave> priorityQueue;

    public SortedCaveList()
    {
        priorityQueue = new PriorityQueue<>(new Comparator<>()
        {
            @Override
            public int compare(Cave o1, Cave o2) {

                return (int)o1.getTotalCost() - (int)o2.getTotalCost();
            }
        });
    }

    public void add(Cave e)
    {
       priorityQueue.add(e);
    }

    public boolean contains(Cave e)
    {
        if(priorityQueue.contains(e))
        {
            return true;
        }

        return false;
    }

    public Cave pop()
    {
        Cave e = priorityQueue.poll();
        return  e;
    }

    public void emptyList()
    {
        while (priorityQueue.size() != 0)
        {
            priorityQueue.poll();
        }
    }
}
