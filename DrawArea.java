import javax.swing.*;
import java.awt.*;
import java.util.*;

class DrawArea extends JPanel
{
	Rectangle graphArea = new Rectangle(10,20,480,570);//x,y,w,h
	boolean rangeReady,labelsAdded,triggered;
	private int start,end;
	JLabel startLbl,endLbl;
	static final Dimension numberSize = new Dimension(20,10);
	ArrayList<Integer> numbers = new ArrayList<Integer>();
	Point speed;	//Pint beause couldnt find a MAthVector class
	int totalTracks;
	
	@Override
	public void paintComponent(Graphics g)
	{
		
		g.setColor(Color.WHITE);
		g.fillRect((int)graphArea.getX(),(int)graphArea.getY(),(int)graphArea.getWidth(),(int)graphArea.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect((int)graphArea.getX(),(int)graphArea.getY(),(int)graphArea.getWidth(),(int)graphArea.getHeight());
		
		if(rangeReady)
		{
			startLbl = new JLabel(Integer.toString(start));
			startLbl.setBounds((int)graphArea.getX(),(int)(graphArea.getY()-numberSize.getHeight()),(int)numberSize.getWidth(),(int)numberSize.getHeight());
			//this.add(startLbl);
			
			//Forget labels. drawing String instead
			g.drawString(Integer.toString(start),(int)graphArea.getX(),(int)(graphArea.getY()-numberSize.getHeight()));
			
			endLbl = new JLabel(Integer.toString(end));
			endLbl.setBounds(
				(int)(graphArea.getX() + graphArea.getWidth() - numberSize.getWidth()),	//X
				(int)(graphArea.getY() - numberSize.getHeight()),	//Y
				(int)numberSize.getWidth(),(int)numberSize.getHeight()	//Width, Height
				);
			//this.add(endLbl);
			g.drawString(
				Integer.toString(end),
				(int)(graphArea.getX() + graphArea.getWidth() - numberSize.getWidth()),	//X
				(int)(graphArea.getY() - numberSize.getHeight()));	//Y
			
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			labels.add(startLbl);
			labels.add(endLbl);
			//this.addLabels(labels);
		}
		
		//int totalTraverse = ;
		if(triggered)
		{
			speed = new Point(	(int)graphArea.getWidth()/(end-start)	,	(int)graphArea.getHeight()/numbers.size()	);
			if(speed.getX() == 0)	speed.move(1,(int)speed.getY());
			if(speed.getY() == 0)	speed.move((int)speed.getX(),1);
			
			int firstX,secondX=0,firstY,secondY=0;
			Iterator i = numbers.iterator();
			if(i.hasNext())
			{
				secondX = (int)graphArea.getX() +(int)(((Integer)i.next() / (double)(end-start)) * graphArea.getWidth());
				secondY = (int)graphArea.getY();
			}	
			System.out.println("Recieved start point : " + secondX + " , " + secondY);
			System.out.println("Second point is at : " + numbers.get(1));
			while(i.hasNext())
			{
				firstX = secondX;
				secondX = (int)graphArea.getX() + (int)(((Integer)i.next() / (double)(end-start)) * graphArea.getWidth());
			
				firstY = secondY;
				secondY += (int)speed.getY(); //* (Math.abs(secondX-firstX) / (int)speed.getX());	//yspeed * time = yspeed * (xdist/xspeed)
			
				System.out.println("Recieved next point : " + secondX + " , " + secondY);
			
				g.drawLine(firstX,firstY,secondX,secondY);
				
				g.setColor(Color.RED);
				g.drawArc(secondX,secondY,2,2,0,360);
				g.setColor(Color.BLACK);
				
				//This doesn't work;
				//try{	Thread.sleep(5);	}catch(InterruptedException e){}
			}
			
			g.drawString("Total number of Tracks covered : "+totalTracks,(int)graphArea.getX(),(int)(graphArea.getY()+graphArea.getHeight())+30);
			
			triggered = false;
		}
	}
	
	public void setRange(int start,int end)
	{
		this.start = start;
		this.end = end;
		rangeReady = true;
	}
	
	public void setData(int start, int end, ArrayList<Integer> numbers)
	{
		setRange(start,end);
		this.numbers = numbers;
	}
	
	private void addLabels(ArrayList<JLabel> labels)
	{
		if(labelsAdded)	return;
		
		for(JLabel text : labels)
			this.add(text);
		
		labelsAdded = true;
	}
}