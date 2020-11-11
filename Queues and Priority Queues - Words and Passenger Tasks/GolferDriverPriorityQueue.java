 import java.util.PriorityQueue;

 public class GolferDriverPriorityQueue
 {
 	public GolferDriverPriorityQueue()
 	{
 		PriorityQueue<Golfer> pq = new PriorityQueue<Golfer>();
		Golfer tiger = new Golfer ("Tiger Woods", 88);
		Golfer phil = new Golfer ("Al Mickelson", 57);
		Golfer ern = new Golfer ("Ernie Els", 57);
		Golfer dustin = new Golfer ("Dustin Johnson", 64);

		pq.add (tiger);
		pq.add (dustin);
		pq.add (ern);
		pq.add (phil);



		while (pq.peek()!=null)
		{
		    Golfer golfer = (Golfer) pq.poll ();
		    System.out.println (golfer.toString());
		}




    }

	public static void main(String args[])
	{
		GolferDriverPriorityQueue app=new GolferDriverPriorityQueue();

	}

 }