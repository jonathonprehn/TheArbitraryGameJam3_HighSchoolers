package tag3.gamelogic;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/13/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConfirmCommand {
    public void preCommandAction();
    public void onYes();
    public void onNo();
}