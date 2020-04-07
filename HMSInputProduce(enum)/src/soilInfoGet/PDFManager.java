package soilInfoGet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFManager {//copy from online
	   private PDFParser parser;
	   private PDFTextStripper pdfStripper;
	   private PDDocument pdDoc ;
	   private COSDocument cosDoc ;
	   
	   private String Text ;
	   private String filePath;
	   private File file;
	 
	   public PDFManager() {}
	   
	   public String ToText() throws IOException
	   {
	       this.pdfStripper = null;
	       this.pdDoc = null;
	       this.cosDoc = null;
	       
	       file = new File(filePath);
	       parser = new PDFParser(new FileInputStream(file));
	       
	       parser.parse();
	       cosDoc = parser.getDocument();
	       pdfStripper = new PDFTextStripper();
	       pdDoc = new PDDocument(cosDoc);
	       //pdDoc.getNumberOfPages();
	       pdfStripper.setStartPage(1);
	       //pdfStripper.setEndPage(10);
	       // reading text from page 1 to 10
	       // if you want to get text from full pdf file use this code
	       int numOfPages = pdDoc.getNumberOfPages();
	       pdfStripper.setEndPage(numOfPages>GUI.Utility.maxPages ? GUI.Utility.maxPages : numOfPages);
	       
	       Text = pdfStripper.getText(pdDoc);
	       return Text;
	   }
	 
	    public void setFilePath(String filePath) {
	        this.filePath = filePath;
	    }
}
