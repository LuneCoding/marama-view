package com.marama.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.marama.view.screens.MainMenuScreen;


public class View extends Game {
    float backgroundColor = 0.8f;

    /**
     *
     */
    @Override
    public void create() {
        setScreen(new MainMenuScreen(
                this,
                new ScreenViewport(),
                new Skin(Gdx.files.internal("skin/uiskin.json"))
        ));
    }

    /**
     *
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(backgroundColor, backgroundColor, backgroundColor, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render();
    }
}
