package com.sitharaj.keyboardvisibility.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sitharaj.keyboardvisibility.keyboardVisibility

class KeyboardTestFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_keyboard_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        keyboardVisibility {
            onVisible {
                Toast.makeText(requireActivity(), "Keyboard is visible", Toast.LENGTH_SHORT).show()
            }

            onHidden {
                Toast.makeText(requireActivity(), "Keyboard is hidden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            KeyboardTestFragment()
    }
}