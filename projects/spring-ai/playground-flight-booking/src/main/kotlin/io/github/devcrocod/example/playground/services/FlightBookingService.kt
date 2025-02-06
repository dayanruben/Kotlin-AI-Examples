package io.github.devcrocod.example.playground.services

import io.github.devcrocod.example.playground.data.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.random.Random


@Service
class FlightBookingService {
    private val db: BookingData = BookingData()

    init {
        initDemoData()
    }

    private fun initDemoData() {
        val firstNames = listOf("John", "Jane", "Michael", "Sarah", "Robert")
        val lastNames = listOf("Doe", "Smith", "Johnson", "Williams", "Taylor")
        val airportCodes =
            listOf("LAX", "SFO", "JFK", "LHR", "CDG", "ARN", "HEL", "TXL", "MUC", "FRA", "MAD", "FUN", "SJC")
        val random: Random = Random

        val customers = mutableListOf<Customer>()
        val bookings = mutableListOf<Booking>()

        repeat(5) {
            val customer = Customer(firstName = firstNames[it], lastName = lastNames[it])

            val booking = Booking(
                bookingNumber = "10${it + 1}",
                date = LocalDate.now().plusDays(2L * it),
                customer = customer,
                from = airportCodes[random.nextInt(airportCodes.size)],
                to = airportCodes[random.nextInt(airportCodes.size)],
                bookingStatus = BookingStatus.CONFIRMED,
                bookingClass = BookingClass.entries[random.nextInt(BookingClass.entries.size)]
            )

            customer.bookings.add(booking)
            customers.add(customer)
            bookings.add(booking)
        }

        // Reset the database on each start
        db.customers = customers
        db.bookings = bookings
    }

    fun getBookings(): List<BookingTools.BookingDetails> =
        db.bookings.map { toBookingDetails(it) }

    private fun findBooking(bookingNumber: String, firstName: String, lastName: String): Booking =
        db.bookings.firstOrNull {
            it.bookingNumber.equals(bookingNumber, ignoreCase = true) &&
                    it.customer.firstName.equals(firstName, ignoreCase = true) &&
                    it.customer.lastName.equals(lastName, ignoreCase = true)
        } ?: throw IllegalArgumentException("Booking not found")

    fun getBookingDetails(bookingNumber: String, firstName: String, lastName: String): BookingTools.BookingDetails {
        val booking = findBooking(bookingNumber, firstName, lastName)
        return toBookingDetails(booking)
    }

    fun changeBooking(
        bookingNumber: String, firstName: String, lastName: String,
        newDate: String, from: String, to: String
    ) {
        val booking = findBooking(bookingNumber, firstName, lastName)
        if (booking.date.isBefore(LocalDate.now().plusDays(1))) {
            throw IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.")
        }
        booking.apply {
            date = LocalDate.parse(newDate)
            this.from = from
            this.to = to
        }
    }

    fun cancelBooking(bookingNumber: String, firstName: String, lastName: String) {
        val booking = findBooking(bookingNumber, firstName, lastName)
        if (booking.date.isBefore(LocalDate.now().plusDays(2))) {
            throw IllegalArgumentException("Booking cannot be cancelled within 48 hours of the start date.")
        }
        booking.bookingStatus = BookingStatus.CANCELED
    }

    private fun toBookingDetails(booking: Booking): BookingTools.BookingDetails =
        BookingTools.BookingDetails(
            booking.bookingNumber,
            booking.customer.firstName,
            booking.customer.lastName,
            booking.date,
            booking.bookingStatus,
            booking.from,
            booking.to,
            booking.bookingClass.toString()
        )
}