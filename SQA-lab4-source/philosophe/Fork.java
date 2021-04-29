package philosophe;


/*
 * Cette classe modélise une fourchette.
 */
public final class Fork {

    private static final Object lock = new Object();

    private boolean free;

    private final int num;

    public Fork(final int n) {
        free = true;
        num = n;
    }


    /*
     * Cette méthode simule la saisie de la fourchettes.
     */
    public void grab() {
        synchronized (lock) {
            try {
                if (!free) {
                    lock.wait();
                }
                free = false;
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Cette méthode simule le relachement de la fourchette.
     */
    public void release() {
        synchronized (lock) {
            free = true;
            lock.notifyAll();
        }
    }

    @Override
    public String toString() {
        return "FORK num =" + num;
    }
}
