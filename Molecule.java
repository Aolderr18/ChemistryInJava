/**
 * The molecule class was designed to extend ObjectWeb, since a molecule is composed of atoms, which are connected to one another.
 */

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Molecule extends ObjectWeb {
	/*
	 * The data structure that contains the information about atoms in any
	 * object of molecule should be dynamic, allowing more chemical bonds to
	 * form.
	 */
	private ArrayList<Atom> allAtoms;
	/*
	 * The following two dimensional boolean array contains information about
	 * which atoms bond to which other atoms.
	 */
	private boolean[][] bondsTo;

	@SuppressWarnings("unchecked")
	public Molecule(ArrayList<Atom> allAtoms, boolean[][] bondsTo)
			throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		super(new ArrayList<Object>(), new boolean[0][0]);
		// A molecule, by definition must contain at least two atoms.
		if (allAtoms.size() == 1)
			throw new IllegalArgumentException("A molecule must contain multiple atoms.");
		setAllAtoms(allAtoms);
		setBonds(bondsTo);
	}

	protected Molecule() {
		/*
		 * Since the set methods are private, a default constructor would serve
		 * no purpose for a user.
		 */
	}

	/*
	 * The following method sums up the valence electron count of every atom in
	 * the molecule.
	 */
	int totalValenceElectrons() {
		int totalValenceElectrons = 0;
		for (int n = 0; n < allAtoms.size(); ++n) {
			totalValenceElectrons += allAtoms.get(n).valenceElectrons();
		}
		return totalValenceElectrons;
	}

	int numberOfElectronPairs() {
		return totalValenceElectrons() / 2;
	}

	void remove(int removalPoint) throws IllegalArgumentException {
		if (allAtoms.size() == 2)
			throw new IllegalArgumentException();
		short numberOfBonds = 0;
		for (int j = 0; j < bondsTo.length - 1; ++j)
			if (bondsTo[removalPoint][j])
				++numberOfBonds;
		if (numberOfBonds > 1)
			throw new IllegalArgumentException("That atom cannot be removed as it is bonded to two other atoms.");
		allAtoms.remove(removalPoint);
		boolean[][] newBonds = new boolean[allAtoms.size() - 1][allAtoms.size() - 2];
		int a = 0, b = 0;
		for (int i = 0; i < bondsTo.length; ++i)
			for (int j = 0; j < bondsTo[i].length; ++j) {
				if (i == removalPoint)
					continue;
				int x = j;
				if (x >= i)
					++x;
				if (x == removalPoint)
					continue;
				newBonds[a++][b++] = bondsTo[i][j];
			}
		bondsTo = newBonds;
	}

	/**
	 * The following method adds a new atom to the list, connecting to exactly
	 * one specified atom already in the molecule.
	 */
	@SuppressWarnings("unchecked")
	void bond(int connectionPoint, Atom newAtom) throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		int connectionPointBondCount = 0;
		for (int e = 0; e < bondsTo[0].length; ++e)
			if (bondsTo[connectionPoint][e])
				++connectionPointBondCount;
		/*
		 * The new number of bonds the connecting atom has must not exceed its
		 * chemical bond limit.
		 */
		if (connectionPointBondCount + 1 > allAtoms.get(connectionPoint).potenialChemicalBonds())
			throw new InvalidChemicalBondException(connectionPointBondCount + 1);
		connect(connectionPoint, newAtom);
		if (connectionPoint + 1 == allAtoms.size())
			allAtoms.add(newAtom);
		else
			allAtoms.add(connectionPoint + 1, newAtom);
		setBonds(super.isConnectedTo());
	}

	/*
	 * The following two dimensional array corresponds to the bondsTo boolean
	 * array. If it is true that one particular atom bonds to another particular
	 * atom, then they are assigned an integer number for the length of their
	 * bond in picometers.
	 */
	int[][] bondLengthsInPicometers() {
		int i, j; // Indexing variables
		int[][] bondLengthsInPicometers = new int[allAtoms.size()][allAtoms.size() - 1];
		for (i = 0; i < bondLengthsInPicometers.length; ++i)
			for (j = 0; j < bondLengthsInPicometers[i].length; ++j)
				switch (/*
						 * The length of the bonds are dependent upon the number
						 * of electron pairs between the two atoms. The more
						 * pairs there are the shorter the bonds get.
						 */electronOrbitConfiguration().electronsInBonds[i][j]) {
				case 2:
					bondLengthsInPicometers[i][j] = 154;
					break;
				case 4:
					bondLengthsInPicometers[i][j] = 133;
					break;
				case 6:
					bondLengthsInPicometers[i][j] = 120;
				}
		return bondLengthsInPicometers;
	}

	// The following contains information regarding the molecular geometry.
	Angle_3DSpace[][] bondingAngles() {
		Angle_3DSpace[][] bondingAngles = new Angle_3DSpace[allAtoms.size()][allAtoms.size() - 1];
		int i, j; // Indexing variables
		for (i = 0; i < bondingAngles.length; ++i) {
			int bondingIndex;
			switch (electronOrbitConfiguration().electronsInOrbit[i]) {
			case 2:
				for (j = 0; j < bondingAngles[i].length; ++j)
					if (bondsTo[i][j])
						try {
							bondingAngles[i][j] = new Angle_3DSpace(null, new AccurateNumber("90", "0"),
									new AccurateNumber("90", "0"));
						} catch (InvalidDimensionUseException ivdue) {

						} catch (Invalid3DAngleInputException e) {

						}
					else
						/*
						 * If the two atoms being examined do not bond to each
						 * other, then there is no angle that needs to be set
						 * for them.
						 */
						bondingAngles[i][j] = null;
				break;
			case 4:
				bondingIndex = 0;
				for (j = 0; j < bondingAngles[i].length; ++j)
					if (bondsTo[i][j])
						switch (bondingIndex++/*
												 * The bondingIndex variable
												 * must contain a post-fix
												 * increment in this case. The
												 * reason is that each bond
												 * needs to be filled following
												 * the rules of molecular
												 * geometry.
												 */) {
						case 0:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(null, new AccurateNumber("90", "0"),
										new AccurateNumber("90", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 1:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(null, new AccurateNumber("270", "0"),
										new AccurateNumber("270", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
						}
					else
						bondingAngles[i][j] = null;
				break;
			case 6:
				bondingIndex = 0;
				for (j = 0; j < bondingAngles[i].length; ++j)
					if (bondsTo[i][j])
						switch (bondingIndex++) {
						case 0:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(null, new AccurateNumber("90", "0"),
										new AccurateNumber("90", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 1:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("180", "0"),
										new AccurateNumber("210", "0"), new AccurateNumber("270", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 2:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("0", "0"),
										new AccurateNumber("330", "0"), new AccurateNumber("270", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
						}
				break;
			case 8:
				bondingIndex = 0;
				for (j = 0; j < bondingAngles[i].length; ++j)
					if (bondsTo[i][j])
						switch (bondingIndex++) {
						case 0:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("90", "0"), null,
										new AccurateNumber("0", "0"));
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 1:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("270", "0"),
										new AccurateNumber("90", "0"), null);
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 2:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("240", "0"),
										new AccurateNumber("210", "0"), null);
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
							break;
						case 3:
							try {
								bondingAngles[i][j] = new Angle_3DSpace(new AccurateNumber("300", "0"),
										new AccurateNumber("330", "0"), null);
							} catch (InvalidDimensionUseException ivdue) {

							} catch (Invalid3DAngleInputException e) {

							}
						}
			}
		}
		return bondingAngles;
	}

	/*
	 * The following one dimensional array contains information about the
	 * location of each atom in a three dimensional coordinate system.
	 */
	@SuppressWarnings("unused")
	Point3D_Space[] molecularCoordinates() throws NumberTooLargeException, InvalidDimensionUseException {
		Point3D_Space[] molecularCoordinates = new Point3D_Space[allAtoms.size()];
		Angle_3DSpace[][] bondingAngles = bondingAngles();
		int i, j; // Indexing variables
		/*
		 * The bonding angles should be rotated to be compatible with the
		 * position of each previous connection.
		 */
		for (i = 0; i < bondingAngles.length; ++i)
			for (j = 0; j < bondingAngles[i].length; ++j) {
				if (!bondsTo[i][j])
					continue;
				int x = j;
				if (x >= i)
					x++;
				else
					continue;
				int index = 0;
				int y = 0;
				while (index < i) {
					if (index++ == x)
						continue;
					y++;
				}
				do {
					AccurateNumber angleDestination, rotationMagnitude;
					try {
						String test0 = bondingAngles[i][j].getXY_Plane_Angle().toString();
						String test1 = bondingAngles[x][y].getXZ_Plane_Angle().toString();
						angleDestination = bondingAngles[i][j].getXY_Plane_Angle().sum(new AccurateNumber("180", "0"));
						for (/*
								 * In this algorithm for rotating the remaining
								 * angles in the two dimensional array, the
								 * integer variable x must begin as it was.
								 * Thus, setting it equal to any other integer
								 * is unnecessary. The same concept holds true
								 * for y.
								 */; x < bondingAngles.length; ++x)
							for (; y < bondingAngles[x].length; ++y) {
								if (!bondsTo[x][y])
									continue;
								rotationMagnitude = (angleDestination
										.difference(bondingAngles[x][y].getXY_Plane_Angle())).absoluteValue();
							}
						/*
						 * The other tests would become unnecessary if this line
						 * of code is reached.
						 */
						continue;
					} catch (NullPointerException n0) {

					}
					try {
						String test0 = bondingAngles[i][j].getXZ_Plane_Angle().toString();
						String test1 = bondingAngles[x][y].getXZ_Plane_Angle().toString();
						continue;
					} catch (NullPointerException n1) {

					}
					try {
						String test0 = bondingAngles[i][j].getYZ_Plane_Angle().toString();
						String test1 = bondingAngles[x][y].getYZ_Plane_Angle().toString();
						continue;
					} catch (NullPointerException n2) {

					}
				} while (false);
			}
		boolean[] coordinateAssigned = new boolean[allAtoms.size()];
		int[][] bondLengthsInPicometers = bondLengthsInPicometers();
		coordinateAssigned[0] = true;
		for (i = 0; i < bondingAngles.length; ++i) {
			if (i == 0)
				molecularCoordinates[0] = new Point3D_Space(new AccurateNumber("500", "0"),
						new AccurateNumber("500", "0"), new AccurateNumber("500", "0"));
			for (j = 0; j < bondingAngles[i].length; ++j) {
				if (!bondsTo[i][j])
					continue;
				int x = j;
				if (x >= i)
					x++;
				if (coordinateAssigned[x])
					continue;
				AccurateNumber[] vector = bondingAngles[i][j].directionalPlaneVector();
				Point3D_Space assignmentPoint = new Point3D_Space();
				assignmentPoint
						.setX_Coordinate(molecularCoordinates[i].getX_Coordinate()
								.sum(vector[0]
										.product(new AccurateNumber(allAtoms.get(x).diameterInPicometers() / 2.0)))
								.sum(vector[0].product(new AccurateNumber(bondLengthsInPicometers[i][j] / 2.0))));
				assignmentPoint
						.setY_Coordinate(molecularCoordinates[i].getY_Coordinate()
								.sum(vector[1]
										.product(new AccurateNumber(allAtoms.get(x).diameterInPicometers() / 2.0)))
								.sum(vector[1].product(new AccurateNumber(bondLengthsInPicometers[i][j] / 2.0))));
				assignmentPoint
						.setZ_Coordinate(molecularCoordinates[i].getZ_Coordinate()
								.sum(vector[2]
										.product(new AccurateNumber(allAtoms.get(x).diameterInPicometers() / 2.0)))
								.sum(vector[2].product(new AccurateNumber(bondLengthsInPicometers[i][j] / 2.0))));
				molecularCoordinates[x] = assignmentPoint;
			}
		}
		return molecularCoordinates;
	}

	/*
	 * The following class stores information regarding the number of electrons
	 * between each atom that is bonded together in the molecule, as well as the
	 * total number of electrons are orbiting around each atom.
	 */

	protected class configuration {
		public short[][] electronsInBonds;
		public short[] electronsInOrbit;

		public configuration(short[][] electronsInBonds, short[] electronsInOrbit) {
			this.electronsInBonds = new short[electronsInBonds.length][electronsInBonds[0].length];
			this.electronsInBonds = electronsInBonds.clone();
			this.electronsInOrbit = new short[electronsInOrbit.length];
			this.electronsInOrbit = electronsInOrbit.clone();
		}
	}

	/**
	 * The following method creates an array, which corresponds to the bondsTo
	 * array. It contains integer values for how many bonds each atom forms with
	 * every other atom in the molecule. For example, if it is false that an
	 * atom is connected to a particular atom, its bond count will be zero. It
	 * also contains integer information about the number of electrons orbiting
	 * each atom in the molecule.
	 */

	public configuration electronOrbitConfiguration() {
		short[][] electronsInBonds = new short[allAtoms.size()][allAtoms.size() - 1];
		/*
		 * The following array ensures than no atom in the molecule receives no
		 * more than its full shell carrying capacity during the electron pair
		 * distribution.
		 */
		short[] electronsInOrbit = new short[allAtoms.size()];
		int i, j; // Indexing variables
		int remainingElectronPairs = numberOfElectronPairs();
		boolean bondsFilled = false;
		short[] previousOrbitCounts = new short[allAtoms.size()];
		boolean fillingStarted = false;
		while (remainingElectronPairs > 0) {
			if (bondsFilled)
				break;
			else {
				if (fillingStarted)
					bondsFilled = true;
				for (i = 0; i < electronsInBonds.length; ++i) {
					if (previousOrbitCounts[i] != electronsInOrbit[i])
						bondsFilled = false;
					previousOrbitCounts[i] = electronsInOrbit[i];
					/**
					 * If each electron orbit count for each atom is consistent,
					 * in that no atoms can receive more electrons in their
					 * bonds, it must be time to start distributing the lone
					 * pairs.
					 */
				}
				for (i = 0; i < electronsInBonds.length; ++i) {
					int bondsActive = 0;
					for (j = 0; j < electronsInBonds[i].length; ++j) {
						bondsActive += (electronsInBonds[i][j] / 2) + (electronsInBonds[i][j] % 2);
					}
					if (bondsActive >= allAtoms.get(i).potenialChemicalBonds())
						continue;
					for (j = 0; j < electronsInBonds[i].length; ++j) {
						fillingStarted = true;
						if (!bondsTo[i][j])
							continue;
						if (electronsInBonds[i][j] == 6)
							// A triple bond is the maximum.
							continue;
						/**
						 * The x and y variables each represent the
						 * corresponding array coordinate to i and j which is
						 * logically equivalent. For example, (3, 1) will be
						 * true if and only if (2, 2) is true. Therefore (3, 1)
						 * and (2, 2) should receive the same electron pair
						 * distributions.
						 */
						int x = j;
						if (x >= i)
							x++;
						int index = 0;
						int y = 0;
						while (index < i) {
							if (index++ == x)
								continue;
							y++;
						}
						/*
						 * Atoms i and x cannot receive any more electrons if
						 * they have reached their full shell. If this is the
						 * case, the loop must be continued.
						 */
						if (electronsInOrbit[i] == allAtoms.get(i).fullShell()
								|| electronsInOrbit[x] == allAtoms.get(x).fullShell())
							continue;
						electronsInBonds[i][j] += 2;
						electronsInBonds[x][y] += 2;
						electronsInOrbit[i] += 2;
						electronsInOrbit[x] += 2;
						remainingElectronPairs--;
					}
				}
			}
		}
		// The lone pairs must be filled.
		for (i = 0; i < allAtoms.size(); ++i) {
			while (electronsInOrbit[i] < allAtoms.get(i).fullShell()) {
				electronsInOrbit[i] += 2;
			}
			if (electronsInOrbit[i] > allAtoms.get(i).fullShell())
				electronsInOrbit[i] -= 2;
		}
		return new configuration(electronsInBonds, electronsInOrbit);
	}

	public ArrayList<Atom> getAllAtoms() {
		ArrayList<Atom> duplicateAllAtoms = allAtoms;
		return duplicateAllAtoms;
	}

	public boolean[][] doesBondTo() {
		boolean[][] duplicateBondsArray = new boolean[bondsTo.length][bondsTo[0].length];
		duplicateBondsArray = bondsTo.clone();
		return duplicateBondsArray;
	}

	private void setAllAtoms(ArrayList<Atom> allAtoms) {
		this.allAtoms = allAtoms;
	}

	private void setBonds(boolean[][] bondsTo) throws InvalidChemicalBondException, InvalidObjectWebSizeException {
		if (bondsTo.length == 0)
			return;
		/*
		 * It is not right for an exception to be thrown if the initial size is
		 * zero.
		 */
		if (bondsTo.length - bondsTo[0].length != 1)
			throw new InvalidObjectWebSizeException(
					"The second dimension of the connections boolean array must be a size one less than that of the first dimension.");
		for (int p = 0; p < bondsTo.length; ++p) {
			short numberOfBonds = 0;
			for (int r = 0; r < bondsTo[p].length; ++r) {
				if (bondsTo[p][r])
					/**
					 * This loop counts the number of atoms a particular atom is
					 * connected to. It increments every time it is evaluated as
					 * 'true' that it is connected to another atom in the list.
					 */
					++numberOfBonds;
			}
			if (numberOfBonds > allAtoms.get(p).potenialChemicalBonds())
				/*
				 * In particle physics, an atom cannot be forced to form bonds
				 * to more atoms than it is capable of.
				 */
				throw new InvalidChemicalBondException(numberOfBonds);
		}
		this.bondsTo = new boolean[bondsTo.length][bondsTo[0].length];
		this.bondsTo = bondsTo.clone();
		setConnections(bondsTo);
	}
}

class InvalidChemicalBondException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidChemicalBondException(int bonds) {
		super("InvalidChemicalBondException : " + bonds);
	}

	public InvalidChemicalBondException(String reason) {
		super(reason);
	}
}
