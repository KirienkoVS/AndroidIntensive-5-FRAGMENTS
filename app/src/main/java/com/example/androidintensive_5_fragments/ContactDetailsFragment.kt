package com.example.androidintensive_5_fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ContactDetailsFragment: Fragment(R.layout.fragment_contact_details) {

    private lateinit var contactDetails: List<EditText>
    private lateinit var saveButtonClickListener: SaveButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveButtonClickListener = context as SaveButtonClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).title = "Contact details"

        contactDetails = listOf(
            view.findViewById(R.id.name_text),
            view.findViewById(R.id.last_name_text),
            view.findViewById(R.id.phone_number_text)
        )

        // Gets contact info from the bundle
        requireArguments().getBundle(CONTACT_INFO).also { bundle ->
            getContactInfoFromBundle(bundle)
        }

        // Edit button
        view.findViewById<Button>(R.id.edit_button).also { editButton ->
            editButton.setOnClickListener {
                contactDetails.forEach { editText ->
                    val defaultBackground = EditText(requireContext()).background
                    editText.apply {
                        isFocusableInTouchMode = true
                        background = defaultBackground
                    }
                }
            }
        }

        // Save button
        view.findViewById<Button>(R.id.save_button).also { saveButton ->
            saveButton.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(CONTACT_ID, requireArguments().getBundle(CONTACT_INFO)?.get(CONTACT_ID).toString())
                contactDetails.forEach { editText ->
                    when(editText.transitionName) {
                        NAME -> {
                            editText.text = editText.text
                            editText.isFocusableInTouchMode = false
                            editText.isFocusable = false
                            editText.setBackgroundColor(Color.WHITE)
                            bundle.putString(NAME, editText.text.toString())
                        }
                        LAST_NAME -> {
                            editText.text = editText.text
                            editText.isFocusableInTouchMode = false
                            editText.isFocusable = false
                            editText.setBackgroundColor(Color.WHITE)
                            bundle.putString(LAST_NAME, editText.text.toString())
                        }
                        PHONE_NUMBER -> {
                            editText.text = editText.text
                            editText.isFocusableInTouchMode = false
                            editText.isFocusable = false
                            editText.setBackgroundColor(Color.WHITE)
                            bundle.putString(PHONE_NUMBER, editText.text.toString())
                        }
                    }
                }
                saveButtonClickListener.onSaveButtonClicked(bundle)
            }
        }
    }

    private fun getContactInfoFromBundle(bundle: Bundle?) {
        contactDetails.forEach { editText ->
            when (editText.transitionName) {
                NAME -> editText.setText(bundle?.get(NAME).toString())
                LAST_NAME -> editText.setText(bundle?.get(LAST_NAME).toString())
                PHONE_NUMBER -> editText.setText(bundle?.get(PHONE_NUMBER).toString())
            }
        }
    }

    interface SaveButtonClickListener {
        fun onSaveButtonClicked(bundle: Bundle)
    }

    companion object {
        private const val CONTACT_INFO = "CONTACT_INFO"
        fun newInstance(bundle: Bundle) = ContactDetailsFragment().also {
            it.arguments = Bundle().apply {
                putBundle(CONTACT_INFO, bundle)
            }
        }
    }
}