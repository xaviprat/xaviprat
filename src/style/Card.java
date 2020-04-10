package style;

public interface Card {
	
public enum Suit {SPADES, HEARTS, DIAMONDS, CLUBS};

int getRank();
Card.Suit getSuit();
String toString();
boolean equals(Card other);

// Method that returns a card's suit as a string
public static String suitToString(Card.Suit s) {
	switch (s) {	
	case SPADES:return "Spades";
	case HEARTS:return "Hearts";
	case DIAMONDS:return "Diamonds";
	case CLUBS:return "Clubs";
	}	
	return null;	
}
}