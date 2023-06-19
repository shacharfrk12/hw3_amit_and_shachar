import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

public class Playlist implements Cloneable, Iterable<Song>, FilteredSongIterable, OrderedSongIterable{
    private ArrayList<Song> songList;
    //filters
    private String artistToFilter;
    private Song.Genre genreToFilter;
    private int durationToFilter;
    private ScanningOrder order;

    public Playlist(){
        songList = new ArrayList<Song>();
        artistToFilter = null;
        genreToFilter = null;
        durationToFilter = -1;
        order = ScanningOrder.ADDING;
    }

    public Playlist(int startingSize){
        songList = new ArrayList<Song>(startingSize);
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
            playlistString += "(" + songsIt.next() + ")";
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

    public class PlaylistIterator implements Iterator<Song>{
        int index=0;
        ArrayList<Song> list;
        String artist;
        Song.Genre genre;
        int duration;

        public PlaylistIterator(Playlist playlist){
            duration = playlist.durationToFilter;
            artist = playlist.artistToFilter;
            genre = playlist.genreToFilter;
            Playlist ordered = playlist.clone();
            switch (order){
                case ADDING:
                    break;
                case NAME:
                    ordered.songList.sort(Comparator.comparing(s -> s.getName()));
                    break;
                case DURATION:
                    ordered.songList.sort(Comparator.comparing(s -> s.getDurationInSeconds()));
                    break;
            }
            list = ordered.songList;
        }
        @Override
        public boolean hasNext(){
            while(index+1<list.size()){
                Song next = list.get(index+1);
                if(!(next.getArtist().equals(artist) || next.getGenre().equals(genre) ||
                        next.getDurationInSeconds()==duration)){
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
                if(!(next.getArtist().equals(artist) || next.getGenre().equals(genre) ||
                        next.getDurationInSeconds()==duration)){
                    return next;
                }
            }
            return null;
        }
    }
}
