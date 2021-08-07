package priv.chunyu.Controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;

public class MapViewController {
    private static WebEngine engine;
    @FXML
    private WebView webView;

    public static void add(String id, String latlng) {
        String lat = latlng.split(",")[0];
        String lng = latlng.split(",")[1];
        engine.executeScript("addmarker(" + id + "," + lat + "," + lng + ")");
    }

    @FXML

    private void initialize() {
        engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);
        File f = new File("map/map.html");
        engine.load(f.toURI().toString());
    }
}
