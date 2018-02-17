# QR CODE TREASURE HUNT
A simple QR Code Based Treasure Hunt Game. Traditional Treasure hunts require pages and manual work. This application aims at going green and paperless. Just Scan the correct QR Code and get the next hint, until you reach the FINAL DESTINATION!

## Getting Started
This app requires nothing more than a Web browser to generate hints. Android App can be directly installed on the mobile which can be found in the /bin folder.
There are two stages while using this application.
  1. Generating hints.
  2. Scanning and playing the treasure hunt.

### Generating hints
Open Hints-Generator/index.html file. Follow on screen instructions to generate the hints. Hints can be generated for upto 10 groups(0 - 9) and only upto 10 hints per group.
After the QR Codes have been generated, save them to a secure location and print them on a page.

**Note :-** You have to generate, print and place the hints at appropriate places before playing the game.

**The Group Code can be viewed in app at the bottom of the screen or in the log cat.**
You can only scan those QR Codes that have been generated for your group code, else it gives an error saying _"Wrong QR Code Scanned."_

### Scanning and playing
Install the android app. There are two options available :- 1) Hints & 2) Hunt

**Hints** - Use this option to find all your previously scanned hints (qr codes).

**Hunt** - Use this option to scan a QR Code.

##TODOs
1. Use Encryption and Decryption so that other qr code scanners wont be able to show hints.
2. Show the Group Code Somewhere.
3. Add an onboarding screen for simplfying instructions to use the App.

## License
This project is licensed under the Apache License, Version 2.0 - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
The only inspiration was COEP college and the way this geeky college and the students carried out their Treasure Hunt - on paper!!! It felt so heartbreaking to watch such a reputed college where TechnoFreaks reside having their Treasure Hunt on paper.
