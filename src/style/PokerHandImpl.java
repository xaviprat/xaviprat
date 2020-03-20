package style;

public class PokerHandImpl implements PokerHand {

	private Card[] hand;

	public PokerHandImpl(Card[] cards) {		
		
		if (cards == null) {
			throw new RuntimeException("There are no cards");
		}
		
		if (cards.length < 5) {
			throw new RuntimeException("There are not enough cards");
		} if (cards.length > 5) {
			throw new RuntimeException("There are too many cards");
		}
		
		for (int i=0; i<5; i++) {
			if (cards[i] == null) {
				throw new RuntimeException("One of the cards is null");
			}
		}

		hand = cards.clone();
		
	// Organizes hand - a Card array - from lowest to highest ranked		
		for (int i=0; i<5; i++) {			
			for (int j=i; j<5; j++) {
				if (hand[j].getRank() < hand[i].getRank()) {
					Card tmp = hand[i];
					hand[i] = hand[j];
					hand[j] = tmp;
				}
			}
		}		
	}

	public Card[] getCards() {
		
		Card[] clone = hand.clone();
		return clone;
	}

	// Checks if the hand of this object contains a specific card
	public boolean contains(Card card) {
		
		if (card == null) {
			throw new IllegalArgumentException ("Card object is null");
		}
		
		for (int i=0; i<5; i++) {
			if (hand[i].equals(card)) {
				return true;
			}
		}
		return false;
	}
	
	// Returns whether exactly two cards in the hand have the same rank		
	public boolean isOnePair() {	
		
		// Checks if there is at least one pair in the hand
		int pairIdx = pairStartingAt(0);
		if (pairIdx == -1) {
			return false;
		}

	// Checks that there is exactly only one pair - no two pair or three of a kind
		if (pairStartingAt(pairIdx+1) == -1) {
			return true;
		}		
		return false;
	}
	
	// Returns whether there are two different pairs 
	// of cards with different ranks within the hand
	public boolean isTwoPair() {	
		
		int firstPairIdx = pairStartingAt(0);
		int secondPairIdx = pairStartingAt(firstPairIdx+2);
		
		// First two lines check that there exists two different pairs,
		// last two check it is not a four of a kind or full house
		return ((firstPairIdx != -1) && 
					(secondPairIdx != -1) && 
					!isFourOfAKind() && 
					!isFullHouse());
	}

	// Returns whether there are exactly three cards with the 
	// same rank and the other two cards are not a pair
	public boolean isThreeOfAKind() {	
		
		int first_pair_idx = pairStartingAt(0);
	// Checks for the possibility of there existing a three of a kind
		if ((first_pair_idx == -1) || (first_pair_idx == 3)) {
		return false;
	}	
	
	// Checks whether three consecutive cards have the same rank and
	// accounts for possibility of four of a kind or full house
		return ((hand[first_pair_idx].getRank() == hand[first_pair_idx+2].getRank()) &&
			!isFourOfAKind() && !isFullHouse());
	}
	
	// Returns whether the 5 cards have consecutive ranks
	public boolean isStraight() {
				
		boolean isStraight = true;
		
		for (int i=0; i<4; i++) {
			if (hand[i].getRank()+1 != hand[i+1].getRank()) {
	// Checks for case where straight is from ace (value of 14) to 5			
				if (hand[i].getRank() != 5 || hand[i+1].getRank() != 14) {
				isStraight = false;
				}
			}
		}
		return isStraight;
	}

	// Returns whether all 5 cards are of the same suit
	public boolean isFlush() {
		
		for (int i=1; i<5; i++) {
			if (hand[i].getSuit() != hand[0].getSuit()) {
				return false;
			}
		}
		return true;
	}
	
	// Returns whether there is a three of a kind and a pair in the hand
	public boolean isFullHouse() {
	
	// Case 1 - first three cards are a three of a kind and last two are a pair
		if ((hand[0].getRank() == hand[2].getRank() && hand[3].getRank() == hand[4].getRank() ||
	// Case 2 - first two cards are a pair and last three are a three of a kind
				hand[0].getRank() == hand[1].getRank() && hand[2].getRank() == hand[4].getRank())) {
			return true;
		}
	return false;
	}
	// Returns true if four cards have the same rank
	public boolean isFourOfAKind() {
		
	// Case 1 - cards index 0-3 are the same rank
		if (hand[0].getRank() == hand[1].getRank()) {
			if (hand[1].getRank() == hand[2].getRank() && hand[1].getRank() == hand[3].getRank()) {
				return true;
			} else {
				return false;
			}
	// Case 2 - cards index 1-4 have the same rank		
		} else {
			for (int i=2; i<4; i++) {
				if (hand[1].getRank() != hand[i].getRank()) {
					return false;
				}
			}
		}
		return true;
	}	
	
	// Returns whether the hand is a straight and a flush
	public boolean isStraightFlush() {
		
		if (isStraight() && isFlush()) {
				return true;
			}
		return false;
	}
	
	// Returns the rank of a hand, used in poker to compare two hands that have 
	// the same value (two pairs or two three of a kinds for example).
	public int getHandRank() {
		// rank is rank of the pair
		if (isOnePair() == true) {
			return hand[pairStartingAt(0)].getRank();
		// rank is rank of the highest rank pair	
		} else if (isTwoPair() == true) {
			return hand[3].getRank();
		// rank is rank of the card there is three or four of with the same rank
		} else if (isThreeOfAKind() == true || isFourOfAKind() == true || isFullHouse() == true) {
			return hand[2].getRank();
		// rank if straight is ace through 5 is 5
		} else if (isStraight() == true) {
			if (hand[4].getRank() == 14 && hand[3].getRank() == 5) {
			return 5;
			}
		} 
		// for all others, return the highest ranking card in the hand
		return hand[4].getRank();
	}
	
	// Returns the hand value, used in poker to determine 
	// which hand is higher. Each hand has a value attached to it
	public int getHandTypeValue() {
		
		if (isStraightFlush()) return 9;	
		if (isFourOfAKind()) return 8;
		if (isFullHouse()) return 7;
		if (isFlush()) return 6;
		if (isStraight()) return 5;
		if (isThreeOfAKind()) return 4;		
		if (isTwoPair()) return 3;		
		if (isOnePair()) return 2;		
	return 1;
	}

	// Uses hand values and ranks to determine which hand is higher
	// If this object's hand is higher, returns 1
	// If the other object's hand is higher, returns -1
	// If both hands are equal in value and rank, returns 0
	public int compareTo(PokerHand other) {
		
		if (getHandTypeValue() < other.getHandTypeValue()) {
			return -1;
		} else if (getHandTypeValue() > other.getHandTypeValue()) {
			return 1;
	// Testing ranks if values are equal
		} else {
			if (getHandRank() < other.getHandRank()) {
				return -1;
			} else if (getHandRank() > other.getHandRank()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	// Returns -1 if there is no pair starting at the index given to it
		// Returns the index at which the pair begins if there is a pair
		private int pairStartingAt(int num) {	
			
			if (num < 0) {
				num = 0;
			}
			if (num >= 4) {
				return -1;
			}
			
			for (int i=num; i<4; i++) 	
				if (hand[i].getRank() == hand[i+1].getRank()) 
					return i;	
			
		return -1;
		}
	
}
