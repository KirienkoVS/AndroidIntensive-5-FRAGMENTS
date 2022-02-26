package com.example.androidintensive_5_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private lateinit var contactClickListener: ContactClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contactClickListener = context as ContactClickListener
        Log.d("ContactsFragment", "onAttach")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).title = "Contacts"

        view.findViewById<LinearLayout>(R.id.contacts_list_layout).forEach { contactLayout ->
            onContactClickListener(contactLayout as ConstraintLayout)
        }

        Log.d("ContactsFragment", "onViewCreated")
    }

    private fun onContactClickListener(contactLayout: ConstraintLayout) {
        val contactInfo = getContactInfoFromLayout(contactLayout)
        contactLayout.setOnClickListener {
            val bundle = Bundle()
            contactInfo.forEach { contactField ->
                when(contactField.first()) {
                    NAME -> bundle.putString(NAME, contactField.last())
                    LAST_NAME -> bundle.putString (LAST_NAME, contactField.last())
                    PHONE_NUMBER -> bundle.putString(PHONE_NUMBER, contactField.last())
                }
                bundle.putString(CONTACT_ID, contactLayout.transitionName)
            }
            contactClickListener.onContactClicked(bundle)
            Toast.makeText(activity, "$bundle", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getContactInfoFromLayout(contactLayout: ConstraintLayout): List<List<String>> {

        val contactInfo = mutableListOf<List<String>>()

        if (requireArguments().getBundle(NEW_CONTACT_INFO)?.get(CONTACT_ID) != null) {
            val contactID = requireArguments().getBundle(NEW_CONTACT_INFO)?.get(CONTACT_ID).toString()
            val name = requireArguments().getBundle(NEW_CONTACT_INFO)?.get(NAME).toString()
            val lastName = requireArguments().getBundle(NEW_CONTACT_INFO)?.get(LAST_NAME).toString()
            val phoneNumber = requireArguments().getBundle(NEW_CONTACT_INFO)?.get(PHONE_NUMBER).toString()

            if (contactLayout.transitionName == contactID) {
                (contactLayout).forEach { textView ->
                    textView as TextView
                    when(textView.transitionName) {
                        NAME -> {
                            textView.text = name
                            contactInfo.add(listOf(NAME, textView.text.toString()))
                        }
                        LAST_NAME -> {
                            textView.text = lastName
                            contactInfo.add(listOf(LAST_NAME, textView.text.toString()))
                        }
                        PHONE_NUMBER -> {
                            textView.text = phoneNumber
                            contactInfo.add(listOf(PHONE_NUMBER, textView.text.toString()))
                        }
                    }
                }
            } else {
                contactLayout.forEach { textView ->
                    textView as TextView
                    when (textView.transitionName) {
                        NAME -> contactInfo.add(listOf(NAME, textView.text.toString()))
                        LAST_NAME -> contactInfo.add(listOf(LAST_NAME, textView.text.toString()))
                        PHONE_NUMBER -> contactInfo.add(listOf(PHONE_NUMBER, textView.text.toString()))
                    }
                }
            }
        } else {
            contactLayout.forEach { textView ->
                textView as TextView
                when (textView.transitionName) {
                    NAME -> contactInfo.add(listOf(NAME, textView.text.toString()))
                    LAST_NAME -> contactInfo.add(listOf(LAST_NAME, textView.text.toString()))
                    PHONE_NUMBER -> contactInfo.add(listOf(PHONE_NUMBER, textView.text.toString()))
                }
            }
        }
        return contactInfo
    }

    interface ContactClickListener {
        fun onContactClicked(bundle: Bundle)
    }

    companion object {
        private const val NEW_CONTACT_INFO = "NEW_CONTACT_INFO"
        fun newInstance(bundle: Bundle) = ContactsFragment().also {
            it.arguments = Bundle().apply {
                putBundle(NEW_CONTACT_INFO, bundle)
            }
        }
    }
}