package AB4;

import AB4.Interfaces.AbstractTreeNode;
import AB4.Interfaces.Dinosaur;

/**
 * Represents a non-empty node in a binary tree structure.
 *
 * <p>This class implements the {@code AbstractTreeNode} interface and encapsulates the functionality
 * of storing and retrieving a {@code Dinosaur} within a binary tree.</p>
 * <p>Each {@code NonEmptyTreeNode} instance represents a node for a specific DNA (key) and may contain a {@code Dinosaur}
 * as its value. The node also contains references to its left and right child nodes, where the left child's key is
 * smaller than the node's key and the right child's key is larger than the node's key. Child nodes are always non-null.</p>
 */
public class NonEmptyTreeNode implements AbstractTreeNode {
    private Dinosaur animal;
    private int key;
    private AbstractTreeNode left;
    private AbstractTreeNode right;

    /**
     * Constructs a {@code NonEmptyTreeNode} that containing a given Dinosaur object.
     * <p>The node initializes its key and value attributes using the provided Dinosaur and initializes its left and right
     * children of type {@code AbstractTreeNode} to indicate that it initially has no child nodes.</p>
     * @param animal the Dinosaur object to be stored in the tree node.
     *               The DNA of this Dinosaur is used as the key, and the Dinosaur itself is the value of the node.
     */
    NonEmptyTreeNode(Dinosaur animal) {
        this.animal = animal;
        this.key = animal.getDNA();
        this.left = EmptyTreeNode.NIL;
        this.right = EmptyTreeNode.NIL;
    }

    /**
     * Stores a given Dinosaur within the tree.
     *
     * <p>The method uses the dinosaur's DNA as the key to determine the correct position
     * within the tree. If the DNA matches the current node's key, the existing value is updated.
     * If the DNA is less than the current node's key, the Dinosaur is stored in the left subtree.
     * Otherwise, the Dinosaur is stored in the right subtree.</p>
     *
     * @param animal the Dinosaur object to be stored in the tree. The dinosaur's DNA is used to determine the storage location.
     * @return the current tree node (the node where the parent node should point to) after the dinosaur has been stored.
     */
    @Override
    public AbstractTreeNode store(Dinosaur animal) {
        int search = animal.getDNA();
        if (search == this.key) {
            this.animal = animal;
        } else if (search < this.key) {
            this.left = this.left.store(animal);
        } else {
            this.right = this.right.store(animal);
        }
        return this;
    }

    /**
     * Removes the dinosaur identified by the given DNA from the tree.
     *
     * <p>The method clears the value (payload) of the node (set value to null) if the DNA matches the key of the current node.
     * If the current node does not have any child nodes after removal, it transitions into an empty node (NIL).</p>
     *
     * <p>If the DNA does not match the key, the operation is delegated to the left or right child node
     * based on whether the DNA is less than or greater than the key of the current node.</p>
     *
     * @param dna the unique integer encoded DNA of the dinosaur to be removed.
     * @return the current tree node after the removal operation or {@code EmptyTreeNode.NIL} after transitioning to an empty node.
     */
    @Override
    public AbstractTreeNode remove(int dna) {
        if (dna == this.key) {
            this.animal = null;
            if (this.left == EmptyTreeNode.NIL && this.right == EmptyTreeNode.NIL)
                return EmptyTreeNode.NIL;
        } else if (dna < this.key) {
            this.left = this.left.remove(dna);
        } else {
            this.right = this.right.remove(dna);
        }
        return this;
    }

    /**
     * Finds and returns a Dinosaur object in the tree based on its unique DNA identifier.
     *
     * <p>The method recursively traverses the tree, comparing the provided DNA with the node's key.
     * If the DNA matches the key, the corresponding Dinosaur is returned. If the DNA is less than
     * the key, the method continues its search in the left subtree. Otherwise, the search proceeds
     * in the right subtree.</p>
     *
     * @param dna the unique DNA, encoded as an {@code int}, of the dinosaur to be found.
     * @return the Dinosaur object with the specified DNA, or {@code null} if no such dinosaur is found in the tree.
     */
    @Override
    public Dinosaur find(int dna) {
        if (dna == this.key) return this.animal;
        else if (dna < this.key) return this.left.find(dna);
        else return this.right.find(dna);
    }

    /**
     * Finds a dinosaur within the tree by its name.
     *
     * <p>The method searches the current tree node and recursively checks
     * its left and right children to locate a dinosaur with the specified name.</p>
     *
     * @param name the name of the dinosaur to locate
     * @return the dinosaur object with the specified name, or {@code null} if no such dinosaur is found
     */
    @Override
    public Dinosaur findByName(String name) {
        if (this.animal != null && this.animal.getName().equals(name)) return this.animal;
        Dinosaur animal = this.left.findByName(name);
        return animal == null ? this.right.findByName(name) : animal;
    }

    /**
     * Flattens the tree into an array of Dinosaur objects using an in-order traversal.
     *
     * <p>This method iterates through the tree's structure by performing:<br>
     * 1. A recursive flattening of the left subtree.<br>
     * 2. Adding the current node's value if it exists, so it is not equal to {@code null}.<br>
     * 3. A recursive flattening of the right subtree.</p>
     *
     * @return an array of {@code Dinosaur} objects representing all dinosaurs in the tree,
     * sorted by their DNA in ascending order.
     */
    @Override
    public Dinosaur[] flatten() {
        Dinosaur[] left = this.left.flatten();
        Dinosaur[] right = this.right.flatten();

        int rightStart = left.length;
        if (this.animal != null) rightStart++;
        Dinosaur[] flattened = new Dinosaur[rightStart + right.length];

        System.arraycopy(left, 0, flattened, 0, left.length);
        if (this.animal != null) flattened[left.length] = this.animal;
        System.arraycopy(right, 0, flattened, rightStart, right.length);

        return flattened;
    }

    // GETTERS AND SETTERS

    /**
     * Retrieves the key of the current tree node.
     *
     * @return the key of the current tree node, represented as an {@code int}.
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Retrieves the left child node of the current tree node.
     *
     * @return the left child node represented as an {@code AbstractTreeNode}.
     */
    public AbstractTreeNode getLeft() {
        return this.left;
    }

    /**
     * Retrieves the right child node of the current tree node.
     *
     * @return the right child node represented as an {@code AbstractTreeNode}.
     */
    public AbstractTreeNode getRight() {
        return this.right;
    }

    /**
     * Sets the left child node of the current tree node.
     *
     * <p>Precondition: (left != null) </p>
     * @param left the left child node to be assigned, represented as an {@code AbstractTreeNode}.
     */
    public void setLeft(AbstractTreeNode left) {
        this.left = left;
    }

    /**
     * Sets the right child node of the current tree node.
     *
     * <p>Precondition: (right != null) </p>
     * @param right the right child node to be assigned, represented as an {@code AbstractTreeNode}.
     */
    public void setRight(AbstractTreeNode right) {
        this.right = right;
    }

}
