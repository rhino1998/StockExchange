import java.io.Writer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.FileMatchProcessor;

public class StockExchangeDocs {
    public static void main(String[] args) {
        final List< String> classNames = new ArrayList< String>();
        ScanResult scan = new FastClasspathScanner(args[0].replaceFirst("#", "").split("#", -1))
                .enableMethodAnnotationIndexing().ignoreMethodVisibility().enableFieldTypeIndexing()
                .ignoreFieldVisibility().matchFilenamePattern("[^$]*", new FileMatchProcessor() {
                    public void processMatch(String str, InputStream in, long l) {
                        classNames.add(str.replaceAll("/", ".").replaceAll(".class", ""));
                        // System.out.println(str);
                    }
                }).scan();
        // System.out.println(Arrays.toString(classNames.toArray(new String[classNames.size()])));
        String dotFile = new FastClasspathScanner(classNames.toArray(new String[classNames.size()])).strictWhitelist()
                .enableMethodAnnotationIndexing().ignoreMethodVisibility().enableFieldTypeIndexing()
                .ignoreFieldVisibility().scan().generateClassGraphDotFile(2000, 2000);
        Writer writer = null;

        System.out.println(dotFile);
    }
}