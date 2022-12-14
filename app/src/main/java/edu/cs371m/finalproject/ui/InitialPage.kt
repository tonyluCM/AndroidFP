package edu.cs371m.finalproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.bumptech.glide.Glide

import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.R

import edu.cs371m.finalproject.databinding.InitialpageBinding

import edu.cs371m.finalproject.ui.categories.Categories

class InitialPage: Fragment() {
    // XXX initialize viewModel
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: InitialpageBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
        }
    companion object {
        fun newInstance(): InitialPage {
            return InitialPage()
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InitialpageBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(javaClass.simpleName, "onViewCreated")
        // XXX Write me
        var temp = (requireActivity() as MainActivity).findViewById<EditText>(R.id.actionSearch)
        temp.visibility = View.INVISIBLE
        binding.SignIn.setOnClickListener {
            AuthInit(viewModel,signInLauncher)
            parentFragmentManager.commit {
                replace(R.id.main_frame, Categories.newInstance())
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
        }

        binding.GuestIn.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.main_frame,Categories.newInstance())
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
        }
       /* binding.LogOut.setOnClickListener {
            viewModel.signOut()
        }*/

    }
}