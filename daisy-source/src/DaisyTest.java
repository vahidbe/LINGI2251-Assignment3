import daisy.*;

/**
 * The DaisyTest provides a threaded example that will exercise
 * the Daisy file system.  It will initialize the file system and then create
 * 2 users of the file system.
 * 
 * @author Todd Wallentine tcw AT cis ksu edu
 * @version $Revision: 1.1 $ - $Date: 2004/12/15 20:47:45 $
 * 
 * Modified by Charles Pecheur, UC Louvain
 */
public class DaisyTest {

	/**
	 * The main method will initialize the file system by creating a file, initializing it with contents, 
	 * and then starting 2 user threads (one reader and one unliner).
	 * The main method then waits for the threads to complete and then completes.
	 * 
	 * @param args Ignored.
	 */
	public static void main(String[] args) {
		
		final int FILECOUNT = 1;
		final int ITERATIONS = 1;
		
		FileHandle root = new FileHandle();
		root.setInodenum(0);
		FileHandle cowFileHandle = new FileHandle();
		Petal.init(false);

		FileHandle[] fileHandles = new FileHandle[FILECOUNT];
		for(int i = 0; i < fileHandles.length; i++) {
			fileHandles[i] = new FileHandle();
		}
		byte[][] filenames = new byte[FILECOUNT][];
		filenames[0] = stringToBytes("cow");
		//filenames[1] = stringToBytes("dog"); //test d
		//filenames[2] = stringToBytes("cat"); //test e
		int status = 0;
		for(int i = 0; i < filenames.length; i++) {
			status = DaisyDir.creat(root, filenames[i], fileHandles[i]);
			assert status == 0;
			byte[] data = stringToBytes("someData");
			status = DaisyDir.write(fileHandles[i], 0, data.length, data);
			assert status == 0;
		}
		
		System.out.println("Creating the DaisyUserThreads ...");
		DaisyUserThread thread1 = new DaisyUserThread(DaisyUserThread.READ_OPERATION, ITERATIONS, filenames, root);
		DaisyUserThread thread2 = new DaisyUserThread(DaisyUserThread.DELETE_OPERATION, ITERATIONS, filenames, root); //WRITE_OPERATION for test a, RANDOM_OPERATION for test b, 2 instead of ITERATIONS for test c
                DaisyUserThread thread3 = new DaisyUserThread(DaisyUserThread.WRITE_OPERATION, ITERATIONS, filenames, root); // test f

		System.out.println("Starting the DaisyUserThreads ...");
		thread1.start();
		thread2.start();
		//thread3.start(); //test f
		
			try {
				thread1.join();
				thread2.join();
				//thread3.join(); //test f
			} catch(Exception e) {
				System.err.println("Error joining DaisyUserThread.");
			}
		
		System.out.println("Finished.");
	}
	
	static byte[] stringToBytes(String s) {
		byte b[] = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			b[i] = (byte) s.charAt(i);
		}
		return b;
	}
}
