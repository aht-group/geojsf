package org.geojsf.util.coordinates;

import java.util.Objects;

public class DmsToDecimalConverter
{
	 private int degree;
	 public int getDegree() {return degree;}
	 public void setDegree(int degree) {this.degree = degree;}

	 private   int minute;
	 public int getMinute() {return minute;}
	 public void setMinute(int minute) {this.minute = minute;}

	 private  double second;
	 public double getSecond() {return second;}
	 public void setSecond(double second) {this.second = second;}

	 private   String direction = null;
	 public String getDirection() {return direction;}
	 public void setDirection(String direction) {this.direction = direction;}

	public DmsToDecimalConverter (String strDmsCoordinate)
	{
		String deg = null;
		String min = null;
		String sec = null;

		setDirection(getDirection(strDmsCoordinate));

		strDmsCoordinate = strDmsCoordinate.replaceAll("([nNeEsSwW])", "").trim();
		strDmsCoordinate = strDmsCoordinate.replaceAll("’’", "''").trim();
		strDmsCoordinate = strDmsCoordinate.replaceAll("’", "'").trim();
		try
		{
			deg =  strDmsCoordinate.substring(0, strDmsCoordinate.indexOf("°"));
			strDmsCoordinate = strDmsCoordinate.substring(strDmsCoordinate.indexOf("°") + 1).trim();
		}
		catch(StringIndexOutOfBoundsException e){deg = "0";}

		try
		{
			min =  strDmsCoordinate.substring(0, strDmsCoordinate.indexOf("'"));
			strDmsCoordinate = strDmsCoordinate.substring(strDmsCoordinate.indexOf("'") + 1).trim();
		}
		catch(StringIndexOutOfBoundsException e){min = "0";}

		try
		{
			sec=  strDmsCoordinate.substring(0, (strDmsCoordinate.length()-1));
		}
        catch(StringIndexOutOfBoundsException e)
		{
        	sec = "0";
        	if(min.contains(","))
        	{
        		sec = min.substring(min.indexOf(",") + 1);
        		min =  min.substring(0, min.indexOf(","));
        	}
        }

	    double second = 0;
	    try
	    {
	    	setDegree(Integer.parseInt(deg));
	    	setMinute(Integer.parseInt(min));
	    	second = Double.parseDouble("0."+sec);
	    	second = (second * 60);
	    }
	    catch(Exception e) {}

	}


	private String getDirection(String dmsCoordinate)
	{

		if (dmsCoordinate.contains("n") ||dmsCoordinate.contains("N"))
		{
			return "N";
		}


		if (dmsCoordinate.contains("e") ||dmsCoordinate.contains("E"))
		{
			return "E";
		}

		if (dmsCoordinate.contains("s") ||dmsCoordinate.contains("S"))
		{
			return "S";
		}

		if (dmsCoordinate.contains("w") ||dmsCoordinate.contains("W"))
		{
			return "W";
		}

		return "";
	}

	private static boolean isDmsCoordinate(String dmsCoordinate)
	{
		if(Objects.nonNull(dmsCoordinate) && dmsCoordinate.contains("°") || dmsCoordinate.contains("'") || dmsCoordinate.contains("''"))
		{
			return true;
		}
		return false;
	}

	public static boolean isSet(String dmsCoordinate)
	{
		if(Objects.isNull(dmsCoordinate) || dmsCoordinate.length() < 1  || dmsCoordinate.equals("null")  || dmsCoordinate.equals("\"\""))
		{
			return false;
		}
		return true;
	}

	public static double convert(String dmsCoordinate)
	{
		if(isDmsCoordinate(dmsCoordinate))
		{
			DmsToDecimalConverter dmsToDecConverter = new DmsToDecimalConverter(dmsCoordinate);
		    double decimal = Math.signum(dmsToDecConverter.getDegree()) * (Math.abs(dmsToDecConverter.getDegree()) + (dmsToDecConverter.getMinute() / 60.0) + (dmsToDecConverter.getSecond() / 3600.0));

		    //reverse for south or west coordinates; north is assumed
		    if (dmsToDecConverter.getDirection().equals("S") || dmsToDecConverter.getDirection().equals("W"))
		    {
		        decimal *= -1;
		    }

		    return decimal;
		}
		else
		{
			return Double.parseDouble(dmsCoordinate);
		}
	}

}
