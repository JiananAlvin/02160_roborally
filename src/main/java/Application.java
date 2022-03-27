import gui.GUIAdaptor;

/**
 * This class is an entrance for our user application
 */
public class Application {
    GUIAdaptor adaptor;

    public Application() {
        this.adaptor = new GUIAdaptor();
    }

    public void run() {

    }

    public static void main(String[] args) {
//        new Application().run();
        String a = null;
        System.out.println("test".equals(a));
        System.out.println(a.equals("test"));
    }
}
