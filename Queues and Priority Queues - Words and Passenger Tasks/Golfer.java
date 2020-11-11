public class Golfer implements Comparable<Golfer>
{
    String golferName;
    int golferScore;

    public Golfer (String name, int score)
    {
        golferName = name;
        golferScore = score;
    }
    public String toString()
    {
		return golferName+" "+golferScore;

	}
	public String getName()
	{
		return golferName;
	}

  	public int compareTo (Golfer otherGolfer)
    {
		int a = golferScore;
		int b = otherGolfer.golferScore;

		if (a>b) return 1;
		if (a<b) return -1;
		return golferName.compareTo(otherGolfer.getName());
    }


/*	public int compareTo (Object obj)
    {
		Golfer otherGolfer = (Golfer) obj;

		char a = golferName.charAt(0);
		char b = otherGolfer.golferName.charAt(0);

		if (a>b) return 1;
		if (a<b) return -1;
		return 0;
    }

*/

}