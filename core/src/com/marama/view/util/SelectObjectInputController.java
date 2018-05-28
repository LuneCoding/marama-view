package com.marama.view.util;

import com.badlogic.gdx.InputAdapter;
import com.marama.view.entities.instances.EntityInstance;
import com.marama.view.renderables.World;

/**
 * A {@link InputAdapter} specifically for selecting 3D objects rendered in {@link World}.
 */
public class SelectObjectInputController extends InputAdapter {
    private World world;
    private EntityInstance newEntityInstance = null;
    private EntityInstance currentEntityInstance = null;

    /**
     * Instantiates an {@link InputAdapter} specifically for selecting 3D objects rendered in {@link World}.
     *
     * @param world The ({@link World}) instance that renders 3D models.
     */
    public SelectObjectInputController(World world) {
        this.world = world;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        newEntityInstance = (EntityInstance) world.getModelInstance(screenX, screenY);
        return false; // Continue to the next 'touchDown' listener.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false; // Continue to the next 'touchDragged' listener.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        EntityInstance instance = world.getModelInstance(screenX, screenY);

        // If a selected EntityInstance is found and it is equal to the found instance the EntityInstance is set to
        // selected.
        if (newEntityInstance != null && instance != null && newEntityInstance == instance) {
            newEntityInstance.setSelected(true); // Apply the new selection.

            // Deselect the previous selection
            if (currentEntityInstance != null) {
                currentEntityInstance.setSelected(false);
            }

            currentEntityInstance = newEntityInstance; // update new current selection.
            newEntityInstance = null; // Reset the new possible selection.
        }

        return false; // Continue to the next 'touchUp' listener.
    }
}
