import java.util.ArrayList;

class Algorithms
{
	static ArrayList<Integer> sstf(ArrayList<Integer> numbers, int start)
	{
		ArrayList<Integer> numbers2 = new ArrayList<Integer>();
		numbers2.add(start);
		//int[] nums = numbers.toArray(new int[numbers.size()]);
		int previous = start;
		while( !numbers.isEmpty() )
		{
			int min = Math.abs( previous - numbers.get(0) );
			int position = 0;
			for(int i = 1; i < numbers.size(); i++)
				if( Math.abs(previous-numbers.get(i)) < min)
				{
					min = Math.abs(previous-numbers.get(i));
					position = i;
				}
			
			numbers2.add(numbers.get(position));
			previous = numbers.get(position);
			numbers.remove(position);
		}
		
		return numbers2;
	}
	
	/*
	FirstRequest is the first one that is supposed to be serviced.
	diskQueue and dQueue are the disk queue.
	headMovement keeps track of the disk head's movement.
	goLeft --> If the initial position of the disk head is closer to the request to its left.
	goRight --> If the initial position of the disk head is closer to the request to its right.	
	*/
	public static ArrayList<Integer> scan(ArrayList<Integer> dQueue,int diskHead,int firstCylinder,int lastCylinder,int firstRequest, String direction) {

		int headMovement=0;
		ArrayList<Integer> diskQueue=new ArrayList<Integer>();
		diskQueue.addAll(dQueue);	//copying it to scan's own diskQueue, so that the original one is not modified.
		int i=diskQueue.indexOf(firstRequest);
		ArrayList<Integer> drawPoints = new ArrayList<Integer>();
		drawPoints.add(diskHead);
 		
		if(direction.compareTo("left")==0) {

			while(i>=0)
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskHead-diskQueue.get(i));
				diskHead=diskQueue.get(i);
				--i;
			}
			drawPoints.add(firstCylinder);
			headMovement+=(diskHead-firstCylinder);
			diskHead=firstCylinder;
			i=diskQueue.indexOf(firstRequest)+1;
			while(i<diskQueue.size())
			{
				int request=diskQueue.get(i);
				drawPoints.add(request);
				headMovement+=(request-diskHead);
				diskHead=request;
				++i;
			}
		}
		else {

			while(i<diskQueue.size())
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskQueue.get(i)-diskHead);
				diskHead=diskQueue.remove(i);
			}
			drawPoints.add(lastCylinder);
			headMovement+=(lastCylinder-diskHead);
			diskHead=lastCylinder;
			while(!diskQueue.isEmpty())
			{
				int request=diskQueue.remove(diskQueue.size()-1);
				drawPoints.add(request);
				headMovement+=(diskHead-request);
				diskHead=request;
			}
		}
				
		System.out.println("Total head movement = "+headMovement);
		return(drawPoints);
	}
	
	//C-SCAN starts here
	
	public static ArrayList<Integer> cScan(ArrayList<Integer> dQueue,int diskHead,int firstCylinder,int lastCylinder, int firstRequest, String direction) {
		int headMovement=0;
		ArrayList<Integer> diskQueue=new ArrayList<Integer>();
		diskQueue.addAll(dQueue);
		int i=diskQueue.indexOf(firstRequest);
		ArrayList<Integer> drawPoints = new ArrayList<Integer>();
		drawPoints.add(diskHead);
		
		if(direction.compareTo("left")==0) {
			
			while(i>=0)
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskHead-diskQueue.get(i));
				diskHead=diskQueue.get(i);
				--i;
			}
			drawPoints.add(firstCylinder);
			drawPoints.add(lastCylinder);
			headMovement+=(diskHead-firstCylinder);
			headMovement+=lastCylinder;
			diskHead=lastCylinder;
			i=diskQueue.size()-1;
			while(i>diskQueue.indexOf(firstRequest))
			{
				int request=diskQueue.get(i);
				drawPoints.add(request);
				headMovement+=(diskHead-request);
				diskHead=request;
				--i;
			}
		}
		else {
			
			while(i<diskQueue.size())
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskQueue.get(i)-diskHead);
				diskHead=Integer.parseInt(diskQueue.remove(i).toString());
			}
			drawPoints.add(lastCylinder);
			drawPoints.add(firstCylinder);
			headMovement+=(lastCylinder-diskHead);
			headMovement+=lastCylinder;
			diskHead=firstCylinder;
		
			while(!diskQueue.isEmpty())
			{
				int request=diskQueue.remove(0);
				drawPoints.add(request);
				headMovement+=(request-diskHead);
				diskHead=request;
			}
		}
		
		//System.out.println("Total head movement = "+headMovement);
		return(drawPoints);
	}
	
	//C-LOOK starts here
	
	public static ArrayList<Integer> cLook(ArrayList<Integer> dQueue,int diskHead,int firstCylinder, int lastCylinder, int firstRequest, String direction) {
		int headMovement=0;
		ArrayList<Integer> diskQueue=new ArrayList<Integer>();
		diskQueue.addAll(dQueue);
		int i=diskQueue.indexOf(firstRequest);
		ArrayList<Integer> drawPoints = new ArrayList<Integer>();
		drawPoints.add(diskHead);
		
		if(direction.compareTo("left")==0) {

			while(i>=0)
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskHead-diskQueue.get(i));
				diskHead=diskQueue.get(i);
				--i;
			}
			i=diskQueue.size()-1;
			headMovement+=(diskQueue.get(i)-diskHead);			
			diskHead=diskQueue.get(i);
			while(i>diskQueue.indexOf(firstRequest))
			{
				int request=diskQueue.get(i);
				drawPoints.add(request);
				headMovement+=(diskHead-request);
				diskHead=request;
				--i;
			}
		}
		else {

			while(i<diskQueue.size())
			{
				drawPoints.add(diskQueue.get(i));
				headMovement+=(diskQueue.get(i)-diskHead);
				diskHead=diskQueue.remove(i);
			}
			int count=0;
			headMovement+=(diskHead-diskQueue.get(0));
			while(!diskQueue.isEmpty())
			{
				int request=diskQueue.remove(0);
				drawPoints.add(request);
				if(count!=0) headMovement+=(Math.abs(diskHead-request));
				diskHead=request;
				++count;
			}
		}

		System.out.println("Total head movement = "+headMovement);
		return(drawPoints);
	}
}