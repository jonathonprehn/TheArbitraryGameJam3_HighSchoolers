package tag3.media;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import tag3.MainStartUp;
/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediaLoader {

    private static HashMap<String, BufferedImage> images;
    private static HashMap<String, AudioClip> sounds;

    static {
        images = new HashMap<String, BufferedImage>();
        sounds = new HashMap<String, AudioClip>();
    }

    public static void permanentLoadImage(String path, String name) {
        BufferedImage img = quickLoadImage(path);
        if (img!=null) {
            images.put(name, img);
        } else {
            System.out.println("Could not index image: " + path + "");
        }
    }

    public static void permanentLoadSound(String path, String name) {
        AudioClip clip = quickLoadSound(path);
        if (clip!=null) {
            sounds.put(name, clip);
        } else {
            System.out.println("Could not index sound: " + path + "");
        }
    }

    public static BufferedImage getLoadedImage(String name) {
        return images.get(name);
    }

    public static AudioClip getLoadedSound(String name) {
        return sounds.get(name);
    }

    public static BufferedImage quickLoadImage(String path) {
        BufferedImage img = null;
        try {
            img = (BufferedImage) ImageIO.read(MainStartUp.class.getResourceAsStream("assets/images/" + path + ""));
            System.out.println("Loaded image: " + path + "");
            return img;
        } catch(Exception e) {
            System.out.println("Could not load image: " + path + "");
            return null;
        }
    }

    public static AudioClip quickLoadSound(String path) {
        AudioClip clip = null;
        try {
               clip = Applet.newAudioClip(MainStartUp.class.getResource("assets/sounds/" + path + ""));
               return clip;
        } catch(Exception e) {
            System.out.println("Could not load sound: " + path + "");
            return null;
        }
    }

    public static void quickPlaySound(String name) {
        quickLoadSound(name).play();
    }
}
