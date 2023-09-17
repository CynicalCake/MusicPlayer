import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class Converter {

    public void addSong(String inputFilePath, String artistName, String songName, String genre) {
        String outputFilePath = "songs/" + artistName + ">" + songName + ">" + genre + ".wav";
        convert(inputFilePath, outputFilePath);
    }

    public void convert(String inputFilePath, String outputFilePath) {
        File input = new File(inputFilePath);
        File output = new File(outputFilePath);

        AudioAttributes audio = new AudioAttributes();
        // Establece el códec para WAV.
        audio.setCodec("pcm_s16le");

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();

        try {
            encoder.encode(input, output, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
}