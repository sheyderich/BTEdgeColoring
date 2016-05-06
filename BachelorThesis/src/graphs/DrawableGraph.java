package graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Observable;

/**
 * An abstract class representing a Drawable Graph.  Edges can 
 * be drawn in certain colors to represent the edge colorings. 
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
public abstract class DrawableGraph extends Observable{

	/**
	 * Draws a given Graph on the graphics context g
	 * @param g
	 */
	public abstract void paintGraph(Graphics g,Dimension d);
	
	/**
	 * Returns a List of points that represent the nodes of a 
	 * graph so it can be drawn
	 * @return
	 */
	public abstract List<Point> calculateNodeCoordinates(Dimension d);
	
	/**
	 * Returns the Type of the Graph for information display 
	 */
	public abstract String getType();
	
	/**
	 * Returns the number of Nodes of a graph for information display
	 */
	public abstract int getVertexNumber();
	
	/**
	 * Draws a black edge between two nodes in a given color on the 
	 * graphics context g.
	 * @param start
	 * @param end
	 * @param g
	 */
	public void paintEdgeBlack(Point start, Point end, Graphics g){
		paintEdgeColor(start, end, false, Color.BLACK,g);
	}
	
	/**
	 * Draws a colored edge between two nodes in a given color on the 
	 * graphics context g.
	 * @param start
	 * @param end
	 * @param color
	 * @param g
	 */
	public void paintEdgeColor(Point start, Point end, boolean thick, Color color, Graphics g){
		Color oldColor = g.getColor();
		g.setColor(color);
		drawColoredLine(new Point((int)(start.getX()+5), (int)(start.getY()+5)),new Point((int)(end.getX()+5),(int)(end.getY()+5)),thick, g);
		g.setColor(oldColor);
	}
	
	/**
	 * Draws a node at the specified location on the graphics context g
	 * @param node
	 * @param g
	 */
	public void paintNode(Point node, Graphics g){
		g.fillOval((int)(node.getX() + 0.5), (int)(node.getY() + 0.5), 10, 10);
	}
	
	/**
	 * Draws an anti aliased line between two nodes that represents the 
	 * edge of the graph.
	 * @param a
	 * @param b
	 * @param g
	 */
	private static void drawColoredLine(Point a, Point b, boolean thick, Graphics g){
		Point p = new Point(a);
		Color c = g.getColor();
		float error, delta, dx, dy;
		int inc_x, inc_y;

		dx = b.x - p.x;
		dy = b.y - p.y;

		if (dx > 0)
			inc_x = 1;
		else
			inc_x = -1;
		if (dy > 0)
			inc_y = 1;
		else
			inc_y = -1;

		  if (Math.abs(dy) < Math.abs(dx)) 
	        {
	            error = 0.0f;
	            delta = Math.abs(dy / dx);
	            while (p.x != b.x) {
	                if(thick){
	                	setBiggerPixel(p.x, p.y, g);
	                }else{
	                	setPixelColor(p.x, p.y, c, g);
	                }
	                if (error > 0.0f){
	                	 if(thick){
	 	                	setBiggerPixel(p.x, p.y, g);
	 	                }else
	 	                	setPixelColor(p.x, p.y+inc_y, c,g);
	                } else{ 
	                	 if(thick){
	 	                	setBiggerPixel(p.x, p.y, g);
	 	                }else
	                	setPixelColor(p.x, p.y-inc_y, c, g);
	                }
	                p.x += inc_x;
	                error = error + delta;
	                if (error > 0.5f) {
	                    p.y += inc_y;
	                    error = error - 1.0f;
	                }
	            }
	        } else 
	        {
	            error = 0.0f;
	            delta = Math.abs(dx / dy);
	            while (p.y != b.y) {
	            	 if(thick){
		                	setBiggerPixel(p.x, p.y, g);
		                }else
		                	setPixelColor(p.x, p.y, c, g);
	                if (error > 0.0f){
	                	 if(thick){
	 	                	setBiggerPixel(p.x, p.y, g);
	 	                }else
	 	                	setPixelColor(p.x+inc_x, p.y, c, g);
	                } else {
	                	 if(thick){
	 	                	setBiggerPixel(p.x, p.y, g);
	 	                }else
	 	                	setPixelColor(p.x-inc_x, p.y, c, g);
	                }
	                p.y += inc_y;
	                error = error + delta;
	                if (error > 0.5f) {
	                    p.x += inc_x;
	                    error = error - 1.0f;
	                }
	            }
	        }
		  if(thick){
          	setBiggerPixel(p.x, p.y, g);
          }else
	        setPixelColor(b.x, b.y, c, g);
	}
	
	/**
	 * Sets a pixel on the graphics context in a specified tone of color to draw a 
	 * smooth line on the canvas
	 * @param x
	 * @param y
	 * @param color
	 * @param g
	 */
	private static void setPixelColor(int x, int y, Color color, Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, 1, 1);
	}
	
	/**
	 * Sets a lightgray bigger pixel that can be used to highlight aspects of 
	 * the drawn graph. 
	 * @param x
	 * @param y
	 * @param g
	 */
	private static void setBiggerPixel(int x, int y, Graphics g){
		Color c = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x-2, y-2, 5, 5);
		g.setColor(c);
	}
	
}
