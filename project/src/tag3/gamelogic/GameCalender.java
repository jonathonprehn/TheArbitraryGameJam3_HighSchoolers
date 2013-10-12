package tag3.gamelogic;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathon
 * Date: 10/12/13
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameCalender {

    private int weeks, days, hours, minutes;
    private int ticksPerMinute;

    public int getDaysPerWeek() {
        return daysPerWeek;
    }

    public void setDaysPerWeek(int daysPerWeek) {
        this.daysPerWeek = daysPerWeek;
    }

    public int getTicksPerMinute() {
        return ticksPerMinute;
    }

    public void setTicksPerMinute(int ticksPerMinute) {
        this.ticksPerMinute = ticksPerMinute;
    }

    public int getMinutesPerHour() {
        return minutesPerHour;
    }

    public void setMinutesPerHour(int minutesPerHour) {
        this.minutesPerHour = minutesPerHour;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    private int minutesPerHour;
    private int hoursPerDay;
    private int daysPerWeek;
    private int ticks;
    private ArrayList<GameCalenderListener> listeners;

    public GameCalender() {
        listeners = new ArrayList<GameCalenderListener>();
        weeks = 0;
        days = 0;
        hours = 0;
        minutes = 0;
        ticks = 0;
        ticksPerMinute = 60;
        minutesPerHour = 60;
        hoursPerDay = 24;
        daysPerWeek = 7;
    }

    public void addCalenderListener(GameCalenderListener gcl) {
        listeners.add(gcl);
    }

    public void removeCalenderListener(GameCalenderListener gcl) {
        listeners.remove(gcl);
    }

    public void clearCalenderListeners() {
        listeners.clear();
    }

    private void notifyListenersOfMinutePassed() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).minutePassed();
        }
    }

    private void notifyListenersOfHourPassed() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).hourPassed();
        }
    }

    private void notifyListenersOfDayPassed() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).dayPassed();
        }
    }

    private void notifyListenersOfWeekPassed() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).weekPassed();
        }
    }

    public void tickCalender() {
        ticks++;
        //Validate numbers
        if (ticks>=ticksPerMinute) { //Minute passed!
            ticks = 0;
            minutes++;
            if(minutes>=minutesPerHour) { //Hour passed!
                minutes = 0;
                hours++;
                if(hours>=hoursPerDay) {  //Day passed!
                    hours = 0;
                    days++;
                    if(days>=daysPerWeek) { //Week passed!
                        days = 0;
                        weeks++;
                    }
                }
            }
        }
    }
}
