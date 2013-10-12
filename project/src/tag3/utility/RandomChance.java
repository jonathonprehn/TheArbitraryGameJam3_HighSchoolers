package tag3.utility;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomChance {
    public static boolean rollForChance(int percentSuccessful) {
        if (percentSuccessful>=0 || percentSuccessful<=100) {
            int randomNumber = (int)(Math.random()*100);
            if (randomNumber<=percentSuccessful) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Need to give the RandomChance class a number between 0 and 100!");
            return false;
        }
    }
    public static boolean randomBoolean() {
        return (Math.random() > 0.5);
    }
}
