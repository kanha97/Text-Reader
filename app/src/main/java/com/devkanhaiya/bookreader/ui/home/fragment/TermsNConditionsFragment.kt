package com.devkanhaiya.bookreader.ui.home.fragment

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeTermsNConditionsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.google.firebase.database.*


class TermsNConditionsFragment : BaseFragment() {

    var binding: HomeTermsNConditionsFragmentBinding? = null
    private lateinit var firebaseDatabaseReference: DatabaseReference
    private var currentLanguage = 0
    private var isAddStories = false
    private var storyTypesId :Long= 0

    override fun createLayout(): Int = R.layout.home_terms_n_conditions_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeTermsNConditionsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        isAddStories = arguments?.getBoolean(SettingsFragment.ADD_STORIES) == true
        appPreferences.putBoolean("admin", true)
        toolbar.showToolbar(true)
        toolbar.showBackButton(true)
        if (isAddStories) {
            toolbar.setToolbarTitle("Add Stories")

        } else {
            toolbar.setToolbarTitle("Add Stories Types")
        }
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference

        binding?.submit?.setOnClickListener {
            addDataToFirebaseDatabase()

        }

        setupSpinner()
        setupDropDownStoryTypes()
    }

    private fun setupDropDownStoryTypes() {

        if (isAddStories) {
            binding?.editTextStories?.visibility = View.VISIBLE
            binding?.dropdownMenuStoryTypes?.visibility = View.VISIBLE
        }
        binding?.progressBar?.visibility = View.VISIBLE


        val storyTypesList = ArrayList<Transport>()
        storyTypesList.clear()
        firebaseDatabaseReference.child("storytypes")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    snapshot.children.forEach {
                        val transport: Transport? = it.getValue(Transport::class.java)

                        transport?.isDeletable = false

                        if (currentLanguage == transport?.language) {
                            storyTypesList.add(transport)
                        }

                    }
                    storyTypesList.let {
                        val listAdapter =
                            ArrayAdapter(
                                context!!,
                                android.R.layout.simple_list_item_1,
                                storyTypesList.map { it.title })
                        (binding?.contextMenuTv as? AutoCompleteTextView)?.setAdapter(listAdapter)


                        binding?.contextMenuTv?.setOnItemClickListener { parent, view, position, id ->
                            storyTypesId = storyTypesList.get(position).id!!
                            Log.d("TAG", "onDataChange: $position")
                        }
                        binding?.contextMenuTv?.setOnTouchListener { v, event ->
                            hideKeyBoard()
                            true
                        }

                    }

                    binding?.progressBar?.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: $error")

                }

            })


    }

    private fun setupSpinner() {

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding!!.spinnerLanguage.adapter = adapter


        binding!!.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    currentLanguage = position
                    setupDropDownStoryTypes()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


    }

    private fun addDataToFirebaseDatabase() {
        var generateId: Long =
            (0..100000).random().toLong() + (0..100000).random().toLong() + (0..100000).random()
                .toLong()

        if (binding!!.editTextTitle.text?.trim().toString().isBlank()) {

            showError("Enter title Bro")
        } else if (binding!!.editTextDescription.text?.trim().toString().isBlank()) {
            showError("Enter Description Bro")

        } else {
            binding?.progressBar?.visibility = View.VISIBLE
            if (!isAddStories) {

                firebaseDatabaseReference.child("storytypes").push()
                    .setValue(Transport().apply {
                        id = generateId
                        title = binding!!.editTextTitle.text?.trim().toString()
                        description = binding!!.editTextDescription.text?.trim().toString()
                        isDeletable = false
                        language = currentLanguage

                    }).addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding?.progressBar?.visibility = View.GONE
                            clearData()
                            generateId = 0
                        } else {
                            showError("ain't working bro")
                        }

                    }

            } else {
                firebaseDatabaseReference.child("stories").push()
                    .setValue(Transport().apply {
                        id = generateId
                        title = binding!!.editTextTitle.text?.trim().toString()
                        text = binding!!.editTextStories.text?.trim().toString()
                        description = binding!!.editTextDescription.text?.trim().toString()
                        isDeletable = false
                        language = currentLanguage
                        types=storyTypesId
                    }).addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding?.progressBar?.visibility = View.GONE
                            clearData()
                            generateId = 0
                        } else {
                            showError("ain't working bro")
                        }

                    }

            }
        }
    }

    private fun clearData() {
        binding!!.editTextTitle.text?.clear()
        binding!!.editTextDescription.text?.clear()
        binding!!.editTextStories.text?.clear()
        setupDropDownStoryTypes()
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.showBackButton(false)
    }
}