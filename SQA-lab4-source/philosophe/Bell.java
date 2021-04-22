package philosophe;

/*
 * Cette classe modélise la clochette.
 */
public final class Bell extends Thread {

    private static boolean cpt = true;

    /**
     * Sonne la clache.
     * 
     * Retourne true si et seulement si la cloche a été sonnée depuis le
     * lancement du programme un nombre pair de fois.
     */
    public static boolean ring() {
        final Object lock = new Object();
        synchronized (lock) {
            final boolean result = cpt;
            cpt = !cpt;
            return result;
        }
    }

    private Bell() {
    }
}