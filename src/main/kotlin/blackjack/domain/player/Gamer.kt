package blackjack.domain.player

import blackjack.domain.card.Card
import blackjack.domain.card.Cards

data class Gamer(
    val profile: Profile,
    val cards: Cards = Cards.EMPTY
) : Player {

    override fun receiveCard(card: Card): Gamer {
        return Gamer(profile, cards.addCards(card))
    }

    override fun turnOff(): Gamer {
        return Gamer(profile.turnOff(), cards)
    }

    override fun turnOn(): Gamer {
        return Gamer(profile.turnOn(), cards)
    }

    override fun isBurst(): Boolean {
        return profile.isBurst()
    }

    override fun openCards(): Cards {
        return cards
    }

    override fun getPlayerName(): Name {
        return profile.name
    }

    override fun getHighestPoint(): Int {
        return cards.getHighestPoint()
    }
}
