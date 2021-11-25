package com.johnmarsel.testtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.johnmarsel.testtask.databinding.FragmentAlbumBinding

class AlbumFragment: Fragment() {

    lateinit var binding: FragmentAlbumBinding
    lateinit var albumListViewModel: AlbumListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        albumListViewModel =
            ViewModelProvider(this).get(AlbumListViewModel::class.java)
        albumListViewModel.getAlbums()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }
}