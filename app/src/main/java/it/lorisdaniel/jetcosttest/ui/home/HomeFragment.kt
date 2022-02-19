package it.lorisdaniel.jetcosttest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.lorisdaniel.jetcosttest.databinding.FragmentHomeBinding
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item
import it.lorisdaniel.jetcosttest.ui.adapter.SearchResultAdapter
import it.lorisdaniel.jetcosttest.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val binding get() = _binding!!
    private lateinit var adapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchResultAdapter(this::saveBookmarks)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchResultList = binding.searchResultListView
        val searchInputText = binding.searchText
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        searchResultList.adapter = adapter
        searchResultList.layoutManager = layoutManager
        binding.search.setOnClickListener {
            fetchResults(searchInputText.text.toString())
        }
    }

    private fun saveBookmarks(item: Item, image: Image?) {
        mainViewModel.saveBookmark(item, image)
    }

    private fun fetchResults(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getImages(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}