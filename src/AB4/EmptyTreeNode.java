package AB4;

import AB4.Interfaces.AbstractTreeNode;
import AB4.Interfaces.Dinosaur;

/**
 * Represents an empty node in a tree structure.
 * Used to signify the end of a branch or an unpopulated node in the tree.
 *
 * <p>This implementation adheres to the {@code AbstractTreeNode} interface,
 * providing a default behavior for all operations performed on an empty tree node.
 * As it represents an unpopulated node, no stored data or child nodes exist.</p>
 */
public class EmptyTreeNode implements AbstractTreeNode {

    public static final EmptyTreeNode NIL = new EmptyTreeNode();    // reference to this object denotes the end of a tree's branch (instead of null)

    /**
     * Stores a new Dinosaur into the tree structure by creating a new non-empty tree node.
     * This method is used in the context of an empty tree node to transition it into a populated state.
     *
     * @param animal the Dinosaur object to be stored within the tree.
     * @return the newly created {@code NonEmptyTreeNode} containing the given Dinosaur object.
     */
    @Override
    public AbstractTreeNode store(Dinosaur animal) {
        // TODO: implementation
        return null;
    }

    /**
     * Removes a dinosaur identified by the given DNA from the tree.
     * This implementation represents an operation on an empty tree node,
     * where removal always results in the same empty tree node being returned.
     *
     * @param dna the unique DNA of the dinosaur to be removed.
     * @return the reference to the empty tree node {@code EmptyTreeNode.NIL}.
     */
    @Override
    public AbstractTreeNode remove(int dna) {
        // TODO: implementation
        return null;
    }

    /**
     * Searches for and retrieves a Dinosaur from the tree structure based on its unique DNA.
     * This implementation represents an operation on an empty tree node, where no Dinosaur can exist.
     *
     * @param dna the unique DNA of the dinosaur to search for in the tree.
     * @return always returns {@code null} as an empty tree node does not contain any Dinosaur.
     */
    @Override
    public Dinosaur find(int dna) {
        // TODO: implementation
        return null;
    }

    /**
     * Searches for a Dinosaur in the tree by its name. This implementation
     * performs the search in the context of an empty tree node, and therefore
     * no Dinosaur can be found.
     *
     * @param name the name of the Dinosaur to search for.
     * @return always returns {@code null}, as an empty tree node does not contain any Dinosaur.
     */
    @Override
    public Dinosaur findByName(String name) {
        // TODO: implementation
        return null;
    }

    /**
     * Returns a flattened array representation of all Dinosaurs stored in the tree structure.
     * In the context of an empty tree node, no Dinosaur exists, and the returned
     * array will always be empty.
     *
     * @return an empty array of Dinosaur objects, as an empty tree node contains no data.
     */
    @Override
    public Dinosaur[] flatten() {
        // TODO: implementation
        return null;
    }
}
