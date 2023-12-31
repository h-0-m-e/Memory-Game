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
import ru.h0me.memorygame.databinding.EndGameFragmentBinding
import ru.h0me.memorygame.util.TimeToStringCounter
import ru.h0me.memorygame.viewmodel.GameViewModel

class EndGameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = EndGameFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.lastReward.observe(viewLifecycleOwner) {
            binding.rewardValue.text = it.toString()
        }
        viewModel.lastRoundTimeInSec.observe(viewLifecycleOwner) {
            binding.time.text = TimeToStringCounter.count(it)
        }

        binding.playButton.setOnClickListener {
            viewModel.increaseBalance()
            findNavController().navigate(R.id.action_endGameFragment_to_gameSceneFragment)
        }

        binding.doubleRewardButton.setOnClickListener {
            viewModel.doubleBalance()
            it.isClickable = false
            it.setBackgroundColor(
                ContextCompat.getColor(
                    this@EndGameFragment.requireContext(), R.color.gray
                )
            )
        }

        binding.menuButton.setOnClickListener {
            viewModel.increaseBalance()
            findNavController().navigate(R.id.action_endGameFragment_to_mainMenuFragment)
        }

        return binding.root
    }
}
