package com.demo.camera;

public interface ImageSensor extends WriteListener {
    void turnOn();
    void turnOff();
    byte[] read();

    @Override
    void writeCompleted();
}
