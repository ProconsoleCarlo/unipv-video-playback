package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
/**
 * Una classe che gestisce la lettura e la scrittura su file.
 *
 */
public class FileLoaderWriter {
	
	/**
	 * Legge un file di testo e restituisce il suo contenuto in un ArrayList<String>.
	 * @param file Il file da leggere
	 * @return Le righe del file da leggere
	 */
	public ArrayList<String> load(File file) {
		ArrayList<String> fileLines = new ArrayList<String>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				fileLines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileLines;
	}
	
	/**
	 * Scrive un file di testo avendo in input un ArrayList<String> contenente le righe da scrivere.
	 * @param fileLines Le righe da scrivere
	 * @param file Il file da scrivere
	 */
	public void write(ArrayList<String> fileLines, File file){
		try {
			PrintStream output = new PrintStream(file);
			for (int i = 0; i < fileLines.size(); i++) {
				output.println(fileLines.get(i));
			}
			output.close();
		}
		catch (IOException e) {
			System.err.println("Errore: " + e);
		}
	}
	
	/**
	 * Permette di aggiungere una riga di testo a un file gia' esistente.
	 * @param line linea da aggiungere
	 * @param file file sul quale eseguire l'operazione
	 */
	public void addLine(String line, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			PrintStream output = new PrintStream(fos);
			output.println(line);
			output.close();
		}
		catch (IOException e) {
			System.err.println("Errore: " + e);
		}
	}
}
