package de.mlandth.divingdrake;

import javafx.scene.media.AudioClip;

public class Media {

    public Media() {
    }

    public AudioClip getMedia(String path) {
        return new AudioClip(getClass().getResource(path).toExternalForm());
    }

    public void playMedia(AudioClip media) {
        media.setVolume(0.2);
        media.play();
    }
}
