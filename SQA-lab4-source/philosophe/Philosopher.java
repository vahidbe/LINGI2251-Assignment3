package philosophe;

/*
 * Cette classe modélise un philosophe.
 */
public class Philosopher extends Thread {

    private static int DEFAULT_AMOUNT_OF_FOOD = 3;

    private int amountOfFood = 0;

    private final Fork left;

    private final int num;

    private final Fork right;

    public Philosopher(final int num, final Fork left, final Fork right) {
        this.num = num;
        this.left = left;
        this.right = right;
    }

    // Cette méthode retourne la quantité de nourriture présente dans
    // l'assiette du philosophe.
    public synchronized int amountOfFood() {
        return amountOfFood;
    }

    // Cette méthode remplit l'assiette du philosophe de
    // DEFAULT_AMOUNT_OF_FOOD.
    public void fill() {
        amountOfFood = DEFAULT_AMOUNT_OF_FOOD;
    }

    @Override
    public void run() {
        try {
            while (true) {
                assert amountOfFood >= 0;

                // Pense
                sleep(num * 100);

                // Saisie des fourchettes
                if (num % 2 == 0) {
		    left.grab();
                    right.grab();
		} else {
		    right.grab();
		    left.grab();
		}


                // Dégustation
                if (amountOfFood <= 0) {
                    final boolean cpt = Bell.ring();
                    System.out.println(cpt);
                    fill();
                }
		amountOfFood = amountOfFood - 1;

                // Reclacgement des fourchettes
                left.release();
                right.release();
            }
        } catch (final InterruptedException e) {
            new RuntimeException(e);
        }
    }
}
