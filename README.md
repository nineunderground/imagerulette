ImageRulette
==============

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c5bdf00d73754cba8c5b071621bce964)](https://www.codacy.com/app/nineunderground/imagerulette?utm_source=github.com&utm_medium=referral&utm_content=nineunderground/imagerulette&utm_campaign=badger) [![Build Status](https://travis-ci.org/nineunderground/imagerulette.svg?branch=master)](https://travis-ci.org/nineunderground/imagerulette) [![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0)

[You can try here](https://mighty-ravine-21171.herokuapp.com)

Simple Vaadin application that only requires a Servlet 3.0 container to run. It has Jetty server embedded into war file

Workflow
========

Basic Vaadin web app with Touchkit included that allows to set up to 30 static url images.
With all uploaded URLs, user can setup how many images to use and then play with a dice simulator
All urls are stored in browser cache.

Views
-------------------------

Upload images

![Upload logo](docs/screenshots/uploadImages.png "Upload")

Setup images

![Setup logo](docs/screenshots/setupImages.png "Setup")

Dice simulator

![Simulator logo](docs/screenshots/diceSimulator.png "Simulator")


Running project locally
-------------------------

From Vaadin template doc...
To compile the entire project, run "mvn install".
To run the application, run "mvn jetty:run" and open http://localhost:8080/ .
