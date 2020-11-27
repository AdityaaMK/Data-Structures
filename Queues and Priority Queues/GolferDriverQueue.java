 import java.util.*;


 public class GolferDriverQueue
 {
 	public GolferDriverQueue()
 	{
 		Queue<Golfer> pq = new LinkedList<Golfer>();
		Golfer tiger = new Golfer ("Tiger Woods", 108);
		Golfer phil = new Golfer ("Phil Mickelson", 61);
		Golfer ernie = new Golfer ("Ernie Els", 69);
		Golfer dustin = new Golfer ("Dustin Johnson", 66);
		pq.add (tiger);
		pq.add (phil);
		pq.add (ernie);
		pq.add(dustin);

		while (!pq.isEmpty())
		{
		    Golfer golfer = (Golfer) pq.poll();
		    System.out.println (golfer.toString());
		}
    }

	public static void main(String args[])
	{
		GolferDriverQueue app=new GolferDriverQueue();

	}

 }