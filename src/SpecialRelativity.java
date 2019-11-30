import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SpecialRelativity {
	public static double c = 299792.458; // in km/s
	public static double gm = 6.67408 * Math.pow(10, -11); // in m3 kg^-1 s^-2
	public static double g = gm/1000;
	public static double earthMass = 5.9723 * Math.pow(10, 24); // in kg
	public static double solMass = 1.989 * Math.pow(10, 30); // in kg. Roughly 330,000 Earth Masses
	public static int earthRadius = 6356; // in km
	
	private static MathContext hpmc = new MathContext(1024, RoundingMode.HALF_EVEN);
	
	public static double getKinematicTimeDilationByPercentC(double percentC) {
		double v = (percentC/100)*c;
		double timeDilationFactor = getKinematicTimeDilationByVelocity(v);
		return timeDilationFactor;
	}
	
	public static double getKinematicTimeDilationByVelocity(double velocity) {
		double v = velocity;
		double timeDilationFactor = 1.0 / Math.sqrt(1.0 - ((v*v)/(c*c)));
		return timeDilationFactor;
	}

	public static double calculateTimeDilation(double percentC) {
		
		return 0;
	}
	
	/**
	 * Calculates the gravitational force between two objects 
	 * @param mass1 in kg
	 * @param mass2 in kg
	 * @param kmApart in km
	 * @return force in newtons
	 */
	public static double getGravitationalForce(double mass1, double mass2, double kmApart) {
		kmApart*=1000;
		double f = (gm * mass1 * mass2) / (kmApart*kmApart);
		return f;
	}
	
	/**
	 * Calculates the escape velocity in km/s required to escape a given mass with a given radius.
	 * @param mass in terms of number of earth masses
	 * @param radius in km
	 * @return velocity in km/s
	 */
	public static double getEscapeVelocity(double mass, double radius) {
		mass *= earthMass;
		radius*=1000;
		double ev = Math.sqrt(2*mass*gm/radius);
		return ev/1000;
	}
	
	/**
	 * Calculates the Schwarzschild radius by solving the Escape Velocity equation for R and assuming Ve equals c.
	 * @param mass in kg
	 * @return radius in km
	 */
	public static double getSchwarzschildUsingEscapeVelocity(double mass) {
		double rs = (2*mass*g)/(c*c);
		return rs/1_000_000;
	}
	
	/**
	 * Calculates the Gravimetric Time Dilation Coefficient using the Schwartzschild metric.
	 * @param distance in km
	 * @param mass in terms of number of earth masses
	 * @return double coefficient
	 */
	public static double getGravimetricTimeDilationCoefficient(double distance, double mass) {
		double rs = getSchwarzschildUsingEscapeVelocity(mass*earthMass);
		double coefficient = Math.sqrt(1/(1-(rs/distance)));
		return coefficient;
	}
	public static BigDecimal getGravimetricTimeDilationCoefficientBD(double distance, double mass) {
		double rs = getSchwarzschildUsingEscapeVelocity(mass*earthMass);
		BigDecimal div = new BigDecimal(rs, hpmc).divide(new BigDecimal(distance, hpmc), 1000, RoundingMode.HALF_EVEN);
		BigDecimal min = new BigDecimal(1, hpmc).subtract(div);
		BigDecimal coefficient = new BigDecimal(1, hpmc).divide(min, 1000, RoundingMode.HALF_EVEN);
		
		return coefficient;
	}
	
}
