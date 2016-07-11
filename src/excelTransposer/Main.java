package excelTransposer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) {
		
		String inputName = ""; // TODO : REWORK because ugly af
		int sheetNb = 0;
		final int linesToCopy = 2;
		final int serieNb = 3;
		
		
		switch (args.length) {
		case 1:
			inputName = args[0];
			break;
		case 2:
			inputName = args[0];
			sheetNb = Integer.parseInt(args[1]);
			break;
		default:
			
			break;	
		}
		
		String[] parts = inputName.split("\\.");
		String outputName = parts[0] + "Transposed.xlsx";
		
		try {
			FileInputStream fis = new FileInputStream(new File(inputName));
			XSSFWorkbook iWorkbook = new XSSFWorkbook(fis);
		    XSSFSheet iSheet = iWorkbook.getSheetAt(sheetNb);
		    XSSFWorkbook oWorkbook = new XSSFWorkbook();
		    XSSFSheet oSheet = oWorkbook.createSheet(iSheet.getSheetName());
		    
		    
		    SheetCouple duo = new SheetCouple(iSheet, oSheet, linesToCopy);
		    
		    duo.getInputFile().setSerieNb(serieNb);
		    
		    duo.copy(0, 0, linesToCopy);
		    
		    duo.writeHeader(linesToCopy);
		    
		    duo.writeBody();
		    
		    duo.insertComments();
		    
		    XSSFSheet iSuppSheet;
		    
		    if ((iSuppSheet = iWorkbook.getSheet("suppression")) != null) {
		    	
		    	XSSFSheet oSuppSheet = oWorkbook.createSheet("suppression");
		    	SheetCouple duoSupp = new SheetCouple(iSuppSheet, oSuppSheet, linesToCopy);
		    	
		    	duoSupp.getInputFile().setSerieNb(serieNb);
			    
			    duoSupp.copy(0, 0, linesToCopy);
			    
			    duoSupp.writeHeader(linesToCopy);
			    
			    duoSupp.writeBody();
			    
			    duoSupp.insertComments();
			    
		    }
		    
		    
		    FileOutputStream out = new FileOutputStream(new File(outputName));
			oWorkbook.write(out);
			out.close();
			iWorkbook.close();
			oWorkbook.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + inputName + " not found.");
			System.out.println("Cancelling transposition of this file.");
		} catch (IOException e) {
			System.out.println("Unknown exception raised for file " + inputName + ".");
			System.out.println("Cancelling transposition of this file.");
			System.out.println("Stack Trace following : ");
			e.printStackTrace();
		} 
		

	}

}
