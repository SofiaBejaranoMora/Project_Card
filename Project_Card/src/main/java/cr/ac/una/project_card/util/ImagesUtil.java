package cr.ac.una.project_card.util;

/** * * @author Michelle Wittingham */
public class ImagesUtil {
    
    private static final String BASE_PATH = "/cr/ac/una/project_card/resources/ProgramImages/";
    /**
     * Devuelve la URL de una imagen en src/main/resources/cr/ac/una/taskprogramll/resources/.
     * @param imageName Nombre del archivo (e.g., "stillBack.png").
     * @return URL como String para Image, o null si no se encuentra.
     */
    public static String getImagePath(int id) {
        try {
            String path = BASE_PATH + id + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen: " + path);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
            return null;
        }
    }
    
    public static String getCardImagePath(String id) {
    try {
        String pathPng = BASE_PATH + id + ".png";
        if (ImagesUtil.class.getResource(pathPng) != null) {
            return ImagesUtil.class.getResource(pathPng).toExternalForm();
        }

        String pathJpg = BASE_PATH + id + ".jpg";
        if (ImagesUtil.class.getResource(pathJpg) != null) {
            return ImagesUtil.class.getResource(pathJpg).toExternalForm();
        }

        System.err.println("No se encontró la imagen en formato PNG ni JPG: " + id);
        return null;
    } catch (Exception e) {
        System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
        return null;
    }
}
}
