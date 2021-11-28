package com.johnmarsel.testtask.album

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnmarsel.testtask.model.ItunesItem
import com.johnmarsel.testtask.databinding.FragmentAlbumBinding
import com.johnmarsel.testtask.databinding.ListItemAlbumsBinding
import com.johnmarsel.testtask.loadImage

private const val TAG = "AlbumFragment"

class AlbumFragment: Fragment() {

    interface Callbacks {
        fun onAlbumSelected(collectionId: Int, collectionViewUrl: String)
    }

    private var callbacks: Callbacks? = null
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumListViewModel: AlbumListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumListViewModel =
            ViewModelProvider(this).get(AlbumListViewModel::class.java)
        albumListViewModel.searchAlbums("Metallica")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        binding.albumRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumListViewModel.albumList.observe(
            viewLifecycleOwner,
            { albumList ->
                (albumList as MutableList).sortWith { a, b ->
                    String.CASE_INSENSITIVE_ORDER.compare(
                        a.collectionName,
                        b.collectionName
                    )
                }
                binding.apply {
                    albumRecyclerView.adapter = AlbumAdapter(albumList)
                    queryText.text = searchView.query
                }
            })

        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    albumListViewModel.searchAlbums(queryText)
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })
        }
    }
        private inner class AlbumHolder(val binding: ListItemAlbumsBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            private lateinit var album: ItunesItem
            init {
                binding.root.setOnClickListener(this)
            }

            fun bind(album: ItunesItem) {
                this.album = album
                binding.apply {
                    albumImage.loadImage(album.artworkUrl100)
                    albumName.text = album.collectionName
                    releaseDate.text = android.text.format.DateFormat.format(
                        "yyyy",
                        album.releaseDate
                    ).toString()
                    artistName.text = album.artistName
                }
            }
            override fun onClick(v: View?) {
                callbacks?.onAlbumSelected(album.collectionId, album.artworkUrl100)
            }
        }

        private inner class AlbumAdapter(private val albumList: List<ItunesItem>) :
            RecyclerView.Adapter<AlbumHolder>() {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AlbumHolder {
                val binding = ListItemAlbumsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return AlbumHolder(binding)
            }

            override fun getItemCount(): Int = albumList.size

            override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
                val album = albumList[position]
                holder.bind(album)
            }
        }

    companion object {
        fun newInstance(): AlbumFragment {
            return AlbumFragment()
        }
    }
}