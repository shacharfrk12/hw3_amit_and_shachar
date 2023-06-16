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

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDuration(int secondsDuration) {
        this.duration = new Duration(secondsDuration);
    }

    @Override
    public Object clone() {
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
        boolean isGenreAndDurationEquals = (this.genre == otherSong.genre && this.duration.equals(otherSong.duration));
        return isNameEquals && isArtistEquals && isGenreAndDurationEquals;
    }

    @Override
    public int hashCode() {
        int hasStrings = this.name.hashCode() + this.artist.hashCode();
        return hasStrings + this.genre.ordinal() * 10000 + this.duration.hashCode() * 500;
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO;
    }
}
