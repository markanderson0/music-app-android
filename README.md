Music App - Android
===================

Android version of the music app.

This application allows users to find various details about artists such as:

 - what tickets they have on sale
 - what merch they have available
 - setlists of their previous shows
 - what albums they have released.

----------

Details of each feature
---
Artist Profile
-
- This is a feature page for an individual artist.
- View the artists most popular videos, all videos, tickets, merch, previous shows and albums.

**Note**: All data for top videos is stored locally. Tickets data is loaded via the TicketMaster API. Merch data is loaded via the Ebay API. Previous shows data is loaded via the setlist.fm API.
Albums data is loaded via the Spotify API.

<img src="screenshots/artist/artistTopVideos.png" width="33%" />
<img src="screenshots/artist/artistVideos.png" width="33%" />
<img src="screenshots/artist/artistVideosExpanded.png" width="33%" />
<img src="screenshots/artist/artistTickets.png" width="33%" />
<img src="screenshots/artist/artistMerch.png" width="33%" />
<img src="screenshots/artist/artistShows.png" width="33%" />
<img src="screenshots/artist/artistShowsSetlist.png" width="33%" />
<img src="screenshots/artist/artistAlbums.png" width="33%" />

Video Player
-
- View a selected video.
- View the videos in the same playlist.

**Note**: All data for videos is stored locally.

<img src="screenshots/videoplayer/videoplayer.png" width="33%" />

Browse
-
- Browse for artists by selecting a genre from the grid and find artists associated with that genre.

**Note**: Data for the genre and artist tiles are stored locally.

<img src="screenshots/browse/browseNavigation.png" width="33%" />
<img src="screenshots/browse/browseGenre.png" width="33%" />

Tickets
-
- Find tickets from the most popular artists currently on tour on the home tickets screen.
- Search for tickets of a particular genre by clicking one of the genre buttons or enter an artists name into the search bar to find tickets for a specific artist.
- Tickets for an artist can be viewed as a list or on a map provided via Google Maps.

**Note**: Data for the top tickets is stored locally and all other ticket data is loaded from the TicketMaster API.

<img src="screenshots/tickets/topTickets.png" width="33%" />
<img src="screenshots/tickets/ticketsGenre.png" width="33%" />
<img src="screenshots/tickets/artistTicketsList.png" width="33%" />
<img src="screenshots/tickets/artistTicketsMap.png" width="33%" />

Merch
-
- Find artist merch by searching from the available product categories.
- Search for a specific artists merch via the search bar.

**Note**: All data for merch is loaded via the Ebay API.

<img src="screenshots/merch/merchCategory.png" width="33%" />
<img src="screenshots/merch/merchProduct.png" width="33%" />

Search
-
- Search for artists using the search bar located on the left sidebar to find artists with the desired name.

**Note**: All data for searching is loaded via the Spotify API.

<img src="screenshots/search/search.png" width="33%" />

User Profile
-
- This is a feature page for an individual user.
- View the videos that the user has uploaded, what artists they follow and what videos they have favourited.

**Note**: All data for videos, following and favourites is stored locally.

<img src="screenshots/user/profile/profileVideos.png" width="33%" />
<img src="screenshots/user/profile/profileVideosExpanded.png" width="33%" />
<img src="screenshots/user/profile/profileFollowing.png" width="33%" />
<img src="screenshots/user/profile/profileFavourites.png" width="33%" />
<img src="screenshots/user/profile/profileFavouritesExpanded.png" width="33%" />

Following
-
- View a list of the artists that you are currently following.

**Note**: All data for following is stored locally.

<img src="screenshots/user/following/following.png" width="33%" />

Upload
-
- Users can upload videos that they have captured from an artists show.
- To upload a video the user must:
  1. Select a file from their file system. (Not currently implemented)
  2. Enter an artists name into the relevant text field.
  3. Enter the year that the show took place.
  4. Select the particular show from which their video was recorded from the shows dialog.
  5. Select the songs that the artist played in the video from the songs dialog.
  6. Press the upload button to review the details for the upload and click the terms check box to upload the video.
- Once the artist name and show year have been entered an api call is made to get the list of shows, then once a show is selected, another api call is made to get the songs played during that show.
- On the My Uploads tab a user can view the videos that they have previously uploaded and select to delete the video or add time cue points to each song in the video to allow users to skip to the specific point in the video that the particular song starts.

**Note**: All data for by My Uploads is stored locally. The delete video button isnt currently functional and is only for show. Data for uploading a video is loaded from the setlist.fm API.

<img src="screenshots/user/upload/uploadVideo.png" width="33%" />
<img src="screenshots/user/upload/uploadVideoShowsDialog.png" width="33%" />
<img src="screenshots/user/upload/uploadVideoSongsDialog.png" width="33%" />
<img src="screenshots/user/upload/uploadVideoComplete.png" width="33%" />
<img src="screenshots/user/upload/uploadVideoConfirmDialog.png" width="33%" />
<img width="33%" />
<img src="screenshots/user/upload/myUploads.png" width="33%" />
<img src="screenshots/user/upload/myUploadsExpanded.png" width="33%" />
<img src="screenshots/user/upload/myUploadsDialog.png" width="33%" />

Settings
-
- Users can change their email address and date of birth.
- The user can also change their password and toggle various settings regarding privacy and email notifications.


**Note**: The Login/Logout and Signup features arent currently functional and are only for show.

<img src="screenshots/user/settings/editProfile.png" width="33%" />
<img src="screenshots/user/settings/changePassword.png" width="33%" />
<img src="screenshots/user/settings/editPrivacy.png" width="33%" />
<img src="screenshots/user/settings/emailNotifications.png" width="33%" />

---

Additional Details
-
- Application follows an MVP architecture.
- Testing for this application was conducted using Espresso, JUnit and Mockito.
- Libraries used include:
    - Butterknife
    - RxJava/RxAndroid
    - Dagger2
    - Retrofit2
    - GSON
    - Picasso

---

**Note**: All api keys have been removed from the project.

---

TODO
---
Libraries to investigate:
-
- Robolectric for testing.
- Lombok for reducing boilerplate code in models.

Features to add (This list is ever expanding and in no particular order):
-
- File upload on the video uploads page.
- Add cue points menu to my uploads video dialog.
- Tick indicator when selecting songs to add when uploading a video.
- Link to other users profiles.
- Link all videos to the video player.


