
import com.elbekd.bot.model.toChatId
import java.util.*

fun longToDate(time: Long): String {
	val sdf = java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
	val date = Date(time * 1000)
	return sdf.format(date)
}

val usernames: List<String> = listOf(
	"gordonmayer",
	"GusIzDerevni",
	"FoxWithAScarf",
	"mgb_gg",
	"a0gzy"
)

var lastIndex = -1
