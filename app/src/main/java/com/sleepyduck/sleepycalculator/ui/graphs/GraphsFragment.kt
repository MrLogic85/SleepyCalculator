package com.sleepyduck.sleepycalculator.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sleepyduck.sleepycalculator.R

class GraphsFragment : Fragment() {

    private lateinit var graphsViewModel: GraphsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        graphsViewModel = ViewModelProviders.of(this).get(GraphsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_graphs, container, false)
        return root
    }
}