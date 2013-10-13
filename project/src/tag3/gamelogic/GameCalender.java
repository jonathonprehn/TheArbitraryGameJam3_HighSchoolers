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

    private int weeks;

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getWeeks() {
        return weeks;
    }

    private int days;
    private int hours;
    private int minutes;
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

    public static GameCalender getFromDays(double days) {
        int weeks = (int)Math.floor(days / 7);
        days = days - (weeks * 7);
        int theDays = (int)Math.floor(days);
        days = days - (theDays);
        int hours = (int)Math.floor(days/24);
        GameCalender calender = new GameCalender();
        calender.weeks = weeks;
        calender.days = theDays;
        calender.hours = hours;
        return calender;
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
        System.out.println(toString());
        System.out.println();
    }

    @Override
    public String toString() {
        return ("Counting: " + counting + "") +
        ("Ticks: " + ticks + "") +
        ("Minutes: " + minutes + "")+
        ("Hours: " + hours + "") +
        ("Days: " + days + "") +
        ("Weeks: " + weeks + "");
    }

    public String getTimeDisplay(DisplayPrecision precision) {
        String timeDisplay = "";
        switch (precision) {
            case TICKS:
                timeDisplay += weeks + " Weeks " + days + " Days " + hours + " Hours " + minutes + " Minutes " + ticks + " Ticks." ;
                break;
            case MINUTES:
                timeDisplay += weeks + " Weeks " + days + " Days " + hours + " Hours " + minutes + " Minutes.";
                break;
            case HOURS:
                timeDisplay += weeks + " Weeks " + days + " Days " + hours + " Hours.";
                break;
            case DAYS:
                timeDisplay += weeks + " Weeks " + days + " Days.";
                break;
            case WEEKS:
                timeDisplay += weeks + " Weeks." ;
                break;
        }
        return timeDisplay;
    }

    public enum DisplayPrecision {
        TICKS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS
    }
}
