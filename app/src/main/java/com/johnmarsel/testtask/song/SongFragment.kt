package com.johnmarsel.testtask.song

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnmarsel.testtask.R
import com.johnmarsel.testtask.model.ItunesItem
import com.johnmarsel.testtask.databinding.FragmentSongBinding
import com.johnmarsel.testtask.databinding.ListItemSongsBinding
import com.johnmarsel.testtask.loadImage

const val ALBUM_ID = "album_id"
const val ALBUM_VIEW_URL = "album_view_url"
private const val DATE_FORMAT_FOOTER = "dd MMMM yyyy"

class SongFragment : Fragment() {

    private lateinit var songListViewModel: SongListViewModel
    private lateinit var binding: FragmentSongBinding
    private lateinit var albumViewUrl: String
    private lateinit var dateTimePattern: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songListViewModel =
            ViewModelProvider(this).get(SongListViewModel::class.java)
        songListViewModel.fetchSongs(arguments?.getSerializable(ALBUM_ID) as Int)
        albumViewUrl = arguments?.getSerializable(ALBUM_VIEW_URL) as String
        dateTimePattern = DateFormat.getBestDateTimePattern(resources.configuration.locale,
            DATE_FORMAT_FOOTER
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        binding.apply {
            songRecyclerView.layoutManager = LinearLayoutManager(context)
            albumImageView.loadImage(albumViewUrl)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""

        songListViewModel.songsList.observe(
            viewLifecycleOwner,
            { songList ->
                updateUI(songList)
            })
    }

    private fun updateUI(songList: List<ItunesItem>) {
        binding.apply {
            toolbar.title = songList[0].collectionName
            albumName.text = songList[0].collectionName
            collectionType.text = songList[0].collectionType
            releaseDate.text = DateFormat.format(
                "yyyy",
                songList[0].releaseDate
            ).toString()
            songRecyclerView.adapter = AlbumAdapter(songList.subList(1, songList.size)
                .sortedWith { a, b ->
                String.CASE_INSENSITIVE_ORDER.compare(
                    a.trackName,
                    b.trackName
                )
            })
            releaseDateFooter.text = DateFormat.format(dateTimePattern,
                songList[0].releaseDate)
        }

        var trackTimeMillis: Long = 0
        songList.forEach {
            trackTimeMillis += it.trackTimeMillis
        }
        val minutes = (trackTimeMillis/1000%3600)/60
        val hours = (trackTimeMillis/1000/3600)
        binding.tracksInfo.text = if (hours.toInt() == 0) {
            getString(R.string.album_info_without_hours, songList[0].trackCount, minutes)
        } else {
            getString(R.string.album_info, songList[0].trackCount, hours, minutes)
        }
    }

    private inner class SongHolder(val binding: ListItemSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: ItunesItem) {
            binding.apply {
                textViewSongName.text = song.trackName
                textViewArtistName.text = song.artistName
            }
        }
    }

    private inner class AlbumAdapter(private val songList: List<ItunesItem>) :
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
}