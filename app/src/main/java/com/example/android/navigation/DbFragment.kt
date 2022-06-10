package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android.navigation.databinding.FragmentDbBinding
import com.example.android.navigation.db.UserEntity

class DbFragment : Fragment(), RecyclerViewAdapter.RowClickListener {
    lateinit var binding: FragmentDbBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentDbBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = RecyclerViewAdapter(this@DbFragment)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(requireContext(), StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(viewLifecycleOwner, {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })


        binding.apply {
            saveButton.setOnClickListener {
                val name = nameInput.text.toString()
                val email = emailInput.text.toString()
                val phone = phoneInput.text.toString()
                if (saveButton.text.equals("Save")) {
                    val user = UserEntity(0, name, email, phone)
                    viewModel.insertUserInfo(user)
                } else {
                    val user = UserEntity(
                        nameInput.getTag(nameInput.id).toString().toInt(),
                        name,
                        email,
                        phone
                    )
                    viewModel.updateUserInfo(user)
                    saveButton.text = "Save"
                }
                nameInput.setText("")
                emailInput.setText("")
            }
        }

        return binding.root
    }



    override fun onDeleteUserClickListener(user: UserEntity) {
        viewModel.deleteUserInfo(user)
    }

    override fun onItemClickListener(user: UserEntity) {
        binding.apply {
            nameInput.setText(user.name)
            emailInput.setText(user.email)
            phoneInput.setText(user.phone)
            nameInput.setTag(nameInput.id, user.id)
            saveButton.text = "Update"
        }
    }
}