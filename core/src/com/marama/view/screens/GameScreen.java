package com.marama.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.marama.view.controllers.addBlockInputController;
import com.marama.view.renderables.World;
import com.marama.view.renderables.stages.WorldUserInterface;
import com.marama.view.controllers.DragObjectInputController;
import com.marama.view.controllers.SelectObjectInputController;

/**
 * The {@link GameScreen} extends a {@link ScreenAdapter} that contains a 3D {@link World} and a {@link WorldUserInterface}.
 */
public class GameScreen extends ScreenAdapter {
    public final World world;
    public final WorldUserInterface worldUserInterface;
    private InputMultiplexer inputMultiplexer;

    private SelectObjectInputController selectObjectInputController;
    private DragObjectInputController dragObjectInputController;
    private addBlockInputController addBlockInputController;

    private String activeMarama;
    private int activeTool;

    /**
     * Instancing the GameScreen that contains a 3D {@link World} and a {@link WorldUserInterface}.
     */
    public GameScreen() {
        inputMultiplexer = new InputMultiplexer();

        this.world = new World(this, new DirectionalLight(),
                new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), new AssetManager());
        this.worldUserInterface = new WorldUserInterface(this, this.world, new Skin(Gdx.files.internal("skin/uiskin.json")));

        selectObjectInputController = new SelectObjectInputController(this);
        dragObjectInputController = new DragObjectInputController(this);
        addBlockInputController = new addBlockInputController(this);
    }

    public String getActiveMarama() {
        return activeMarama;
    }

    public void setActiveMarama(String activeMarama) {
        this.activeMarama = activeMarama;
        worldUserInterface.update();
    }

    public int getActiveTool() {
        return activeTool;
    }

    public void setTool(int index) {
        this.activeTool = index;
        this.initializeInputControllers();
    }

    @Override
    public void show() {
        this.activeMarama = "block";
        this.setActiveMarama(this.activeMarama);
        this.activeTool = 0;
        this.setTool(activeTool);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1f);
        world.render(delta);
        worldUserInterface.render(delta);
    }

    public void initializeInputControllers() {
        inputMultiplexer.clear();

        worldUserInterface.update();

        // The user interface has the highest priority.
        inputMultiplexer.addProcessor(worldUserInterface);

        // Add the tool controllers in between.
        switch (activeTool) {
            // Select tool
            case 0:
                inputMultiplexer.addProcessor(selectObjectInputController);
                break;
            // Move tool
            case 1:
                inputMultiplexer.addProcessor(dragObjectInputController);
                break;
            // Add tool
            case 2:
                inputMultiplexer.addProcessor(addBlockInputController);
                break;
        }

        // Always add the camera controller but make it a last priority.
        inputMultiplexer.addProcessor(world.getCameraInputController());

        // Apply the input multiplexer.
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
