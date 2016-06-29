package excelTransposer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testTools.CellArray;
import testTools.StringArray;

public class Main {

	public static void main(String[] args) throws IOException {

		String inputName = "exemplePerso1.xlsx";
		String outputName = "sortie1.xlsx";
		int linesToCopy = 2;
		int serieNb = 3;
		
		FileInputStream fis = new FileInputStream(new File(inputName));
		XSSFWorkbook iWorkbook = new XSSFWorkbook(fis);
	    XSSFSheet iSheet = iWorkbook.getSheetAt(0);
	    
	    InputFile input = new InputFile(inputName, iSheet, linesToCopy);
	    
	    XSSFWorkbook oWorkbook = new XSSFWorkbook();
	    XSSFSheet oSheet = oWorkbook.createSheet("default");
	    
	    OutputFile output = new OutputFile(outputName, oSheet);
	    
	    Tools T = new Tools(iSheet, oSheet);
	    
	    T.copy(0, 0, linesToCopy);
	    
	    input.setCurrentLine(linesToCopy); // Useless ?
	    output.setCurrentLine(linesToCopy); // Useless ?
	    
	    input.setHeader(T.extractLine(linesToCopy));
	    
	    CellArray cA = new CellArray(input.getHeader()); // TODO : TBR
	    cA.print(); // TODO : TBR

	    
	    output.divideHeader(input.getHeader(), serieNb);
	    input.incrLine(); // Useless?
	    
	    CellArray serieHeader = new CellArray(output.getSerieHeader());
	    serieHeader.print();
	    
	    CellArray years = new CellArray(output.getYears());// TODO : TBR
	    years.print(); // TODO : TBR
	    
	    
	    
	    
	    T.writeLine(linesToCopy, output.getSerieHeader(), OutputFile.serieEnd);


	    
	    
	    
	    FileOutputStream out = new FileOutputStream( 
  				new File(outputName));
				oWorkbook.write(out);
				out.close();
				System.out.println( 
				outputName + " written successfully" );
	    
	    
	}

}
