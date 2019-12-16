package ru.skillbranch.gameofthrones.di.modules

import dagger.Module
import dagger.Provides
import ru.skillbranch.gameofthrones.di.ListScope
import ru.skillbranch.gameofthrones.presentation.list.ListViewModel
import ru.skillbranch.gameofthrones.presentation.list.ListViewModelFactory

@Module
class ListModule {
    @Provides
    @ListScope
    fun provideListViewModelFactory(): ListViewModelFactory = ListViewModelFactory()

    @Provides
    @ListScope
    fun provideListViewModel(listViewModelFactory: ListViewModelFactory): ListViewModel =
        listViewModelFactory.create(ListViewModel::class.java)
}