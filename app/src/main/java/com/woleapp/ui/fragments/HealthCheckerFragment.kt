package com.woleapp.ui.fragments

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.woleapp.R
import com.woleapp.databinding.FragmentHealthCheckerBinding
import com.woleapp.viewmodels.HealthCheckerViewModel
import java.text.SimpleDateFormat
import java.util.*

class HealthCheckerFragment : BaseFragment() {
    private lateinit var binding: FragmentHealthCheckerBinding
    private lateinit var viewModel: HealthCheckerViewModel
    private lateinit var hide: Array<View>
    private lateinit var datePickerDialog: DatePickerDialog
    private var calendar = Calendar.getInstance(TimeZone.getDefault())
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var alertDialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HealthCheckerViewModel::class.java)
        binding = FragmentHealthCheckerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
            viewModel = this@HealthCheckerFragment.viewModel
        }
        hide =
            arrayOf(
                binding.firstname,
                binding.lastname,
                binding.dateofbirth,
                binding.telephone,
                binding.customerDetailsCaseNote,
                binding.customersDetails,
                binding.genderHeader,
                binding.genderOption
            )
        binding.newOrFollowUpOption.setOnCheckedChangeListener { _, checkedId ->
            val visibility = when (checkedId) {
                R.id.follow_up_case -> View.GONE
                R.id.new_case -> View.VISIBLE
                else -> View.VISIBLE
            }
            toggleVisibility(visibility = visibility)
        }
        binding.dob.setOnClickListener {
            datePickerDialog.show()
        }
        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val cal = Calendar.getInstance(TimeZone.getDefault()).apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                binding.dob.setText(dateFormat.format(cal.timeInMillis))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        viewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.dialogMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { dialogHelper ->
                alertDialog = AlertDialog.Builder(requireContext()).create()
                alertDialog.apply {
                    setTitle(dialogHelper.dialogType.title)
                    setIcon(dialogHelper.dialogType.icon)
                    setMessage(dialogHelper.message)
                    setButton(DialogInterface.BUTTON_NEGATIVE, "Dismiss") { _, _ ->
                        this.cancel()
                    }
                    dialogHelper.action?.let { retryAction ->
                        setButton(
                            DialogInterface.BUTTON_POSITIVE,
                            dialogHelper.actionName
                        ) { _, _ ->
                            retryAction.invoke()
                            this.cancel()
                        }
                    }
                }.show()
            }
        }
        return binding.root
    }

    private fun toggleVisibility(visibility: Int) {
        binding.followUpCode.visibility =
            if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
        hide.forEach {
            it.visibility = visibility
        }
    }
}