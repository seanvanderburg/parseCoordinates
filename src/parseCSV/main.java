package parseCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PVector;

/**
 * Small tool to process large CSV-dataset and only output coordinates within range from x,y center
 * designed to use rotterdamOpendata height file (east)
 * @author seanv_000
 */
public class main {

	public static ArrayList<PVector> coords;
	public static float centerPointX = 92796f;
	public static float centerPointY = 436960f;
	public static float rangeX = 1000;
	public static float rangeY = 1000;	
	private static BufferedReader csvReader;
	static ArrayList<PVector> dataSubSet;

	public static void main(String[] args) {
		try {
			System.out.println("CSV parsing...");
			dataSubSet = parseCSV();
			System.out.println("CSV writing...");
			generateCSVFile("C:/Users/seanv_000/Desktop/dataSubset.csv");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateCSVFile(String filename) throws IOException {
		FileWriter writer = new FileWriter(filename);
		String csvdata = dataSubSet.toString().replace("[", "")
				.replace("],", "\n").replace(", ", ",");
		writer.append(csvdata);

		writer.flush();
		writer.close();
	}

	public static ArrayList<PVector> parseCSV() throws FileNotFoundException,
			IOException {
		csvReader = new BufferedReader(
				new FileReader(
						new File(
								"C:/Users/seanv_000/Desktop/rotterdamopendata_hoogtebestandtotaal_oost.csv")));

		csvReader.readLine();
		csvReader.readLine();

		String dataline;

		coords = new ArrayList<>();

		while ((dataline = csvReader.readLine()) != null) {
			String[] splittedCoords = dataline.split(",");

			float xCoord = Float.parseFloat(splittedCoords[0]);
			float yCoord = Float.parseFloat(splittedCoords[1]);
			float zCoord = Float.parseFloat(splittedCoords[2]);

			PVector currentCoord = new PVector(xCoord, yCoord,
					zCoord);

			float sumX = Math.abs(centerPointX - currentCoord.x);
			float sumY = Math.abs(centerPointY - currentCoord.y);

			if (sumX < rangeX && sumY < rangeY) {
				coords.add(currentCoord);

			}
		}

		return coords;
	}

}
