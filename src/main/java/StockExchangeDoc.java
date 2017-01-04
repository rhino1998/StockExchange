

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class StockExchangeDoc{
    public static void main(String[] args){
        String dotFile = new FastClasspathScanner("com.stockexchange").scan()
                         .generateClassGraphDotFile(1024, 1280);
        try{
            PrintWriter writer = new PrintWriter(
                StockExchangeDoc
                .class
                .getClassLoader()
                .getResource("documentation/doc.dot")
                .toExternalForm()
                , "UTF-8");
            writer.println(dotFile);
            writer.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}