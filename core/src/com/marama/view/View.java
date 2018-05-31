package com.marama.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.marama.view.screens.SplashScreen;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import java.io.IOException;

/**
 * This is the initial {@link Game} class that will be instantiated by libGDX in the different launchers.
 */
public class View extends Game {
    @Override
    public void create () {
        String line = "java -jar ../..//libs/marama-editor.jar test marama";
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        int exitValue = 0;
        try {
            exitValue = executor.execute(cmdLine);
        } catch (ExecuteException ee) {
            ee.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exitValue != 0) {
            System.err.println("Editor: error, returned " + exitValue);
            // TODO better error logging, static error code to string function from editor
        }

        setScreen(new SplashScreen(
            this,
            new ScreenViewport()
        ));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render();
    }
}
