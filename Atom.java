public class Atom {
	protected int atomicNumber;
	protected boolean[][] s_OrbitalActive;
	protected boolean[][] p_OrbitalActive;
	protected boolean[][] d_OrbitalActive;
	protected boolean[][] f_OrbitalActive;
	protected int s, p, d, f;
	private final short s_OrbitalStartingShell = 1;
	private final short p_OrbitalStartingShell = 2;

	public Atom(int atomicNumber) throws InvalidAtomicNumberException {
		try {
			setAtomicNumber(atomicNumber);
		} catch (InvalidAtomicNumberException ivane) {
			ivane.printStackTrace();
			throw ivane;
		}
		s_OrbitalActive = new boolean[2][7];
		p_OrbitalActive = new boolean[6][6];
		d_OrbitalActive = new boolean[10][4];
		f_OrbitalActive = new boolean[14][2];
		s = -1;
		p = -1;
		d = -1;
		f = -1;
		configureOrbitals();
	}
	
	public Atom(ChemicalElement element) throws InvalidAtomicNumberException {
		this(ChemicalElement.findAtomicNumberByElement(element));
	}

	/*
	 * The following method sets the arrangement of electrons orbiting the
	 * shells around the atom.
	 */
	void configureOrbitals() {
		for (int n = 1; n <= atomicNumber; ++n) {
			OrbitalChangeVector ocv = ChemicalElement.electronOrbitalMap()
					.get(ChemicalElement.lookUpChemicalElementByOrdinal(n));
			s += ocv.getS_Change();
			p += ocv.getP_Change();
			d += ocv.getD_Change();
			f += ocv.getF_Change();
			if (s >= 0)
				s_OrbitalActive[s % 2][s / 2] = true;
			if (p >= 0)
				p_OrbitalActive[p % 6][p / 6] = true;
			if (d >= 0)
				d_OrbitalActive[d % 10][d / 10] = true;
			if (f >= 0)
				f_OrbitalActive[f % 14][f / 14] = true;
		}
		short x;
		for (x = (short) (s + 1); x < atomicNumber; ++x)
			try {
				s_OrbitalActive[x % 2][x / 2] = false;
			} catch (ArrayIndexOutOfBoundsException aiob) {
				break;
			}
		for (x = (short) (p + 1); x < atomicNumber; ++x)
			try {
				p_OrbitalActive[x % 6][x / 6] = false;
			} catch (ArrayIndexOutOfBoundsException aiob) {
				break;
			}
		for (x = (short) (d + 1); x < atomicNumber; ++x)
			try {
				d_OrbitalActive[x % 10][x / 10] = false;
			} catch (ArrayIndexOutOfBoundsException aiob) {
				break;
			}
		for (x = (short) (f + 1); f < atomicNumber; ++x)
			try {
				f_OrbitalActive[x % 14][x / 14] = false;
			} catch (ArrayIndexOutOfBoundsException aiob) {
				break;
			}
	}

	int valenceElectrons() {
		short highestShell = /*
								 * By definition the number of valence electrons
								 * must be the number of electrons in orbit in
								 * the highest shell.
								 */ highestShell();
		int valenceElectrons = 0;
		short e;
		for (e = 0; e < 2; ++e)
			if (s_OrbitalActive[e][highestShell - 1])
				++valenceElectrons;
		if (atomicNumber > 2)
			for (e = 0; e < 6; ++e)
				if (p_OrbitalActive[e][(highestShell - 1) - (p_OrbitalStartingShell - s_OrbitalStartingShell)])
					++valenceElectrons;
		return valenceElectrons;
	}

	int fullShell() {/**
						 * This is the remaining number of electrons required to
						 * complete a full shell.
						 */
		short highestInS_Shell = 0, highestInP_Shell = 0;
		short i, j;
		for (j = 0; j < 7; ++j)
			for (i = 0; i < 2; ++i)
				if (s_OrbitalActive[i][j])
					if (j + 1 > highestInS_Shell)
						highestInS_Shell = (short) (j + 1);
		for (j = 0; j < 6; ++j)
			for (i = 0; i < 6; ++i)
				if (p_OrbitalActive[i][j])
					if (j + 1 > highestInP_Shell)
						highestInP_Shell = (short) (j + 1);
		if (highestInS_Shell == highestInP_Shell + 1 && atomicNumber >= 3)
			return 8;
		else
			return 2;
	}

	int diameterInPicometers() {
		return (int) ChemicalElement.atomicDiameters().get(elementType());
	}

	short potenialChemicalBonds() {
		if (atomicNumber == 5)
			return 3;
		return (short) (fullShell() - valenceElectrons());
	}

	short highestShell() {/**
							 * This is the number of shells around the nucleus.
							 */
		short highestShell = 0;
		short i, j;
		for (j = 0; j < 7; ++j)
			for (i = 0; i < 2; ++i)
				if (s_OrbitalActive[i][j])
					if (j + 1 > highestShell)
						highestShell = (short) (j + 1);
		return highestShell;
	}

	ChemicalElement elementType() {
		return ChemicalElement.lookUpChemicalElementByOrdinal(atomicNumber);
	}
	
	@Override
	public String toString() {
		return "" + elementType();
	}

	public int getAtomicNumber() {
		int duplicateAtomicNumber = atomicNumber;
		return duplicateAtomicNumber;
	}

	public void setAtomicNumber(int atomicNumber) throws InvalidAtomicNumberException {
		if (atomicNumber < 1
				|| atomicNumber > 120)/*
										 * The known elements range in atomic
										 * number from 1 to 118.
										 */
			throw new InvalidAtomicNumberException(atomicNumber);
		this.atomicNumber = atomicNumber;
	}
}

/**
 * The following class is used for creating objects that contain information as
 * to the change in the number of electrons in each orbital from one atom of an
 * atomic number to the atom with the next highest atomic number.
 */
class OrbitalChangeVector {
	private int s_Change;/*
							 * This is the increment of electrons in the s
							 * orbital from the previous atom in the element
							 * series.
							 */
	private int p_Change;/*
							 * This is the increment of electrons in the p
							 * orbital from the previous atom in the element
							 * series.
							 */
	private int d_Change;/*
							 * This is the increment of electrons in the d
							 * orbital from the previous atom in the element
							 * series.
							 */
	private int f_Change;/*
							 * This is the increment of electrons in the f
							 * orbital from the previous atom in the element
							 * series.
							 */

	public OrbitalChangeVector(int i, int j, int k, int l) {
		setS_Change(i);
		setP_Change(j);
		setD_Change(k);
		setF_Change(l);
	}

	public int getS_Change() {
		return s_Change;
	}

	public int getP_Change() {
		return p_Change;
	}

	public int getD_Change() {
		return d_Change;
	}

	public int getF_Change() {
		return f_Change;
	}

	public void setS_Change(int s_Change) {
		this.s_Change = s_Change;
	}

	public void setP_Change(int p_Change) {
		this.p_Change = p_Change;
	}

	public void setD_Change(int d_Change) {
		this.d_Change = d_Change;
	}

	public void setF_Change(int f_Change) {
		this.f_Change = f_Change;
	}
}

@SuppressWarnings("serial")
class InvalidAtomicNumberException extends Exception {
	public InvalidAtomicNumberException(int number) {
		super("InvalidAtomicNumberException : " + number);
	}
}
