package cr.ac.una.project_card.util;

/** * * @author Michelle Wittingham */
public class ImagesUtil {
    
    private static final String BASE_PATH = "/cr/ac/una/project_card/resources/";
    /**
     * Devuelve la URL de una imagen en src/main/resources/cr/ac/una/taskprogramll/resources/.
     * @param imageName Nombre del archivo (e.g., "stillBack.png").
     * @return URL como String para Image, o null si no se encuentra.
     */
     public static String getCardPath(String id) {
    try {
        String[] extensions = {".png", ".jpg"};
        for (String ext : extensions) {
            String path = BASE_PATH + "ProgramImages/" + id + ext;
            if (ImagesUtil.class.getResource(path) != null) {
                return ImagesUtil.class.getResource(path).toExternalForm();
            }
        }
        System.err.println("No se encontró la imagen en formato PNG ni JPG: " + id);
        return null;
    } catch (Exception e) {
        System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
        return null;
    }
}


    public static String getUserPath(String userImage) {
        try {
            String path = BASE_PATH + userImage + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen: " + path);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + userImage + ": " + e.getMessage());
            return null;
        }
    }


}
