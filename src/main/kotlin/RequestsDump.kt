import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.closeQuietly
import org.bson.Document

class RequestsDump {
    val client = OkHttpClient().newBuilder()
        .build()
    val gson=Gson()


    fun searchProperties(page:Int): Int {
        val pageSize=100
        val request = Request.Builder()
            .url("https://www.99acres.com/api-aggregator/discovery/srp/search?area_unit=1&platform=MOBILE&moduleName=GRAILS_SRP&workflow=GRAILS_SRP&page_size=50&page=25&city=20&preference=S&res_com=R&seoUrlType=DEFAULT")
            .method("GET", null)
//            .addHeader("authority", "www.99acres.com")
//            .addHeader("sec-ch-ua", "\" Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"91\", \"Chromium\";v=\"91\"")
            .addHeader("dnt", "1")
            .addHeader("sec-ch-ua-mobile", "?0")
            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            .addHeader("accept", "*/*")
            .addHeader("sec-fetch-site", "same-origin")
            .addHeader("sec-fetch-mode", "cors")
            .addHeader("sec-fetch-dest", "empty")
//            .addHeader("referer", "https://www.99acres.com/search/property/buy/residential-all/bangalore-all?search_type=QS&refSection=GNB&search_location=NRI&lstAcn=NR_R&lstAcnId=-1&src=CLUSTER&preference=S&selected_tab=1&city=20&res_com=R&property_type=R&isvoicesearch=N&keyword_suggest=Bangalore%20(All)%3B&fullSelectedSuggestions=Bangalore%20(All)&strEntityMap=W3sidHlwZSI6ImNpdHkifSx7IjEiOlsiQmFuZ2Fsb3JlIChBbGwpIiwiQ0lUWV8yMCwgUFJFRkVSRU5DRV9TLCBSRVNDT01fUiJdfV0%3D&texttypedtillsuggestion=ban&refine_results=Y&Refine_Localities=Refine%20Localities&action=%2Fdo%2Fquicksearch%2Fsearch&suggestion=CITY_20%2C%20PREFERENCE_S%2C%20RESCOM_R&searchform=1&price_min=null&price_max=null")
            .addHeader("accept-language", "en-IN,en;q=0.9")
//            .addHeader("cookie", "99_ab=36; 99_FP_VISITOR_OFFSET=43; NEW_VISITOR=1; GOOGLE_SEARCH_ID=950523909767526101; PROP_SOURCE=IP; 99_suggestor=34; _sess_id=wL6dkX3sUA4qQ1OutYSFtq3WZagJMvMF5HYnaldBki6KMZCNYvpc89tievPnYatE28uBWbcd0PTpvCp9k3O20w%3D%3D; 99_trackIP=SC; 99NRI=2; RES_COM=RES; src_city=-1; 99_citypage=-1; 99_city=; 99zedoParameters=%7B%22city%22%3A%22-1%22%2C%22locality%22%3A%22%22%2C%22budgetBucket%22%3Anull%2C%22activity%22%3A%22NRI%22%2C%22rescom%22%3A%22RES%22%2C%22preference%22%3A%22BUY%22%2C%22nri%22%3A%22YES%22%7D; kwp_last_action_id_type=-1%2CNR_R%2C950523909767526101; CPN=https%3A%2F%2Fwww.99acres.com%2Fsearch%2Fproperty%2Fbuy%2Fresidential-all%2Fpune; 99_ab=18; GOOGLE_SEARCH_ID=4676121625203908077")
            .build()
        val response = client.newCall(request).execute()
        val string = response.body?.string()
        response.closeQuietly()

        val propEleam: JsonArray?
        return try {
            val document = JsonParser.parseString(string).asJsonObject
            propEleam = document["properties"].asJsonArray

            propEleam.forEach {
                val document1 = Document.parse(it.toString())
                Main.data.insertOne(document1)
            }
            println(propEleam.size())
            propEleam.size()
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }

    }
}