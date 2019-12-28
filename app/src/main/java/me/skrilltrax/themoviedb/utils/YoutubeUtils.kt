package me.skrilltrax.themoviedb.utils

object YoutubeUtils {

    //  Size : 480x360
    fun getPlayerBackgroundThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/0.jpg"
    }

    //  Size : 120x90
    fun getPlayerStartThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/1.jpg"
    }

    //  Size : 120x90
    fun getPlayerMiddleThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/2.jpg"
    }

    //  Size : 120x90
    fun getPlayerEndThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/3.jpg"
    }

    //  Size : 480x360
    fun getHighQualityThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/hqdefault.jpg"
    }

    //  Size : 320x180
    fun getMediumQualityThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/mqdefault.jpg"
    }

    //  Size : 120x90
    fun getNormalQualityThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/default.jpg"
    }

    //  Size : 640x480
    //  May not be available
    fun getStandardDefinitionThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/sddefault.jpg"
    }

    //  Size : 1920x1080
    //  May not be available
    fun getMaximumResolutionThumbnail(movieId: String): String {
        return "https://img.youtube.com/vi/$movieId/maxresdefault.jpg"
    }
}
