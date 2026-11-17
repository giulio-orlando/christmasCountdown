# Christmas Countdown — APK tramite GitHub Actions

App Android offline in Kotlin: mostra i giorni fino al 25 dicembre, mostra "Buon Natale!" il 25 e aggiorna il numero quando l'app è aperta o riaperta. Non richiede permessi.

## Compila senza installare software sul PC

1. Vai su https://github.com/new, crea un repository **privato** chiamato `ChristmasCountdown` e seleziona **Add a README file**. Crea il repository.
2. Nel repository apri **Add file → Upload files**. Carica il **contenuto** della cartella `ChristmasCountdown`, mantenendo la struttura delle cartelle. Nota: il sito GitHub può non caricare le cartelle nascoste come `.github` con il drag-and-drop. Per assicurarti che il workflow sia presente, usa il passo 3.
3. Se `.github/workflows/build-apk.yml` non appare nel repository, scegli **Add file → Create new file** e scrivi `.github/workflows/build-apk.yml` come nome. Incolla il contenuto dell'omonimo file fornito nel progetto e conferma con **Commit changes**.
4. Vai su **Actions → Build Android APK → Run workflow → Run workflow** (se GitHub richiede di abilitare Actions, abilitalo per il repository).
5. Attendi che il job `build` diventi verde. Apri l'esecuzione e, nella sezione **Artifacts**, scarica `ChristmasCountdown-APK`.
6. Estrai lo ZIP dell'artifact: dentro trovi `app-debug.apk`. Trasferiscilo sul telefono Android e aprilo per installarlo. Autorizza l'installazione da questa sorgente solo se ti fidi del file.

Il workflow compila con Java 17, Android SDK 35 e Gradle 8.9 su GitHub, non sul tuo PC. La prima compilazione può richiedere alcuni minuti. Il file è un **APK debug** firmato automaticamente per test/installazione personale, non una release Play Store.

**Attenzione:** se non vedi il workflow in Actions, verifica che il file `.github/workflows/build-apk.yml` esista nel branch `main` (o nel branch predefinito) e che Actions sia abilitato.
