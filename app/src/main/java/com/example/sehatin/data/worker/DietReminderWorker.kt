package com.example.sehatin.data.worker

import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.sehatin.R

// path: worker/DietReminderWorker.kt

class DietReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        val mealName = inputData.getString("meal_name") ?: "Makanan"
        val mealServingAmount = inputData.getString("meal_serving_amount") ?: "0"
        val mealServingUnit = inputData.getString("meal_serving_unit") ?: "gram"
        val largeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)

        val notification = NotificationCompat.Builder(applicationContext, "diet_channel")
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(largeIcon)
            .setContentTitle("Pengingat Saran Maknaan")
            .setContentText(

                "Saatnya makan $mealName sebanyak $mealServingAmount $mealServingUnit"

            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(System.currentTimeMillis().toInt(), notification)
        return Result.success()
    }
}
