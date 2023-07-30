package cool.foxes;

import java.util.HashMap;
import java.util.Stack;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import java.io.IOException;
import java.lang.Math;

public class Foxes {
    private String url;
    private HashMap<String, Tags> Totals = new HashMap<String, Tags>();

    private static Foxes instance = null;

    private class Tags {
        public long time;
        public int count;
    }

    public class Options {
        public int width = 0;
        public int height = 0;
        public String aspectRatio = "";

        public Options Width(int x) {
            width = x;
            return this;
        }

        public Options Height(int y) {
            height = y;
            return this;
        }

        public Options AspectRatio(String ar) {
            aspectRatio = ar;
            return this;
        }
    }

    private static Foxes getFoxes() {
        if(instance==null){
            instance = new Foxes();
        }
        return instance;
    }

    public static Options Options() {
        return getFoxes().new Options();
    }

    private static String internal(String tag, Options options) {
        Foxes self = getFoxes();
        long days = System.currentTimeMillis()/86400000;
        if (!self.Totals.containsKey(tag) || self.Totals.get(tag).time != days) {
            try {
                var client = HttpClient.newHttpClient();

                var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://foxes.cool/counts/"+tag))
                    .GET()
                    .build();

                var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                var tags = self.new Tags();
                tags.time = days;
                tags.count = Integer.parseInt(response.body());

                self.Totals.put(tag, tags);
            }
            catch (IOException e) {}
            catch (InterruptedException e) {}
        }

        var id = Math.round(Math.floor(Math.random()*self.Totals.get(tag).count));

        Stack<String> paramaters = new Stack<String>();

        if (options.width != 0) {paramaters.push("width="+options.width);}
        if (options.height != 0) {paramaters.push("height="+options.height);}
        if (options.aspectRatio != "") {paramaters.push("aspect_ratio="+options.aspectRatio);}

        var paramatersString = "";

        if (paramaters.size() > 0) {
            paramatersString = "?"+String.join("&", paramaters);
        }
        return "https://img.foxes.cool/"+tag+"/"+id+".jpg"+paramatersString;
    }

    public static String Fox(Options options) {
        return internal("fox", options);
    }

    public static String Curious(Options options) {
        return internal("curious", options);
    }

    public static String Happy(Options options) {
        return internal("happy", options);
    }

    public static String Scary(Options options) {
        return internal("scary", options);
    }

    public static String Sleeping(Options options) {
        return internal("sleeping", options);
    }
}
