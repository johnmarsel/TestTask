package com.johnmarsel.testtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnmarsel.testtask.api.Album
import com.johnmarsel.testtask.api.Song
import com.johnmarsel.testtask.databinding.FragmentAlbumBinding
import com.johnmarsel.testtask.databinding.FragmentSongBinding
import com.johnmarsel.testtask.databinding.ListItemAlbumsBinding
import com.johnmarsel.testtask.databinding.ListItemSongsBinding
import java.util.*

const val ALBUM_ID = "album_id"

class SongFragment : Fragment() {

    lateinit var albumListViewModel: AlbumListViewModel
    lateinit var songRecyclerView: RecyclerView
    private lateinit var binding: FragmentSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        albumListViewModel =
            ViewModelProvider(this).get(AlbumListViewModel::class.java)
        albumListViewModel.getSongs(arguments?.getSerializable(ALBUM_ID) as Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        songRecyclerView = binding.songRecyclerView

        songRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumListViewModel.songsList.observe(
            viewLifecycleOwner,
            { songList ->
                songRecyclerView.adapter = AlbumAdapter(songList)
            })
    }

    private inner class SongHolder(val binding: ListItemSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.apply {
                textViewSongName.text = song.trackName
            }
        }
    }

    private inner class AlbumAdapter(private val songList: List<Song>) :
        RecyclerView.Adapter<SongHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SongHolder {
            val binding = ListItemSongsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return SongHolder(binding)
        }

        override fun getItemCount(): Int = songList.size

        override fun onBindViewHolder(holder: SongHolder, position: Int) {
            val song = songList[position]
            holder.bind(song)
        }
    }

    companion object {
        fun newInstance(album: Int): SongFragment {
            val args = Bundle().apply {
                putSerializable(ALBUM_ID, album)
            }
            return SongFragment().apply {
                arguments = args
            }
        }
    }


}