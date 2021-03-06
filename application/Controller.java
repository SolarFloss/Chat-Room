package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class Controller {


    @FXML
    public TextField lblScreenName;

    @FXML
    public Text lblStatus;

    @FXML
    public Button btnEnter;

    @FXML
    public Button btnAbout;


    @FXML
    public ListView listView;

    @FXML
    public Button btnCreateRoom;

    @FXML
    public TextField txtNewRoom;




    private static String screenName;
    private static final int MAX_LENGTH = 10;
    private FileChooser fileChooser;
    public static String directory;
    public static String fileName;
    private File rooms = new File("D:\\Rooms");
    private boolean stuffLoaded = false;

    public static String getScreenName() {
        return screenName;
    }
    public static Stage stage;
    public static Stage aboutStage;



    //Populate the listview with the current rooms
    public void populateList(){
        //Only update the list if there is nothing selected
        if(rooms.exists() && listView.getSelectionModel().getSelectedItem() == null){
            ObservableList items = FXCollections.observableArrayList();
            listView.setItems(items);

            File[] directoryListing = rooms.listFiles();


            if(directoryListing != null){
                for(File child : directoryListing) {
                    items.add(child.getName());
                }
            }
        }else{
            //Make the directory if it doesn't exist
            rooms.mkdir();
        }
    }


    public void createRoomClicked(){
        if(rooms.exists()){
            if(!txtNewRoom.getText().equals("")) {
                File newRoom = new File(rooms.getAbsolutePath() + "\\" + txtNewRoom.getText() + ".txt");
                try {
                    newRoom.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ObservableList items = FXCollections.observableArrayList();
                listView.setItems(items);

                File[] directoryListing = rooms.listFiles();


                if (directoryListing != null) {
                    for (File child : directoryListing) {
                        items.add(child.getName());
                    }
                }
            }else{
                lblStatus.setText("Status: Your room must have a name!");
            }
        }else{
            //This is redundant but whatever
            rooms.mkdir();
            createRoomClicked();
        }
    }



    /*
    public void browseButtonClick(){
        fileChooser = new FileChooser();
        //File homeDirectory = new File("..\\The Chat\\Rooms");
        //fileChooser.setInitialDirectory(homeDirectory);
        fileChooser.setTitle("Select the chat file");
        File file = fileChooser.showOpenDialog(Controller.aboutStage);
        if (file != null) {
            directory = file.getAbsolutePath();
            fileName = file.getName();
            lblChatroomName.setText(fileName);
        }
    }
    */

    private void openChatroom(){
        try {
            ChatroomController.setChatFile(directory);
            Main.stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Chatroom.fxml"));
            stage.setTitle(listView.getSelectionModel().getSelectedItem().toString());
            stage.setResizable(false);
            stage.setScene(new Scene(root, 916, 636));
            stage.show();
            stage.setOnCloseRequest(e -> ChatroomController.closeProgram());
            ChatroomController chat = new ChatroomController();
            ChatroomController.screenName = lblScreenName.getText();
            chat.initialize();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void openConfig(){
        try{
            aboutStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Config.fxml"));
            aboutStage.setTitle("About");
            aboutStage.setResizable(false);
            aboutStage.setScene(new Scene(root,600,462));
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.show();
            //ConfigController config = new ConfigController();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void checkConditions(){
        if(checkForFile(directory)) {
                if (lblScreenName.getText().length() < 15) {
                    if (!lblScreenName.getText().equals("")) {
                        openChatroom();
                    } else if (lblScreenName.getText().equals("")) {
                        lblScreenName.setText("anon");
                        openChatroom();
                    }
                } else {
                    lblStatus.setText("Status: Your screen name is too long!");
                }
        }else{
            lblStatus.setText("Status: No chat room selected");
        }
    }

    public boolean checkForFile(String fileName){
        boolean pass = false;
            try{
                File f = new File(fileName);
                pass = f.exists();
            }catch (NullPointerException e){
                pass = false;
            }
        return pass;
    }


    public void newRoomKeyPress(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER) {
            checkConditions();
        }
    }

    public void enterButtonClicked(ActionEvent actionEvent) {
        try {
            fileName = listView.getSelectionModel().getSelectedItem().toString();
            directory = rooms.getAbsolutePath() + "\\" + listView.getSelectionModel().getSelectedItem().toString();
            checkConditions();
        }catch(Exception e){
            lblStatus.setText("Status: Did you pick a room?");
        }
    }

    public void configButtonClicked(ActionEvent actionEvent) {
        openConfig();
    }


}
