package style;

public class DeckImpl implements Deck {
	
	private Card[] deck;			
	private int leftToDeal;	
	
	public DeckImpl() {
		leftToDeal = 52;
		deck = new Card[leftToDeal];
	// Loop that fills the deck with all 52 different cards
		int idx = 0;
		for (Card.Suit s : Card.Suit.values()) {
			for (int rank = 2; rank <= CardImpl.ACE; rank++) {
				//System.out.println(rank);
				deck[idx] = new CardImpl(rank, s);
				idx += 1;
			}
		}
	
	// Shuffles the deck
		for (int i=0; i<deck.length; i++) {
			int swapIdx = i + ((int) (Math.random() * (deck.length - i)));
			Card tmp = deck[i];			
			deck[i] = deck[swapIdx];		
			deck[swapIdx] = tmp;
		}		
	}

	// Returns whether there are enough cards left for a hand
	public boolean hasHand() {
		boolean bool = false;
		if (leftToDeal >= 5) {
			bool = true;
		}
		return (bool);
	}

	// Deals a card
	public Card dealNextCard() {
		if (leftToDeal == 0) {
			throw new RuntimeException("no cards left in the deck");
		}
		Card dealtCard = deck[nextUndealtIndex()];
		leftToDeal -= 1;
		return dealtCard;
	}

	// Deals 5 cards to make a poker hand
	public PokerHand dealHand() {
		if (hasHand() == false) {
			throw new RuntimeException("Deck does not have enough cards to deal another hand");
		}
		
		Card[] hand = new Card[5];
		for (int i=0; i<hand.length; i++) {			
			hand[i] = dealNextCard();
		}
		
		PokerHand pokerH = new PokerHandImpl(hand);
		return pokerH;
	}	

	// Finds whether a certain card still has not been dealt and deals it if it hasn't been
	public void findAndDeal(Card c) {
		if (leftToDeal == 0) {
			return;
		}
		
		for (int i=nextUndealtIndex(); i<52; i++) {
			if (deck[i].equals(c)) {
				Card tmp = deck[i];
				deck[i] = deck[nextUndealtIndex()];
				deck[nextUndealtIndex()] = tmp;
				dealNextCard();
				return;
			}
		}
		return;
	}
	
	// Method useful to determine what card the deck should deal next
	private int nextUndealtIndex() {
		int x = 52-leftToDeal;
		return x;
	}
}
