package com.example.administrator.test.kotlinTest

import com.google.gson.Gson
import com.yibasan.lizhi.sdk.network.http.HttpRequest
import com.yibasan.lizhifm.sdk.platformtools.Ln
import com.yibasan.lizhifm.sdk.platformtools.TextUtils
import com.yibasan.lizhifm.sdk.platformtools.http.PlatformHttpUtils
import com.yibasan.lizhifm.sdk.platformtools.http.PlatformHttpUtils.OnUrlConnectionOpenListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.tls.OkHostnameVerifier
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.Comparator
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 10:42 2020/6/3
 */
class KTest {

    @Test
    fun testSth() {
        val b = true
        println("${TAG} res:${b}")
        test()

        val list = mutableListOf<Int>()
        for (i in 0..2) list.add(i)
        println(list)
        val asReversed = list.asReversed()
        println(asReversed)
        println(list)

        println("time:${secToTime(9990)}")
    }


    fun secToTime(time: Int): String {
        var time = time
        var timeStr: String? = null
        var hour = 0
        var minute = 0
        var second = 0
        time = Math.max(0, time)
        minute = time / 60
        if (time <= 60 * 60) {
            second = time % 60
            timeStr = String.format(Locale.CHINA, "%02d:%02d", minute, second)
        } else {
            hour = minute / 60
            if (hour > 999) {
                return "59:59:59"
            }
            minute %= 60
            second = time - hour * 3600 - minute * 60
            timeStr = String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second)
        }
        return timeStr
    }

    @Test
    fun testFor() {
//        print(secToTime(3601))
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    fun findMode(root: TreeNode?): IntArray {
        if (root == null) return intArrayOf()
        val map = mutableMapOf<Int, Int>()
        val resList = mutableListOf<Int>()
        dfs(root, resList)
        return resList.toIntArray()
    }

    var preVal = 0
    var curCount = 0
    var maxCount = 0

    fun dfs(node: KTest.TreeNode?, list: MutableList<Int>) {
        if (node == null) return
        dfs(node.left, list)
        if (preVal == node.`val`) {
            curCount++
//            maxCount = max(curCount,maxCount)
        } else {
            preVal = node.`val`
            curCount = 1
        }
        if (maxCount == curCount) {
            list.add(node.`val`)
        } else if (curCount > maxCount) {
            maxCount = curCount
            list.clear()
            list.add(node.`val`)
        }
        dfs(node.right, list)
    }

    var postIndex = 0

    fun buildTree(inorder: IntArray, postorder: IntArray): TreeNode? {
        val midIndexMap = mutableMapOf<Int, Int>()
        inorder.forEachIndexed { index: Int, i: Int ->
            midIndexMap[i] = index
        }
        val len = inorder.size - 1
        postIndex = len
        return buildTree(inorder, postorder, 0, len, midIndexMap)
    }

    private fun buildTree(inorder: IntArray, postorder: IntArray, preStart: Int, preEnd: Int, indexMap: MutableMap<Int, Int>): TreeNode? {
        if (postIndex < 0 || preStart > preEnd) return null
        val indexOrderIndex = indexMap[postorder[postIndex]] ?: return null
        val treeNode = TreeNode(inorder[indexOrderIndex])
//        var pIndex = postIndex
        postIndex--
        treeNode.right = buildTree(inorder, postorder, indexOrderIndex + 1, preEnd, indexMap)
        treeNode.left = buildTree(inorder, postorder, preStart, indexOrderIndex - 1, indexMap)
        return treeNode
    }

    fun exist(board: Array<CharArray>, word: String): Boolean {
        if (board.isEmpty()) return false
        if (word.isEmpty()) return true
        val m = board.size
        val n = board[0].size
        board.forEachIndexed { mIndex, mChars ->
            mChars.forEachIndexed { nIndex, nChars ->
                if (exist(board, word, 0, mIndex, nIndex, m, n)) return true
            }
        }
        return false
    }

    private fun exist(board: Array<CharArray>, word: String, wIndex: Int, x: Int, y: Int, m: Int, n: Int): Boolean {
        if (wIndex >= word.length) return true
        if (x < 0 || x >= m || y < 0 || y >= n) return false
        if (word[wIndex] != board[x][y] || board[x][y] == '-') return false
        val c = board[x][y]
        board[x][y] = '-'
        val res = exist(board, word, wIndex, x, y, m, n) || exist(board, word, wIndex, x, y, m, n)
                || exist(board, word, wIndex, x, y, m, n) || exist(board, word, wIndex, x, y, m, n)
        board[x][y] = c
        return res
    }

    @Test
    fun testKobj() {
        val kObj = KObj<Int>()
        kObj.obj = 10
        kObj.init {
            println(it)
        }
        println(measureTimeMillis {

            val json = "{\"priorityList\":[{\"adSourceType\":0,\"priority\":1,\"weight\":0},{\"adSourceType\":2,\"priority\":1,\"weight\":100}],\"rcode\":0}"
            val data = Gson().fromJson(json, ThirdAdPriorityResponse::class.java)
            println(data.priorityList)
        })
    }

    data class ThirdAdPriorityResponse(val rcode: Int, val priorityList: List<ThirdAdPriority>)

    data class ThirdAdPriority(
            /**
             * 广告源，0为荔枝直投，1为实时api，2为sdk
             */
            val adSourceType: Int,
            /**
             * 广告源优先级，优先判断优先级，优先级一样再判断权重，越小越优先
             */
            val priority: Int,
            /**
             * 广告源权重，优先级一样时使用
             */
            val weight: Int
    )

    fun sortedSquares(A: IntArray): IntArray {
        val res = IntArray(A.size)
        var resIndex = 0
        var start = 0
        var end = A.size - 1
        while (resIndex < A.size) {
            if (Math.abs(A[start]) > Math.abs(A[end])) {
                res[resIndex++] = Math.pow(A[start++].toDouble(), 2.0).toInt()
            } else {
                res[resIndex++] = Math.pow(A[end--].toDouble(), 2.0).toInt()
            }
        }

        return res
    }

    fun backspaceCompare(S: String, T: String): Boolean {
        val sChars = mutableListOf<Char>()

        return false
    }

    @Test
    fun testBreak() {
        val listOf = listOf<Int>(1, 2, 3, 4)
        listOf.forEach Fuck@{
            if (it == 3) return
            println(it)
        }
        println("finish")
    }

    fun sortByBits(arr: IntArray): IntArray {
        val bitMap = IntArray(10001)
        for (i in 1..bitMap.size) {
            bitMap[i] = bitMap[i shr 1] + bitMap[i] and 1
        }
        return arr.sortedWith(Comparator<Int> { o1, o2 ->
            bitMap[o1] - bitMap[o2]
        }).toIntArray()
    }


    fun compute(data: Collection<Int>): Int {
//        Logz.tag(IDecision.TAG).d("LzSplashDecision#start compute in:$data")
        val priorityMap = mResponseData!!.priorityList.groupBy { it.priority }
        val sortedPriorityList = priorityMap.keys.sorted()
        var res = -1
        // 根据优先级低的顺序取，取到直接返回
        sortedPriorityList.forEach {
            if (res != -1) {
                return@forEach
            }
            val weightList = priorityMap[it]
            // 当当前优先级相同数量大于1时，先去除广告数据不存在的广告类型配置
            if (weightList?.size!! > 1) {
                var l = weightList.toMutableList()
                l.filter { thirdAdPriority ->
                    !data.contains(thirdAdPriority.adSourceType)
                }
                // 根据权重比算出要展示哪个
                val predictionValue = Random.nextFloat()
                val totalWeight = l.sumBy { thirdAdPriority -> thirdAdPriority.weight }
                var curValue = 0f
                l.forEach { thirdAdPriority ->
                    val pre = curValue
                    curValue += thirdAdPriority.weight.div(totalWeight.toFloat())
                    if (pre <= predictionValue && predictionValue < curValue) {
                        res = thirdAdPriority.adSourceType
                    }
                }
            } else if (data.contains(weightList[0].adSourceType)) {
                res = weightList[0].adSourceType
            }
        }
//        Logz.tag(IDecision.TAG).w("LzSplashDecision#compute show splash type is $res")
        return res
    }

    var mResponseData: ThirdAdPriorityResponse? = null

    @Test
    fun computeTest() {
        val json = "{\"priorityList\":[{\"adSourceType\":0,\"priority\":1,\"weight\":100},{\"adSourceType\":1,\"priority\":3,\"weight\":100},{\"adSourceType\":2,\"priority\":3,\"weight\":100}],\"rcode\":0}"
        mResponseData = Gson().fromJson(json, ThirdAdPriorityResponse::class.java)
        println(mResponseData?.priorityList)

        println(compute(listOf(1, 2)))
    }


    fun reconstructQueue(people: Array<IntArray>): Array<IntArray> {
        people.sortWith(compareBy({ it[0] }, { it[1] }))
        val res = arrayOfNulls<IntArray>(people.size)
        people.forEach {
            var pre = it[1]
            run out@{
                people.forEachIndexed{index,value->
                    if(pre == 0 && res[index]?.isEmpty() != false){
                        res[index] = it
                        return@out
                    }else if(pre > 0){
                        pre--
                    }
                }
            }
        }
        return res.requireNoNulls()
    }

    fun moveZeroes(nums: IntArray): Unit {
        val last = nums.size - 1
        for (i in last downTo 0){
            if(nums[i] == 0){
                var tempI = i
                while(tempI < last){
                    if(nums[tempI + 1] == 0)break
                    val t = nums[tempI]
                    nums[tempI] = nums[tempI + 1]
                    nums[tempI + 1] = t
                }
            }
        }
    }

    fun searchRange(nums: IntArray, target: Int): IntArray {
        var resStartPos = -1
        var resEndPos = -1
        var start = 0
        var end = nums.size - 1
        while(start <= end){
            val mid = start + (end - start)/2
            if(nums[mid] == target){
                resStartPos = mid
                resEndPos = mid
                break
            }else if(nums[mid] < target){
                start = mid + 1
            }else{
                end = mid - 1
            }
        }
        if(resStartPos != -1 && nums[resStartPos - 1] == target){
            resStartPos--
        }
        if(resEndPos != -1 && nums[resEndPos + 1] == target){
            resEndPos++
        }
        return intArrayOf(resStartPos,resEndPos)
    }

    fun lemonadeChange(bills: IntArray): Boolean {
        val billMap = mutableMapOf<Int,Int>()
        bills.forEach {
            var rest = it - 5
            while(rest > 0){

            }
            billMap[it] = billMap[it]?:0.plus(1)
        }
        return true
    }

    class Resp{
        fun show(){

        }
    }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        // val res = mutableListOf<List<String>>()
        val map = mutableMapOf<String,MutableList<String>>()
        strs.forEach{
            val charMap = IntArray(26)
            it.forEach{ c->
                charMap[c-'a']++
            }
            val cKey = StringBuilder().apply{
                charMap.forEachIndexed{ index,i->
                    if(i > 0){
                        append('a'+ index + "$i")
                    }
                }
            }.toString()
            println("$it hash:$cKey")
            if(map.containsKey(cKey)){
                map[cKey]!!.add(it)
            }else{
                val list = mutableListOf<String>()
                list.add(it)
                map[cKey] = list
            }
        }
        return map.values.toList()
    }

    @Test
    fun testCheck(){
//        println(monotoneIncreasingDigits(123))
        "123".forEach{
            println("c:$it")
            run out@{
                "111".forEachIndexed { index, c ->
                    println("i:$index")
                    if(index == 1)return@out
                }
            }
        }
    }


    fun checkIncrease(num:Int):Boolean{
        if(num < 10)return true
        var n = num
        var pre = n % 10
        n /= 10
        while(n > 0){
            var cur = n % 10
            println("n:$n cur:$cur pre:$pre")
            if(cur > pre)return false
            pre = cur
            n /= 10
        }
        return true
    }

    fun monotoneIncreasingDigits(N: Int): Int {
        val strN = N.toString().toMutableList()
        var length = strN.size
        var index = 1
        while(index < length && strN[index - 1] <= strN[index]){
            index++
        }
        if(index>=length)return N
        var idx = index - 1
        while(idx > 0 && strN[idx - 1] == strN[idx]){
            idx--
        }
        strN[idx] = strN[idx] - 1
        idx++
        while(idx < length){
            strN[idx++] = '9'
        }
        return Integer.parseInt(String(strN.toCharArray()))
    }

    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        intervals.contentToString()
        intervals.sortBy { v->
            v[0]
        }
        intervals.sortWith(object :Comparator<IntArray>{
            override fun compare(o1: IntArray?, o2: IntArray?): Int {
                if (o1!![0] == o2!![0]) {
                    return o1!![1] - o2!![1]
                }
                return o1!![0] - o2!![0]
            }
        })
        return 0
    }

    @Test
    fun testHttpUrlConnection(){
        val  url = "https://e.cn.miaozhen.com/r/k=2214707&p=7olHd&dx=__IPDX__&rt=2&pro=s&ns=__IP__&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&tr=__REQUESTID__&mo=__OS__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&m11=__OAID__&mn=__ANAME__&o=http://trace.rtbasia.com/tkp?rta_k=sj3B6daQfO&utm_campaign=_rtc_90a234b4bedfc469&utm_source=lizhi&utm_content=_rtb_b9bqxw&utm_medium=national&utm_term=CPM&device=mo&imei=__IMEI__&mac=__MAC__&idfa=__IDFA__&openudid=__OPENUDID__&oaid=__OAID__&custid1=__CUSTID1__&custid2=__CUSTID2__&clientip=__IP__&os=__OS__&ts=__TS__&ua=__UA__&androidid=__ANDROIDID__&adtype=7&crtype=2&appname=__APPNAME__&bundle=__BUNDLE__&sg=a595c7a10b36916116d2a0a70782f81f&ord=__CACHEBUSTER__"
//        val listener = object :OnUrlConnectionOpenListener{
//            override fun onUrlConnnectionOpen(conn: HttpURLConnection?) {
//                println("code:${conn?.responseCode} redirect:${conn?.instanceFollowRedirects} location:${conn?.getHeaderField("Location")}")
//                if(conn?.responseCode == HttpURLConnection.HTTP_MOVED_TEMP){
//                    PlatformHttpUtils.openUrlConnByGetMethod(conn?.getHeaderField("Location"),"",null,this)
//                }
//            }
//
//        }
//        PlatformHttpUtils.openUrlConnByGetMethod(url,"",null,listener)

//        HttpRequest.Builder()
//                .url(url)
//                .method(HttpRequest.GET)
//                .contentType(HttpRequest.CONTENT_TYPE_FORM_UTF8)
//                .build()
//                .asObservable()
//                .blockingSubscribe(){
//                    println("res:$it")
//                }

        val request = Request.Builder().get().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        println("code:${response.code()}")
    }

    @Throws(Exception::class)
    private fun openUrlConn(urlString: String, isGet: Boolean, userAgent: String, headers: Map<String, String>?, requestBody: String, connectTimeout: Int, readTimeout: Int, listener: OnUrlConnectionOpenListener?) {
        Ln.d("urlString=%s", urlString)
        var conn: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            conn = url.openConnection() as HttpURLConnection
            conn.doInput = true
            conn!!.allowUserInteraction = true
            if (isGet) {
                conn.requestMethod = "GET"
            } else {
                conn.requestMethod = "POST"
                conn.doOutput = true
            }
            if (!TextUtils.isNullOrEmpty(userAgent)) {
                conn.setRequestProperty("User-agent", userAgent)
            }
            Ln.d("User-agent=%s", userAgent)
            if (connectTimeout > 0) {
                conn.connectTimeout = connectTimeout
            }
            if (readTimeout > 0) {
                conn.readTimeout = readTimeout
            }
            if (headers != null && headers.size > 0) {
                for (key in headers.keys) {
                    conn.setRequestProperty(key, headers[key])
                }
            }
            //增加对SSL证书的校验
            if (conn is HttpsURLConnection) {
                val httpsURLConnection = conn
                httpsURLConnection.sslSocketFactory = PlatformHttpUtils.getSSLFactory()
                httpsURLConnection.hostnameVerifier = OkHostnameVerifier.INSTANCE
            }
            conn.connect()
            if (!TextUtils.isEmpty(requestBody)) {
                conn.outputStream.write(requestBody.toByteArray())
            }
            listener?.onUrlConnnectionOpen(conn)
        } catch (e: Exception) {
            Ln.e(e)
            throw e
        } finally {
            conn?.disconnect()
        }
    }
}