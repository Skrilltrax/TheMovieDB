package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.CastCrewAdapter.CastCrewViewHolder
import me.skrilltrax.themoviedb.constants.CastCrewAdapterType
import me.skrilltrax.themoviedb.constants.Constants
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem

class CastCrewAdapter(val list: List<Any>, private val type: CastCrewAdapterType) :
    RecyclerView.Adapter<CastCrewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastCrewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast_crew, parent, false)
        return CastCrewViewHolder(view, type)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CastCrewViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CastCrewViewHolder(itemView: View, private val type: CastCrewAdapterType) :
        RecyclerView.ViewHolder(itemView) {

        private val castCrewImageView: ImageView = itemView.findViewById(R.id.cast_crew_image)
        private val castCrewName: TextView = itemView.findViewById(R.id.cast_crew_name)
        private val castCrewRole: TextView = itemView.findViewById(R.id.cast_crew_role)

        fun bind(position: Int) {
            if (type == CastCrewAdapterType.CAST) {
                val castList = list as List<CastItem>
                castCrewName.text = castList[position].name
                castCrewRole.text = castList[position].character
                Glide.with(itemView.context)
                    .load(Constants.POSTER_W185_IMAGE_PATH + castList[position].profilePath)
                    .transform(MultiTransformation(FitCenter(), RoundedCorners(8)))
                    .error(R.drawable.ic_person_outline_black_16dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(castCrewImageView)
            } else {
                val crewList = list as List<CrewItem>
                castCrewName.text = crewList[position].name
                castCrewRole.text = crewList[position].job

                Glide.with(itemView.context)
                    .load(Constants.POSTER_W185_IMAGE_PATH + crewList[position].profilePath)
                    .transform(MultiTransformation(FitCenter(), RoundedCorners(8)))
                    .error(R.drawable.ic_person_outline_black_16dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(castCrewImageView)

            }
        }
    }
}