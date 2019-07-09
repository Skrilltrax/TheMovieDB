package me.skrilltrax.themoviedb.constants

enum class MovieTabs(val tabId: Int) {

    TAB_POPULAR(0),
    TAB_PLAYING(1),
    TAB_UPCOMING(2),
    TAB_TOP_RATED(3);

    companion object {

        fun getMovieTabById(id: Int): MovieTabs? {
            for (movieTab in values()) {
                if (movieTab.tabId == id) {
                    return movieTab
                }
            }
            return null
        }
    }
}