package Mnist;

import org.neuroph.eval.classification.Utils;

import java.io.File;
import java.io.IOException;

public class MnistReader {
    public static void unzipFileIfNotExit(String fileDir) throws IOException{
        File file =new File(fileDir);
        if(!file.exists()){
            int i=fileDir.lastIndexOf('.');
            String outputFileName =fileDir.substring(0,i);

        }
    }

}
