import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Playlist {
    public void createPlaylist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre de la playlist:");
        String playlistName = scanner.nextLine();

        Menu menu = new Menu();
        String song;
        Map<Integer, String> songs = new HashMap<>();
        int n = 1;
        while (true) {
            System.out.println("Seleccione las canciones (0 para dejar de seleccionar): ");
            song = menu.showFiles(menu.getFiles());
            if (song.equals("0"))
                break;
            songs.put(n, song);
            n++;
        }
        JSONObject jsonObject = new JSONObject(songs);
        try (FileWriter file = new FileWriter("playlists/" + playlistName + ".json")) {
            file.write(jsonObject.toJSONString());
            System.out.println("Playlist creada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPlaylists() {
        String folderLocation = "playlists/";
        HashMap<String, String> files = new HashMap<>();

        // Convierte la ruta ingresada a un objeto Path.
        Path folder = Paths.get(folderLocation);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            int n = 1;
            for (Path set : stream) {
                files.put(n + "", set.getFileName().toString());
                n++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Elija una playlist: ");

        for (Map.Entry<String, String> set : files.entrySet()) {
            String playlistName = set.getValue().substring(0, set.getValue().length() - 5);
            System.out.println(set.getKey() + ". " + playlistName);
        }

        // Nuevo input.
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        loadPlaylist(files.get(input));
    }

    public void loadPlaylist(String jsonName) {
        String filePath = "playlists/" + jsonName;

        JSONParser parser = new JSONParser();

        try (FileReader file = new FileReader(filePath)) {
            Object obj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) obj;

            // Convierte el JSONObject en un Map.
            Map<String, String> playlist = (Map<String, String>) jsonObject;

            Menu menu = new Menu();
            MusicPlayer musicPlayer = new MusicPlayer();

            String songName = menu.showFiles(playlist);
            musicPlayer.playMusic(songName);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
