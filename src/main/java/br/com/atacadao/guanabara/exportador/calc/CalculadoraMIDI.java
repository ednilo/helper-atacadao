package br.com.atacadao.guanabara.exportador.calc;

import java.io.File;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class CalculadoraMIDI {

	public static void main(String[] args) throws Exception{
		File file = new File("src/main/resources/LEITRA LAIS22-MAR.pdf"); 
		if(file.exists()) {
			System.out.println("Arquivo existente...");
		}
	   
		PdfReader reader = new PdfReader("src/main/resources/LEITRA LAIS22-MAR.pdf");
	    int pages = reader.getNumberOfPages();
	    for (int i = 1; i <= pages; i++) {
	        System.out.println(PdfTextExtractor.getTextFromPage(reader, i));
	    }
	    reader.close();
	}
	
}
