import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;

import java.util.Scanner;

public class Menu {
    public void ShowMenu() {
        System.out.println("Reproductor de música. \n");
        System.out.println("1. Ver canciones");
        System.out.println("2. Agregar canciones");
        System.out.println("3. Ver playlists");
        System.out.println("4. Crear playlist");
        System.out.println("5. Filtrar por artista");
        System.out.println("6. Filtrar por género");

        Playlist playlist = new Playlist();
        MusicPlayer musicPlayer = new MusicPlayer();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> {
                String songName = showFiles(getFiles());
                musicPlayer.playMusic(songName);
            }
            case "2" -> addSong();
            case "3" -> playlist.showPlaylists();
            case "4" -> playlist.createPlaylist();
            case "5" -> {
                System.out.println("Nombre del artista:");
                String artist = scanner.nextLine();
                String songName = showFiles(getFiles("artist", artist));
                musicPlayer.playMusic(songName);
            }
            case "6" -> {
                System.out.println("Nombre del género:");
                String genre = scanner.nextLine();
                String songName = showFiles(getFiles("genre", genre));
                musicPlayer.playMusic(songName);
            }
        }

    }

    public String showFiles(Map<String, String> hashMap) {
        String fullSongName;
        for(Map.Entry<String, String> set : hashMap.entrySet()) {
            // Convierte el nombre completo del archivo a un String más legible.
            fullSongName = getSongData(set.getValue()).get("artist") + " - " + getSongData(set.getValue()).get("name");
            // Muestra dicho String por consola.
            System.out.println(set.getKey() + ". " + fullSongName);
        }

        System.out.println("Elija la canción: ");

        // Nuevo input.
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("0"))
            return input;

        // Se obtiene el nombre de la canción a través de la clave la retorna.
        return hashMap.get(input);
    }

    public Map<String, String> getFiles() {
        return getFiles("none", "none");
    }

    public Map<String, String> getFiles(String filter, String filterName) {
        String folderLocation = "songs/";
        HashMap<String, String> files = new HashMap<>();

        // Convierte la ruta ingresada a un objeto Path.
        Path folder = Paths.get(folderLocation);

        // Verifica si la ruta existe.
        if (Files.isDirectory(folder)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
                int n = 1;
                if (!filter.equals("none"))
                    for (Path set : stream) {
                        Map<String, String> song = getSongData(set.getFileName().toString());
                        if (filterName.equals(song.get(filter))) {
                            files.put(n + "", set.getFileName().toString());
                            n++;
                        }
                    }
                else
                    for (Path set : stream) {
                        files.put(n + "", set.getFileName().toString());
                        n++;
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.err.println("La ruta no existe.");
        return files;
    }

    public Map<String, String> getSongData(String fileName) {
        // Se "quita" la extensión del archivo del String.
        String fileData = fileName.substring(0, fileName.length() - 4);

        // Se dividen las partes del nombre del archivo para almacenarlas como artista, nombre y género.
        String[] data = fileData.split(";");
        String artist = data[0];
        String name = data[1];
        String genre = data[2];

        // Los datos son ingresados al mapa.
        Map<String, String> songData = new HashMap<>();
        songData.put("artist", artist);
        songData.put("name", name);
        songData.put("genre", genre);

        return songData;
    }

    public void addSong() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el directorio de la canción:");
        String inputFilePath = scanner.nextLine();

        System.out.println("Nombre del artista:");
        String artist = scanner.nextLine();

        System.out.println("Nombre de la canción:");
        String name = scanner.nextLine();

        System.out.println("Género de la canción:");
        String genre = scanner.nextLine();

        Converter converter = new Converter();
        converter.addSong(inputFilePath, artist, name, genre);

        System.out.println("Canción añadida.");
        ShowMenu();
    }
}
