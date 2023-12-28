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
import ru.h0me.memorygame.databinding.SettingsFragmentBinding
import ru.h0me.memorygame.viewmodel.GameViewModel

class SettingsFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SettingsFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        var tapsOnPaw = 0

        viewModel.catMode.observe(viewLifecycleOwner) {
            if (it == true) {
                tapsOnPaw = 5
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@SettingsFragment.requireContext(), R.color.cat_mode_background
                    )
                )
                binding.paw.setImageResource(R.drawable.paw_5)
                binding.backButton.setImageResource(R.drawable.cat_mode_back_button_48)
                binding.enabledText.visibility = View.VISIBLE
            } else {
                tapsOnPaw
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@SettingsFragment.requireContext(), R.color.white
                    )
                )
                binding.paw.setImageResource(R.drawable.paw_0)
                binding.backButton.setImageResource(R.drawable.back_button_48)
                binding.enabledText.visibility = View.GONE
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.paw.setOnClickListener {
            if (tapsOnPaw != 5) {
                tapsOnPaw += 1
                when (tapsOnPaw) {
                    1 -> binding.paw.setImageResource(R.drawable.paw_1)
                    2 -> binding.paw.setImageResource(R.drawable.paw_2)
                    3 -> binding.paw.setImageResource(R.drawable.paw_3)
                    4 -> binding.paw.setImageResource(R.drawable.paw_4)
                    5 -> binding.paw.setImageResource(R.drawable.paw_5)
                }
            } else {
                viewModel.catModeOff()
            }
            if (tapsOnPaw == 5) {
                viewModel.catModeOn()
            }
        }


        return binding.root
    }
}
