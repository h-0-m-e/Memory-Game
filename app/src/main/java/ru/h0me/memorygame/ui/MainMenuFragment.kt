package ru.h0me.memorygame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.h0me.memorygame.R
import ru.h0me.memorygame.util.TimeToStringCounter
import ru.h0me.memorygame.databinding.MainMenuFragmentBinding
import ru.h0me.memorygame.viewmodel.GameViewModel

class MainMenuFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainMenuFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.catMode.observe(viewLifecycleOwner){
            if (it == true){
                binding.root.setBackgroundColor( ContextCompat.getColor(
                    this@MainMenuFragment.requireContext(), R.color.cat_mode_background
                ))
                binding.playButton.setBackgroundColor( ContextCompat.getColor(
                    this@MainMenuFragment.requireContext(), R.color.cat_mode_button
                ))
                binding.settingsButton.setImageResource(R.drawable.cat_mode_settings_48)
                binding.privacyButton.setImageResource(R.drawable.cat_mode_privacy_tip_48)
                binding.balanceIcon.setImageResource(R.drawable.cat_mode_coin_24)
                binding.bestTimeIcon.setImageResource(R.drawable.cat_mode_time_24)
                binding.logo.setImageResource(R.drawable.cat_mode_logo)
            } else {
                binding.root.setBackgroundColor( ContextCompat.getColor(
                    this@MainMenuFragment.requireContext(), R.color.white
                ))
                binding.playButton.setBackgroundColor( ContextCompat.getColor(
                    this@MainMenuFragment.requireContext(), R.color.black
                ))
                binding.settingsButton.setImageResource(R.drawable.settings_48)
                binding.privacyButton.setImageResource(R.drawable.privacy_tip_48)
                binding.balanceIcon.setImageResource(R.drawable.coin_24)
                binding.bestTimeIcon.setImageResource(R.drawable.time_24)
                binding.logo.setImageResource(R.drawable.logo)
            }
        }

        viewModel.currentBalance.observe(viewLifecycleOwner) {
            binding.balanceText.text = it.toString()
        }
        viewModel.currentBestTimeInSec.observe(viewLifecycleOwner) {
            binding.bestTimeValue.text = TimeToStringCounter.count(it)
        }

        binding.settingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_settingsFragment)
        }

        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameSceneFragment)
        }

        return binding.root
    }

}
