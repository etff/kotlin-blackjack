package blackjack.domain.player

import blackjack.domain.card.Card
import blackjack.domain.card.Cards
import blackjack.domain.card.Deck
import blackjack.domain.card.Denomination
import blackjack.domain.card.Suit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PlayersTest {

    @Test
    fun `플레이어 목록을 생성할 수 있다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1)

        val givenGamer2 = Gamer(profile2)
        val actual = Players(listOf(givenGamer1, givenGamer2))

        assertThat(actual).isNotNull
    }

    @Test
    fun `플레이어 이름을 입력받아 플레이어 목록을 생성할 수 있다`() {
        val givenNames = "player1, player2"

        assertThat(Players.of(givenNames)).isNotNull
    }

    @Test
    fun `플레이어 목록에 한 명만 존재하면 예외를 던진다`() {
        val givenGamer1 = Gamer(Profile.from(Name("player1")))

        assertThrows<IllegalArgumentException> { Players(listOf(givenGamer1)) }
    }

    @Test
    fun `플레이어의 첫 시작단계에서 카드를 받을 수 있다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val givenDeck = Deck()
        val givenGamer1 = Gamer(profile1)
        val givenGamer2 = Gamer(profile2)
        val players = Players(listOf(givenGamer1, givenGamer2))

        val actual = players.startInitPhase(givenDeck)

        assertThat(actual).allMatch { !it.openCards().isEmpty() }
    }

    @Test
    fun `플레이어가 모두 종료상태라면 true를 아니라면 false를 리턴한다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1)
        val givenGamer2 = Gamer(profile2)
        val players = Players(listOf(givenGamer1, givenGamer2))

        assertThat(players.isAllPlayerTurnOff()).isTrue
    }

    @Test
    fun `플레이어 목록을 준비로 설정하면 바뀌면 준비 상태를 리턴한다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1)
        val givenGamer2 = Gamer(profile2)
        val players = Players(listOf(givenGamer1, givenGamer2))

        val actual = players.turnToReady()

        assertThat(actual.players).allMatch { it.isBurst() }
    }

    @Test
    fun `플레이어의 턴을 종료하면 종료된 상태를 리턴한다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1)
        val givenGamer2 = Gamer(profile2)
        val players = Players(listOf(givenGamer1, givenGamer2))

        val updatedPlayers = players.endPlayerTurn(givenGamer1)
        val actual = updatedPlayers.players.find { it.getPlayerName() == givenGamer1.getPlayerName() }

        assertThat(actual?.isBurst()).isFalse
    }

    @Test
    fun `플레이어를 추가하면 추가된 플레이어 목록을 리턴한다`() {
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val profile3 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1)
        val givenGamer2 = Gamer(profile2)
        val givenGamer3 = Gamer(profile3)
        val players = Players(listOf(givenGamer1, givenGamer2))

        val updatedPlayers = players.addPlayer(givenGamer3)

        assertThat(updatedPlayers.players).hasSize(3)
    }

    @Test
    fun `플레이어가 가진 점수 순으로 정렬한 목록을 리턴한다`() {
        val card1 = Card(Suit.HEART, Denomination.FIVE)
        val card2 = Card(Suit.CLUB, Denomination.SEVEN)
        val card3 = Card(Suit.DIAMOND, Denomination.KING)
        val profile1 = Profile.from(Name("player1"))
        val profile2 = Profile.from(Name("player2"))
        val profile3 = Profile.from(Name("player2"))
        val givenGamer1 = Gamer(profile1, Cards(listOf(card1)))
        val givenGamer2 = Gamer(profile2, Cards(listOf(card2)))
        val givenGamer3 = Gamer(profile3, Cards(listOf(card3)))
        val players = Players(listOf(givenGamer1, givenGamer2, givenGamer3))

        val actual = players.getPlayersByScore()

        assertThat(actual).containsExactly(givenGamer3, givenGamer2, givenGamer1)
    }

    @Test
    fun `플레이어 목록에 딜러가 2명이면 예외를 던진다`() {
        val dealer1 = Dealer.of()
        val dealer2 = Dealer.of()

        assertThrows<IllegalStateException> { Players(listOf(dealer1, dealer2)) }
    }
}
