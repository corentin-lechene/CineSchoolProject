import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.R
import com.cineschoolproject.models.movie_model.TheMovieDbDto
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<TheMovieDbDto>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.movie_release_date)
        val imageView: ImageView = itemView.findViewById(R.id.movie_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.releaseDateTextView.text = movie.release_date
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w300" + movie.backdrop_path)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
