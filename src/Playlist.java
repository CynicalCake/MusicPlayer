import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
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
        try (FileWriter archivo = new FileWriter("playlists/" + playlistName + ".json")) {
            archivo.write(jsonObject.toJSONString());
            System.out.println("Playlist creada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
