package blackjack.domain.player

import blackjack.domain.card.Card
import blackjack.domain.card.Cards

class Dealer(profile: Profile, cards: Cards = Cards.EMPTY) : BlackJackPlayer(profile, cards) {

    override fun receiveCard(card: Card): Player {
        if (!canReceiveCard()) {
            return this
        }
        return Dealer(profile, cards.addCards(card))
    }

    override fun turnOff(): Player {
        return Dealer(profile.turnOff(), cards)
    }

    override fun turnOn(): Player {
        return Dealer(profile.turnOn(), cards)
    }

    override fun canReceiveCard(): Boolean {
        return getHighestPoint() <= CAN_ACHIEVE_POINT
    }

    companion object {
        private const val CAN_ACHIEVE_POINT = 16

        fun of(): Dealer {
            return Dealer(Profile.createDealer())
        }
    }
}
