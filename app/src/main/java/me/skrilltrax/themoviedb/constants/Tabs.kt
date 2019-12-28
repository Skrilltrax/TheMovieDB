package me.skrilltrax.themoviedb.constants

enum class Tabs(val tabId: Int) {

    TAB_POPULAR(0),
    TAB_PLAYING(1),
    TAB_UPCOMING(2),
    TAB_TOP_RATED(3);

    companion object {
        fun getTabById(id: Int): Tabs? {
            for (tab in values()) {
                if (tab.tabId == id) {
                    return tab
                }
            }
            return null
        }
    }
}
