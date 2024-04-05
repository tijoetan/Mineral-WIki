### Diagram Image
![UML image](UML%20Image.svg)

### Code for Diagram 
```plantumlx
@startuml
!theme vibrant
'skinparam ranksep 50
'skinparam nodesep 50
skinparam linetype polyline
skinparam arrowFontColor darkblue
skinparam arrowFontSize 15
skinparam arrowMessageAlignment right
skinparam groupInheritance 2

class Formula
class FormulaElement
class MoleculeGroup


class Family
class Mineral
abstract class WikiEntry


enum AtomicSymbols
enum Cleavage
enum CrystalStructure

class Event
class EventLog

class FamilyTable
class MineralTable
interface WikiEntryTable




class TableReader
class TableWriter

interface Writable


class AddedItemBox
class AdditionPanel
class FamilyAdditionPanel
class FamilyQueryHandler

class MineralAdditionPanel
class MineralQueryHandler

class QuerySelector

class ClickItemHandler
interface ClickObserver

abstract class DisplayPage
class EntryHyperLink
class FamilyDisplayPage
class ItemView
class MineralDisplayPage

class FileBroswer
class LoadSavePopupMenu

class CardPanel
class DescendantMenu
class Images
class UserQuery

class NameCellRenderer
class TableDataHandler
class TableView

package "TableView Listeners" <<Node>> {
class ClickMouseListener
class HyperLinkManager
}


class Toolbar
package "Toolbar Listeners" <<Node>> {
class SearchButtonListener
class WindowStateListener
class DeleteButtonListener
class FileHandler
class MineralAdditionButtonListener
}

class Main
class MineralWikiGuiApp


enum Attributes

WikiEntry .up.|> Writable
WikiEntryTable .up.|> Writable

MineralTable .up.|> WikiEntryTable
FamilyTable .up.|> WikiEntryTable

Mineral --|> WikiEntry
Family --|> WikiEntry

MineralWikiGuiApp .up.|> ClickObserver

MineralDisplayPage --|> DisplayPage
FamilyDisplayPage --|> DisplayPage

Formula "1"  --> "0..*" FormulaElement
Formula "1"  --> "0..*" MoleculeGroup
MoleculeGroup "1" --> "0..*" FormulaElement

FormulaElement "1" --> "1" AtomicSymbols

WikiEntry "1" --> "1" Formula

Mineral "1" --> "1" Cleavage
Mineral "1" --> "1" CrystalStructure

Family "1" --> "0..*" WikiEntry

FamilyTable "1" --> "0..*" WikiEntry
MineralTable "1"  --> "0..*" Mineral

TableReader "1"  --> "1" MineralTable
TableReader "1"  --> "1" FamilyTable

FamilyAdditionPanel "1" --> "1" DescendantMenu
DescendantMenu "1" --> "1" AdditionPanel

ClickItemHandler "1" --> "0..*" ClickObserver
ClickItemHandler "1" --> "0..1" WikiEntry

EntryHyperLink "1" --> "1" WikiEntry
FamilyDisplayPage "1" --> "1" Family
ItemView "1" --> "0..1" DisplayPage

MineralDisplayPage "1" --> "1" Mineral

Images "1" --> "0..7" Cleavage
Images "1" -up-> "0..7" CrystalStructure

TableDataHandler "1" --> "1" WikiEntryTable
TableDataHandler "1" --> "1" Attributes
TableView "1" --> "1" TableDataHandler
TableView "1" -[#red]-+ "1" HyperLinkManager
TableView "1" -[#red]-+ "1" ClickMouseListener

Toolbar "1" --> "1" LoadSavePopupMenu
Toolbar "1" --> "2" TableDataHandler
Toolbar "1" --> "1" CardPanel

Toolbar "1" -[#red]-+ "1" SearchButtonListener
Toolbar "1" -[#red]-+ "1" WindowStateListener
Toolbar "1" -[#red]-+ "1" DeleteButtonListener
Toolbar "1" -[#red]-+ "1" FileHandler
Toolbar "1" -[#red]-+ "1" MineralAdditionButtonListener
MineralWikiGuiApp "1" --> "2" TableView
@enduml
```
