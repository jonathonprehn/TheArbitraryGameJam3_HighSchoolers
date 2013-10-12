package tag3.states;

import horsentp.gamelogic.GameState;
import horsentp.input.KeyDownListener;
import tag3.gamelogic.GameCalender;
import tag3.gamelogic.PartyWrapper;
import tag3.gui.*;
import tag3.gui.GuiComponent;
import tag3.media.MediaLoader;
import tag3.party.Party;
import tag3.utility.GraphicsFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/11/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayState extends GameState implements KeyDownListener {

    //Graphical User Interface stuff
    protected GuiComponent[] labels, manageLabels;
    protected ImageButton[] buttons, manageButtons;
    protected ImageToggle[] toggles;
    private ImageLabel background;

    //Actual game stuff now
    protected ImageLabel[] gameLabels;
    protected GameCalender calender;
    protected PartyWrapper partyWrapper;                //Where should the Party go anyway?
    private Font infoFont;
    private TextLabel[] quickInfoText, muchInfoText;
    private ImageLabel partyImage;

    //Global Logic and managers
    boolean paused, managing;

    @Override
    public void updateLogic() {
        //System.out.println("Tick");
        if(!isPaused()) {  //Do logic only if the game is running!
            calender.tickCalender(); //Calender handles the updating of the party (PartyWrapper is a GameCalenderListener)
            updateInfoText();
            partyImage.setImage(partyWrapper.getCurrentAnimationFrame());
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        buttons[2].setVisible(paused);
        buttons[3].setVisible(paused);
        buttons[4].setVisible(paused);
        labels[3].setVisible(paused);
        buttons[2].setEnabled(paused);
        buttons[3].setEnabled(paused);
        buttons[4].setEnabled(paused);

        toggles[0].setEnabled(!paused);
        toggles[1].setEnabled(!paused);
        buttons[0].setEnabled(!paused);
        buttons[1].setEnabled(!paused);
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isManaging() {
        return managing;
    }

    public void setManaging(boolean managing) {
        this.managing = managing;
        for (int i=0; i<manageLabels.length; i++) {
            if (manageLabels[i] == null) {
                continue;
            }
            manageLabels[i].setVisible(managing);
        }
        for (int i=0; i<manageButtons.length; i++) {
            if (manageLabels[i] == null) {
                continue;
            }
            manageButtons[i].setVisible(managing);
        }
    }

    @Override
    public void initState() {
        //Global values
        paused = false;
        managing = false;

        //Init arrays
        buttons = new ImageButton[5]; //Sleep, manage, resume, quit, and main menu buttons
        labels = new ImageLabel[4]; //Info bar, quick info, pause background, and much info
        toggles = new ImageToggle[3]; //More info, pause and move toggles

        //Other values
        int quickInfoCornerX, quickInfoCornerY, muchInfoCornerX, muchInfoCornerY;

        //Init things for the walking game cycle
        buttons[0] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("play_state_images/sleepUp.png"),
                MediaLoader.quickLoadImage("play_state_images/sleepDown.png"),
                0, 0, new SleepButtonListener()
        );
         buttons[1] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("play_state_images/manageUp.png"),
                MediaLoader.quickLoadImage("play_state_images/manageDown.png"),
                150, 0, new ManageButtonListener()
         );

        toggles[0] = GraphicsFactory.getFactory().makeLinkedImageToggle(
                    MediaLoader.quickLoadImage("play_state_images/moving.png"),
                    MediaLoader.quickLoadImage("play_state_images/notMoving.png"),
                    300, 0, false, new MovementToggleListener()
            );
        toggles[1] = GraphicsFactory.getFactory().makeLinkedImageToggle(
                MediaLoader.quickLoadImage("play_state_images/moreInfoOff.png"),
                MediaLoader.quickLoadImage("play_state_images/moreInfoOn.png"),
                725, 70, false, new MoreInfoListener()
        );
        quickInfoCornerX = 500;
        quickInfoCornerY = 0;
        muchInfoCornerX = 600;
        muchInfoCornerY = 100;
        labels[0] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/quickInfoBackground.png"), quickInfoCornerX, quickInfoCornerY);
        labels[1] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/muchInfoBackground.png"), muchInfoCornerX, muchInfoCornerY);
        labels[2] = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/distanceBar.png"), 0, 100);

        //Special case stuff for game
        labels[1].setVisible(false);
        background = new ImageLabel(MediaLoader.quickLoadImage("play_state_images/gameBackground.png"), 0, 0);

        //Init things for the pause menu
        int centerWidth = (getDisplayer().getDisplayWidth()/2);
        int centerHeight = (getDisplayer().getDisplayHeight()/2);
        labels[3] = new ImageLabel(MediaLoader.quickLoadImage("pause_menu_images/pauseBackground.png"), centerWidth-200,centerHeight-250);

        buttons[2] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("pause_menu_images/resumeUp.png"),
                MediaLoader.quickLoadImage("pause_menu_images/resumeDown.png"),
                centerWidth-100, centerHeight-200, new ResumeButtonListener()
        );

        buttons[3] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("pause_menu_images/mainMenuUp.png"),
                MediaLoader.quickLoadImage("pause_menu_images/mainMenuDown.png"),
                centerWidth-100, centerHeight-50, new MainMenuButtonListener()
        );

        buttons[4] = GraphicsFactory.getFactory().makeLinkedImageButton(
                MediaLoader.quickLoadImage("pause_menu_images/quitUp.png"),
                MediaLoader.quickLoadImage("pause_menu_images/quitDown.png"),
                centerWidth-100, centerHeight+100, new QuitButtonListener()
        );

        labels[3].setVisible(false);
        buttons[2].setVisible(false);
        buttons[3].setVisible(false);
        buttons[4].setVisible(false);

        //Init things for management menu
        manageLabels = new ImageLabel[7]; //background, 3 icons, 1 image for 3 total boxes
        manageButtons = new ImageButton[7]; //Kick out buttons and exit button

        //Load total box
        BufferedImage totalBox = MediaLoader.quickLoadImage("management_images/totalLabel.png");

        manageLabels[0] = new ImageLabel(MediaLoader.quickLoadImage("management_images/managementBackground.png"), centerWidth-300, centerHeight-225);
        manageLabels[1] = new ImageLabel(MediaLoader.quickLoadImage("management_images/giraffeIcon.png"), centerWidth+160, centerHeight-160);
        manageLabels[2] = new ImageLabel(MediaLoader.quickLoadImage("management_images/lionIcon.png"), centerWidth+160, centerHeight-50);
        manageLabels[3] = new ImageLabel(MediaLoader.quickLoadImage("management_images/llamaIcon.png"), centerWidth+160, centerHeight+60);
        manageLabels[4] = new ImageLabel(totalBox, centerWidth+50, centerHeight-160);
        manageLabels[5] = new ImageLabel(totalBox, centerWidth+50, centerHeight-50);
        manageLabels[6] = new ImageLabel(totalBox, centerWidth+50, centerHeight+60);

        //Load images for buttons
        BufferedImage dataDown = MediaLoader.quickLoadImage("management_images/dataButtonDown.png");
        BufferedImage dataUp = MediaLoader.quickLoadImage("management_images/dataButtonUp.png");

        manageButtons[0] = GraphicsFactory.getFactory().makeLinkedImageButton(
            dataUp, dataDown, centerWidth-220, centerHeight-160, new KickOutNormalGiraffe()
        );
        manageButtons[1] = GraphicsFactory.getFactory().makeLinkedImageButton(
                dataUp, dataDown, centerWidth-85, centerHeight-160, new KickOutDiseasedGiraffe()
        );
        manageButtons[2] = GraphicsFactory.getFactory().makeLinkedImageButton(
                dataUp, dataDown, centerWidth-220, centerHeight-50, new KickOutNormalLion()
        );
        manageButtons[3] = GraphicsFactory.getFactory().makeLinkedImageButton(
                dataUp, dataDown, centerWidth-85, centerHeight-50, new KickOutDiseasedLion()
        );
        manageButtons[4] = GraphicsFactory.getFactory().makeLinkedImageButton(
                dataUp, dataDown, centerWidth-220, centerHeight+60, new KickOutNormalLlama()
        );
        manageButtons[5] = GraphicsFactory.getFactory().makeLinkedImageButton(
                dataUp, dataDown, centerWidth-85, centerHeight+60, new KickOutDiseasedLlama()
        );
        manageButtons[6] = GraphicsFactory.getFactory().makeLinkedImageButton(
            MediaLoader.quickLoadImage("buttons/xUp.png"), MediaLoader.quickLoadImage("buttons/xDown.png"),
            centerWidth+250, centerHeight-225, new ExitManageButton()
        );

        //Pause button
        toggles[2] = GraphicsFactory.getFactory().makeLinkedImageToggle(
                MediaLoader.quickLoadImage("toggles/pauseOn.png"),
                MediaLoader.quickLoadImage("toggles/pauseOff.png"),
                740, 520, false, new PauseToggleListener()
        );
        //Graphic User Interface stuff initialed! (Whew!)

        //Notifications and resources
        gameLabels = new ImageLabel[6];
        //Water
        gameLabels[0] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/waterNotification.png"), 20, 160);
        gameLabels[1] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/waterResource.png"), 20, 300);
        //Meat
        gameLabels[2] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/meatNotification.png"), 20, 160);
        gameLabels[3] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/meatResource.png"), 20, 300);
        //Plant
        gameLabels[4] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/plantNotification.png"), 20, 160);
        gameLabels[5] = new ImageLabel(MediaLoader.quickLoadImage("notifications_and_resources/plantResource.png"), 20, 300);

        hideGameLabels();

        //Init game logic handlers
        calender = new GameCalender();
        // start with 15 of each animal at the start
        partyWrapper = new PartyWrapper(15,15,15, calender);    //Party initialized here!
        infoFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

        //Init info text
        quickInfoText = new TextLabel[6];
        muchInfoText = new TextLabel[9];
        int xOffsetMuchInfo = 10;
        int yOffsetMuchInfo = 30;

        //Total animal num, total diseased animal num
        // morale, water supplies, food supplies, days since last slept
        quickInfoText[0] = makeInfoText("Total Animals:", quickInfoCornerX+10, quickInfoCornerY+20);
        quickInfoText[1] = makeInfoText("Total Diseased Animals:", quickInfoCornerX+130, quickInfoCornerY+20);
        quickInfoText[2] = makeInfoText("Morale:", quickInfoCornerX+10, quickInfoCornerY+50);
        quickInfoText[3] = makeInfoText("Food:", quickInfoCornerX+110, quickInfoCornerY+50);
        quickInfoText[4] = makeInfoText("Water:", quickInfoCornerX+210, quickInfoCornerY+50);
        quickInfoText[5] = makeInfoText("Days awake:", quickInfoCornerX+10, quickInfoCornerY+80);

        //Giraffe num, lion num, llama num, diseased giraffe num, diseased lion num, diseased llama num
        //Days passed, rate of water consumption, rate of food consumption
        muchInfoText[0] = makeInfoText("Giraffes:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*1));
        muchInfoText[1] = makeInfoText("Lions:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*2));
        muchInfoText[2] = makeInfoText("Llamas:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*3));
        muchInfoText[3] = makeInfoText("Diseased Giraffes:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*4));
        muchInfoText[4] = makeInfoText("Diseased Lions:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*5));
        muchInfoText[5] = makeInfoText("Diseased Llamas:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*6));
        muchInfoText[6] = makeInfoText("Water drank:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*7));
        muchInfoText[7] = makeInfoText("Food eaten:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*8));
        muchInfoText[8] = makeInfoText("Days traveled:", muchInfoCornerX+xOffsetMuchInfo, muchInfoCornerY+(yOffsetMuchInfo*9));

        partyImage = new ImageLabel(partyWrapper.getCurrentAnimationFrame(), 300, 150);

        //Init input
        getInput().addKeyDownListener(this);
    }

    //Make sure the text actually shows the correct values
    public void updateInfoText() {
        //Quick info
        quickInfoText[0].setText("Total Animals: " + (partyWrapper.getRawParty().getNumberOfDiseased()+partyWrapper.getRawParty().getNumberOfNonDiseased()) + "");
        quickInfoText[1].setText("Total Diseased Animals: " + partyWrapper.getRawParty().getNumberOfDiseased() + "");
        quickInfoText[2].setText("Morale: " + partyWrapper.getRawParty().getMorale() + "");
        quickInfoText[3].setText("Food: " + partyWrapper.getRawParty().getFoodAmount() + "");
        quickInfoText[4].setText("Water: " + partyWrapper.getRawParty().getWaterAmount() + "");
        quickInfoText[5].setText("Days awake: " + partyWrapper.getRawParty().getDaysSinceSlept() + "");

        //Much info
        muchInfoText[0].setText("Giraffes: " + partyWrapper.getRawParty().getNumGiraffe() + "");
        muchInfoText[1].setText("Lions: " + partyWrapper.getRawParty().getNumLion() + "");
        muchInfoText[2].setText("Llamas: " + partyWrapper.getRawParty().getNumLlama() + "");
        muchInfoText[3].setText("Diseased Giraffes: " + partyWrapper.getRawParty().getNumDiseasedGiraffe() + "");
        muchInfoText[4].setText("Diseased Lions: " + partyWrapper.getRawParty().getNumDiseasedLion() + "");
        muchInfoText[5].setText("Diseased Llamas: " + partyWrapper.getRawParty().getNumDiseasedLlama() + "");
        muchInfoText[6].setText("Water drank: " + partyWrapper.getRawParty().getConsumeRate() + "");
        muchInfoText[7].setText("Food eaten: " + partyWrapper.getRawParty().getConsumeRate() + "");
        muchInfoText[8].setText("Days traveled: " + partyWrapper.getDaysPassed() + "");
    }

    private TextLabel makeInfoText(String text, int x, int y) {
        TextLabel l = new TextLabel(text, x, y);
        l.setFont(infoFont);
        return l;
    }

    @Override
    public BufferedImage render(BufferedImage bufferedImage, Graphics2D graphics2D) {
        graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        //Draw the game
        bufferedImage = background.render(bufferedImage, graphics2D); //Game Background
        bufferedImage = partyImage.render(bufferedImage, graphics2D); //Draw the party
        bufferedImage = drawWalkingPart(bufferedImage, graphics2D); //UI mostly
        bufferedImage = drawResources(bufferedImage, graphics2D); //Resources and resource spots
        bufferedImage = toggles[2].render(bufferedImage, graphics2D); //Draw the pause button

        //Info text
        bufferedImage = drawQuickTextInfo(bufferedImage, graphics2D); //Quick info
        if (toggles[1].isToggle()) { //Is more info requested?
            bufferedImage = drawMoreTextInfo(bufferedImage, graphics2D); //Much info
        }

        //Draw management window if managing
        if (isManaging()) {
            bufferedImage = drawManageMenu(bufferedImage, graphics2D); //Management window
        }
        //Draw the pause menu if it is paused
        if (isPaused()) {
            bufferedImage = drawPauseMenu(bufferedImage, graphics2D); //Pause menu
        }
        return bufferedImage;
    }

    //Draw quick info text labels
    private BufferedImage drawQuickTextInfo(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        for (int i=0; i<quickInfoText.length; i++) {
            bImage = quickInfoText[i].render(bImage, g2d);
        }
        return bImage;
    }

    //Draw much info text labels
    private BufferedImage drawMoreTextInfo(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        for (int i=0; i<muchInfoText.length; i++) {
            bImage = muchInfoText[i].render(bImage, g2d);
        }
        return bImage;
    }

    //Buttons 0, 1. Toggles 0, 1, labels 0, 1, 2
    private BufferedImage drawWalkingPart(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        bImage = labels[0].render(bImage, g2d);
        bImage = labels[1].render(bImage, g2d);
        bImage = labels[2].render(bImage, g2d);
        bImage = buttons[0].render(bImage, g2d);
        bImage = buttons[1].render(bImage, g2d);
        bImage = toggles[0].render(bImage, g2d);
        bImage = toggles[1].render(bImage, g2d);
        return bImage;
    }

    //GuiComponent 3, buttons 2, 3, 4
    private BufferedImage drawPauseMenu(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        bImage = labels[3].render(bImage, g2d);
        bImage = buttons[2].render(bImage, g2d);
        bImage = buttons[3].render(bImage, g2d);
        bImage = buttons[4].render(bImage, g2d);
        return bImage;
    }

    private BufferedImage drawManageMenu(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        for (int i=0; i<manageLabels.length; i++) {
            if (manageLabels[i] == null) {
                continue;
            }
            bImage = manageLabels[i].render(bImage, g2d);
        }
        for (int j=0; j<manageButtons.length; j++) {
            if (manageButtons[j] == null) {
                continue;
            }
            bImage = manageButtons[j].render(bImage, g2d);
        }
        return bImage;
    }

    private BufferedImage drawResources(BufferedImage bImage, Graphics2D g2d) {
        g2d = (Graphics2D) bImage.getGraphics();
        for (int i=0; i<gameLabels.length; i++) {
            if (gameLabels[i] == null) {
                continue;
            }
            bImage = gameLabels[i].render(bImage, g2d);
        }
        return bImage;
    }

    @Override
    public void reactToKeyDown(KeyEvent keyEvent) {
        //P or Esc for pausing
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_P:
                togglePaused();
                break;
            case KeyEvent.VK_ESCAPE:
                togglePaused();
                break;
        }
    }

    public void togglePaused() {
        if (isPaused()) {
            setPaused(false);
        } else {
            setPaused(true);
        }
    }

    private void hideGameLabels() {
        for (int i=0; i<gameLabels.length; i++) {
            if (gameLabels[i] == null) {
                continue;
            }
            gameLabels[i].setVisible(false);
        }
    }

    private void notifyWater() {
        hideGameLabels();
        gameLabels[0].setVisible(true);
        gameLabels[1].setVisible(true);
    }

    private void notifyMeat() {
        hideGameLabels();
        gameLabels[2].setVisible(true);
        gameLabels[3].setVisible(true);
    }

    private void notifyPlants() {
        hideGameLabels();
        gameLabels[4].setVisible(true);
        gameLabels[5].setVisible(true);
    }

    class SleepButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class ManageButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {
            setManaging(true);
        }
    }

    class ExitManageButton implements GenericButtonListener {
        @Override
        public void buttonPushed() {
            setManaging(false);
        }
    }

    class ResumeButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {
            setPaused(false);
        }
    }

    class PauseToggleListener implements GenericToggleListener {
        @Override
        public void toggleChanged(boolean valueSetTo) {
            setPaused(valueSetTo);
        }
    }

    class MainMenuButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {
            getRunner().changeState(new MainMenuState());
        }
    }

    class QuitButtonListener implements GenericButtonListener {
        @Override
        public void buttonPushed() {
            getRunner().exitGame();
        }
    }

    class KickOutNormalLlama implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class KickOutDiseasedLlama implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class KickOutNormalGiraffe implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class KickOutDiseasedGiraffe implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class KickOutNormalLion implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class KickOutDiseasedLion implements GenericButtonListener {
        @Override
        public void buttonPushed() {

        }
    }

    class MoreInfoListener implements GenericToggleListener {
        @Override
        public void toggleChanged(boolean valueSetTo) {
            labels[1].setVisible(valueSetTo);
        }
    }

    class MovementToggleListener implements GenericToggleListener {
        @Override
        public void toggleChanged(boolean valueSetTo) {

        }
    }
}
