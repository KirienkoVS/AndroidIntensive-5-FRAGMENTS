package com.example.androidintensive_5_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity(), ContactsFragment.ContactClickListener, ContactDetailsFragment.SaveButtonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(CONTACTS_FRAGMENT_TAG) == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, ContactsFragment.newInstance(Bundle()), CONTACTS_FRAGMENT_TAG)
                addToBackStack(CONTACTS_FRAGMENT_TAG)
                commit()
            }
        }
        Log.d(MAIN_ACTIVITY_TAG, "onCreate")
    }

    override fun onContactClicked(bundle: Bundle) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, ContactDetailsFragment.newInstance(bundle), CONTACT_DETAILS_FRAGMENT_TAG)
            addToBackStack(CONTACT_DETAILS_FRAGMENT_TAG)
            commit()
        }
    }

    override fun onSaveButtonClicked(bundle: Bundle) {
        supportFragmentManager.apply {
            popBackStack()
            beginTransaction().run {
                replace(R.id.fragment_container, ContactsFragment.newInstance(bundle), CONTACTS_FRAGMENT_TAG)
                commit()
            }
        }
    }

    override fun onBackPressed() {
       if (supportFragmentManager.backStackEntryCount > 1) {
           supportFragmentManager.popBackStack()
       } else finish()
    }
}