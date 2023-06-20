/**
 * Interface for iterable object that can be ordered by ScanningOrder types
 */
public interface OrderedSongIterable extends Iterable<Song>{
    /**
     * Update the object such that in the next iteration set it will iterate by the given order
     * @param order the order to iterate by
     */
    public void setScanningOrder(ScanningOrder order);
}
