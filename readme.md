# github-pv-pma-2026

## Úkol 003 – Hod kostkou

Aplikace je vytvořena ve dvou variantách:

- **cv2a – XML + Kotlin**
- **cv2b – Jetpack Compose**

## Porovnání

V **XML + Kotlin** variantě je uživatelské rozhraní vytvořeno v souboru `activity_main.xml`. Jednotlivé prvky se načítají pomocí `findViewById` a kostka se aktualizuje přímou změnou hodnoty `TextView`.

```kotlin
tvDice.text = diceSymbols[diceValue - 1]
```

V **Jetpack Compose** variantě je uživatelské rozhraní vytvořeno přímo v Kotlinu. Hodnota kostky je uložena ve stavové proměnné a po její změně Compose automaticky aktualizuje zobrazení.

```kotlin
firstDiceValue = (1..6).random()
```

Hlavní rozdíl je tedy v tom, že **XML varianta mění přímo konkrétní prvek rozhraní**, zatímco **Jetpack Compose mění stav aplikace a rozhraní se následně překreslí automaticky**.