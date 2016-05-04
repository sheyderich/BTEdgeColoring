package helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HSLColor {
	
	private float hue; 
	private float saturation;
	private float lightness;
	
	public HSLColor(int hue, float saturation, float lightness){
		if(hue < 0 || hue > 360)
			throw new IllegalArgumentException("HSL hue has to be in the range of [0 - 360)");
		if(saturation < 0 || saturation > 100)
			throw new IllegalArgumentException("HSL saturation has to be in the range of [0 - 100)");
		if(lightness < 0 || lightness > 100)
			throw new IllegalArgumentException("HSL lightness has to be in the range of [0 - 100)");
		
		this.hue = hue/360;
		this.saturation = saturation/100f;
		this.lightness = lightness/100f;
	}
	
	public Color convertHSLtoRGB(){
		return Color.getHSBColor(hue, saturation, lightness);
	}
	
	public static List<HSLColor> getNDistinctColors(int n){
		if(n < 1){
			throw new IllegalArgumentException("We need at least 1 different colors");
		}
		List<HSLColor> colors = new ArrayList<HSLColor>();
		Random rand = new Random();
		for(int i = 0; i < 360; i+=360/n){
			HSLColor color = new HSLColor(i, 90 + rand.nextFloat()*10, 50+rand.nextFloat()*20);
			colors.add(color);
		}
		return colors;
	}
}
