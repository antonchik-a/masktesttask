package com.regextest.android.ui

import android.content.Context
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import com.regextest.android.Const.LOG_TAG
import com.regextest.android.R
import com.regextest.android.entity.ParseResult




class ResultAdapter(var context: Context,var delegate: ResultClick) : BaseAdapter() {

    private var inflater: LayoutInflater
    private  var data: MutableList<ParseResult> = mutableListOf()
    private lateinit var holder: ViewHolder


    init {
        inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(LOG_TAG, "get view position $position")
        var view: View

        if (convertView == null) {

            view = inflater.inflate(R.layout.item_result, parent, false)

            holder = ViewHolder()
            holder.checkBox = view.findViewById(R.id.checkBox)
            holder.textView = view.findViewById(R.id.value)

            view.tag = holder

        }else{
            view = convertView!!
            holder = convertView.tag as ViewHolder
        }

        val result = data.get(position)
        holder.textView?.setText(result.value)
        holder.checkBox?.setOnCheckedChangeListener(null)
        holder.checkBox?.isChecked = result.isSelected
        holder.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
            result.isSelected = isChecked
            delegate?.checkedResult(position, isChecked)
        }

        if(position == data.size - 1){
            delegate?.loadMore(position)
        }

        return view
    }


    fun showData(resultsPage: MutableList<ParseResult>) {
        data.clear()
        data.addAll(resultsPage)
        notifyDataSetChanged()
    }

    fun appendData(resultsPage: MutableList<ParseResult>) {
        data.addAll(resultsPage)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun appendValue(results: ParseResult) {
        data.add(results)
        notifyDataSetChanged()
    }

    interface ResultClick{
        fun checkedResult(index: Int, isChecked: Boolean)
        fun loadMore(position: Int)
    }

    internal class ViewHolder() {
        lateinit var checkBox: CheckBox
        lateinit var textView: TextView
    }


}