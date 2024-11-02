package pl.marbirch.fiszki2.data.model.domain

sealed class MenuEvents {
    data object NewGame : MenuEvents()
    data object History : MenuEvents()  //Dorobić logikę z historia
}