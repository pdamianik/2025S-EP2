package AB7;

import AB7.Interfaces.Dealer;
import AB7.Interfaces.Hand;

/**
 * Represents a Blackjack player who participates in a game managed by a dealer.
 *
 * <p>The BJPlayer interacts with a {@code Dealer} to receive cards and manages its own hand using the {@code Hand}
 * interface. The class supports common Blackjack actions, such as starting a game, drawing additional cards (hit),
 * and calculating the current hand's score, while ensuring compliance with game rules.</p>
 */
public class BJPlayer {
     private final Hand hand;        // the hand of cards the player uses in all his games of Blackjack
     private final Dealer dealer;    // the dealer responsible for managing the deck and dealing cards

    /**
     * Constructs a new BJPlayer object that participates in a game of Blackjack.
     *
     * @param hand   an instance of the {@code Hand} interfaces that stores the cards, a player gets within a game.
     *               Must not be {@code null}.
     * @param dealer the dealer responsible for managing the deck and dealing cards. Must not be {@code null}.
     */
    public BJPlayer(Hand hand, Dealer dealer) {
        this.hand = hand;
        this.dealer = dealer;

    }

    /**
     * Initializes the player's hand to start a new game of Blackjack by clearing any existing cards
     * via {@code hand.clear()}, dealing two new cards into the hand from the dealer, and returning a copy
     * of the updated hand.
     *
     * @return a deep copy of the player's hand after receiving the initial two cards of a start hand
     * @throws IllegalOperationException if an error occurs when attempting to add a card to the hand
     * @throws BadDeckException          if the deck cannot provide any card
     */
    public Hand startGame() throws IllegalOperationException, BadDeckException {
        this.hand.clear();
        for (int i = 0; i < 2; i++) {
            this.hand.addCard(this.dealer.dealCard());
        }
        return this.hand.clone();
    }

    /**
     * Adds a new card to the player's hand received from the dealer and returns a copy of the updated hand.
     *
     * <p>If an IllegalOperationException occurs while adding the card to the hand (e.g. by adding a card to an already
     * busted hand), a message is logged to the console, and execution continues (the player's hand remains unchanged).</p>
     *
     * @return a deep copy of the player's hand after attempting to add a new card
     * @throws BadDeckException if the dealer's deck cannot provide a card for any reason
     */
    public Hand hit() throws BadDeckException {
        try {
            this.hand.addCard(this.dealer.dealCard());
        } catch (IllegalOperationException e) {
            System.err.println(e.getMessage());
        }
        return this.hand.clone();
    }

    /**
     * Calculates and retrieves the total score of the player's hand in the game of Blackjack.
     *
     * @return the total score of the player's current hand.
     */
    public int getScore() {
        return this.hand.getScore();
    }
}
