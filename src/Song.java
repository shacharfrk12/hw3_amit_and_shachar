/**
 * class that represents a song
 */
public class Song implements Cloneable{
    private final String name;
    private final String artist;
    private Genre genre;
    private int duration;
    // minimum number of digits to show in toString (add zeroes at the beginning if necessary)
    private final static int NUM_DIGITS_IN_SECONDS = 2;

    public Song(String name, String artist, Genre genre, int duration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }
    public String getName(){
        return this.name;
    }

    public String getArtist(){
        return this.artist;
    }

    public Genre getGenre(){
        return this.genre;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * clone the current song, with deep copy
     * @return the copied song
     */
    @Override
    public Song clone() {
        try {
            return (Song)super.clone();
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * calculate (wishedNumDigits - (num digits of num))
     * @param num the number to calculate its number of digits
     * @param wishedNumDigits the wished number of digits
     * @return (wishedNumDigits - (num digits of num)) if it is not negative, else 0
     */
    private int numOfZeroesToAdd(int num, int wishedNumDigits) {
        if (num == 0) {
            return wishedNumDigits;
        }
        int counter = 0;
        while (num > 0) {
            num = num / 10;
            counter++;
        }
        return wishedNumDigits - counter >= 0 ? wishedNumDigits - counter : 0;
    }

    /**
     * add numZeroesToAdd in the beginning of the String conversion of num
     * @param num the number to add zeroes to it
     * @param numZeroesToAdd number of zeroes to add
     * @return the string that contains the zeroes with the number
     */
    private String addZeroes(int num, int numZeroesToAdd) {
        String res = "";
        for (int i = 0; i < numZeroesToAdd; i++) {
            res += "0";
        }
        return res + Integer.toString(num);
    }

    /**
     * checks if a song fits a certain filter
     * @param artistToFilter artist to compare to this artist, it null the song fits the artist filter always,
     *                       otherwise it only fits if the songs artist is the same as given artist
     * @param genreToFilter genre to compare to this artist, it null the song fits the genre filter always,
     *      *               otherwise it only fits if the songs genre is the same as given genre
     * @param durationToFilter max duration, only fits duration filter if the duration is smaller or equal
     *                         to given duration
     * @return true if fits filter, false otherwise
     */
    public boolean songFitsFilter(String artistToFilter, Song.Genre genreToFilter,
                                         int durationToFilter){
        boolean isArtist = (artistToFilter==null || (this.artist).equals(artistToFilter));
        boolean isGenre = (genreToFilter==null || (this.genre).equals(genreToFilter));
        boolean isSmallerThanDuration = ((this.duration) <= durationToFilter);
        return isArtist && isGenre && isSmallerThanDuration;
    }

    /**
     * calculate string version of instance of song, in this format - "NAME, ARTIST, GENRE, MM:SS"
     * @return the calculated String
     */
    @Override
    public String toString() {
        int zeroes_to_add_to_seconds = this.numOfZeroesToAdd(this.duration % 60, Song.NUM_DIGITS_IN_SECONDS);
        String seconds_to_show = addZeroes(this.duration % 60, zeroes_to_add_to_seconds);
        String minutes_to_show = Integer.toString(this.duration / 60);
        return this.name + ", " + this.artist + ", " + this.genre + ", " + minutes_to_show + ":" + seconds_to_show;
    }

    /**
     * calculate if the current object equal to 'other' (by their type, name of song and name of artist only)
     * @param other the other object to compare with
     * @return true if other is a Song, and they have the same name and the same artist
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Song)) {
            return false;
        }
        Song otherSong = (Song)other;
        boolean isNameEquals = this.name.compareTo(otherSong.name) == 0;
        boolean isArtistEquals = this.artist.compareTo(otherSong.artist) == 0;
        return isNameEquals && isArtistEquals;
    }

    /**
     * calculate hash code of current song (by its name and artist only)
     * @return the calculated hash code
     */
    @Override
    public int hashCode() {
        return (this.name+this.artist).hashCode();
    }

    /**
     * Enum for all the genres that can be included as type of song
     */
    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }
}
