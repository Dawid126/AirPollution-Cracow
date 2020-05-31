package map_drawer;

import processing.core.PFont;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class LabeledMarker extends SimplePointMarker {

	private String stationName;
	private int PM10Value;
	private int PM2_5Value;
	private int PM10Norm;
	private int PM2_5Norm;
	private float size = 15;
	private int space = 6;

	private PFont font;
	private float fontSize = 15;

	public LabeledMarker(Location location, String stationName, int PM10Value, int PM2_5Value, 
			int PM10Norm, int PM2_5Norm, PFont font) 
	{
		this.location = location;
		this.stationName = stationName;
		this.PM10Value = PM10Value;
		this.PM2_5Value = PM2_5Value;
		this.PM10Norm = PM10Norm;
		this.PM2_5Norm = PM2_5Norm;

		this.font = font;
	}

	public void draw(PGraphics pg, float x, float y) 
	{
		
		String PM10String;
		String PM2_5String;
		
		if(PM10Value > PM10Norm)
		{
			PM10String = "PM10: " + PM10Value + ", Przekroczono! Norma to: " + PM10Norm;
		}
		else
		{
			PM10String = "PM10: " + PM10Value + ", Norma to: " + PM10Norm;
		}
		
		if(PM2_5Value > PM2_5Norm)
		{
			PM2_5String = "PM2,5: " + PM2_5Value + ", Przekroczono! Norma to: " + PM2_5Norm;
		}
		else
		{
			PM2_5String = "PM2,5: " + PM2_5Value + ", Norma to: " + PM2_5Norm;
		}
		
		pg.pushStyle();
		pg.pushMatrix();
		if (selected) 
		{
			pg.translate(0, 0, 1);
		}
		pg.strokeWeight(strokeWeight);
		if (selected) 
		{
			pg.fill(highlightColor);
			pg.stroke(highlightStrokeColor);
		} 
		else 
		{
			if(PM2_5Value > PM2_5Norm && PM10Value > PM10Norm)
			{
				pg.fill(0, 0, 0);
				pg.stroke(0, 0, 0);
			}
			else if(PM2_5Value > PM2_5Norm || PM10Value > PM10Norm)
			{
				pg.fill(128, 86, 4);
				pg.stroke(128, 86, 4);
			}
			else
			{
				pg.fill(31, 145, 16);
				pg.stroke(31, 145, 16);
			}
			
		}
		
		pg.ellipse(x, y, 17, 17);

		// label
		if (selected) 
		{
			pg.fill(highlightColor);
			pg.stroke(highlightStrokeColor);
			pg.rect(9 + x + strokeWeight / 2, y - fontSize + strokeWeight / 2 - space, 
					Math.max(pg.textWidth(PM10String), pg.textWidth(PM2_5String)) + space * 1.5f,
					3*(fontSize + space));
			pg.fill(255, 255, 255);
			pg.text(stationName, 9 + Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(y + strokeWeight / 2 - space * 0.75f));
			
			pg.text(PM10String, 9 + Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(15 + y + strokeWeight / 2 - space * 0.75f));
			
			pg.text(PM2_5String, 9 + Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(30 + y + strokeWeight / 2 - space * 0.75f));
		}
		pg.popMatrix();
		pg.popStyle();
	}
}
