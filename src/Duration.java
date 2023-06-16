public class Duration implements Cloneable {
    private int seconds;
    private int minutes;
    private final static int NUM_DIGITS_SECONDS_TO_PRINT = 2;

    Duration(int seconds) {
        // TODO: delete the exception section
        if(seconds < 0) {
            throw new ExceptionInInitializerError("There was entered a negative duration");
        }
        this.seconds = seconds % 60;
        this.minutes = seconds / 60;
    }

    public void setDuration(int seconds) {
        // TODO: delete the exception section
        if(seconds < 0) {
            throw new ExceptionInInitializerError("There was entered a negative duration");
        }
        this.seconds = seconds % 60;
        this.minutes = seconds / 60;
    }

    public int getDurationInSeconds() {
        return this.seconds + this.minutes * 60;
    }

    private int numOfZeroesToAdd(int num, int wishedNumDigits) {
        if (num == 0) {
            return wishedNumDigits;
        }
        int counter = 0;
        while (num > 0) {
            num = num / 10;
            counter++;
        }
        return wishedNumDigits - counter;
    }

    private String addZeroes(int num, int numZeroesToAdd) {
        String res = "";
        for (int i = 0; i < numZeroesToAdd; i++) {
            res += "0";
        }
        return res + Integer.toString(num);
    }

    @Override
    public String toString() {
        // TODO: check if the split line does problems
        String secondsToPrint =
                this.addZeroes(this.seconds, this.numOfZeroesToAdd(this.seconds, Duration.NUM_DIGITS_SECONDS_TO_PRINT));
        return Integer.toString(this.minutes) + ":" + secondsToPrint;
    }

    @Override
    public Object clone() {
        try {
            Duration ret = new Duration(this.seconds + this.minutes * 60);
            return ret;
        }
        catch (Exception e) {
            // TODO: delete this print
            System.out.println("USTON, we have a problem (Duration.clone)");
            return null;
        }
    }

    @Override
    public int hashCode() {
        return this.seconds + this.minutes * 60;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Duration)) {
            return false;
        }
        Duration otherDur = (Duration)other;
        if (this.minutes == otherDur.minutes && this.seconds == otherDur.seconds) {
            return true;
        }
        return false;
    }
}
