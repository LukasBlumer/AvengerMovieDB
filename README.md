# AvengerMovieDB

## Overview
The Avenger Movie Database is a collaborative project for the class Software Engineering at the University of Zurich Institute for Informatics. The aim is to take a provided source file and Stakeholder text and develop an interactable movie database according to their specifications.
The complete code for our project is located in this GitHub. As was specified for the exercise, we are using GWT to implement the movie database. In addition we also use Google Charts to visualize most of the data.

## Architecture
In the following an overview over the architecture of the code in the project.

### Background
The basic idea is that we do most (if not nearly all) of the heavy lifting needed to process and display the data on the side of the client, as the capacity of the app on the appengine is limited.
The source file(s) are located on the server and are preprocessed before being sent to the client where the data is converted into an internal MovieCollection object, which is our internal database format.
The MovieCollection exists as one original instance and a second instance, which is reduced and enlargened via filters. That collection is then converted, depending on the visualization type, into a fitting hashmap which then gets converted into the dataTable needed for the specific Google Chart.
The whole thing is inserted into the website by a central file organizing the different panels the website is parted up into.

### client
Due to limitations of the appengine memory, most of the processing and general work is done clientside. We handle visualizations here including column, pie, geo and table charts. In addition the parser does its work here as well as the export functionality and the basic website framework.

### preprocessing
The preprocessing side on the server handles some smaller changes to the data from the source file to then send that to the actual parser located clientside.

### shared
This part of the app handles the internal database object, the MovieCollection. It also contains the converter which takes the current, filtered, MovieCollection and converts it to hashmaps and then to DataTables.

## Contributors
* Petra Wittwer
* Selin Fabel
* Armando Müller
* Lukas Blumer

## Sources