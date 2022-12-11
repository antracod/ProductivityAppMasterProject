package com.leusoft.taskcore.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.TextUtils
import android.util.SparseArray
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.leusoft.taskcore.R
import com.leusoft.taskcore.data.Note
import com.leusoft.taskcore.viewmodel.NoteViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private val REQUEST_SPEECH_CODE = 102

    lateinit var noteDescription: EditText
    lateinit var noteTitle: EditText
    private val REQUEST_CAMERA_CODE = 100
    lateinit var bitmap: Bitmap

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteTitle = findViewById<EditText>(R.id.addNewNoteTitleEditText)
        noteDescription = findViewById<EditText>(R.id.addNewNoteDescriptionEditText)
        var scanTextButton = findViewById<Button>(R.id.addNewNoteScanDocumentButton)
        var speakTextButton = findViewById<Button>(R.id.addNewNoteTtsButton)
        var submitNoteButton = findViewById<Button>(R.id.addNewNoteSubmitButton)

        speakTextButton.setOnClickListener {
            askSpeechInput()
        }

        scanTextButton.setOnClickListener {
            checkForPermission(android.Manifest.permission.CAMERA, "camera", REQUEST_CAMERA_CODE)
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
        }

        submitNoteButton.setOnClickListener {
            val titleText = noteTitle.text
            val descriptionText = noteDescription.text
            insertNoteToDatabase()
        }
    }

    private fun insertNoteToDatabase() {
        val titleText = noteTitle.text.toString()
        val descriptionText = noteDescription.text.toString()

        if (inputCheck(titleText, descriptionText)) {
            val note = Note(0, titleText, descriptionText)
            mNoteViewModel.addNote(note)
            Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Please fill out all fields corectly.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(titleText: String, descriptionText: String): Boolean {
        return !(TextUtils.isEmpty(titleText) && TextUtils.isEmpty(descriptionText))
    }

    private fun checkForPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "permission refused", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "permission granted", Toast.LENGTH_SHORT).show()
            }
        }

        when (requestCode) {
            REQUEST_CAMERA_CODE -> innerCheck("camera")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to acces $name")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@AddNoteActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SPEECH_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            var recognizedTextData = result?.get(0).toString()
            noteDescription.setText(recognizedTextData)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            var result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {

                var resultUri: Uri = result.getUri()
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                getTextFromImage(bitmap)

            }
        }
    }

    private fun getTextFromImage(bitmap: Bitmap) {
        var recognizer: TextRecognizer = TextRecognizer.Builder(this).build()
        if (!recognizer.isOperational) {
            Toast.makeText(this, "Eroare detectie", Toast.LENGTH_SHORT).show()
        } else {
            var frame: Frame = Frame.Builder().setBitmap(bitmap).build()
            var textBlockSpareArray: SparseArray<TextBlock> = recognizer.detect(frame)
            var stringBuilder: StringBuilder = StringBuilder()
            var i = 0
            while (i < textBlockSpareArray.size()) {
                var textBlock: TextBlock = textBlockSpareArray.valueAt(i)
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
                i++

            }
            noteDescription.setText(stringBuilder.toString())
        }
    }


    private fun askSpeechInput() {

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognision is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Dictate your note")
            startActivityForResult(i, REQUEST_SPEECH_CODE)
        }
    }
}