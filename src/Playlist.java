import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SimpleTimeZone;

public class Playlist implements Cloneable, Iterable<Song>, FilteredSongIterable, OrderedSongIterable{
    private LinkedList<Song> songList;
    //filters
    private String artistToFilter;
    private Song.Genre genreToFilter;
    private int durationToFilter;
    private ScanningOrder order;

    public Playlist(){
        songList = new LinkedList<>();
        artistToFilter = null;
        genreToFilter = null;
        durationToFilter = -1;
        order = ScanningOrder.ADDING;
    }

    //attributes

    public void addSong(Song song) throws SongAlreadyExistsException{
        if (this.songList.contains(song)){
            throw new SongAlreadyExistsException("The song you are trying to add already exists in Playlist");
        }
        this.songList.add(song);
    }

    public boolean removeSong(Song song){
        if(song==null)
            return false;
        if(this.songList.contains(song)){
            this.songList.remove(song);
            return true;
        }
        return false;
    }
    public void filterArtist(String Artist){
        this.artistToFilter = Artist;
    }
    public void filterGenre(Song.Genre genre){
        this.genreToFilter = genre;
    }
    public void filterDuration(int seconds){
        //maybe we need to think about getting rid of duration class???
        this.durationToFilter = seconds;
    }

    @Override
    public void setScanningOrder(ScanningOrder order) {
        this.order = order;
    }

    @Override
    public String toString(){
        String playlistString = "[";
        Iterator songsIt = this.songList.iterator();
        while(songsIt.hasNext()){
            playlistString += songsIt.next();
            if(songsIt.hasNext())
                playlistString += ", ";
        }
        playlistString += "]";
        return playlistString;
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Playlist))
            return false;
        return (this.songList.containsAll(((Playlist)other).songList)) &&
                (((Playlist)other).songList.containsAll(this.songList));

    }
    @Override
    public int hashCode(){
        int sumHashCodes = 0;
        for(Song song: this.songList)
            sumHashCodes += song.hashCode();
        return sumHashCodes;
    }
    @Override
    public Playlist clone(){
        Playlist copiedPlaylist = new Playlist();
        for(Song song: this.songList){
            copiedPlaylist.addSong(song.clone());
        }
        return copiedPlaylist;
    }
    @Override
    public Iterator<Song> iterator(){
        Playlist filtered = this.clone();
        if(artistToFilter != null)
            filtered.songList.removeIf(s -> s.getArtist().equals(artistToFilter));
        if(genreToFilter != null)
            filtered.songList.removeIf(s -> s.getGenre() == genreToFilter);
        if(durationToFilter != -1)
            filtered.songList.removeIf(s -> s.getDurationInSeconds() > durationToFilter);
        switch (order){
            case ADDING:
                return filtered.songList.listIterator();
            case NAME:
                filtered.songList.sort(Comparator.comparing(s -> s.getName()));
                break;
            case DURATION:
                filtered.songList.sort(Comparator.comparing(s -> s.getDurationInSeconds()));
                break;
        }
        return filtered.songList.listIterator();
    }
}
