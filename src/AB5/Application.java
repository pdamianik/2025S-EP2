package AB5;

import AB5.Interfaces.BucketList;
import AB5.Interfaces.Dinosaur;

import java.lang.reflect.Field;

public class Application {
    public static void main(String[] args) {

        Dinosaur dino;

        // check dino DNA
        System.out.println(new DinosaurDNA(5));
        System.out.println();

        // add some dinosaurs to the hashmap
        System.out.println("Storing some dinosaurs in the hashmap...");
        DinosaurHashMap map = new DinosaurHashMap(4, 4);
        for (int i = 0; i < 10; i++) {
            dino = map.put(new TyrannosaurusRex(new DinosaurDNA(i), "TRex_" + i));
            System.out.println((dino != null) ? "Error: store should return null for new nodes" : "dino TRex_" + i + " was stored.");
            dino = map.put(new Edmontosaurus(new DinosaurDNA(i + 16), "Edmont_" + i));
            System.out.println((dino != null) ? "Error: store should return null for new nodes" : "dino Edmont_" + i + " was stored.");
        }
        System.out.println();

        // check the size of the hashmap
        System.out.println("Checking hashmap size...");
        System.out.println("Size: " + map.size());
        System.out.println();

        // find dinosaurs in the hashmap
        System.out.println("Finding some dinosaurs in the hashmap...");
        dino = map.get(new DinosaurDNA(1));
        System.out.println((dino != null) ? dino + " was found." : "Error: find returned null");
        dino = map.get(new DinosaurDNA(3));
        System.out.println((dino != null) ? dino + " was found." : "Error: find returned null");
        dino = map.get(new DinosaurDNA(33));
        System.out.println((dino != null) ? dino + "Error: an unknown dino was found." : "OK: find for unknown dino returned null");
        System.out.println();

        // now remove dinosaurs from the hashmap
        System.out.println("Removing some dinosaurs from the hashmap...");
        dino = map.remove(new DinosaurDNA(1));
        System.out.println((dino != null) ? "removed dino, length=" + map.size() : "Error: removed dino should not be null after removing a dinosaur.");
        dino = map.remove(new DinosaurDNA(3));
        System.out.println((dino != null) ? "removed dino, length=" + map.size() : "Error: removed dino should not be null after removing a dinosaur.");
        dino = map.remove(new DinosaurDNA(666));
        System.out.println((dino == null) ? "OK: unknown dino can not be removed, length=" + map.size() : "Error: dino should be unknown...");
        System.out.println();

        // test the hashmap iterator
        System.out.println("Test hashmap iterator...");
        System.out.println("Order of dinos may vary here, as this iterator's order is implementation specific.");
        DinosaurHashMapIterator mapIterator = map.iterator();
        while (mapIterator.hasNext()) {
            System.out.println(mapIterator.next());
        }
        System.out.println();

        // now clear the hashmap
        System.out.println("Clearing the hashmap...");
        map.clear();
        System.out.println("Size: " + map.size());
        System.out.println();

        // test find and remove on an empty map
        System.out.println("Test find and remove on empty map...");
        dino = map.get(new DinosaurDNA(33));
        System.out.println((dino != null) ? dino + "Error: a dino was found in an empty map!" : "OK: find in empty map returned null");
        dino = map.remove(new DinosaurDNA(1));
        System.out.println((dino == null) ? "OK: remove dino from empty map returned null, length=" + map.size() : "Error: remove from empty map returned a dino!");
        System.out.println();

        // test the hashmap iterator on an empty map
        System.out.println("Test hashmap iterator... (should not produce any output)");
        mapIterator = map.iterator();
        while (mapIterator.hasNext()) {
            System.out.println(mapIterator.next());
        }
        System.out.println();

        testHashmapZeroCapacity();
        testIteratorOverflow();
    }

    public static void testHashmapZeroCapacity() {
        System.out.println("* test hashmap with zero capacity");

        final int DEFAULT_NUM_OF_BUCKETS;
        try {
            Field capacityField = DinosaurHashMap.class.getDeclaredField("DEFAULT_NUM_OF_BUCKETS");
            if (!capacityField.trySetAccessible()) {
                System.err.println("[FAIL] The field DinosaurHashmap.DEFAULT_NUM_OF_BUCKETS field could not be set accessible");
                return;
            }
            DEFAULT_NUM_OF_BUCKETS = (int) capacityField.get(null);
        } catch (NoSuchFieldException e) {
            System.err.println("The field DinosaurHashmap.DEFAULT_NUM_OF_BUCKETS could not found (may need adjustment)");
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The field DinosaurHashmap.DEFAULT_NUM_OF_BUCKETS could not be accessed, even after setting the accessible flag");
            return;
        }

        final int DEFAULT_MAX_BUCKET_SIZE;
        try {
            Field maxCapacityField = DinosaurHashMap.class.getDeclaredField("DEFAULT_MAX_BUCKET_SIZE");
            if (!maxCapacityField.trySetAccessible()) {
                System.err.println("[FAIL] The field DinosaurHashmap.DEFAULT_MAX_BUCKET_SIZE field could not be set accessible");
                return;
            }
            DEFAULT_MAX_BUCKET_SIZE = (int) maxCapacityField.get(null);
        } catch (NoSuchFieldException e) {
            System.err.println("The field DinosaurHashmap.DEFAULT_MAX_BUCKET_SIZE could not found (may need adjustment)");
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The field DinosaurHashmap.DEFAULT_MAX_BUCKET_SIZE could not be accessed, even after setting the accessible flag");
            return;
        }

        System.out.print("|- create a zero sized hashmap ");
        DinosaurHashMap map = new DinosaurHashMap(0, DEFAULT_MAX_BUCKET_SIZE);
        try {
            Field bucketsField = DinosaurHashMap.class.getDeclaredField("buckets");
            if (!bucketsField.trySetAccessible()) {
                System.out.println("[FAIL]");
                System.err.println("The buckets field could not be set accessible");
                return;
            }
            var buckets = (BucketList[])bucketsField.get(map);

            if (buckets.length == DEFAULT_NUM_OF_BUCKETS) {
                System.out.println("[PASS]");
            } else {
                System.out.println("[FAIL]");
                System.err.printf("The capacity of the hashmap is %d not %d%n", buckets.length, DEFAULT_NUM_OF_BUCKETS);
                return;
            }
        } catch (NoSuchFieldException e) {
            System.out.println("[FAIL]");
            System.err.println("Please adjust the name of the buckets field");
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The buckets field could not be accessed, even after setting the accessible flag");
            return;
        }

        System.out.print("|- create a zero max sized hashmap ");
        map = new DinosaurHashMap(DEFAULT_NUM_OF_BUCKETS, 0);
        try {
            Field maxBucketSizeField = DinosaurHashMap.class.getDeclaredField("maxBucketSize");
            if (!maxBucketSizeField.trySetAccessible()) {
                System.out.println("[FAIL]");
                System.err.println("The maxBucketSize field could not be set accessible");
                return;
            }
            var maxBucketSize = (int)maxBucketSizeField.get(map);

            if (maxBucketSize == DEFAULT_MAX_BUCKET_SIZE) {
                System.out.println("[PASS]");
            } else {
                System.out.println("[FAIL]");
                System.err.printf("The maxBucketSize field of the hashmap is %d not %d%n", maxBucketSize, DEFAULT_MAX_BUCKET_SIZE);
                return;
            }
        } catch (NoSuchFieldException e) {
            System.out.println("[FAIL]");
            System.err.println("Please adjust the name of the maxBucketSize field");
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The maxBucketSize field could not be accessed, even after setting the accessible flag");
            return;
        }

        System.out.print("|- create a zero sized and max sized hashmap ");
        map = new DinosaurHashMap(0, 0);
        try {
            Field bucketsField = DinosaurHashMap.class.getDeclaredField("buckets");
            if (!bucketsField.trySetAccessible()) {
                System.out.println("[FAIL]");
                System.err.println("The buckets field could not be set accessible");
                return;
            }
            var buckets = (BucketList[])bucketsField.get(map);

            if (buckets.length != DEFAULT_NUM_OF_BUCKETS) {
                System.out.println("[FAIL]");
                System.err.printf("The capacity of the hashmap is %d not %d%n", buckets.length, DEFAULT_NUM_OF_BUCKETS);
                return;
            }
        } catch (NoSuchFieldException e) {
            System.out.println("[FAIL]");
            System.err.println("Please adjust the name of the buckets field");
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The buckets field could not be accessed, even after setting the accessible flag");
            return;
        }
        try {
            Field maxBucketSizeField = DinosaurHashMap.class.getDeclaredField("maxBucketSize");
            if (!maxBucketSizeField.trySetAccessible()) {
                System.out.println("[FAIL]");
                System.err.println("The maxBucketSize field could not be set accessible");
                return;
            }
            var maxBucketSize = (int)maxBucketSizeField.get(map);

            if (maxBucketSize == DEFAULT_MAX_BUCKET_SIZE) {
                System.out.println("[PASS]");
            } else {
                System.out.println("[FAIL]");
                System.err.printf("The maxBucketSize field of the hashmap is %d not %d%n", maxBucketSize, DEFAULT_MAX_BUCKET_SIZE);
                return;
            }
        } catch (NoSuchFieldException e) {
            System.out.println("[FAIL]");
            System.err.println("Please adjust the name of the maxBucketSize field");
            return;
        } catch (IllegalAccessException e) {
            System.out.println("[FAIL]");
            System.err.println("The maxBucketSize field could not be accessed, even after setting the accessible flag");
            return;
        }


        System.out.printf("\\%s%n", "-".repeat(10));
    }

    public static void testIteratorOverflow() {
        System.out.println("* test iterator overflow");

        System.out.print("|- next on an empty list should return null ");
        DinosaurBucketList list = new DinosaurBucketList();
        DinosaurListIterator listIterator = list.iterator();
        if (listIterator.next() == null && listIterator.next() == null) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.println("Got non null value after iterating over an empty list");
            return;
        }

        System.out.print("|- next on a list with 1 element should return null after one next call ");
        list.store(new Edmontosaurus(new DinosaurDNA(1), "Melsa"));
        listIterator = list.iterator();
        if (listIterator.next() != null && listIterator.next() == null && listIterator.next() == null) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.println("Got non null value after iterating over a list with 1 element");
            return;
        }

        System.out.print("|- next on an empty hashmap should return null ");
        DinosaurHashMap hashMap = new DinosaurHashMap();
        DinosaurHashMapIterator mapIterator = hashMap.iterator();
        if (mapIterator.next() == null && mapIterator.next() == null) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.println("Got non null value after iterating over an empty hashmap");
            return;
        }

        System.out.print("|- next on a hashmap with 1 element should return null after one next call ");
        hashMap.put(new Edmontosaurus(new DinosaurDNA(1), "Melsa"));
        mapIterator = hashMap.iterator();
        if (mapIterator.next() != null && mapIterator.next() == null && mapIterator.next() == null) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.println("Got non null value after iterating over a hashmap with 1 element");
            return;
        }

        System.out.printf("\\%s%n", "-".repeat(10));
    }
}
