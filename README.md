ImageRulette
==============

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c5bdf00d73754cba8c5b071621bce964)](https://www.codacy.com/app/nineunderground/imagerulette?utm_source=github.com&utm_medium=referral&utm_content=nineunderground/imagerulette&utm_campaign=badger)

Simple Vaadin application that only requires a Servlet 3.0 container to run. It has Jetty server embedded into war file

Workflow
========

Basic Vaadin web app with Touchkit included that allows to set up to 30 static url images.
With all uploaded URLs, user can setup how many images to use and then play with a dice simulator
All urls are stored in browser cache.

Views
-------------------------

1. -> Upload images
![Upload logo](docs/screenshots/uploadImages.png "Upload")

2. -> Setup images
![Upload logo](docs/screenshots/setupImages.png "Upload")

3. -> Dice simulator
![Upload logo](docs/screenshots/diceSimulator.png "Upload")


Running project
-------------------------

From Vaadin template doc...
To compile the entire project, run "mvn install".
To run the application, run "mvn jetty:run" and open http://localhost:8080/ .
