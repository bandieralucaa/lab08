package it.unibo.mvc;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {

    private static final String FILENAME = "output.txt";
    private static final String FILEPATH = "user.home";
    private File filedest = new File(FILENAME + File.separator + FILEPATH); 

    /**
     * Method getCurrentFile.
     * @return a filedest
     */
    public File getCurrentFile() {
        return filedest;
    }


    /**
     * Method getCurrentFilePath.
     * @return a path of filedest
     */
    public String getCurrentFilePath() {
        return filedest.getPath();
    }

    /**
     * Method saveFile.
     * @param text
     * @throws IOException
     */
    public void saveFile(final String text) throws IOException {
        try (PrintStream out = new PrintStream(filedest, StandardCharsets.UTF_8.name())) {
            out.print(text);
        } 

    }

    /**
     * Method setDestination.
     * @param file
     */
    public void setDestination(final File file) {
        final File f = file.getParentFile();
        if (f.exists()) {
            filedest = file;
        } else {
            throw new IllegalArgumentException("The file does not exist");
        }
    }

    /**
     * Method setDestination.
     * @param file
     */
    public void setDestination(final String file) {
        setDestination(new File(file));
    }

}
