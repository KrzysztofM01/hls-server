# Simple spring based HTTP live streaming (HLS) server

####Hard requirements for application to work:
- ffmpeg

####Allows authorized clients (Spring security basic auth) to:
- Create, read, update and delete users (basic CRUD with usage of spring-data)
- Upload audio files to server and download them in hls-like streaming way

Files can be stored internally on disk or in google cloud with the usage of spring-cloud-gcp. (via configuration)
Uploaded audio files are cut into configured chunks (via ffmpeg), and their m3u8 (metadata) location is stored
in a database.