package it.unibo.mvc;
import java.util.List;

/**
 *
 */
public interface Controller {


    /**
     * 
     * @param next next string to print.
     */
    void setNextStringPrint(String next);

    /**
     * 
     * @return the next string to print.
     */
    String getNextStringPrint();


    /**
     * @return history of printed strings.
     */
    List<String> getPrinteStringHistory();

   /**
    *  print current string.
    */
    void printCurrentString();

}
