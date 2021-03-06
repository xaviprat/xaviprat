package style;

public class CardImpl implements Card {
	
	// String array that contains all card ranks.
	// Their index on the array represents their rank.
	private static String[] ranks = {null, null, "Two", "Three", "Four", "Five", "Six", "Seven", 
										"Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	
	// Public fields to allow DeckImpl access to variables
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;

	private int rank;
	private Card.Suit suit;

	public CardImpl(int rank, Card.Suit suite) {
		this.rank = rank;
		this.suit = suite;
	}
	
	public int getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}
	
	// Returns the rank and suit of a card as a String
	public String toString() {
		return ranks[getRank()] + " of " + Card.suitToString(getSuit());
	}
	
	// Checks if two cards have the same rank and suit
	public boolean equals(Card other) {
		return (rank == other.getRank()) && (suit.equals(other.getSuit()));
	}

}
