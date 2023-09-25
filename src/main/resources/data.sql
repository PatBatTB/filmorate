MERGE INTO genres (genre_id, name)
KEY (genre_id)
VALUES
(1, 'комедия'),
(2, 'драма'),
(3, 'боевик'),
(4, 'мультфильм'),
(5, 'триллер'),
(6, 'документальный');


MERGE INTO mpaa_ratings (mpaa_id, name)
KEY (mpaa_id)
VALUES
(1, 'G'),
(2, 'PG'),
(3, 'PG-13'),
(4, 'R'),
(5, 'NC-17');