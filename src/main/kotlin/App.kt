import com.elbekd.bot.types.PhotoSize
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import java.time.LocalDateTime

data class Posts(
	val fileId: String,
	val postTime: LocalDateTime
)
fun getPostsCollection(database: MongoDatabase): MongoCollection<Posts> {
	return database.getCollection<Posts>(collectionName)
}

suspend fun getNewPostTime(database: MongoDatabase): LocalDateTime {
	val collection = getPostsCollection(database)
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

suspend fun addItem(database: MongoDatabase, img: PhotoSize) {

	val collection = getPostsCollection(database)
	val item = Posts(
		fileId = img.fileId,
		postTime = getNewPostTime(database)
	)
	collection.insertOne(item).also {
		println("Item added with id - ${it.insertedId}")
	}

}

suspend fun getPostTimeList(): List<LocalDateTime> {
	val collection = getPostsCollection(db)
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
suspend fun postItem(database: MongoDatabase): String {
	val collection = getPostsCollection(database)
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
	val collection = getPostsCollection(db)
	if (collection.countDocuments(client.startSession()).toInt() != 0) {
		collection.deleteOne(Filters.eq("postTime", getPostTimeList()[0]))
	}
}