package com.example.penndinning

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.penndinning.databinding.FragmentDiningDetailsBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


class DiningDetailsFragment : Fragment() {
    private var _binding: FragmentDiningDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DiningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiningDetailsBinding.inflate(inflater, container, false)
        val animationView = binding.animationView
        animationView.playAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DiningViewModel::class.java]
        val position = DiningDetailsFragmentArgs.fromBundle(requireArguments()).id
        viewModel.response.observe(viewLifecycleOwner) {

        }

        val animationView = binding.animationView

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.hoursButton.visibility = View.GONE
                binding.menuButton.visibility = View.GONE
                animationView.visibility = View.VISIBLE
                animationView.playAnimation()
            } else {
                animationView.visibility = View.GONE
                binding.hoursButton.visibility = View.VISIBLE
                binding.menuButton.visibility = View.VISIBLE
                val dining = viewModel.getDiningsList()?.get(position)
                Picasso.get().load(dining?.image).fit()
                    .into(binding.diningImageBig)
                binding.diningNameDetails.text = dining?.name
                val openTimes = dining?.days?.get(0)
                with(openTimes) {
                    val daypartsSize = this?.dayparts?.size

                    for (i in 0 until daypartsSize!!) {
                        val part = this?.dayparts?.get(i)
                        with(part) {
                            val textBoxStart = this?.starttime?.substring(11, 16)
                            val textBoxEnd = this?.endtime?.substring(11, 16)
                            val stringText = "$textBoxStart - $textBoxEnd"

                            val textBoxMeal = TextView(context)
                            val textBoxTime = TextView(context)

                            textBoxMeal.text = this?.label
                            textBoxTime.text = stringText
                            textBoxMeal.textSize = 15F
                            textBoxTime.textSize = 15F
                            textBoxMeal.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                            ).apply {
                                bottomMargin = 16
                            }

                            textBoxTime.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                            ).apply {
                                bottomMargin = 16
                            }

                            binding.mealNameBox.addView(textBoxMeal)
                            binding.mealTimeBox.addView(textBoxTime)
                        }

                    }
                }

                binding.menuButton.setOnClickListener {
                    val baseurl = "https://university-of-pennsylvania.cafebonappetit.com"
                    val diningurl = baseurl + mapDiningToURL(dining?.id)
                    val webpage: Uri = Uri.parse(diningurl)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(intent)
                    }
                }
                binding.hoursButton.setOnClickListener {
                    val bundle = bundleOf("id" to position)
                    view?.findNavController()?.navigate(
                        R.id.action_diningDetailsFragment_to_hoursFragment2, bundle
                    )
                }
            }
        }
    }

    fun mapDiningToURL(id: Int?): String {
        when (id) {
            593 -> return "/cafe/1920-commons/"
            636 -> return "/cafe/hill-house/"
            637 -> return "/cafe/kings-court-english-house/"
            638 -> return "/cafe/falk-dining-commons/"
            639 -> return "/cafe/houston-market/"
            641 -> return "/cafe/accenture-cafe/"
            642 -> return "/cafe/joes-cafe/"
            747 -> return "/cafe/mcclelland/"
            1057 -> return "/cafe/1920-gourmet-grocer/"
            1163 -> return "/cafe/1920-starbucks/"
            1442 -> return "/cafe/lauder-college-house/"
            1732 -> return "/cafe/pret-a-manger-upper/"
            1733 -> return "/cafe/pret-a-manger-lower/"
            1464004 -> return "/cafe/quaker-kitchen/"
            1464009 -> return "/cafe/cafe-west/"
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}