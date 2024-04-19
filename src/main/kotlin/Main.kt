import com.elbekd.bot.Bot
import com.elbekd.bot.model.toChatId
import com.elbekd.bot.server
import com.elbekd.bot.types.BotCommand
import com.elbekd.bot.types.BotCommandScope
import com.elbekd.bot.types.KeyboardButton
import com.elbekd.bot.types.KeyboardButtonPollType
import com.elbekd.bot.types.ParseMode
import com.elbekd.bot.types.ReplyKeyboardMarkup
import kotlin.random.Random

fun main() {
	val token = BOT_TOKEN
	val username = BOT_USERNAME
	val bot = Bot.createPolling(token, username)

	bot.onCommand("/start") { (msg, _) ->
		bot.sendMessage(
			chatId = msg.chat.id.toChatId(),
			text = "Hi"
		)

	}

	bot.onCommand("/churka") { (msg, _) ->
		val random = Random.nextInt(usernames.size)
		lastIndex = random
		bot.sendMessage(
			msg.chat.id.toChatId(),
			"Я думаю, что чурка - ${usernames[random]}"
		)
	}

	bot.onCommand("/lastchurka") { (msg, _) ->
		if (lastIndex != -1) {
			bot.sendMessage(
				msg.chat.id.toChatId(),
				"${usernames[lastIndex]}, а ну быстро в газовую камеру"
			)
		}
		else bot.sendMessage(
			msg.chat.id.toChatId(),
			"Сначала выбери чурку, долбаёб"
		)
	}

	bot.onCommand("/date") { (msg, _) ->
		bot.sendMessage(
			msg.chat.id.toChatId(),
			longToDate(msg.date)
		)
	}

	bot.onCommand("/set") { (msg, _) ->
		bot.setMyCommands(
			commands = listOf(
				BotCommand(
					command = "/start", description = "this is cringe"
				),
				BotCommand(
					command = "/set", description = "setting commands"
				),
				BotCommand(
					command = "/date", "u now"
				),
				BotCommand(
					"/churka", "who is churka?"
				),
				BotCommand(
					"/lastchurka", "быстро в газовую камеру"
				)
			)
		)
		bot.sendMessage(
			chatId = msg.chat.id.toChatId(),
			text = "Commands installed"
		)
	}
	bot.start()
}