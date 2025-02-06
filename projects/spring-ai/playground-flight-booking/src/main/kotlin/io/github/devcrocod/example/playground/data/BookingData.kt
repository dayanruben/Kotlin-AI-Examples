package io.github.devcrocod.example.playground.data

data class BookingData(
    var customers: List<Customer> = emptyList(),
    var bookings: List<Booking> = emptyList()
)