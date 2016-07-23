package applogic.collision;

import java.awt.Point;
import java.awt.Rectangle;

public class Collision implements ICollision{
	
	private DoublePoint[] eightPoints;
	
	private Line[] staticLines;
	private Line moverLine;
	
	private DoublePoint[] moverpositions;
	
	private ILineCounter lineCounter;
	
	Line[] line = new Line[2];

	private Line[] lines;

	private DoublePoint[] collidedPoints;
	
	public Collision() {
		
		collidedPoints = new DoublePoint[4];
		
		this.lineCounter = new LineCounter();
		moverpositions = new DoublePoint[2];
		
		for(int i=0;i<moverpositions.length;i++){
			moverpositions[i] = new DoublePoint();
		}
		
		staticLines = new Line[4];
		for(int i=0;i<staticLines.length;i++){
			staticLines[i] = new Line(null, null, null);
		}
		
		
		eightPoints = new DoublePoint[4];
		for(int i=0;i<eightPoints.length;i++){
			eightPoints[i] = new DoublePoint();
		}
		
		moverLine = new Line(null, null,null);
	}
	
	@Override
	public DoublePoint newLocation(Rectangle mover,Rectangle staticElement, DoublePoint moverDestination){
		
		eightPoints[0].setLocation(staticElement.x - mover.width, staticElement.y - mover.height);/*balfelsõ*/
		eightPoints[1].setLocation(staticElement.x + staticElement.width, staticElement.y - mover.height);/*jobbfelsõ*/
		eightPoints[2].setLocation(staticElement.x + staticElement.width, staticElement.y + staticElement.height);/*jobbalsó*/
		eightPoints[3].setLocation(staticElement.x - mover.width, staticElement.y + staticElement.height);/*balalsó*/
		
		/*Statikus vonalak adatai*/
		staticLines[0].setP1(eightPoints[1]).setP2(eightPoints[2]).setSide(LineSide.RIGHT);/*jobboldali line*/
		staticLines[1].setP1(eightPoints[3]).setP2(eightPoints[2]).setSide(LineSide.DOWN);/*alsó*/
		staticLines[2].setP1(eightPoints[0]).setP2(eightPoints[3]).setSide(LineSide.LEFT);;/*baloldali*/
		staticLines[3].setP1(eightPoints[0]).setP2(eightPoints[1]).setSide(LineSide.TOP);;/*felsõ*/
		
		moverpositions[0].setLocation(mover.x,mover.y);
		moverpositions[1].setLocation(moverDestination.getX(), moverDestination.getY());
		
		moverLine.setP1(moverpositions[0]).setP2(moverpositions[1]);
		
		
		/*Azok a vonalak, melyek az ütközésnél.*/
		lines = mustCheckLines();
		
		for(int i=0;i<lines.length;i++){
			
			if(lines[i] != null){
				
				for(int j=0;j<collidedPoints.length;j++){
					collidedPoints[j] = new DoublePoint();
				}
				
				collidedPoints[0] = moverpositions[0];
				collidedPoints[1] = moverpositions[1];
				
				collidedPoints[2] = lines[i].getP1();
				collidedPoints[3] = lines[i].getP2();
				if(lineCounter.twoLinesCollided(collidedPoints)){
					
					for(int k = 0;k<4;k++){
						Rectangle rect = new Rectangle((int)eightPoints[0].getX(), (int)eightPoints[0].getY(), (int)eightPoints[1].getX() - (int)eightPoints[0].getX(), (int)eightPoints[3].getY() - (int)eightPoints[0].getY());
						Point po = new Point((int)moverpositions[1].getX(), (int)moverpositions[1].getY());
						DoublePoint newpoint = new DoublePoint();
						if((moverpositions[0].getX() == eightPoints[k].getX() && moverpositions[0].getY() == eightPoints[k].getY()) && !rect.contains(po)){
							newpoint.setLocation(moverpositions[1].getX(), moverpositions[1].getY());
							return newpoint;
						}
					}
					
					if(lines[i].getSide() == LineSide.DOWN){
						DoublePoint newpoint = new DoublePoint();
						
						
						if(moverpositions[1].getY() > lines[i].getP1().getY()){
							newpoint.setLocation(moverpositions[1].getX(), moverpositions[1].getY());
						}else{
							newpoint.setLocation(moverpositions[1].getX(), lines[i].getP1().getY() + 1);
						}
				
						return newpoint;
					}else if(lines[i].getSide() == LineSide.LEFT){
						DoublePoint newpoint = new DoublePoint();
						if(moverpositions[1].getX() < lines[i].getP1().getX()){
							newpoint.setLocation(moverpositions[1].getX(), moverpositions[1].getY());
							
						}else{
							newpoint.setLocation(lines[i].getP1().getX() - 1, moverpositions[1].getY());
							
						}
						return newpoint;
					}else if(lines[i].getSide() == LineSide.RIGHT){
						DoublePoint newpoint = new DoublePoint();
						if(moverpositions[1].getX() > lines[i].getP1().getX()){
							newpoint.setLocation(moverpositions[1].getX(), moverpositions[1].getY());
						}else{
							newpoint.setLocation(lines[i].getP1().getX() + 1, moverpositions[1].getY());		
						}
						
						return newpoint;
					}else{
						/*UP*/
						DoublePoint newpoint = new DoublePoint();
						if(moverpositions[1].getY() < lines[i].getP1().getY()){
							newpoint.setLocation(moverpositions[1].getX(), moverpositions[1].getY());
						}else{
							newpoint.setLocation(moverpositions[1].getX(), lines[i].getP1().getY() - 1);
						}
						return newpoint;
					}	
				}
			}
		}
		
		/*Ha nem volt ütközés, akkor térjünk vissza azzal a pozícióval, ahova menni akartunk.*/
		return null;
	}
	
	@Override
	public Line[] mustCheckLines(){
		
		
		if(moverpositions[0].getX() >= eightPoints[0].getX() && moverpositions[0].getX() <= eightPoints[1].getX() && moverpositions[0].getY() <= eightPoints[1].getY()){
			line[0] = staticLines[3];
			line[1] = null;
			//System.out.println("Felsõ oldal");
			/*felsõ vonallal vizsgáljuk*/
		}else if(moverpositions[0].getX() >= eightPoints[3].getX() && moverpositions[0].getX() <= eightPoints[2].getX() && moverpositions[0].getY() >= eightPoints[2].getY()){
			line[0] = staticLines[1];
			line[1] = null;
			//System.out.println("alsó oldal");
			/*alsó vonallal vizsgáljuk*/
		}else if(moverpositions[0].getY() >= eightPoints[0].getY() && moverpositions[0].getY() <= eightPoints[3].getY() && moverpositions[0].getX() <= eightPoints[3].getX()){
			line[0] = staticLines[2];
			line[1] = null;
			//System.out.println("bal oldal");
			/*baloldali vonallal vizsgáljuk*/
		}else if(moverpositions[0].getY() >= eightPoints[1].getY() && moverpositions[0].getY() <= eightPoints[2].getY() && moverpositions[0].getX() >= eightPoints[2].getX()){
			line[0] = staticLines[0];
			line[1] = null;
			//System.out.println("jobb oldal");
			/*jobboldali vonallal vizsgáljuk*/
		}else if(moverpositions[0].getX() <= eightPoints[0].getX() && moverpositions[0].getY() <= eightPoints[0].getY()){
			line[0] = staticLines[2];
			line[1] = staticLines[3];
			//System.out.println("felsõ és bal oldal");
			/*felsõ és baloldali vonallal vizsgáljuk*/
		}else if(moverpositions[0].getX() >= eightPoints[1].getX() && moverpositions[0].getY() <= eightPoints[1].getY()){
			line[0] = staticLines[0];
			line[1] = staticLines[3];
			//System.out.println("felsõ és jobb oldal");
			/*felsõ és jobboldali vonallal vizsgáljuk*/
		}else if(moverpositions[0].getX() >= eightPoints[2].getX() && moverpositions[0].getY() >= eightPoints[2].getY()){
			line[0] = staticLines[0];
			line[1] = staticLines[1];
			//System.out.println("alsó és jobb oldal");
			/*alsó és jobboldali vonallal vizsgáljuk*/
		}else if(moverpositions[0].getX() <= eightPoints[3].getX() && moverpositions[0].getY() >= eightPoints[3].getY()){
			line[0] = staticLines[1];
			line[1] = staticLines[2];
			//System.out.println("alsó és bal oldal");
			/*alsó és baloldali vonallal vizsgáljuk*/
		}
		return line;
	}
}