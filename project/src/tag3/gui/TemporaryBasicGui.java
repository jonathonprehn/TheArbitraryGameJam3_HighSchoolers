package tag3.gui;

import horsentp.gametime.GameTimer;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/13/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class TemporaryBasicGui extends BasicGui {

    private int ticksAlive, goalTicks;
    private GameTimer timer;

     public TemporaryBasicGui(int secondsAlive, GameTimer timer) {
         super();
         ticksAlive = 0;
         goalTicks = (timer.getTPS()*secondsAlive);
         this.timer = timer;
         super.setVisible(true);
     }

    public void forceEndTimer() {
        ticksAlive = goalTicks;
        super.setVisible(false);
    }

    public void resetTimer() {
        ticksAlive = 0;
        setVisible(true);
    }


    @Override
    public void setVisible(boolean visibility) {
        if (ticksAlive<goalTicks) {
            super.setVisible(visibility);
        }
    }

    @Override
    public void updateComponent() {
        super.updateComponent();
        ticksAlive++;
        if (ticksAlive>=goalTicks) {
            setVisible(false);
        }
    }
}
