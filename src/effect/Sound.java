package effect;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class Sound {
    Clip clip;
    Hashtable<String, URL> sounds;
    private String soundfile = "src/data/sounds.txt";

    public Sound() {
        try {
            LoadSound();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadSound() throws IOException {
        sounds = new Hashtable<String, URL>();

        FileReader fr = new FileReader(soundfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        } else {
            fr = new FileReader(soundfile);
            br = new BufferedReader(fr);

            while ((line = br.readLine()).equals(""))
                ;
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++) {
                URL url = null;
                while ((line = br.readLine()).equals(""))
                    ;
                String[] str = line.split(" ");
                String name= str[0];
                String path= str[1];

                try {
                    url = getClass().getResource(path);
                } catch (Exception e) {
                    
                }
                sounds.put(name, url);
            }
        }
        br.close();
    }

    public void setFile(String str) {
        try {
            AudioInputStream ais= AudioSystem.getAudioInputStream(sounds.get(str));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            
        }
    }
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public Clip getClip() {
        return clip;
    }
}
