package helper;

import java.awt.Color;
import exceptions.NotEnoughColorsException;

/**
 * A Collection of different looking colors that can be
 * used to create the edge colorization 
 * @author Stephanie Heyderich
 * @version 06.05.2016
 */
public class EdgeColor {
	private static final Color[] EDGE_COLORS = {
		    Color.decode("0xb62020"),    // dark red
		    Color.decode("0x3366ff"),    // darker blue
		    Color.decode("0xffbf00"),    // yellow
		    Color.decode("0x028900"),    // dark green
		    Color.decode("0x660066"),    // dark purple
		    Color.decode("0xff0097"),    // bright pink
		    Color.decode("0x55ccff"),	 // light blue
		    Color.decode("0xfe2e2e"),    // bright red
		    Color.decode("0xadff00"),    // light green
		    Color.decode("0xbe29ec"),    // bright purple
		    Color.decode("0xf09609"),	 // orange
		    Color.decode("0xab3832"),    // brown
		    Color.decode("0x0000FF"), 	 // totally blue
		    Color.decode("0xffdc73"),    // light brown
		    Color.decode("0xaaaaaa"),    // gray
		    Color.decode("0x24a191"),    // bluish green
		    Color.decode("0xe1f7d5"),    // pastel green
		    Color.decode("0xc9c9ff"),    // pastel blue
		    Color.decode("0xffbdbd"),    // pastel red
		    Color.decode("0x00ff00"),    // toxic green
		};
	
	private static final String[] COLOR_NAMES = {
		   "Dark Red",    
		   "Dark Blue",   
		   "Yellow",    
		   "Dark Green",
		   "Dark Purple",
		   "Bright Pink",
		   "Ligth Blue",
		   "Brigth Red",
		   "Light Green",
		   "Bright Purple",
		   "Orange",
		   "Brown",
		   "Deep Blue",
		   "Ligth Brown",
		   "Gray",
		   "Blue-Green",
		   "Pastel Green",
		   "Pastel Blue",
		   "Pastel Red",
		   "Toxic Green"
		};
	
	/**
	 * Returns a color based on its position in the list. 
	 * There are only 20 colors to choose from
	 * @param i
	 * @return
	 */
	public static Color getColor(int i){
		if(i > EDGE_COLORS.length){
			throw new NotEnoughColorsException("There are only 20 colors in the set.");
		}
		return EDGE_COLORS[i];
	}
	
	/**
	 * Returns a color name based on its position in the list. 
	 * There are only 20 colors to choose from
	 * @param i
	 * @return
	 */
	public static String getColorName(int i){
		if(i > COLOR_NAMES.length){
			throw new NotEnoughColorsException("There are only 20 colors in the set.");
		}
		return COLOR_NAMES[i];
	}
}
