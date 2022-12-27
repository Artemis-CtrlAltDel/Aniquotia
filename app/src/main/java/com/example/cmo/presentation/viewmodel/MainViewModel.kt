package com.example.cmo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.repository.Repository
import com.example.cmo.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val TAG = "MainViewModel"

    private val compositeDisposable = CompositeDisposable()

    /** API **/
    private val _animeQuotesList: MutableLiveData<Resource<ArrayList<AnimeQuote>>> =
        MutableLiveData(Resource.Loading(arrayListOf()))
    val animeQuotesList get() = _animeQuotesList

    private val _animeRandomQuote: MutableLiveData<AnimeQuote> =
        MutableLiveData(null)
    val animeRandomQuote get() = _animeRandomQuote


    // 10 random quotes :
    fun getQuotes() {
        val observable =
            repository.getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotes: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByCharacter(character: String) {
        val observable =
            repository.getQuotesByCharacter(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotesByCharacter: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByAnime(anime: String) {
        val observable =
            repository.getQuotesByCharacter(anime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotesByCharacter: ${error.message}") }

        compositeDisposable.add(observable)
    }

    // 1 random quote :
    fun getRandomQuote() {
        val observable =
            repository.getRandomQuote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeRandomQuote.value = result })
                { error -> Log.e(TAG, "getRandomQuote: ${error.message}") }
    }

    fun getRandomQuoteByCharacter(character: String) {
        val observable =
            repository.getRandomQuoteByCharacter(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeRandomQuote.value = result })
                { error -> Log.e(TAG, "getRandomQuote: ${error.message}") }
    }

    fun getRandomQuoteByAnime(anime: String) {
        val observable =
            repository.getRandomQuoteByAnime(anime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> _animeRandomQuote.value = result })
                { error -> Log.e(TAG, "getRandomQuote: ${error.message}") }
    }

    /** Database **/
    private var _animeSavedQuotesList: LiveData<List<AnimeQuote>> =
        MutableLiveData(listOf())
    val animeSavedQuotesList get() = _animeSavedQuotesList

    fun insertQuote(quote: AnimeQuote) =
        repository.insertQuote(quote)

    fun deleteQuote(quote: AnimeQuote) =
        repository.deleteQuote(quote)

    fun getSavedQuotes() {
        _animeSavedQuotesList = repository.getSavedQuotes()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}