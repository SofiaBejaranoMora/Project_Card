/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.project_card.util;

/**
 *
 * @author Michelle Wittingham
 */
public class ImagesUtil {
    private static final String BASE_PATH = "/cr/ac/una/Project_Card/resources/";
    
    //intenta con png o jpg
    public static String getCardImage(String id) {
        try {
            String[] extensions = {".jpg", ".png"};
            for (String ext : extensions) {
                String path = BASE_PATH + id + ext;
                if (ImagesUtil.class.getResource(path) != null) {
                    return ImagesUtil.class.getResource(path).toExternalForm();
                }
            }
            System.err.println("No se encontró la imagen para ID: " + id);
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
        }
        return null;
    }
}

