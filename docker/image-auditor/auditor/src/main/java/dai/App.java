package dai;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Auditor start listening and send info" );
        try {
            Thread t1 = new Thread(new AuditorListen());
            t1.start();
            Thread t2 = new Thread(new SenderInfo());
            t2.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
