package com.sitharaj.keyboardvisibility.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sitharaj.keyboardvisibility.keyboardVisibility

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        keyboardVisibility {
            onVisible {
                Toast.makeText(this@MainActivity, "Keyboard is visible", Toast.LENGTH_SHORT).show()
            }

            onHidden {
                Toast.makeText(this@MainActivity, "Keyboard is hidden", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<AppCompatButton>(R.id.fragmentActivity).setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }


    }
}