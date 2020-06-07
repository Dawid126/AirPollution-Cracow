package map_drawer;

public class MeasurementContainer 
{
	private int measurementValue;
	private int normValue;
	private String type;
	
	public MeasurementContainer(int measurementValue, int normValue, String type)
	{
		this.measurementValue = measurementValue;
		this.normValue = normValue;
		this.type = type;
	}

	public int getMeasurementValue()
	{
		return this.measurementValue;
	}
	
	public int getNormValue()
	{
		return this.normValue;
	}
	
	public String getType()
	{
		return this.type;
	}
}
