package com.latihan.batiqu.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.latihan.batiqu.R
import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.ui.theme.BatiquTheme

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataBatik = Batik(
        id = 0,
        name = "AlKautsarRussetyaTamora",
        origin = "Surakarta",
        image = R.drawable.al,
        description = "Surakarta berisi banyak sekali jenis batik yang harus selalu dijaga agar tidak cepat dilupakan oleh masyarakat kedepan.",
        since = "Jungler",
        price = "Rp. 5.000 - Rp. 10.000",
        status = "Ready",
        isFavorite = false
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            BatiquTheme {
                DetailInformation(
                    id = fakeDataBatik.id,
                    name = fakeDataBatik.name,
                    origin = fakeDataBatik.origin,
                    image = fakeDataBatik.image,
                    description = fakeDataBatik.description,
                    since = fakeDataBatik.since,
                    status = fakeDataBatik.status,
                    price = fakeDataBatik.price,
                    isFavorite = fakeDataBatik.isFavorite,
                    navigateBack = {}
                ) { _, _ -> }
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeDataBatik.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataBatik.origin).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataBatik.since).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataBatik.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        val isFavorite = fakeDataBatik.isFavorite
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}