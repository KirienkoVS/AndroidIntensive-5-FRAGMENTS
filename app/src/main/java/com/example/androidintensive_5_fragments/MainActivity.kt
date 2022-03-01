package com.example.androidintensive_5_fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity: AppCompatActivity(), ContactsFragment.ContactClickListener, ContactDetailsFragment.SaveButtonClickListener {

    private lateinit var manager: FragmentManager
    private var isTablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
        isTablet = resources.getBoolean(R.bool.isTablet)

        if (manager.findFragmentByTag(CONTACTS_FRAGMENT_TAG) == null) {
            if (isTablet) {
                manager.beginTransaction().run {
                    add(R.id.contacts_fragment_container, ContactsFragment.newInstance(Bundle()), CONTACTS_FRAGMENT_TAG)
                    commit()
                }
            } else {
                manager.beginTransaction().run {
                    replace(R.id.fragment_container, ContactsFragment.newInstance(Bundle()), CONTACTS_FRAGMENT_TAG)
                    addToBackStack(CONTACTS_FRAGMENT_TAG)
                    commit()
                }
            }
        }

    }

    override fun onContactClicked(bundle: Bundle, contactID: String) {
        if (isTablet) {
            manager.beginTransaction()
                .replace(R.id.contacts_details_fragment_container, ContactDetailsFragment.newInstance(bundle, contactID), CONTACT_DETAILS_FRAGMENT_TAG)
                .commit()
            findViewById<View>(R.id.contacts_details_fragment_container).visibility = View.VISIBLE
        } else {
            manager.beginTransaction().run {
                replace(R.id.fragment_container,
                    ContactDetailsFragment.newInstance(bundle, contactID),
                    CONTACT_DETAILS_FRAGMENT_TAG
                )
                addToBackStack(CONTACT_DETAILS_FRAGMENT_TAG)
                commit()
            }
        }
    }

    override fun onSaveButtonClicked(bundle: Bundle) {
        if (isTablet) {
            manager.beginTransaction().run {
                replace(R.id.contacts_fragment_container, ContactsFragment.newInstance(bundle), CONTACTS_FRAGMENT_TAG)
                commit()
            }
            findViewById<View>(R.id.contacts_details_fragment_container).visibility = View.GONE
        } else {
            manager.apply {
                popBackStack()
                beginTransaction().run {
                    replace(R.id.fragment_container, ContactsFragment.newInstance(bundle), CONTACTS_FRAGMENT_TAG)
                    commit()
                }
            }
        }
    }

    override fun onBackPressed() {
       if (manager.backStackEntryCount > 1) {
           manager.popBackStack()
       } else finish()
    }
}