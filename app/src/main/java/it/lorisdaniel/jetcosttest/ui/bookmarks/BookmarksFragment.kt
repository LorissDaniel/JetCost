package it.lorisdaniel.jetcosttest.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.lorisdaniel.jetcosttest.databinding.FragmentBookmarksBinding
import it.lorisdaniel.jetcosttest.model.ItemAndImage
import it.lorisdaniel.jetcosttest.ui.adapter.BookmarksAdapter
import it.lorisdaniel.jetcosttest.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        mainViewModel.loadBookmarks()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookmarksList = binding.bookmarksList
        val adapter = BookmarksAdapter(requireContext(), ArrayList(), this::onDeleteBookmark)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bookmarksList.adapter = adapter
        bookmarksList.layoutManager = layoutManager
        mainViewModel.bookmarks.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            showEmptyView(it)
        }
    }

    private fun onDeleteBookmark(item: ItemAndImage) {
        mainViewModel.deleteBookmark(item)
        Toast.makeText(requireContext(), "Bookmark deleted successfully!", Toast.LENGTH_LONG).show()
    }

    private fun showEmptyView(data: List<ItemAndImage>) {
        if (data.isEmpty()) {
            binding.bookmarksList.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.bookmarksList.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}