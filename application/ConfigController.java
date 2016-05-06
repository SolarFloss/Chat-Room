package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;

/**
 * Created by Nicholas on 5/4/2016.
 */
public class ConfigController {
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    @FXML
    public Button chatFileBrowse;
    @FXML
    public Button btnApply;
    @FXML
    public TextField txtChatFile;




    public void applyButtonClicked(ActionEvent actionEvent){
        ChatroomController.setChatFile(txtChatFile.getText());
        //ChatroomController.setChatFolder(txtChatFolder.getText());
        Controller.configStage.close();
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        Controller.configStage.close();
    }

    public void fileBrowseClicked(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select the chat file");
        File file = fileChooser.showOpenDialog(Controller.configStage);
        if (file != null) {
            String directory = file.getAbsolutePath();
            txtChatFile.setText(directory);
        }
    }


}
