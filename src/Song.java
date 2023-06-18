import java.io.ObjectInputStream;

public class Song implements Cloneable{
    private final String name;
    private final String artist;
    private Genre genre;
    private Duration duration;

    public Song(String name, String artist, Genre genre, int secondsDuration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = new Duration(secondsDuration);
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
    //not sure about this way of implementing
    public int getDurationInSeconds(){
        return duration.getDurationInSeconds();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDuration(int secondsDuration) {
        this.duration = new Duration(secondsDuration);
    }

    @Override
    public Song clone() {
        try {
            return new Song(this.name, this.artist, this.genre, this.duration.getDurationInSeconds());
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public String toString() {
        // TODO: make sure that works
        return "(" + this.name + "," + this.artist + "," + this.genre + "," + this.duration.toString() + ")";
    }

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

    @Override
    public int hashCode() {
        return (this.name+this.artist).hashCode();
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }
}
