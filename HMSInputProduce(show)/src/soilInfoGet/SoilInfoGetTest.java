package soilInfoGet;

import java.io.IOException;

public class SoilInfoGetTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        SoilInfoGet soilInfoGet = SoilInfoGet.getInstance();
        String filePath = "d:/soilReport2.pdf";
        soilInfoGet.setFilePath(filePath);
        String soilData = soilInfoGet.getFinalSoilInfo().toString();
        System.out.println("soilFinalData = "+soilData);
	}

}
