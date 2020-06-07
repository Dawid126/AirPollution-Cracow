# Autorzy

Dawid Białka, Piotr Tekielak

# Bazy danych - projekt

Aplikacja łączy się z bazą danych i wyświetla na mapie pomiary zanieczyszczeń powietrza w danym dniu.

# Elementy projektu:

## Baza danych
Baza w MicrosoftSQLu składa się z trzech tabel: stations, measurements, norms.

Schemat bazy
<img src= "https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/databse_diagram.png">

### Tabela stations

Zawiera ID stacji, jej nazwę oraz współrzędne geograficzne.

### Tabela measurements

Zawiera ID pomiaru, jego wartość, czas wykonania, typ jako klucz obcy do norm oraz ID 
stacji, na której został wykonany dany pomiar.

### Tabela norms

Zawiera ID danej normy, jej typ i wartość. Na przykład dla PM10 (mieszaniny cząsteczek o średnicy nie 
większej niż 10 mikrometrów) dopuszczalna norma wynosi 40 mikrogramów na metr sześcienny.

## Generator danych

Generator losowych wartości pomiarów dla zanieczyszczeń typu PM2,5 i PM10 dla dwóch miesięcy. 
Napisany w języku C++.

## Moduły aplikacji:

### DatabaseProvider

Odpowiada za połączenie aplikacji z bazą danych za pomocą sterownika JDBC.

### GUI

Odpowiada za interfejs ekranu startowego.

Przekazanie wybranego dnia i miesiąca do ProgramController i utworzenie mapy:

<img src= "https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/show_map.png>

Wygląd okna startowego
<img src= "https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/starting_window.png">

Mapa
<img src ="https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/map.png">


### Main

Klasa główna. Ładuje parametry z pliku parameters.json i uruchamia całą aplikację.

### ProgramController

Odpowiada za logikę aplikacji - pobiera dane z bazy, inicjuje ramkę i panel.

Pobranie danych z bazy przy tworzeniu mapy:
<img src="(https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/obtain_data.png">

### MapDrawer

Klasa Madrawer odpowiada za rysowanie mapy za pomocą biblioteki Unfolding wraz z zaznaczeniem
wszystkich stacji.

Dodanie znaczników wszystkich stacji:

<img src ="https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/add_markers.png">

Klasa MeasurementContainer służy jako kontener dla danych z pojedynczego pomiaru.
Klasa ZoomSlider odpowiada za pasek Zooma mapy.
Klasa LabeledMarker to znacznik stacji pomiarowej, a gdy najedziemy myszką na dany znacznik to zostaną
wyświetlone dane pomiarowe z danej stacji. 
oraz za wyświetlanie szczegółów danej stacji pomiarowej.

Wyświetlenie szczegółów stacji:
<img src ="https://raw.githubusercontent.com/Dawid126/Data-base-project/master/Resources/show_details.png">



