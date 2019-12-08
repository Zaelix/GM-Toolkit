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
}
