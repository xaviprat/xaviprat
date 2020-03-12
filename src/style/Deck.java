package style;

public interface Deck {

	boolean hasHand();
	Card dealNextCard();
	PokerHand dealHand();
	void findAndRemove(Card c);

}