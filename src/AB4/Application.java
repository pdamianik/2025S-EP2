package AB4;

import AB4.Interfaces.AbstractTreeNode;
import AB4.Interfaces.Dinosaur;

public class Application {
    public static void main(String[] args) {

        // build the corporation with an empty dino population
        AbstractTreeNode animals = new EmptyTreeNode();
        DinoCorp corp = new DinoCorp(animals);

        // setup of two factories, one for Tyrannosaurus rex, one for Edmontosaurus
        corp.registerFactory(new TRexFactory(), "TREX");
        corp.registerFactory(new EdSFactory(), "EDMONTOSAURUS");

        // create the production "script"
        String[] executiveOrders = {
                "#TREX",
                "Mom!200",
                "Tick!456",
                "Trick!123",
                "Track!7",
                "#EDMONTOSAURUS",
                "Donald!1",
                "Daisy!8"
        };
        corp.setOrders(executiveOrders);

        // start production by executing the production script
        int oid = 0;
        while (corp.processNextOrder()) {
            System.out.println("order " + executiveOrders[oid++] + " executed.");
        }
        System.out.println();

        // feeding time
        System.out.println("All new and hungry dinos...");
        System.out.println("Angry dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.ANGRY));
        System.out.println("Sad dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.SAD));
        System.out.println("Happy dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.HAPPY));

        System.out.println("Feed some meat...");
        corp.feed(Dinosaur.Food.MEAT);
        System.out.println("Angry dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.ANGRY));
        System.out.println("Sad dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.SAD));
        System.out.println("Happy dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.HAPPY));

        System.out.println("Feed some plants...");
        corp.feed(Dinosaur.Food.PLANTS);
        System.out.println("Angry dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.ANGRY));
        System.out.println("Sad dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.SAD));
        System.out.println("Happy dinos: " + corp.countAnimalsByMood(Dinosaur.Happiness.HAPPY));
        System.out.println();

        System.out.println("Today's cast:");
        // dump dinos to console utilizing the flatten() method
        Dinosaur[] flattened = animals.flatten();
        for (Dinosaur dino : flattened) {
            System.out.println(dino);
        }


        // some additional test code for the tree implementation

        AbstractTreeNode root = new EmptyTreeNode();    // create an empty tree to start with

        root = root.store(new TyrannosaurusRex(200, "Mom"));     // add some dinos
        root = root.store(new TyrannosaurusRex(123, "Jim"));     // add some dinos
        root = root.store(new TyrannosaurusRex(456, "Jack"));   // add some dinos
        root = root.store(new Edmontosaurus(7, "Joe"));          // add some dinos
        root = root.store(new Edmontosaurus(127, "Elvis"));      // add some dinos

        // dump dinos to console utilizing the flatten() method
        Dinosaur[] population = root.flatten();
        for (Dinosaur dino : population) {
            System.out.println(dino);
        }

        System.out.println();
        // find dino via DNA
        Dinosaur target = null;
        target = root.find(200);    // should be found
        System.out.println(target != null ? target : "200 not found");
        target = root.find(123);    // should be found
        System.out.println(target != null ? target : "123 not found");
        target = root.find(456);    // should be found
        System.out.println(target != null ? target : "456 not found");
        target = root.find(7);      // should be found
        System.out.println(target != null ? target : "7 not found");
        target = root.find(127);    // should be found
        System.out.println(target != null ? target : "127 not found");

        target = root.find(100);    // should not be found
        System.out.println(target != null ? target : "100 not found");

        // find dino by name
        target = root.findByName("Mom");
        System.out.println(target != null ? target : "not found");

        target = root.findByName("Joe");
        System.out.println(target != null ? target : "not found");

        target = root.findByName("Bob");
        System.out.println(target != null ? target : "not found");


        // remove mom and joe
        root = root.remove(200);
        target = root.find(200);
        System.out.println(target != null ? target : "not found");

        // remove non existing dino
        root = root.remove(6);
        target = root.find(7);
        System.out.println(target != null ? target : "not found");

        root = root.remove(7);
        target = root.find(7);
        System.out.println(target != null ? target : "not found");

        // flatten tree
        flattened = root.flatten();
        for (Dinosaur dino : flattened) {
            System.out.println(dino);
        }

        testEdmontosaurusHappinessDecrease();
        testLeafRemoval();
        testFindAnimalByNameInNullTree();
    }

    public static void testEdmontosaurusHappinessDecrease() {
        System.out.println("* test Edmontosaurus happiness decrease");

        AB4.Interfaces.AbstractDinosaurFactory edSFactory = new EdSFactory();
        Dinosaur edmontosaurus = edSFactory.create(1, "Melsa");

        System.out.printf("|- initially %s ", Dinosaur.Happiness.HAPPY);
        if (edmontosaurus.getHappiness() == Dinosaur.Happiness.HAPPY) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  got happiness %s%n", edmontosaurus.getHappiness());
            return;
        }

        System.out.printf("|- decrease to %s ", Dinosaur.Happiness.SAD);
        edmontosaurus.feed(Dinosaur.Food.MEAT);
        if (edmontosaurus.getHappiness() == Dinosaur.Happiness.SAD) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  got happiness %s%n", edmontosaurus.getHappiness());
            return;
        }

        System.out.printf("|- decrease to %s ", Dinosaur.Happiness.ANGRY);
        edmontosaurus.feed(Dinosaur.Food.MEAT);
        if (edmontosaurus.getHappiness() == Dinosaur.Happiness.ANGRY) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  got happiness %s%n", edmontosaurus.getHappiness());
            return;
        }

        System.out.printf("|- decrease stays %s ", Dinosaur.Happiness.ANGRY);
        edmontosaurus.feed(Dinosaur.Food.MEAT);
        if (edmontosaurus.getHappiness() == Dinosaur.Happiness.ANGRY) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  got happiness %s%n", edmontosaurus.getHappiness());
            return;
        }

        System.out.printf("|- veggies reset to %s ", Dinosaur.Happiness.HAPPY);
        edmontosaurus.feed(Dinosaur.Food.PLANTS);
        if (edmontosaurus.getHappiness() == Dinosaur.Happiness.HAPPY) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  got happiness %s%n", edmontosaurus.getHappiness());
            return;
        }

        System.out.printf("\\%s%n", "-".repeat(10));
    }

    public static void testLeafRemoval() {
        System.out.println("* test leaf child removal");

        AB4.Interfaces.AbstractDinosaurFactory edSFactory = new EdSFactory();
        NonEmptyTreeNode root = new NonEmptyTreeNode(edSFactory.create(1, "Melsa"));
        root.store(edSFactory.create(0, "Tial"));
        root.store(edSFactory.create(2, "Firs"));

        System.out.println("|- tree has a correct initial structure");
        System.out.print("|--- root has a non empty left child ");
        if (root.getLeft() instanceof NonEmptyTreeNode) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|    left child is a %s%n", root.getLeft().getClass());
            return;
        }
        System.out.print("|--- root has a non empty right child ");
        if (root.getRight() instanceof NonEmptyTreeNode) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|    right child is a %s%n", root.getLeft().getClass());
            return;
        }

        System.out.print("|- remove left child ");
        root.remove(0);
        if (root.getLeft() instanceof EmptyTreeNode) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|    left child is a %s%n", root.getLeft().getClass());
            return;
        }

        System.out.print("|- remove right child ");
        root.remove(2);
        if (root.getLeft() instanceof EmptyTreeNode) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|    right child is a %s%n", root.getLeft().getClass());
            return;
        }

        System.out.printf("\\%s%n", "-".repeat(10));
    }

    public static void testFindAnimalByNameInNullTree() {
        System.out.println("* test Edmontosaurus happiness decrease");

        AB4.Interfaces.AbstractDinosaurFactory edSFactory = new EdSFactory();
        NonEmptyTreeNode root = new NonEmptyTreeNode(edSFactory.create(1, "Melsa"));
        root.store(edSFactory.create(0, "Tial"));
        root.store(edSFactory.create(2, "Firs"));

        System.out.print("|- find by name succeeds initially ");
        var dino = root.findByName("Tial");
        if (dino.getDNA() == 0 && dino.getName().equals("Tial")) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  Got %s%n", dino);
            return;
        }

        System.out.print("|- find by name succeeds after removal ");
        root.remove(1);
        dino = root.findByName("Tial");
        if (dino.getDNA() == 0 && dino.getName().equals("Tial")) {
            System.out.println("[PASS]");
        } else {
            System.out.println("[FAIL]");
            System.err.printf("|  Got %s%n", dino);
            return;
        }

        System.out.printf("\\%s%n", "-".repeat(10));
    }
}
