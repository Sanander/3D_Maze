import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class MazeProject extends JPanel implements KeyListener,MouseListener{

	static JFrame frame;
	char[][] mazeInfo;
	int sideLength;
	int playerRow=Integer.MAX_VALUE;
	int playerCol=Integer.MAX_VALUE;
	int endRow;
	int endCol;
	Image player;
	int pos=0;
	int moveCount=0;
	boolean endScreen=false;
	boolean threeD=false;

	public static void main (String [] args){
		new MazeProject();
	}

	public void setBoard() {
		String fileName="maze.txt";
		String line ="";
		try{
			player=ImageIO.read(new File("src/player.jpg"));
			//player=ImageIO.read(new File("player.jpg"));
			FileReader fileReader=new FileReader(fileName);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			line=bufferedReader.readLine();
			sideLength=line.length();

			mazeInfo=new char [sideLength][sideLength];
			mazeInfo[0]=line.toCharArray();
			int pos=1;

			while ((line=bufferedReader.readLine()) !=null) {
				mazeInfo[pos]=line.toCharArray();
				pos++;
			}

		}catch(Exception e){e.printStackTrace();};

	}

	public MazeProject(){
				setBoard();
				setBackground(Color.WHITE);
				frame=new JFrame();
				frame.add(this);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(1000,1000);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);
				frame.addKeyListener(this);
				this.addMouseListener(this);
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		if (threeD==true) {
			render3D(g2);
		}
		if (!threeD) {
		for (int i=0;i<sideLength;i++)
			for (int x=0;x<sideLength;x++) {
				if (!endScreen) {
						if (mazeInfo[i][x]=='0') {
							g2.setColor(Color.BLACK);
							g2.fillRect(x*((frame.getWidth()-50)/sideLength)+15, i*((frame.getHeight()-100)/sideLength), ((frame.getWidth()-50)/sideLength), ((frame.getHeight()-100)/sideLength));
						}
						else if (mazeInfo[i][x]=='S') {
							playerRow=i;
							playerCol=x;
							mazeInfo[i][x]='1';
							g2.setColor(Color.RED);
							g2.fillRect(x*((frame.getWidth()-50)/sideLength)+15, i*((frame.getHeight()-100)/sideLength), ((frame.getWidth()-50)/sideLength), ((frame.getHeight()-100)/sideLength));
						}
						else if (mazeInfo[i][x]=='E') {
							endRow=i;
							endCol=x;
							g2.setColor(Color.GREEN);
							g2.fillRect(x*((frame.getWidth()-50)/sideLength)+15, i*((frame.getHeight()-100)/sideLength), ((frame.getWidth()-50)/sideLength), ((frame.getHeight()-100)/sideLength));
						}
						else {
							g2.setColor(Color.WHITE);
							g2.fillRect(x*((frame.getWidth()-50)/sideLength)+15, i*((frame.getHeight()-100)/sideLength), ((frame.getWidth()-50)/sideLength), ((frame.getHeight()-100)/sideLength));
						}

						if (i==playerRow && x==playerCol) {
							g2.drawImage(player, x*((frame.getWidth()-50)/sideLength)+15, i*((frame.getHeight()-100)/sideLength),(frame.getWidth()-50)/sideLength,(frame.getHeight()-100)/sideLength,null);
						}
				}
			}
		}
				if (playerRow==endRow && playerCol==endCol) {
						this.removeAll();
						endScreen=true;
						g2.drawString("YOU ARE WIN! \n IN "+moveCount+" MOVES", frame.getWidth()/2-200, frame.getHeight()/2);
				}

	}


	public void render3D(Graphics2D g) {

		if (playerRow==Integer.MAX_VALUE) {
			for (int i=0;i<sideLength;i++)
				for (int x=0;x<sideLength;x++) {
						if (mazeInfo[i][x]=='S') {
							playerRow=i;
							playerCol=x;
							mazeInfo[i][x]='1';
						}
				}
		}

		if (pos==0) {
			
			int i=playerRow;
			double z=-.8;
			while (mazeInfo[i][playerCol]!='0') {
				i--;
				z++;
				if (i<0)
					break;
			}
		

			double [] corner= {-.5,.5,z};
			//true if wall filled {left wall,right}
			boolean[] wallFilled=new boolean[2];
			
			Cube cube=new Cube(new double[] {-.5,.5,(z+1)},new boolean[] {true},g,"back wall");
			for (int q=i+1;q<=playerRow;q++) {

				if (playerCol-1>0 && mazeInfo[q][playerCol-1]!='0') {
					wallFilled[0]=false;
					if (mazeInfo[q-1][playerCol-1]!='0') {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"none");
					}
					else {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"fillBack");
					}
				}
				else wallFilled[0]=true;
				
				if (playerCol+1<sideLength && mazeInfo[q][playerCol+1]!='0') {
					wallFilled[1]=false;
					if (mazeInfo[q-1][playerCol+1]!='0') {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"none");
					}
					else {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"fillBack");
					}
				}
					
				else wallFilled[1]=true;
				
				corner= new double[] {-.5,.5,(z)};	
				if (mazeInfo[q][playerCol]=='E') {
					cube=new Cube(corner,wallFilled,g,"end");
				}
				else if (mazeInfo[q][playerCol]=='S') {
					cube=new Cube(corner,wallFilled,g,"start");
				}
				else cube=new Cube(corner,wallFilled,g,"none");
				z--;
				
				if (z==1) {
					z=(-.8);
				}
				if (z<.00001) {
					break;
				}
					
			}

		}
		
		
		if (pos==2) {
			
			int i=playerRow;
			double z=-.8;
			while (mazeInfo[i][playerCol]!='0') {
				i++;
				z++;
				if (i>=sideLength)
					break;
			}

			double [] corner= {-.5,.5,z};
			//true if wall filled {left wall,top,right,bottom}
			boolean[] wallFilled=new boolean[2];
			
			Cube cube=new Cube(new double[] {-.5,.5,(z+1)},new boolean[] {true},g,"back wall");
			for (int q=i-1;q>=playerRow;q--) {
				if (playerCol-1>0 && mazeInfo[q][playerCol-1]!='0') {
					wallFilled[0]=false;
					if (mazeInfo[q+1][playerCol-1]!='0') {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"none");
					}
					else {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"fillBack");
					}
				}
				else wallFilled[0]=true;
				
				if (playerCol+1<sideLength && mazeInfo[q][playerCol+1]!='0') {
					wallFilled[1]=false;
					if (mazeInfo[q+1][playerCol+1]!='0') {
						cube=new Cube(new double[] {.5,.5,z},new boolean [] {false,true},g,"none");
					}
					else {
						cube=new Cube(new double[] {.5,.5,z},new boolean [] {false,true},g,"fillBack");
					}
				}
				else wallFilled[1]=true;
				
				corner= new double[] {-.5,.5,(z)};	
				if (mazeInfo[q][playerCol]=='E') {
					cube=new Cube(corner,wallFilled,g,"end");
				}
				else if (mazeInfo[playerRow][q]=='S') {
					cube=new Cube(corner,wallFilled,g,"start");
				}
				else cube=new Cube(corner,wallFilled,g,"none");
				z--;
				
				if (z==1) {
					z=(-.8);
				}
				if (z<.00001) {
					break;
				}
					
			}

		}
		
		if (pos==1) {
			
			int i=playerCol;
			double z=-.8;
			while (mazeInfo[playerRow][i]!='0') {
				i++;
				z++;
				if (i>=sideLength)
					break;
			}
			double [] corner= {-.5,.5,z};
			//true if wall filled {left wall,top,right,bottom}
			boolean[] wallFilled=new boolean[2];
			
			Cube cube=new Cube(new double[] {-.5,.5,(z+1)},new boolean[] {true},g,"back wall");
			for (int q=i-1;q>=playerCol;q--) {
				if (playerRow-1>0 && mazeInfo[playerRow-1][q]!='0') {
					wallFilled[0]=false;

					if (mazeInfo[playerRow-1][q+1]!='0' && mazeInfo[q+1][playerRow-1]!='0') {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {false,false},g,"none");
					}
					else {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {false,false},g,"fillBack");
					}
				}	
				else wallFilled[0]=true;
				
				if (playerRow+1<sideLength && mazeInfo[playerRow+1][q]!='0') {
					wallFilled[1]=false;
					if (mazeInfo[playerRow+1][q+1]!='0') {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"none");
					}
					else {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"fillBack");
					}
				}
				else wallFilled[1]=true;
				
				corner= new double[] {-.5,.5,(z)};	
				if (mazeInfo[playerRow][q]=='E') {
					cube=new Cube(corner,wallFilled,g,"end");
				}
				else if (mazeInfo[playerRow][q]=='S') {
					cube=new Cube(corner,wallFilled,g,"start");
				}
				else cube=new Cube(corner,wallFilled,g,"none");
				z--;
				
				if (z==1) {
					z=(-.8);
				}
				if (z<.00001) {
					break;
				}
					
			}
			

		}
		
		
		if (pos==3) {
			
			int i=playerCol;
			double z=-.8;
			while (mazeInfo[playerRow][i]!='0') {
				i--;
				z++;
				if (i<0)
					break;
			}

			double [] corner= {-.5,.5,z};
			//true if wall filled {left wall,top,right,bottom}
			boolean[] wallFilled=new boolean[2];
			
			Cube cube=new Cube(new double[] {-.5,.5,(z+1)},new boolean[] {true},g,"back wall");
			for (int q=i+1;q<=playerCol;q++) {
				if (playerRow-1>0 && mazeInfo[playerRow-1][q]!='0') {
					wallFilled[1]=false;
					
					if (mazeInfo[playerRow-1][q-1]!='0' && mazeInfo[q-1][playerCol-1]!='0') {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"none");
					}
					else {
						cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,true},g,"fillBack");
					}
					cube=new Cube(new double[] {0.5,.5,z},new boolean [] {false,false},g,"none");
				}
				else wallFilled[1]=true;
				
				if (playerRow+1<sideLength && mazeInfo[playerRow+1][q]!='0') {
					wallFilled[0]=false;
					if (mazeInfo[playerRow+1][q-1]!='0' && mazeInfo[q+1][playerCol-1]!='0') {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"none");
					}
					else {
						cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {true,false},g,"fillBack");
					}
					cube=new Cube(new double[] {-1.5,.5,z},new boolean [] {false,false},g,"none");
				}
				else wallFilled[0]=true;
				
				corner= new double[] {-.5,.5,(z)};
				if (mazeInfo[playerRow][q]=='E') {
					cube=new Cube(corner,wallFilled,g,"end");
				}
				else if (mazeInfo[playerRow][q]=='S') {
					cube=new Cube(corner,wallFilled,g,"start");
				}
				else cube=new Cube(corner,wallFilled,g,"none");
				z--;
				
				if (z==1) {
					z=(-.8);
				}
				if (z<.00001) {
					break;
				}
					
			}
			

		}
		
		
		Image direction = null;
		try {
			if (pos==0)
				direction=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\player.jpg"));
			else if (pos==1)
				direction=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerRight.jpg"));
			else if (pos==2) {
				direction=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerDown.jpg"));
			}
			else if (pos==3)
				direction=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerLeft.jpg"));

		}catch(Exception e) {System.out.println("err");};
		
		
		g.setFont(new Font("Papyrus",Font.PLAIN,32));
		g.drawString("SPACE=MAP", frame.getWidth()-400, 90);
		
			g.drawImage(direction, frame.getWidth()-300, 150,100,100,null);
		

	}


	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			if (threeD) {
				threeD=false;
				setBackground(Color.WHITE);
			}
			else if(!threeD) {
				threeD=true;
				setBackground(Color.CYAN);
			}
			
		}
		
		if (e.getKeyCode()==KeyEvent.VK_UP){
			moveCount++;
			 if (pos==0 && mazeInfo[playerRow-1][playerCol]!='0')
				 playerRow-=1;
			 else if (pos==1 && mazeInfo[playerRow][playerCol+1]!='0') {
				 playerCol+=1;
			 }
			 else if (pos==2 && mazeInfo[playerRow+1][playerCol]!='0')
				 playerRow+=1;
			 else if (pos==3 && mazeInfo[playerRow][playerCol-1]!='0')
				 playerCol-=1;

			 if (playerRow==endRow && playerCol==endCol && !endScreen) {
					//paint(this.getGraphics());
				 repaint();
			 }
		}

		else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			if (pos==0) {
				pos=3;
				try {
					player=ImageIO.read(new File("src/playerLeft.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerLeft.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==3) {
				pos=2;
				try {
					player=ImageIO.read(new File("src/playerDown.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerDown.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==2 && threeD) {
				pos=3;
				try {
					player=ImageIO.read(new File("src/playerRight.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerLeft.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==2) {
				pos=1;
				try {
					player=ImageIO.read(new File("src/playerRight.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerRight.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==1) {
				pos=0;
				try {
					player=ImageIO.read(new File("src/player.jpg"));
				    //player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\player.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}


		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if (pos==0) {
				pos=1;
				try {
					player=ImageIO.read(new File("src/playerRight.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerRight.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==1) {
				pos=2;
				try {
					player=ImageIO.read(new File("src/playerDown.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerDown.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==2 && threeD) {
				pos=1;
				try {
					player=ImageIO.read(new File("src/playerLeft.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerRight.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (pos==2) {
				pos=3;
				try {
					player=ImageIO.read(new File("src/playerLeft.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\playerLeft.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if(pos==3) {
				pos=0;
				try {
					player=ImageIO.read(new File("src/player.jpg"));
					//player=ImageIO.read(new File("C:\\Users\\Alex\\eclipse\\workspace\\MazeProject\\src\\player.jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}


		}
	paint(this.getGraphics());
	}

	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}

}

