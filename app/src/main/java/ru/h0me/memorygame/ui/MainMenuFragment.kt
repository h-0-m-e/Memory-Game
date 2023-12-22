package ru.h0me.memorygame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        viewModel.currentBalance.observe(viewLifecycleOwner) {
            binding.balanceText.text = it.toString()
        }
        viewModel.currentBestTimeInSec.observe(viewLifecycleOwner) {
            binding.bestTimeValue.text = TimeToStringCounter.count(it)
        }

        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameSceneFragment)
        }

        return binding.root
    }

}
