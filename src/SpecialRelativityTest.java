import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class SpecialRelativityTest {

	@Test
	public void testKinematicTimeDilation() {
		assertEquals(1.55906266, SpecialRelativity.getKinematicTimeDilationByVelocity(230000f), 0.00001);
		assertEquals(1.000000014, SpecialRelativity.getKinematicTimeDilationByVelocity(50f), 0.00001);
		assertEquals(13.76240757, SpecialRelativity.getKinematicTimeDilationByVelocity(299000f), 0.00001);

		assertEquals(1.55906266, SpecialRelativity.getKinematicTimeDilationByPercentC(76.7197419), 0.00001);
		assertEquals(1.000000014, SpecialRelativity.getKinematicTimeDilationByPercentC(0.01667820476), 0.00001);
		assertEquals(13.76240757, SpecialRelativity.getKinematicTimeDilationByPercentC(99.73566446), 0.00001);
	}
	
	@Test
	public void testGravitationalForces() {
		assertEquals(0.000000000013348, SpecialRelativity.getGravitationalForce(1000, 5000, 5), 0.000000000001);
		assertEquals(0.000000000000014, SpecialRelativity.getGravitationalForce(30, 7, 1), 0.000000000000001);
		assertEquals(0.00583982, SpecialRelativity.getGravitationalForce(5000000, 70000000, 2), 0.00001);
		assertEquals(0.000_000_000_004_557, SpecialRelativity.getGravitationalForce(892345934, 70000000, 956472), 0.000_000_000_000_1);
		assertEquals(0.0519095, SpecialRelativity.getGravitationalForce(1, 70000000, 0.0003), 0.001);
	}
	
	@Test
	public void testSchwarzschildRadius() {
		assertEquals(0.0000088694, SpecialRelativity.getSchwarzschildUsingEscapeVelocity(1*SpecialRelativity.earthMass), 0.00000001);
		assertEquals(0.0000177388, SpecialRelativity.getSchwarzschildUsingEscapeVelocity(2*SpecialRelativity.earthMass), 0.00000001);
		assertEquals(0.0000266082, SpecialRelativity.getSchwarzschildUsingEscapeVelocity(3*SpecialRelativity.earthMass), 0.00000001);
		assertEquals(0.00177388, SpecialRelativity.getSchwarzschildUsingEscapeVelocity(200*SpecialRelativity.earthMass), 0.0001);
		assertEquals(709.553, SpecialRelativity.getSchwarzschildUsingEscapeVelocity(80_000_000*SpecialRelativity.earthMass), 1);
	}
	
	@Test
	public void testEscapeVelocity() {
		assertEquals(11.18573, SpecialRelativity.getEscapeVelocity(1, 6378), 0.01);
		assertEquals(19.96425, SpecialRelativity.getEscapeVelocity(2, 4000), 0.01);
		assertEquals(16.30074, SpecialRelativity.getEscapeVelocity(3, 9000), 0.01);
		assertEquals(15.66125, SpecialRelativity.getEscapeVelocity(4, 13000), 0.01);
	}
	
	@Test
	public void testGravimetricTimeDilation() {
		assertEquals(1.00000088694, SpecialRelativity.getGravimetricTimeDilationCoefficient(10, 2), 0.0000001);
		assertEquals(1.00000013304, SpecialRelativity.getGravimetricTimeDilationCoefficient(100, 3), 0.0000001);
		assertEquals(1.00000886952, SpecialRelativity.getGravimetricTimeDilationCoefficient(1, 2), 0.0000001);
		assertEquals(1.00001773927, SpecialRelativity.getGravimetricTimeDilationCoefficient(50, 200), 0.000001);
	}
}
