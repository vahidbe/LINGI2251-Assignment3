package philosophe;

/*
 * Cette classe est le point d'entrée du programme.
 */
public class DiningPhilosophers {

    // Le nombre de philosophe.
    public static final int NUM_PHILS = 3;

    public static void main(final String[] args) {

        // Création des fourchettes
        final Fork[] forks = new Fork[NUM_PHILS];
        for (int i = 0; i != NUM_PHILS; i++) {
            forks[i] = new Fork(i);
        }

        // Création des philosophes.
        final Philosopher[] philosophers = new Philosopher[NUM_PHILS];
        for (int i = 0; i != NUM_PHILS; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % NUM_PHILS]);
            // philosophers[i].fill();
	    philosophers[i].start();
        }
    }
}
