package com.example.penndinning

import android.graphics.Color
import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.penndinning.databinding.FragmentHoursBinding
import java.text.SimpleDateFormat
import java.util.*

class HoursFragment : Fragment() {
    private var _binding: FragmentHoursBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DiningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        val animationView = binding.animationView
        animationView.playAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DiningViewModel::class.java]
        val position = HoursFragmentArgs.fromBundle(requireArguments()).id

        viewModel.response.observe(viewLifecycleOwner) {

        }

        val animationView = binding.animationView
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.monday.visibility = View.GONE
                binding.tuesday.visibility = View.GONE
                binding.wednesday.visibility = View.GONE
                binding.thursday.visibility = View.GONE
                binding.friday.visibility = View.GONE
                binding.saturday.visibility = View.GONE
                binding.sunday.visibility = View.GONE
                animationView.visibility = View.VISIBLE
                animationView.playAnimation()
            } else {
                animationView.visibility = View.GONE
                val dining = viewModel.getDiningsList()?.get(position)

                binding.monday.visibility = View.VISIBLE
                binding.tuesday.visibility = View.VISIBLE
                binding.wednesday.visibility = View.VISIBLE
                binding.thursday.visibility = View.VISIBLE
                binding.friday.visibility = View.VISIBLE
                binding.saturday.visibility = View.VISIBLE
                binding.sunday.visibility = View.VISIBLE

                val openDays = dining?.days

                with(openDays) {
                    val size = this?.size
                    for (i in 0 until size!!) {
                        val day = openDays?.get(i)
                        val dayPartSize = day?.dayparts?.size

                        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val inputDate = inputDateFormat.parse(day?.date)

                        val outputDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
                        val dayOfWeek = outputDateFormat.format(inputDate)

                        for (i in 0 until dayPartSize!!) {
                            val part = day?.dayparts?.get(i)

                            with(part) {
                                val textBoxStart = this?.starttime?.substring(11, 16)
                                val textBoxEnd = this?.endtime?.substring(11, 16)
                                val stringText = "$textBoxStart - $textBoxEnd"

                                val textBoxTime = TextView(context)

                                textBoxTime.text = stringText

                                textBoxTime.textSize = 15F


                                textBoxTime.layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    1f
                                ).apply {
                                    bottomMargin = 16
                                    topMargin = 16
                                    rightMargin = 16
                                }
                                textBoxTime.setBackgroundColor(Color.parseColor("#d9d9d9"))
                                textBoxTime.setPadding(16, 10, 16, 10)

                                textBoxTime.outlineProvider = object : ViewOutlineProvider() {
                                    override fun getOutline(view: View, outline: Outline) {
                                        outline.setRoundRect(0, 0, view.width, view.height, 16f)
                                    }
                                }
                                textBoxTime.clipToOutline = true

                                when(dayOfWeek) {
                                    "Monday" -> binding.mondayContainer.addView(textBoxTime)
                                    "Tuesday" -> binding.tuesdayContainer.addView(textBoxTime)
                                    "Wednesday" -> binding.wednesdayContainer.addView(textBoxTime)
                                    "Thursday" -> binding.thursdayContainer.addView(textBoxTime)
                                    "Friday" -> binding.fridayContainer.addView(textBoxTime)
                                    "Saturday" -> binding.saturdayContainer.addView(textBoxTime)
                                    "Sunday" -> binding.sundayContainer.addView(textBoxTime)
                                }
                            }
                            //println("DAY OF THE WEEK: $dayOfWeek")
                        }
                    }
                }
            }
        }
    }
}