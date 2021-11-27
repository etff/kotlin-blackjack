package blackjack.domain.player

import blackjack.domain.card.Card
import blackjack.domain.card.Cards
import blackjack.domain.game.ScoreResult

interface Player {
    fun receiveCard(card: Card): Player

    fun turnOff(): Player

    fun turnOn(): Player

    fun isBurst(): Boolean

    fun openCards(): Cards

    fun getPlayerName(): Name

    fun getHighestPoint(): Int

    fun canReceiveCard(): Boolean

    fun judge(players: List<Player>): ScoreResult
}
