package com.example.administrator.test.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.administrator.test.R
import com.example.administrator.test.view.VotePluginManager
import com.example.administrator.test.view.VoteTeamView
import com.yibasan.lizhifm.sdk.platformtools.ResUtil
import com.yibasan.lizhifm.sdk.platformtools.ui.ViewUtils
import kotlinx.android.synthetic.main.activity_float_view.*
import kotlinx.android.synthetic.main.item_topic_read_card.view.iv_cover
import kotlinx.android.synthetic.main.item_topic_read_card.view.tv_card_title
import kotlinx.android.synthetic.main.item_vote_comptition_record.view.*
import me.drakeet.multitype.Item
import me.drakeet.multitype.ItemViewProvider
import me.drakeet.multitype.MultiTypeAdapter
import kotlin.random.Random

class FloatViewActivity : AppCompatActivity() {


    val ran = Random(System.currentTimeMillis())

    var data = mutableListOf<Item>()
    lateinit var mAdapter : MultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_view)
        VotePluginManager.attachTo(window.decorView as ViewGroup)

//        v_vote_progress.setVoteData(1)
        v_vote_progress.setOnClickListener {
            v_vote_progress.setVoteData(ran.nextInt(100), ran.nextInt(100))
        }

        val list = mutableListOf<VoteTeamView.ItemData>()
        for (i in 0..3) {
            list.add(VoteTeamView.ItemData(i.toLong(),
                    "https://avatars1.githubusercontent.com/u/5967952${i%10}",
                    "JINO$i"))
        }
        v_vote_team.itemSize = ViewUtils.dipToPx(22f)
        v_vote_team.setData(list)


        v_vote_team1.leftToRight = false
        v_vote_team1.itemSize = ViewUtils.dipToPx(22f)
        v_vote_team1.setData(list)


        mAdapter = MultiTypeAdapter(data)
        rv_test.adapter = mAdapter
        rv_test.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        mAdapter.register(TestData::class.java, TestProvider())

        for (i in 0..0){
            data.add(TestData("JINO$i","https://avatars1.githubusercontent.com/u/5967952${i%10}"))
            mAdapter.notifyDataSetChanged()
        }

        val maxTextWidth = tv_loading.paint.measureText("投票结束")
        tv_loading.layoutParams.width = maxTextWidth.toInt()
        startSettleAnim(tv_loading)
    }


    private var mSettleAnim: ValueAnimator? = null

    private fun startSettleAnim(textView: TextView) {
        mSettleAnim = ValueAnimator.ofInt(4)
                .apply {
                    addUpdateListener { animation ->
                        var tailStr = ""
                        val curValue = animation.animatedValue as Int
                        for (i in 1..curValue) {
//                            if(curValue < i){
//                                tailStr += " "
//                            }else{
//                            }
                                tailStr += "."
                        }
                        tailStr += " "
                        val settleStr = "结算中" + tailStr
                        textView.text = settleStr
                        textView.gravity = Gravity.START
                    }
                    addListener(object :AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            textView.text = "投票结束"
                            textView.gravity = Gravity.CENTER
                        }
                    })
                    duration = 800
                    repeatCount = 5
                }
        mSettleAnim?.start()
    }

    data class TestData(val name: String, val avatar: String) : Item

    class TestProvider : ItemViewProvider<TestData, TestProvider.ViewHolder>() {


        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.item_vote_comptition_record, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, data: TestData) {
            holder.setData(data)
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

            fun setData(data: TestData) {
                view.tv_card_title.text = data.name
                Glide.with(view.context)
                        .load(data.avatar)
                        .into(view.iv_cover)
                view.rv_vote_record.apply {
//                    setHasFixedSize(false)
                    val linearLayoutManager = LinearLayoutManager(view.context,
                            LinearLayoutManager.VERTICAL, false)
//                    linearLayoutManager.isAutoMeasureEnabled = false
                    layoutManager = linearLayoutManager
                    val subData = mutableListOf<Item>()
                    for (i in 0..4){
                        subData.add(TestData2("JINO$i","https://avatars1.githubusercontent.com/u/5967952${i%10}"))
                    }
                    val multiTypeAdapter = MultiTypeAdapter(subData)
                    multiTypeAdapter.register(TestData2::class.java, TestProvider2())
                    adapter = multiTypeAdapter
                }
                view.setOnClickListener {
                    Toast.makeText(view.context, "JINO", Toast.LENGTH_SHORT).show()
                    if (view.rv_vote_record.visibility == View.VISIBLE) {
                        view.rv_vote_record.visibility = View.GONE
                    }else{
                        view.rv_vote_record.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    data class TestData2(val name: String, val avatar: String) : Item

    class TestProvider2 : ItemViewProvider<TestData2, TestProvider2.ViewHolder>() {


        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.item_vote_comptition_record, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, data: TestData2) {
            holder.setData(data)
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

            fun setData(data: TestData2) {
                view.setBackgroundColor(Color.BLUE)
                view.tv_card_title.text = data.name
                Glide.with(view.context)
                        .load(data.avatar)
                        .into(view.iv_cover)
            }
        }

    }


}