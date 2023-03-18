package com.example.cmo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.data.repository.QuotesRepository
import com.example.cmo.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: QuotesRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    /** API **/
    var animeQuotesList = MutableLiveData<Resource<ArrayList<Quote>>>()
        private set

    var animeSearchedQuotesList = MutableLiveData<Resource<ArrayList<Quote>>>()
        private set

    private val _animeRandomQuote: MutableLiveData<Quote> =
        MutableLiveData(null)
    val animeRandomQuote get() = _animeRandomQuote


    // 10 random quotes :
    fun getQuotes() {
        val observable =
            repository.getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> animeQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotes: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByCharacter(character: String) {
        val observable =
            repository.getQuotesByCharacter(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> animeSearchedQuotesList.value = result })
                { error -> Log.e(TAG, "getQuotesByCharacter: ${error.message}") }

        compositeDisposable.add(observable)
    }

    fun getQuotesByAnime(anime: String) {
        val observable =
            repository.getQuotesByCharacter(anime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> animeSearchedQuotesList.value = result })
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
    val animeSavedQuotesList = repository.getSavedQuotes()

    private fun insertQuote(quote: Quote) =
        repository.insertQuote(quote)

    private fun deleteQuote(quote: Quote) =
        repository.deleteQuote(quote)


    /** Shared data **/
    private var _sharedQuotesList = MutableLiveData<Pair<String, List<Quote>>>()
    val sharedQuotesList get() = _sharedQuotesList

    fun setCollectionQuotes(collectionQuotes: Pair<String, List<Quote>>) {
        _sharedQuotesList.value = collectionQuotes
    }

    /** Actions **/
    fun bookmarkQuote(quote: Quote) {
        quote.isBookmarked = !quote.isBookmarked
        quote.bookmarkCount += if (quote.isBookmarked) 1 else -1
        quote.savedAtTimestamp = System.currentTimeMillis()

        when (quote.isBookmarked) {
            true -> insertQuote(quote)
            else -> deleteQuote(quote)
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}