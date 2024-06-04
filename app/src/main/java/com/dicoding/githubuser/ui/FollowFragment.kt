package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollowBinding

class FollowFragment: Fragment() {
    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    companion object{
        val POSITION = "position"
        val USERNAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val followersViewModel = ViewModelProvider(this ).get(FollowViewModel::class.java)
        arguments?.let {
            val position = it.getInt(POSITION)
            val username = it.getString(USERNAME)

            if (position == 1) {
                followersViewModel.getListFollowers(username.toString())
                followersViewModel.listFollowers.observe(viewLifecycleOwner) { ItemsItem ->
                    setUserData(ItemsItem)

                }
                val layoutManager = LinearLayoutManager(requireActivity())
                binding.rvfollows.layoutManager = layoutManager

                followersViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            } else {
                followersViewModel.getListFollowing(username.toString())
                followersViewModel.listFollowers.observe(viewLifecycleOwner) { ItemsItem ->
                    setUserData(ItemsItem)

                }
                val layoutManager = LinearLayoutManager(requireActivity())
                binding.rvfollows.layoutManager = layoutManager

                followersViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            }

        }

    }

    private fun setUserData(items: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvfollows.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

}