package com.johnmarsel.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.johnmarsel.testtask.album.AlbumFragment
import com.johnmarsel.testtask.song.ALBUM_ID
import com.johnmarsel.testtask.song.ALBUM_VIEW_URL

class ItunesActivity : AppCompatActivity(), AlbumFragment.Callbacks {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onAlbumSelected(collectionId: Int, collectionViewUrl: String) {
        val args = Bundle().apply {
            putSerializable(ALBUM_ID, collectionId)
            putSerializable(ALBUM_VIEW_URL, collectionViewUrl)
        }
        navController.navigate(R.id.action_albumFragment_to_songFragment, args)
    }
}