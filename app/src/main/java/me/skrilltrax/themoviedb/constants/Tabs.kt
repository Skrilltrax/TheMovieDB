package me.skrilltrax.themoviedb.constants

enum class Tabs(val tabId: Int, val tabName: String) {

    TAB_POPULAR(0, "Popular"),
    TAB_PLAYING(1, "Now Playing"),
    TAB_UPCOMING(2, "Upcoming"),
    TAB_TOP_RATED(3, "Top Rated");

    companion object {
        fun getTabById(id: Int): Tabs? {
            for (tab in values()) {
                if (tab.tabId == id) {
                    return tab
                }
            }
            return null
        }

        fun getTabByName(name: String): Tabs? {
            for (tab in values()) {
                if (tab.tabName == name) {
                    return tab
                }
            }
            return null
        }
    }
}
