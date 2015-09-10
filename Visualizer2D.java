import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Gurkan Solmaz
 *
 */
public class Visualizer2D implements KeyListener{
	// GUI Data
	public JFrame frame; 
	public Canvas area2D; 
	private int width; // the width of the 2D area
	private int height; // the height of the 2D area
	private int nodeRadius;
	public boolean case1;
	private int sdCount=0;

	Calendar keyTimeFlag;
	
	
	// constructor
	public Visualizer2D(double dimensionLength) {
		this.width=(int) (dimensionLength/1.5);
		this.height=(int)(dimensionLength/1.5);
		this.nodeRadius = 5;
		this.case1=false;

		configureGUI();
	}


	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public Canvas getArea2D() {
		return area2D;
	}


	public void setArea2D(Canvas area2d) {
		area2D = area2d;
	}


	private void configureGUI()
	{
		// Create the window object
		frame = new JFrame("MANET Simulation - Network Optimization Project - Fall2011");
		frame.setSize(width+50, height+50);
		frame.setResizable(false);
		
		// The program should end when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the window's layout manager
		frame.setLayout(new FlowLayout());
		
		// set area
		createArea();
		
		// Make the frame listen to keystrokes
		frame.addKeyListener(this);
		frame.setVisible(true);

	}
	
	public void draw(boolean[][] adjMatrix, List<SDSession> sdSessionList, List<MobileNode> mobileNodeList, List<List<Integer>> pathSDList){
		Graphics g = area2D.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLUE);
		Font font = new Font ("Monospaced", Font.BOLD , 14);
		g.setFont(font);
		// draw nodes
		for(int i=0; i<mobileNodeList.size(); i++){
			g.drawOval((int)Math.floor(mobileNodeList.get(i).getCurrentX()/1.5-nodeRadius),(int)Math.floor((int)mobileNodeList.get(i).getCurrentY()/1.5 - nodeRadius), nodeRadius*2, nodeRadius*2);
		}	
		g.setColor(Color.GRAY);
		// draw the edges
		for(int i=0; i<mobileNodeList.size(); i++){
			for(int j=0; j<mobileNodeList.size();j++){
				if(i>=j && adjMatrix[i][j]){
					g.drawLine((int)Math.floor(mobileNodeList.get(i).getCurrentX()/1.5), (int)(Math.floor(mobileNodeList.get(i).getCurrentY()/1.5)),
							(int)Math.floor(mobileNodeList.get(j).getCurrentX()/1.5),(int)(Math.floor( mobileNodeList.get(j).getCurrentY())/1.5));
				}
			}
		}
		// draw sd paths
		g.setColor(Color.RED);
		for(int i=0; i<pathSDList.size(); i++){
			List<Integer> tmpSDPath = pathSDList.get(i);
			if(tmpSDPath==null){
				continue;
			}
			for(int j=0;j<tmpSDPath.size()-1;j++){
				int a = tmpSDPath.get(j);
				int b =tmpSDPath.get(j+1);
				g.setColor(Color.RED);
				g.drawLine((int)Math.floor(mobileNodeList.get(a).getCurrentX()/1.5)+2, (int)Math.floor(mobileNodeList.get(a).getCurrentY()/1.5)+2,
						(int)Math.floor(mobileNodeList.get(b).getCurrentX()/1.5)+2,(int)Math.floor( mobileNodeList.get(b).getCurrentY()/1.5)+2);
			}
		}
		
		//draw sources and destinations
		for(int i=0; i<sdSessionList.size();i++){
		
			SDSession tmpSD = sdSessionList.get(i);
			g.setColor(Color.GREEN);
			g.drawString("S" + (i+1), (int)Math.floor(mobileNodeList.get(tmpSD.getSourceVertex()).getCurrentX()/1.5+4), 
					(int)Math.floor(mobileNodeList.get(tmpSD.getSourceVertex()).getCurrentY()/1.5)+i*3);
		
			g.setColor(Color.MAGENTA);
			g.drawString("D" + (i+1), (int)Math.floor(mobileNodeList.get(tmpSD.getDestinationVertex()).getCurrentX()/1.5+4), 
					(int)Math.floor(mobileNodeList.get(tmpSD.getDestinationVertex()).getCurrentY()/1.5)+i*3);
			sdCount++;
		
		}
	}
	
	public void createArea(){
		// Create the play area
		area2D = new Canvas();
		area2D.setSize(width, height);
		area2D.setBackground(Color.WHITE);
		area2D.setFocusable(false);
		frame.add(area2D);
	}
	
	
	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		if(key.getKeyCode() == KeyEvent.VK_UP){
			this.case1 = true;
			System.out.println("key pressed!");
		}
	
	}
	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub

	}
	


	public boolean isCase1() {
		return case1;
	}


	public void setCase1(boolean case1) {
		this.case1 = case1;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
