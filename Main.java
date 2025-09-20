import javax.swing.SwingUtilities;
import controller.AppController;

public class Main {
    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AppController controller = new AppController();
			controller.start();
		});
	}
}
