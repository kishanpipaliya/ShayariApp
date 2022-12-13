package com.example.shayariapp

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream

class FullScreenActivity : AppCompatActivity() {

    lateinit var imgShare: ImageView
    lateinit var imgBack: ImageView
    lateinit var imgCopyFull: ImageView
    lateinit var rel3: RelativeLayout
    lateinit var rel2: RelativeLayout
    lateinit var imgFromUser: ImageView
    lateinit var imgClick: ImageView
    lateinit var txtQuotes: TextView
    var image = ArrayList<Int>()
    var Count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)
        initView()
    }

    private fun initView() {

        imgShare = findViewById(R.id.imgShare)
        imgBack = findViewById(R.id.imgBack)
        imgClick = findViewById(R.id.imgClick)
        txtQuotes = findViewById(R.id.txtQuotes)
        imgCopyFull = findViewById(R.id.imgCopyFull)
        imgFromUser = findViewById(R.id.imgFromUser)
        rel2 = findViewById(R.id.rel2)
        rel3 = findViewById(R.id.rel3)




        image.add(R.drawable.image1)
        image.add(R.drawable.image2)
        image.add(R.drawable.image3)
        image.add(R.drawable.image4)
        image.add(R.drawable.image5)
        image.add(R.drawable.image6)
        image.add(R.drawable.image7)
        image.add(R.drawable.image8)

        var Quotes = intent.getStringExtra("Quotes")
        Log.e(TAG, "initView: " + Quotes)
        txtQuotes.setText(Quotes)

        imgShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, Quotes)
            startActivity(Intent.createChooser(intent, "share via"))
        }
        imgBack.setOnClickListener {
            finish()
        }
        rel3.setOnClickListener {
            saveimage()
        }
        imgClick.setOnClickListener {
            imgClick.setImageResource(image.get(Count))
            Count++
            if (Count == 8) {
                Count = 0
            }
        }
        imgCopyFull.setOnClickListener {
            var copyQuote = txtQuotes.text.toString()
            val clipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text Copy", copyQuote)
            Toast.makeText(this, "Quotes is Copy", Toast.LENGTH_SHORT).show()
            clipboardManager.setPrimaryClip(clipData)
        }

        imgFromUser.setOnClickListener(View.OnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.cameragallerty)
            dialog.show()
            val btnGallery = dialog.findViewById<View>(R.id.btnGallery)
            btnGallery.setOnClickListener {
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 100)
                gallery.putExtra("gallery", gallery)
                dialog.dismiss()
            }
            val btnCamera = dialog.findViewById<View>(R.id.btnCamera)
            btnCamera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 200)
                intent.putExtra("camera", intent)
                dialog.dismiss()
            }
        })

    }
    private fun saveimage() {

        var bitmap = rel2.drawToBitmap()
        var foutStream: FileOutputStream? = null
        var sdCard = Environment.getExternalStorageDirectory()
        var dir = File(sdCard.absolutePath + "/DCIM/Camera")
        dir.mkdir()
        var fileName = String.format("%d.jpg", System.currentTimeMillis())
        var outfile = File(dir, fileName)
        foutStream = FileOutputStream(outfile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 99, foutStream)
        foutStream.flush()
        foutStream.close()
        Toast.makeText(this, "Photo saved", Toast.LENGTH_SHORT).show()
        var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(outfile)
        sendBroadcast(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == -1 && data != null) {
                val uri = data.data
                imgClick.setImageURI(uri)
            }
        } else if (requestCode == 200) {
            if (resultCode == -1 && data != null) {
                val photo = data.extras!!["data"] as Bitmap?
                imgClick.setImageBitmap(photo)
            }
        }
    }
}