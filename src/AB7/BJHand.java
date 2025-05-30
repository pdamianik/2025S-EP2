package AB7;

import AB7.Interfaces.Card;
import AB7.Interfaces.Hand;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a hand of cards in a game of Blackjack.
 * The hand consists of a collection of cards and provides functionality to add
 * cards and calculate the hand's current score based on Blackjack rules.
 */
public class BJHand implements Hand {
    private ArrayList<Card> cards;

    /**
     * Constructs a new BJHand object representing a hand of cards in Blackjack.
     *
     * <p>Initializes an empty collection of cards, which will be used to store the cards
     * in the player's hand. The hand starts empty but can be populated by adding cards
     * during the game.</p>
     */
    public BJHand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Checks whether the current hand is a Blackjack.
     * A Blackjack is defined as a hand containing exactly two cards
     * whose combined score equals 21.
     *
     * @return true if the hand contains exactly two cards and their total score is 21, false otherwise
     */
    private boolean isBlackJack() {
        return this.getScore() == 100;
    }

    /**
     * Adds a card to the current hand in a Blackjack game and returns the hand's new total score.
     *
     * <p>If the hand is in a "busted" state (total score exceeds 21) or is a Blackjack, it is considered locked.
     * An attempt to add a card will throw an IllegalOperationException as that specific hand can no longer be modified.</p>
     *
     * @param card the card to be added to the hand. Must not be {@code null}.
     * @return the updated total score of the hand after adding the card.
     * @throws IllegalOperationException if the hand is in a "busted" state or a Blackjack, and a card is added.
     */
    @Override
    public int addCard(Card card) throws IllegalOperationException {
        int score = this.getScore();
        if (score == 100) {
            throw new IllegalOperationException("Cannot add card to a black jack hand.");
        } else if (score == 0 && !this.cards.isEmpty()) {
            throw new IllegalOperationException("Cannot add card to a busted hand.");
        }
        this.cards.add(card);
        return this.getScore();
    }

    /**
     * Calculates and retrieves the total score of the current hand in a Blackjack game.
     *
     * <p>The method determines the total score based on the following rules:<br>
     * - If the hand is a Blackjack (exactly two cards totaling 21), it returns a score of 100.<br>
     * - Any aces are evaluated as either 1 or 11, choosing the card's score that maximizes the total score without exceeding 21.<br>
     * - If the total score exceeds 21, the hand is considered "busted" and the score becomes 0.<br>
     * - Otherwise, the score is the sum of all cards' score contained within the hand.</p>
     *
     * @return the total score of the current hand.<br>
     * - If the hand is a Blackjack, returns 100.<br>
     * - If the hand is busted, returns 0.<br>
     * - Otherwise, returns the calculated score based on the hand's cards.
     */
    @Override
    public int getScore() {
        int scoreWithoutAces = this.cards.stream()
                .filter(card -> card.getValue() != Card.Value.ACE)
                .mapToInt(Card::getScore)
                .sum();
        int aceCount = (int) this.cards.stream() // cast is safe since there can only be 4 aces and a total of 52 cards
                .filter(card -> card.getValue() == Card.Value.ACE)
                .count();
        int score = scoreWithoutAces + aceCount;
        if (score > 21) return 0; // busted
        if (aceCount > 0 && score <= 11) {
            score += 10; // possibly count at most one ace as value 11 (since two 11 aces would already count as 22)
        }
        return this.cards.size() == 2 && score == 21 ? 100 : score;
    }

    /**
     * Removes all cards from the current hand, effectively resetting it to an empty state.
     *
     * <p>This method is used to clear the hand of all cards, which may be necessary
     * when resetting the game, starting a new round, or when the hand needs to be emptied for other reasons.</p>
     */
    @Override
    public void clear() {
        this.cards.clear();

    }

    /**
     * Creates and returns a deep copy of this BJHand object.
     * Each card in the hand is also cloned to ensure a completely independent copy.
     *
     * @return a deep copy of this BJHand or null if the clone operation fails.
     */
    @Override
    public Hand clone() {
        BJHand newHand = new BJHand();
        newHand.cards = this.cards.stream()
                .map(card -> new BJCard(card.getSuit(), card.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
        return newHand;
    }

    /**
     * Returns a string representation of the Blackjack hand.
     *
     * @return a string representing the Blackjack hand, where cards are listed in the order they appear.
     */
    @Override
    public String toString() {
        return this.cards.toString();
    }
}
