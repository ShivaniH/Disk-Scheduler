import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

class DiskScheduler extends JFrame
{
	Container c;
	DrawArea outputPanel;
	JLabel rangePrompt,startPrompt,endPrompt,inputPrompt,initialPrompt;
	JTextField startBox,endBox,reference,initialBox;
	JButton fcfs,sstf,scan,cScan,cLook;
	
	DiskScheduler()
	{
		//Frame initiallisations
		//this.setSize(2000,2000);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Disk Scheduler");
		this.setLocation(100,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setVisible(true);
		
		
		//Container
		c = getContentPane();
		c.setLayout(null);
		
		//Range prompt label
		rangePrompt = new JLabel("Please enter a range of track numbers : ");
		rangePrompt.setBounds(100,100,1000,20);
		c.add(rangePrompt);
	
		//range endpoint text boxes
		//Start
		startPrompt = new JLabel("Start : ");
		startPrompt.setBounds(100,125,100,20);
		c.add(startPrompt);
		startBox = new JTextField(5);
		startBox.setBounds(150,125,100,20);
		c.add(startBox);
		startBox.requestFocus();
		//End
		endPrompt = new JLabel("End : ");
		endPrompt.setBounds(300,125,100,20);
		c.add(endPrompt);
		//endPrompt.requestFocus();
		endBox = new JTextField(5);
		endBox.setBounds(350,125,100,20);
		c.add(endBox);
		endBox.requestFocus();
		
		
		//initial prompt label
		initialPrompt = new JLabel("Please enter the starting track : ");
		initialPrompt.setBounds(100,200,600,20);
		c.add(initialPrompt);
		
		//initial text box
		initialBox = new JTextField(5);
		initialBox.setBounds(300,200,100,20);
		c.add(initialBox);
		
		//Input prompt label
		inputPrompt = new JLabel("Please enter a referance String with commas as delimiters : ");
		inputPrompt.setBounds(100,250,1000,20);
		c.add(inputPrompt);
		//inputPrompt.requestFocus();
		
		
		//Reference string text box
		reference = new JTextField(50);
		reference.setBounds(100,270,550,20);
		c.add(reference);
		reference.requestFocus();
		
		GraphArtist drawer = new GraphArtist();
		//Buttons
		//FCFS
		fcfs = new JButton("FCFS");
		fcfs.setBounds(200,400,100,100);
		c.add(fcfs);
		fcfs.requestFocus();
		fcfs.addActionListener(	drawer );
		
		
		//SSTF
		sstf = new JButton("SSTF");
		sstf.setBounds(400,400,100,100);
		c.add(sstf);
		sstf.addActionListener(	drawer );
		//sstf.requestFocus();
		
		//SCAN
		scan = new JButton("SCAN");
		scan.setBounds(100,550,100,100);
		c.add(scan);
		scan.addActionListener(	drawer );
		//scan.requestFocus();
		
		//C-SCAN
		cScan = new JButton("C-SCAN");
		cScan.setBounds(300,550,100,100);
		c.add(cScan);
		cScan.addActionListener( drawer );
		//cScan.requestFocus();
		
		//C-LOOK
		cLook = new JButton("C-LOOK");
		cLook.setBounds(500,550,100,100);
		c.add(cLook);
		cLook.addActionListener( drawer );
		//cLook.requestFocus();
		
		
		
		//Graph output
		outputPanel = new DrawArea();
		//outputPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		outputPanel.setBounds(750,50,500,630);
		c.add(outputPanel);
		
		this.setVisible(true);
	}
	
	void output(ArrayList<Integer> arrangedString)
	{
		int start=0,end=0;
		try
		{
			start = Integer.parseInt(startBox.getText().trim());
		}
		catch(NumberFormatException e)
		{
			System.out.println("Please enter a proper start track number");//For now, console output. Later output on frame.
		}
		
		try
		{
			end = Integer.parseInt(endBox.getText().trim());
		}
		catch(NumberFormatException e)
		{
			System.out.println("Please enter a proper end track number");//For now, console output. Later output on frame.
		}
		
		outputPanel.setData(start,end,arrangedString);
		
		//outputPanel.drawGraph();
		outputPanel.triggered = true;
		//outputPanel.revalidate();
		this.repaint();
	}
	
	
	class GraphArtist implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			//READ STRING TO ARRAYLIST
			Scanner referenceScan = new Scanner(reference.getText());
			referenceScan.useDelimiter(",");
			ArrayList<Integer> references = new ArrayList<Integer>();
			while(referenceScan.hasNext())
				references.add(referenceScan.nextInt());
			
			
			int start=0,end=0;
			try
			{
				start = Integer.parseInt(startBox.getText().trim());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Please enter a proper start track number");//For now, console output. Later output on frame.
			}
	
			try
			{
				end = Integer.parseInt(endBox.getText().trim());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Please enter a proper end track number");//For now, console output. Later output on frame.
			}
			
			int initial=start;
			try
			{
				initial = Integer.parseInt(initialBox.getText().trim());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Please enter a proper end track number");//For now, console output. Later output on frame.
			}
			
			// REARRANGE 
			if(ae.getSource() == fcfs)
			{
				references.add(0,initial);
				DiskScheduler.this.outputPanel.totalTracks = Utility.totalTracks(references);
			}
			else if(ae.getSource() == sstf)
			{
				references = Algorithms.sstf(references,initial);
				DiskScheduler.this.outputPanel.totalTracks = Utility.totalTracks(references);
			}
			else
			{
				
				
				int i=references.size()-1, firstRequest,firstRequestLeft=start, firstRequestRight=end;
				String direction;
				while(i>=0)
				{
					if(Integer.parseInt(references.get(i).toString())<=initial) {
						firstRequestLeft=Integer.parseInt(references.get(i).toString());
						break;
					}
					--i;
				}
				i=0;
				while(i<=references.size())
				{
					if(Integer.parseInt(references.get(i).toString())>=initial) {
						firstRequestRight=Integer.parseInt(references.get(i).toString());
						break;
					}
					++i;
				}		
				if((initial-firstRequestLeft)<(firstRequestRight-initial)) {
					firstRequest=firstRequestLeft;
					direction="left";
				}			
				else {
					firstRequest=firstRequestRight;
					direction="right";
				}		
				
				references.sort(null);
				if(ae.getSource() == scan)
				{
					System.out.print("Before : ");
					references.forEach(num->System.out.print(" "+num));
					System.out.println();
					
					references = Algorithms.scan(references,initial,start,end,firstRequest,direction); 
					
					System.out.println("After : ");
					references.forEach(num->System.out.print(" "+num));
					System.out.println();
					
					DiskScheduler.this.outputPanel.totalTracks = Utility.totalTracks(references);
					/* if(DiskScheduler.this.outputPanel.totalTracks > (end - start))
						DiskScheduler.this.outputPanel.totalTracks -= (end - start); */
				}
				else if(ae.getSource() == cScan)
				{
					//references.sort(null);
					references = Algorithms.cScan(references,initial,start,end,firstRequest,direction); 
					
					DiskScheduler.this.outputPanel.totalTracks = Utility.totalTracks(references);
					if(DiskScheduler.this.outputPanel.totalTracks > (end - start))
						DiskScheduler.this.outputPanel.totalTracks -= (end - start);
				}
				else if(ae.getSource() == cLook)
				{
					references = Algorithms.cLook(references,initial,start,end,firstRequest,direction); 
					
					DiskScheduler.this.outputPanel.totalTracks = Utility.totalTracks(references);
					/* if(DiskScheduler.this.outputPanel.totalTracks > (end - start))
						DiskScheduler.this.outputPanel.totalTracks -= (end - start); */
				}
			}	
			
			//TODO DRAW
			DiskScheduler.this.output(references);
			
		}
	}
	
	
	public static void main(String args[])
	{
		new DiskScheduler();
	}
}