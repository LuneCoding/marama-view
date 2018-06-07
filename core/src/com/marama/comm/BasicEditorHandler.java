package com.marama.comm;

import java.io.*;

public class BasicEditorHandler implements Runnable {

    private static BasicEditorHandler instance; // Singleton instance.
    private final String path = "../../libs/marama-editor.jar"; // Path to Editor executable.
    private static Thread thread; // Receive Thread of the EditorHandler.
    private Process editor; // Process handler for the Editor.
    private BufferedWriter writer; // OutputStream for View, InputStream for the  Editor.

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
        this.writer = new BufferedWriter(new OutputStreamWriter(editor.getOutputStream()));
        thread = new Thread(this);
    }

    /**
     * Queries the Editor for the specified request, using passed arguments if necessary.
     * @param type The type of the request.
     * @param args The arguments passed along certain requests. Not all requests take arguments, they will be ignored.
     */
    public void requestOperation(RequestType type, String[] args) {
        // Incrementally build the operation string.
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(type.name());
        for (String arg : args) {
            strBuilder.append(' ');
            strBuilder.append(arg);
        }
        String operationLine = strBuilder.toString();

        // Send the operation to the Editor process.
        try {
            writer.write(operationLine);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The run method that is executed in a separate thread.
     * This method listens for any messages the Editor might send and puts them into the console.
     */
    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(instance.editor.getInputStream()));

        // Continuously check output of the Editor and respond accordingly.
        // For now just echo responses to the StdIn.
        String line = "";
        while(line != null && !line.equals("exit") && !line.equals("quit") ) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line != null && !line.equals("")) {
                System.out.println(line);
            }
        }
        // TODO shutdown code
    }
}
