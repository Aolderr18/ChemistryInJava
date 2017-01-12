public class Isotope extends Atom {

	/*
	 * By definition, an isotope is any variation of any atom with a certain
	 * neutron count.
	 */
	private int neutronCount;

	public Isotope(int atomicNumber, int neutronCount) throws InvalidAtomicNumberException {
		super(atomicNumber);
	}

	public Isotope(Atom isotope, int neutronCount) throws InvalidAtomicNumberException {
		super(isotope.getAtomicNumber());
	}

	public Isotope(ChemicalElement element, int neutronCount) throws InvalidAtomicNumberException {
		super(element);
	}

	public int getNeutronCount() {
		return neutronCount;
	}

	public void setNeutronCount(int neutronCount) {
		this.neutronCount = neutronCount;
	}
}
