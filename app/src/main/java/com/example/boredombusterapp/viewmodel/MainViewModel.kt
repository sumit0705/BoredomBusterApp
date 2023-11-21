package com.example.boredombusterapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boredombusterapp.models.Activity
import com.example.boredombusterapp.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** Creates a new ViewModel instance. */
class MainViewModel(private val repo: Repository) : ViewModel() {

    /** MutableLiveData to hold the fetched activity data. */
    private val _activityLiveData = MutableLiveData<Activity>()

    /** Expose the LiveData for the UI to observe. */
    val activityLiveData: LiveData<Activity>
        get() = _activityLiveData

    /** This method will clear all the data from [repo] and fetch the latest activity data. */
    fun fetchActivityData() {
        repo.clearCache()
        getActivityData()
    }

    /** This method will fetch the Activity data from the API. */
    private fun getActivityData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Activity = repo.getActivity()
                _activityLiveData.postValue(response)
                response.activity?.let { activityData ->
                    saveDataToRepo(activityData)
                }
            } catch (e: Exception) {
                // Got error while fetching activity data e.g. changing internet connection status.
                e.printStackTrace()
                _activityLiveData.postValue(Activity("Got error in fetching activity!!"))
            }
        }
    }

    /** This method will fetch the Activity data from the database. */
    fun getPrevActivityData() {
        viewModelScope.launch(Dispatchers.IO) {
            val activityData = repo.getData(ACTIVITY_DATA)
            _activityLiveData.postValue(Activity(activityData ?: "Data doesn't exist in database!! "))
        }
    }

    /** This method will save the latest activity data in database via [repo]. */
    private fun saveDataToRepo(activityData: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveData(ACTIVITY_DATA, activityData)
        }
    }

    private companion object {
        const val ACTIVITY_DATA = "activity"
    }
}
