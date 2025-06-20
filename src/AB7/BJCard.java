package AB7;

import AB7.Interfaces.Card;

/**
 * Represents a playing card for the Blackjack game.
 * A card consists of a suit and a value. The suit can be hearts, diamonds, clubs, or spades, and the value
 * can range from the numbers two through ten, to face cards (jack, queen, king) and ace.
 * It also includes functionality to calculate the score based on the card's value.
 */
public class BJCard implements Card {
    private final Suit suit;
    private final Value value;

    /**
     * Constructs a new BJCard object representing a playing card in the game of Blackjack.
     *
     * @param suit  the suit of the card, such as HEARTS, DIAMONDS, CLUBS, or SPADES
     * @param value the value of the card, such as TWO, THREE, TEN, JACK, QUEEN, KING, or ACE
     */
    public BJCard(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * Retrieves the suit of the card.
     *
     * @return the suit of the card, which is one of the four suits in a standard deck of
     * playing cards: HEARTS, DIAMONDS, CLUBS, or SPADES.
     */
    @Override
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Retrieves the value of the card.
     *
     * @return the value of the card, which represents its rank in a standard deck of playing cards.
     * This includes number values (TWO through TEN), face cards (JACK, QUEEN, KING), and ACE.
     */
    @Override
    public Value getValue() {
        return this.value;
    }

    /**
     * Calculates the score associated with the card based on its value.
     * For number cards (2-10), the score matches the card value.
     * For face cards (jack, queen, king), the score is 10.
     * For an ace, the score is 11.
     *
     * @return the score value of the card based on its value.
     */
    @Override
    public int getScore() {
        return switch (this.value) {
            case JACK, QUEEN, KING -> 10;
            case ACE -> 11;
            default -> this.value.ordinal() - Value.TWO.ordinal() + 2;
        };
    }

    /**
     * Returns a string representation of the card, combining its value and suit.
     *
     * <p>The string's format and content can be freely chosen, but should at least contain information on suit and value.</p>
     *
     * @return a string containing the card's value and suit.
     */
    @Override
    public String toString() {
        return String.format("%s of %s (%d)", this.value, this.suit, this.getScore());
    }

    /**
     * Compares this card to the specified object for equality. Two cards are considered equal
     * if they have the same suit and value.
     *
     * @param obj the object to be compared with this card for equality
     * @return true if the specified object is equal to this card; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj instanceof BJCard card)
            return this.suit == card.suit && this.value == card.value;
        return false;
    }

}
