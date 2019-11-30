import java.awt.Color;
import java.awt.Graphics2D;

public class Cube{
	
	public Cube(double[] corner, boolean[] wallFilled, Graphics2D g2, String specialCase) {
			
		if (specialCase.equals("back wall")) {
			double [] bottomLeft=corner;
			double [] topLeft={corner[0],corner[1]-1,corner[2]};
			double [] bottomRight={corner[0]+1,corner[1],corner[2]};
			double [] topRight={corner[0]+1,corner[1]-1,corner[2]};
			int [] xPoly={(int)(bottomLeft[0]/bottomLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topLeft[0]/topLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topRight[0]/topRight[2]*1000+MazeProject.frame.getWidth()/2),(int)(bottomRight[0]/bottomRight[2]*1000+MazeProject.frame.getWidth()/2)};
			int [] yPoly={(int)(bottomLeft[1]/bottomLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topLeft[1]/topLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topRight[1]/topRight[2]*1000+MazeProject.frame.getHeight()/2),(int)(bottomRight[1]/bottomRight[2]*1000+MazeProject.frame.getHeight()/2)};
			g2.setColor(Color.CYAN);
			g2.fillPolygon(xPoly,yPoly,4);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(xPoly,yPoly,4);
			
		}else {
		if (specialCase.equals("fillBack")) {
			double [] bottomLeft={corner[0],corner[1],corner[2]+1};
			double [] topLeft={corner[0],corner[1]-1,corner[2]+1};
			double [] bottomRight={corner[0]+1,corner[1],corner[2]+1};
			double [] topRight={corner[0]+1,corner[1]-1,corner[2]+1};
			int [] xPoly={(int)(bottomLeft[0]/bottomLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topLeft[0]/topLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topRight[0]/topRight[2]*1000+MazeProject.frame.getWidth()/2),(int)(bottomRight[0]/bottomRight[2]*1000+MazeProject.frame.getWidth()/2)};
			int [] yPoly={(int)(bottomLeft[1]/bottomLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topLeft[1]/topLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topRight[1]/topRight[2]*1000+MazeProject.frame.getHeight()/2),(int)(bottomRight[1]/bottomRight[2]*1000+MazeProject.frame.getHeight()/2)};
			//System.out.println(yPoly[0]+" "+ yPoly[1]+" "+ yPoly[2]+" "+yPoly[3]);
			g2.setColor(Color.CYAN);
			g2.fillPolygon(xPoly,yPoly,4);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(xPoly,yPoly,4);
		}
	//FLOOR	
		double [] bottomFrontLeft=corner;
		double [] bottomBackLeft={corner[0],corner[1],corner[2]+1};
		double [] bottomFrontRight={corner[0]+1,corner[1],corner[2]};
		double [] bottomBackRight={corner[0]+1,corner[1],corner[2]+1};
		int [] xPoly={(int)(bottomFrontLeft[0]/bottomFrontLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(bottomBackLeft[0]/bottomBackLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(bottomBackRight[0]/bottomBackRight[2]*1000+MazeProject.frame.getWidth()/2),(int)(bottomFrontRight[0]/bottomFrontRight[2]*1000+MazeProject.frame.getWidth()/2)};
		int [] yPoly={(int)(bottomFrontLeft[1]/bottomFrontLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(bottomBackLeft[1]/bottomBackLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(bottomBackRight[1]/bottomBackRight[2]*1000+MazeProject.frame.getHeight()/2),(int)(bottomFrontRight[1]/bottomFrontRight[2]*1000+MazeProject.frame.getHeight()/2)};
		System.out.println(specialCase);
		if (specialCase.equals("end")) { 
			g2.setColor(Color.PINK);
		}
		else if (specialCase.equals("start")) {
			g2.setColor(Color.ORANGE);
		}
		else if (!specialCase.equals("end") && !specialCase.equals("start")){
			g2.setColor(Color.GREEN);
		}
		g2.fillPolygon(xPoly,yPoly,4);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoly,yPoly,4);

		
	//CEILING
		double [] topFrontLeft= {corner[0],corner[1]-1,corner[2]+1};
		double [] topBackLeft={corner[0],corner[1]-1,corner[2]};
		double [] topFrontRight={corner[0]+1,corner[1]-1,corner[2]+1};
		double [] topBackRight={corner[0]+1,corner[1]-1,corner[2]};
		xPoly=new int[] {(int)(topFrontLeft[0]/topFrontLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topBackLeft[0]/topBackLeft[2]*1000+MazeProject.frame.getWidth()/2),(int)(topBackRight[0]/topBackRight[2]*1000+MazeProject.frame.getWidth()/2),(int)(topFrontRight[0]/topFrontRight[2]*1000+MazeProject.frame.getWidth()/2)};
		yPoly=new int[] {(int)(topFrontLeft[1]/topFrontLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topBackLeft[1]/topBackLeft[2]*1000+MazeProject.frame.getHeight()/2),(int)(topBackRight[1]/topBackRight[2]*1000+MazeProject.frame.getHeight()/2),(int)(topFrontRight[1]/topFrontRight[2]*1000+MazeProject.frame.getHeight()/2)};
		g2.setColor(Color.GREEN);
		g2.fillPolygon(xPoly,yPoly,4);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoly,yPoly,4);
		
	//LEFT WALL
		double [] leftFrontBottom= corner;
		double [] leftFrontTop=topFrontLeft;
		double [] leftBackBottom=bottomBackLeft;
		double [] leftBackTop=topBackLeft;
		xPoly=new int[] {(int)(leftFrontTop[0]/leftFrontTop[2]*1000+MazeProject.frame.getWidth()/2),(int)(leftBackTop[0]/leftBackTop[2]*1000+MazeProject.frame.getWidth()/2),(int)(leftFrontBottom[0]/leftFrontBottom[2]*1000+MazeProject.frame.getWidth()/2),(int)(leftBackBottom[0]/leftBackBottom[2]*1000+MazeProject.frame.getWidth()/2)};
		yPoly=new int[] {(int)(leftFrontTop[1]/leftFrontTop[2]*1000+MazeProject.frame.getHeight()/2),(int)(leftBackTop[1]/leftBackTop[2]*1000+MazeProject.frame.getHeight()/2),(int)(leftFrontBottom[1]/leftFrontBottom[2]*1000+MazeProject.frame.getHeight()/2),(int)(leftBackBottom[1]/leftBackBottom[2]*1000+MazeProject.frame.getHeight()/2)};
		if (wallFilled[0]==true) {
			g2.setColor(Color.CYAN);	
			g2.fillPolygon(xPoly,yPoly,4);
		}
		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoly,yPoly,4);
		
	//RIGHT WALL
		double [] rightFrontBottom= bottomFrontRight;
		double [] rightFrontTop=topFrontRight;
		double [] rightBackBottom=bottomBackRight;
		double [] rightBackTop=topBackRight;
		
		xPoly=new int[] {(int)(rightFrontTop[0]/rightFrontTop[2]*1000+MazeProject.frame.getWidth()/2),(int)(rightBackTop[0]/rightBackTop[2]*1000+MazeProject.frame.getWidth()/2),(int)(rightFrontBottom[0]/rightFrontBottom[2]*1000+MazeProject.frame.getWidth()/2),(int)(rightBackBottom[0]/rightBackBottom[2]*1000+MazeProject.frame.getWidth()/2)};
		yPoly=new int[] {(int)(rightFrontTop[1]/rightFrontTop[2]*1000+MazeProject.frame.getHeight()/2),(int)(rightBackTop[1]/rightBackTop[2]*1000+MazeProject.frame.getHeight()/2),(int)(rightFrontBottom[1]/rightFrontBottom[2]*1000+MazeProject.frame.getHeight()/2),(int)(rightBackBottom[1]/rightBackBottom[2]*1000+MazeProject.frame.getHeight()/2)};
		
		if (wallFilled[1]==true) {
			g2.setColor(Color.CYAN);	
			g2.fillPolygon(xPoly,yPoly,4);
		}
		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoly,yPoly,4);
		
		}
	}
	
}
