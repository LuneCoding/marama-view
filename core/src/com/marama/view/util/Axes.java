package com.marama.view.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import java.util.ArrayList;

public class Axes {
    private float length = 1.5f;
    private float coneLength = 0.15f;
    private float coneHeight = 0.05f;
    private int coneSegments = 16;
    private float bbHeight = 0.1f;
    private float bbWidth = length + coneLength;

    private ArrayList<Vector3> boundsX = new ArrayList<Vector3>();
    private ArrayList<Vector3> boundsY = new ArrayList<Vector3>();
    private ArrayList<Vector3> boundsZ = new ArrayList<Vector3>();

    private Vector3 origin;

    public BoundingBox boundingBoxX;
    public BoundingBox boundingBoxY;
    public BoundingBox boundingBoxZ;

    public Axes(Vector3 origin) {
        this.calculateBoundingBoxes(origin);
    }

    public void calculateBoundingBoxes(Vector3 origin) {
        this.origin = origin;
        boundingBoxX = calculateBoundingBoxX();
        boundingBoxY = calculateBoundingBoxY();
        boundingBoxZ = calculateBoundingBoxZ();
    }

    public void draw(ShapeRenderer shapeRenderer, Vector3 origin) {
        Vector3 target;

        // X Line
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        target = new Vector3(length, 0, 0).add(origin);
        shapeRenderer.line(origin, target);

        // X Cone
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.translate(target.x, target.y, target.z);
        shapeRenderer.rotate(0, 1, 0, 90);
        shapeRenderer.cone(0, 0, 0, coneHeight, coneLength, coneSegments);

        // Y Line
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        target = new Vector3(0, length, 0).add(origin);
        shapeRenderer.line(origin, target);

        // Y Cone
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.translate(target.x, target.y, target.z);
        shapeRenderer.rotate(1, 0, 0, -90);
        shapeRenderer.cone(0, 0, 0, coneHeight, coneLength, coneSegments);

        // Z Line
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        target = new Vector3(0, 0, length).add(origin);
        shapeRenderer.line(origin, target);

        // Z Cone
        shapeRenderer.identity();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.translate(target.x, target.y, target.z);
        shapeRenderer.cone(0, 0, 0, coneHeight, coneLength, coneSegments);

//        drawBoundingBoxes(shapeRenderer);
    }

    private BoundingBox calculateBoundingBoxX() {
        ArrayList<Vector3> bounds = new ArrayList<Vector3>();

        // Small square start.
        bounds.add(new Vector3(bbHeight, 0, 0));
        bounds.add(new Vector3(bbHeight, bbHeight, 0));
        bounds.add(new Vector3(bbHeight, bbHeight, bbHeight));
        bounds.add(new Vector3(bbHeight, 0, bbHeight));

        // Small square end.
        bounds.add(new Vector3(bbWidth, 0, 0));
        bounds.add(new Vector3(bbWidth, bbHeight, 0));
        bounds.add(new Vector3(bbWidth, bbHeight, bbHeight));
        bounds.add(new Vector3(bbWidth, 0, bbHeight));


        for (int i = 0; i < bounds.size(); i++) {
            bounds.get(i).add(new Vector3(0, -(bbHeight/2), -(bbHeight/2))).add(origin); // Center around axis and origin.
        }

        BoundingBox boundingBox = new BoundingBox();
        boundingBox.set(bounds);

        boundsX = bounds;

        return boundingBox;
    }

    private BoundingBox calculateBoundingBoxY() {
        ArrayList<Vector3> bounds = new ArrayList<Vector3>();

        // Small square start.
        bounds.add(new Vector3(0, bbHeight, 0));
        bounds.add(new Vector3(bbHeight, bbHeight, 0));
        bounds.add(new Vector3(bbHeight, bbHeight, bbHeight));
        bounds.add(new Vector3(0, bbHeight, bbHeight));

        // Small square end.
        bounds.add(new Vector3(0, bbWidth, 0));
        bounds.add(new Vector3(bbHeight, bbWidth, 0));
        bounds.add(new Vector3(bbHeight, bbWidth, bbHeight));
        bounds.add(new Vector3(0, bbWidth, bbHeight));

        for (int i = 0; i < bounds.size(); i++) {
            bounds.get(i).add(new Vector3(-(bbHeight/2), 0, -(bbHeight/2))).add(origin); // Center around axis and origin.
        }

        BoundingBox boundingBox = new BoundingBox();
        boundingBox.set(bounds);

        boundsZ = bounds;

        return boundingBox;
    }

    private BoundingBox calculateBoundingBoxZ() {
        ArrayList<Vector3> bounds = new ArrayList<Vector3>();

        // Small square start.
        bounds.add(new Vector3(0, 0, bbHeight));
        bounds.add(new Vector3(bbHeight, 0, bbHeight));
        bounds.add(new Vector3(bbHeight, bbHeight, bbHeight));
        bounds.add(new Vector3(0, bbHeight, bbHeight));

        // Small square end.
        bounds.add(new Vector3(0, 0, bbWidth));
        bounds.add(new Vector3(bbHeight, 0, bbWidth));
        bounds.add(new Vector3(bbHeight, bbHeight, bbWidth));
        bounds.add(new Vector3(0, bbHeight, bbWidth));

        for (int i = 0; i < bounds.size(); i++) {
            bounds.get(i).add(new Vector3(-(bbHeight/2), -(bbHeight/2), 0)).add(origin); // Center around axis and origin.
        }

        BoundingBox boundingBox = new BoundingBox();
        boundingBox.set(bounds);

        boundsY = bounds;

        return boundingBox;
    }

    private void drawBoundingBoxes(ShapeRenderer shapeRenderer) {
        BoundingBoxHelper.draw(shapeRenderer, origin, boundingBoxX);
        BoundingBoxHelper.draw(shapeRenderer, origin, boundingBoxY);
        BoundingBoxHelper.draw(shapeRenderer, origin, boundingBoxZ);
    }
}
