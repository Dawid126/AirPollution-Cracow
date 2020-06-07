package map_drawer;

import processing.core.PFont;
import processing.core.PGraphics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

class LabeledMarker extends SimplePointMarker 
{

	private ArrayList<MeasurementContainer> stationMeasurements;
	private String stationName;
	private float size = 15;
	private int space = 6;
	private String maxString;;

	boolean exceededNorm = false;
	int numOfRows;
	
	private PFont font;
	private float fontSize = 15;

	public LabeledMarker(Location location, String stationName, ArrayList<MeasurementContainer> stationMeasurements, PFont font) 
	{
		this.location = location;
		this.stationName = stationName;
		this.stationMeasurements = stationMeasurements;
		this.numOfRows = stationMeasurements.size();
		this.maxString = stationName;
		
		
		for(MeasurementContainer measurement: stationMeasurements)
		{
			int measurementValue = measurement.getMeasurementValue();
			int normValue = measurement.getNormValue();
			String type = measurement.getType();
			String measurementString;
					
			if(measurement.getMeasurementValue() > measurement.getNormValue())
			{
				this.exceededNorm = true;
				measurementString = type + " :" + measurementValue + ", Norma to:" + normValue
						+ " Przekroczono!";
			}
			else
			{
				measurementString = type + " :" + measurementValue + ", Norma to:" + normValue;
			}
			
			
			if(measurementString.length() > maxString.length())
			{
				this.maxString = measurementString;
			}
		}

		this.font = font;
	}

	public void draw(PGraphics pg, float x, float y) 
	{
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
		else if(exceededNorm)
		{	
			pg.fill(0, 0, 0);
			pg.stroke(0, 0, 0);
		}
		else
		{
			pg.fill(31, 145, 16);
			pg.stroke(31, 145, 16);
		}
					
		pg.ellipse(x, y, 17, 17);

		// label
		if (selected) 
		{
			
			pg.fill(highlightColor);
			pg.stroke(highlightStrokeColor);
			pg.rect(9 + x + strokeWeight / 2, y - fontSize + strokeWeight / 2 - space, 
					pg.textWidth(maxString) + space * 1.5f, (numOfRows + 1)*(fontSize + space));
			
			
			pg.fill(255, 255, 255);
			
			pg.text(stationName, 9 + Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(y + strokeWeight / 2 - space * 0.75f));
			
			int counter = 1;
			for(MeasurementContainer measurement: stationMeasurements)
			{
				int measurementValue = measurement.getMeasurementValue();
				int normValue = measurement.getNormValue();
				String type = measurement.getType();
				String measurementString;
				
				if(measurementValue > normValue)
				{
					measurementString = type + " :" + measurementValue + ", Norma to:" + normValue
										+ " Przekroczono!";
				}
				else
				{
					measurementString = type + " :" + measurementValue + ", Norma to:" + normValue;
				}
				
				pg.text(measurementString, 9 + Math.round(x + space * 0.75f + strokeWeight / 2),
						Math.round(counter * size + y + strokeWeight / 2 - space * 0.75f));
				
				counter++;
				
			}
			
		}
		
		pg.popMatrix();
		pg.popStyle();
		
	}
}
