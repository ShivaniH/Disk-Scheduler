import java.util.*;

class Utility
{
	/// This function assumes the starting point is fed into the ArrayList
	public static int totalTracks(ArrayList<Integer> numbers)
	{
		int sum = 0;
		int first,second=0;
		
		Iterator i = numbers.iterator();
		if(i.hasNext())	second = (Integer)i.next();
		
		while(i.hasNext())
		{
			first = second;
			second = (Integer)i.next();
			
			sum += Math.abs(first - second);
		}
		
		return sum;
	}
}