package AB5;

import AB5.Interfaces.BucketList;
import AB5.Interfaces.Dinosaur;
import AB5.Interfaces.HashMap;

/**
 * DinosaurHashMap is an implementation of the {@code HashMap} interface for storing, retrieving,
 * and managing {@code Dinosaur} objects.
 *
 * <p>This implementation uses an array of buckets to store dinosaurs. The specific bucket a dinosaur is stored in
 * (the bucket's index within the array) is determined by the dinosaur's DNA and the number of buckets.
 * It can be calculated as follows:<br>
 * {@code bucketIndex = ( absolute value of hash of dinosaur's DNA )  % ( number of buckets )}.</p>
 *
 * <p>To ensure performance efficiency, the {@code DinosaurHashMap} dynamically increases its number of buckets
 * if any bucket exceeds a specific size. The size of a bucket is defined as the number of elements within it.</p>
 */
public class DinosaurHashMap implements HashMap {
    private static final int DEFAULT_NUM_OF_BUCKETS = 4;
    private static final int DEFAULT_MAX_BUCKET_SIZE = 4;

    private BucketList[] buckets;
    private int maxBucketSize;   // maximum number of dinos stored within a bucket
    private int size;

    /**
     * Constructs a new instance of DinosaurHashMap with default capacity and default maximum bucket depth.
     *
     * <p>The default capacity (number of buckets) and maximum bucket size values are defined by
     * `DEFAULT_NUM_OF_BUCKETS` and `DEFAULT_MAX_BUCKET_SIZE` respectively.
     * These values ensure that even if no specific capacity or bucket size is specified, the hashmap
     * will initialize with suitable defaults for efficient operation.</p>
     */
    public DinosaurHashMap() {
        this.buckets = new BucketList[DEFAULT_NUM_OF_BUCKETS];
        this.maxBucketSize = DEFAULT_MAX_BUCKET_SIZE;
    }

    /**
     * Constructs a new instance of the {@code DinosaurHashMap} with the specified initial bucket capacity
     * and the maximum allowed bucket size.
     *
     * @param capacity      the initial number of buckets to allocate for the hashmap. If less than or equal to 0, the
     *                      default capacity {@code DEFAULT_NUM_OF_BUCKETS} is used.
     * @param maxBucketSize the maximum number of elements allowed in any single bucket. If less than or equal to 0, the
     *                      default value is used {@code DEFAULT_MAX_BUCKET_SIZE}.
     */
    public DinosaurHashMap(int capacity, int maxBucketSize) {
        if (capacity <= 0) capacity = DEFAULT_NUM_OF_BUCKETS;
        if (maxBucketSize <= 0) maxBucketSize = DEFAULT_MAX_BUCKET_SIZE;
        this.buckets = new BucketList[capacity];
        this.maxBucketSize = maxBucketSize;

    }

    /**
     * Creates a new array of buckets with the specified capacity and rehashes all elements currently stored in the
     * hashmap into this new array. This method is used for resizing the hashmap's bucket array if any bucket exceeds
     * the maximum allowed size.
     *
     * <p>This method does not check if new buckets do meet the size requirement after rehashing.</p>
     *
     * @param newCapacity the new capacity (number of buckets) of the array of buckets. If the value is
     *                    less than or equal to 0, the default number of buckets is used.
     * @return an array of {@code BucketList} objects (each one of type {@code DinosaurBucketList} of new capacity
     *         containing all rehashed elements.
     */
    private BucketList[] reorganizeBuckets(int newCapacity) {
        if (newCapacity <= 0) newCapacity = DEFAULT_NUM_OF_BUCKETS;

        DinosaurHashMapIterator dinosaurs = this.iterator();
        BucketList[] newBuckets = new BucketList[newCapacity];

        while (dinosaurs.hasNext()) {
            var dinosaur = dinosaurs.next();
            int index = dinosaur.getDNA().hashCode() % newCapacity;
            if (newBuckets[index] == null) {
                newBuckets[index] = new DinosaurBucketList();
            }
            newBuckets[index].store(dinosaur);
        }

        return newBuckets;
    }

    private int calculateIndex(DinosaurDNA dna) {
        return dna.hashCode() % this.buckets.length;
    }

    /**
     * Puts a dinosaur into the hashmap.
     * <p>If a dinosaur with the same DNA already exists, it is replaced/updated by the new one. The replaced dinosaur is
     * returned.</p>
     * <p>If a bucket exceeds the defined maximum size after the addition of a new element,
     * the hashmap's array of buckets is replaced by a new one of double the size and all elements are rehashed into the
     * new array. Remark: In rare cases, the rehashed array may still contain a bucket that exceeds the allowed size. Hence, a
     * consecutive put() operation might trigger another reorganization. This special case may be ignored for reasons
     * of performance and simplicity.</p>
     *
     * @param dinosaur the {@code Dinosaur} object to insert into the hashmap. It is identified by its unique DNA.
     *                 Must not be {@code null}.
     * @return the previously stored {@code Dinosaur} with the same DNA if one exists, or {@code null} if the dinosaur
     *         is newly added to the hashmap.
     */
    @Override
    public Dinosaur put(Dinosaur dinosaur) {
        int index = this.calculateIndex(dinosaur.getDNA());
        if (this.buckets[index] == null) {
            this.buckets[index] = new DinosaurBucketList();
        }
        BucketList bucket = this.buckets[index];

        Dinosaur existing = bucket.store(dinosaur);
        if (existing == null) this.size++;

        if (bucket.size() > maxBucketSize) {
            this.buckets = this.reorganizeBuckets(this.buckets.length * 2);
        }

        return existing;
    }

    /**
     * Retrieves the {@code Dinosaur} object corresponding to the provided {@code DinosaurDNA}.
     * The method calculates the appropriate bucket based on the DNA's hash code and searches
     * for the dinosaur within that bucket. If no dinosaur is found with the given DNA, it returns {@code null}.
     *
     * @param dna the {@code DinosaurDNA} used to locate the associated {@code Dinosaur}. Must not be {@code null}.
     * @return the {@code Dinosaur} object associated with the specified DNA, or {@code null} if no match is found.
     */
    @Override
    public Dinosaur get(DinosaurDNA dna) {
        BucketList bucket = this.buckets[this.calculateIndex(dna)];
        if (bucket == null) return null;
        return bucket.find(dna);
    }

    /**
     * Removes the {@code Dinosaur} object associated with the specified {@code DinosaurDNA} from the hashmap.
     * Based on the DNA's hash code, the method determines the appropriate bucket and removes the corresponding
     * dinosaur if it exists. If no dinosaur is associated with the given DNA, the method returns {@code null}.
     *
     * @param dna the {@code DinosaurDNA} identifying the dinosaur to be removed from the hashmap.
     *            Must not be {@code null}.
     * @return the removed {@code Dinosaur} object if it was found and removed, or {@code null} if no dinosaur
     *         with the specified DNA exists in the hashmap.
     */
    @Override
    public Dinosaur remove(DinosaurDNA dna) {
        BucketList bucket = this.buckets[this.calculateIndex(dna)];
        if (bucket == null) return null;
        var removed =  bucket.remove(dna);
        if (removed != null) this.size--;
        return removed;
    }

    /**
     * Removes all elements from the hashmap by clearing all buckets.
     *
     * <p>This operation does not modify the capacity or structure of the hashmap, only the contents.</p>
     */
    @Override
    public void clear() {
        for (BucketList bucket : this.buckets) {
            if (bucket != null) {
                bucket.clear();
            }
        }
        this.size = 0;
    }

    /**
     * Computes the total number of elements stored in the hashmap.
     *
     * @return the total count of elements in the hashmap as an integer. If the hashmap
     * is empty, returns 0.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Provides a {@code DinosaurHashMapIterator} for this hashmap.
     * @return a {@code DinosaurHashMapIterator} that can be used to iterate over all elements in the hashmap.
     */
    public DinosaurHashMapIterator iterator() {
        return new DinosaurHashMapIterator(this.buckets);
    }
}
