package com.marama.comm;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

public class BasicEditorHandler implements Runnable {

    private final String path = "../../libs/marama-editor.jar";
    private static BasicEditorHandler instance;
    private static Thread thread;
    private Process editor;

    public static BasicEditorHandler getInstance() {
        if (instance == null) {
            instance = new BasicEditorHandler();
            thread.start();
        }
        return instance;
    }

    private BasicEditorHandler() {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", this.path);
        try {
            this.editor = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
    }

    public void requestOperation(RequestType type, String[] args) {
        throw new NotImplementedException();
    }

    @Override
    public void run() {
        boolean exit = false;

        InputStream is = instance.editor.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = "";
        while(!line.equals("exit") && !line.equals("exit") ) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(line);
        }
    }
}
