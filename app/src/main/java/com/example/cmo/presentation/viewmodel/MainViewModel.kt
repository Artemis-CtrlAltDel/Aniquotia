package com.example.cmo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmo.data.network.AnimeQuotesApiService
import com.example.cmo.data.network.pojo.AnimeQuoteApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: AnimeQuotesApiService) : ViewModel() {

    private val TAG = "MainViewModel"

    private val compositeDisposable = CompositeDisposable()

    private val _animeQuotesList: MutableLiveData<ArrayList<AnimeQuoteApiResponse>> =
        MutableLiveData(
            arrayListOf()
        )
    val animeQuotesList: LiveData<ArrayList<AnimeQuoteApiResponse>> get() = _animeQuotesList

    private val _animeRandomQuote: MutableLiveData<AnimeQuoteApiResponse> = MutableLiveData(null)
    val animeRandomQuote: LiveData<AnimeQuoteApiResponse> get() = _animeRandomQuote

    // 10 random quotes :
    fun getQuotes() {
        val observable =
            api.getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotes: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByCharacter(character: String) {
        val observable =
            api.getQuotesByCharacter(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotesByCharacter: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByAnime(anime: String) {
        val observable =
            api.getQuotesByCharacter(anime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotesByCharacter: ${error.message}") }

        compositeDisposable.add(observable)
    }

    // 1 random quote :
    fun getRandomQuote(){
        val observable =
            api.getRandomQuote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> _animeRandomQuote.value = result})
                {error -> Log.e(TAG, "getRandomQuote: ${error.message}")}
    }

    fun getRandomQuoteByCharacter(character: String){
        val observable =
            api.getRandomQuoteByCharacter(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> _animeRandomQuote.value = result})
                {error -> Log.e(TAG, "getRandomQuote: ${error.message}")}
    }

    fun getRandomQuoteByAnime(anime: String){
        val observable =
            api.getRandomQuoteByAnime(anime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> _animeRandomQuote.value = result})
                {error -> Log.e(TAG, "getRandomQuote: ${error.message}")}
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}