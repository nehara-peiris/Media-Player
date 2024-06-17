package lk.ijse.mediaplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerController {

    @FXML
    private Slider slider;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;

    private Media media;
    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;

    @FXML
    void btnStop(MouseEvent event) {
        btnStop.setText("STOP");
        mediaPlayer.stop();
        isPlayed = false;
    }

    @FXML
    void btnPlay(MouseEvent event) {
        if (!isPlayed){
            btnPlay.setText("PAUSE");
            mediaPlayer.play();
            isPlayed = true;
        }else{
            btnPlay.setText("PLAY");
            mediaPlayer.pause();
            isPlayed = false;
        }
    }

    @FXML
    void btnSelectMediaOnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select media");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null){
            String url = selectedFile.toURI().toString();

            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
                slider.setValue(newValue.toSeconds());
                lblDuration.setText("Duration " + (int)slider.getValue() + " / " + (int)media.getDuration().toSeconds());
            }));

            mediaPlayer.setOnReady(() ->{
                Duration totalDuration = media.getDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration 00 / " + (int)media.getDuration().toSeconds());
            });

            Scene scene = mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());

            //mediaPlayer.setAutoPlay(true);
        }
    }

    @FXML
    void sliderPressed(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }
}
