package dominando.android.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dominando.android.recyclerview.model.Message

class MessageAdapter(
    private val messages: MutableList<Message>,
    private val callback: (Message) -> Unit):
    RecyclerView.Adapter<MessageAdapter.VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.VH {
            val v = LayoutInflater.from(parent.context)
                                  .inflate(R.layout.item_message, parent, false)
            val vh = VH(v)
            vh.itemView.setOnClickListener {
                val message = messages[vh.absoluteAdapterPosition]
                callback(message)
            }
            return vh
        }

        override fun onBindViewHolder(holder: MessageAdapter.VH, position: Int) {
            val message = messages[position]
            holder.txtTitle.text = message.title
            holder.txtText.text = message.text
        }

        override fun getItemCount(): Int = messages.size

        class VH(itemView: View):RecyclerView.ViewHolder(itemView) {
            val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
            val txtText: TextView = itemView.findViewById(R.id.txtText)
        }
    }
