package AB4;

import AB4.Interfaces.AbstractDinosaurFactory;
import AB4.Interfaces.AbstractTreeNode;
import AB4.Interfaces.Dinosaur;

/**
 * The DinoCorp class represents a corporation operating Jurassic Dino Parks. It acts as a central point of coordination
 * for the lifecycle and operations surrounding dinosaurs in the system.
 *
 * <p>It provides functionality to create, register, and operate various breeding facilities (dino factories) by a
 * simplified scripting mechanic (order list), as well as means of animal interaction (for now, feeding only). In addition, it allows
 * querying the population's emotional state, to monitor the danger level as much as the impact of food on the population.
 */
public class DinoCorp {
    private static final int MAX_FACTORIES = 10;

    private AbstractDinosaurFactory[] factories = new AbstractDinosaurFactory[MAX_FACTORIES];
    private String[] factoryNames = new String[MAX_FACTORIES];

    private String[] orders = new String[0];
    private AbstractTreeNode population;

    // TODO: variable declarations

    /**
     * Constructor for the DinoCorp class that initializes the corporation's
     * population with a given AbstractTreeNode and sets default values for
     * other fields.
     *
     * @param initialPopulation the initial binary search tree representing
     *                          the population of dinosaurs owned by the corporation.
     */
    DinoCorp(AbstractTreeNode initialPopulation){
        // TODO: implementation
    }

    /**
     * Registers a new dinosaur factory for the corporation.
     *
     * @param dinoFactory a specialized implementation of the AbstractDinosaurFactory responsible for creating specific dinosaurs.
     * @param name the name associated with the factory being registered.
     * @return the index at which the factory was added to the corporations factories array.
     */
    public int registerFactory(AbstractDinosaurFactory dinoFactory, String name){
        // TODO: implementation
        return 0;
    }

    /**
     * Activates a specified dinosaur factory by its name, making it the currently active factory.
     *
     * @param factoryName the name of the factory to activate
     * @return true if the factory was successfully activated, false if no factory with the given name is found
     */
    private boolean activateFactory(String factoryName){
        // TODO: implementation
        return false;
    }

    /**
     * Sets the list of production orders for the corporation and resets the current order index to the first order.
     *
     * @param productionOrders an array of strings representing the new list of production orders.<br>
     *                        Precondition: productionOrders != null.
     */
    public void setOrders(String[] productionOrders){
        // TODO: implementation
    }

    /**
     * Processes the next production order of the corporation. The order can either activate a specific factory
     * or instruct the currently active factory to create a dinosaur. If no factory is currently active, the order is
     * dropped and ignored. Also, if a factory by a given name should be activated, but the name is unknown, the order
     * is dropped and ignored.
     *
     * <p>The method behaves as follows:<br>
     * - If the order starts with the '#' character, it attempts to activate the factory previously registered by the
     *   specified name. (e.g. "#EDMONTOSAURUS_FACTORY") <br>
     * - If the order starts with a letter, it specifies dinosaur creation. It requires both a valid name and a DNA code
     *   separated by exactly one '!' character. The DNA is parsed as an integer. If the active factory successfully creates a dinosaur,
     *   the new dinosaur is stored in the population. (e.g. "BillyTheKid!12345678")</p>
     *
     * @return {@code true} if the order was successfully processed (factory activated or dinosaur created);
     *         {@code false} otherwise (e.g., invalid order format, failure in processing, or  no active factory).
     */
    public boolean processNextOrder(){
        // TODO: implementation
        return false;
    }

    /**
     * Feeds all dinosaurs in the corporation's population with the one specified type of food.
     *
     * @param food the type of food to provide to the dinosaurs. This must be one of the {@code Dinosaur.Food} enum values (e.g., MEAT or PLANTS).
     */
    public void feed(Dinosaur.Food food){
        // TODO: implementation
    }

    /**
     * Counts the number of dinosaurs in the corporation's dino population that match a specified emotional state.
     *
     * @param mood the {@code Dinosaur.Happiness} enum value representing the emotional state to count in the population.
     * @return the number of dinosaurs in the population with the specified emotional state.
     */
    public int countAnimalsByMood(Dinosaur.Happiness mood){
        // TODO: implementation
        return 0;
    }

    /**
     * Retrieves the currently active dinosaur factory for the corporation.
     * The active factory is the one used to process creation orders for dinosaurs.
     *
     * @return the active {@code AbstractDinosaurFactory} instance, or {@code null} if no factory is currently active.
     */
    public AbstractDinosaurFactory getActiveFactory(){
        // TODO: implementation
        return null;
    }

}
