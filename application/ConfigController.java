package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Nicholas on 5/4/2016.
 */
public class ConfigController {
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;


    //@FXML
    //public Button chatFileBrowse;
    //@FXML
    //public Button btnApply;

    @FXML
    public TextField txtChatFile;
    @FXML
    public Hyperlink lblCodeLink;
    @FXML
    public Hyperlink lblTwitterLink;





    public void applyButtonClicked(ActionEvent actionEvent){
        ChatroomController.setChatFile(txtChatFile.getText());
        //ChatroomController.setChatFolder(txtChatFolder.getText());
        Controller.aboutStage.close();
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        Controller.aboutStage.close();
    }

    public void fileBrowseClicked(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select the chat file");
        File file = fileChooser.showOpenDialog(Controller.aboutStage);
        if (file != null) {
            String directory = file.getAbsolutePath();
            txtChatFile.setText(directory);
        }
    }

    public void codeLinkClicked(){
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/SolarFloss/Chat-Room"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void twitterLinkClicked(){
        try {
            Desktop.getDesktop().browse(new URI("https://twitter.com/Nickels_7"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
