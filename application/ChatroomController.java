package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.nio.file.WatchKey;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nicholas on 4/20/2016.
 */




public class ChatroomController extends Thread{


    @FXML
    public TextArea lblTextArea;

    @FXML
    public TextField lblMessage;

    private static String chatFile = "C:/Users/Nicholas/Desktop/Chat Folder/ChatFile.txt";
    //private static String testFile = "C:/Users/Nicholas/Dropbox/test.txt";
    private Thread thread;
    public static String screenName = Controller.getScreenName();
    private static int num = 0;
    private Scanner in;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private WatchService watchService;
    private WatchKey watchKey;
    private Path directory;
    private static boolean running = true;

    public static void setChatFile(String directory){
        chatFile = directory;
    }


    public static String getChatFile(){
        return chatFile;
    }





    public boolean checkForFile(String fileName){
        File f = new File(fileName);
        return f.exists();
    }

    public void initialize(){
        if(lblTextArea != null){
            lblTextArea.appendText("Welcome to that Chatroom\n");

            try {
                this.watchService = FileSystems.getDefault().newWatchService();
                this.directory = Paths.get(chatFile + "/..");
                this.watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            }catch (IOException e){
                e.printStackTrace();
            }

            thread = new Thread(this);
            thread.start();
        }
    }

    public String getLastLine(){
        String line = null;
        String lastLine = null;
        int lineCount = 0;
        try{
            fileReader = new FileReader(chatFile);
            bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                lineCount += 1;
                //System.out.println(lineCount + ":" + line);
            }


            fileReader = new FileReader(chatFile);
            bufferedReader = new BufferedReader(fileReader);
            int max = lineCount;
            int newCount = 0;
            lineCount = 1;

            while((line = bufferedReader.readLine()) != null){
                if(lineCount < max){
                    //System.out.println(lineCount + ":" + line);
                    lineCount += 1;
                }else{
                    lastLine = line;
                }
            }

            bufferedReader.close();
        }catch (IOException e){
            System.out.println("File input error");
        }
        return lastLine;
    }

    public void sendMessage(){
        String message = "[" + this.screenName + "]: " + lblMessage.getText();

        //lblTextArea.appendText(message);
        if(checkForFile(chatFile)){
            lblMessage.setText("");
            try{
                fileWriter = new FileWriter(chatFile,true);
                bufferedWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(bufferedWriter);

                printWriter.println(message);
                printWriter.close();


                //bufferedWriter.write("\n" + message);

                bufferedWriter.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            System.out.println(chatFile);
        }
    }


    public void onKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            if(!lblMessage.getText().equals("")) {
                sendMessage();
            }
        }
    }

    public void sendButtonClicked(ActionEvent event){
        if(!lblMessage.getText().equals("")) {
            sendMessage();
        }
    }

    public void run(){
        try {
            while(running) {
                for (WatchEvent event : watchKey.pollEvents()) {
                    if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                        lblTextArea.appendText("\n" + getLastLine());
                    }
                }
                thread.sleep(50);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void closeProgram(){
        running = false;
    }
}
