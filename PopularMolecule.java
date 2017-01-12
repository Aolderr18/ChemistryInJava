import java.util.ArrayList;

// This class is used for creating objects of commonly used molecules such as water and carbon dioxide.
public abstract class Popular_Molecule extends Molecule {
	protected Popular_Molecule(ArrayList<Atom> allAtoms, boolean[][] bondsTo)
			throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		super(allAtoms, bondsTo);
	}

	protected Popular_Molecule() {

	}
}

final class Water extends Popular_Molecule {
	private static ArrayList<Atom> atomsInWaterMolecule;
	private static boolean[][] waterBonds;

	public Water() throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		super(atomsInWaterMolecule(), waterBonds());
	}

	private static ArrayList<Atom> atomsInWaterMolecule() {
		atomsInWaterMolecule = new ArrayList<Atom>();
		try {
			atomsInWaterMolecule.add(new Atom(1));
			atomsInWaterMolecule.add(new Atom(8));
			atomsInWaterMolecule.add(new Atom(1));
		} catch (InvalidAtomicNumberException e) {

		}
		return atomsInWaterMolecule;
	}

	private static boolean[][] waterBonds() {
		waterBonds = new boolean[3][2];
		waterBonds[0][0] = true;
		waterBonds[0][1] = false;
		waterBonds[1][0] = true;
		waterBonds[1][1] = true;
		waterBonds[2][0] = false;
		waterBonds[2][1] = true;
		return waterBonds;
	}
}

final class CarbonDioxide extends Popular_Molecule {
	private static ArrayList<Atom> atomsInCarbonDioxideMolecule;
	private static boolean[][] carbonDioxideBonds;

	public CarbonDioxide() throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		super(atomsInCarbonDioxideMolecule(), carbonDioxideBonds());
	}

	private static ArrayList<Atom> atomsInCarbonDioxideMolecule() {
		atomsInCarbonDioxideMolecule = new ArrayList<Atom>();
		try {
			atomsInCarbonDioxideMolecule.add(new Atom(8));
			atomsInCarbonDioxideMolecule.add(new Atom(6));
			atomsInCarbonDioxideMolecule.add(new Atom(8));
		} catch (InvalidAtomicNumberException e) {

		}
		return atomsInCarbonDioxideMolecule;
	}

	private static boolean[][] carbonDioxideBonds() {
		carbonDioxideBonds = new boolean[3][2];
		carbonDioxideBonds[0][0] = true;
		carbonDioxideBonds[0][1] = false;
		carbonDioxideBonds[1][0] = true;
		carbonDioxideBonds[1][1] = true;
		carbonDioxideBonds[2][0] = false;
		carbonDioxideBonds[2][1] = true;
		return carbonDioxideBonds;
	}
}
