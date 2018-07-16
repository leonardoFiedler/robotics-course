import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;

public class MainJSON {

	public static void main(String[] args) throws IOException {
		String path = "/Users/leonardofiedler/eclipse-workspace/TesteProject/src/file.json";
		String content = MainJSON.readFile(path);
		JSONArray jsArrX = new JSONArray(content);
		String[][] matRes = new String[7][7];
		
		for (int i = 0; i < jsArrX.length(); i++)
		{
			JSONArray jsArrY = jsArrX.getJSONArray(i);
			for (int j = 0; j < jsArrY.length(); j++)
			{
				String val = String.valueOf(jsArrY.getInt(j));
				matRes[i][j] = val;
				System.out.println("Valor " + matRes[i][j]);
			}
		}
	}
	
	public static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}

}
