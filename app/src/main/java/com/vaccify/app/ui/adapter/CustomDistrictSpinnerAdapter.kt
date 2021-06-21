package com.vaccify.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.vaccify.app.R
import com.vaccify.app.model.res.DistrictListResModel

class CustomDistrictSpinnerAdapter(
    val context: Context,
    private var districtListResModel: List<DistrictListResModel>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.state_list_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = districtListResModel[position].district_name



        return view
    }

    override fun getItem(position: Int): Any {
        return districtListResModel[position];
    }

    override fun getCount(): Int {
        return districtListResModel.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.stateNameTv) as TextView

    }

}