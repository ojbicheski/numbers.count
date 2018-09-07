/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @author orlei
 *
 */
public class CounterFile {
	
	private String fileName;
	
	private BufferedReader in; 
	
	private String line;
	

	/**
	 * @param fileName the fileName to set
	 */
	public CounterFile fileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	
	public void open() throws FileNotFoundException {
		if (!validField(fileName)) {
			throw new IllegalArgumentException("Field filename is required.");
		}
		
		in = new BufferedReader(new FileReader(getClass().getResource(fileName).getFile()));
	}
	
	public boolean hasNext() throws IOException {
		line = in.readLine();
		
		return Objects.nonNull(line) && !line.isEmpty();
	}
	
	public String next() throws IOException {
		if (Objects.nonNull(line) && !line.isEmpty()) {
			return line;
		}
		
		throw new IOException("EOF");
	}
	
	public void close() throws IOException {
		in.close();
	}

	private boolean validField(String field) {
		if (Objects.isNull(field)) {
			return false;
		}
		if (field.isEmpty()) {
			return false;
		}
		return true;
	}

}
