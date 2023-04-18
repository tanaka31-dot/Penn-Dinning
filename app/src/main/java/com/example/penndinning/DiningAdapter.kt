package com.example.penndinning

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.penndinning.databinding.DiningItemBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.*


class DiningAdapter(private val context: Context, private val dinings: MutableList<Dining>):
    RecyclerView.Adapter<DiningAdapter.DiningViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiningViewHolder {
        val binding = DiningItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DiningViewHolder(binding)
    }

    override fun getItemCount() = dinings.size

    override fun onBindViewHolder(holder: DiningViewHolder, position: Int) {

        with(holder) {
            with(dinings[position]) {
                binding.textBoxContainer.removeAllViews()
                binding.diningNameRv.text = name
                Picasso.get().load(image).resize(250, 200)
                    .transform(RoundedCornersTransformation(10, 0))
                    .into(binding.diningImageRv)
                val today = days?.get(0)
                val currentDateTime = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter2 = SimpleDateFormat("HH:mm")
                val formattedDateTime = formatter.format(currentDateTime)
                val formattedTime = formatter2.format(currentDateTime)
                with(today) {
                    val daypartsSize = this?.dayparts?.size
                    var isOpen = false;
                    for (i in 0 until daypartsSize!!) {
                        val part = this?.dayparts?.get(i)
                        with(part) {
                            //Populate textboxes with opening times
                            val textBoxStart = this?.starttime?.substring(11, 16)
                            val textBoxEnd = this?.endtime?.substring(11,16)
                            val stringText = "$textBoxStart-$textBoxEnd"
                            val textBox = TextView(context)
                            textBox.text = stringText
                            textBox.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                            ).apply {
                                marginEnd = 16
                            }

                            val startTime = formatter2.parse(textBoxStart)
                            val endTime = formatter2.parse(textBoxEnd)
                            val currTime = formatter2.parse(formattedTime)

                            if (currTime.after(startTime) && currTime.before(endTime)) {
                                textBox.setBackgroundColor(Color.parseColor("#7cc497"))
                                textBox.setPadding(16, 10, 16, 10)
                                textBox.setTextColor(Color.WHITE)
                                textBox.outlineProvider = object : ViewOutlineProvider() {
                                    override fun getOutline(view: View, outline: Outline) {
                                        outline.setRoundRect(0, 0, view.width, view.height, 16f)
                                    }
                                }
                                textBox.clipToOutline = true
                            } else {
                            textBox.setBackgroundColor(Color.parseColor("#d9d9d9"))
                            textBox.setPadding(16, 10, 16, 10)

                            textBox.outlineProvider = object : ViewOutlineProvider() {
                                override fun getOutline(view: View, outline: Outline) {
                                    outline.setRoundRect(0, 0, view.width, view.height, 16f)
                                }
                            }
                            textBox.clipToOutline = true
                            }

                            binding.textBoxContainer.addView(textBox)
                            // Get dates
                            val start = formatter.parse(this?.starttime)
                            val end = formatter.parse(this?.endtime)
                            val checkTime = formatter.parse(formattedDateTime)


                            // Compare dates with current time
                            if (checkTime.time > start.time && checkTime.time < end.time) {
                                binding.statusRv.text = this?.label
                                binding.statusRv.setTextColor(Color.parseColor("#3dcc61"))
                                isOpen = true
                            }

                        }
                        }
                    if (!isOpen) {
                        binding.statusRv.text = "Closed"
                        binding.statusRv.setTextColor(Color.GRAY)
                    }
                }
            }
        }

    }

//    private fun mapHours(hour: String): String {
//        println(hour)
//        when(hour) {
//            "13" -> return "1"
//            "14" -> return "2"
//            "15" -> return "3"
//            "16" -> return "4"
//            "17" -> return "5"
//            "18" -> return "6"
//            "19" -> return "7"
//            "20" -> return "8"
//            "21" -> return "9"
//            "22" -> return "10"
//            "23" -> return "11"
//            "24" -> return "12"
//        }
//        return hour
//    }

    inner class DiningViewHolder(val binding: DiningItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}