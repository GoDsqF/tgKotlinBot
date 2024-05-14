import com.elbekd.bot.types.PhotoSize
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import java.time.LocalDateTime

data class Posts(
	val fileId: String,
	val postTime: LocalDateTime,
	val fileUniqueId: String
)
fun getPostsCollection(): MongoCollection<Posts> {
	return db.getCollection<Posts>(collectionName)
}

suspend fun getNewPostTime(): LocalDateTime {
	val collection = getPostsCollection()
	var newPostTime = LocalDateTime.now().plusHours(4)
	if (collection.countDocuments(client.startSession()).toInt() != 0) {
		val listOfPosts = mutableListOf<Posts>()
		val flowResult = collection.find(Filters.empty())
		flowResult.collect {
			listOfPosts.add(it)
		}
		newPostTime = getPostTimeList().sortedDescending()[0].plusHours(1)
	}
	return newPostTime
}

suspend fun addItem(img: PhotoSize) {

	val collection = getPostsCollection()
	val item = Posts(
		fileId = img.fileId,
		postTime = getNewPostTime(),
		fileUniqueId = img.fileUniqueId
	)
	collection.insertOne(item).also {
		println("Item added with id - ${it.insertedId}")
	}

}

suspend fun getPostTimeList(): List<LocalDateTime> {
	val collection = getPostsCollection()
	val listOfPosts = mutableListOf<Posts>()
	val listOfDateTimes = mutableListOf<LocalDateTime>()
	val flowResult = collection.find(Filters.empty())
	flowResult.collect {
		listOfPosts.add(it)
	}

	for (post in listOfPosts) {
		listOfDateTimes.add(post.postTime)
	}

	listOfDateTimes.sorted()

	return listOfDateTimes
}
suspend fun postItem(): String {
	val collection = getPostsCollection()
	return if (collection.countDocuments(client.startSession()).toInt() != 0) {
		val str = mutableListOf<Posts>()
		val result = collection.find(Filters.eq("postTime", getPostTimeList()[0]))
		result.collect {
			str.add(it)
		}
		(str[0].fileId)
	}
	else "AgACAgEAAxkBAANeZjZMTTWIWvVA8G1U2FO-zl3OP0oAAvGrMRtGQbFFRcoGKy8erqsBAAMCAAN5AAM0BA"
}

suspend fun deleteItem() {
	val collection = getPostsCollection()
	if (collection.countDocuments(client.startSession()).toInt() != 0) {
		collection.deleteOne(Filters.eq("postTime", getPostTimeList()[0]))
	}
}