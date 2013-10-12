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
    private boolean counting;

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

    public boolean isCounting() {
        return counting;
    }

    public void setCounting(boolean counting) {
        this.counting = counting;
    }

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
        counting = true;
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

    private void notifyListenersOfTicksPassed() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).tickPassed();
        }
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
        if (counting) {
            ticks++;
            notifyListenersOfTicksPassed();
            //Validate numbers
            if (ticks>=ticksPerMinute) { //Minute passed!
                ticks = 0;
                minutes++;
                notifyListenersOfMinutePassed();
                if(minutes>=minutesPerHour) { //Hour passed!
                    minutes = 0;
                    hours++;
                    notifyListenersOfHourPassed();
                    if(hours>=hoursPerDay) {  //Day passed!
                        hours = 0;
                        days++;
                        notifyListenersOfDayPassed();
                        if(days>=daysPerWeek) { //Week passed!
                            days = 0;
                            weeks++;
                            notifyListenersOfWeekPassed();
                        }
                    }
                }
            }
        }
    }

    public void printData() {
        System.out.println("Counting: " + counting + "");
        System.out.println("Ticks: " + ticks + "");
        System.out.println("Minutes: " + minutes + "");
        System.out.println("Hours: " + hours + "");
        System.out.println("Days: " + days + "");
        System.out.println("Weeks: " + weeks + "");
        System.out.println();
    }
}
