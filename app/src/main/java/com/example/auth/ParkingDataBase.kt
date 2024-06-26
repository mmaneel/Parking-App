package com.example.auth


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.auth.data.Reservation

@Database(entities = [Reservation::class],version = 4)
@TypeConverters(Converters::class)
abstract class ParkingDatabase: RoomDatabase() {
    abstract fun getReservationDao():ReservationDao

    companion object {
        private var INSTANCE: ParkingDatabase? = null
        fun getDatabase(context: Context): ParkingDatabase? {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context,ParkingDatabase::class.java,
                            "parking_db").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }
}