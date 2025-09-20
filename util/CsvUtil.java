package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

	private static final String CSV_DELIMITER = ",";

	public List<String[]> read(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;

			reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] row = line.split(CSV_DELIMITER);
				data.add(row);
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + filePath);
			e.printStackTrace();
		}
		return data;
	}

	public void write(String filePath, List<String[]> data, String[] header) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(String.join(CSV_DELIMITER, header));
			writer.newLine();

			for (String[] row : data) {
				writer.write(String.join(CSV_DELIMITER, row));
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " + filePath);
			e.printStackTrace();
		}
	}
}