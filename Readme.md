"TRULY" OOP + Selenium webdriver
<br>

На текущей работе нужно загружать несколько десятков файлов по одному за раз. Страницы для загрузки бывают двух видов.
Но имеют общий Xpath.
<br>

Код собирает файлы из директорий, при необходимости проверяет длину полного пути файла, после обхода директорий,
сравнивает количество до посещения и количество добавленных файлов в Map во время обхода. Дальше загружает файлы в
нужные импуты из Map, где ключ это полный путь к файлу, значение имя файла без расширения.
<br>
После загуркзи проверяет количество загруженных файлов на странице и сравнивает с размером Map и дополнительной
переменной.

commit - console app now
<br>
Концептуально ничего не поменялось. Появилась необходимость поделиться данным инструментом с коллегой. Пришлось
превратить "скрипт для загрузки файлов на сайт" в консольное приложение.
