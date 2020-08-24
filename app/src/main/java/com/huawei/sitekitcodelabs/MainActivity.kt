package com.huawei.sitekitcodelabs


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchService
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //KEYWORD SEARCH
        keywordSearchButton.setOnClickListener {
            // Declare and Instantiate the SearchService object.
            val searchService: SearchService = SearchServiceFactory.create(this, "CgB6e3x9bApFAd9aKFQIVJVWnwOuDNsrxV2eWlBkAsEbrMj1N93nXj/B431reSUlNuntcFxpi3wVtyCwTtqPfqrr")
            // Create a request body.
            val request = TextSearchRequest()
            request.query = "Paris"
            val location = Coordinate(48.893478, 2.334595)
            request.location = location
            request.radius = 1000
            request.hwPoiType = HwLocationType.ENTERTAINMENT_PLACE
            request.countryCode = "FR"
            request.language = "fr"
            request.pageIndex = 1
            request.pageSize = 5
            // Create a search result listener.
            val resultListener: SearchResultListener<TextSearchResponse?> = object : SearchResultListener<TextSearchResponse?> {
                // Return search results upon a successful search.
                override fun onSearchResult(results: TextSearchResponse?) {
                    if (results == null || results.totalCount <= 0) {
                        return
                    }
                    val sites = results.sites
                    if (sites == null || sites.size == 0) {
                        return
                    }
                    for (site in sites) {
                        Log.i("TAG", String.format("siteId: '%s', name: %s\r\n", site.siteId, site.name))
                    }
                }
                // Return the result code and description upon a search exception.
                override fun onSearchError(status: SearchStatus) {
                    Log.i("TAG2", "Error : " + status.errorCode + " " + status.errorMessage)
                }
            }
            // Call the place search API.
            searchService.textSearch(request, resultListener)
        }

        //PLACE DETAIL SEARCH
        placeDetailSearchButton.setOnClickListener {
            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();

            // Declare and Instantiate the SearchService object.
            val searchService: SearchService = SearchServiceFactory.create(this, "CgB6e3x9bApFAd9aKFQIVJVWnwOuDNsrxV2eWlBkAsEbrMj1N93nXj/B431reSUlNuntcFxpi3wVtyCwTtqPfqrr")
            // Create a request body.
            val request = DetailSearchRequest()
            request.siteId = "C2B922CC4651907A1C463127836D3957"
            request.language = "fr"
            // Create a search result listener.
            val resultListener: SearchResultListener<DetailSearchResponse?> = object : SearchResultListener<DetailSearchResponse?> {
                    // Return search results upon a successful search.
                    override fun onSearchResult(result: DetailSearchResponse?) {
                        var site = result?.site
                        if (result == null || result.site.also { site = it } == null) {
                            return
                        }
                        Log.i("TAG", String.format("siteId: '%s', name: %s\r\n", site?.siteId, site?.name))
                    }
                    // Return the result code and description upon a search exception.
                    override fun onSearchError(status: SearchStatus) {
                        Log.i("TAG", "Error : " + status.errorCode + " " + status.errorMessage)
                    }
                }
            // Call the place details search API.
            searchService.detailSearch(request, resultListener)
        }

        //PLACE SEARCH SUGGESTION SEARCH
        placeSearchSuggestionButton.setOnClickListener {
            // Declare and Instantiate the SearchService object.
            val searchService: SearchService = SearchServiceFactory.create(this, "CgB6e3x9bApFAd9aKFQIVJVWnwOuDNsrxV2eWlBkAsEbrMj1N93nXj/B431reSUlNuntcFxpi3wVtyCwTtqPfqrr")
            // Create a request body.
            val request = QuerySuggestionRequest()
            request.query = "Paris"
            val location = Coordinate(48.893478, 2.334595)
            request.location = location
            request.radius = 50
            request.countryCode = "FR"
            request.language = "fr"
            // Create a search result listener.
            val resultListener: SearchResultListener<QuerySuggestionResponse?> = object : SearchResultListener<QuerySuggestionResponse?> {
                    // Return search results upon a successful search.
                    override fun onSearchResult(results: QuerySuggestionResponse?) {
                        val sites = results?.sites
                        if (sites == null || sites.size <= 0) {
                            return
                        }
                        for (site in sites) {
                            Log.i("TAG", String.format("siteId: '%s', name: %s\r\n", site.siteId, site.name))
                        }
                    }

                    // Return the result code and description upon a search exception.
                    override fun onSearchError(status: SearchStatus) {
                        Log.i("TAG", "Error : " + status.errorCode + " " + status.errorMessage)
                    }
                }
            // Call the place search suggestion API.
            searchService.querySuggestion(request, resultListener)
        }

    }
}