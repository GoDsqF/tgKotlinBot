import com.elbekd.bot.*
import com.elbekd.bot.model.toChatId
import com.elbekd.bot.types.BotCommand
import com.elbekd.bot.util.SendingString
import kotlinx.coroutines.*
import java.time.LocalDateTime

suspend fun main() = coroutineScope{
	val token = BOT_TOKEN
	val username = BOT_USERNAME
	val bot = Bot.createPolling(token, username)

	bot.onCommand("/start") { (msg, _) ->
		repeat(1) {
			bot.sendMessage(
				chatId = msg.chat.id.toChatId(),
				text = msg.chat.id.toString()
			)
		}
	}

	bot.onCommand("/set") { (msg, _) ->
		bot.setMyCommands(
			commands = listOf(
				BotCommand(
					command = "/start", description = "this is cringe"
				),
				BotCommand(
					command = "/set", description = "commands setter"
				),
				BotCommand(
					"/nextpost", "когда следующий пост?"
				)
			)
		)
		bot.sendMessage(
			chatId = msg.chat.id.toChatId(),
			text = "Commands installed"
		)
	}

	bot.onCommand("/nextpost") { (msg, _) ->
		if (getPostTimeList().isNotEmpty()) {
			val time = getPostTimeList()[0]
			var hours = ""
			var minutes = ""
			for (i in 0..<2) {
				when (i) {
					0 -> hours = time.toString().split("T")[1].split(":")[i]
					1 -> minutes = time.toString().split("T")[1].split(":")[i]
				}
			}
			bot.sendMessage(
				msg.chat.id.toChatId(),
				"Следующий пост в $hours:$minutes"
			)
		}
		else bot.sendMessage(
			msg.chat.id.toChatId(),
			"Новых постов нет"
		)
	}

	bot.onMessage { msg ->
		if (msg.chat.id == BOT_CHAT_ID){
			if (msg.photo.isNotEmpty()) {
				addItem(db, msg.photo[0])
			}
		}
	}

	launch {
		while (true) {
			delay(60000L)
			if (getPostTimeList()[0] < LocalDateTime.now().plusHours(3)) {
				bot.sendPhoto(
					POST_CHAT_ID.toChatId(),
					SendingString(postItem(db))
				)
				deleteItem()
			}
		}
	}

	bot.start()
}