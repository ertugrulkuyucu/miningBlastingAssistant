package mineBlast;

import java.util.Arrays;

public class Calculations {

	double[] f = { 1.11, 1.075, 1.05, 1.025, 1.00, 0.95, 0.83 };
	double[] k = { 1.00, 1.005, 1.02, 1.03, 1.05, 1.12, 1.41 };
	String[] blasterNames = { "Dynamex A", "Dynamex M", "ANFO", "ANFO (%5 Al Katkili}", "Dinamit 1", "Dinamit 2",
			"Dinamit 4", "Gomme A", "Gomme AS", "Gomme AT", "Gomme 777", "Gom I", "Gom II", "Gom III",
			"Grizu Guvenli Dinamit", "Jelatinit Dinamit", "Elbar I", "Gurit A", "Emulite 100", "Emulite 150",
			"Emulite 200", "Emulite 300"};

	double[] blasterDensity = {1.40, 1.40, 0.85, 0.87, 0.98, 1.00, 1.00, 1.55, 1.57, 1.55, 1.53, 1.50, 1.50, 1.50,
			1.10, 1.50, 1.00, 1.00, 1.20, 1.21, 1.25, 1.28 };

	double[] blasterPowerFactor = {1.02, 1.00, 0.96, 1.10, 1.06, 0.94, 1.05, 1.28, 1.15, 1.11, 0.99, 1.28, 1.15, 1.02,
			0.58, 1.00, 0.76, 0.81, 0.75, 0.95, 0.75, 0.73 };

	public double[] calculate(double stepH, String blasterName1, String blasterName2, double holeD, double rockC,
			double angle) {

		double holeArea = (holeD * holeD * Math.PI) / 4;

		double vMax = holeD * 45 / 1000 * Math.sqrt(0.4 / rockC)
				* Math.sqrt(getBlasterDensity(blasterName1) * getBlasterPowerFactor(blasterName1) / 1.25)
				* Math.sqrt(1 / getAngleCoefficient(angle, f));
		double U = 0.3 * vMax;
		U = Math.round(U);
		double H = (stepH + U) * getAngleCoefficient(angle, k);
		H = (int) H;
		double F = (holeD / 1000) + (0.03 * H);
		double V = vMax - F;
		double E = 1.25 * V;
		double Ldip = 1.3 * V;
		double Qdip = Ldip * holeArea * getBlasterDensity(blasterName2) / 1000;
		double S = V;
		double Lkol = H - Ldip - S;
		double Qkol = Lkol * holeArea * getBlasterDensity(blasterName1) / 1000;
		double Qtop = Qdip + Qkol;
		double Y = (Qdip + Qkol) * 0.03 ;
		double I = H / (E * V * stepH);
		double q = (Qdip + Qkol) / (E * V * stepH);
		double W = (E * V * stepH);

		double[] results = { vMax, U, H, F, V, E, Ldip, Qdip, S, Lkol, Qkol, Qtop, Y, I, q, W };
		return results;
	}

	public double getBlasterDensity(String name) {

		int index = Arrays.asList(blasterNames).indexOf(name);
		return blasterDensity[index];

	}

	public double getBlasterPowerFactor(String name) {

		int index = Arrays.asList(blasterNames).indexOf(name);
		return blasterPowerFactor[index];

	}

	public double getAngleCoefficient(double angle, double[] arr) {

		if (angle >= 87) {
			return arr[0];
		} else if (angle >= 82) {
			return arr[1];
		} else if (angle >= 78) {
			return arr[2];
		} else if (angle >= 69) {
			return arr[3];
		} else if (angle >= 58) {
			return arr[4];
		} else if (angle >= 49) {
			return arr[5];
		} else {
			return arr[6];
		}

	}

}
