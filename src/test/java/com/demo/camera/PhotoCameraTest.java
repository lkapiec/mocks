package com.demo.camera;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class PhotoCameraTest {
    ImageSensor imageSensor;
    PhotoCamera photoCamera;
    Card card;

    @BeforeEach
    void prepareTest() {
        imageSensor = mock(ImageSensor.class);
        card = mock(Card.class);
        photoCamera = new PhotoCamera(imageSensor, card);
    }

    //1. Włączenie kamery włącza zasilanie sensora.
    @Test
    void turningCameraOnSetSensorOn() {
        photoCamera.turnOn();

        Mockito.verify(imageSensor).turnOn();
    }

    //2. Wyłączenie kamery odcina zasilanie sensora.
    @Test
    void turningCameraOffSetSensorOff() {
        photoCamera.turnOff();

        Mockito.verify(imageSensor).turnOff();
    }

    //3. Naciśnięcie migawki jeśli zasilanie jest odcięte nie robi nic.
    @Test
    void pressingButtonWhileCamersIsOffDoesNothing()
    {
        photoCamera.turnOff();

        Mockito.clearInvocations(imageSensor);

        photoCamera.pressButton();

        Mockito.verifyZeroInteractions(imageSensor);
    }

    //4. Naciśnięcie migawki z włączonym zasilaniem, kopiuje dane z sensora do karty pamięci.
    //_(Zapisywanie danych do karty pamięci zajmuje nieco czasu.)_
    @Test
    void pressingButtonStartsCopyDataFromSensorToCard()
    {
        photoCamera.turnOn();
        photoCamera.pressButton();

        Mockito.verify(imageSensor).read();
        Mockito.verify(card).write(any());
    }

    //5. Jeśli dane są obecnie zapisywane, wyłączenie kamery nie odcina zasilania sensora
    @Test
    void copyPedingDoesNotPowerOffSensor()
    {
        photoCamera.turnOn();
        photoCamera.pressButton();
        photoCamera.turnOff();

        Mockito.verify(imageSensor).turnOn();
    }

    //6. Kiedy zapis danych się zakończy, aparat odcina zasilanie sensora.
    @Test
    void whenDataWillBeWrittenPowerOffSensor()
    {
        photoCamera.turnOn();
        photoCamera.pressButton();
        photoCamera.turnOff();
        photoCamera.writeCompletedArtificial();

        Mockito.verify(imageSensor).turnOff();
    }
}