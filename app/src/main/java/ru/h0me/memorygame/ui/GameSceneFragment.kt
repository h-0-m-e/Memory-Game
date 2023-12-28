package ru.h0me.memorygame.ui

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.h0me.memorygame.R
import ru.h0me.memorygame.databinding.GameSceneFragmentBinding
import ru.h0me.memorygame.viewmodel.GameViewModel

class GameSceneFragment : Fragment() {

    private val matchesForWin = 8

    private var matches = MutableLiveData(0)
    private var firstTap = true
    private var firstButtonShape = 0
    private var firstButton: ImageView? = null
    private var secondButtonShape = 0
    private var secondButton: ImageView? = null

    private var currentBackDrawable = R.drawable.back

    private val viewModel: GameViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = GameSceneFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        val chronometer = binding.gameChronometer
        var isChronometerStarted = false

        viewModel.currentBalance.observe(viewLifecycleOwner) {
            binding.balanceText.text = it.toString()
        }

        viewModel.catMode.observe(viewLifecycleOwner) {
            if (it == true) {
                currentBackDrawable = R.drawable.cat_mode_back
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@GameSceneFragment.requireContext(), R.color.cat_mode_background
                    )
                )
                binding.backToMenuButton.setImageResource(R.drawable.cat_mode_back_button_48)
                binding.balanceIcon.setImageResource(R.drawable.cat_mode_coin_24)
                binding.timeIcon.setImageResource(R.drawable.cat_mode_time_24)
            } else {
                currentBackDrawable = R.drawable.back
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@GameSceneFragment.requireContext(), R.color.white
                    )
                )
                binding.backToMenuButton.setImageResource(R.drawable.back_button_48)
                binding.balanceIcon.setImageResource(R.drawable.coin_24)
                binding.timeIcon.setImageResource(R.drawable.time_24)
            }
        }

        var matchMap: Map<ImageView, Int> = mapOf(
            (binding.button1 to R.drawable.shape_1),
            (binding.button2 to R.drawable.shape_1),
            (binding.button3 to R.drawable.shape_2),
            (binding.button4 to R.drawable.shape_2),
            (binding.button5 to R.drawable.shape_3),
            (binding.button6 to R.drawable.shape_3),
            (binding.button7 to R.drawable.shape_4),
            (binding.button8 to R.drawable.shape_4),
            (binding.button9 to R.drawable.shape_5),
            (binding.button10 to R.drawable.shape_5),
            (binding.button11 to R.drawable.shape_6),
            (binding.button12 to R.drawable.shape_6),
            (binding.button13 to R.drawable.shape_7),
            (binding.button14 to R.drawable.shape_7),
            (binding.button15 to R.drawable.shape_8),
            (binding.button16 to R.drawable.shape_8)
        )

        matches.observe(viewLifecycleOwner) {
            if (it == matchesForWin) {
                val time = SystemClock.elapsedRealtime() - chronometer.base
                chronometer.stop()
                viewModel.calcReward(time)
                findNavController().navigate(R.id.action_gameSceneFragment_to_endGameFragment)
            }
        }

        matchMap = matchMap.keys.zip(viewModel.currentModeMap.shuffled()).toMap()

        for (view in matchMap.keys) {
            if (viewModel.catMode.value == true) {
                view.setImageResource(R.drawable.cat_mode_back)
            } else {
                view.setImageResource(R.drawable.back)
            }
            view.setOnClickListener {
                if (!isChronometerStarted) {
                    chronometer.start()
                    isChronometerStarted = true
                }
                if (secondButton == null) {
                    tap(it, matchMap)
                }
            }
        }

        binding.backToMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameSceneFragment_to_mainMenuFragment)
        }

        return binding.root

    }

    private fun tap(view: View, map: Map<ImageView, Int>) {
        if (firstTap) {
            firstButton = view as ImageView
            firstTap = false
            firstButtonShape = map[view]!!
            this@GameSceneFragment.requireActivity()
                .findViewById<ImageView>(firstButton!!.id)
                .isClickable = false
            this@GameSceneFragment.requireActivity()
                .findViewById<ImageView>(firstButton!!.id)
                .setImageResource(firstButtonShape)
        } else {
            firstTap = true
            secondButton = view as ImageView
            secondButtonShape = map[view]!!
            this@GameSceneFragment.requireActivity()
                .findViewById<ImageView>(view.id)
                .setImageResource(secondButtonShape)
            this@GameSceneFragment.requireActivity()
                .findViewById<ImageView>(secondButton!!.id)
                .isClickable = false
            if (secondButtonShape == firstButtonShape) {
                matches.value = matches.value?.plus(1)
                firstButton = null
                secondButton = null

            } else {
                this@GameSceneFragment.requireActivity()
                    .findViewById<ImageButton>(R.id.backToMenuButton).isClickable = false
                lifecycleScope.launch {
                    delay(1000)
                        this@GameSceneFragment.requireActivity()
                            .findViewById<ImageView>(firstButton!!.id)
                            .setImageResource(currentBackDrawable)
                        this@GameSceneFragment.requireActivity()
                            .findViewById<ImageView>(secondButton!!.id)
                            .setImageResource(currentBackDrawable)
                        this@GameSceneFragment.requireActivity()
                            .findViewById<ImageView>(firstButton!!.id)
                            .isClickable = true
                        this@GameSceneFragment.requireActivity()
                            .findViewById<ImageView>(secondButton!!.id)
                            .isClickable = true
                        firstButton = null
                        secondButton = null
                    this@GameSceneFragment.requireActivity()
                        .findViewById<ImageButton>(R.id.backToMenuButton).isClickable = true
                }

            }
            firstButtonShape = 0
            secondButtonShape = 0
        }
    }

}
