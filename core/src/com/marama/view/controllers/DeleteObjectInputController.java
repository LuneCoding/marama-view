package com.marama.view.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.collision.Ray;
import com.marama.view.entities.instances.SelectableInstance;
import com.marama.view.screens.GameScreen;

public class DeleteObjectInputController extends InputAdapter {
    private final GameScreen gameScreen;
    private SelectableInstance DeletionSelected = null;
    public DeleteObjectInputController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    private final float deletionOpacity = 0.5f;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Ray ray = gameScreen.world.getPerspectiveCamera().getPickRay(screenX, screenY);
        SelectableInstance instance = (SelectableInstance) gameScreen.world.getClosestModelInstance(ray);
        if (instance != null) {
            if (DeletionSelected != null) {
                DeletionSelected.setOpacity(1f);
            }
            if (instance == DeletionSelected) {
                gameScreen.world.deleteObject(instance);
                DeletionSelected = null;
            } else {
                DeletionSelected = ((SelectableInstance) gameScreen.world.getClosestModelInstance(ray));
                DeletionSelected.setOpacity(deletionOpacity);
            }
        } else {
            if (DeletionSelected != null) {
                DeletionSelected.setOpacity(1f);
                DeletionSelected = null;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return DeletionSelected != null;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Ray ray = gameScreen.world.getPerspectiveCamera().getPickRay(screenX, screenY);
        SelectableInstance instance = (SelectableInstance) gameScreen.world.getClosestModelInstance(ray);

        return false; // Continue to the next 'touchDragged' listener.
    }
}
