package excelTransposer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testTools.CellArray;
import testTools.StringArray;

public class Main {

	public static void main(String[] args) throws IOException {

		String inputName = "exemplePerso2.xlsx";
		String[] parts = inputName.split("\\.");
		String outputName = parts[0] + "OUT.xlsx";
		final int linesToCopy = 2;
		final int serieNb = 3;
		
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
	    
	    input.findFirstBlankColumn(linesToCopy + 1, serieNb);
	    T.setLastColumn(input.getColumnLimit());
	    input.setHeader(T.extractLine(linesToCopy));
	    
	    CellArray cA = new CellArray(input.getHeader()); // TODO : TBR
	    cA.print(); // TODO : TBR

	    
	    output.divideHeader(input.getHeader(), serieNb, input.getLastPeriod() + 1);
	    input.incrLine(); // Useless?
	    
	    CellArray serieHeader = new CellArray(output.getLeftHeader()); // TODO : TBR
	    serieHeader.print(); // TODO : TBR
	    
	    
	    CellArray years = new CellArray(output.getYears());// TODO : TBR
	    years.print(); // TODO : TBR
	    
	    
	    
	    
	    T.writeLine(linesToCopy, output.getLeftHeader(), OutputFile.serieEnd, output.getRightHeader());

	    System.out.println(serieNb + " " + (T.getLastColumn() - input.getLastPeriod()) + " " + (input.getLastPeriod() - serieNb));
	    
	    output.setLeftHeader(T.extractLine(3, 0, serieNb - 1));
//	    System.out.println("leftHeader fait, values inc");
	    
	    output.setValues(T.extractLine(3, serieNb, input.getLastPeriod()));
	    System.out.println(input.getColumnLimit());
	    output.setRightHeader(T.extractLine(3, input.getLastPeriod() + 1, input.getColumnLimit()));

	    CellArray yearsValue1 = new CellArray(output.getValues());
	    yearsValue1.print();
	    
	    Cell[] line = new Cell[serieNb + 2 + output.getRightHeader().length];
	    System.out.println("line de longueur " + line.length);
	    
	    Tools.fill(line, output.getLeftHeader(), 0);
	    Tools.fill(line, output.getRightHeader(), serieNb + 2);
	    
	    for (int i = 0; i < output.getValues().length; i++) {
	    	
	    	line[serieNb] = output.getYears()[i];
	    	line[serieNb + 1] = output.getValues()[i];
	    	int j = line[i].getCellType();
	    	T.writeLine(serieNb + i, line);

	    }
	    
	    FileOutputStream out = new FileOutputStream( 
  				new File(outputName));
				oWorkbook.write(out);
				out.close();
				System.out.println( 
				outputName + " written successfully" );
	    
	    
	}

}
