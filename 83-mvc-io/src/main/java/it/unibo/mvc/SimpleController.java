package it.unibo.mvc;

import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;


/**
 * 
 *
 */
public final class SimpleController implements Controller {

    private final List<String> history = new LinkedList<>();
    private String nextStringPrint;

    @Override
    public void setNextStringPrint(final String next) {
        this.nextStringPrint = Objects.requireNonNull(next);
    }

    @Override
    public String getNextStringPrint() {
        return this.nextStringPrint;
    }

    @Override
    public List<String> getPrinteStringHistory() {
        return Collection.unmodifiableList(history);
    }

    @Override
    public void printCurrentString() {
        if(this.nextStringPrint == null) {
            throw new IllegalStateException("No string to set");
        }

        history.add(this.nextStringPrint);
        System.out.println(this.nextStringPrint); //NOPMD: allow in exercise
    }

}
