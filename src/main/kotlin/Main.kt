import org.litote.kmongo.KMongo


object Main {

    val mongoClient = KMongo.createClient() //get com.mongodb.MongoClient new instance
    val database = mongoClient.getDatabase("test") //normal java driver usage
    val data = database.getCollection("99accers")

    @JvmStatic
    fun main(args: Array<String>) {
        val dumps = RequestsDump()
        (1..320).forEach {page->
            val pageCOunt = dumps.searchProperties(page)
            println("Page $page  has $pageCOunt" )
        }


    }
}