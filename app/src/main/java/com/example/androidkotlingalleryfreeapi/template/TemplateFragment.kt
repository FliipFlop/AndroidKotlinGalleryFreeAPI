package com.example.androidkotlingalleryfreeapi.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidkotlingalleryfreeapi.R

class TemplateFragment : Fragment() {

    companion object {
        fun newInstance(): TemplateFragment {
            val fragment = TemplateFragment()
            val args = Bundle()
            // bundle.put...
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // init argument from newInstance to someVar
        // someVar = arguments.get...

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_template , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Do anythings
    }


}