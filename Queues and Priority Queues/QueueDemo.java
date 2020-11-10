import java.util.*;
public class QueueDemo
{
	public QueueDemo(){
		Queue<Integer> queue=new LinkedList<Integer>();

		for(int x=0;x<10;x++)
			queue.add(x);
		System.out.print("[");
		while(!queue.isEmpty())
		{

			System.out.print(queue.poll());
			if(queue.peek()!=null)
				System.out.print(", ");

		}
		System.out.println("]");
	}

	public static void main(String[] args){
		QueueDemo app=new QueueDemo();
	}
}