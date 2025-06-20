package cr.ac.una.project_card.util;

/** * * @author Michelle Wittingham */
public class ImagesUtil {
    
    private static final String BASE_PATH = "/cr/ac/una/project_card/resources/";
   
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

        public static String getCardPath(String style, String id) {
        try {
            String path = BASE_PATH + "ProgramImages/Cards/" + style + id + ".png";
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
        
    public static String getAchievement() {
        try {
            String path = BASE_PATH + "Achievements/";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato PNG ");
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen : " + e.getMessage());
            return null;
        }
    }

    public static String getBackground(String nameBackground) {
        try {
            String path = BASE_PATH + "ProgramImages/" + nameBackground + ".jpg";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato JPG " + nameBackground);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + nameBackground + ": " + e.getMessage());
            return null;
        }
    }

    public static String getAnimationImage(String nameBackground) {
        try {
            String path = BASE_PATH + "ProgramImages/" + nameBackground + ".png";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato PNG " + nameBackground);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + nameBackground + ": " + e.getMessage());
            return null;
        }
    }
        
    public static String getAnimationGif(String nameGif) {
        try {
            String path = BASE_PATH + "ProgramImages/" + nameGif + ".gif";
            if (ImagesUtil.class.getResource(path) == null) {
                System.err.println("No se encontró la imagen en formato GIF " + nameGif);
                return null;
            }
            return ImagesUtil.class.getResource(path).toExternalForm();
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + nameGif + ": " + e.getMessage());
            return null;
        }
    }
    
}
