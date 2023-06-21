import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * This class represent a playlist of songs
 */

public class Playlist implements Cloneable, Iterable<Song>, FilteredSongIterable, OrderedSongIterable{
    private ArrayList<Song> songList;
    //filters
    private String artistToFilter;
    private Song.Genre genreToFilter;
    private int durationToFilter;
    private ScanningOrder order;

    /**
     * constructor of Empty Playlist
     */

    public Playlist(){
        songList = new ArrayList<>();
        artistToFilter = null;
        genreToFilter = null;
        durationToFilter = -1;
        order = ScanningOrder.ADDING;
    }

    /**
     * constructor of a Playlist that we know is going to have a certain number of songs
     * @param startingSize number of song we know the playlist is going to have
     */
    public Playlist(int startingSize){
        songList = new ArrayList<>(startingSize);
        artistToFilter = null;
        genreToFilter = null;
        durationToFilter = -1;
        order = ScanningOrder.ADDING;
    }

    /**
     * Adds song to playlist
     * Throws exception in case the song or its similar is already in playlist
     * @param song a song to add
     * @throws SongAlreadyExistsException if the song already exist throws runtime error of this type
     */
    public void addSong(Song song) throws SongAlreadyExistsException{
        if (this.songList.contains(song)){
            throw new SongAlreadyExistsException("The song you are trying to add already exists in Playlist");
        }
        this.songList.add(song);
    }

    /**
     * Removes a given song from playlist
     * @param song A song to remove
     * @return true if succeeded to remove song, false if failed
     */
    public boolean removeSong(Song song){
        if(song==null)
            return false;
        if(this.songList.contains(song)){
            this.songList.remove(song);
            return true;
        }
        return false;
    }

    /**
     * updates playlist attribute so that when the user goes over it, it will only go over the songs of the given artist
     * if given null it will not filter according to artist
     * @param artist the artist that all the iterations should have
     */
    public void filterArtist(String artist){
        this.artistToFilter = artist;
    }

    /**
     * updates playlist attribute so that when the user goes over it, it will only go over the songs of the given genre
     * if given null it will not filter according to genre
     * @param genre the genre that all the iterations should have
     */
    public void filterGenre(Song.Genre genre){
        this.genreToFilter = genre;
    }

    /**
     * updates playlist attribute so that when the user goes over it,
     * it will only go over the songs shorter than or equal to given duration
     * @param duration the minimum duration that all the iterations should have
     */
    public void filterDuration(int duration){
        //maybe we need to think about getting rid of duration class???
        this.durationToFilter = duration;
    }

    /**
     * updates playlist so that when the user goes over it, it will be in the given order
     * @param order the order to iterate by
     */
    @Override
    public void setScanningOrder(ScanningOrder order) {
        this.order = order;
    }

    /**
     * Concatenates the playlist as list of songs in format [(songString),(songString),...,(songString)]
     * @return string representation of Playlist
     */
    @Override
    public String toString(){
        String playlistString = "[";
        Iterator songsIt = this.songList.iterator();
        while(songsIt.hasNext()){
            playlistString += "(" + songsIt.next() + ")";
            if(songsIt.hasNext())
                playlistString += ", ";
        }
        playlistString += "]";
        return playlistString;
    }

    /**
     * Checks if this playlist is equal to other in contents(both have the same songs, disregarding their order)
     * @param other other object to compare to
     * @return true if the objets are equal, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Playlist))
            return false;
        return (this.songList.containsAll(((Playlist)other).songList)) &&
                (((Playlist)other).songList.containsAll(this.songList));
    }

    /**
     * Calculates hashcode according to songs in playlist
     * @return the hasCode
     */
    @Override
    public int hashCode(){
        int sumHashCodes = 0;
        for(Song song: this.songList)
            sumHashCodes += song.hashCode();
        return sumHashCodes;
    }

    @Override
    public Playlist clone(){
        try{
            Playlist copiedPlaylist = new Playlist(this.songList.size());
            for(Song song: this.songList){
                Song songCopy = song.clone();
                if (songCopy==null) throw new NullPointerException();
                copiedPlaylist.addSong(song.clone());
            }
            return copiedPlaylist;
        } catch(NullPointerException e){
            return null;
        }


    }
    @Override
    public Iterator<Song> iterator(){
        return new PlaylistIterator(this);
    }

    /**
     * This class represents a playlist iterator
     */
    public class PlaylistIterator implements Iterator<Song>{
        int index=0;
        ArrayList<Song> list;
        String artist;
        Song.Genre genre;
        int duration;

        /**
         * Constructor of PlaylistIterator
         * @param playlist A playlist to iterate
         */
        public PlaylistIterator(Playlist playlist){
            Playlist ordered = playlist.clone();
            artist = playlist.artistToFilter;
            genre = playlist.genreToFilter;
            duration = playlist.durationToFilter;
            switch (order){
                case ADDING: // do nothing - already ordered by adding
                    break;
                case NAME: //order by alphabetical order of name
                    ordered.songList.sort(Comparator.comparing(Song::getName));
                    break;
                case DURATION: //order by duration, if equal duration by name, if equal name by author
                    ordered.songList.sort(Comparator.comparing(Song::getDuration).
                            thenComparing(Song::getName).thenComparing(Song::getArtist));
                    break;
            }
            list = ordered.songList;
        }
        @Override
        public boolean hasNext(){
            while(index<list.size()){
                Song next = list.get(index);
                if(next.songFitsFilter(artist, genre, duration)){
                    return true;
                }
                index++;
            }
            return false;
        }
        @Override
        public Song next(){
            while(index<list.size()){
                Song next = list.get(index++);
                if(next.songFitsFilter(artist, genre, duration)){
                    return next;
                }
            }
            return null;
        }
    }
}
