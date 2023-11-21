package com.example.boredombusterapp

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.boredombusterapp.database.SharedPreferencesHelper
import com.example.boredombusterapp.repo.Repository
import com.example.boredombusterapp.utils.NetworkCallbackListener
import com.example.boredombusterapp.utils.NetworkUtils
import com.example.boredombusterapp.viewmodel.MainViewModel
import com.example.boredombusterapp.viewmodel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

/** Starting point of the App. */
class MainActivity : AppCompatActivity() {

    /** ViewModel to fetch the API result. */
    private lateinit var viewModel: MainViewModel

    /**
     * The ProgressBar used to indicate loading or progress and it should be visible when we are
     * fetching the data from API.
     */
    private lateinit var progressBar: ProgressBar

    /** TextView to display the latest activity. */
    private lateinit var activityNameTV: TextView

    /**
     * Button to make the API call if the internet is connected and it should retrieve the last
     * response saved in local storage if the internet is disconnected.
     */
    private lateinit var fetchButton: Button

    private lateinit var connectivityManager: ConnectivityManager

    /** Listener variable to show a Snackbar whenever the internet connectivity status changes. */
    private lateinit var networkCallbackListener: NetworkCallbackListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showInitialSnackbar()
        setupViews()
        setupViewModel()
        setClickListeners()
    }

    /** This method will initially show a Snackbar based on internet connection status. */
    private fun showInitialSnackbar() {
        if (isInternetConnected()) {
            showSnackbar("Internet Connected")
        } else {
            showSnackbar("Internet Disconnected")
        }
    }

    /** This method will return true if internet is connected, otherwise false. */
    private fun isInternetConnected(): Boolean {
        return NetworkUtils.isInternetConnected(this)
    }

    /** This method will initialize the views associated with [MainActivity]. */
    private fun setupViews() {
        progressBar = findViewById(R.id.progress_layout)
        activityNameTV = findViewById(R.id.activityNameTV)
        fetchButton = findViewById(R.id.fetchButton)
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallbackListener = NetworkCallbackListener(
            onInternetConnected = { showSnackbar("Internet Connected") },
            onInternetDisconnected = { showSnackbar("Internet Disconnected") }
        )
    }

    /**
     * This method will set up the [viewModel] and adds an observer for Activity Data and Api request
     * status.
     */
    private fun setupViewModel() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "MyActivityData",
            Context.MODE_PRIVATE
        )
        val repo = Repository(SharedPreferencesHelper(sharedPreferences))
        val viewModelFactory = MainViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.activityLiveData.observe(this) { activityData ->
            if (activityData.activity != null) {
                Log.d("Debug", "Got non-null result, activity: ${activityData.activity}")
                setText(activityData.activity)
            } else {
                Log.d("Debug", "Got null result")
                setText("Error in fetching activity data.")
                generateToast("Error in fetching activity Data.")
            }
            hideProgressBar()
        }
    }

    /** This method will display the latest activity data on [activityNameTV]. */
    private fun setText(activity: String) {
        activityNameTV.text = activity
    }

    /** This method will set click listeners and registers callbacks. */
    private fun setClickListeners() {
        fetchButton.setOnClickListener {
            setText("")
            showProgressBar()
            if (!isInternetConnected()) {
                generateToast("Fetching previous activity from database")
                viewModel.getPrevActivityData()
            } else {
                generateToast("Fetching latest Data from API")
                viewModel.fetchActivityData()
            }
        }
        networkCallbackListener.registerNetworkCallback(connectivityManager)
    }

    /** This method will show Snackbar. */
    private fun showSnackbar(message: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    /** This method will show the loading Spinner. */
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    /** This method will hide the loading Spinner. */
    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    /** This method will show the toast. */
    private fun generateToast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the network callback to avoid memory leaks
        networkCallbackListener.unregisterNetworkCallback(connectivityManager)
    }
}