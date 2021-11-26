package com.johnmarsel.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johnmarsel.testtask.album.AlbumFragment
import com.johnmarsel.testtask.song.SongFragment

class ItunesActivity : AppCompatActivity(), AlbumFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, AlbumFragment.newInstance())
                .commit()
        }
    }

    override fun onAlbumSelected(collectionId: Int) {
        val fragment = SongFragment.newInstance(collectionId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}