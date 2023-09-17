import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.util.Scanner;

public class MusicPlayer {
    public void playMusic(String songName){
        try {
            File songPath = new File("songs/" + songName);

            if (songPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(songPath);
                Clip clip = AudioSystem.getClip();
                System.out.println("Reproduciendo ahora: " + songName);
                clip.open(audioInput);
                clip.start();
                while (true) {
                    System.out.println("P - Pausa         R - Reanudar         A - Atrás");
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();

                    switch (input) {
                        case "P", "p" -> {
                            clip.stop();
                            System.out.println("Canción detenida.");
                        }
                        case "R", "r" -> {
                            clip.start();
                            System.out.println("Canción reanudada.");
                        }
                        case "A", "a" -> {
                            clip.close();
                            Menu menu = new Menu();
                            menu.ShowMenu();
                        }
                    }
                    if (input.equals("P") || input.equals("p"))
                        clip.stop();
                    else
                        if (input.equals("R") || input.equals("r"))
                            clip.start();
                }
            }
            else {
                System.out.println("No existe el archivo indicado.");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}