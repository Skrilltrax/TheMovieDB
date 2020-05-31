package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.adapter.CreditsAdapter.CastCrewViewHolder
import me.skrilltrax.themoviedb.constants.CreditsType
import me.skrilltrax.themoviedb.databinding.ItemCastCrewBinding
import me.skrilltrax.themoviedb.model.credits.CastItem
import me.skrilltrax.themoviedb.model.credits.CrewItem
import me.skrilltrax.themoviedb.utils.setCreditImage
import timber.log.Timber

class CreditsAdapter(val list: List<Any>, private val type: CreditsType) :
    RecyclerView.Adapter<CastCrewViewHolder>() {

    private lateinit var binding: ItemCastCrewBinding
    private lateinit var castList: List<CastItem>
    private lateinit var crewList: List<CrewItem>

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastCrewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCastCrewBinding.inflate(inflater, parent, false)
        if (type == CreditsType.CAST) {
            castList = list as List<CastItem>
        } else {
            crewList = list as List<CrewItem>
        }
        return CastCrewViewHolder(binding.root, type)
    }

    override fun onBindViewHolder(holder: CastCrewViewHolder, position: Int) {
        Timber.d("$type : $position")
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class CastCrewViewHolder(itemView: View, private val type: CreditsType) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            if (type == CreditsType.CAST) {
                val castItem = castList[position]
                binding.castCrewName.text = castItem.name
                binding.castCrewRole.text = castItem.character
                castItem.profilePath?.let { binding.castCrewImage.setCreditImage(it) }
            } else {
                val crewItem = crewList[position]
                binding.castCrewName.text = crewItem.name
                binding.castCrewRole.text = crewItem.job
                crewItem.profilePath?.let { binding.castCrewImage.setCreditImage(it) }
            }
        }
    }
}
