package com.tim.coroutines.ui.home

import LoginViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tim.coroutines.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome

        loginViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.buttonLogin.setOnClickListener {

            // Give Exception for "username" can trigger the exception
//            loginViewModel.login("Exception", "password") { isSuccess ->
//                Log.d("Tim", "isSuccess: " + isSuccess)
//            }
            loginViewModel.fetchPlatformToken(SocialMedia.FACEBOOK)
        }

        binding.buttonTwitterLogin.setOnClickListener {
            loginViewModel.fetchPlatformToken(SocialMedia.TWITTER)
        }

        binding.buttonCancel.setOnClickListener {
            loginViewModel.cancel()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}