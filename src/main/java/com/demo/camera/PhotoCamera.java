package com.demo.camera;

public class PhotoCamera {
    private ImageSensor imageSensor;
    private Card card;
    private boolean cameraStatus;
    private boolean copyPending;
    private boolean shutdownPending;

    public PhotoCamera(ImageSensor imageSensor, Card card) {
        this.imageSensor = imageSensor;
        this.card = card;
        this.cameraStatus = false;
        this.copyPending = false;
        this.shutdownPending = false;
    }

    public void turnOn() {
        if(!this.cameraStatus) {
            this.imageSensor.turnOn();
            this.cameraStatus = true;
        }
    }

    public void turnOff() {
        if(!this.copyPending) {
            this.imageSensor.turnOff();
            this.cameraStatus = false;
        } else
        {
            this.shutdownPending = true;
        }
    }

    // Naciśnięcie migawki z włączonym zasilaniem, kopiuje dane z sensora do karty pamięci.
    // _(Zapisywanie danych do karty pamięci zajmuje nieco czasu.)_
    public void pressButton() {
        if(this.cameraStatus)
        {
            this.copyPending = true;
            card.write(imageSensor.read());
        }
    }

    public void writeCompletedArtificial()
    {
        this.imageSensor.writeCompleted();

        this.copyPending = false;
        if(this.shutdownPending) {
            this.turnOff();
        }
    }
}
