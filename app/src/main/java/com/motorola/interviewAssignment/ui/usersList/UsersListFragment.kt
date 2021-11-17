package com.motorola.interviewAssignment.ui.usersList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.databinding.FragmentUsersListBinding
import com.motorola.interviewAssignment.domain.models.UserItemComperator
import com.motorola.interviewAssignment.ui.base.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: UsersListViewModel by viewModels()

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        val view = binding.root

        initView()

        viewModel.loadUsers()

        return view
    }

    private fun initView() {
        initRecyclerView()

        // Hide or revel no network notification text
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.networkAvailableFlow.collectLatest { networkAvailable ->
                if (networkAvailable) {
                    binding.networkAvailability.visibility = View.GONE
                } else {
                    binding.networkAvailability.visibility = View.VISIBLE
                }
            }
        }

        // Set swipe down to refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.refreshList()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorFlow.collectLatest { error ->
                if (error) {
                    Toast.makeText(
                        context,
                        "An Error acquired while fetching data from server",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.requestErrorRegistered()
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkForNetworkAvailability()
    }

    private fun checkForNetworkAvailability() {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(viewModel.onNetWorkChange())
    }

    private fun initRecyclerView() {
        recyclerView = binding.usersList

        val pagingAdapter = UsersListAdapter(diffCallback = UserItemComperator)

        recyclerView.adapter = pagingAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        //Set recyclerview on click and long click listeners
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val userId = pagingAdapter.peek(position)?.id
                        viewLifecycleOwner.lifecycleScope.launch {
                            val userDetails = viewModel.getUserDetailsById(userId)
                            userDetails?.let {
                                val action = UsersListFragmentDirections
                                    .actionUsersListFragmentToUserDetailsFragment(userDetails)
                                binding.root.findNavController().navigate(action)
                            }
                        }
                    }
                },
                object : RecyclerItemClickListener.OnItemLongClickListener {
                    override fun onItemLongClick(position: Int) {
                        val userEmail = pagingAdapter.peek(position)?.email
                        userEmail?.let { composeEmail(userEmail) }
                    }
                })
        )

        // Submit data changes to the recyclerview
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun composeEmail(addresses: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(addresses))
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
        }
        activity?.packageManager?.let {
            if (intent.resolveActivity(it) != null) {
                startActivity(intent)
            }
        }
    }
}