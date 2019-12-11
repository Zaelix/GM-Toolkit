import java.io.Serializable;
import java.util.Calendar;

public class SREvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 348172801942594618L;
	public String name;
	public Calendar date;
	public String description;
	
	public SREvent(String name, Calendar date, String description) {
		this.name = name;
		this.date = date;
		this.description = description;
	}
	
	public boolean isSoonerThan(SREvent e) {
		int comp = date.compareTo(e.date);
		//System.out.println("Comparing " + name + " to " + e.name + ": " + comp);
		if(comp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getTimeTo(Calendar e) {
		Calendar c = Calendar.getInstance();
		c.set(e.get(Calendar.YEAR), e.get(Calendar.MONTH), e.get(Calendar.DAY_OF_MONTH), e.get(Calendar.HOUR), e.get(Calendar.MINUTE), e.get(Calendar.SECOND));;
		c.add(Calendar.YEAR, -date.get(Calendar.YEAR));
		c.add(Calendar.MONTH, -date.get(Calendar.MONTH));
		c.add(Calendar.DAY_OF_MONTH, -date.get(Calendar.DAY_OF_MONTH));
		c.add(Calendar.HOUR, -date.get(Calendar.HOUR));
		c.add(Calendar.MINUTE, -date.get(Calendar.MINUTE));
		c.add(Calendar.SECOND, -date.get(Calendar.SECOND));
		String s = c.get(Calendar.YEAR) + "y " + c.get(Calendar.MONTH) + "mn " + c.get(Calendar.DAY_OF_MONTH) + "d "  + c.get(Calendar.HOUR) + "h " + c.get(Calendar.MINUTE) + "m " + c.get(Calendar.SECOND) + "s";
		return s;
	}
}
