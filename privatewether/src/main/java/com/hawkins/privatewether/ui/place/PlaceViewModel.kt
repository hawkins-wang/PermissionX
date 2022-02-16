package com.hawkins.privatewether.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hawkins.privatewether.logic.Repository
import com.hawkins.privatewether.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlace(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}