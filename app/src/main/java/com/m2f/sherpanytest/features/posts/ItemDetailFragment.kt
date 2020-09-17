package com.m2f.sherpanytest.features.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.features.posts.placeholder.PlaceholderContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

  /**
   * The placeholder content this fragment is presenting.
   */
  private var item: PlaceholderContent.PlaceholderItem? = null

  lateinit var itemDetailTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
      if (it.containsKey(ARG_ITEM_ID)) {
        // Load the placeholder content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        item = PlaceholderContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
      }
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val rootView = inflater.inflate(R.layout.fragment_item_detail, container, false)

    rootView.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = item?.content

    itemDetailTextView = rootView.findViewById(R.id.item_detail)
    // Show the placeholder content as text in a TextView.
    item?.let {
      itemDetailTextView.text = it.details
    }

    return rootView
  }

  companion object {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    const val ARG_ITEM_ID = "item_id"
  }
}