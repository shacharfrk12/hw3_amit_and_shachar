/**
 * Interface for iterable objects that can be filtered by artist, genre and duration
 */
public interface FilteredSongIterable extends Iterable<Song>{
    /**
     * Update the object such that in the next iteration set it will iterate only over songs with the same artist
     * @param artist the artist that all the iterations should have
     */
    public void filterArtist(String artist);
    /**
     * Update the object such that in the next iteration set it will iterate only over songs from the same genre
     * @param genre the genre that all the iterations should have
     */
    public void filterGenre(Song.Genre genre);
    /**
     * Update the object such that in the next iteration set it will iterate only over songs with at least
     * the given duration
     * @param duration the minimum duration that all the iterations should have
     */
    public void filterDuration(int duration);

}
