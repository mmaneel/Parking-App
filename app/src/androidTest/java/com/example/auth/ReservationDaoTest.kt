package com.example.auth

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.auth.data.Reservation

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ReservationDaoTest {

    private var mdb: ParkingDatabase? = null
    val date1 =  Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    val date2 =  Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant())

    @Before
    fun initDB() {
        val appContext =
            InstrumentationRegistry.getInstrumentation().targetContext
        mdb = Room.inMemoryDatabaseBuilder(appContext, ParkingDatabase::class.java).build()

    }

    @Test
    fun testInsertandGetReservation()
    {
        val parking = Parking(1,"testPark",5,"Alger",200,"ouedsmar","description")
        val res1 = Reservation(1,parking, date1)
        val res2 = Reservation(2,parking, date2)
        mdb?.getReservationDao()?.addReservations(res1,res2)
        val list = mdb?.getReservationDao()?.getReservations()
        assertTrue(list?.contains(res1)?: false)
        assertTrue(list?.contains(res2)?: false)
    }

    @Test
    fun testInsertandGetReservationbyDate()
    {
        val parking = Parking(1,"testPark",5,"Alger",200,"ouedsmar","description")
        val res1 = Reservation(1,parking, date1)
        val res2 = Reservation(2,parking, date2)
        mdb?.getReservationDao()?.addReservations(res1,res2)
        val list = mdb?.getReservationDao()?.getReservationsByDate(date2)
        assertEquals(res2, list?.get(0))
    }

}
