package cr.ac.una.project_card.util;

/** * * @author Michelle Wittingham */
public class ImagesUtil {
    
    private static final String BASE_PATH = "/cr/ac/una/project_card/resources/";
    /**
     * Devuelve la URL de una imagen en src/main/resources/cr/ac/una/taskprogramll/resources/.
     * @param imageName Nombre del archivo (e.g., "stillBack.png").
     * @return URL como String para Image, o null si no se encuentra.
     */
    public static String getCardDifficultPath(String id) {
        try {
            String path = BASE_PATH + "ProgramImages/Cards/" + id + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato PNG " + id);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
            return null;
        }
    }

        public static String getBackCardPath(String id) {
        try {
            String path = BASE_PATH + "ProgramImages/Cards/Backs/" + id + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato PNG" + id);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + id + ": " + e.getMessage());
            return null;
        }
    }
        
        public static String getAchievement(String nameAchievement) {
        try {
            String path = BASE_PATH + "Achievements/" + nameAchievement + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato PNG " + nameAchievement);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + nameAchievement + ": " + e.getMessage());
            return null;
        }
    }

}
