# My Personal Project: Mineral Wiki

## Overview

My personal project will be a mineral database that allows users to view minerals, their
important properties and will allow them to search and filter minerals based on these
properties. 


The program should also provide suggested similar minerals and should provide a way to 
navigate through mineral families. For example the **feldspar** mineral group should refer to entries
of common minerals in the feldspar family. *Finally*, I plan on allowing users to upload images of 
these minerals to these individual entries. 

## Who could use this

This project could be of use to:

1. Collectors of gems and minerals
2. Geology enthusiasts
3. People looking for data or properties of specific materials

I have always been interested in minerals and geology and would find a resource
like this very useful for learning about new minerals. 

## User Stories

- *As a user*, I would like to view a sortable table of all minerals and their respective properties
- *As a user*, I would like to add minerals that are not included in the database
- *As a user*, I would like to be able to search and view minerals by name, chemical composition and other important properties
- *As a user*, I would like for the ability to add short entries on a mineral's page
- *As a user*, I would like to view images of these minerals and upload my own 


- *As a user*, I would like to be prompted to save my current database to a file when I quit the application
- *As a user*, I would like to be prompted to load a pre-existing mineral database from a save file when I start 
the application [^1]

### Using the Gui
- To add X to Y, click the "Add Item" box specify either a mineral or family and enter the values in
- Note the "Formula" segment must contain a valid chemical formula
- The "Table" and "Item" page button provide ways to view either all the data or an individual page
- Clicking on an item in the table page loads its corresponding item page
- In the item view, wiki entries can be edited/deleted
- Clicking on the table headers sorts/groups it by the corresponding property. This works for all quantitative properties
and "Crystal Structure" and "Cleavage"
- The "File" button produces a dropdown that gives options for saving/loading
- If a file has not been saved before, use "Save As" to designate a path or to make a new Save Path

---

[^1]: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/tree/master/src/main/persistence 