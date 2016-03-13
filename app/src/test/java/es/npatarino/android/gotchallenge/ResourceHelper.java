package es.npatarino.android.gotchallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

/**
 * @author Antonio López.
 */
public class ResourceHelper {

    public StringBuffer getContentFromFile(String filePath) throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        File file = new File(resource.getPath());
        return converterFileToString(file);
    }

    private StringBuffer converterFileToString(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null){
            sb.append(line).append("\n");
        }
        reader.close();
        return sb;
    }

}
