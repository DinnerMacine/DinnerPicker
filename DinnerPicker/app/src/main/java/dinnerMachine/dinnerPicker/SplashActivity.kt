package dinnerMachine.dinnerPicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    inner class sleep(val theAct : Activity) : Thread() {
        override fun run()
        {
            try {
                Thread.sleep(4000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            startActivity(Intent(theAct, MainActivity::class.java))
            finish()
        }
    }
}
