package com.yaromchikv.thecatapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yaromchikv.thecatapi.model.Cat
import com.yaromchikv.thecatapi.ui.overview.OverviewViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NavigationTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: OverviewViewModel
    private lateinit var cat: Cat

    @Before
    fun initialization() {
        viewModel = OverviewViewModel()
        cat = Cat("abc", "https://cdn2.thecatapi.com/images/abc.jpg", 768, 1024)
    }

    @Test
    fun `Checking the navigation LiveData`() {
        viewModel.displayCatDetails(cat)
        assertEquals(viewModel.navigateToSelectedCat.value, cat)
    }

    @Test
    fun `Checking the completion of navigation`() {
        viewModel.displayCatDetails(cat)
        viewModel.displayCatDetailsComplete()
        assertEquals(viewModel.navigateToSelectedCat.value, null)
    }
}