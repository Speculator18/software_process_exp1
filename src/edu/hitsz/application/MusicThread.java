package edu.hitsz.application;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicThread extends Thread {

    private final String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private final boolean loop;
    private volatile boolean running = true;

    public MusicThread(String filename, boolean loop) {
        this.filename = filename;
        this.loop = loop;
        loadMusic();
    }

    private void loadMusic() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getSamples(AudioInputStream stream) throws IOException {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] data = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        dataInputStream.readFully(data);
        return data;
    }

    private void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        SourceDataLine dataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        if (dataLine == null) {
            return;
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1 && running) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataLine.drain();
        dataLine.close();
    }

    public void stopMusic() {
        running = false;
    }

    @Override
    public void run() {
        if (!loop) {
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
            return;
        }
        while (running) {
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
        }
    }
}
