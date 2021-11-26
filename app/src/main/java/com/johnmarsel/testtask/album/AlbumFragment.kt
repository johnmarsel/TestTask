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
import com.bumptech.glide.Glide
import com.johnmarsel.testtask.R
import com.johnmarsel.testtask.api.Album
import com.johnmarsel.testtask.databinding.FragmentAlbumBinding
import com.johnmarsel.testtask.databinding.ListItemAlbumsBinding

private const val TAG = "AlbumFragment"

class AlbumFragment: Fragment() {

    interface Callbacks {
        fun onAlbumSelected(collectionId: Int)
    }

    private var callbacks: Callbacks? = null
    lateinit var binding: FragmentAlbumBinding
    lateinit var albumListViewModel: AlbumListViewModel
    lateinit var albumRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
        albumRecyclerView = binding.albumRecyclerView

        albumRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumListViewModel.albumsList.observe(
            viewLifecycleOwner,
            { albumList ->
                albumRecyclerView.adapter = AlbumAdapter(albumList)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_album, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    albumListViewModel.searchAlbums(queryText)
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
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

            private lateinit var album: Album
            init {
                binding.root.setOnClickListener(this)
            }

            fun bind(album: Album) {
                this.album = album
                binding.apply {
                    textViewAlbumName.text = album.collectionName
                    textViewSongName.text = android.text.format.DateFormat.format(
                        "yyyy",
                        album.releaseDate
                    ).toString()
                    Glide.with(binding.albumImageView.context)
                        .load(album.artworkUrl60)
                        .into(binding.albumImageView)


                }
            }

            override fun onClick(v: View?) {
                callbacks?.onAlbumSelected(album.collectionId)
            }
        }

        private inner class AlbumAdapter(private val albumList: List<Album>) :
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