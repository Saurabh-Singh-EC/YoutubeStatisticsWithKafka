* This project is about getting the statistics of your YouTube playlist.
* It requires the playlist id, this id is used to make a rest call to the YouTube api.
* Once we have the playlist statistics, we make a call to the YouTube api with VideoId to get the video statistics.
* We are using kafka producer, streams and kafka consumer to achieve this.