package com.marama.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.marama.view.screens.GameScreen;

/**
 * This is the initial {@link Game} class that will be instantiated by libGDX in the different launchers.
 */
public class View extends Game {
    @Override
    public void create () {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render();
    }
}
