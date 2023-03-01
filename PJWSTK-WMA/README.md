# PJWSTK-WMA

WIM1 - Śledzenie środka ciężkości czerwonej piłki na filmiku. Nałożony został filtr barwy czerwonej, wykonane zostały przekształcenia morfologiczne, dzięki którym za pomocą openCV znalazłem koło i jego środek.

WIM2 - Obliczenie monet na tacy. Poprzez kombinację filtru Canny i HoughLines znalazłem krawędzie tacki (linie poziome - minY i maxY, oraz linie pionowe minX i maxX). Następnie po odpowiednich przekształceniach i nałożeniu filtrów odróżniam pięciozłotówki od pięciogroszówek na podstawie średnicy ich obwodów.

WIM3 - Wyszukuję podobieństw za pomocą kluczowych punktów. Biorę sobie klatkę z filmu, porównuję z próbkami - tam, gdzie będzie najwięcej wspólnych punktów kluczowych - rysuję je i wokół najbardizej obiecujących rysuję czworokąt.

WIM4 - Zaproponuj splotową sieć neuronową i naucz ją rozpoznawać obiekty, zwiększ bazę danych za pomocą technik augmentacji. Naucz sieć na rozszerzonym zbiorze danych. Porównaj wyniki.