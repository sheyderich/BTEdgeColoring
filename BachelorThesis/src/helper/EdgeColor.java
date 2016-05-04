package helper;

import java.awt.Color;

public class EdgeColor {
	private static final Color[] EDGE_COLORS = {
		    Color.decode("0xd11141"),    
		    Color.decode("0x00b159"),    
		    Color.decode("0x00aedb"),    
		    Color.decode("0xf37735"),    
		    Color.decode("0xffc425"),  
		    
		    Color.decode("0x373854"),    
		    Color.decode("0x493267"),    
		    Color.decode("0x9e379f"),    
		    Color.decode("0xe86af0"),    
		    Color.decode("0x7bb3ff"),
		    
		    Color.decode("0xFF7A5C"),
		    Color.decode("0x53377A"),  
		    Color.decode("0xFF8E00"),  
		    Color.decode("0xB32851"), 
		    Color.decode("0xF4C800"),    
		    Color.decode("0x7F180D"),    
		    Color.decode("0x93AA00"),   
		    Color.decode("0x593315"),   
		    Color.decode("0xF13A13"),   
		    Color.decode("0x232C16"),    
		};
	
	public static Color[] getColors(){
		return EDGE_COLORS;
	}
}
