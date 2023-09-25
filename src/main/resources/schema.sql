CREATE TABLE IF NOT EXISTS mpaa_ratings (
  mpaa_id integer PRIMARY KEY,
  name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
  film_id integer PRIMARY KEY,
  title varchar NOT NULL,
  description varchar,
  release_date date,
  duration integer,
  mpaa_id integer REFERENCES mpaa_ratings (mpaa_id)
);

CREATE TABLE IF NOT EXISTS genres (
  genre_id integer PRIMARY KEY,
  name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS films_x_genres (
  film_id integer REFERENCES films (film_id),
  genre_id integer REFERENCES genres (genre_id),
  PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users (
  user_id integer PRIMARY KEY,
  email varchar NOT NULL UNIQUE,
  login varchar,
  nickname varchar,
  birth_date date
);

CREATE TABLE IF NOT EXISTS likes (
  film_id integer REFERENCES films (film_id) ON DELETE CASCADE,
  user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friends (
  user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  friend_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS friend_requests (
  user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  friend_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, friend_id)
);
