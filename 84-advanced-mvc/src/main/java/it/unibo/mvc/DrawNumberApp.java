package it.unibo.mvc;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.List;


/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String configFile, final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
    
        final Configuration.Builder builderConf = new Configuration.Builder();

        try(var content = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(configFile)))){
            for (var line = content.readLine(); line != null; line = content.readLine()) {
                final String[] split = line.split(":");
                if(split.length == 2) {
                    final int value = Integer.parseInt(split[1].trim());
                    if(split[0].contains("max")){
                        builderConf.setMax(value);
                    } else if(split[0].contains("min")){
                        builderConf.setMin(value);
                    } else if(split[0].contains("attempts")){
                        builderConf.setAttempts(value);
                    }
                } else {
                    displayError("Error in configuration file" + line);
                }

            }
        } catch (IOException | NumberFormatException e) {
            displayError("Error in configuration file" + e.getMessage());
        }
        final Configuration conf = builderConf.build();
        if(conf.isConsistent()){
            this.model = new DrawNumberImpl(conf);
        } else {
            displayError("Error in configuration file" + "min" + conf.getMin() + "max" + conf.getMax() + "attempts" + conf.getAttempts());
            this.model = new DrawNumberImpl(new Configuration.Builder().build());
        }
    
    }

    private void displayError(final String message) {
        for (final DrawNumberView view: views) {
            view.displayError(message);
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("config.yml", 
            new DrawNumberViewImpl(), 
            new DrawNumberViewImpl(), 
            new PrintStreamView(System.out), 
            new PrintStreamView("output.log"));
    }

}
