package com.leusoft.taskcore.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.leusoft.taskcore.R
import com.leusoft.taskcore.auth.WelcomeAuth
import com.leusoft.taskcore.data.Event
import com.leusoft.taskcore.utils.*
import com.leusoft.taskcore.viewmodel.EventViewModel
import com.leusoft.taskcore.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream


class SettingsActivity : AppCompatActivity() {


    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var mDialog : CustomPlusDialogFragment;
    lateinit var bottomNavigationView: BottomNavigationView;
    lateinit var settingProfileImage : ImageView

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val fireEventRef = firestoreInstance.collection("fireEvent")


    private lateinit var mEventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mEventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        settingProfileImage = findViewById<ImageView>(R.id.settingsImageView)
        mAuth = FirebaseAuth.getInstance()

        bottomNavigationView = findViewById(R.id.bottom_navigator)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.plus -> {
                    mDialog = CustomPlusDialogFragment(this)
                    mDialog.show(supportFragmentManager,"customDialog")
                }
                R.id.dashboard -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }
                R.id.calendar -> {
                    val intent = Intent(this, DailyCalendarActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }
                R.id.social -> {
                    val intent = Intent(this, SocialActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }
                R.id.profile -> {

                }

            }
            true
        }


        var enableSharedChatAndExports = findViewById<Button>(R.id.enableChatAndExportsButton)
        enableSharedChatAndExports.setOnClickListener {
            val sharedPref =getSharedPreferences("myPref",Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.apply {
                putInt("exportsEnabled",1)
                apply()
            }
            Toast.makeText(this,"Experiemental features enabled",Toast.LENGTH_LONG).show()

        }


        var logOutButtonSettings = findViewById<Button>(R.id.logOutButtonSettings)

        logOutButtonSettings.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(intentFor<WelcomeAuth>().newTask().clearTask())
                }

        }


        val syncEventsButton = findViewById<Button>(R.id.firebaseGetEventsSettingsButton)
        syncEventsButton.setOnClickListener {
            val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
            getEventFromFirebase(currentUserId)
        }




        settingProfileImage.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            Toast.makeText(this@SettingsActivity,"Succes",Toast.LENGTH_SHORT).show()
            startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
        }

        updateProfileButton.setOnClickListener {
            if (::selectedImageBytes.isInitialized)
                StorageUtil.uploadProfilePhoto(selectedImageBytes) { imagePath ->
                    FirestoreUtil.updateCurrentUser(nameValueSettingsTextView.text.toString(),
                        "biotest", imagePath)
                }
            else
                FirestoreUtil.updateCurrentUser(nameValueSettingsTextView.text.toString(),
                    "bio", null)
            toast("Saving")
        }




    }

    fun addEventToFirebase()
    {
        val tmpEvent = Event(1, "title","description","AZI","MAINE",null,null)
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val newChannel = fireEventRef.document(currentUserId)
        newChannel.set(tmpEvent)
        getEventFromFirebase(currentUserId)
    }

    fun getEventFromFirebase(uid: String)
    {
        val docRef: DocumentReference = firestoreInstance.collection("fireEvent").document("HZjlcL8TfxN3AUJWXxNZtqPqPQD2")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val tmpEvent: transferEvent = documentSnapshot.toObject(transferEvent::class.java)!!
            val dbEventTmp : Event = Event(99,tmpEvent.eventTitle,tmpEvent.eventDescription,tmpEvent.startDate,tmpEvent.endDate,tmpEvent.invitedName,tmpEvent.invitedEmail)

            Log.i("DBADD",dbEventTmp.eventTitle)
            mEventViewModel.addEvent(dbEventTmp)
            mEventViewModel.updateEvent(dbEventTmp)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("BEL","BEL")

        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                .getBitmap(this.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                .load(selectedImageBytes)
                .into(settingsImageView)

            pictureJustChanged = true
        }
    }



    override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { FireUser ->

            nameValueSettingsTextView.setText(FireUser.name)

            if (!pictureJustChanged && FireUser.profilePicturePath != null) {
                GlideApp.with(this)
                    .load(StorageUtil.pathToReference(FireUser.profilePicturePath))
                    .placeholder(R.drawable.ic_hard_avatar)
                    .into(settingProfileImage)
            }

        }
    }

}