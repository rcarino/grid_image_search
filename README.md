grid_image_search
=================
This is an Android demo application for displaying google image search results

Time spent: 20 hours spent in total

Completed user stories:

 * [x] Required: User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] Required: User can click on "settings" which allows selection of advanced search options to filter results
 * [x] Required: User can configure advanced search filters
 * [x] Required: Subsequent searches will have any filters applied to the search results
 * [x] Required: User can tap on any image in results to see the image full-screen
 * [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
 * [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
 * [x] Advanced: User can share an image to their friends or email it to themselves
 * [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
 * [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
 * [x] Bonus: Use the StaggeredGridView to display improve the grid of image results
 * [x] Bonus: User can zoom or pan images displayed in full-screen detail view (Buggy, does not work together with swipe left/right to change images)
 * [x] Bonus: User can swipe left and right to change fullsreen images


Notes:

Pinch zoom and swipe left/right to change fullscreen images don't work together. After implementing swipe left/right, pinch zoom stopped working.

Walkthrough of all user stories:

![Video Walkthrough](anim_grid_imagesearch.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
