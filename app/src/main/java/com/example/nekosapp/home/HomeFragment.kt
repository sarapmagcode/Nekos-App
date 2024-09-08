package com.example.nekosapp.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nekosapp.R
import com.example.nekosapp.adapter.RandomImageAdapter
import com.example.nekosapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Single observer for multiple properties:
        homeViewModel.homeViewModelProps.observe(viewLifecycleOwner) {
            binding.randomImagesRecyclerView.adapter =
                RandomImageAdapter(it.itemList ?: listOf(), it.status) { currentItem ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToSpecificImageFragment(currentItem.id!!)
                    this.findNavController().navigate(action)
                }

            when (it.status) {
                RandomImageStatus.LOADING -> binding.apiStatusImageView.visibility = View.VISIBLE
                RandomImageStatus.DONE -> binding.apiStatusImageView.visibility = View.GONE
                else -> binding.apiStatusImageView.visibility = View.VISIBLE
            }
        }

        binding.randomImagesRecyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}