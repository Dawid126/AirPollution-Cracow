# Bazy danych - projekt

Aplikacja łączy się z bazą danych i wyświetla na mapie pomiary zanieczyszczeń powietrza w danym dniu.


# Elementy projektu:

## Baza danych
Baza w SQLu składa się z trzech tabel: stations, measurements, norms.

## Generator danych

Generator losowych wartości pomiarów dla zanieczyszczeń typu PM2,5 i PM10 dla dwóch miesięcy. Napisany w języku C++.

## Aplikacja

Aplikacja pobierająca dane z bazy i wyświetlająca je na mapie. Napisana w języku Java.

## Moduły aplikacji:

### DatabaseProvider

Odpowiada za połączenie aplikacji z bazą danych za pomocą sterownika JDBC.

### GUI

Odpowiada za interfejs ekranu startowego.

### Main

Klasa główna.

### MapDrawer

Odpowiada za rysowanie mapy za pomocą biblioteki Unfolding, za pasek Zooma mapy oraz za wyświetlanie szczegółów danej stacji pomiarowej.

### ProgramController

Odpowiada za logikę aplikacji - pobiera dane z bazy, inicjuje ramkę i panel.
