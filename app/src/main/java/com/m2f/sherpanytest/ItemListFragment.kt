package com.m2f.sherpanytest

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.placeholder.PlaceholderContent;

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ItemListFragment : Fragment() {

  /**
   * Method to intercept global key events in the
   * item list fragment to trigger keyboard shortcuts
   * Currently provides a toast when Ctrl + Z and Ctrl + F
   * are triggered
   */
  private val unhandledKeyEventListenerCompat = ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
    if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
      Toast.makeText(
          v.context,
          "Undo (Ctrl + Z) shortcut triggered",
          Toast.LENGTH_LONG
      ).show()
      true
    } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
      Toast.makeText(
          v.context,
          "Find (Ctrl + F) shortcut triggered",
          Toast.LENGTH_LONG
      ).show()
      true
    }
    false
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_item_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

    val recyclerView: RecyclerView = view.findViewById(R.id.item_list)
    val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

    /** Click Listener to trigger navigation based on if you have
     * a single pane layout or two pane layout
     */
    val onClickListener = View.OnClickListener { itemView ->
      val item = itemView.tag as PlaceholderContent.PlaceholderItem
      val bundle = Bundle()
      bundle.putString(
          ItemDetailFragment.ARG_ITEM_ID,
          item.id
      )
      if (itemDetailFragmentContainer != null) {
        itemDetailFragmentContainer.findNavController()
            .navigate(R.id.fragment_item_detail, bundle)
      } else {
        itemView.findNavController().navigate(R.id.show_item_detail, bundle)
      }
    }

    /**
     * Context click listener to handle Right click events
     * from mice and trackpad input to provide a more native
     * experience on larger screen devices
     */
    val onContextClickListener = View.OnContextClickListener { v ->
      val item = v.tag as PlaceholderContent.PlaceholderItem
      Toast.makeText(
          v.context,
          "Context click of item " + item.id,
          Toast.LENGTH_LONG
      ).show()
      true
    }
    setupRecyclerView(recyclerView, onClickListener, onContextClickListener)
  }

  private fun setupRecyclerView(
      recyclerView: RecyclerView,
      onClickListener: View.OnClickListener,
      onContextClickListener: View.OnContextClickListener
  ) {

    recyclerView.adapter = SimpleItemRecyclerViewAdapter(
        PlaceholderContent.ITEMS,
        onClickListener,
        onContextClickListener
    )
  }

  class SimpleItemRecyclerViewAdapter(
      private val values: List<PlaceholderContent.PlaceholderItem>,
      private val onClickListener: View.OnClickListener,
      private val onContextClickListener: View.OnContextClickListener
  ) :
      RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_list_content, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = values[position]
      holder.idView.text = item.id
      holder.contentView.text = item.content

      with(holder.itemView) {
        tag = item
        setOnClickListener(onClickListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          setOnContextClickListener(onContextClickListener)
        }
      }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val idView: TextView = view.findViewById(R.id.id_text)
      val contentView: TextView = view.findViewById(R.id.content)
    }
  }
}