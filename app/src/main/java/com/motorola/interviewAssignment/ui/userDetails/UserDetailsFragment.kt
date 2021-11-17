package com.motorola.interviewAssignment.ui.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.motorola.interviewAssignment.R
import com.motorola.interviewAssignment.domain.models.UserDetails
import com.motorola.interviewAssignment.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private val args: UserDetailsFragmentArgs by navArgs()

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val userDetails = args.userDetails

        initView(userDetails)

        return view
    }

    private fun initView(userDetails: UserDetails) {
        binding.userName.text = userDetails.name

        Glide.with(binding.userImage.context)
            .load(userDetails.userPictureUrl)
            .thumbnail(
                Glide.with(binding.userImage.context).load(userDetails.userThumbnailPictureUrl)
            )
            .error(R.drawable.image_error)
            .placeholder(R.drawable.image_loading)
            .into(binding.userImage)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.daysTillNextBirthdayFlow(userDetails.birthday).collectLatest { daysTillBirthday ->
                val string = "$daysTillBirthday days till next birthday"
                binding.birthdayCountdown.text = string
            }
        }
    }


}