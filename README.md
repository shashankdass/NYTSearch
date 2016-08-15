# NYTSearch
# Project 2 - *NYTSearch*

**NYTSearch** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

* [yes ] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [ yes] User can click on "settings" which allows selection of **advanced search options** to filter results
* [ yes] User can configure advanced search filters such as:
  * [ yes] Begin Date (using a date picker)
  * [ yes] News desk values (Arts, Fashion & Style, Sports)
  * [ yes] Sort order (oldest or newest)
* [yes ] Subsequent searches have any filters applied to the search results
* [ yes] User can tap on any image in results to see the full text of article **full-screen**
* [ yes] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [ yes] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ yes] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [ yes] User can **share an article link** to their friends or email it to themselves
* [ yes] Replaced Filter Settings Activity with a lightweight modal overlay
* [yes ] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [ yes] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [ yes] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [ no] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [yes ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ yes] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [yes ] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ yes] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [no ] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [ no] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ no] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.

The following **additional** features are implemented:

* Added Item Decorator for better spacing between articles. 
* Added Animators to Recycler view items.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/kfjAueM.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Glide]

## License

    Copyright [2016] [Shashank Dass]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
