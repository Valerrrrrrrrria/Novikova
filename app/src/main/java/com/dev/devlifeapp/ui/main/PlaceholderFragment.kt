package com.dev.devlifeapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.devlifeapp.R
import com.dev.devlifeapp.common.Common
import com.dev.devlifeapp.databinding.FragmentMainBinding
import com.dev.devlifeapp.interfaces.RetrofitServices
import com.dev.devlifeapp.model.Life
import com.dev.devlifeapp.model.Topic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    lateinit var mService: RetrofitServices

    var topicsCache = HashMap<String, ArrayList<Topic>>()

    var viewdPosts = HashMap<String, Int>()

    var topic: String = "latest"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        pageViewModel.context = context

        val textView: TextView = binding.sectionLabel
//        pageViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        pageViewModel.index.observe(viewLifecycleOwner, Observer {
//            textView.text = it.toString()
            getAllLatestList(it)
        })

        binding.nextButton.setOnClickListener {
            if (topicsCache[topic]?.size!! - 1 > viewdPosts[topic]!!) {
                binding.nextButton.visibility = View.VISIBLE
                binding.backButton.visibility = View.VISIBLE

                viewdPosts[topic] = viewdPosts.get(topic)!! + 1

                val localtopic = topicsCache[topic]?.get(viewdPosts[topic]!!)

                if (localtopic != null) {
                    Log.i("INFOINFO", "${viewdPosts[topic]}")
                    downloadImage(localtopic)
                }

            } else {
                binding.nextButton.visibility = View.INVISIBLE
            }
        }

        binding.backButton.setOnClickListener {
            if (viewdPosts[topic]!! > 0) {
                binding.backButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.VISIBLE

                viewdPosts[topic] = viewdPosts.get(topic)!! - 1
                Log.i("INFOINFO", "${viewdPosts[topic]}")

                val localtopic = topicsCache[topic]?.get(viewdPosts[topic]!!)

                if (localtopic != null) {
                    downloadImage(localtopic)
                }

            } else {
                binding.backButton.visibility = View.INVISIBLE
            }
        }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAllLatestList(position: Int) {
        mService = Common.retrofitService

        topic = when (position) {
            1 -> "latest"
            2 -> "hot"
            else -> "top"
        }

        if (!topicsCache.containsKey(topic))
            topicsCache[topic] = ArrayList()

        if (!viewdPosts.containsKey(topic))
            viewdPosts[topic] = 0

        mService.getPostsList(topic).enqueue(object : Callback<Life> {
            override fun onFailure(call: Call<Life>, t: Throwable) {
                binding.imageView.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
                binding.errorTextView.text =
                    "Произошла ошибка при загрузке данных. Проверьте подключение к сети."
            }

            override fun onResponse(call: Call<Life>, response: Response<Life>) {

                if (response.body()?.result?.size != 0) {

                    var url = response.body()?.result?.get(0)?.gifURL.toString()
                    var description = response.body()?.result?.get(0)?.description!!

                    for (post in response.body()?.result!!) {

                        topicsCache[topic]?.add(Topic(post.gifURL, post.description))

                    }

                    binding.descriptionTextView.text = description

                    Glide.with(this@PlaceholderFragment)
                        .load(url)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imageView)

                } else {
                    binding.nextButton.visibility = View.INVISIBLE
                    binding.imageView.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.errorTextView.text = "Данные отсутствуют, попробуйте позже."
                }
            }
        })
    }

    fun downloadImage(localtopic: Topic) {

        binding.descriptionTextView.text = localtopic.description

        Log.i("INFOINFO", "${viewdPosts[topic]}")

        Glide.with(this@PlaceholderFragment)
            .load(localtopic.imageUrl)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imageView)
    }
}