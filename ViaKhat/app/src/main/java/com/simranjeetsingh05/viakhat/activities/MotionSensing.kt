package com.simranjeetsingh05.viakhat.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.ux.ArFragment
import com.simranjeetsingh05.viakhat.R
import com.simranjeetsingh05.viakhat.databinding.ActivityMotionSensingBinding

class MotionSensing : AppCompatActivity() {
    private lateinit var binding: ActivityMotionSensingBinding
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
//                .setOnTapPlaneGlbModel("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb")
//
//    }
}
//        super.onCreate(savedInstanceState)
//        binding = ActivityMotionSensingBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        checkOrientation()
//    }
//    private fun checkOrientation() {
//        val sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        sensorManager.registerListener(
//            object : SensorEventListener {
//                var orientation = -1
//                override fun onSensorChanged(event: SensorEvent) {
//                    orientation = if (event.values[1] < 6.5 && event.values[1] > -6.5) {
//                        if (orientation != 1) {
//                            Log.d("Sensor", "Landscape")
//                            binding.iv.rotation = 90f
//                        }
//                        1
//                    } else {
//                        if (orientation != 0) {
//                            Log.d("Sensor", "Portrait")
//                            binding.iv.rotation = 0f
//                        }
//                        0
//                    }
//                }
//
//                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//                    // TODO Auto-generated method stub
//                }
//            },
//            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//    }
//}