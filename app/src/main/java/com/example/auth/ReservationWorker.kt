package com.example.auth

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.auth.Repo.ReservationRepository
import com.example.auth.Screens.formatTime
import com.example.auth.data.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class ReservationWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            // Query the database for reservations
            val reservations = getReservationsFromDatabase()

            // Get current time
            val currentTime = System.currentTimeMillis()

            for (reservation in reservations) {
                val notificationTime = reservation.arrivalTime.time - 3600000 // 1 hour before

                if (currentTime <= notificationTime && notificationTime <= currentTime + NOTI_CHECK*60000) {
                    // Send notification
                    sendNotification(reservation)
                }
            }

        }
        return Result.success()
    }

    private suspend fun getReservationsFromDatabase(): List<Reservation> {
        return withContext(Dispatchers.IO) {
            return@withContext ParkingDatabase.getDatabase(context)?.getReservationDao()
                ?.getReservations(AuthManager.getUserId(context)) ?: emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast", "MissingPermission")
    private fun sendNotification(reservation: Reservation) {
        val channelId = "reservation_channel"
        val notificationId = reservation.id // Use reservation ID as notification ID

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reservation Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for reservation notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Upcoming Reservation")
            .setContentText("You have a reservation at ${formatTime(reservation.arrivalTime)}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}
