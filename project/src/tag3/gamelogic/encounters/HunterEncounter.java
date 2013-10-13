package tag3.gamelogic.encounters;

import horsentp.input.KeyDownListener;
import horsentp.input.KeyUpListener;
import tag3.gamelogic.ConfirmCommand;
import tag3.gamelogic.PartyWrapper;
import tag3.states.GameOverState;
import tag3.states.PlayState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Starbuck Johnson on 10/13/13.
 */
public class HunterEncounter implements RandomEncounter, KeyDownListener{
    @Override
    public double getChancePerHour() {
        return 30;
    }

    @Override
    public void handleEncounter(PartyWrapper partyWrapper, final PlayState gameState) {
<<<<<<< HEAD
        gameState.askForConfirmation(new HunterDialog(gameState, partyWrapper));
    }

    @Override
    public void reactToKeyDown(KeyEvent keyEvent) {

    }

    private class HunterDialog implements ConfirmCommand, KeyUpListener, KeyDownListener {
        private PlayState gameState;
        private PartyWrapper partyWrapper;

        private boolean spaceLastUp = true;
        private int remainingToggles = 10;
        private long lastDownMillis = System.currentTimeMillis();
        private boolean complete = false;

        public HunterDialog(PlayState state, PartyWrapper wrapper) {
            this.gameState = state;
            this.partyWrapper = wrapper;
        }

        public void preCommandAction() {
            gameState.setOtherResourceDialogText("Hunters have appeared!");
            gameState.setResourceDialogText("JAM SPACE!!!!");

            gameState.getInput().addKeyDownListener(this);
            gameState.getInput().addKeyUpListener(this);

            Timer timer = new Timer();
            timer.schedule(new CheckForCompletion(this), 1000);
        }

        @Override
        public void onYes() {
        }

        @Override
        public void onNo() {
        }

        public synchronized void setComplete() {
            complete = true;
        }

        @Override
        public void reactToKeyDown(KeyEvent keyEvent) {
            if (complete) {
                return;
=======
        gameState.askForConfirmation(new ConfirmCommand() {
            @Override
            public void preCommandAction() {
                gameState.setResourceDialogText("Hunters have appeared!");
                gameState.setOtherResourceDialogText("");
>>>>>>> 0390dad4338e3f71317adcd4fa8883213e9802aa
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                if (spaceLastUp) {
                    spaceLastUp = false;
                    lastDownMillis = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - lastDownMillis >= 500) {
                    System.out.println("You lose.");
                    gameState.getRunner().changeState(new GameOverState("Lost to hunters."));
                }
            }
        }

        @Override
        public void reactToKeyUp(KeyEvent keyEvent) {
            if (complete) {
                return;
            }
            if (System.currentTimeMillis() - lastDownMillis >= 500) {
                System.out.println("You lose.");
                gameState.getRunner().changeState(new GameOverState("Lost to hunters."));
            } else {
                spaceLastUp = true;
                remainingToggles--;
            }
        }

        private class CheckForCompletion extends TimerTask {
            private HunterDialog dialog;

<<<<<<< HEAD
            public CheckForCompletion(HunterDialog dialog) {
                this.dialog = dialog;
=======
            @Override
            public void onYes() {gameState.setPressed(false);
                gameState.setAsking(true);
                gameState.setPressed(false);
                gameState.setResourceDialogText("You have lost half your party!");
                //Future             //I am not sure if that should be there or not
>>>>>>> 0390dad4338e3f71317adcd4fa8883213e9802aa
            }

            public void run() {
                if (this.dialog.remainingToggles <= 0) {
                    System.out.println("You made it");
                    gameState.setAsking(false);
                    gameState.setPressed(true);
                    dialog.setComplete();
                } else {
                    System.out.println("You didn't make it.");
                    gameState.getRunner().changeState(new GameOverState("Lost to hunters"));
                }
            }
<<<<<<< HEAD
        }
=======

            public boolean isAChoice() { return false; }
        });
>>>>>>> 0390dad4338e3f71317adcd4fa8883213e9802aa
    }
}