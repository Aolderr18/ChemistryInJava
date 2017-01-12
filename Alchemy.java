import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * This program starts in the main method of a subclass of Application. It
 * creates a GUI in which the user can create any molecule possible to exist in
 * nature. It uses several interacting classes to follow the rules of chemistry.
 */
public final class Alchemy extends Application {
	private Pane primaryPane;
	private Rectangle[] elementSymbols;
	private static Atom previousAtom;
	private static Atom sequentialAtom;
	private Molecule inputMolecule, outputMolecule;
	private static char mode;
	private static boolean inputMoleculeCreated, outputMoleculeCreated;
	private Point3D_Space[] inputMolecularCoordinates, outputMolecularCoordinates;
	private static ArrayList<Circle> inputMoleculePositions;
	private static ArrayList<Circle> outputMoleculePositions;
	private Pane secondaryPane;
	private Pane tertiaryPane;
	private static AccurateNumber secondarySceneXZ, tertiarySceneXZ;

	public void start(Stage primaryStage) {
		elementSymbols = new Rectangle[118];
		int width = 18;
		int height = 10;
		for (int j = 0; j < height; ++j)
			for (int i = 0; i < width; ++i) {
				// The periodic table is color coded.
				switch (j) {
				case 0:
					switch (i) {
					case 0:
						elementSymbols[0] = new Rectangle(0, 0, 50, 50);
						elementSymbols[0].setFill(Color.LIMEGREEN);
						elementSymbols[0].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[1] = new Rectangle(850, 0, 50, 50);
						elementSymbols[1].setFill(Color.PINK);
						elementSymbols[1].setStroke(Color.BLACK);
					}
					break;
				case 1:
					switch (i) {
					case 0:
						elementSymbols[2] = new Rectangle(0, 50, 50, 50);
						elementSymbols[2].setFill(Color.ORANGE);
						elementSymbols[2].setStroke(Color.BLACK);
						break;
					case 1:
						elementSymbols[3] = new Rectangle(50, 50, 50, 50);
						elementSymbols[3].setFill(Color.YELLOW);
						elementSymbols[3].setStroke(Color.BLACK);
						break;
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
						elementSymbols[i - 8] = new Rectangle(600 + (50 * (i - 12)), 50, 50, 50);
						elementSymbols[i - 8].setFill(Color.LIMEGREEN);
						elementSymbols[i - 8].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[9] = new Rectangle(850, 50, 50, 50);
						elementSymbols[9].setFill(Color.PINK);
						elementSymbols[9].setStroke(Color.BLACK);
					}
					break;
				case 2:
					switch (i) {
					case 0:
						elementSymbols[10] = new Rectangle(0, 100, 50, 50);
						elementSymbols[10].setFill(Color.ORANGE);
						elementSymbols[10].setStroke(Color.BLACK);
						break;
					case 1:
						elementSymbols[11] = new Rectangle(50, 100, 50, 50);
						elementSymbols[11].setFill(Color.YELLOW);
						elementSymbols[11].setStroke(Color.BLACK);
						break;
					case 12:
						elementSymbols[12] = new Rectangle(600, 100, 50, 50);
						elementSymbols[12].setFill(Color.CYAN);
						elementSymbols[12].setStroke(Color.BLACK);
						break;
					case 13:
					case 14:
					case 15:
					case 16:
						elementSymbols[i] = new Rectangle(650 + (50 * (i - 13)), 100, 50, 50);
						elementSymbols[i].setFill(Color.LIMEGREEN);
						elementSymbols[i].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[17] = new Rectangle(850, 100, 50, 50);
						elementSymbols[17].setFill(Color.PINK);
						elementSymbols[17].setStroke(Color.BLACK);
					}
					break;
				case 3:
					elementSymbols[i + 18] = new Rectangle(50 * i, 150, 50, 50);
					switch (i) {
					case 0:
						elementSymbols[i + 18].setFill(Color.ORANGE);
						break;
					case 1:
						elementSymbols[i + 18].setFill(Color.YELLOW);
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
						elementSymbols[i + 18].setFill(Color.ROYALBLUE);
						break;
					case 11:
					case 12:
					case 13:
						elementSymbols[i + 18].setFill(Color.CYAN);
						break;
					case 14:
					case 15:
					case 16:
						elementSymbols[i + 18].setFill(Color.LIMEGREEN);
						break;
					case 17:
						elementSymbols[i + 18].setFill(Color.PINK);
					}
					elementSymbols[i + 18].setStroke(Color.BLACK);
					break;
				case 4:
					switch (i) {
					case 0:
						elementSymbols[i + 36] = new Rectangle(0, 200, 50, 50);
						elementSymbols[i + 36].setFill(Color.ORANGE);
						elementSymbols[i + 36].setStroke(Color.BLACK);
						break;
					case 1:
						elementSymbols[i + 36] = new Rectangle(50, 200, 50, 50);
						elementSymbols[i + 36].setFill(Color.YELLOW);
						elementSymbols[i + 36].setStroke(Color.BLACK);
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
						elementSymbols[i + 36] = new Rectangle(50 * i, 200, 50, 50);
						elementSymbols[i + 36].setFill(Color.ROYALBLUE);
						elementSymbols[i + 36].setStroke(Color.BLACK);
						break;
					case 11:
					case 12:
					case 13:
					case 14:
						elementSymbols[i + 36] = new Rectangle(50 * i, 200, 50, 50);
						elementSymbols[i + 36].setFill(Color.CYAN);
						elementSymbols[i + 36].setStroke(Color.BLACK);
						break;
					case 15:
					case 16:
						elementSymbols[i + 36] = new Rectangle(50 * i, 200, 50, 50);
						elementSymbols[i + 36].setFill(Color.LIMEGREEN);
						elementSymbols[i + 36].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[53] = new Rectangle(850, 200, 50, 50);
						elementSymbols[53].setFill(Color.PINK);
						elementSymbols[53].setStroke(Color.BLACK);
					}
					break;
				case 5:
					switch (i) {
					case 0:
						elementSymbols[54] = new Rectangle(0, 250, 50, 50);
						elementSymbols[54].setFill(Color.ORANGE);
						elementSymbols[54].setStroke(Color.BLACK);
						break;
					case 1:
						elementSymbols[55] = new Rectangle(50, 250, 50, 50);
						elementSymbols[55].setFill(Color.YELLOW);
						elementSymbols[55].setStroke(Color.BLACK);
						break;
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
						elementSymbols[i + 68] = new Rectangle(50 * i, 250, 50, 50);
						elementSymbols[i + 68].setFill(Color.ROYALBLUE);
						elementSymbols[i + 68].setStroke(Color.BLACK);
						break;
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
						elementSymbols[i + 68] = new Rectangle(50 * i, 250, 50, 50);
						elementSymbols[i + 68].setFill(Color.CYAN);
						elementSymbols[i + 68].setStroke(Color.BLACK);
						break;
					case 16:
						elementSymbols[84] = new Rectangle(800, 250, 50, 50);
						elementSymbols[84].setFill(Color.LIMEGREEN);
						elementSymbols[84].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[85] = new Rectangle(850, 250, 50, 50);
						elementSymbols[85].setFill(Color.PINK);
						elementSymbols[85].setStroke(Color.BLACK);
					}
					break;
				case 6:
					switch (i) {
					case 0:
						elementSymbols[86] = new Rectangle(0, 300, 50, 50);
						elementSymbols[86].setFill(Color.ORANGE);
						elementSymbols[86].setStroke(Color.BLACK);
						break;
					case 1:
						elementSymbols[87] = new Rectangle(50, 300, 50, 50);
						elementSymbols[87].setFill(Color.YELLOW);
						elementSymbols[87].setStroke(Color.BLACK);
						break;
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
						elementSymbols[100 + i] = new Rectangle(50 * i, 300, 50, 50);
						elementSymbols[100 + i].setFill(Color.ROYALBLUE);
						elementSymbols[100 + i].setStroke(Color.BLACK);
						break;
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
						elementSymbols[100 + i] = new Rectangle(50 * i, 300, 50, 50);
						elementSymbols[100 + i].setFill(Color.CYAN);
						elementSymbols[100 + i].setStroke(Color.BLACK);
						break;
					case 17:
						elementSymbols[117] = new Rectangle(850, 300, 50, 50);
						elementSymbols[117].setFill(Color.PINK);
						elementSymbols[117].setStroke(Color.BLACK);
					}
					break;
				case 8:
					if (i > 2) {
						elementSymbols[i + 53] = new Rectangle(50 * i, 400, 50, 50);
						elementSymbols[i + 53].setFill(Color.VIOLET);
						elementSymbols[i + 53].setStroke(Color.BLACK);
					}
					break;
				case 9:
					if (i > 2) {
						elementSymbols[i + 85] = new Rectangle(50 * i, 450, 50, 50);
						elementSymbols[i + 85].setFill(Color.YELLOWGREEN);
						elementSymbols[i + 85].setStroke(Color.BLACK);
					}
				}
			}
		Text[] correspondingSymbols = new Text[118];
		primaryPane = new Pane();
		Button modeSelector = new Button();
		modeSelector.setPrefSize(230.0, 75.0);
		modeSelector.setTranslateX(100.0);
		switch (mode) {
		case 'A':
			modeSelector.setText("Remove");
			primaryStage.setTitle("Mode is \"Add\"");
			break;
		case 'R':
			modeSelector.setText("Add");
			primaryStage.setTitle("Mode is \"Remove\"");
		}
		primaryPane.getChildren().add(modeSelector);
		for (int r = 0; r < elementSymbols.length; ++r) {
			for (String key : ChemicalElement.symbols().keySet()) {
				if (ChemicalElement.symbols().get(key) == ChemicalElement.lookUpChemicalElementByOrdinal(r + 1))
					correspondingSymbols[r] = new Text(key);
			}
			correspondingSymbols[r].setX(10 + elementSymbols[r].getX());
			correspondingSymbols[r].setY(15 + elementSymbols[r].getY());
			primaryPane.getChildren().add(elementSymbols[r]);
			primaryPane.getChildren().add(correspondingSymbols[r]);
		}
		Scene primaryScene = new Scene(primaryPane);
		primaryStage.setScene(primaryScene);
		if (!inputMoleculeCreated)
			JOptionPane.showMessageDialog(null, "Please select two atoms to begin creation of the first molecule.");
		else if (!outputMoleculeCreated)
			JOptionPane.showMessageDialog(null, "Please select two atoms to begin creation of the second molecule.");
		modeSelector.setOnMouseClicked(e -> {
			switch (mode) {
			case 'A':
				mode = 'R';
				primaryStage.setTitle("Mode is \"Remove\"");
				modeSelector.setText("Add");
				break;
			case 'R':
				mode = 'A';
				primaryStage.setTitle("Mode is \"Add\"");
				modeSelector.setText("Remove");
			}
		});
		primaryScene.setOnMouseClicked(f -> {
			for (int search = 0; search < 118; ++search) {
				if (elementSymbols[search].contains(new Point2D(f.getSceneX(), f.getSceneY())))
					try {
						Atom selection = new Atom(search + 1);
						try {
							String test0 = previousAtom.toString();
							sequentialAtom = selection;
							if (!inputMoleculeCreated) {
								ArrayList<Atom> listA = new ArrayList<Atom>();
								listA.add(previousAtom);
								listA.add(sequentialAtom);
								boolean[][] temp = new boolean[2][1];
								temp[0][0] = true;
								temp[1][0] = true;
								try {
									inputMolecule = new Molecule(listA, temp);
									inputMoleculeCreated = true;
									try {
										inputMolecularCoordinates = inputMolecule.molecularCoordinates();
										inputMoleculePositions.add(atomicImage(previousAtom));
										inputMoleculePositions.add(atomicImage(sequentialAtom));
									} catch (Exception e0) {

									}
								} catch (InvalidChemicalBondException ivcbe) {
									JOptionPane.showMessageDialog(null,
											"Sorry, those two atoms cannot be bonded together.");
									JOptionPane.showMessageDialog(null, "Please make a different selection.");
								} catch (InvalidObjectWebSizeException ivowse) {

								}
								previousAtom = null;
								sequentialAtom = null;
								primaryStage.close();
								start(new Stage());
							} else if (!outputMoleculeCreated) {
								ArrayList<Atom> listB = new ArrayList<Atom>();
								listB.add(previousAtom);
								listB.add(sequentialAtom);
								boolean[][] temp = new boolean[2][1];
								temp[0][0] = true;
								temp[1][0] = true;
								try {
									outputMolecule = new Molecule(listB, temp);
									outputMoleculeCreated = true;
									try {
										outputMolecularCoordinates = outputMolecule.molecularCoordinates();
										outputMoleculePositions.add(atomicImage(previousAtom));
										outputMoleculePositions.add(atomicImage(sequentialAtom));
									} catch (Exception e0) {

									}
								} catch (InvalidChemicalBondException ivcbe) {
									JOptionPane.showMessageDialog(null,
											"Sorry, those two atoms cannot be bonded together.");
									JOptionPane.showMessageDialog(null, "Please make a different selection.");
									previousAtom = null;
									sequentialAtom = null;
									primaryStage.close();
									start(new Stage());
								} catch (InvalidObjectWebSizeException ivowse) {
									previousAtom = null;
									sequentialAtom = null;
									primaryStage.close();
									start(new Stage());
								}
								primaryStage.close();
								start(new Stage());
							}
						} catch (NullPointerException npe1) {
							previousAtom = selection;
						}
					} catch (InvalidAtomicNumberException ivane) {

					}
			}
		});
		Stage secondaryStage = new Stage();
		secondaryPane = new Pane();
		Scene secondaryScene = new Scene(secondaryPane);
		Stage tertiaryStage = new Stage();
		tertiaryPane = new Pane();
		Scene tertiaryScene = new Scene(tertiaryPane);
		secondaryStage.setScene(secondaryScene);
		tertiaryStage.setScene(tertiaryScene);
		secondaryScene.setFill(Color.BLACK);
		tertiaryScene.setFill(Color.BLACK);
		secondaryScene.setOnKeyReleased(e -> {
			ArrayList<Point3D_Space> secondaryScenePoints = new ArrayList<Point3D_Space>();
			int h;
			for (h = 0; h < inputMolecularCoordinates.length; ++h)
				secondaryScenePoints.add(inputMolecularCoordinates[h]);
			Point3D_System secondaryScenePointSystem = new Point3D_System(secondaryScenePoints);
			try {
				switch (e.getCode()) {
				case UP:
					secondaryScenePointSystem.rotateYZ(new AccurateNumber("358", "0"));
					break;
				case DOWN:
					secondaryScenePointSystem.rotateYZ(new AccurateNumber("2", "0"));
					break;
				case RIGHT:
					secondaryScenePointSystem.rotateXZ(new AccurateNumber("358", "0"));
					break;
				case LEFT:
					secondaryScenePointSystem.rotateXZ(new AccurateNumber("2", "0"));
				}
				for (h = 0; h < inputMolecularCoordinates.length; ++h)
					inputMolecularCoordinates[h] = secondaryScenePoints.get(h);
				setPositions();
			} catch (NumberTooLargeException ntle) {

			}
		});
		tertiaryScene.setOnKeyReleased(e -> {
			ArrayList<Point3D_Space> tertiaryScenePoints = new ArrayList<Point3D_Space>();
			int h;
			for (h = 0; h < outputMolecularCoordinates.length; ++h)
				tertiaryScenePoints.add(outputMolecularCoordinates[h]);
			Point3D_System tertiaryScenePointSystem = new Point3D_System(tertiaryScenePoints);
			try {
				switch (e.getCode()) {
				case UP:
					tertiaryScenePointSystem.rotateYZ(new AccurateNumber("358", "0"));
					break;
				case DOWN:
					tertiaryScenePointSystem.rotateYZ(new AccurateNumber("2", "0"));
					break;
				case RIGHT:
					tertiaryScenePointSystem.rotateXZ(new AccurateNumber("358", "0"));
					break;
				case LEFT:
					tertiaryScenePointSystem.rotateXZ(new AccurateNumber("2", "0"));
				}
				for (h = 0; h < outputMolecularCoordinates.length; ++h)
					outputMolecularCoordinates[h] = tertiaryScenePoints.get(h);
			} catch (NumberTooLargeException ntle) {

			}
		});
		secondaryScene.setOnMouseClicked(e -> {
			final int moleculeSize = inputMoleculePositions.size();
			for (short point = 0; point < moleculeSize; ++point) {
				if (inputMoleculePositions.get(point).contains(new Point2D(e.getSceneX(), e.getSceneY())))
					switch (mode) {
					case 'R':
						try {
							inputMolecule.remove(point);
							inputMoleculePositions.remove(inputMoleculePositions.get(point));
						} catch (IllegalArgumentException ilae) {

						}
						break;
					case 'A':
						try {
							inputMolecule.bond(point, sequentialAtom);
							inputMolecularCoordinates = inputMolecule.molecularCoordinates();
							inputMoleculePositions.add(point, atomicImage(sequentialAtom));
						} catch (NumberTooLargeException | InvalidDimensionUseException | InvalidObjectWebSizeException
								| InvalidChemicalBondException e0) {

						} finally {
							secondaryStage.close();
							start(new Stage());
						}
					}
				++point;
			}
		});
		tertiaryScene.setOnMouseClicked(e -> {
			final int moleculeSize = outputMoleculePositions.size();
			for (short point = 0; point < moleculeSize; ++point) {
				if (outputMoleculePositions.get(point).contains(new Point2D(e.getSceneX(), e.getSceneY())))
					switch (mode) {
					case 'R':
						try {
							outputMolecule.remove(point);
							outputMoleculePositions.remove(outputMoleculePositions.get(point));
						} catch (IllegalArgumentException ilae) {

						}
						break;
					case 'A':
						try {
							outputMolecule.bond(point, sequentialAtom);
							outputMolecularCoordinates = outputMolecule.molecularCoordinates();
							outputMoleculePositions.add(point, atomicImage(sequentialAtom));
						} catch (NumberTooLargeException | InvalidDimensionUseException | InvalidObjectWebSizeException
								| InvalidChemicalBondException e0) {

						} finally {
							tertiaryStage.close();
							start(new Stage());
						}
					}
				++point;
			}
		});
		Button sceneSwitchPS = new Button("Input");
		Button sceneSwitchPT = new Button("Output");
		Button sceneSwitchSP = new Button("Table");
		Button sceneSwitchST = new Button("Output");
		Button sceneSwitchTP = new Button("Table");
		Button sceneSwitchTS = new Button("Input");
		sceneSwitchPS.setPrefSize(120.0, 60.0);
		sceneSwitchPT.setPrefSize(120.0, 60.0);
		sceneSwitchSP.setPrefSize(120.0, 60.0);
		sceneSwitchST.setPrefSize(120.0, 60.0);
		sceneSwitchTP.setPrefSize(120.0, 60.0);
		sceneSwitchTS.setPrefSize(120.0, 60.0);
		sceneSwitchPS.setTranslateX(330.0);
		sceneSwitchPT.setTranslateX(450.0);
		sceneSwitchSP.setTranslateX(330.0);
		sceneSwitchST.setTranslateX(450.0);
		sceneSwitchTP.setTranslateX(330.0);
		sceneSwitchTS.setTranslateX(450.0);
		sceneSwitchPS.setOnMouseClicked(e -> {
			primaryStage.close();
			secondaryStage.show();
		});
		sceneSwitchPT.setOnMouseClicked(e -> {
			primaryStage.close();
			tertiaryStage.show();
		});
		sceneSwitchSP.setOnMouseClicked(e -> {
			secondaryStage.close();
			primaryStage.show();
		});
		sceneSwitchST.setOnMouseClicked(e -> {
			secondaryStage.close();
			tertiaryStage.show();
		});
		sceneSwitchTP.setOnMouseClicked(e -> {
			tertiaryStage.close();
			primaryStage.show();
		});
		sceneSwitchTS.setOnMouseClicked(e -> {
			tertiaryStage.close();
			secondaryStage.show();
		});
		primaryPane.getChildren().add(sceneSwitchPS);
		primaryPane.getChildren().add(sceneSwitchPT);
		secondaryPane.getChildren().add(sceneSwitchSP);
		secondaryPane.getChildren().add(sceneSwitchST);
		tertiaryPane.getChildren().add(sceneSwitchTP);
		tertiaryPane.getChildren().add(sceneSwitchTS);
		primaryScene.setFill(Color.BLACK);
		secondaryScene.setFill(Color.BLACK);
		tertiaryScene.setFill(Color.BLACK);
		setPositions();
		primaryStage.show();
	}

	protected void setPositions() {
		short s = 0;
		for (Circle im : inputMoleculePositions) {
			secondaryPane.getChildren().remove(im);
			try {
				double I = inputMolecularCoordinates[s].getX_Coordinate().asDouble();
				double J = inputMolecularCoordinates[s].getY_Coordinate().asDouble();
				im.setCenterX(I);
				im.setCenterY(J);
				if (inputMolecularCoordinates[s].getZ_Coordinate().isLessThan(new AccurateNegativeNumber("25", "0")))
					throw new ComponentNotVisibleException();
				AccurateNumber distanceFromViewer = new AccurateNumber("25", "0")
						.difference(inputMolecularCoordinates[s].getZ_Coordinate()).absoluteValue();
				double size = new AccurateNumber("25", "0").quotient(distanceFromViewer).asDouble();
				size *= 1000.0;
				im.setRadius(size);
				secondaryPane.getChildren().add(im);
			} catch (NumberTooLargeException ntle) {

			} catch (ComponentNotVisibleException cnve) {

			}
			s++;
		}
		s = 0;
		for (Circle om : outputMoleculePositions) {
			tertiaryPane.getChildren().remove(om);
			try {
				double I = outputMolecularCoordinates[s].getX_Coordinate().asDouble();
				double J = outputMolecularCoordinates[s].getY_Coordinate().asDouble();
				om.setCenterX(I);
				om.setCenterY(J);
				if (outputMolecularCoordinates[s].getZ_Coordinate().isLessThan(new AccurateNegativeNumber("25", "0")))
					throw new ComponentNotVisibleException();
				AccurateNumber distanceFromViewer = new AccurateNumber("25", "0")
						.difference(outputMolecularCoordinates[s].getZ_Coordinate()).absoluteValue();
				double size = new AccurateNumber("25", "0").quotient(distanceFromViewer).asDouble();
				size *= 1000.0;
				om.setRadius(size);
				tertiaryPane.getChildren().add(om);
			} catch (NumberTooLargeException ntle) {

			} catch (ComponentNotVisibleException cnve) {

			}
			s++;
		}
	}

	protected Circle atomicImage(Atom a) {
		Circle atomicImage = new Circle(a.diameterInPicometers() / 2.0);
		atomicImage.setStroke(Color.WHITE);
		switch (a.getAtomicNumber() % 8) {
		case 0:
			atomicImage.setFill(Color.CYAN);
			break;
		case 1:
			atomicImage.setFill(Color.RED);
			break;
		case 2:
			atomicImage.setFill(Color.PINK);
			break;
		case 3:
			atomicImage.setFill(Color.GRAY);
			break;
		case 4:
			atomicImage.setFill(Color.ORANGE);
			break;
		case 5:
			atomicImage.setFill(Color.BEIGE);
			break;
		case 6:
			atomicImage.setFill(Color.TAN);
			break;
		case 7:
			atomicImage.setFill(Color.GREEN);
		}
		if (a.elementType() == ChemicalElement.Sulfur)
			atomicImage.setFill(Color.YELLOW);
		else if (a.elementType() == ChemicalElement.Silver)
			atomicImage.setFill(Color.SILVER);
		else if (a.elementType() == ChemicalElement.Gold)
			atomicImage.setFill(Color.GOLD);
		return atomicImage;
	}

	public static void main(String[] args) {
		inputMoleculeCreated = false;
		outputMoleculeCreated = false;
		previousAtom = null;
		sequentialAtom = null;
		inputMoleculePositions = new ArrayList<Circle>();
		outputMoleculePositions = new ArrayList<Circle>();
		mode = 'A';
		secondarySceneXZ = new AccurateNumber("0", "0");
		tertiarySceneXZ = new AccurateNumber("0", "0");
		launch(args);
	}
}

class ComponentNotVisibleException extends Exception {
	private static final long serialVersionUID = 1L;

	public ComponentNotVisibleException() {

	}
}
