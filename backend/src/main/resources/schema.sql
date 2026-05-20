CREATE TABLE IF NOT EXISTS hotels (
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    name  TEXT NOT NULL,
    city  TEXT NOT NULL,
    stars INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms (
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id         INTEGER NOT NULL,
    number           TEXT NOT NULL,
    capacity         INTEGER NOT NULL,
    price_per_night  REAL NOT NULL,
    available        INTEGER NOT NULL,
    room_type        TEXT NOT NULL,
    has_balcony      INTEGER,
    has_living_area  INTEGER,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);

CREATE TABLE IF NOT EXISTS guests (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    name           TEXT NOT NULL,
    email          TEXT NOT NULL,
    loyalty_points INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT NOT NULL,
    email    TEXT NOT NULL,
    position TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    guest_id       INTEGER NOT NULL,
    hotel_id       INTEGER NOT NULL,
    room_id        INTEGER NOT NULL,
    check_in_date  TEXT NOT NULL,
    nights         INTEGER NOT NULL,
    status         TEXT NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES guests(id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    FOREIGN KEY (room_id)  REFERENCES rooms(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INTEGER NOT NULL,
    amount     REAL NOT NULL,
    method     TEXT NOT NULL,
    paid       INTEGER NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    guest_id INTEGER NOT NULL,
    hotel_id INTEGER NOT NULL,
    stars    INTEGER NOT NULL,
    comment  TEXT NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES guests(id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);
