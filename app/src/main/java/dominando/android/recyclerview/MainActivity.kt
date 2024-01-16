package dominando.android.recyclerview

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dominando.android.recyclerview.model.Message

class MainActivity : AppCompatActivity() {

    private var messages = mutableListOf<dominando.android.recyclerview.model.Message>()
    private var adapter = MessageAdapter(messages, this::onMessageItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lastCustomNonConfigurationInstance.let { savedMessages ->
            if(savedMessages is MutableList<*>) {
                messages.addAll(savedMessages.filterIsInstance(Message::class.java))
            }
        }

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        initRecyclerView()
        fabAdd.setOnClickListener {
            addMessage()
        }
    }

    private fun initRecyclerView() {
        var rvMessages = findViewById<RecyclerView>(R.id.rvMessages)

        rvMessages.adapter = adapter
        val layoutManager = GridLayoutManager(this, 1)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if (position == 0) 2 else 1
//            }
//        }
        initSwipeGesture()
        rvMessages.layoutManager = layoutManager
    }

    private fun addMessage() {

        var edtTitle = findViewById<TextInputEditText>(R.id.tieTitle)
        var edtText = findViewById<TextInputEditText>(R.id.tieText)

        val message = dominando.android.recyclerview.model.Message(
            edtTitle.text.toString(),
            edtText.text.toString()
        )

        messages.add(message)
        adapter.notifyItemInserted(messages.lastIndex)
        edtTitle.text?.clear()
        edtText.text?.clear()

    }

    private fun onMessageItemClick(message: dominando.android.recyclerview.model.Message) {
        val s = "${message.title}\n${message.text}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun initSwipeGesture() {
        val swipe = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                messages.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
        itemTouchHelper.attachToRecyclerView(rvMessages)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return messages
    }

}