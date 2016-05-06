package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    public PasswordField lblPassword;

    @FXML
    public TextField lblScreenName;

    @FXML
    public Text lblStatus;

    @FXML
    public Button btnEnter;

    @FXML
    public Button btnConfig;

    private static String screenName;

    public static String getScreenName() {
        return screenName;
    }
    public static Stage stage;
    public static Stage configStage;



    public boolean checkForFile(String fileName){
        File f = new File(fileName);
        boolean pass = false;
        if(f.exists()){
            pass = true;
        }else{
            pass = false;
        }
        return pass;
    }

    private void openChatroom(){
        try {
            Main.stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Chatroom.fxml"));
            stage.setTitle("The Chatroom");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 600, 400));
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
            configStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Config.fxml"));
            configStage.setTitle("Config");
            configStage.setResizable(false);
            configStage.setScene(new Scene(root,600,143));
            configStage.initModality(Modality.APPLICATION_MODAL);
            configStage.show();
            //ConfigController config = new ConfigController();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void checkConditions(){
        if(checkForFile(ChatroomController.getChatFile())) {
            if (lblPassword.getText().equals("password")) {
                if (!lblScreenName.getText().equals("")) {
                    openChatroom();
                } else if (lblScreenName.getText().equals("")) {
                    lblScreenName.setText("anonymous");
                    openChatroom();
                }
            }else{
                lblStatus.setText("Status: Incorrect password!");
            }
        }else{
            lblStatus.setText("Status: No chat file selected");
        }
    }


    public void onKeyPress(KeyEvent event){
        checkConditions();
    }

    public void enterButtonClicked(ActionEvent actionEvent) {
        checkConditions();
    }

    public void configButtonClicked(ActionEvent actionEvent) {
        openConfig();
    }
}
