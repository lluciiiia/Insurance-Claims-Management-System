package src.FileManager;

import java.io.IOException;
import java.util.HashMap;

/*
 * @author <Seokyung Kim - s3939114>
 */

public interface FileManager {

    HashMap<String, HashMap<String, ?>> loadFiles() throws IOException;

    void saveFiles(HashMap<String, HashMap<String, ?>> objectsHashMap) throws IOException;

}
